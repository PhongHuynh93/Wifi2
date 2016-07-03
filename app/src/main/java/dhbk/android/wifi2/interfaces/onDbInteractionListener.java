package dhbk.android.wifi2.interfaces;

import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;

import dhbk.android.wifi2.models.MobileModel;
import dhbk.android.wifi2.models.WifiLocationModel;
import dhbk.android.wifi2.models.WifiScanWifiModel;
import dhbk.android.wifi2.models.WifiStateAndDateModel;

/**
 * Created by phongdth.ky on 6/15/2016.
 */
public interface onDbInteractionListener {

    // create and upgrade table
    interface onDbTableInteractionListener {
        void onCreate(SQLiteDatabase db);
        void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion);
    }

    // method table wifi
    interface onDbWifiTableInteractionListener extends onDbTableInteractionListener{
        void getWifiHistoryCursor(SQLiteDatabase db, Fragment fragment);
        void onGetWifiHotspot(SQLiteDatabase db, Fragment fragment);
        void getWifiStateAndDateCursor(SQLiteDatabase readableDatabase, Fragment frag, WifiScanWifiModel wifiScanWifiModel);

        // new method
        void addWifiLocation(SQLiteDatabase db, WifiLocationModel wifiLocationModel);
        void addWifiStateAndDate(SQLiteDatabase db, WifiStateAndDateModel wifiStateAndDateModel);

        // update record
        void editWifiHotspot(SQLiteDatabase writableDatabase, WifiLocationModel wifiScanWifiModel);
    }

    // method table mobile
    interface onDbMobileTableInteractionListener extends onDbTableInteractionListener{
        void onInsert(SQLiteDatabase db, MobileModel model);
        void getMobileHistoryCursor(SQLiteDatabase readableDatabase, Fragment fragment);
    }
}
