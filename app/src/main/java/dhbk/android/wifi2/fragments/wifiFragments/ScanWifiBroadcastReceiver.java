package dhbk.android.wifi2.fragments.wifiFragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import dhbk.android.wifi2.interfaces.OnFragInteractionListener;
import dhbk.android.wifi2.models.WifiScanWifiModel;
import dhbk.android.wifi2.utils.Constant;

/**
 * Created by phongdth.ky on 7/1/2016.
 */
public class ScanWifiBroadcastReceiver extends BroadcastReceiver {

    private final Fragment mFragment;

    public ScanWifiBroadcastReceiver(Fragment fragment) {
        this.mFragment = fragment;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        List<ScanResult> wifiScanList = wifiManager.getScanResults();
        ArrayList<WifiScanWifiModel> wifis = new ArrayList<>();

        // : 6/14/2016 send result back
        for (int i = 0; i < wifiScanList.size(); i++) {
            String ssid = wifiScanList.get(i).SSID;
            String capabilities = wifiScanList.get(i).capabilities;

//                get wifi encryption
            String encryption;
            if (capabilities.contains(Constant.WIFI_WPA2)) {
                encryption = Constant.WIFI_WPA2;
            } else if (capabilities.contains(Constant.WIFI_WPA)) {
                encryption = Constant.WIFI_WPA;
            } else if (capabilities.contains(Constant.WIFI_WEP)) {
                encryption = Constant.WIFI_WEP;
            } else {
                encryption = Constant.WIFI_OPEN;
            }

            WifiScanWifiModel wifiScanWifiModel = new WifiScanWifiModel(ssid, encryption);
            wifis.add(wifiScanWifiModel);
        }
        // populate vào recyclerview
        if (mFragment instanceof OnFragInteractionListener.OnWifiScanFragInteractionListener) {
            ((OnFragInteractionListener.OnWifiScanFragInteractionListener) mFragment).onGetDataAfterScanWifi(wifis);
        }
    }
}
