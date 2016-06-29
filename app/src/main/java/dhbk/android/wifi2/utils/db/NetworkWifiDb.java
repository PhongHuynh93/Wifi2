package dhbk.android.wifi2.utils.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;

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

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    // wifi table - (notice it doesn't have location)
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

    public static final String[] COLUMN_TABLE_WIFI = new String[] {
            KEY_ID,
            KEY_WIFI_STATE,
            KEY_WIFI_SSID,
            KEY_WIFI_DATE,
            KEY_WIFI_BSSID,
            KEY_WIFI_RSSI,
            KEY_WIFI_MAC_ADDRESS,
            KEY_WIFI_IP_ADDRESS,
            KEY_WIFI_LINK_SPEED,
            KEY_WIFI_NETWORK_ID
    };

    // because Each configured network has a unique small integer ID, used to identify the network
    // https://developer.android.com/reference/android/net/wifi/WifiInfo.html#getNetworkId()
    // not ssid - because many wifi have the same ssid
    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_WIFI + "("
            + COLUMN_TABLE_WIFI[0] + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_TABLE_WIFI[1] + " TEXT NOT NULL, "
            + COLUMN_TABLE_WIFI[2] + " TEXT NOT NULL, "
            + COLUMN_TABLE_WIFI[3] + " TEXT NOT NULL, "
            + COLUMN_TABLE_WIFI[4] + " TEXT NOT NULL, "
            + COLUMN_TABLE_WIFI[5] + " INTEGER NOT NULL, "
            + COLUMN_TABLE_WIFI[6] + " TEXT NOT NULL, "
            + COLUMN_TABLE_WIFI[7] + " INTEGER NOT NULL, "
            + COLUMN_TABLE_WIFI[8] + " INTEGER NOT NULL, "
            + COLUMN_TABLE_WIFI[9] + " INTEGER NOT NULL UNIQUE);";

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    // wifi hotspot with location
    public static final String TABLE_WIFI_HOTSPOT = "table_wifi_hotspot";
    public static final String KEY_WIFI_HOTSPOT_ID = "_id";
    public static final String KEY_WIFI_HOTSPOT_SSID = "key_wifi_hotspot_ssid";
    public static final String KEY_WIFI_HOTSPOT_PASS = "key_wifi_hotspot_pass";
    public static final String KEY_WIFI_HOTSPOT_LAT = "key_wifi_hotspot_lat";
    public static final String KEY_WIFI_HOTSPOT_LONG = "key_wifi_hotspot_long";
    public static final String KEY_WIFI_HOTSPOT_ISTURNONGPS = "key_wifi_hotspot_turn_on_gps";

    public static final String[] COLUMN_TABLE_WIFI_HOTSPOT_LOCATION = new String[] {
            KEY_WIFI_HOTSPOT_ID,
            KEY_WIFI_HOTSPOT_SSID,
            KEY_WIFI_HOTSPOT_PASS,
            KEY_WIFI_HOTSPOT_LAT,
            KEY_WIFI_HOTSPOT_LONG,
            KEY_WIFI_HOTSPOT_ISTURNONGPS
    };


    public static final String CREATE_TABLE_WIFI_HOTSPOT = "CREATE TABLE " + TABLE_WIFI_HOTSPOT + "("
            + COLUMN_TABLE_WIFI_HOTSPOT_LOCATION[0] + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_TABLE_WIFI_HOTSPOT_LOCATION[1] + " TEXT NOT NULL UNIQUE, "
            + COLUMN_TABLE_WIFI_HOTSPOT_LOCATION[2] + " TEXT NOT NULL, "
            + COLUMN_TABLE_WIFI_HOTSPOT_LOCATION[3] + " REAL NOT NULL, "
            + COLUMN_TABLE_WIFI_HOTSPOT_LOCATION[4] + " REAL NOT NULL, "
            + COLUMN_TABLE_WIFI_HOTSPOT_LOCATION[5] + " INTEGER NOT NULL);"; // giá trị này = 1 thì mới chứng to wifi hotspot đó có location

    // TODO: 6/29/2016 AFTER THIS TABLE RUN, REMOVE 2 TABLE ABOVE
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    // WIFI HOTSPOT
    public static final String TABLE_WIFI_HOTSPOT_INFO = "table_wifi_hotspot_new";

    // column name and value
    public static final String KEY_WIFI_HOTSPOT_INFO_ID = "_id";
    public static final String KEY_WIFI_HOTSPOT_INFO_SSID = "column_ssid";
    public static final String KEY_WIFI_HOTSPOT_INFO_MAC_ADDRESS = "column_mac_address";
    public static final String KEY_WIFI_HOTSPOT_INFO_NETWORK_ID = "column_network_id";

    public static final String VALUE_WIFI_HOTSPOT_INFO_ID = " INTEGER PRIMARY KEY AUTOINCREMENT, ";
    public static final String VALUE_WIFI_HOTSPOT_INFO_SSID = " TEXT NOT NULL, ";
    public static final String VALUE_WIFI_HOTSPOT_INFO_MAC_ADDRESS = " TEXT NOT NULL, ";
    public static final String VALUE_WIFI_HOTSPOT_INFO_NETWORK_ID = " INTEGER NOT NULL UNIQUE);";

    // declare array of column and value
    public static final String[] COLUMN_TABLE_WIFI_HOTSPOT_INFO = new String[] {
            KEY_WIFI_HOTSPOT_INFO_ID,
            KEY_WIFI_HOTSPOT_INFO_SSID,
            KEY_WIFI_HOTSPOT_INFO_MAC_ADDRESS,
            KEY_WIFI_HOTSPOT_INFO_NETWORK_ID,
    };

    public static final String[] VALUE_COLUMN_WIFI_HOTSPOT_INFO = new String[] {
            VALUE_WIFI_HOTSPOT_INFO_ID,
            VALUE_WIFI_HOTSPOT_INFO_SSID,
            VALUE_WIFI_HOTSPOT_INFO_MAC_ADDRESS,
            VALUE_WIFI_HOTSPOT_INFO_NETWORK_ID,
    };


    // create wifi table
    private String createWifiHotspotInfoTable() {
        StringBuilder createWifiTable = new StringBuilder();
        createWifiTable.append("CREATE TABLE ");
        createWifiTable.append(TABLE_WIFI_HOTSPOT_INFO);
        createWifiTable.append("(");

        for (int i = 0; i < COLUMN_TABLE_WIFI_HOTSPOT_INFO.length; i++) {
            switch (COLUMN_TABLE_WIFI_HOTSPOT_INFO[i]) {
                case KEY_WIFI_HOTSPOT_INFO_ID:
                    createWifiTable.append(COLUMN_TABLE_WIFI_HOTSPOT_INFO[i]);
                    createWifiTable.append(VALUE_COLUMN_WIFI_HOTSPOT_INFO[i]);
                    break;
                case KEY_WIFI_HOTSPOT_INFO_SSID:
                    createWifiTable.append(COLUMN_TABLE_WIFI_HOTSPOT_INFO[i]);
                    createWifiTable.append(VALUE_COLUMN_WIFI_HOTSPOT_INFO[i]);
                    break;
                case KEY_WIFI_HOTSPOT_INFO_MAC_ADDRESS:
                    createWifiTable.append(COLUMN_TABLE_WIFI_HOTSPOT_INFO[i]);
                    createWifiTable.append(VALUE_COLUMN_WIFI_HOTSPOT_INFO[i]);
                    break;
                case KEY_WIFI_HOTSPOT_INFO_NETWORK_ID:
                    createWifiTable.append(COLUMN_TABLE_WIFI_HOTSPOT_INFO[i]);
                    createWifiTable.append(VALUE_COLUMN_WIFI_HOTSPOT_INFO[i]);
                    break;
                default:
                    break;
            }
        }
        return createWifiTable.toString();
    }




    ///////////////////////////////////////////////////////////////////////////////////////////////////
    // LOCATION AT THAT WIFI HOTSPOT

    // column name and value
    public static final String KEY_WIFI_LOCATION_ID = "_id";
    public static final String KEY_WIFI_LOCATION_LAT = "column_lat";
    public static final String KEY_WIFI_LOCATION_LONG = "column_long";
    public static final String KEY_WIFI_LOCATION_ISTURNONGPS = "column_turn_on_gps";

    public static final String VALUE_WIFI_LOCATION_ID = " INTEGER PRIMARY KEY AUTOINCREMENT, ";
    public static final String VALUE_WIFI_LOCATION_LAT = " REAL NOT NULL DEFAULT 0, ";
    public static final String VALUE_WIFI_LOCATION_LONG = " REAL NOT NULL DEFAULT 0, ";
    public static final String VALUE_WIFI_LOCATION_ISTURNONGPS = " INTEGER NOT NULL UNIQUE DEFAULT 0);";

    // declare array of column and value
    public static final String[] COLUMN_TABLE_WIFI_LOCATION = new String[] {
            KEY_WIFI_LOCATION_ID,
            KEY_WIFI_LOCATION_LAT,
            KEY_WIFI_LOCATION_LONG,
            KEY_WIFI_LOCATION_ISTURNONGPS
    };

    public static final String[] VALUE_COLUMN_WIFI_LOCATION = new String[] {
            VALUE_WIFI_LOCATION_ID,
            VALUE_WIFI_LOCATION_LAT,
            VALUE_WIFI_LOCATION_LONG,
            VALUE_WIFI_LOCATION_ISTURNONGPS
    };


    // create wifi table
    private String createWifiLocationTable(String tableName) {
        StringBuilder createWifiTable = new StringBuilder();
        createWifiTable.append("CREATE TABLE ");
        createWifiTable.append(tableName);
        createWifiTable.append("(");

        for (int i = 0; i < COLUMN_TABLE_WIFI_LOCATION.length; i++) {
            if (COLUMN_TABLE_WIFI_LOCATION[i].equals(KEY_WIFI_LOCATION_ID)) {
                createWifiTable.append(COLUMN_TABLE_WIFI_LOCATION[i]);
                createWifiTable.append(VALUE_COLUMN_WIFI_LOCATION[i]);
            } else if (COLUMN_TABLE_WIFI_LOCATION[i].equals(KEY_WIFI_LOCATION_LAT)) {
                createWifiTable.append(COLUMN_TABLE_WIFI_LOCATION[i]);
                createWifiTable.append(VALUE_COLUMN_WIFI_LOCATION[i]);
            } else if (COLUMN_TABLE_WIFI_LOCATION[i].equals(KEY_WIFI_LOCATION_LONG)) {
                createWifiTable.append(COLUMN_TABLE_WIFI_LOCATION[i]);
                createWifiTable.append(VALUE_COLUMN_WIFI_LOCATION[i]);
            } else if (COLUMN_TABLE_WIFI_LOCATION[i].equals(KEY_WIFI_LOCATION_ISTURNONGPS)) {
                createWifiTable.append(COLUMN_TABLE_WIFI_LOCATION[i]);
                createWifiTable.append(VALUE_COLUMN_WIFI_LOCATION[i]);
            }
        }
        return createWifiTable.toString();
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////
    // STATE AND DATE OF WIFI HOTSPOT

    // column name and value
    public static final String KEY_WIFI_STATE_AND_DATE_ID = "_id";
    public static final String KEY_WIFI_STATE_AND_DATE_STATE = "column_state";
    public static final String KEY_WIFI_STATE_AND_DATE_DATE = "column_date";
    public static final String KEY_WIFI_STATE_AND_DATE_RSSI = "column_rssi";
    public static final String KEY_WIFI_STATE_AND_DATE_LINK_SPEED = "column_link_speed";

    public static final String VALUE_WIFI_STATE_AND_DATE_ID = " INTEGER PRIMARY KEY AUTOINCREMENT, ";
    public static final String VALUE_WIFI_STATE_AND_DATE_STATE = " TEXT NOT NULL, ";
    public static final String VALUE_WIFI_STATE_AND_DATE_DATE = " TEXT NOT NULL, ";
    public static final String VALUE_WIFI_STATE_AND_DATE_RSSI = " INTEGER, ";
    public static final String VALUE_WIFI_STATE_AND_DATE_LINK_SPEED = " INTEGER);";

    // declare array of column and value
    public static final String[] COLUMN_TABLE_WIFI_STATE_AND_DATE = new String[] {
            KEY_WIFI_STATE_AND_DATE_ID,
            KEY_WIFI_STATE_AND_DATE_STATE,
            KEY_WIFI_STATE_AND_DATE_DATE,
            KEY_WIFI_STATE_AND_DATE_RSSI,
            KEY_WIFI_STATE_AND_DATE_LINK_SPEED
    };

    public static final String[] VALUE_COLUMN_WIFI_STATE_AND_DATE = new String[] {
            VALUE_WIFI_STATE_AND_DATE_ID,
            VALUE_WIFI_STATE_AND_DATE_STATE,
            VALUE_WIFI_STATE_AND_DATE_DATE,
            VALUE_WIFI_STATE_AND_DATE_RSSI,
            VALUE_WIFI_STATE_AND_DATE_LINK_SPEED
    };


    // create wifi table
    private String createWifiStateAndDate(String tableName) {
        StringBuilder createWifiTable = new StringBuilder();
        createWifiTable.append("CREATE TABLE ");
        createWifiTable.append(tableName);
        createWifiTable.append("(");

        for (int i = 0; i < COLUMN_TABLE_WIFI_STATE_AND_DATE.length; i++) {
            if (COLUMN_TABLE_WIFI_STATE_AND_DATE[i].equals(KEY_WIFI_STATE_AND_DATE_STATE)) {
                createWifiTable.append(COLUMN_TABLE_WIFI_STATE_AND_DATE[i]);
                createWifiTable.append(VALUE_COLUMN_WIFI_STATE_AND_DATE[i]);
            } else if (COLUMN_TABLE_WIFI_STATE_AND_DATE[i].equals(KEY_WIFI_STATE_AND_DATE_DATE)) {
                createWifiTable.append(COLUMN_TABLE_WIFI_STATE_AND_DATE[i]);
                createWifiTable.append(VALUE_COLUMN_WIFI_STATE_AND_DATE[i]);
            } else if (COLUMN_TABLE_WIFI_STATE_AND_DATE[i].equals(KEY_WIFI_STATE_AND_DATE_RSSI)) {
                createWifiTable.append(COLUMN_TABLE_WIFI_STATE_AND_DATE[i]);
                createWifiTable.append(VALUE_COLUMN_WIFI_STATE_AND_DATE[i]);
            } else if (COLUMN_TABLE_WIFI_STATE_AND_DATE[i].equals(KEY_WIFI_STATE_AND_DATE_LINK_SPEED)) {
                createWifiTable.append(COLUMN_TABLE_WIFI_STATE_AND_DATE[i]);
                createWifiTable.append(VALUE_COLUMN_WIFI_STATE_AND_DATE[i]);
            }
        }
        return createWifiTable.toString();
    }


    // : 6/17/2016 test name of create table
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
        db.execSQL(CREATE_TABLE_WIFI_HOTSPOT);

        // create table: wifi hotspot info
        db.execSQL(createWifiHotspotInfoTable());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WIFI);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WIFI_HOTSPOT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WIFI_HOTSPOT_INFO);
        onCreate(db);
    }

    @Override
    public void onInsert(SQLiteDatabase db, WifiModel wifiModel) {
        new AddWifiToDbTask(db, wifiModel).execute();
    }

    @Override
    public void getWifiHistoryCursor(SQLiteDatabase db, Fragment fragment) {
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

    // TODO: 6/29/2016 new method to deal with new table, after the program run, remove another method
    // add wifi info to db
    @Override
    public void addWifiInfo(WifiModel wifiInfoModel) {

    }

    // add wifi location to db
    @Override
    public void addWifiLocation(WifiModel wifiLocationModel) {

    }

    // add wifi state and date to db
    @Override
    public void addWifiStateAndDate(WifiModel wifiStateAndDateModel) {

    }


}
