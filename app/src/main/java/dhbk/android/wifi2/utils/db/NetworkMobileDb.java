package dhbk.android.wifi2.utils.db;

import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;

import dhbk.android.wifi2.interfaces.onDbInteractionListener;
import dhbk.android.wifi2.models.MobileModel;
import dhbk.android.wifi2.utils.backgroundTasks.AddMobileToDbTask;
import dhbk.android.wifi2.utils.backgroundTasks.GetMobileFromDbTask;

/**
 * Created by huynhducthanhphong on 6/19/16.
 * contain table name, column, method to control mobile table
 */
public class NetworkMobileDb implements onDbInteractionListener.onDbMobileTableInteractionListener{

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    // MOBILE GENERATION, contains only generation of mobile (such as 2G, 3G or 4G)
    public static final String TABLE_MOBILE_GENERATION = "table_mobile_gen";
    // column
    public static final String KEY_MOBILE_GENERATION_ID = "_id";
    public static final String KEY_MOBILE_GENERATION_GEN = "key_mobile_generation";
    // value
    public static final String VALUE_MOBILE_GENERATION_ID = " INTEGER PRIMARY KEY AUTOINCREMENT, ";
    public static final String VALUE_MOBILE_GENERATION_GEN = " TEXT NOT NULL UNIQUE);";
    // declare array of column and value
    public static final String[] COLUMN_TABLE_MOBILE_GENERATION = new String[] {
            KEY_MOBILE_GENERATION_ID,
            KEY_MOBILE_GENERATION_GEN
    };

    public static final String[] VALUE_TABLE_MOBILE_GENERATION = new String[] {
            VALUE_MOBILE_GENERATION_ID,
            VALUE_MOBILE_GENERATION_GEN
    };

    // method to create table
    private String createMobileGenTable() {
        StringBuilder createMobileGenTable = new StringBuilder();
        createMobileGenTable.append("CREATE TABLE ");
        createMobileGenTable.append(TABLE_MOBILE_GENERATION);
        createMobileGenTable.append("(");

        for (int i = 0; i < COLUMN_TABLE_MOBILE_GENERATION.length; i++) {
            switch (COLUMN_TABLE_MOBILE_GENERATION[i]) {
                case KEY_MOBILE_GENERATION_ID:
                    createMobileGenTable.append(COLUMN_TABLE_MOBILE_GENERATION[i]);
                    createMobileGenTable.append(VALUE_TABLE_MOBILE_GENERATION[i]);
                    break;
                case KEY_MOBILE_GENERATION_GEN:
                    createMobileGenTable.append(COLUMN_TABLE_MOBILE_GENERATION[i]);
                    createMobileGenTable.append(VALUE_TABLE_MOBILE_GENERATION[i]);
                    break;
                default:
                    break;
            }
        }
        return createMobileGenTable.toString();
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    // MOBILE STATE AND DATE
    public static final String TABLE_MOBILE = "table_mobile";
    // column
    public static final String KEY_MOBILE_ID = "_id";
    public static final String KEY_MOBILE_TYPE = "key_mobile_type";
    public static final String KEY_MOBILE_GENERATION = "key_mobile_generation";
    public static final String KEY_MOBILE_SPEED = "key_mobile_speed";
    public static final String KEY_MOBILE_DATE = "key_wifi_date";
    public static final String KEY_MOBILE_STATE = "key_wifi_state";

    // value
    public static final String VALUE_MOBILE_ID = " INTEGER PRIMARY KEY AUTOINCREMENT, ";
    public static final String VALUE_MOBILE_TYPE = " TEXT DEFAULT ' ', ";
    public static final String VALUE_MOBILE_GENERATION = " TEXT DEFAULT ' ', ";
    public static final String VALUE_MOBILE_SPEED = " TEXT DEFAULT ' ', ";
    public static final String VALUE_MOBILE_DATE = " TEXT DEFAULT ' ', ";
    public static final String VALUE_MOBILE_STATE = " TEXT DEFAULT ' ');";

    // declare array of column and value
    public static final String[] COLUMN_TABLE_MOBILE = new String[] {
            KEY_MOBILE_ID,
            KEY_MOBILE_TYPE,
            KEY_MOBILE_GENERATION,
            KEY_MOBILE_SPEED,
            KEY_MOBILE_DATE,
            KEY_MOBILE_STATE
    };

    public static final String[] VALUE_TABLE_MOBILE = new String[] {
            VALUE_MOBILE_ID,
            VALUE_MOBILE_TYPE,
            VALUE_MOBILE_GENERATION,
            VALUE_MOBILE_SPEED,
            VALUE_MOBILE_DATE,
            VALUE_MOBILE_STATE
    };

    // method to create table mobile
    // create wifi table
    private String createMobileTable() {
        StringBuilder createMobileTable = new StringBuilder();
        createMobileTable.append("CREATE TABLE ");
        createMobileTable.append(TABLE_MOBILE);
        createMobileTable.append("(");

        for (int i = 0; i < COLUMN_TABLE_MOBILE.length; i++) {
            switch (COLUMN_TABLE_MOBILE[i]) {
                case KEY_MOBILE_ID:
                    createMobileTable.append(COLUMN_TABLE_MOBILE[i]);
                    createMobileTable.append(VALUE_TABLE_MOBILE[i]);
                    break;
                case KEY_MOBILE_TYPE:
                    createMobileTable.append(COLUMN_TABLE_MOBILE[i]);
                    createMobileTable.append(VALUE_TABLE_MOBILE[i]);
                    break;
                case KEY_MOBILE_GENERATION:
                    createMobileTable.append(COLUMN_TABLE_MOBILE[i]);
                    createMobileTable.append(VALUE_TABLE_MOBILE[i]);
                    break;
                case KEY_MOBILE_SPEED:
                    createMobileTable.append(COLUMN_TABLE_MOBILE[i]);
                    createMobileTable.append(VALUE_TABLE_MOBILE[i]);
                    break;
                case KEY_MOBILE_DATE:
                    createMobileTable.append(COLUMN_TABLE_MOBILE[i]);
                    createMobileTable.append(VALUE_TABLE_MOBILE[i]);
                    break;
                case KEY_MOBILE_STATE:
                    createMobileTable.append(COLUMN_TABLE_MOBILE[i]);
                    createMobileTable.append(VALUE_TABLE_MOBILE[i]);
                    break;
                default:
                    break;
            }
        }
        return createMobileTable.toString();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createMobileTable());
        db.execSQL(createMobileGenTable());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOBILE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOBILE_GENERATION);
        onCreate(db);
    }

    // add mobile network to database
    @Override
    public void addMobileNetwork(SQLiteDatabase db, MobileModel model) {
        new AddMobileToDbTask(db).execute(model);
    }

    // get mobile network from db
    @Override
    public void getMobileHistoryCursor(SQLiteDatabase db, Fragment fragment) {
        new GetMobileFromDbTask(db, fragment).execute();
    }
}
