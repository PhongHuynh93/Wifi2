package dhbk.android.wifi2.utils.db;

import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;

import dhbk.android.wifi2.interfaces.onDbInteractionListener;
import dhbk.android.wifi2.models.WifiLocationModel;
import dhbk.android.wifi2.models.WifiStateAndDateModel;
import dhbk.android.wifi2.utils.backgroundTasks.AddWifiLocationToDbTask;
import dhbk.android.wifi2.utils.backgroundTasks.AddWifiStateAndDateToDbTask;
import dhbk.android.wifi2.utils.backgroundTasks.GetWifiFromDbTask;
import dhbk.android.wifi2.utils.backgroundTasks.GetWifiHotspotFromDbTask;
import dhbk.android.wifi2.utils.backgroundTasks.GetWifiStateAndDateFromDbTask;
import dhbk.android.wifi2.utils.backgroundTasks.UpdateWifiInDbTask;

/**
 * Created by phongdth.ky on 6/15/2016.
 */
public class NetworkWifiDb implements
        onDbInteractionListener.onDbWifiTableInteractionListener {

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    // WIFI HOTSPOT
    public static final String TABLE_WIFI_HOTSPOT_INFO = "table_wifi_hotspot_new";

    // column name and value
    public static final String KEY_WIFI_HOTSPOT_INFO_ID = "_id";
    public static final String KEY_WIFI_HOTSPOT_INFO_SSID = "column_ssid";
    public static final String KEY_WIFI_HOTSPOT_INFO_BSSID = "column_bssid";
    public static final String KEY_WIFI_HOTSPOT_INFO_MAC_ADDRESS = "column_mac_address";
    public static final String KEY_WIFI_HOTSPOT_INFO_ENCRYPTION = "column_encryption";
    public static final String KEY_WIFI_HOTSPOT_INFO_PASSWORD = "column_password";
    public static final String KEY_WIFI_HOTSPOT_INFO_LAT = "column_lat";
    public static final String KEY_WIFI_HOTSPOT_INFO_LONG = "column_long";
    public static final String KEY_WIFI_HOTSPOT_INFO_ISTURNONGPS = "column_turn_on_gps";
    public static final String KEY_WIFI_HOTSPOT_INFO_NETWORK_ID = "column_network_id";

    // we want to show a unique wifi hotspot, bssid is a mac address of access points, it's unique so we want this value to create a list of wifi hotspot
    public static final String VALUE_WIFI_HOTSPOT_INFO_ID = " INTEGER PRIMARY KEY AUTOINCREMENT, ";
    public static final String VALUE_WIFI_HOTSPOT_INFO_SSID = " TEXT DEFAULT ' ', ";
    public static final String VALUE_WIFI_HOTSPOT_INFO_BSSID = " TEXT NOT NULL UNIQUE, ";
    public static final String VALUE_WIFI_HOTSPOT_INFO_MAC_ADDRESS = " TEXT DEFAULT ' ', ";
    public static final String VALUE_WIFI_HOTSPOT_INFO_ENCRYPTION = " TEXT DEFAULT ' ', ";
    public static final String VALUE_WIFI_HOTSPOT_INFO_PASSWORD = " TEXT DEFAULT ' ', ";
    public static final String VALUE_WIFI_HOTSPOT_INFO_LAT = " REAL DEFAULT 0, ";
    public static final String VALUE_WIFI_HOTSPOT_INFO_LONG = " REAL DEFAULT 0, ";
    public static final String VALUE_WIFI_HOTSPOT_INFO_ISTURNONGPS = " INTEGER DEFAULT 0, ";
    public static final String VALUE_WIFI_HOTSPOT_INFO_NETWORK_ID = " INTEGER);";

    // declare array of column and value
    public static final String[] COLUMN_TABLE_WIFI_HOTSPOT_INFO = new String[] {
            KEY_WIFI_HOTSPOT_INFO_ID,
            KEY_WIFI_HOTSPOT_INFO_SSID,
            KEY_WIFI_HOTSPOT_INFO_BSSID,
            KEY_WIFI_HOTSPOT_INFO_MAC_ADDRESS,
            KEY_WIFI_HOTSPOT_INFO_ENCRYPTION,
            KEY_WIFI_HOTSPOT_INFO_PASSWORD,
            KEY_WIFI_HOTSPOT_INFO_LAT,
            KEY_WIFI_HOTSPOT_INFO_LONG,
            KEY_WIFI_HOTSPOT_INFO_ISTURNONGPS,
            KEY_WIFI_HOTSPOT_INFO_NETWORK_ID,
    };

    public static final String[] VALUE_COLUMN_WIFI_HOTSPOT_INFO = new String[] {
            VALUE_WIFI_HOTSPOT_INFO_ID,
            VALUE_WIFI_HOTSPOT_INFO_SSID,
            VALUE_WIFI_HOTSPOT_INFO_BSSID,
            VALUE_WIFI_HOTSPOT_INFO_MAC_ADDRESS,
            VALUE_WIFI_HOTSPOT_INFO_ENCRYPTION,
            VALUE_WIFI_HOTSPOT_INFO_PASSWORD,
            VALUE_WIFI_HOTSPOT_INFO_LAT,
            VALUE_WIFI_HOTSPOT_INFO_LONG,
            VALUE_WIFI_HOTSPOT_INFO_ISTURNONGPS,
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
                case KEY_WIFI_HOTSPOT_INFO_BSSID:
                    createWifiTable.append(COLUMN_TABLE_WIFI_HOTSPOT_INFO[i]);
                    createWifiTable.append(VALUE_COLUMN_WIFI_HOTSPOT_INFO[i]);
                    break;
                case KEY_WIFI_HOTSPOT_INFO_MAC_ADDRESS:
                    createWifiTable.append(COLUMN_TABLE_WIFI_HOTSPOT_INFO[i]);
                    createWifiTable.append(VALUE_COLUMN_WIFI_HOTSPOT_INFO[i]);
                    break;
                case KEY_WIFI_HOTSPOT_INFO_ENCRYPTION:
                    createWifiTable.append(COLUMN_TABLE_WIFI_HOTSPOT_INFO[i]);
                    createWifiTable.append(VALUE_COLUMN_WIFI_HOTSPOT_INFO[i]);
                    break;
                case KEY_WIFI_HOTSPOT_INFO_PASSWORD:
                    createWifiTable.append(COLUMN_TABLE_WIFI_HOTSPOT_INFO[i]);
                    createWifiTable.append(VALUE_COLUMN_WIFI_HOTSPOT_INFO[i]);
                    break;
                case KEY_WIFI_HOTSPOT_INFO_LAT:
                    createWifiTable.append(COLUMN_TABLE_WIFI_HOTSPOT_INFO[i]);
                    createWifiTable.append(VALUE_COLUMN_WIFI_HOTSPOT_INFO[i]);
                    break;
                case KEY_WIFI_HOTSPOT_INFO_LONG:
                    createWifiTable.append(COLUMN_TABLE_WIFI_HOTSPOT_INFO[i]);
                    createWifiTable.append(VALUE_COLUMN_WIFI_HOTSPOT_INFO[i]);
                    break;
                case KEY_WIFI_HOTSPOT_INFO_ISTURNONGPS:
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
    // STATE AND DATE OF WIFI HOTSPOT
    // column name and value
    public static final String TABLE_WIFI_STATE_AND_DATE = "table_wifi_state_and_date";

    public static final String KEY_WIFI_STATE_AND_DATE_ID = "_id";
    public static final String KEY_WIFI_STATE_AND_DATE_BSSID = "column_bssid";
    public static final String KEY_WIFI_STATE_AND_DATE_STATE = "column_state";
    public static final String KEY_WIFI_STATE_AND_DATE_DATE = "column_date";
    public static final String KEY_WIFI_STATE_AND_DATE_RSSI = "column_rssi";
    public static final String KEY_WIFI_STATE_AND_DATE_LINK_SPEED = "column_link_speed";
    public static final String KEY_WIFI_STATE_AND_DATE_IP_ADDRESS = "column_ip_address";

    public static final String VALUE_WIFI_STATE_AND_DATE_ID = " INTEGER PRIMARY KEY AUTOINCREMENT, ";
    public static final String VALUE_WIFI_STATE_AND_DATE_BSSID = " TEXT, ";
    public static final String VALUE_WIFI_STATE_AND_DATE_STATE = " TEXT, ";
    public static final String VALUE_WIFI_STATE_AND_DATE_DATE = " TEXT, ";
    public static final String VALUE_WIFI_STATE_AND_DATE_RSSI = " INTEGER, ";
    public static final String VALUE_WIFI_STATE_AND_DATE_LINK_SPEED = " INTEGER, ";
    public static final String VALUE_WIFI_STATE_AND_DATE_IP_ADDRESS = " INTEGER);";

    // declare array of column and value
    public static final String[] COLUMN_TABLE_WIFI_STATE_AND_DATE = new String[] {
            KEY_WIFI_STATE_AND_DATE_ID,
            KEY_WIFI_STATE_AND_DATE_BSSID,
            KEY_WIFI_STATE_AND_DATE_STATE,
            KEY_WIFI_STATE_AND_DATE_DATE,
            KEY_WIFI_STATE_AND_DATE_RSSI,
            KEY_WIFI_STATE_AND_DATE_LINK_SPEED,
            KEY_WIFI_STATE_AND_DATE_IP_ADDRESS
    };

    public static final String[] VALUE_COLUMN_WIFI_STATE_AND_DATE = new String[] {
            VALUE_WIFI_STATE_AND_DATE_ID,
            VALUE_WIFI_STATE_AND_DATE_BSSID,
            VALUE_WIFI_STATE_AND_DATE_STATE,
            VALUE_WIFI_STATE_AND_DATE_DATE,
            VALUE_WIFI_STATE_AND_DATE_RSSI,
            VALUE_WIFI_STATE_AND_DATE_LINK_SPEED,
            VALUE_WIFI_STATE_AND_DATE_IP_ADDRESS
    };


    // create wifi table
    public static String createWifiStateAndDate() {
        StringBuilder createWifiTable = new StringBuilder();
        createWifiTable.append("CREATE TABLE ");
        createWifiTable.append(TABLE_WIFI_STATE_AND_DATE);
        createWifiTable.append("(");

        for (int i = 0; i < COLUMN_TABLE_WIFI_STATE_AND_DATE.length; i++) {
            switch (COLUMN_TABLE_WIFI_STATE_AND_DATE[i]) {
                case KEY_WIFI_STATE_AND_DATE_ID:
                    createWifiTable.append(COLUMN_TABLE_WIFI_STATE_AND_DATE[i]);
                    createWifiTable.append(VALUE_COLUMN_WIFI_STATE_AND_DATE[i]);
                    break;
                case KEY_WIFI_STATE_AND_DATE_BSSID:
                    createWifiTable.append(COLUMN_TABLE_WIFI_STATE_AND_DATE[i]);
                    createWifiTable.append(VALUE_COLUMN_WIFI_STATE_AND_DATE[i]);
                    break;
                case KEY_WIFI_STATE_AND_DATE_STATE:
                    createWifiTable.append(COLUMN_TABLE_WIFI_STATE_AND_DATE[i]);
                    createWifiTable.append(VALUE_COLUMN_WIFI_STATE_AND_DATE[i]);
                    break;
                case KEY_WIFI_STATE_AND_DATE_DATE:
                    createWifiTable.append(COLUMN_TABLE_WIFI_STATE_AND_DATE[i]);
                    createWifiTable.append(VALUE_COLUMN_WIFI_STATE_AND_DATE[i]);
                    break;
                case KEY_WIFI_STATE_AND_DATE_RSSI:
                    createWifiTable.append(COLUMN_TABLE_WIFI_STATE_AND_DATE[i]);
                    createWifiTable.append(VALUE_COLUMN_WIFI_STATE_AND_DATE[i]);
                    break;
                case KEY_WIFI_STATE_AND_DATE_LINK_SPEED:
                    createWifiTable.append(COLUMN_TABLE_WIFI_STATE_AND_DATE[i]);
                    createWifiTable.append(VALUE_COLUMN_WIFI_STATE_AND_DATE[i]);
                    break;
                case KEY_WIFI_STATE_AND_DATE_IP_ADDRESS:
                    createWifiTable.append(COLUMN_TABLE_WIFI_STATE_AND_DATE[i]);
                    createWifiTable.append(VALUE_COLUMN_WIFI_STATE_AND_DATE[i]);
                    break;
                default:
                    break;
            }
        }
        return createWifiTable.toString();
    }


    // : 6/17/2016 test name of create table
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createWifiHotspotInfoTable());
        db.execSQL(createWifiStateAndDate());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WIFI_HOTSPOT_INFO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WIFI_STATE_AND_DATE);
        onCreate(db);
    }

    @Override
    public void getWifiHistoryCursor(SQLiteDatabase db, Fragment fragment) {
        new GetWifiFromDbTask(db, fragment).execute();
    }

    @Override
    public void getWifiStateAndDateCursor(SQLiteDatabase db, Fragment frag, WifiLocationModel wifiScanWifiModel) {
        new GetWifiStateAndDateFromDbTask(db, frag, wifiScanWifiModel).execute();
    }

    // get wifi hotspot to show on map
    @Override
    public void onGetWifiHotspot(SQLiteDatabase db, Fragment fragment) {
        new GetWifiHotspotFromDbTask(db, fragment).execute();
    }


    // add wifi location to db
    @Override
    public void addWifiLocation(SQLiteDatabase db, WifiLocationModel wifiLocationModel) {
        new AddWifiLocationToDbTask(db, wifiLocationModel).execute();
    }

    // add wifi state and date to db
    @Override
    public void addWifiStateAndDate(SQLiteDatabase db, WifiStateAndDateModel wifiStateAndDateModel) {
        new AddWifiStateAndDateToDbTask(db, wifiStateAndDateModel).execute();
    }

    // update wifi record in db
    @Override
    public void editWifiHotspot(SQLiteDatabase db, WifiLocationModel wifiScanWifiModel) {
        new UpdateWifiInDbTask(db, wifiScanWifiModel).execute();
    }
}
