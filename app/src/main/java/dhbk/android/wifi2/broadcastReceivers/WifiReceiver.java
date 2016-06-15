package dhbk.android.wifi2.broadcastReceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import dhbk.android.wifi2.models.WifiModel;
import dhbk.android.wifi2.utils.Constant;
import dhbk.android.wifi2.utils.NetworkDb;

/**
 * Created by phongdth.ky on 6/14/2016.
 */
public class WifiReceiver extends BroadcastReceiver {
    private static final String TAG = WifiReceiver.class.getSimpleName();
    private static boolean firstConnect = true;
    private static boolean firstDisconnect = true;

    private static String mSsid;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (WifiManager.NETWORK_STATE_CHANGED_ACTION.equals(intent.getAction())) {
            NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);

            // ket noi
            if (info != null && info.isConnected()) {
                if (firstConnect) {
                    // Do your work.

                    // e.g. To check the Network Name or other info:
                    WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                    WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                    mSsid = wifiInfo.getSSID();

                    firstDisconnect = true;
                    firstConnect = false;

                    // : if date wrong, change locale
                    // time now
                    Date now = new Date(System.currentTimeMillis());
                    DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.JAPANESE);
                    String nowDate = formatter.format(now);


                    // : save connect to database, state(disconnect), ssid and data
                    NetworkDb networkDb = NetworkDb.getInstance(context);
                    WifiModel wifiModel = new WifiModel(Constant.WIFI_CONNECT, mSsid, nowDate);
                    networkDb.addWifiHotspot(wifiModel);
                }

            }
            // da ngat ket noi
            else {
                if (firstDisconnect) {
                    firstConnect = true;
                    firstDisconnect = false;

                    // time now
                    Date now = new Date(System.currentTimeMillis());
                    DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.JAPANESE);
                    String nowDate = formatter.format(now);

                    // : save disconnect to database, state(disconnect), ssid and data
                    NetworkDb networkDb = NetworkDb.getInstance(context);
                    WifiModel wifiModel = new WifiModel(Constant.WIFI_DISCONNECT, mSsid, nowDate);
                    networkDb.addWifiHotspot(wifiModel);
                }
            }
        }
    }
}
