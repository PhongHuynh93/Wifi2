package dhbk.android.wifi2.utils.backgroundTasks;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;

import dhbk.android.wifi2.interfaces.OnFragInteractionListener;
import dhbk.android.wifi2.utils.db.NetworkWifiDb;


/**
 * Created by phongdth.ky on 6/15/2016.
 */
public class GetWifiFromDbTask extends AsyncTask<Void, Void, Cursor>{
    private final SQLiteDatabase mDb;
    private final Fragment mFragment;

    // : 6/15/2016 get cursor from database
    public GetWifiFromDbTask(SQLiteDatabase db, Fragment fragment) {
        mDb = db;
        mFragment = fragment;
    }

    @Override
    protected Cursor doInBackground(Void... params) {
        Cursor cursor;
        try {
            cursor = mDb.query (NetworkWifiDb.TABLE_WIFI,
                    new String[] {
                            NetworkWifiDb.KEY_ID,
                            NetworkWifiDb.KEY_WIFI_STATE,
                            NetworkWifiDb.KEY_WIFI_SSID,
                            NetworkWifiDb.KEY_WIFI_DATE,


                            NetworkWifiDb.KEY_WIFI_BSSID,
                            NetworkWifiDb.KEY_WIFI_RSSI ,
                            NetworkWifiDb.KEY_WIFI_MAC_ADDRESS ,

                            NetworkWifiDb.KEY_WIFI_IP_ADDRESS,
                            NetworkWifiDb.KEY_WIFI_LINK_SPEED ,
                            NetworkWifiDb.KEY_WIFI_NETWORK_ID
                    },
                    null,
                    null,
                    null, null,null);
            // TODO: 6/25/16 test get enough
        } catch (SQLiteException e) {
            return null;
        }
        return cursor;
    }

    @Override
    protected void onPostExecute(Cursor cursor) {
        super.onPostExecute(cursor);
        if (mFragment != null) {
            if (mFragment instanceof OnFragInteractionListener.OnHistoryFragInteractionListener) {
                ((OnFragInteractionListener.OnHistoryFragInteractionListener) mFragment).populateCursorToRcv(cursor);
            }
        }

    }
}
