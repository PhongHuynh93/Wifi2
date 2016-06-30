package dhbk.android.wifi2.utils.backgroundTasks;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.util.Log;

import dhbk.android.wifi2.models.WifiModel;
import dhbk.android.wifi2.utils.Constant;
import dhbk.android.wifi2.utils.HelpUtils;
import dhbk.android.wifi2.utils.db.NetworkWifiDb;

/**
 * Created by phongdth.ky on 6/15/2016.
 * add wifi info contains ssid, networkid, mLatitude, mLongitude, mIsHasLocation to db
 */
public class AddWifiLocationToDbTask extends AsyncTask<Void, Void, Boolean> {
    private static final String TAG = AddWifiLocationToDbTask.class.getSimpleName();
    private final SQLiteDatabase mDb;
    private final WifiModel mWifiModel;
    private String mTableName;

    public AddWifiLocationToDbTask(SQLiteDatabase db, WifiModel wifiModel) {
        this.mDb = db;
        this.mWifiModel = wifiModel;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        String ssid = mWifiModel.getSsid();
        int networkId = mWifiModel.getNetworkId();
        double latitude = mWifiModel.getLatitude();
        double longitude = mWifiModel.getLongitude();
        int isHasLocation = mWifiModel.getIsHasLocation();

        // remove "" in ssid before make a table name, if not - we have a syntax in tablename which not containging ""
        mTableName = HelpUtils.getTableDbName(ssid, networkId, Constant.TABLE_LOCATION);

        mDb.beginTransaction();
        try {
            ContentValues wifiLocationValues = new ContentValues();

            for (int i = 0; i < NetworkWifiDb.COLUMN_TABLE_WIFI_LOCATION.length; i++) {
                switch (NetworkWifiDb.COLUMN_TABLE_WIFI_LOCATION[i]) {
                    case NetworkWifiDb.KEY_WIFI_LOCATION_LAT:
                        wifiLocationValues.put(NetworkWifiDb.KEY_WIFI_LOCATION_LAT, latitude);
                        break;
                    case NetworkWifiDb.KEY_WIFI_LOCATION_LONG:
                        wifiLocationValues.put(NetworkWifiDb.KEY_WIFI_LOCATION_LONG, longitude);
                        break;
                    case NetworkWifiDb.KEY_WIFI_LOCATION_ISTURNONGPS:
                        wifiLocationValues.put(NetworkWifiDb.KEY_WIFI_LOCATION_ISTURNONGPS, isHasLocation);
                        break;
                    default:
                        break;
                }
            }


            mDb.insertOrThrow(mTableName, null, wifiLocationValues);
            mDb.setTransactionSuccessful();

        } catch (SQLiteException e) {
            // if we catch this exception, means that we have not already created table wifi location, so we create in here.
            Log.e(TAG, "doInBackground: " + e);
            return false;
        } finally {
            mDb.endTransaction();
        }
        return true;
    }

    @Override
    protected void onPostExecute(Boolean correctDb) {
        super.onPostExecute(correctDb);
        if (!correctDb) {
            mDb.beginTransaction();
            try {
                String createTableName = NetworkWifiDb.createWifiLocationTable(mTableName);
                mDb.execSQL(createTableName);
                mDb.setTransactionSuccessful();
            } catch (SQLiteException errorCreateTable) {
                Log.e(TAG, "doInBackground: " + errorCreateTable);
            } finally {
                mDb.endTransaction();
            }
        }
    }
}
