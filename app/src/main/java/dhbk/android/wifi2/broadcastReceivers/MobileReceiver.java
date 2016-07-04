package dhbk.android.wifi2.broadcastReceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import dhbk.android.wifi2.models.MobileModel;
import dhbk.android.wifi2.utils.Connectivity;
import dhbk.android.wifi2.utils.Constant;
import dhbk.android.wifi2.utils.HelpUtils;
import dhbk.android.wifi2.utils.db.NetworkDb;

/**
 * receive an intent whenever a network state has changed.
 */
public class MobileReceiver extends BroadcastReceiver {
    private static boolean firstMobileConnect = true;
    private static boolean firstMobileDisconnect = true;
    private String mobileType;
    private String generation;
    private String speedText;
    private String nowDate;



    @Override
    public void onReceive(Context context, Intent intent) {
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
            if (Connectivity.isConnectedMobile(context)) {
                if (firstMobileConnect) {
                    firstMobileDisconnect = true;
                    firstMobileConnect = false;

                    mobileType = Connectivity.getSubtypeName(context);
                    generation = Connectivity.getNetWordGeneration(context);
                    speedText = Connectivity.getSpeedMobile(context);
                    nowDate = HelpUtils.getNowDate();

                    NetworkDb networkDb = NetworkDb.getInstance(context);
                    MobileModel mobileReceiver = new MobileModel(mobileType, generation, speedText, nowDate, Constant.MOBILE_CONNECT);
                    networkDb.addMobile(mobileReceiver);
                }
            } else {
                if (firstMobileDisconnect) {
                    firstMobileConnect = true;
                    firstMobileDisconnect = false;
                    NetworkDb networkDb = NetworkDb.getInstance(context);
                    MobileModel mobileReceiver = new MobileModel(mobileType, generation, speedText, nowDate, Constant.MOBILE_CONNECT);
                    networkDb.addMobile(mobileReceiver);
                }
            }
        }
    }
}
