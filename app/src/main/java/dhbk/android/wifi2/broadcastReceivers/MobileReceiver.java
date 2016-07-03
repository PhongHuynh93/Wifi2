package dhbk.android.wifi2.broadcastReceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import dhbk.android.wifi2.utils.Connectivity;

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

                }
            } else {
                if (firstMobileDisconnect) {
                    firstMobileConnect = true;
                    firstMobileDisconnect = false;


                }
            }
        }
    }
}
