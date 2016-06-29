package dhbk.android.wifi2.broadcastReceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import dhbk.android.wifi2.models.WifiModel;
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

                    // : save connect to database, state(disconnect), ssid and data
                    NetworkDb networkDb = NetworkDb.getInstance(context);
//                    WifiModel wifiModel = new WifiModel(Constant.WIFI_CONNECT, mSsid, nowDate, mBssid, mRssi, mMacAddress, mIpAddress, mLinkSpeed, mNetworkId);
//                    networkDb.addWifiHotspot(wifiModel);

                    // TODO: 6/29/2016 check if user has turn on location, if has turn on, get current location
                    // get wifi infor and save it to db
                    int isHasLocation = 0;

                    if (HelpUtils.isGpsHasTurnOn(context)) {
                        isHasLocation = 1;
                    } else {
                        isHasLocation = 0;
                    }
                    if (isHasLocation == 1) {
                        // get gps and save to db
                        Location currentLocation = HelpUtils.getCurrentLocation(context);
                        double latitude = currentLocation.getLatitude();
                        double longitude = currentLocation.getLongitude();
                        WifiModel wifiModel = new WifiModel(Constant.WIFI_CONNECT, mSsid, nowDate, mBssid, mRssi, mMacAddress, mIpAddress, mLinkSpeed, mNetworkId, latitude, longitude, isHasLocation);
                        networkDb.addWifiWithLocation(wifiModel);
                    } else {
                        double latitude = 0;
                        double longitude = 0;
                        WifiModel wifiModel = new WifiModel(Constant.WIFI_CONNECT, mSsid, nowDate, mBssid, mRssi, mMacAddress, mIpAddress, mLinkSpeed, mNetworkId, latitude, longitude, isHasLocation);
                        networkDb.addWifiWithLocation(wifiModel);
                    }
                }

            }

            // wifi state disconnect the first time, so I spare wifi info, get date at the moment and save to db.
            else {
                if (firstDisconnect) {
                    firstConnect = true;
                    firstDisconnect = false;

                    Log.i(TAG, "WifiReceiver onReceive: disconnected");
                    // time now
                    Date now = new Date(System.currentTimeMillis());
                    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.UK);
                    String nowDate = formatter.format(now);

                    // : save disconnect to database, state(disconnect), ssid and data
                    NetworkDb networkDb = NetworkDb.getInstance(context);
                    WifiModel wifiModel = new WifiModel(Constant.WIFI_DISCONNECT, mSsid, nowDate, mBssid, mRssi, mMacAddress, mIpAddress, mLinkSpeed, mNetworkId);
                    networkDb.addWifiHotspot(wifiModel);
                }
            }
        }
    }
}
