package dhbk.android.wifi2.fragments;


import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.osmdroid.api.IMapController;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import dhbk.android.wifi2.R;
import dhbk.android.wifi2.utils.Constant;

// TODO: 6/17/16 when clcik marker, auto connect to that wifi hotspot.
public class HistoryChildOsmMapFragment extends Fragment {
    private static final String TAG = HistoryChildOsmMapFragment.class.getSimpleName();

    public HistoryChildOsmMapFragment() {
        // Required empty public constructor
    }

    public static HistoryChildOsmMapFragment newInstance() {
        HistoryChildOsmMapFragment fragment = new HistoryChildOsmMapFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history_child_osm_map, container, false);
    }

//    set default map - zoom
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MapView mapView = (MapView) getActivity().findViewById(R.id.map_child); // map
        mapView.setTileSource(TileSourceFactory.MAPNIK);
        mapView.setMultiTouchControls(true);
        IMapController iMapController = mapView.getController(); // map controller
        iMapController.setZoom(Constant.ZOOM);
        GeoPoint startPoint = new GeoPoint(10.772241, 106.657676);
        iMapController.setCenter(startPoint);

        // load wifi hotspot on map
        // query database
        loadWifiHotspotFromDb();
    }

    private void loadWifiHotspotFromDb() {
        Fragment parentFrag = getParentFragment();
        if (parentFrag instanceof HistoryWithOsmMapFragment) {
            ((HistoryWithOsmMapFragment) parentFrag).getWifiHotspotFromDb();
        }
    }


    // a callback after getting wifi hotspot from db, we draw it on osm map
    public void showWifiHotspotOnMap(String ssid, String pass, double latitutude, double longitude) {
        // TODO: 6/17/2016 test this callback after getting from db
        Log.i(TAG, "showWifiHotspotOnMap: " + ssid);
        Log.i(TAG, "showWifiHotspotOnMap: " + pass);
        Log.i(TAG, "showWifiHotspotOnMap: " + latitutude);
        Log.i(TAG, "showWifiHotspotOnMap: " + longitude);
        Location wifiHpLoc = new Location("phong");
        wifiHpLoc.setLatitude(latitutude);
        wifiHpLoc.setLongitude(longitude);
        setMarkerAtLocation(wifiHpLoc, R.drawable.ic_wifi_map, ssid);
    }

    // phong - add marker at a location with instruction
    protected void setMarkerAtLocation(Location userCurrentLocation, int icon, String title) {
        if (userCurrentLocation != null) {
            GeoPoint userCurrentPoint = new GeoPoint(userCurrentLocation.getLatitude(), userCurrentLocation.getLongitude());
            MapView mapView = (MapView) getActivity().findViewById(R.id.map_child); // map
            IMapController mIMapController = mapView.getController();
            Marker hereMarker = new Marker(mapView);
            hereMarker.setPosition(userCurrentPoint);
            hereMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
            hereMarker.setIcon(ContextCompat.getDrawable(getContext(), icon));
            hereMarker.setTitle(title);
            mapView.getOverlays().add(hereMarker);
            mapView.invalidate();
        } else {
            Toast.makeText(getContext(), "Not determine your current location", Toast.LENGTH_SHORT).show();
        }
    }
}
