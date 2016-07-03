package dhbk.android.wifi2.utils.backgroundTasks;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.util.Log;

import dhbk.android.wifi2.models.WifiLocationModel;
import dhbk.android.wifi2.utils.db.NetworkWifiDb;

/**
 * Created by phongdth.ky on 6/15/2016.
 * add wifi info contains ssid, networkid, mLatitude, mLongitude, mIsHasLocation to db
 */
public class AddWifiLocationToDbTask extends AsyncTask<Void, Void, Boolean> {
    private static final String TAG = AddWifiLocationToDbTask.class.getSimpleName();
    private final SQLiteDatabase mDb;
    private final WifiLocationModel mWifiLocationModel;
    private String ssid;
    private String bssid;
    private double latitude;
    private double longitude;
    private int isHasLocation;
    private String macaddress;
    private int networkId;

    public AddWifiLocationToDbTask(SQLiteDatabase db, WifiLocationModel wifiLocationModel) {
        this.mDb = db;
        this.mWifiLocationModel = wifiLocationModel;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        ssid = mWifiLocationModel.getSsid();
        bssid = mWifiLocationModel.getBssid();
        latitude = mWifiLocationModel.getLatitude();
        longitude = mWifiLocationModel.getLongitude();
        isHasLocation = mWifiLocationModel.getIsHasLocation();
        macaddress = mWifiLocationModel.getMacAddress();
        networkId = mWifiLocationModel.getNetworkId();

        mDb.beginTransaction();
        try {

            // add location to a wifi hotspot
            ContentValues wifiLocationValues = new ContentValues();

            for (int i = 0; i < NetworkWifiDb.COLUMN_TABLE_WIFI_HOTSPOT_INFO.length; i++) {
                switch (NetworkWifiDb.COLUMN_TABLE_WIFI_HOTSPOT_INFO[i]) {
                    case NetworkWifiDb.KEY_WIFI_HOTSPOT_INFO_SSID:
                        wifiLocationValues.put(NetworkWifiDb.KEY_WIFI_HOTSPOT_INFO_SSID, ssid);
                        break;
                    case NetworkWifiDb.KEY_WIFI_HOTSPOT_INFO_BSSID:
                        wifiLocationValues.put(NetworkWifiDb.KEY_WIFI_HOTSPOT_INFO_BSSID, bssid);
                        break;
                    case NetworkWifiDb.KEY_WIFI_HOTSPOT_INFO_LAT:
                        wifiLocationValues.put(NetworkWifiDb.KEY_WIFI_HOTSPOT_INFO_LAT, latitude);
                        break;
                    case NetworkWifiDb.KEY_WIFI_HOTSPOT_INFO_LONG:
                        wifiLocationValues.put(NetworkWifiDb.KEY_WIFI_HOTSPOT_INFO_LONG, longitude);
                        break;
                    case NetworkWifiDb.KEY_WIFI_HOTSPOT_INFO_ISTURNONGPS:
                        wifiLocationValues.put(NetworkWifiDb.KEY_WIFI_HOTSPOT_INFO_ISTURNONGPS, isHasLocation);
                        break;
                    case NetworkWifiDb.KEY_WIFI_HOTSPOT_INFO_MAC_ADDRESS:
                        wifiLocationValues.put(NetworkWifiDb.KEY_WIFI_HOTSPOT_INFO_MAC_ADDRESS, macaddress);
                        break;
                    case NetworkWifiDb.KEY_WIFI_HOTSPOT_INFO_NETWORK_ID:
                        wifiLocationValues.put(NetworkWifiDb.KEY_WIFI_HOTSPOT_INFO_NETWORK_ID, networkId);
                        break;
                    default:
                        break;
                }
            }
            mDb.insertOrThrow(NetworkWifiDb.TABLE_WIFI_HOTSPOT_INFO, null, wifiLocationValues);
            mDb.setTransactionSuccessful();

        } catch (SQLiteException e) {
            // if we catch this exception, means that we've already had a wifi info record in db -> so update it
            return false;
        } finally {
            mDb.endTransaction();
        }
        return true;
    }

    @Override
    protected void onPostExecute(Boolean correctDb) {
        super.onPostExecute(correctDb);
        // update when isHasLocation = 0 (not have a location )
        if (!correctDb && isHasLocation == 0) {
            mDb.beginTransaction();
            try {
                // update table where bssid
                // add location to a wifi hotspot
                ContentValues wifiLocationValues = new ContentValues();

                for (int i = 0; i < NetworkWifiDb.COLUMN_TABLE_WIFI_HOTSPOT_INFO.length; i++) {
                    switch (NetworkWifiDb.COLUMN_TABLE_WIFI_HOTSPOT_INFO[i]) {
                        case NetworkWifiDb.KEY_WIFI_HOTSPOT_INFO_SSID:
                            wifiLocationValues.put(NetworkWifiDb.KEY_WIFI_HOTSPOT_INFO_SSID, ssid);
                            break;
                        case NetworkWifiDb.KEY_WIFI_HOTSPOT_INFO_LAT:
                            wifiLocationValues.put(NetworkWifiDb.KEY_WIFI_HOTSPOT_INFO_LAT, latitude);
                            break;
                        case NetworkWifiDb.KEY_WIFI_HOTSPOT_INFO_LONG:
                            wifiLocationValues.put(NetworkWifiDb.KEY_WIFI_HOTSPOT_INFO_LONG, longitude);
                            break;
                        case NetworkWifiDb.KEY_WIFI_HOTSPOT_INFO_ISTURNONGPS:
                            wifiLocationValues.put(NetworkWifiDb.KEY_WIFI_HOTSPOT_INFO_ISTURNONGPS, isHasLocation);
                            break;
                        case NetworkWifiDb.KEY_WIFI_HOTSPOT_INFO_MAC_ADDRESS:
                            wifiLocationValues.put(NetworkWifiDb.KEY_WIFI_HOTSPOT_INFO_MAC_ADDRESS, macaddress);
                            break;
                        case NetworkWifiDb.KEY_WIFI_HOTSPOT_INFO_NETWORK_ID:
                            wifiLocationValues.put(NetworkWifiDb.KEY_WIFI_HOTSPOT_INFO_NETWORK_ID, networkId);
                            break;
                        default:
                            break;
                    }
                }
                mDb.update(NetworkWifiDb.TABLE_WIFI_HOTSPOT_INFO, wifiLocationValues, NetworkWifiDb.KEY_WIFI_HOTSPOT_INFO_BSSID + " = ?", new String[]{bssid});
                mDb.setTransactionSuccessful();
            } catch (SQLiteException errorUpdateTable) {
                Log.e(TAG, "doInBackground: " + errorUpdateTable);
            } finally {
                mDb.endTransaction();
            }
        }
    }
}
