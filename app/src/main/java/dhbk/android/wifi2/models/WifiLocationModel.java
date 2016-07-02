package dhbk.android.wifi2.models;

/**
 * Created by huynhducthanhphong on 6/30/16.
 */
public class WifiLocationModel {
    private  double mLatitude;
    private  double mLongitude;
    private  int mIsHasLocation;
    private  String mBssid;
    private  String mSsid;
    private  String mPassword;

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
}
