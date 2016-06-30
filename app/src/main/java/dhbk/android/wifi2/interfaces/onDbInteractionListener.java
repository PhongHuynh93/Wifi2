package dhbk.android.wifi2.interfaces;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;

import dhbk.android.wifi2.models.MobileModel;
import dhbk.android.wifi2.models.WifiHotsPotModel;
import dhbk.android.wifi2.models.WifiModel;

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
        void onInsert(SQLiteDatabase db, WifiModel wifiModel);
        void getWifiHistoryCursor(SQLiteDatabase db, Fragment fragment);
        void onInsertWifiLocation(SQLiteDatabase db, WifiHotsPotModel wifiHotsPotModel);
        void onGetWifiHotspot(SQLiteDatabase db, Context activityContext);

        // new method
        void addWifiInfo(SQLiteDatabase db, WifiModel wifiInfoModel);
        void addWifiLocation(SQLiteDatabase db, WifiModel wifiLocationModel);
        void addWifiStateAndDate(SQLiteDatabase db, WifiModel wifiStateAndDateModel);
    }

    // method table mobile
    interface onDbMobileTableInteractionListener extends onDbTableInteractionListener{
        void onInsert(SQLiteDatabase db, MobileModel model);
        void getMobileHistoryCursor(SQLiteDatabase readableDatabase, Fragment fragment);
    }
}
