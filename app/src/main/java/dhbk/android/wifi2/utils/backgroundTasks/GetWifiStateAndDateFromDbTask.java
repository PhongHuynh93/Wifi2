package dhbk.android.wifi2.utils.backgroundTasks;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;

import dhbk.android.wifi2.interfaces.OnFragInteractionListener;
import dhbk.android.wifi2.models.WifiLocationModel;
import dhbk.android.wifi2.utils.db.NetworkWifiDb;

/**
 * Created by phongdth.ky on 6/17/2016.
 * get wifi state and date with location from db with table name is created by "para - wifiModel"
 */
public class GetWifiStateAndDateFromDbTask extends AsyncTask<Void, Void, Cursor> {
    private final SQLiteDatabase mDb;
    private final Fragment mFragment;
    private final WifiLocationModel mWifiLocationModel;

    public GetWifiStateAndDateFromDbTask(SQLiteDatabase db, Fragment fragment, WifiLocationModel wifiScanWifiModel) {
        mDb = db;
        mFragment = fragment;
        mWifiLocationModel = wifiScanWifiModel;
    }

    @Override
    protected Cursor doInBackground(Void... params) {
        Cursor cursor;
        try {
            // bssid is the condition which record should the database get
            String bssidCondition = mWifiLocationModel.getBssid();
            // get all column
            cursor = mDb.query(NetworkWifiDb.TABLE_WIFI_STATE_AND_DATE,
                    NetworkWifiDb.COLUMN_TABLE_WIFI_STATE_AND_DATE,
                    NetworkWifiDb.KEY_WIFI_STATE_AND_DATE_BSSID + " = ?", new String[]{bssidCondition},
                    null, null, null);
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
                ((OnFragInteractionListener.OnHistoryFragInteractionListener) mFragment).onGetWifiStateAndDateCursor(cursor);
            }
        }
    }
}
