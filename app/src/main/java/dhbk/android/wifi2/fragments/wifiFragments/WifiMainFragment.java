package dhbk.android.wifi2.fragments.wifiFragments;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dhbk.android.wifi2.R;
import dhbk.android.wifi2.fragments.historyFragments.BaseFragment;

/**
 * contain an layout for an empty wifi hotspot and a button for user to turn on wifi to continue scanning wifi hotspot again.
 * check if user has turned on wifi yet.
 */
public class WifiMainFragment extends BaseFragment {
    @BindView(R.id.retry_scan_wifi)
    Button mRetryScanWifiBtn;
    private boolean mHasTurnOnGps;

    public WifiMainFragment() {
    }

    public static WifiMainFragment newInstance() {
        return new WifiMainFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wifi, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    // check if only when app start and restart app
    @Override
    public void onStart() {
        super.onStart();
        hasUserTurnOnWifi();
    }

    // if user has turn
    private void hasUserTurnOnWifi() {
        // check wifi enabled or not
        WifiManager wifi = (WifiManager) getActivity().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (wifi.isWifiEnabled()) {
            // : 6/13/2016 call method to show wifi access points around user location.
            Fragment fragment = getParentFragment();
            if (fragment instanceof WifiPresenterFragment) {
                ((WifiPresenterFragment)fragment).showScanWifiAround();
            }
        } else {
            // user hasn't enabled wifi.
            // : 6/13/2016 call a dialog to help user turn on wifi
            Fragment fragment = getParentFragment();
            if (fragment instanceof WifiPresenterFragment) {
                ((WifiPresenterFragment)fragment).showDialogToTurnOnWifi();
            }
        }
    }

    // if click retry button, turn on wifi and scan wifi hotspot.
    @OnClick(R.id.retry_scan_wifi)
    public void onClick() {
        Fragment parentFragment = getParentFragment();
        if (parentFragment instanceof WifiPresenterFragment) {
            ((WifiPresenterFragment)parentFragment).turnOnWifi();
        }
    }
}
