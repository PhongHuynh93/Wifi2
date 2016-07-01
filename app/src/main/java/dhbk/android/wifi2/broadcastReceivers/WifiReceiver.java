package dhbk.android.wifi2.broadcastReceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import dhbk.android.wifi2.models.WifiLocationModel;
import dhbk.android.wifi2.models.WifiModel;
import dhbk.android.wifi2.models.WifiStateAndDateModel;
import dhbk.android.wifi2.utils.Constant;
import dhbk.android.wifi2.utils.HelpUtils;
import dhbk.android.wifi2.utils.db.NetworkDb;

/**
 * Created by phongdth.ky on 6/14/2016.
 * listen state of wifi: connect and disconnect, save state to db
 */

public class WifiReceiver extends BroadcastReceiver {
    private static final String TAG = WifiReceiver.class.getSimpleName();

    // because when i connect or disconnect to a wifi network, this broadcast is called about 2 or 3 times
    // i only need 1 time that we can save to db, so i make 2 static var to remember not to save to db duplicate.
    private static boolean firstConnect = true;
    private static boolean firstDisconnect = true;

    private static String mSsid;
    private static String mBssid;
    private static int mRssi;
    private static String mMacAddress;
    private static int mIpAddress;
    private static int mLinkSpeed;
    private static int mNetworkId;
    private static String mEncryption;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (WifiManager.NETWORK_STATE_CHANGED_ACTION.equals(intent.getAction())) {
            NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);

            // wifi state connect the first time, so I spare wifi info, get date at the moment and save to db.
            if (info != null && info.isConnected()) {
                if (firstConnect) {
                    // 2 var to make this class run one time
                    firstDisconnect = true;
                    firstConnect = false;
                    // wifi info
                    WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                    WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                    mSsid = wifiInfo.getSSID();
                    mSsid = HelpUtils.getStringAfterRemoveChar(mSsid, "\"");
                    mBssid = wifiInfo.getBSSID();
                    mRssi = wifiInfo.getRssi();
                    mMacAddress = wifiInfo.getMacAddress();
                    mIpAddress = wifiInfo.getIpAddress();
                    mLinkSpeed = wifiInfo.getLinkSpeed();
                    mNetworkId = wifiInfo.getNetworkId();

                    // time now
                    Date now = new Date(System.currentTimeMillis());
                    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.UK);
                    String nowDate = formatter.format(now);

                    // TODO: 6/29/2016 check if user has turn on location, if has turn on, get current location
                    int isHasLocation = 0;

                    if (HelpUtils.isGpsHasTurnOn(context)) {
                        isHasLocation = 1;
                    } else {
                        isHasLocation = 0;
                    }

                    double latitude = 0;
                    double longitude = 0;
                    if (isHasLocation == 1) {
                        // get gps and save to db
                        Location currentLocation = HelpUtils.getCurrentLocation(context);
                        if (currentLocation != null) {
                            latitude = currentLocation.getLatitude();
                            longitude = currentLocation.getLongitude();
                        }
                    }

                    NetworkDb networkDb = NetworkDb.getInstance(context);

                    // add  wifi info
                    WifiModel wifiInfoModel = new WifiModel(mSsid, mBssid, mMacAddress, mNetworkId);
                    networkDb.addWifiInfoToTable(wifiInfoModel);

                    // because wifiLocation table and wifi state and date table make a name that contain bssid, so we must check whether bssid is null or not.
                    if (mBssid != null) {
                        // add location to wifi
                        WifiLocationModel wifiLocationModel = new WifiLocationModel(mBssid, latitude, longitude, isHasLocation);
                        networkDb.addWifiLocationToTable(wifiLocationModel);

                        // add state and date to db
                        WifiStateAndDateModel wifiStateAndDateModel = new WifiStateAndDateModel(mBssid, mLinkSpeed, mRssi, nowDate, Constant.WIFI_CONNECT, mIpAddress);
                        networkDb.addStateAndDateWifiToTable(wifiStateAndDateModel);
                    }
                }

            }

            // wifi state disconnect the first time, so I spare wifi info, get date at the moment and save to db.
            else {
                if (firstDisconnect) {
                    firstConnect = true;
                    firstDisconnect = false;

                    // time now
                    Date now = new Date(System.currentTimeMillis());
                    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.UK);
                    String nowDate = formatter.format(now);

                    // because wifiLocation table and wifi state and date table make a name that contain ssid, so we must check whether ssid is null or not.
                    if (mSsid != null) {
                        // add state and date to db
                        WifiStateAndDateModel wifiStateAndDateModel = new WifiStateAndDateModel(mBssid, mLinkSpeed, mRssi, nowDate, Constant.WIFI_DISCONNECT, mIpAddress);
                        NetworkDb networkDb = NetworkDb.getInstance(context);
                        networkDb.addStateAndDateWifiToTable(wifiStateAndDateModel);
                    }
                }
            }
        }
    }
}
