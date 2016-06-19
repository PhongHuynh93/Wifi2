package dhbk.android.wifi2.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dhbk.android.wifi2.R;
import dhbk.android.wifi2.utils.Constant;
import dhbk.android.wifi2.utils.NetworkDb;

public class HistoryWithOsmMapFragment extends Fragment {

    public HistoryWithOsmMapFragment() {
    }

    public static HistoryWithOsmMapFragment newInstance() {
        HistoryWithOsmMapFragment fragment = new HistoryWithOsmMapFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history_with_osm_map, container, false);
    }

//     show map
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        HistoryChildOsmMapFragment historyChildOsmMapFragment = (HistoryChildOsmMapFragment) getChildFragmentManager().findFragmentByTag(Constant.TAG_CHILD_OSM_MAP_FRAGMENT);
        if (historyChildOsmMapFragment == null) {
            getChildFragmentManager()
                    .beginTransaction()
                    .add(R.id.map_parent, HistoryChildOsmMapFragment.newInstance(), Constant.TAG_CHILD_OSM_MAP_FRAGMENT)
                    .commit();
        }
    }

    // get wifi hotspot by async from db
    public void getWifiHotspotFromDb() {
        NetworkDb networkDb = NetworkDb.getInstance(getActivity());
        networkDb.getWifiHotspot(getActivity());
    }

    // result wifi hotspot from db
    public void onReturnWifiHotspot(Cursor cursor) {
        Fragment childFragment = getChildFragmentManager().findFragmentByTag(Constant.TAG_CHILD_OSM_MAP_FRAGMENT);
        if (childFragment instanceof HistoryChildOsmMapFragment)  {
            if (cursor.moveToFirst()) {
                do {
                    String ssid = cursor.getString(1); // cot 0 la id
                    String pass = cursor.getString(2);
                    double latitutude = cursor.getDouble(3);
                    double longitude = cursor.getDouble(4);

                    // show on map
                    ((HistoryChildOsmMapFragment) childFragment).showWifiHotspotOnMap(ssid, pass, latitutude, longitude);
                } while (cursor.moveToNext());
            }
        }

        cursor.close();
    }
}
