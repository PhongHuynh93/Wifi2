package dhbk.android.wifi2.utils.backgroundTasks;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;

import dhbk.android.wifi2.interfaces.OnFragInteractionListener;
import dhbk.android.wifi2.utils.db.NetworkWifiDb;

/**
 * Created by phongdth.ky on 6/17/2016.
 * get wifi hotspot with location from db
 */
public class GetWifiHotspotFromDbTask extends AsyncTask<Void, Void, Cursor>{
    private static final String TAG = GetWifiHotspotFromDbTask.class.getSimpleName();
    private final SQLiteDatabase mDb;
    private final Fragment mFragment;

    public GetWifiHotspotFromDbTask(SQLiteDatabase db, Fragment fragment) {
        mDb = db;
        mFragment = fragment;
    }

    @Override
    protected Cursor doInBackground(Void... params) {
        Cursor cursor;
        try {
            // get all column
            cursor = mDb.query (NetworkWifiDb.TABLE_ALL_WIFI_LOCATIONS,
                    NetworkWifiDb.COLUMN_TABLE_ALL_WIFI_LOCATION,
                    null,
                    null,
                    null, null,null);
        } catch (SQLiteException e) {
            return null;
        }
        return cursor;
    }

    @Override
    protected void onPostExecute(Cursor cursor) {
        super.onPostExecute(cursor);
        if (mFragment != null) {
            if (mFragment instanceof OnFragInteractionListener.OnMapFragInteractionListerer) {
                ((OnFragInteractionListener.OnMapFragInteractionListerer) mFragment).onGetWifiLocationCursor(cursor);
            }
        }
    }
}
