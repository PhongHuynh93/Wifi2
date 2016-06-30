package dhbk.android.wifi2.models;

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

    //    wifi state and date
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
}
