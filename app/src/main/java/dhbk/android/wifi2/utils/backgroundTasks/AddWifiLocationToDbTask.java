package dhbk.android.wifi2.utils.backgroundTasks;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;

import dhbk.android.wifi2.models.WifiModel;
import dhbk.android.wifi2.utils.HelpUtils;
import dhbk.android.wifi2.utils.db.NetworkWifiDb;

/**
 * Created by phongdth.ky on 6/15/2016.
 * add wifi info contains ssid, networkid, mLatitude, mLongitude, mIsHasLocation to db
 */
public class AddWifiLocationToDbTask extends AsyncTask<Void, Void, Boolean> {
    private final SQLiteDatabase mDb;
    private final WifiModel mWifiModel;

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

            String tableName = HelpUtils.getTableDbName(ssid, networkId);
            mDb.insertOrThrow(tableName, null, wifiLocationValues);
            mDb.setTransactionSuccessful();

        } catch (SQLiteException e) {
            return false;
        } finally {
            mDb.endTransaction();
        }
        return true;
    }

}
