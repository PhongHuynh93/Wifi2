package dhbk.android.wifi2.broadcastReceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import dhbk.android.wifi2.utils.Connectivity;
import dhbk.android.wifi2.utils.db.NetworkDb;

/**
 * receive an intent whenever a network state has changed.
 */
public class MobileReceiver extends BroadcastReceiver {
    private static boolean firstMobileConnect = true;
    private static boolean firstMobileDisconnect = true;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
            if (Connectivity.isConnectedMobile(context)) {
                if (firstMobileConnect) {
                    firstMobileDisconnect = true;
                    firstMobileConnect = false;

                    NetworkInfo info = Connectivity.getNetworkInfo(context.getApplicationContext());
                    String mobileType = info.getSubtypeName();
                    NetworkDb networkDb = NetworkDb.getInstance(context);
//                    networkDb.addMobile(mobileType, speedText, nowDate, Constant.MOBILE_CONNECT);
                }
            } else {
                if (firstMobileDisconnect) {
                    firstMobileConnect = true;
                    firstMobileDisconnect = false;
//                    NetworkDb networkDb = NetworkDb.getInstance(context);
//                    networkDb.addMobile(mobileType, speedText, nowDate, Constant.MOBILE_DISCONNECT);
                }
            }
        }
    }
}
