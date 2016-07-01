package dhbk.android.wifi2.fragments.mapFragments;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dhbk.android.wifi2.R;
import dhbk.android.wifi2.interfaces.OnFragInteractionListener;
import dhbk.android.wifi2.models.WifiHotsPotModel;
import dhbk.android.wifi2.utils.Constant;
import dhbk.android.wifi2.utils.db.NetworkDb;
import dhbk.android.wifi2.utils.db.NetworkWifiDb;


/*
. contain background tasks such as getting datas from db
. not effected by config change
. add child fragment by control ChildFragmentManger
 */
public class MapPresenterFragment extends Fragment implements
        OnFragInteractionListener.OnMapFragInteractionListerer{

    public MapPresenterFragment() {
    }

    public static MapPresenterFragment newInstance() {
        return new MapPresenterFragment();
    }

    // not effected by config change
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

//   add child fragment to  show map
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MapFragment mapFragment = (MapFragment) getChildFragmentManager().findFragmentByTag(Constant.TAG_CHILD_OSM_MAP_FRAGMENT);
        if (mapFragment == null) {
            getChildFragmentManager()
                    .beginTransaction()
                    .add(R.id.map_parent, MapFragment.newInstance(), Constant.TAG_CHILD_OSM_MAP_FRAGMENT)
                    .commit();
        }
    }

    // get wifi hotspot from db
    public void getWifiHotspotFromDb() {
        NetworkDb networkDb = NetworkDb.getInstance(getActivity());
        networkDb.getWifiHotspotLocation(this);
    }

    // result wifi hotspot from db
    public void onReturnWifiHotspot(Cursor cursor) {
        Fragment childFragment = getChildFragmentManager().findFragmentByTag(Constant.TAG_CHILD_OSM_MAP_FRAGMENT);
        if (childFragment instanceof MapFragment)  {
            if (cursor.moveToFirst()) {
                // loop all row in cursor
                do {
                    String ssid = "";
                    String pass = "";
                    double latitude = 0.0;
                    double longitude = 0.0;
                    for (int i = 0; i < NetworkWifiDb.COLUMN_TABLE_WIFI_HOTSPOT_LOCATION.length; i++) {
                        if (NetworkWifiDb.COLUMN_TABLE_WIFI_HOTSPOT_LOCATION[i].equals(NetworkWifiDb.KEY_WIFI_HOTSPOT_SSID)) {
                            ssid = cursor.getString(i);
                        } else if ((NetworkWifiDb.COLUMN_TABLE_WIFI_HOTSPOT_LOCATION[i].equals(NetworkWifiDb.KEY_WIFI_HOTSPOT_PASS))) {
                            pass = cursor.getString(i);
                        } else if ((NetworkWifiDb.COLUMN_TABLE_WIFI_HOTSPOT_LOCATION[i].equals(NetworkWifiDb.KEY_WIFI_HOTSPOT_LAT))) {
                            latitude = cursor.getDouble(i);
                        } else if ((NetworkWifiDb.COLUMN_TABLE_WIFI_HOTSPOT_LOCATION[i].equals(NetworkWifiDb.KEY_WIFI_HOTSPOT_LONG))) {
                            longitude = cursor.getDouble(i);
                        }
                    }
                    WifiHotsPotModel wifiHotsPotModel = new WifiHotsPotModel(ssid, pass, latitude, longitude);
                    ((MapFragment) childFragment).showWifiHotspotOnMap(wifiHotsPotModel);
                } while (cursor.moveToNext());
            }
        }

        cursor.close();
    }


    // TODO: 7/1/16 getting cursor a set to map
    @Override
    public void onGetWifiLocationCursor(Cursor cursor) {

    }
}
