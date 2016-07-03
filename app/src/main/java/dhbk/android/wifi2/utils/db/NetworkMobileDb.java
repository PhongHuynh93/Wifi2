package dhbk.android.wifi2.utils.db;

import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;

import dhbk.android.wifi2.interfaces.onDbInteractionListener;
import dhbk.android.wifi2.models.MobileModel;
import dhbk.android.wifi2.utils.backgroundTasks.AddMobileToDbTask;
import dhbk.android.wifi2.utils.backgroundTasks.GetMobileFromDbTask;

/**
 * Created by huynhducthanhphong on 6/19/16.
 */
public class NetworkMobileDb implements onDbInteractionListener.onDbMobileTableInteractionListener{

    public static final String TABLE_MOBILE = "table_mobile";
    public static final String KEY_MOBILE_ID = "_id";
    public static final String KEY_MOBILE_NAME = "key_mobile_name";
    public static final String KEY_MOBILE_SPEED = "key_mobile_speed";
    public static final String KEY_MOBILE_DATE = "key_wifi_date";


    public static final String CREATE_TABLE_MOBILE = "CREATE TABLE " + TABLE_MOBILE + "("
            + KEY_MOBILE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_MOBILE_NAME + " TEXT NOT NULL, "
            + KEY_MOBILE_SPEED + " TEXT NOT NULL, "
            + KEY_MOBILE_DATE + " TEXT NOT NULL);";


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_MOBILE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOBILE);
        onCreate(db);
    }

    @Override
    public void onInsert(SQLiteDatabase db, MobileModel model) {
        new AddMobileToDbTask(db).execute(model);
    }

    @Override
    public void getMobileHistoryCursor(SQLiteDatabase db, Fragment fragment) {
        new GetMobileFromDbTask(db, fragment).execute();
    }
}
