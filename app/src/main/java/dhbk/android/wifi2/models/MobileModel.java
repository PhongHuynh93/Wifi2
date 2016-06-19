package dhbk.android.wifi2.models;

/**
 * Created by huynhducthanhphong on 6/19/16.
 */
public class MobileModel {
    private String networkName;
    private String speed;
    private String date;

    public MobileModel(String networkName, String speed, String date) {
        this.networkName = networkName;
        this.speed = speed;
        this.date = date;
    }

    public String getNetworkName() {
        return networkName;
    }

    public String getSpeed() {
        return speed;
    }

    public String getDate() {
        return date;
    }
}
