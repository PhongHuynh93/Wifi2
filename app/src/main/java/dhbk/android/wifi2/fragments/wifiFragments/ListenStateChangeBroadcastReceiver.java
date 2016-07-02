package dhbk.android.wifi2.fragments.wifiFragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.support.v4.app.Fragment;

import dhbk.android.wifi2.interfaces.OnFragInteractionListener;

/**
 * Created by phongdth.ky on 7/1/2016.
 * listen for network state change
 */
public class ListenStateChangeBroadcastReceiver extends BroadcastReceiver {
    private static boolean firstConnect = true;
    private final Fragment mFragment;
    private final String mSsid;
    private final String mPass;

    public ListenStateChangeBroadcastReceiver(Fragment fragment, String ssid, String pass) {
        this.mFragment = fragment;
        this.mSsid = ssid;
        this.mPass = pass;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        final String action = intent.getAction();
        if (action.equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)) {
            NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
            boolean connected = info.isConnected();
            if (connected) {
                if (firstConnect) {
                    firstConnect = false;
                    if (mFragment instanceof OnFragInteractionListener.OnWifiScanFragInteractionListener) {
                        // allow to save that wifi hotspot to db
                        ((OnFragInteractionListener.OnWifiScanFragInteractionListener) mFragment).onAllowToSaveWifiHotspotToDb(mSsid, mPass);
                    }
                }
            } else {
                firstConnect = true;
            }
        }
    }
}
