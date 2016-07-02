package dhbk.android.wifi2.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import org.osmdroid.api.IMapController;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by phongdth.ky on 6/29/2016.
 * contains all methods that uses in many class in my project
 */
public class HelpUtils {
    public static boolean isGpsHasTurnOn(Context context) {
        LocationManager locationManager = (LocationManager) context.getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            // not turn on gps
            return false;
        } else {
            // if already turn on gps
            return true;
        }
    }


    @Nullable
    public static Location getCurrentLocation(Context context) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return null;
        }

        LocationManager mLocationManager = (LocationManager) context.getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
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

    //get table db name from ssid and networkId
    @NonNull
    public static String getTableDbName(@NonNull String bssid, String addString) {
        String bssidNew = getStringAfterRemoveChar(bssid, ":");
        return addString + "_" + bssidNew;
    }

    @NonNull
    public static String getStringAfterRemoveChar(@NonNull String textBeforeRemove, @NonNull String charRemove) {
        return textBeforeRemove.replace(charRemove, "");
    }


    // translate the para string that was retrieved from db to data format
    public static Date date(String string) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.UK).parse(string);
        } catch (ParseException e) {
            throw new IllegalArgumentException(e);
        }
    }

    // set default to map
    public static void setDefaultSettingToMap(MapView mapView) {
        mapView.setTileSource(TileSourceFactory.MAPNIK);
        mapView.setMultiTouchControls(true);
        IMapController iMapController = mapView.getController(); // map controller
        iMapController.setZoom(Constant.ZOOM);
        GeoPoint startPoint = new GeoPoint(Constant.START_LATITUDE, Constant.STATE_LONGITUDE);
        iMapController.setCenter(startPoint);
    }

    //  - add marker at a location with instruction
    public static void setMarkerAtLocation(Context context, MapView mapView, Location userCurrentLocation, int icon, String title) {
        if (userCurrentLocation != null) {
            GeoPoint userCurrentPoint = new GeoPoint(userCurrentLocation.getLatitude(), userCurrentLocation.getLongitude());
            IMapController mIMapController = mapView.getController();
            Marker hereMarker = new Marker(mapView);
            hereMarker.setPosition(userCurrentPoint);
            hereMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
            hereMarker.setIcon(ContextCompat.getDrawable(context, icon));
            hereMarker.setTitle("SSID: " + title);
            mapView.getOverlays().add(hereMarker);
            mapView.invalidate();
        } else {
            Toast.makeText(context, "Not determine your current location", Toast.LENGTH_SHORT).show();
        }
    }

    public static void setDefaultMapSetting(MapView mapView) {
        mapView.setTileSource(TileSourceFactory.MAPNIK);
        mapView.setMultiTouchControls(true);
        IMapController iMapController = mapView.getController(); // map controller
        iMapController.setZoom(Constant.ZOOM);
        GeoPoint startPoint = new GeoPoint(Constant.START_LATITUDE, Constant.STATE_LONGITUDE);
        iMapController.setCenter(startPoint);
    }
}
