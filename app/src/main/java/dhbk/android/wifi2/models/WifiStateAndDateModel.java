package dhbk.android.wifi2.models;

import android.database.Cursor;
import android.support.annotation.Nullable;

import dhbk.android.wifi2.utils.db.NetworkWifiDb;

/**
 * Created by huynhducthanhphong on 6/30/16.
 */
public class WifiStateAndDateModel {
    private final int mLinkSpeed;
    private final int mRssi;
    private final String mDate;
    private final String mState;
    private final String mBssid;
    private final int mIpAddress;

    //    wifi state and date, this constructor uses to add new values in db, bssid is a name of table
    public WifiStateAndDateModel(String bssid, int linkSpeed, int rssi, String nowDate, String state, int ipAddress) {
        this.mLinkSpeed = linkSpeed;
        this.mRssi = rssi;
        this.mDate = nowDate;
        this.mState = state;
        this.mBssid = bssid;
        this.mIpAddress = ipAddress;
    }




    public int getLinkSpeed() {
        return mLinkSpeed;
    }

    public int getRssi() {
        return mRssi;
    }

    public String getDate() {
        return mDate;
    }

    public String getState() {
        return mState;
    }

    public String getBssid() {
        return mBssid;
    }

    public int getIpAddress() {
        return mIpAddress;
    }


    // get wifi state and date from cursor
    @Nullable
    public static WifiStateAndDateModel fromCursorWifiStateAndDate(Cursor cursor) {
        int linkSpeed = 0;
        int rssi = 0;
        String date = null;
        String state = null;
        int ipAddress = 0;

        for (int i = 0; i < NetworkWifiDb.COLUMN_TABLE_WIFI_STATE_AND_DATE.length; i++) {
            switch (NetworkWifiDb.COLUMN_TABLE_WIFI_STATE_AND_DATE[i]) {
                case NetworkWifiDb.KEY_WIFI_STATE_AND_DATE_STATE:
                    state = cursor.getString(i);
                    break;
                case NetworkWifiDb.KEY_WIFI_STATE_AND_DATE_DATE:
                    date = cursor.getString(i);
                    break;
                case NetworkWifiDb.KEY_WIFI_STATE_AND_DATE_RSSI:
                    rssi = cursor.getInt(i);
                    break;
                case NetworkWifiDb.KEY_WIFI_STATE_AND_DATE_LINK_SPEED:
                    linkSpeed = cursor.getInt(i);
                    break;
                case NetworkWifiDb.KEY_WIFI_STATE_AND_DATE_IP_ADDRESS:
                    ipAddress = cursor.getInt(i);
                    break;
                default:
                    break;
            }
        }

        // not need bssid, so make it null
        return new WifiStateAndDateModel(null, linkSpeed, rssi, date, state, ipAddress);
    }
}
