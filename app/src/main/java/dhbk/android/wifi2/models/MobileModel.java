package dhbk.android.wifi2.models;

import android.database.Cursor;

/**
 * Created by huynhducthanhphong on 6/19/16.
 */
public class MobileModel {
    private String state;
    private String networkName;
    private String speed;
    private String date;

    public MobileModel(String networkName, String speed, String date) {
        this.networkName = networkName;
        this.speed = speed;
        this.date = date;
    }

    public MobileModel(String networkName, String speed, String date, String state) {
        this.networkName = networkName;
        this.speed = speed;
        this.date = date;
        this.state = state;
    }

    public String getState() {
        return state;
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

    public static MobileModel fromCursor(Cursor cursor) {
        // ko lay 0 ly do 0 là cột id_
        String name = cursor.getString(1);
        String speed = cursor.getString(2);
        String date = cursor.getString(3);
        return new MobileModel(name, speed, date);
    }
}
