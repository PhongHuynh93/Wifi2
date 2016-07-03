package dhbk.android.wifi2.fragments.mapFragments;


import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.osmdroid.views.MapView;

import butterknife.BindView;
import butterknife.ButterKnife;
import dhbk.android.wifi2.R;
import dhbk.android.wifi2.models.WifiLocationModel;
import dhbk.android.wifi2.utils.HelpUtils;

/**
 * show map
 * show markers corresponding to wifi hotspot location.
 */
public class MapFragment extends Fragment {

    @BindView(R.id.map_child)
    MapView mMapChild;

    public MapFragment() {
    }

    public static MapFragment newInstance() {
        return new MapFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history_child_osm_map, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    //    set default map - zoom and load wifi hotspot locations.
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        HelpUtils.setDefaultSettingToMap(mMapChild);
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
    public void showWifiHotspotOnMap(@NonNull WifiLocationModel wifiLocationModel) {
        Location wifiHpLoc = new Location("phong");
        wifiHpLoc.setLatitude(wifiLocationModel.getLatitude());
        wifiHpLoc.setLongitude(wifiLocationModel.getLongitude());
        HelpUtils.setMarkerAtLocation(getContext(), mMapChild, wifiHpLoc, R.drawable.ic_wifi_map, wifiLocationModel.getSsid());
    }
}
