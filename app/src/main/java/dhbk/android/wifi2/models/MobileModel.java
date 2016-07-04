package dhbk.android.wifi2.models;

import android.database.Cursor;

import dhbk.android.wifi2.utils.db.NetworkMobileDb;

/**
 * Created by huynhducthanhphong on 6/19/16.
 */
public class MobileModel {

    private String mobileType; // such as GPRS
    private String generation; // such as 2GB
    private String speedText; // such as 100kpbs
    private String nowDate; // such as 5/6/2016
    private String mobileState; // such as connect/disconnect

    // use to store mobile details info such as state and date
    public MobileModel(String mobileType, String generation, String speedText, String nowDate, String mobileState) {
        this.mobileType = mobileType;
        this.generation = generation;
        this.speedText = speedText;
        this.nowDate = nowDate;
        this.mobileState = mobileState;
    }

    // use to store mobile generation
    public MobileModel(String generation) {
        this.generation = generation;
    }

    // get mobilemodel from cursor
    public static MobileModel fromCursor(Cursor cursor) {
        String generation = null;
        for (int i = 0; i < NetworkMobileDb.COLUMN_TABLE_MOBILE_GENERATION.length; i++) {
            switch (NetworkMobileDb.COLUMN_TABLE_MOBILE_GENERATION[i]) {
                case NetworkMobileDb.KEY_MOBILE_GENERATION_GEN:
                    generation = cursor.getString(i);
                    break;
                default:
                    break;
            }
        }

        return new MobileModel(generation);
    }

    public String getMobileType() {
        return mobileType;
    }

    public String getGeneration() {
        return generation;
    }

    public String getSpeedText() {
        return speedText;
    }

    public String getNowDate() {
        return nowDate;
    }

    public String getMobileState() {
        return mobileState;
    }
}
