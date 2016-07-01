package dhbk.android.wifi2.fragments.mapFragments;


import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.osmdroid.api.IMapController;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import dhbk.android.wifi2.R;
import dhbk.android.wifi2.models.WifiHotsPotModel;
import dhbk.android.wifi2.utils.HelpUtils;

/**
 * show map
 * show markers corresponding to wifi hotspot location.
 */
public class MapFragment extends Fragment {

    public MapFragment() {
    }

    public static MapFragment newInstance() {
        return new MapFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_history_child_osm_map, container, false);
    }

//    set default map - zoom and load wifi hotspot locations.
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MapView mapView = (MapView) getActivity().findViewById(R.id.map_child);
        HelpUtils.setDefaultSettingToMap(mapView);
        loadWifiHotspotFromDb();
    }

    // call presenter to query database
    private void loadWifiHotspotFromDb() {
        Fragment parentFrag = getParentFragment();
        if (parentFrag instanceof MapPresenterFragment) {
            ((MapPresenterFragment) parentFrag).getWifiHotspotFromDb();
        }
    }


    // a callback after getting wifi hotspot from db, we draw it on osm map
    public void showWifiHotspotOnMap(@NonNull WifiHotsPotModel wifiHotsPotModel) {
        Location wifiHpLoc = new Location("phong");
        wifiHpLoc.setLatitude(wifiHotsPotModel.getLatitude());
        wifiHpLoc.setLongitude(wifiHotsPotModel.getLongitude());
        setMarkerAtLocation(wifiHpLoc, R.drawable.ic_wifi_map, wifiHotsPotModel.getNetworkSSID());
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
            hereMarker.setTitle("SSID: " + title);
            mapView.getOverlays().add(hereMarker);
            mapView.invalidate();
        } else {
            Toast.makeText(getContext(), "Not determine your current location", Toast.LENGTH_SHORT).show();
        }
    }
}
