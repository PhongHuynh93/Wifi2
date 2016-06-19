package dhbk.android.wifi2.utils;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;

import dhbk.android.wifi2.interfaces.OnFragInteractionListener;


/**
 * Created by phongdth.ky on 6/15/2016.
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
            cursor = mDb.query (NetworkWifiDb.TABLE_WIFI,
                    new String[] {NetworkWifiDb.KEY_ID, NetworkWifiDb.KEY_WIFI_STATE, NetworkWifiDb.KEY_WIFI_SSID, NetworkWifiDb.KEY_WIFI_DATE},
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
                ((OnFragInteractionListener.OnHistoryFragInteractionListener) mFragment).populateCursorToRcv(cursor);
            }
        }

    }
}
