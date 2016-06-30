package dhbk.android.wifi2.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;

import java.util.List;

/**
 * Created by phongdth.ky on 6/29/2016.
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
    public static String getTableDbName(@NonNull String ssid, int networkId, String addString) {
        return ssid + "_" + networkId + "_" + addString;
    }

    @NonNull
    public static String getStringAfterRemoveChar(@NonNull String textBeforeRemove, @NonNull String charRemove) {
        return textBeforeRemove.replace(charRemove, "");
    }
}
