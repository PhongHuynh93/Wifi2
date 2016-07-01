package dhbk.android.wifi2.models;

/**
 * Created by huynhducthanhphong on 6/30/16.
 */
public class WifiLocationModel {
    private final double mLatitude;
    private final double mLongitude;
    private final int mIsHasLocation;
    private final String mBssid;

    //    wifi with location
    public WifiLocationModel(String bssid, double latitude, double longitude, int isHasLocation) {
        this.mLatitude = latitude;
        this.mLongitude = longitude;
        this.mIsHasLocation = isHasLocation;
        this.mBssid = bssid;
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
