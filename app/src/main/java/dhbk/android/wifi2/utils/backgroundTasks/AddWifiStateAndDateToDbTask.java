package dhbk.android.wifi2.utils.backgroundTasks;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.util.Log;

import dhbk.android.wifi2.models.WifiStateAndDateModel;
import dhbk.android.wifi2.utils.db.NetworkWifiDb;

/**
 * Created by phongdth.ky on 6/15/2016.
 * * add wifi info contains ssid, networkid, state, date, linkspeed, wifisignal to db
 */
public class AddWifiStateAndDateToDbTask extends AsyncTask<Void, Void, Boolean> {
    private static final String TAG = AddWifiStateAndDateToDbTask.class.getSimpleName();
    private final SQLiteDatabase mDb;
    private final WifiStateAndDateModel mWifiStateAndDateModel;
    private String mTableName;

    public AddWifiStateAndDateToDbTask(SQLiteDatabase db, WifiStateAndDateModel wifiStateAndDateModel) {
        this.mDb = db;
        this.mWifiStateAndDateModel = wifiStateAndDateModel;
    }

    // FIXME: 7/3/16 test this
    @Override
    protected Boolean doInBackground(Void... params) {
        // this var to create tablename
        String bssid = mWifiStateAndDateModel.getBssid();

        // save to db
        String state = mWifiStateAndDateModel.getState();
        String date = mWifiStateAndDateModel.getDate();
        int linkSpeed = mWifiStateAndDateModel.getLinkSpeed();
        int rssi = mWifiStateAndDateModel.getRssi();
        int ipAddress = mWifiStateAndDateModel.getIpAddress();

        mDb.beginTransaction();
        try {
            ContentValues wifiStateAndDateValues = new ContentValues();

            for (int i = 0; i < NetworkWifiDb.COLUMN_TABLE_WIFI_STATE_AND_DATE.length; i++) {
                switch (NetworkWifiDb.COLUMN_TABLE_WIFI_STATE_AND_DATE[i]) {
                    case NetworkWifiDb.KEY_WIFI_STATE_AND_DATE_BSSID:
                        wifiStateAndDateValues.put(NetworkWifiDb.KEY_WIFI_STATE_AND_DATE_BSSID, bssid);
                        break;
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

            mDb.insertOrThrow(NetworkWifiDb.TABLE_WIFI_STATE_AND_DATE, null, wifiStateAndDateValues);
            mDb.setTransactionSuccessful();

        } catch (SQLiteException e) {
            Log.e(TAG, "doInBackground: " + e);
            return false;
        } finally {
            mDb.endTransaction();
        }
        return true;
    }
}
