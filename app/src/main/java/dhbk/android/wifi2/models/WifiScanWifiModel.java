package dhbk.android.wifi2.models;

import android.database.Cursor;
import android.support.annotation.Nullable;

import dhbk.android.wifi2.utils.db.NetworkWifiDb;

/**
 * Created by phongdth.ky on 6/14/2016.
 */
public class WifiScanWifiModel {

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

    public WifiScanWifiModel(String state, String ssid, String date, String bssid, int rssi, String macAddress, int ipAddress, int linkSpeed, int networkId) {
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

    public WifiScanWifiModel(String state, String ssid, String date, String bssid, int rssi, String macAddress, int ipAddress, int linkSpeed, int networkId, double latitude, double longitude, int isHasLocation) {
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


    //    wifi info
    public WifiScanWifiModel(String mSsid, String mBssid, String mMacAddress, int mNetworkId) {
        this.mSsid = mSsid;
        this.mBssid = mBssid;
        this.mMacAddress = mMacAddress;
        this.mNetworkId = mNetworkId;
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
    public static WifiScanWifiModel fromCursor(Cursor cursor) {
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
            return new WifiScanWifiModel(ssid, bssid, mac_address, network_id);
        }
        return null;
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

}
