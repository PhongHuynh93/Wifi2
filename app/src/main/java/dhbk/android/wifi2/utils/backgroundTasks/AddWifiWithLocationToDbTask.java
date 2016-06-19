package dhbk.android.wifi2.utils.backgroundTasks;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;

import dhbk.android.wifi2.models.WifiHotsPotModel;
import dhbk.android.wifi2.utils.db.NetworkWifiDb;

/**
 * Created by phongdth.ky on 6/15/2016.
 */
public class AddWifiWithLocationToDbTask extends AsyncTask<WifiHotsPotModel, Void, Boolean> {
    private static final String TAG = AddWifiWithLocationToDbTask.class.getSimpleName();
    private final SQLiteDatabase mDb;

    public AddWifiWithLocationToDbTask(SQLiteDatabase db) {
        this.mDb = db;
    }
    @Override
    protected Boolean doInBackground(WifiHotsPotModel... params) {
        if (params.length != 1) {
            return false;
        }
        WifiHotsPotModel wifiHotsPotModel = params[0];
        String ssid = wifiHotsPotModel.getNetworkSSID();
        String pass = wifiHotsPotModel.getNetworkPass();
        double latitude = wifiHotsPotModel.getLatitude();
        double longitude = wifiHotsPotModel.getLongitude();
        int isTurnOnGps = wifiHotsPotModel.isTurnOnGps();

        // : 6/17/2016 test the see what field in wifihotspotmodel

        mDb.beginTransaction();
        try {
            ContentValues wifiHotspotValues = new ContentValues();
            wifiHotspotValues.put(NetworkWifiDb.KEY_WIFI_HOTSPOT_SSID, ssid);
            wifiHotspotValues.put(NetworkWifiDb.KEY_WIFI_HOTSPOT_PASS, pass);
            wifiHotspotValues.put(NetworkWifiDb.KEY_WIFI_HOTSPOT_LAT, latitude);
            wifiHotspotValues.put(NetworkWifiDb.KEY_WIFI_HOTSPOT_LONG, longitude);
            wifiHotspotValues.put(NetworkWifiDb.KEY_WIFI_HOTSPOT_ISTURNONGPS, isTurnOnGps);

            mDb.insertOrThrow(NetworkWifiDb.TABLE_WIFI_HOTSPOT, null, wifiHotspotValues);
            mDb.setTransactionSuccessful();
        } catch (SQLiteException e) {
            return false;
        } finally {
            mDb.endTransaction();
        }
        return true;
    }
}
