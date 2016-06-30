package dhbk.android.wifi2.utils.backgroundTasks;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;

import dhbk.android.wifi2.interfaces.OnFragInteractionListener;
import dhbk.android.wifi2.utils.db.NetworkWifiDb;


/**
 * Created by phongdth.ky on 6/15/2016.
 * get wifi data info in TABLE_WIFI_HOTSPOT_INFO table (dont have location) from db
 */
public class GetWifiFromDbTask extends AsyncTask<Void, Void, Cursor>{
    private final SQLiteDatabase mDb;
    private final Fragment mFragment;

    // : 6/15/2016 get cursor from database
    public GetWifiFromDbTask(SQLiteDatabase db, Fragment fragment) {
        mDb = db;
        mFragment = fragment;
    }

    @Override
    protected Cursor doInBackground(Void... params) {
        Cursor cursor;

        try {
            // get all of column from table
            cursor = mDb.query (NetworkWifiDb.TABLE_WIFI_HOTSPOT_INFO,
                    NetworkWifiDb.COLUMN_TABLE_WIFI_HOTSPOT_INFO,
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
            if (mFragment instanceof OnFragInteractionListener.OnHistoryFragInteractionListener) {
                ((OnFragInteractionListener.OnHistoryFragInteractionListener) mFragment).onGetWifiHistoryCursor(cursor);
            }
        }

    }
}
