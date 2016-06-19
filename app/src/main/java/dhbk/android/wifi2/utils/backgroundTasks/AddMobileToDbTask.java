package dhbk.android.wifi2.utils.backgroundTasks;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;

import dhbk.android.wifi2.models.MobileModel;
import dhbk.android.wifi2.utils.db.NetworkMobileDb;

/**
 * Created by huynhducthanhphong on 6/19/16.
 */
public class AddMobileToDbTask extends AsyncTask<MobileModel, Void, Boolean> {
    private final SQLiteDatabase mDb;

    public AddMobileToDbTask(SQLiteDatabase db) {
        mDb = db;
    }

    @Override
    protected Boolean doInBackground(MobileModel... params) {
        if (params.length != 1) {
            return false;
        }

        MobileModel mobileModel = params[0];
        String name = mobileModel.getNetworkName();
        String speed = mobileModel.getSpeed();
        String date = mobileModel.getDate();

        mDb.beginTransaction();
        try {
            ContentValues mobileHotspotValues = new ContentValues();
            mobileHotspotValues.put(NetworkMobileDb.KEY_MOBILE_NAME, name);
            mobileHotspotValues.put(NetworkMobileDb.KEY_MOBILE_SPEED, speed);
            mobileHotspotValues.put(NetworkMobileDb.KEY_MOBILE_DATE, date);

            mDb.insertOrThrow(NetworkMobileDb.TABLE_MOBILE, null, mobileHotspotValues);
            mDb.setTransactionSuccessful();

        } catch (SQLiteException e) {
            return false;
        } finally {
            mDb.endTransaction();
        }
        return true;
    }


}

