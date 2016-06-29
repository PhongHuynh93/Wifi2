package dhbk.android.wifi2.utils.backgroundTasks;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;

import dhbk.android.wifi2.models.WifiModel;
import dhbk.android.wifi2.utils.db.NetworkWifiDb;

/**
 * Created by phongdth.ky on 6/15/2016.
 */
public class AddWifiInfoToDbTask extends AsyncTask<Void, Void, Boolean> {
    private static final String TAG  = AddWifiInfoToDbTask.class.getSimpleName();
    private final SQLiteDatabase mDb;
    private final WifiModel mWifiModel;

    public AddWifiInfoToDbTask(SQLiteDatabase db, WifiModel wifiModel) {
        this.mDb = db;
        this.mWifiModel = wifiModel;
    }
    @Override
    protected Boolean doInBackground(Void... params) {
        String state = mWifiModel.getState();
        String ssid = mWifiModel.getSsid();
        String date = mWifiModel.getDate();
        String bssid = mWifiModel.getBssid();
        int rssi = mWifiModel.getRssi();
        String macAddress = mWifiModel.getMacAddress();
        int ipAddress = mWifiModel.getIpAddress();
        int linkSpeed = mWifiModel.getLinkSpeed();
        int networkId = mWifiModel.getNetworkId();

        mDb.beginTransaction();
        try {
            ContentValues wifiHotspotValues = new ContentValues();
            wifiHotspotValues.put(NetworkWifiDb.KEY_WIFI_STATE, state);
            wifiHotspotValues.put(NetworkWifiDb.KEY_WIFI_SSID, ssid);
            wifiHotspotValues.put(NetworkWifiDb.KEY_WIFI_DATE, date);

            wifiHotspotValues.put(NetworkWifiDb.KEY_WIFI_BSSID, bssid);
            wifiHotspotValues.put(NetworkWifiDb.KEY_WIFI_RSSI, rssi);
            wifiHotspotValues.put(NetworkWifiDb.KEY_WIFI_MAC_ADDRESS, macAddress);

            wifiHotspotValues.put(NetworkWifiDb.KEY_WIFI_IP_ADDRESS, ipAddress);
            wifiHotspotValues.put(NetworkWifiDb.KEY_WIFI_LINK_SPEED, linkSpeed);
            wifiHotspotValues.put(NetworkWifiDb.KEY_WIFI_NETWORK_ID, networkId);

            mDb.insertOrThrow(NetworkWifiDb.TABLE_WIFI, null, wifiHotspotValues);
            mDb.setTransactionSuccessful();

        } catch (SQLiteException e) {
            return false;
        } finally {
            mDb.endTransaction();
        }
        return true;
    }

}
