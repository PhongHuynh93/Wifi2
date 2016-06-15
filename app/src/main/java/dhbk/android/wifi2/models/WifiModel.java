package dhbk.android.wifi2.models;

import android.database.Cursor;

/**
 * Created by phongdth.ky on 6/14/2016.
 */
public class WifiModel {
    private String state;
    private String ssid;
    private String encryption;
    private String date;

    public WifiModel(String state, String ssid, String date) {
        this.state = state;
        this.ssid = ssid;
        this.date = date;
    }

    public WifiModel(String ssid, String encryption) {
        this.ssid = ssid;
        this.encryption = encryption;
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
        return new WifiModel(state, ssid, date);
    }
}
