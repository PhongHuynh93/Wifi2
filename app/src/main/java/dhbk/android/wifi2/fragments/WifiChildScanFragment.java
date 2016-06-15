package dhbk.android.wifi2.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import dhbk.android.wifi2.R;
import dhbk.android.wifi2.adapters.ScanWifiRecyclerviewAdapter;
import dhbk.android.wifi2.models.WifiModel;
import dhbk.android.wifi2.utils.Constant;

// FIXME: 6/15/16 not show all of wifi around yet
public class WifiChildScanFragment extends Fragment {
    private static final String TAG = WifiChildScanFragment.class.getSimpleName();
    private WifiScanReceiver mWifiReciever;
    private WifiManager mWifi;
    private RecyclerView mListWifiRecyclerView;
    ArrayList<WifiModel> wifiModels;

    public WifiChildScanFragment() {
    }

    public static WifiChildScanFragment newInstance() {
        WifiChildScanFragment fragment = new WifiChildScanFragment();
        return fragment;
    }

    // start scan wifi
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mWifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        mWifiReciever = new WifiScanReceiver();
        mWifi.startScan();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_wifi_child_scan, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // add a placeholder for adapter in main thread
        mListWifiRecyclerView = (RecyclerView) view.findViewById(R.id.list_scan_wifi);
        wifiModels = new ArrayList<>();
        WifiModel emptyWifi = new WifiModel("empty", "empty");
        wifiModels.add(emptyWifi);
        ScanWifiRecyclerviewAdapter scanWifiRecyclerviewAdapter = new ScanWifiRecyclerviewAdapter(wifiModels);
        mListWifiRecyclerView.setAdapter(scanWifiRecyclerviewAdapter);
        mListWifiRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        mListWifiRecyclerView.setHasFixedSize(true);
    }

    // register broadcast to get wifi scans
    @Override
    public void onResume() {
        getActivity().getApplicationContext().registerReceiver(mWifiReciever, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        super.onResume();
    }

    // unregister
    @Override
    public void onPause() {
        super.onPause();
        getActivity().getApplicationContext().unregisterReceiver(mWifiReciever);
    }

    // declare
    private void addWifiHotSpotToRcv(ArrayList<WifiModel> wifis) {
        ScanWifiRecyclerviewAdapter scanWifiRecyclerviewAdapter = (ScanWifiRecyclerviewAdapter) mListWifiRecyclerView.getAdapter();
        wifiModels.clear();
        wifiModels.addAll(wifis);
        scanWifiRecyclerviewAdapter.notifyItemRangeChanged(0, wifiModels.size());
    }

    // get result and show in recyclerview
    public class WifiScanReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            List<ScanResult> wifiScanList = mWifi.getScanResults();
            ArrayList<WifiModel> wifis = new ArrayList<>();

            // : 6/14/2016 send result back
            for (int i = 0; i < wifiScanList.size(); i++) {
                String ssid = wifiScanList.get(i).SSID;
                String capabilities = wifiScanList.get(i).capabilities;
                String encryption;
                if (capabilities.contains(Constant.WIFI_WPA)) {
                    // We know there is WPA encryption
                    encryption = Constant.WIFI_WPA;

                } else if (capabilities.contains(Constant.WIFI_WEP)) {
                    // We know there is WEP encryption
                    encryption = Constant.WIFI_WEP;

                } else {
                    // Another type of security scheme, open wifi, captive portal, etc..
                    encryption = Constant.WIFI_OPEN;
                }

                WifiModel wifiModel = new WifiModel(ssid, encryption);
                wifis.add(wifiModel);
            }

            // TODO: 6/14/2016 populate vào recyclerview
            addWifiHotSpotToRcv(wifis);
        }
    }
}
