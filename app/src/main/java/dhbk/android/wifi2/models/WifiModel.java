package dhbk.android.wifi2.models;

import android.database.Cursor;
import android.support.annotation.Nullable;

import dhbk.android.wifi2.utils.db.NetworkWifiDb;

/**
 * Created by phongdth.ky on 6/14/2016.
 */
public class WifiModel {

    private static final String TAG = WifiModel.class.getSimpleName();
    private int mIsHasLocation;
    private double mLongitude;
    private double mLatitude;
    private String state;
    private String mSsid;
    private String encryption;
    private String date;

    private String mBssid;
    private int mRssi;
    private String mMacAddress;
    private int mIpAddress;
    private int mLinkSpeed;
    private int mNetworkId;

    public WifiModel(String state, String ssid, String date, String bssid, int rssi, String macAddress, int ipAddress, int linkSpeed, int networkId) {
        this.state = state;
        this.mSsid = ssid;
        this.date = date;
        mBssid = bssid;
        mRssi = rssi;
        mMacAddress = macAddress;
        mIpAddress = ipAddress;
        mLinkSpeed = linkSpeed;
        mNetworkId = networkId;
    }

    public WifiModel(String state, String ssid, String date, String bssid, int rssi, String macAddress, int ipAddress, int linkSpeed, int networkId, double latitude, double longitude, int isHasLocation) {
        this.state = state;
        this.mSsid = ssid;
        this.date = date;
        mBssid = bssid;
        mRssi = rssi;
        mMacAddress = macAddress;
        mIpAddress = ipAddress;
        mLinkSpeed = linkSpeed;
        mNetworkId = networkId;
        mLatitude = latitude;
        mLongitude = longitude;
        mIsHasLocation = isHasLocation;
    }


    public WifiModel(String ssid, String encryption) {
        this.mSsid = ssid;
        this.encryption = encryption;
    }

    //    wifi info
    public WifiModel(String mSsid, String mBssid, String mMacAddress, int mNetworkId) {
        this.mSsid = mSsid;
        this.mBssid = mBssid;
        this.mMacAddress = mMacAddress;
        this.mNetworkId = mNetworkId;
    }



    //    wifi state and date
    public WifiModel(String mSsid, int mNetworkId, int mLinkSpeed, int mRssi, String nowDate, String state, int ipAddress) {
        this.mLinkSpeed = mLinkSpeed;
        this.mRssi = mRssi;
        this.date = nowDate;
        this.state = state;
        this.mSsid = mSsid;
        this.mNetworkId = mNetworkId;
        this.mIpAddress = ipAddress;
    }

    public WifiModel(int linkSpeed, int rssi, String date, String state, int ipAddress) {
        this.mLinkSpeed = linkSpeed;
        this.mRssi = rssi;
        this.date = date;
        this.state = state;
        this.mIpAddress = ipAddress;
    }

    public String getState() {
        return state;
    }

    public String getDate() {
        return date;
    }

    public String getSsid() {
        return mSsid;
    }

    public String getEncryption() {
        return encryption;
    }

    // get wifi info from cursor
    @Nullable
    public static WifiModel fromCursor(Cursor cursor) {
        String ssid = null;
        String bssid = null;
        String mac_address = null;
        int network_id = 0;
        for (int i = 0; i < NetworkWifiDb.COLUMN_TABLE_WIFI_HOTSPOT_INFO.length; i++) {
            switch (NetworkWifiDb.COLUMN_TABLE_WIFI_HOTSPOT_INFO[i]) {
                case NetworkWifiDb.KEY_WIFI_HOTSPOT_INFO_SSID:
                    ssid = cursor.getString(i);
                    break;
                case NetworkWifiDb.KEY_WIFI_HOTSPOT_INFO_BSSID:
                    bssid = cursor.getString(i);
                    break;
                case NetworkWifiDb.KEY_WIFI_HOTSPOT_INFO_MAC_ADDRESS:
                    mac_address = cursor.getString(i);
                    break;
                case NetworkWifiDb.KEY_WIFI_HOTSPOT_INFO_NETWORK_ID:
                    network_id = cursor.getInt(i);
                    break;
                default:
                    break;
            }
        }

        if (ssid != null) {
            return new WifiModel(ssid, bssid, mac_address, network_id);
        }
        return null;
    }

    // get wifi state and date from cursor
    @Nullable
    public static WifiModel fromCursorWifiStateAndDate(Cursor cursor) {
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

        return new WifiModel(linkSpeed, rssi, date, state, ipAddress);
    }

    public String getBssid() {
        return mBssid;
    }

    public int getRssi() {
        return mRssi;
    }

    public String getMacAddress() {
        return mMacAddress;
    }

    public int getIpAddress() {
        return mIpAddress;
    }

    public int getLinkSpeed() {
        return mLinkSpeed;
    }

    public int getNetworkId() {
        return mNetworkId;
    }

    public int getIsHasLocation() {
        return mIsHasLocation;
    }

    public double getLongitude() {
        return mLongitude;
    }

    public double getLatitude() {
        return mLatitude;
    }
}
