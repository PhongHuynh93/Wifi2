package dhbk.android.wifi2.fragments;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import dhbk.android.wifi2.R;
import dhbk.android.wifi2.utils.Constant;

public class WifiFragment extends Fragment {

    public WifiFragment() {
    }

    public static WifiFragment newInstance() {
        WifiFragment fragment = new WifiFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_wifi, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView tv = (TextView) view.findViewById(R.id.mes_not_have_wifi);
    }

    // check if only when open app, not restart app
    @Override
    public void onStart() {
        super.onStart();
        hasUserTurnOnWifi();
    }

    private void hasUserTurnOnWifi() {
        WifiManager wifi = (WifiManager)getActivity().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (wifi.isWifiEnabled()){
            // : 6/13/2016 1 call method to show wifi access point
            showWifi();
        } else {
            // : 6/13/2016 2 call a dialog to help user turn on wifi
            WifiChildTurnOnDialogFragment wifiChildTurnOnDialogFragment = new WifiChildTurnOnDialogFragment();
            wifiChildTurnOnDialogFragment.show(getChildFragmentManager(), Constant.TAG_CHILD_WIFI_DIALOG_FRAGMENT);
        }
    }

    // add wifiChildScanFragment into container.
    private void showWifi() {
        WifiChildScanFragment wifiChildScanFragment = (WifiChildScanFragment) getChildFragmentManager().findFragmentByTag(Constant.TAG_CHILD_SCAN_WIFI_FRAGMENT);
        if (wifiChildScanFragment == null) {
            getChildFragmentManager()
                    .beginTransaction()
                    .add(R.id.wifi_frag_container, WifiChildScanFragment.newInstance(), Constant.TAG_CHILD_SCAN_WIFI_FRAGMENT)
                    .commit();
        }
    }

    // turn on wifi and add wifiscan fragment
    public void turnOnWifi() {
        WifiManager wifi = (WifiManager)getActivity().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        wifi.setWifiEnabled(true);
        showWifi();
    }
}
