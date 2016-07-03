package dhbk.android.wifi2.utils.backgroundTasks;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;

import dhbk.android.wifi2.models.WifiLocationModel;
import dhbk.android.wifi2.utils.db.NetworkWifiDb;

/**
 * Created by phongdth.ky on 6/15/2016.
 * update ssid, password, encryption of a wifihotspot depends on bssid
 */
public class UpdateWifiInDbTask extends AsyncTask<Void, Void, Boolean> {
    private static final String TAG = UpdateWifiInDbTask.class.getSimpleName();
    private final SQLiteDatabase mDb;
    private final WifiLocationModel mWifiLocationModel;

    public UpdateWifiInDbTask(SQLiteDatabase db, WifiLocationModel wifiLocationModel) {
        this.mDb = db;
        this.mWifiLocationModel = wifiLocationModel;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        String ssid = mWifiLocationModel.getSsid();
        String bssid = mWifiLocationModel.getBssid();
        String password = mWifiLocationModel.getPassword();
        String encryption = mWifiLocationModel.getEncryption();
        double latitude = mWifiLocationModel.getLatitude();
        double longitude = mWifiLocationModel.getLongitude();
        int isTurnGpsInt = mWifiLocationModel.getIsHasLocation();

        mDb.beginTransaction();
        try {


            ContentValues wifiLocationValues = new ContentValues();

            for (int i = 0; i < NetworkWifiDb.COLUMN_TABLE_WIFI_HOTSPOT_INFO.length; i++) {
                switch (NetworkWifiDb.COLUMN_TABLE_WIFI_HOTSPOT_INFO[i]) {
                    case NetworkWifiDb.KEY_WIFI_HOTSPOT_INFO_SSID:
                        wifiLocationValues.put(NetworkWifiDb.KEY_WIFI_HOTSPOT_INFO_SSID, ssid);
                        break;
                    case NetworkWifiDb.KEY_WIFI_HOTSPOT_INFO_ENCRYPTION:
                        wifiLocationValues.put(NetworkWifiDb.KEY_WIFI_HOTSPOT_INFO_ENCRYPTION, encryption);
                        break;
                    case NetworkWifiDb.KEY_WIFI_HOTSPOT_INFO_PASSWORD:
                        wifiLocationValues.put(NetworkWifiDb.KEY_WIFI_HOTSPOT_INFO_PASSWORD, password);
                        break;

                    // add location to db.
                    case NetworkWifiDb.KEY_WIFI_HOTSPOT_INFO_LAT:
                        wifiLocationValues.put(NetworkWifiDb.KEY_WIFI_HOTSPOT_INFO_LAT, latitude);
                        break;
                    case NetworkWifiDb.KEY_WIFI_HOTSPOT_INFO_LONG:
                        wifiLocationValues.put(NetworkWifiDb.KEY_WIFI_HOTSPOT_INFO_LONG, longitude);
                        break;
                    case NetworkWifiDb.KEY_WIFI_HOTSPOT_INFO_ISTURNONGPS:
                        wifiLocationValues.put(NetworkWifiDb.KEY_WIFI_HOTSPOT_INFO_ISTURNONGPS, isTurnGpsInt);
                        break;
                    default:
                        break;
                }
            }

            mDb.update(NetworkWifiDb.TABLE_WIFI_HOTSPOT_INFO, wifiLocationValues, NetworkWifiDb.KEY_WIFI_HOTSPOT_INFO_BSSID + " = ?", new String[] {bssid});
            mDb.setTransactionSuccessful();

        } catch (SQLiteException e) {
            return false;
        } finally {
            mDb.endTransaction();
        }
        return true;
    }


}
