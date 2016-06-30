package dhbk.android.wifi2.utils.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import java.util.ArrayList;

import dhbk.android.wifi2.interfaces.onDbInteractionListener;
import dhbk.android.wifi2.models.MobileModel;
import dhbk.android.wifi2.models.WifiHotsPotModel;
import dhbk.android.wifi2.models.WifiLocationModel;
import dhbk.android.wifi2.models.WifiModel;

/**
 * Created by phongdth.ky on 6/15/2016.
 */
public class NetworkDb extends SQLiteOpenHelper{
    private static NetworkDb sInstance;
    private static final String DATABASE_NAME = "database_network";
    private static final int DATABASE_VERSION = 8;

    private ArrayList<onDbInteractionListener.onDbTableInteractionListener> listTable = new ArrayList<>();
    public static synchronized NetworkDb getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new NetworkDb(context.getApplicationContext());
        }
        return sInstance;
    }

    // add new table class here
    private NetworkDb(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        listTable.add(new NetworkWifiDb());
        listTable.add(new NetworkMobileDb());
    }

    // make table
    @Override
    public void onCreate(SQLiteDatabase db) {
        for (int i = 0; i < listTable.size(); i++) {
            listTable.get(i).onCreate(db);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        for (int i = 0; i < listTable.size(); i++) {
            listTable.get(i).onUpgrade(db, oldVersion, newVersion);
        }
    }

    //##########################################################################################
    // METHOD WIFI TABLE

    // insert wifi hotspot properties in datbase
    public void addWifiHotspot(WifiModel wifiModel) {
        for (int i = 0; i < listTable.size(); i++) {
            onDbInteractionListener.onDbTableInteractionListener tableName = listTable.get(i);
            if (tableName instanceof onDbInteractionListener.onDbWifiTableInteractionListener) {
                ((onDbInteractionListener.onDbWifiTableInteractionListener) tableName).onInsert(getWritableDatabase(), wifiModel);
            }
        }
    }

    public void getWifiHistory(Fragment frag) {
        for (int i = 0; i < listTable.size(); i++) {
            onDbInteractionListener.onDbTableInteractionListener tableName = listTable.get(i);
            if (tableName instanceof onDbInteractionListener.onDbWifiTableInteractionListener) {
                ((onDbInteractionListener.onDbWifiTableInteractionListener) tableName).getWifiHistoryCursor(getReadableDatabase(), frag);
            }
        }
    }

    public void getWifiStateAndDate(Fragment frag, WifiModel wifiModel) {
        for (int i = 0; i < listTable.size(); i++) {
            onDbInteractionListener.onDbTableInteractionListener tableName = listTable.get(i);
            if (tableName instanceof onDbInteractionListener.onDbWifiTableInteractionListener) {
                ((onDbInteractionListener.onDbWifiTableInteractionListener) tableName).getWifiStateAndDateCursor(getReadableDatabase(), frag, wifiModel);
            }
        }
    }

    // add wifi hotspot with location to database
    public void addWifiHotspotWithLocation(WifiHotsPotModel wifiHotsPotModel) {
        for (int i = 0; i < listTable.size(); i++) {
            onDbInteractionListener.onDbTableInteractionListener tableName = listTable.get(i);
            if (tableName instanceof onDbInteractionListener.onDbWifiTableInteractionListener) {
                ((onDbInteractionListener.onDbWifiTableInteractionListener) tableName).onInsertWifiLocation(getWritableDatabase(), wifiHotsPotModel);
            }
        }
    }

    public void getWifiHotspot(Context activityContext) {
        for (int i = 0; i < listTable.size(); i++) {
            onDbInteractionListener.onDbTableInteractionListener tableName = listTable.get(i);
            if (tableName instanceof onDbInteractionListener.onDbWifiTableInteractionListener) {
                ((onDbInteractionListener.onDbWifiTableInteractionListener) tableName).onGetWifiHotspot(getReadableDatabase(), activityContext);
            }
        }
    }

    // TODO: 6/29/16 add new method to add to new table
    public void addWifiInfoToTable(WifiModel wifiInfoModel) {
        onDbInteractionListener.onDbWifiTableInteractionListener tableName = getNetworkWifiDb();
        if (tableName != null) {
            tableName.addWifiInfo(getWritableDatabase(), wifiInfoModel);
        }
    }

    public void addWifiLocationToTable(WifiLocationModel wifiLocationModel) {
        onDbInteractionListener.onDbWifiTableInteractionListener tableName = getNetworkWifiDb();
        if (tableName != null) {
            tableName.addWifiLocation(getWritableDatabase(), wifiLocationModel);
        }
    }

    public void addStateAndDateWifiToTable(WifiModel wifiStateAndDateModel) {
        onDbInteractionListener.onDbWifiTableInteractionListener tableName = getNetworkWifiDb();
        if (tableName != null) {
            tableName.addWifiStateAndDate(getWritableDatabase(), wifiStateAndDateModel);
        }
    }

    //##########################################################################################
    // METHOD MOBILE TABLE

    public void addMobile(String mobileType, String speedText, String nowDate) {
        MobileModel mobileModel = new MobileModel(mobileType, speedText, nowDate);

        for (int i = 0; i < listTable.size(); i++) {
            onDbInteractionListener.onDbTableInteractionListener tableName = listTable.get(i);
            if (tableName instanceof onDbInteractionListener.onDbMobileTableInteractionListener) {
                ((onDbInteractionListener.onDbMobileTableInteractionListener) tableName).onInsert(getReadableDatabase(), mobileModel);
            }
        }
    }

    public void getMobileHistory(Fragment fragment) {
        for (int i = 0; i < listTable.size(); i++) {
            onDbInteractionListener.onDbTableInteractionListener tableName = listTable.get(i);
            if (tableName instanceof onDbInteractionListener.onDbMobileTableInteractionListener) {
                ((onDbInteractionListener.onDbMobileTableInteractionListener) tableName).getMobileHistoryCursor(getReadableDatabase(), fragment);
            }
        }
    }

    @Nullable
    private onDbInteractionListener.onDbWifiTableInteractionListener getNetworkWifiDb() {
        for (int i = 0; i < listTable.size(); i++) {
            onDbInteractionListener.onDbTableInteractionListener tableName = listTable.get(i);
            if (tableName instanceof onDbInteractionListener.onDbWifiTableInteractionListener) {
                return ((onDbInteractionListener.onDbWifiTableInteractionListener) tableName);
            }
        }
        return null;
    }


}
