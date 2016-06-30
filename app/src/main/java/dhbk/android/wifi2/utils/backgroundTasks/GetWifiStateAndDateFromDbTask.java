package dhbk.android.wifi2.utils.backgroundTasks;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;

import dhbk.android.wifi2.interfaces.OnFragInteractionListener;
import dhbk.android.wifi2.models.WifiModel;
import dhbk.android.wifi2.utils.Constant;
import dhbk.android.wifi2.utils.HelpUtils;
import dhbk.android.wifi2.utils.db.NetworkWifiDb;

/**
 * Created by phongdth.ky on 6/17/2016.
 * get wifi state and date with location from db with table name is created by "para - wifiModel"
 */
public class GetWifiStateAndDateFromDbTask extends AsyncTask<Void, Void, Cursor>{
    private static final String TAG = GetWifiHotspotFromDbTask.class.getSimpleName();
    private final SQLiteDatabase mDb;
    private final Fragment mFragment;
    private final WifiModel mWifiModel;

    public GetWifiStateAndDateFromDbTask(SQLiteDatabase db, Fragment fragment, WifiModel wifiModel) {
        mDb = db;
        mFragment = fragment;
        mWifiModel = wifiModel;
    }

    @Override
    protected Cursor doInBackground(Void... params) {
        Cursor cursor;
        try {
            // get table name
            String tableName = HelpUtils.getTableDbName(mWifiModel.getSsid(), mWifiModel.getNetworkId(), Constant.TABLE_STATE_AND_DATE);
            // get all column
            cursor = mDb.query (tableName,
                    NetworkWifiDb.COLUMN_TABLE_WIFI_STATE_AND_DATE,
                    null, null,
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
                ((OnFragInteractionListener.OnHistoryFragInteractionListener) mFragment).onGetWifiStateAndDateCursor(cursor);
            }
        }
    }
}
