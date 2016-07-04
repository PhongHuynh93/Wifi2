package dhbk.android.wifi2.models;

import android.database.Cursor;

/**
 * Created by huynhducthanhphong on 6/19/16.
 */
public class MobileModel {

    private String mobileType;
    private String generation;
    private String speedText;
    private String nowDate;
    private String mobileState;

    public MobileModel(String mobileType, String generation, String speedText, String nowDate, String mobileState) {
        this.mobileType = mobileType;
        this.generation = generation;
        this.speedText = speedText;
        this.nowDate = nowDate;
        this.mobileState = mobileState;
    }

    public static MobileModel fromCursor(Cursor cursor) {
        // ko lay 0 ly do 0 là cột id_
        String name = cursor.getString(1);
        String speed = cursor.getString(2);
        String date = cursor.getString(3);
        return new MobileModel(name, speed, date);
    }
}
