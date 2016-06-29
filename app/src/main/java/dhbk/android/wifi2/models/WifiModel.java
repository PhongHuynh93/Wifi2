package dhbk.android.wifi2.models;

import android.database.Cursor;

/**
 * Created by phongdth.ky on 6/14/2016.
 */
public class WifiModel {

    private int mIsHasLocation;
    private double mLongitude;
    private double mLatitude;
    private String state;
    private String ssid;
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
        this.ssid = ssid;
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
        this.ssid = ssid;
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
        this.ssid = ssid;
        this.encryption = encryption;
    }

    public WifiModel(String mSsid, String mBssid, String mMacAddress, int mNetworkId) {
        this.ssid = mSsid;
        this.mBssid = mBssid;
        this.mMacAddress = mMacAddress;
        this.mNetworkId = mNetworkId;
    }

    public WifiModel(double latitude, double longitude, int isHasLocation) {
        this.mLatitude = latitude;
        this.mLongitude = longitude;
        this.mIsHasLocation = isHasLocation;
    }

    public WifiModel(int mLinkSpeed, int mRssi, String nowDate, String state) {
        this.mLinkSpeed = mLinkSpeed;
        this.mRssi = mRssi;
        this.date = nowDate;
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public String getDate() {
        return date;
    }

    public String getSsid() {
        return ssid;
    }

    public String getEncryption() {
        return encryption;
    }

    public static WifiModel fromCursor(Cursor cursor) {
        // ko lay 0 ly do 0 là cột id_
        String state = cursor.getString(1);
        String ssid = cursor.getString(2);
        String date = cursor.getString(3);
        String bssid = cursor.getString(4);
        int rssi = cursor.getInt(5);
        String macAddress = cursor.getString(6);
        int ipAddress = cursor.getInt(7);
        int linkSpeed = cursor.getInt(8);
        int networkId = cursor.getInt(9);
        return new WifiModel(state, ssid, date, bssid, rssi, macAddress, ipAddress, linkSpeed, networkId);
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
