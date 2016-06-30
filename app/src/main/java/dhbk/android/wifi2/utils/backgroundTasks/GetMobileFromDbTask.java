package dhbk.android.wifi2.utils.backgroundTasks;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;

import dhbk.android.wifi2.interfaces.OnFragInteractionListener;
import dhbk.android.wifi2.utils.db.NetworkMobileDb;

/**
 * Created by huynhducthanhphong on 6/19/16.
 */
public class GetMobileFromDbTask  extends AsyncTask<Void, Void, Cursor> {
    private final SQLiteDatabase mDb;
    private final Fragment mFragment;

    // : 6/15/2016 get cursor from database
    public GetMobileFromDbTask(SQLiteDatabase db, Fragment fragment) {
        mDb = db;
        mFragment = fragment;
    }

    @Override
    protected Cursor doInBackground(Void... params) {
        Cursor cursor;
        try {
            cursor = mDb.query (NetworkMobileDb.TABLE_MOBILE,
                    new String[] {NetworkMobileDb.KEY_MOBILE_ID, NetworkMobileDb.KEY_MOBILE_NAME, NetworkMobileDb.KEY_MOBILE_SPEED, NetworkMobileDb.KEY_MOBILE_DATE},
                    null,
                    null,
                    null, null,null);
        } catch (SQLiteException e) {
            return null;
        }
        return cursor;
    }

    @Override
    protected void onPostExecute(Cursor cursor) {
        super.onPostExecute(cursor);
        if (mFragment != null) {
            if (mFragment instanceof OnFragInteractionListener.OnHistoryFragInteractionListener) {
                ((OnFragInteractionListener.OnHistoryFragInteractionListener) mFragment).onGetMobileHistoryCursor(cursor);
            }
        }

    }
}
