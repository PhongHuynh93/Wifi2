package dhbk.android.wifi2.utils.backgroundTasks;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;

import dhbk.android.wifi2.models.WifiScanWifiModel;
import dhbk.android.wifi2.utils.db.NetworkWifiDb;

/**
 * Created by phongdth.ky on 6/15/2016.
 * add wifi info contains ssid, bssid, macaddress, networkid to db
 */
public class AddWifiInfoToDbTask extends AsyncTask<Void, Void, Boolean> {
    private final SQLiteDatabase mDb;
    private final WifiScanWifiModel mWifiScanWifiModel;

    public AddWifiInfoToDbTask(SQLiteDatabase db, WifiScanWifiModel wifiScanWifiModel) {
        this.mDb = db;
        this.mWifiScanWifiModel = wifiScanWifiModel;
    }
    @Override
    protected Boolean doInBackground(Void... params) {
        String ssid = mWifiScanWifiModel.getSsid();
        String bssid = mWifiScanWifiModel.getBssid();
        String macAddress = mWifiScanWifiModel.getMacAddress();
        int networkId = mWifiScanWifiModel.getNetworkId();

        mDb.beginTransaction();
        try {
            ContentValues wifiInfoValues = new ContentValues();

            for (int i = 0; i < NetworkWifiDb.COLUMN_TABLE_WIFI_HOTSPOT_INFO.length; i++) {
                switch (NetworkWifiDb.COLUMN_TABLE_WIFI_HOTSPOT_INFO[i]) {
                    case NetworkWifiDb.KEY_WIFI_HOTSPOT_INFO_SSID:
                        wifiInfoValues.put(NetworkWifiDb.KEY_WIFI_HOTSPOT_INFO_SSID, ssid);
                        break;
                    case NetworkWifiDb.KEY_WIFI_HOTSPOT_INFO_BSSID:
                        wifiInfoValues.put(NetworkWifiDb.KEY_WIFI_HOTSPOT_INFO_BSSID, bssid);
                        break;
                    case NetworkWifiDb.KEY_WIFI_HOTSPOT_INFO_MAC_ADDRESS:
                        wifiInfoValues.put(NetworkWifiDb.KEY_WIFI_HOTSPOT_INFO_MAC_ADDRESS, macAddress);
                        break;
                    case NetworkWifiDb.KEY_WIFI_HOTSPOT_INFO_NETWORK_ID:
                        wifiInfoValues.put(NetworkWifiDb.KEY_WIFI_HOTSPOT_INFO_NETWORK_ID, networkId);
                        break;
                    default:
                        break;
                }
            }

            mDb.insertOrThrow(NetworkWifiDb.TABLE_WIFI_HOTSPOT_INFO, null, wifiInfoValues);
            mDb.setTransactionSuccessful();

        } catch (SQLiteException e) {
            return false;
        } finally {
            mDb.endTransaction();
        }
        return true;
    }

}
