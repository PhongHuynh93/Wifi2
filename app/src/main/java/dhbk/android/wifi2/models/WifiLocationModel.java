package dhbk.android.wifi2.models;

import android.database.Cursor;

import dhbk.android.wifi2.utils.db.NetworkWifiDb;

/**
 * Created by huynhducthanhphong on 6/30/16.
 */
public class WifiLocationModel {
    private int mNetworkId;
    private String mMacAddress;
    private String mEncryption;
    private double mLatitude;
    private double mLongitude;
    private int mIsHasLocation;
    private String mBssid;
    private String mSsid;
    private String mPassword;

    public int getNetworkId() {
        return mNetworkId;
    }

    public String getMacAddress() {
        return mMacAddress;
    }

    //    wifi with location
    public WifiLocationModel(String ssid, String bssid, double latitude, double longitude, int isHasLocation) {
        this.mLatitude = latitude;

        this.mLongitude = longitude;
        this.mIsHasLocation = isHasLocation;
        this.mBssid = bssid;
        this.mSsid = ssid;
    }

    //    wifi with location with password
    public WifiLocationModel(String ssid, String bssid, String password, double latitude, double longitude, int isHasLocation) {
        this.mLatitude = latitude;
        this.mLongitude = longitude;
        this.mIsHasLocation = isHasLocation;
        this.mBssid = bssid;
        this.mSsid = ssid;
        this.mPassword = password;
    }

    //    wifi with location + password + encryption
    public WifiLocationModel(String ssid, String bssid, String password, double latitude, double longitude, int isHasLocation, String encryption) {
        this.mBssid = bssid;
        this.mSsid = ssid;
        this.mLatitude = latitude;
        this.mLongitude = longitude;
        this.mIsHasLocation = isHasLocation;

        this.mPassword = password;
        this.mEncryption = encryption;
    }

    //    wifi with location + password + encryption + mac add + network id
    public WifiLocationModel(String ssid, String bssid, String password, double latitude, double longitude, int isHasLocation, String encryption, String macaddress, int network_id) {
        this.mBssid = bssid;
        this.mSsid = ssid;
        this.mLatitude = latitude;
        this.mLongitude = longitude;
        this.mIsHasLocation = isHasLocation;

        this.mPassword = password;
        this.mEncryption = encryption;
        this.mMacAddress = macaddress;
        this.mNetworkId = network_id;
    }


    // this constructor used to show in a recyclerview

    public WifiLocationModel(String ssid, String encryption, String bssid) {
        this.mSsid = ssid;
        this.mEncryption = encryption;
        this.mBssid = bssid;
    }

    public String getEncryption() {
        return mEncryption;
    }

    public String getPassword() {
        return mPassword;
    }

    public String getSsid() {
        return mSsid;
    }

    public double getLatitude() {
        return mLatitude;
    }

    public double getLongitude() {
        return mLongitude;
    }

    public int getIsHasLocation() {
        return mIsHasLocation;
    }

    public String getBssid() {
        return mBssid;
    }


    // get wifi info from cursor
    public static WifiLocationModel fromCursor(Cursor cursor) {
        String ssid = null;
        String bssid = null;
        String mac_address = null;
        String encryption = null;
        String password = null;
        double latitude = 0;
        double longitude = 0;
        int isTurnOnGps = 0;
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
                case NetworkWifiDb.KEY_WIFI_HOTSPOT_INFO_ENCRYPTION:
                    encryption = cursor.getString(i);
                    break;
                case NetworkWifiDb.KEY_WIFI_HOTSPOT_INFO_PASSWORD:
                    password = cursor.getString(i);
                    break;
                case NetworkWifiDb.KEY_WIFI_HOTSPOT_INFO_LAT:
                    latitude = cursor.getDouble(i);
                    break;
                case NetworkWifiDb.KEY_WIFI_HOTSPOT_INFO_LONG:
                    longitude = cursor.getDouble(i);
                    break;
                case NetworkWifiDb.KEY_WIFI_HOTSPOT_INFO_ISTURNONGPS:
                    isTurnOnGps = cursor.getInt(i);
                    break;
                case NetworkWifiDb.KEY_WIFI_HOTSPOT_INFO_NETWORK_ID:
                    network_id = cursor.getInt(i);
                    break;
                default:
                    break;
            }
        }

        return new WifiLocationModel(ssid, bssid,password, latitude, longitude, isTurnOnGps, encryption, mac_address, network_id);
    }

}
