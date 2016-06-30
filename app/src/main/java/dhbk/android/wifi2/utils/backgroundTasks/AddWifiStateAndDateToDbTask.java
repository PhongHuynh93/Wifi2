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
 * * add wifi info contains ssid, networkid, state, date, linkspeed, wifisignal to db
 */
public class AddWifiStateAndDateToDbTask extends AsyncTask<Void, Void, Boolean> {
    private static final String TAG = AddWifiStateAndDateToDbTask.class.getSimpleName();
    private final SQLiteDatabase mDb;
    private final WifiModel mWifiModel;
    private String mTableName;

    public AddWifiStateAndDateToDbTask(SQLiteDatabase db, WifiModel wifiModel) {
        this.mDb = db;
        this.mWifiModel = wifiModel;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        // 2 var to create tablename
        String ssid = mWifiModel.getSsid();
        int networkId = mWifiModel.getNetworkId();

        mTableName = HelpUtils.getTableDbName(ssid, networkId, Constant.TABLE_STATE_AND_DATE);

        // save to db
        String state = mWifiModel.getState();
        String date = mWifiModel.getDate();
        int linkSpeed = mWifiModel.getLinkSpeed();
        int rssi = mWifiModel.getRssi();
        int ipAddress = mWifiModel.getIpAddress();

        mDb.beginTransaction();
        try {
            ContentValues wifiStateAndDateValues = new ContentValues();

            for (int i = 0; i < NetworkWifiDb.COLUMN_TABLE_WIFI_STATE_AND_DATE.length; i++) {
                switch (NetworkWifiDb.COLUMN_TABLE_WIFI_STATE_AND_DATE[i]) {
                    case NetworkWifiDb.KEY_WIFI_STATE_AND_DATE_STATE:
                        wifiStateAndDateValues.put(NetworkWifiDb.KEY_WIFI_STATE_AND_DATE_STATE, state);
                        break;
                    case NetworkWifiDb.KEY_WIFI_STATE_AND_DATE_DATE:
                        wifiStateAndDateValues.put(NetworkWifiDb.KEY_WIFI_STATE_AND_DATE_DATE, date);
                        break;
                    case NetworkWifiDb.KEY_WIFI_STATE_AND_DATE_RSSI:
                        wifiStateAndDateValues.put(NetworkWifiDb.KEY_WIFI_STATE_AND_DATE_RSSI, rssi);
                        break;
                    case NetworkWifiDb.KEY_WIFI_STATE_AND_DATE_LINK_SPEED:
                        wifiStateAndDateValues.put(NetworkWifiDb.KEY_WIFI_STATE_AND_DATE_LINK_SPEED, linkSpeed);
                        break;
                    case NetworkWifiDb.KEY_WIFI_STATE_AND_DATE_IP_ADDRESS:
                        wifiStateAndDateValues.put(NetworkWifiDb.KEY_WIFI_STATE_AND_DATE_IP_ADDRESS, ipAddress);
                        break;
                    default:
                        break;
                }
            }

            mDb.insertOrThrow(mTableName, null, wifiStateAndDateValues);
            mDb.setTransactionSuccessful();

        } catch (SQLiteException e) {
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
            // if we catch this exception, means that we have not already created table wifi location, so we create in here.
            try {
                String createTableName = NetworkWifiDb.createWifiStateAndDate(mTableName);
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
