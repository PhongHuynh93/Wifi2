package dhbk.android.wifi2.models;

/**
 * Created by phongdth.ky on 6/17/2016.
 */
public class WifiHotsPotModel {
    String networkSSID;
    String networkPass;
    double latitude;
    double longitude;
    int isTurnOnGps;

    public WifiHotsPotModel(String networkSSID, String networkPass, double latitude, double longitude, int isTurnOnGps) {
        this.networkSSID = networkSSID;
        this.networkPass = networkPass;
        this.latitude = latitude;
        this.longitude = longitude;
        this.isTurnOnGps = isTurnOnGps;
    }

    public WifiHotsPotModel(String networkSSID, String networkPass, double latitude, double longitude) {
        this.networkSSID = networkSSID;
        this.networkPass = networkPass;
        this.latitude = latitude;
        this.longitude = longitude;
    }



    public String getNetworkSSID() {
        return networkSSID;
    }

    public String getNetworkPass() {
        return networkPass;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public int isTurnOnGps() {
        return isTurnOnGps;
    }
}
