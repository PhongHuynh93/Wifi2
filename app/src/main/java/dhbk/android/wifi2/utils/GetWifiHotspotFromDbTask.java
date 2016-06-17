package dhbk.android.wifi2.utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.util.Log;

import dhbk.android.wifi2.interfaces.OnFragInteractionListener;

/**
 * Created by phongdth.ky on 6/17/2016.
 */
public class GetWifiHotspotFromDbTask extends AsyncTask<Void, Void, Cursor>{

    private static final String TAG = GetWifiHotspotFromDbTask.class.getSimpleName();
    private final SQLiteDatabase mDb;
    private final Context mActivityContext;

    public GetWifiHotspotFromDbTask(SQLiteDatabase db, Context activityContext) {
        mDb = db;
        mActivityContext = activityContext;
    }

    @Override
    protected Cursor doInBackground(Void... params) {
        Cursor cursor;
        try {
            // FIXME (check this cursor): 6/17/2016 change column to get ssid, pass, lat/long
            cursor = mDb.query (NetworkWifiDb.TABLE_WIFI_HOTSPOT,
                    new String[] {NetworkWifiDb.KEY_WIFI_HOTSPOT_ID, NetworkWifiDb.KEY_WIFI_HOTSPOT_SSID, NetworkWifiDb.KEY_WIFI_HOTSPOT_PASS, NetworkWifiDb.KEY_WIFI_HOTSPOT_LAT, NetworkWifiDb.KEY_WIFI_HOTSPOT_LONG},
                    NetworkWifiDb.KEY_WIFI_HOTSPOT_ISTURNONGPS + " = ?",
                    new String[] {Integer.toString(1)},
                    null, null,null);
        } catch (SQLiteException e) {
            return null;
        }
        return cursor;
    }

    @Override
    protected void onPostExecute(Cursor cursor) {
        super.onPostExecute(cursor);
        if (mActivityContext instanceof OnFragInteractionListener.OnMainFragInteractionListener) {
            Log.i(TAG, "onPostExecute: WifiHotspot" + cursor);
            ((OnFragInteractionListener.OnMainFragInteractionListener) mActivityContext).onReturnCursorWifiHotspot(cursor);
        }
    }
}
