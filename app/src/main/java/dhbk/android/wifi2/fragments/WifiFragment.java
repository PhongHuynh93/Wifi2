package dhbk.android.wifi2.fragments;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import dhbk.android.wifi2.R;
import dhbk.android.wifi2.utils.Constant;

public class WifiFragment extends Fragment {

    private static final String TAG = WifiFragment.class.getSimpleName();
    private boolean mHasTurnOnGps;

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

    // check if only when app start and restart app
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
                    .add(R.id.wifi_frag_container, WifiChildScanFragment.newInstance(mHasTurnOnGps), Constant.TAG_CHILD_SCAN_WIFI_FRAGMENT)
                    .commit();
        }
    }

    // turn on wifi and add wifiscan fragment
    public void turnOnWifi() {
        WifiManager wifi = (WifiManager)getActivity().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        wifi.setWifiEnabled(true);
        showWifi();
    }

    // : 6/16/2016 implement this to show a gps dialog
    public void showGpsDialogToTurnOn() {
        GpsChildTurnOnDialogFragment gpsChildTurnOnDialogFragment = new GpsChildTurnOnDialogFragment();
        gpsChildTurnOnDialogFragment.show(getChildFragmentManager(), Constant.TAG_CHILD_GPS_FRAGMENT);
    }

    //  a callback with para showing the gps has turn on or not
    public void hasTurnOnGPS(boolean b) {
        mHasTurnOnGps = b;
    }

    //  call Dialog to to let user complete pass
    public void onShowFillInPassWifiDialog(@NonNull String ssid, int position) {
        ChildConnectWifiDialogFragment childConnectWifiDialogFragment = ChildConnectWifiDialogFragment.newInstance(ssid, position);
        childConnectWifiDialogFragment.setStyle(DialogFragment.STYLE_NO_TITLE, 0);
        childConnectWifiDialogFragment.show(getChildFragmentManager(), Constant.TAG_CHILD_CONNECT_WIFI_FRAGMENT);
    }

    //  authen with pass at position in recyclerview at WifiChildTurnOnDialogFragment Fragment.
    public void onAuthen(String pass, int position) {
        // correct pass and position
        Log.i(TAG, "onAuthen: " + pass);
        Log.i(TAG, "onAuthen: " + position);

        // : 6/16/2016 depend on position, get ssid and security
        Fragment wifiChildScanFragment = getChildFragmentManager().findFragmentByTag(Constant.TAG_CHILD_SCAN_WIFI_FRAGMENT);
        if (wifiChildScanFragment instanceof WifiChildScanFragment) {
            ((WifiChildScanFragment)wifiChildScanFragment).authenWifiWithPass(pass, position);
        }
    }
}
