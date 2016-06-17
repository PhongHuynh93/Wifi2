package dhbk.android.wifi2.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v4.app.Fragment;

import java.util.ArrayList;

import dhbk.android.wifi2.interfaces.onDbInteractionListener;
import dhbk.android.wifi2.models.WifiHotsPotModel;
import dhbk.android.wifi2.models.WifiModel;

/**
 * Created by phongdth.ky on 6/15/2016.
 */
public class NetworkDb extends SQLiteOpenHelper{
    private static final String TAG = NetworkDb.class.getSimpleName();

    private static NetworkDb sInstance;
    private static final String DATABASE_NAME = "database_network";
    private static final int DATABASE_VERSION = 1;

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

    // insert wifi hotspot properties in datbase
    public void addWifiHotspot(WifiModel wifiModel) {
        for (int i = 0; i < listTable.size(); i++) {
            onDbInteractionListener.onDbTableInteractionListener tableName = listTable.get(i);
            if (tableName instanceof onDbInteractionListener.onDbWifiTableInteractionListener) {
                ((onDbInteractionListener.onDbWifiTableInteractionListener) tableName).onInsert(getWritableDatabase(), wifiModel);
            }
        }
    }

    public void getCursor(Fragment frag) {
        for (int i = 0; i < listTable.size(); i++) {
            onDbInteractionListener.onDbTableInteractionListener tableName = listTable.get(i);
            if (tableName instanceof onDbInteractionListener.onDbWifiTableInteractionListener) {
                ((onDbInteractionListener.onDbWifiTableInteractionListener) tableName).onGetCursor(getReadableDatabase(), frag);
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
}
