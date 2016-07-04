package dhbk.android.wifi2.utils.backgroundTasks;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;

import dhbk.android.wifi2.models.MobileModel;
import dhbk.android.wifi2.utils.db.NetworkMobileDb;

/**
 * Created by huynhducthanhphong on 6/19/16.
 * add mobile info to 2 table
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
        String mobileType = mobileModel.getMobileType();
        String generation = mobileModel.getGeneration();
        String speedText = mobileModel.getSpeedText();
        String nowDate = mobileModel.getNowDate();
        String mobileState = mobileModel.getMobileState();

        mDb.beginTransaction();
        try {
            // add to TABLE_MOBILE_GENERATION
            ContentValues mobileHotspotValues = new ContentValues();
            mobileHotspotValues.put(NetworkMobileDb.KEY_MOBILE_TYPE, mobileType);
            mobileHotspotValues.put(NetworkMobileDb.KEY_MOBILE_GENERATION, generation);
            mobileHotspotValues.put(NetworkMobileDb.KEY_MOBILE_SPEED, speedText);
            mobileHotspotValues.put(NetworkMobileDb.KEY_MOBILE_DATE, nowDate);
            mobileHotspotValues.put(NetworkMobileDb.KEY_MOBILE_STATE, mobileState);

            mDb.insertOrThrow(NetworkMobileDb.TABLE_MOBILE, null, mobileHotspotValues);

            // add to TABLE_MOBILE
            ContentValues mobileGenValues = new ContentValues();
            mobileGenValues.put(NetworkMobileDb.KEY_MOBILE_GENERATION, generation);
            mDb.insertOrThrow(NetworkMobileDb.TABLE_MOBILE_GENERATION, null, mobileGenValues);

            //
            mDb.setTransactionSuccessful();

        } catch (SQLiteException e) {
            return false;
        } finally {
            mDb.endTransaction();
        }
        return true;
    }


}

