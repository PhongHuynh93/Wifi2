package dhbk.android.wifi2.utils.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;
import android.util.Log;

import dhbk.android.wifi2.interfaces.onDbInteractionListener;
import dhbk.android.wifi2.models.WifiHotsPotModel;
import dhbk.android.wifi2.models.WifiModel;
import dhbk.android.wifi2.utils.backgroundTasks.AddWifiToDbTask;
import dhbk.android.wifi2.utils.backgroundTasks.AddWifiWithLocationToDbTask;
import dhbk.android.wifi2.utils.backgroundTasks.GetWifiFromDbTask;
import dhbk.android.wifi2.utils.backgroundTasks.GetWifiHotspotFromDbTask;

/**
 * Created by phongdth.ky on 6/15/2016.
 */
public class NetworkWifiDb implements
        onDbInteractionListener.onDbWifiTableInteractionListener {
    public static final String TAG = NetworkWifiDb.class.getSimpleName();

    // wifi table
    public static final String TABLE_WIFI = "table_wifi";
    public static final String KEY_ID = "_id";
    public static final String KEY_WIFI_STATE = "key_wifi_state";
    public static final String KEY_WIFI_SSID = "key_wifi_ssid";
    public static final String KEY_WIFI_DATE = "key_wifi_date";

    public static final String KEY_WIFI_BSSID = "key_wifi_bssid";
    public static final String KEY_WIFI_RSSI = "key_wifi_rssi";
    public static final String KEY_WIFI_MAC_ADDRESS = "key_wifi_mac_address";
    public static final String KEY_WIFI_IP_ADDRESS = "key_wifi_ip_address";
    public static final String KEY_WIFI_LINK_SPEED = "key_wifi_link_speed";
    public static final String KEY_WIFI_NETWORK_ID = "key_wifi_network_id";

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_WIFI + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_WIFI_STATE + " TEXT NOT NULL, "
            + KEY_WIFI_SSID + " TEXT NOT NULL, "
            + KEY_WIFI_DATE + " TEXT NOT NULL, "
            + KEY_WIFI_BSSID + " TEXT NOT NULL, "
            + KEY_WIFI_RSSI + " INTEGER NOT NULL, "
            + KEY_WIFI_MAC_ADDRESS + " TEXT NOT NULL, "
            + KEY_WIFI_IP_ADDRESS + " INTEGER NOT NULL, "
            + KEY_WIFI_LINK_SPEED + " INTEGER NOT NULL, "
            + KEY_WIFI_NETWORK_ID + " INTEGER NOT NULL);";

    // wifi hotspot table
    public static final String TABLE_WIFI_HOTSPOT = "table_wifi_hotspot";
    public static final String KEY_WIFI_HOTSPOT_ID = "_id";
    public static final String KEY_WIFI_HOTSPOT_SSID = "key_wifi_hotspot_ssid";
    public static final String KEY_WIFI_HOTSPOT_PASS = "key_wifi_hotspot_pass";
    public static final String KEY_WIFI_HOTSPOT_LAT = "key_wifi_hotspot_lat";
    public static final String KEY_WIFI_HOTSPOT_LONG = "key_wifi_hotspot_long";
    public static final String KEY_WIFI_HOTSPOT_ISTURNONGPS = "key_wifi_hotspot_turn_on_gps";

    public static final String CREATE_TABLE_WIFI_HOTSPOT = "CREATE TABLE " + TABLE_WIFI_HOTSPOT + "("
            + KEY_WIFI_HOTSPOT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_WIFI_HOTSPOT_SSID + " TEXT NOT NULL UNIQUE, "
            + KEY_WIFI_HOTSPOT_PASS + " TEXT NOT NULL, "
            + KEY_WIFI_HOTSPOT_LAT + " REAL NOT NULL, "
            + KEY_WIFI_HOTSPOT_LONG + " REAL NOT NULL, "
            + KEY_WIFI_HOTSPOT_ISTURNONGPS + " INTEGER NOT NULL);";


    // : 6/17/2016 test name of create table
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "onCreate: name of table syntax" + CREATE_TABLE_WIFI_HOTSPOT);
        db.execSQL(CREATE_TABLE);
        db.execSQL(CREATE_TABLE_WIFI_HOTSPOT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WIFI);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WIFI_HOTSPOT);
        onCreate(db);
    }

    @Override
    public void onInsert(SQLiteDatabase db, WifiModel wifiModel) {
        new AddWifiToDbTask(db).execute(wifiModel.getState(), wifiModel.getSsid(), wifiModel.getDate());
    }

    @Override
    public void onGetCursor(SQLiteDatabase db, Fragment fragment) {
        new GetWifiFromDbTask(db, fragment).execute();
    }

    // add wifi hotspot with location to db
    @Override
    public void onInsertWifiLocation(SQLiteDatabase db, WifiHotsPotModel wifiHotsPotModel) {
        new AddWifiWithLocationToDbTask(db).execute(wifiHotsPotModel);
    }

    // get wifi hotspot to show on map
    @Override
    public void onGetWifiHotspot(SQLiteDatabase db, Context activityContext) {
        new GetWifiHotspotFromDbTask(db, activityContext).execute();
    }
}
