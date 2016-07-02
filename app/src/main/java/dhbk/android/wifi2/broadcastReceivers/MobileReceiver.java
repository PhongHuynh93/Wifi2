package dhbk.android.wifi2.broadcastReceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class MobileReceiver extends BroadcastReceiver {
    private static boolean firstMobileConnect = true;
    private static boolean firstMobileDisconnect = true;

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        boolean isConnected = activeNetInfo != null && activeNetInfo.isConnectedOrConnecting();
        if (isConnected) {
            if (firstMobileConnect) {
                Log.i("NET", "connecte" + isConnected);
                firstMobileDisconnect = true;
                firstMobileConnect = false;
            }
        } else {
            if (firstMobileDisconnect) {
                firstMobileConnect = true;
                firstMobileDisconnect = false;
                Log.i("NET", "not connecte" + isConnected);

            }
        }
    }
}
