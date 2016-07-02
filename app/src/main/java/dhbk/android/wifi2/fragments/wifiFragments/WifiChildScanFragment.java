package dhbk.android.wifi2.fragments.wifiFragments;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import dhbk.android.wifi2.R;
import dhbk.android.wifi2.adapters.wifiAdapters.ScanWifiRecyclerviewAdapter;
import dhbk.android.wifi2.fragments.historyFragments.BaseFragment;
import dhbk.android.wifi2.models.WifiLocationModel;
import dhbk.android.wifi2.utils.ItemClickSupport;

/**
 * show all of wifi around yet
 */
public class WifiChildScanFragment extends BaseFragment {
    private static final String TAG = WifiChildScanFragment.class.getSimpleName();
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.list_scan_wifi)
    RecyclerView mListWifiRecyclerView;
    @BindView(R.id.wifi_scan_coordinator)
    CoordinatorLayout mWifiScanCoordinator;

    // contains list of wifi hotspot
    ArrayList<WifiLocationModel> mWifiScanWifiModels = new ArrayList<>();

    // presenter
    private WifiPresenterFragment mWifiPresenterFragment;

    public WifiChildScanFragment() {
    }

    public static WifiChildScanFragment newInstance() {
        WifiChildScanFragment fragment = new WifiChildScanFragment();
        return fragment;
    }

    // start scan wifi hotspot
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // get the Presenter
        Fragment parentFragment = getParentFragment();
        if (parentFragment instanceof WifiPresenterFragment) {
            mWifiPresenterFragment = (WifiPresenterFragment) parentFragment;
        }

        // start scan wifi hotspot
        if (mWifiPresenterFragment != null) {
            mWifiPresenterFragment.startScanWifiHotspot();
        }
    }

    // set presenter to null
    @Override
    public void onDetach() {
        super.onDetach();
        mWifiPresenterFragment = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wifi_child_scan, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        declareToolbar(mToolbar);
        mToolbar.setTitle("Wifi Hotspot");

        if (mWifiPresenterFragment != null) {
            mWifiPresenterFragment.checkGpsHasTurnOn();
        }

        // add a placeholder "empty" for adapter.
        mWifiScanWifiModels.add(new WifiLocationModel("empty", "empty", "empty"));
        ScanWifiRecyclerviewAdapter scanWifiRecyclerviewAdapter = new ScanWifiRecyclerviewAdapter(mWifiScanWifiModels);
        declareRcvAndGetDataFromDb(mListWifiRecyclerView, scanWifiRecyclerviewAdapter, "");
//        mListWifiRecyclerView.setAdapter(scanWifiRecyclerviewAdapter);
//        mListWifiRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        mListWifiRecyclerView.setHasFixedSize(true);

        // when click an item in rcv, show a dialog to help user input password.
        ItemClickSupport.addTo(mListWifiRecyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                //  (check position) get ssid, position of item on recyclerview
                if (mWifiPresenterFragment != null) {
                    // get ssid at position, so after user has input pass in a dialog, we can authen at that position
                    TextView ssidTv = (TextView) v.findViewById(R.id.ssid);
                    String ssid = ssidTv.getText().toString();
                    mWifiPresenterFragment.showFillInPassWifiDialog(ssid, position);
                }
            }
        });



    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.wifi_menu, menu);
    }

    // listen check icon
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.turn_on_loc) {
            // : 6/18/16 turn on location if not
            if (mWifiPresenterFragment != null) {
                mWifiPresenterFragment.checkGpsHasTurnOn();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    // register broadcast to get wifi scans
    @Override
    public void onResume() {
        super.onResume();
        if (mWifiPresenterFragment != null) {
            mWifiPresenterFragment.registerBroadcastToScanWifiHotspot();
        }
    }

    // unregister
    @Override
    public void onPause() {
        super.onPause();
        if (mWifiPresenterFragment != null) {
            mWifiPresenterFragment.unRegisterBroadcastToScanWifiHotspot();
        }
    }


    // show a snack bar and a button action
    public void showSnackBar() {
        Snackbar.make(mWifiScanCoordinator, getActivity().getResources().getString(R.string.let_user_turn_on_location), Snackbar.LENGTH_LONG)
                .setAction(getActivity().getResources().getString(R.string.turn_on_location), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // call a dialog to turn help user turn on lcaotion
                        if (mWifiPresenterFragment != null) {
                            mWifiPresenterFragment.showGpsDialogToTurnOn();
                        }
                    }
                })
                .show();
    }

    // a callback from presenter, get wifi scan data and show to recyclerview
    public void addWifiHotSpotToRcv(ArrayList<WifiLocationModel> wifis) {
        ScanWifiRecyclerviewAdapter scanWifiRecyclerviewAdapter = (ScanWifiRecyclerviewAdapter) mListWifiRecyclerView.getAdapter();
        mWifiScanWifiModels.clear();
        mWifiScanWifiModels.addAll(wifis);
        scanWifiRecyclerviewAdapter.notifyItemRangeChanged(0, mWifiScanWifiModels.size());
    }


    // unregister broadcast success wifi, get location and save to datbase
    public void saveWifiHotspotToDb(String ssid, String pass, String encryption, String bssid) {
        // : 6/16/16 get lat long
        boolean isTurnOnGps = false;

        // check again before save to db, if users has turned on GPS before.
        if (mWifiPresenterFragment != null) {
            mWifiPresenterFragment.checkGpsHasTurnOn();
            isTurnOnGps = mWifiPresenterFragment.isHasTurnOnGps();
        }

        if (isTurnOnGps) {
            Location currentLocation = null;

            if (mWifiPresenterFragment != null) {
                currentLocation = mWifiPresenterFragment.getCurrentLocation();
            }

            if (currentLocation != null) {
                //  save to datbase
                if (mWifiPresenterFragment != null) {
                    mWifiPresenterFragment.saveWifiHotspotToDb(ssid, pass, currentLocation.getLatitude(), currentLocation.getLongitude(), isTurnOnGps);
                }
            }

        } // if user hasn't turn on Location
        else {
            if (mWifiPresenterFragment != null) {
                mWifiPresenterFragment.saveWifiHotspotToDb(ssid, pass, 0, 0, isTurnOnGps);
            }
        }

    }



    // get ssid and pass depond on position
    public WifiLocationModel getWifiSsidAndPass(int position) {
        ScanWifiRecyclerviewAdapter scanWifiRecyclerviewAdapter = (ScanWifiRecyclerviewAdapter) mListWifiRecyclerView.getAdapter();
        return scanWifiRecyclerviewAdapter.getWifiModelAtPosition(position);
    }

}
