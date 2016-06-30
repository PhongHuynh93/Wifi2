package dhbk.android.wifi2.utils.backgroundTasks;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.util.Log;

import dhbk.android.wifi2.interfaces.OnFragInteractionListener;
import dhbk.android.wifi2.utils.db.NetworkWifiDb;

/**
 * Created by phongdth.ky on 6/17/2016.
 * get wifi hotspot with location from db
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
            // get
            cursor = mDb.query (NetworkWifiDb.TABLE_WIFI_HOTSPOT,
                    NetworkWifiDb.COLUMN_TABLE_WIFI_HOTSPOT_LOCATION,
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
        if (mActivityContext != null) {
            if (mActivityContext instanceof OnFragInteractionListener.OnMainFragInteractionListener) {
                Log.i(TAG, "onPostExecute: WifiHotspot" + cursor);
                ((OnFragInteractionListener.OnMainFragInteractionListener) mActivityContext).onReturnCursorWifiHotspot(cursor);
            }
        }
    }
}
