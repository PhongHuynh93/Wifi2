package dhbk.android.wifi2.fragments.wifiFragments;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import dhbk.android.wifi2.R;
import dhbk.android.wifi2.models.WifiHotsPotModel;
import dhbk.android.wifi2.utils.Constant;
import dhbk.android.wifi2.utils.db.NetworkDb;

public class WifiFragment extends Fragment {

    private static final String TAG = WifiFragment.class.getSimpleName();
    private boolean mHasTurnOnGps;

    public WifiFragment() {
    }

    public static WifiFragment newInstance() {
        return new WifiFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_wifi, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView tv = (TextView) view.findViewById(R.id.mes_not_have_wifi);
        Button retryBtn = (Button) view.findViewById(R.id.retry_scan_wifi);
        retryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                // : 6/18/16 call turn on wifi method
                turnOnWifi();
            }
        });
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

    // : 6/16/2016 implement this to show a gps dialog
    public void showGpsDialogToTurnOn() {
        GpsChildTurnOnDialogFragment gpsChildTurnOnDialogFragment = new GpsChildTurnOnDialogFragment();
        gpsChildTurnOnDialogFragment.show(getChildFragmentManager(), Constant.TAG_CHILD_GPS_FRAGMENT);
    }

    //  update gps button
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
        // : 6/16/2016 depend on position, get ssid and security
        Fragment wifiChildScanFragment = getChildFragmentManager().findFragmentByTag(Constant.TAG_CHILD_SCAN_WIFI_FRAGMENT);
        if (wifiChildScanFragment instanceof WifiChildScanFragment) {
            ((WifiChildScanFragment)wifiChildScanFragment).authenWifiWithPass(pass, position);
        }
    }

    //  (test again - get location):  6/16/16 save wifi states to database
    // isTurnOnGps = true => has location
    public void saveWifiHotspotToDb(String networkSSID, String networkPass, double latitude, double longitude, boolean isTurnOnGps) {
        int isTurnGpsInt = 0;
        if (isTurnOnGps) {
            isTurnGpsInt = 1;
        } else {
            isTurnGpsInt = 0;
        }


        WifiHotsPotModel wifiHotsPotModel = new WifiHotsPotModel(networkSSID, networkPass, latitude, longitude, isTurnGpsInt);
        NetworkDb networkDb = NetworkDb.getInstance(getActivity());
        networkDb.addWifiHotspotWithLocation(wifiHotsPotModel);
    }

    public boolean isHasTurnOnGps() {
        return mHasTurnOnGps;
    }

    public void setHasTurnOnGps(boolean hasTurnOnGps) {
        mHasTurnOnGps = hasTurnOnGps;
    }
}
