package dhbk.android.wifi2.fragments;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import dhbk.android.wifi2.R;
import dhbk.android.wifi2.adapters.ScanWifiRecyclerviewAdapter;
import dhbk.android.wifi2.models.WifiModel;
import dhbk.android.wifi2.utils.Constant;
import dhbk.android.wifi2.utils.ItemClickSupport;

// TODO: 6/16/16 add animation when wait for wifi scan
// show all of wifi around yet
public class WifiChildScanFragment extends Fragment {
    private static final String TAG = WifiChildScanFragment.class.getSimpleName();
    private static final String ARG_GPS = "turn_gps";
    private WifiScanReceiver mWifiReciever;
    private WifiManager mWifi;
    private RecyclerView mListWifiRecyclerView;
    ArrayList<WifiModel> wifiModels;
    private boolean mHasTurnOnGps; // state of turn of GPS

    private boolean firstConnect = true;
    private boolean firstDisconnect = true;
    private Object mCurrentLocation;
    private String networkSSID;
    private String networkPass;

    public WifiChildScanFragment() {
    }

    public static WifiChildScanFragment newInstance(boolean hasGpsTurnOn) {
        WifiChildScanFragment fragment = new WifiChildScanFragment();
        Bundle args = new Bundle();
        args.putBoolean(ARG_GPS, hasGpsTurnOn);
        fragment.setArguments(args);
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mHasTurnOnGps = getArguments().getBoolean(ARG_GPS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_wifi_child_scan, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);
        toolbar.setTitle("Wifi Hotspot");

        // : 6/16/2016 check to see GPS is enabled? if not, show a dialog
        // add a snackbar to let the user turn on location
        LocationManager locationManager = (LocationManager) getActivity().getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Snackbar.make(view.findViewById(R.id.wifi_scan_coordinator), getActivity().getResources().getString(R.string.let_user_turn_on_location), Snackbar.LENGTH_LONG)
                    .setAction(getActivity().getResources().getString(R.string.turn_on_location), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Fragment fragment = getParentFragment();
                            if (fragment instanceof WifiFragment) {
                                ((WifiFragment) fragment).showGpsDialogToTurnOn();
                            }
                        }
                    })
                    .show();
        }

        // add a placeholder for adapter in main thread
        mListWifiRecyclerView = (RecyclerView) view.findViewById(R.id.list_scan_wifi);
        wifiModels = new ArrayList<>();
        WifiModel emptyWifi = new WifiModel("empty", "empty");
        wifiModels.add(emptyWifi);
        final ScanWifiRecyclerviewAdapter scanWifiRecyclerviewAdapter = new ScanWifiRecyclerviewAdapter(wifiModels);
        mListWifiRecyclerView.setAdapter(scanWifiRecyclerviewAdapter);
        mListWifiRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        mListWifiRecyclerView.setHasFixedSize(true);

        // click listener to recyclerview
        ItemClickSupport.addTo(mListWifiRecyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                //  (check position) get ssid, position of item on recyclerview
                Fragment parentFragment = getParentFragment();
                if (parentFragment instanceof WifiFragment) {
                    // get ssid at position
                    TextView ssidTv = (TextView) v.findViewById(R.id.ssid);
                    String ssid = ssidTv.getText().toString();
                    ((WifiFragment) parentFragment).onShowFillInPassWifiDialog(ssid, position);
                }
            }
        });
    }

    // TODO: 6/16/16
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

    // show in recyclerview
    private void addWifiHotSpotToRcv(ArrayList<WifiModel> wifis) {
        ScanWifiRecyclerviewAdapter scanWifiRecyclerviewAdapter = (ScanWifiRecyclerviewAdapter) mListWifiRecyclerView.getAdapter();
        wifiModels.clear();
        wifiModels.addAll(wifis);
        scanWifiRecyclerviewAdapter.notifyItemRangeChanged(0, wifiModels.size());
    }

    // : 6/16/2016 test this (forget old wifi AP and input pass) depend on position, get ssid and encryption, and then authen with pass
    public void authenWifiWithPass(String pass, int position) {
        ScanWifiRecyclerviewAdapter scanWifiRecyclerviewAdapter = (ScanWifiRecyclerviewAdapter) mListWifiRecyclerView.getAdapter();
        WifiModel wifiModel = scanWifiRecyclerviewAdapter.getWifiModelAtPosition(position);
        networkSSID = wifiModel.getSsid();
        String encryption = wifiModel.getEncryption();
        networkPass = pass;

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

        WifiManager wifiManager = (WifiManager) getActivity().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        int networkId = wifiManager.addNetwork(conf);
        if (networkId != -1) {
            wifiManager.enableNetwork(networkId, true);
            // Use this to permanently save this network
            // Otherwise, it will disappear after a reboot
            wifiManager.saveConfiguration();
        }

        // TODO: 6/16/16 fix broadcast receiver to recieve wifi connect success
        WifiGetConnectionEstablished WifiGetConnectionEstablished = new WifiGetConnectionEstablished();
        getActivity().getApplicationContext().registerReceiver(WifiGetConnectionEstablished, new IntentFilter(WifiManager.NETWORK_STATE_CHANGED_ACTION));
    }

    // get location and save to datbase
    private void saveWifiHotspotToDb(boolean hasTurnOnGps) {
        // : 6/16/16 get lat long
        if (mHasTurnOnGps) {
            Location currentLocation = getCurrentLocation();
            if (currentLocation != null) {
                Double latitude = currentLocation.getLatitude();
                Double longitude = currentLocation.getLongitude();
                Log.i(TAG, "saveWifiHotspotToDb onReceive: " + latitude);
                Log.i(TAG, "saveWifiHotspotToDb onReceive: " + longitude);
                // : 6/16/16 save to datbase
                Fragment parentFragment = getParentFragment();
                if (parentFragment instanceof WifiFragment) {
                    ((WifiFragment) parentFragment).saveWifiHotspotToDb(networkSSID, networkPass, currentLocation.getLatitude(), currentLocation.getLongitude());
                }
            }

        } else {
            Fragment parentFragment = getParentFragment();
            if (parentFragment instanceof WifiFragment) {
                ((WifiFragment) parentFragment).saveWifiHotspotToDb(networkSSID, networkPass, 0, 0);
            }
        }

    }


    @Nullable
    private Location getCurrentLocation() {
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

    public void setHasTurnOnGps(boolean hasTurnOnGps) {
        mHasTurnOnGps = hasTurnOnGps;
    }


    // get result when scan wifi hotspot around
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
                Log.i(TAG, "onReceive: capabilities: " + capabilities);
                if (capabilities.contains(Constant.WIFI_WPA2)) {
                    // We know there is WPA2 encryption
                    encryption = Constant.WIFI_WPA2;

                } else if (capabilities.contains(Constant.WIFI_WPA)) {
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

            // : 6/14/2016 populate vào recyclerview
            addWifiHotSpotToRcv(wifis);
        }
    }

    // : 6/16/2016 receive when a connection to the supplicant has been established
    public class WifiGetConnectionEstablished extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (action.equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)) {
                NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
                boolean connected = info.isConnected();

                if (connected) {
                    if (firstConnect) {
                        firstConnect = false;
                        // save to datbase
                        saveWifiHotspotToDb(mHasTurnOnGps);
                    }
                } else {
                    firstConnect = true;
                }
            }
            // TODO: 6/16/16 unregister it
        }
    }


}
