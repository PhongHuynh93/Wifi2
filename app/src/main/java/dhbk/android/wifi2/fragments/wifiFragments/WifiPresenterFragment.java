package dhbk.android.wifi2.fragments.wifiFragments;


import android.Manifest;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import dhbk.android.wifi2.R;
import dhbk.android.wifi2.fragments.historyFragments.BaseFragment;
import dhbk.android.wifi2.interfaces.OnFragInteractionListener;
import dhbk.android.wifi2.models.WifiHotsPotModel;
import dhbk.android.wifi2.models.WifiScanWifiModel;
import dhbk.android.wifi2.utils.Constant;
import dhbk.android.wifi2.utils.db.NetworkDb;

/**
 * presenter for wifi contains all background tasks related to connect to db.
 * contains business logics.
 * controls the add/replace child fragments.
 */
public class WifiPresenterFragment extends BaseFragment implements
        OnFragInteractionListener.OnWifiScanFragInteractionListener {
    // state of GPS
    private boolean mHasTurnOnGps = false;
    private ScanWifiBroadcastReceiver mWifiReciever;
    private ListenStateChangeBroadcastReceiver mListenStateChangeBroadcastReceiver;

    public WifiPresenterFragment() {
    }

    public static WifiPresenterFragment newInstance() {
        return new WifiPresenterFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wifi_presenter, container, false);
        return view;
    }

    // add a wifi main fragment contains an empty wifi hotspot layout
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getChildFragmentManager()
                .beginTransaction()
                .add(R.id.container_wifi_presenter, WifiMainFragment.newInstance(), Constant.TAG_WIFI_FRAGMENT)
                .commit();

        // declare var to register to broadcast receiver
        mWifiReciever = new ScanWifiBroadcastReceiver(this);
    }


    // replace with fragment that shows wifi hotspot around, but not add to backstack, so that new fragment, when press back, we go back to main fragment
    public void showScanWifiAround() {
        getChildFragmentManager()
                .beginTransaction()
                .replace(R.id.wifi_frag_container, WifiChildScanFragment.newInstance(), Constant.TAG_CHILD_SCAN_WIFI_FRAGMENT)
                .commit();
    }

    // show a dialog to turn on wifi
    public void showDialogToTurnOnWifi() {
        TurnOnWifiDialogFragment turnOnWifiDialogFragment = new TurnOnWifiDialogFragment();
        turnOnWifiDialogFragment.show(getChildFragmentManager(), Constant.TAG_CHILD_WIFI_DIALOG_FRAGMENT);
    }

    // : 6/16/2016 show a gps dialog
    public void showGpsDialogToTurnOn() {
        GpsChildTurnOnDialogFragment gpsChildTurnOnDialogFragment = new GpsChildTurnOnDialogFragment();
        gpsChildTurnOnDialogFragment.show(getChildFragmentManager(), Constant.TAG_CHILD_GPS_FRAGMENT);
    }

    // a callback from GpsChildTurnOnDialogFragment, the para is the var which determine that the user has turn on gps or not
    public void onHasUserTurnOnGps(boolean b) {
        mHasTurnOnGps = b;
    }

    // turn on wifi and replace with wifiscan fragment
    public void turnOnWifi() {
        WifiManager wifi = (WifiManager) getActivity().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        wifi.setWifiEnabled(true);
        showScanWifiAround();
    }


    //  call this method whenever app need to a gps location
    public void checkGpsHasTurnOn() {
        LocationManager locationManager = (LocationManager) getActivity().getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            // if not turn on gps, call wifiChildScanFragment to make a snackbar
            Fragment childFrag = getChildFragmentManager().findFragmentByTag(Constant.TAG_CHILD_SCAN_WIFI_FRAGMENT);
            if (childFrag instanceof WifiChildScanFragment) {

                ((WifiChildScanFragment) childFrag).showSnackBar();
            }
        } else {
            // if already turn on gps, set var
            mHasTurnOnGps = true;
        }
    }

    public boolean isHasTurnOnGps() {
        return mHasTurnOnGps;
    }

    // show a dialog to help user input pass to authen a wifi hotspot
    public void showFillInPassWifiDialog(String ssid, int position) {
        InputPassWifiDialogFragment inputPassWifiDialogFragment = InputPassWifiDialogFragment.newInstance(ssid, position);
        // we create a custome dialog, so dont need a title
        inputPassWifiDialogFragment.setStyle(DialogFragment.STYLE_NO_TITLE, 0);
        inputPassWifiDialogFragment.show(getChildFragmentManager(), Constant.TAG_CHILD_CONNECT_WIFI_FRAGMENT);
    }

    public void startScanWifiHotspot() {
        WifiManager wifiManager = (WifiManager) getContext().getSystemService(Context.WIFI_SERVICE);
        wifiManager.startScan();
    }

    public void registerBroadcastToScanWifiHotspot() {
        getContext().registerReceiver(mWifiReciever, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
    }

    public void unRegisterBroadcastToScanWifiHotspot() {
        getContext().unregisterReceiver(mWifiReciever);
    }

    // a callback from WifiScanRecever with wifi info
    @Override
    public void onGetDataAfterScanWifi(ArrayList<WifiScanWifiModel> wifiScanList) {
        Fragment childFragment = getChildFragmentManager().findFragmentByTag(Constant.TAG_CHILD_SCAN_WIFI_FRAGMENT);
        if (childFragment instanceof WifiChildScanFragment) {
            ((WifiChildScanFragment) childFragment).addWifiHotSpotToRcv(wifiScanList);
        }
    }


    // a callback from InputPassWifiDialogFragment, user input a pass, so we authen this
    public void onAuthenWifiHotspot(String networkPass, int position) {
        Fragment childFragment = getChildFragmentManager().findFragmentByTag(Constant.TAG_CHILD_SCAN_WIFI_FRAGMENT);
        if (childFragment instanceof WifiChildScanFragment) {
            WifiScanWifiModel wifiScanWifiModel = ((WifiChildScanFragment) childFragment).getWifiSsidAndPass(position);

            String networkSSID = wifiScanWifiModel.getSsid();
            String encryption = wifiScanWifiModel.getEncryption();

            // Add a new network description to the set of configured networks.
            WifiConfiguration conf = new WifiConfiguration();
            conf.SSID = "\"" + networkSSID + "\"";

            if (encryption.equals(Constant.WIFI_WEP)) {
                conf.wepKeys[0] = "\"" + networkPass + "\"";
                conf.wepTxKeyIndex = 0;
                conf.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
                conf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
            } else if (encryption.equals(Constant.WIFI_WPA)) {
                conf.preSharedKey = "\"" + networkPass + "\"";
            } else if (encryption.equals(Constant.WIFI_OPEN)) {
                conf.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
            } else if (encryption.equals(Constant.WIFI_WPA2)) {
                conf.preSharedKey = "\"" + networkPass + "\"";
            }

            WifiManager wifiManager = (WifiManager) getContext().getSystemService(Context.WIFI_SERVICE);
            int networkId = wifiManager.addNetwork(conf);
            if (networkId != -1) {
                wifiManager.enableNetwork(networkId, true);
                // Use this to permanently save this network
                // Otherwise, it will disappear after a reboot
                wifiManager.saveConfiguration();
            }

            // if connect to that wifi hotspot success, we will have state change in wifi.
            // so to listen for state change in wifi, call a broadcast recever
            mListenStateChangeBroadcastReceiver = new ListenStateChangeBroadcastReceiver(this, networkSSID, networkPass);
            getContext().registerReceiver(mListenStateChangeBroadcastReceiver, new IntentFilter(WifiManager.NETWORK_STATE_CHANGED_ACTION));
        }
    }


    // a callback from broadcast receiver, store wifiHotspot to DB.
    @Override
    public void onAllowToSaveWifiHotspotToDb(String ssid, String pass) {
        // register broadcast
        getContext().unregisterReceiver(mListenStateChangeBroadcastReceiver);
        // call WifiChildScanFragment to save to db
        Fragment childFragment = getChildFragmentManager().findFragmentByTag(Constant.TAG_CHILD_SCAN_WIFI_FRAGMENT);
        if (childFragment instanceof WifiChildScanFragment) {
            ((WifiChildScanFragment) childFragment).saveWifiHotspotToDb(ssid, pass);
        }
    }


    // get the current location of a user
    @Nullable
    public Location getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return null;
        }
        LocationManager mLocationManager = (LocationManager) getActivity().getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        List<String> providers = mLocationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            Location l = mLocationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                // Found best last known location: %s", l);
                bestLocation = l;
            }
        }
        return bestLocation;
    }


    // TODO: 7/1/2016 change to use new table
    // save wifi states to database
    // isTurnOnGps = true => has location
    public void saveWifiHotspotToDb(String networkSSID, String networkPass, double latitude, double longitude, boolean isTurnOnGps) {
        int isTurnGpsInt;
        if (isTurnOnGps) {
            isTurnGpsInt = 1;
        } else {
            isTurnGpsInt = 0;
        }

        WifiHotsPotModel wifiHotsPotModel = new WifiHotsPotModel(networkSSID, networkPass, latitude, longitude, isTurnGpsInt);
        NetworkDb networkDb = NetworkDb.getInstance(getActivity());
        networkDb.addWifiHotspotWithLocation(wifiHotsPotModel);
    }
}
