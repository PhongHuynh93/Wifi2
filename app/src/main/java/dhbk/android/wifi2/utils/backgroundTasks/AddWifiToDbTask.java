package dhbk.android.wifi2.utils.backgroundTasks;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.util.Log;

import dhbk.android.wifi2.models.WifiModel;
import dhbk.android.wifi2.utils.db.NetworkWifiDb;

/**
 * Created by phongdth.ky on 6/15/2016.
 */
public class AddWifiToDbTask extends AsyncTask<String, Void, Boolean> {
    private static final String TAG  = AddWifiToDbTask.class.getSimpleName();
    private final SQLiteDatabase mDb;

    public AddWifiToDbTask(SQLiteDatabase db) {
        this.mDb = db;
    }
    @Override
    protected Boolean doInBackground(String... params) {
        if (params.length != WifiModel.LENGTH) {
            Log.e(TAG, "doInBackground: not enough params");
            return false;

        }
        String state = params[0];
        String ssid = params[1];
        String date = params[2];

        mDb.beginTransaction();
        try {
            // TODO: 6/25/16 change this
            ContentValues wifiHotspotValues = new ContentValues();
            wifiHotspotValues.put(NetworkWifiDb.KEY_WIFI_STATE, state);
            wifiHotspotValues.put(NetworkWifiDb.KEY_WIFI_SSID, ssid);
            wifiHotspotValues.put(NetworkWifiDb.KEY_WIFI_DATE, date);

            mDb.insertOrThrow(NetworkWifiDb.TABLE_WIFI, null, wifiHotspotValues);
            mDb.setTransactionSuccessful();

        } catch (SQLiteException e) {
            return false;
        } finally {
            mDb.endTransaction();
        }
        return true;
    }
}
