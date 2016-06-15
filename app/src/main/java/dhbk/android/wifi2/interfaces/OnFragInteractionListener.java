package dhbk.android.wifi2.interfaces;

import android.database.Cursor;

/**
 * Created by phongdth.ky on 6/13/2016.
 */
public interface OnFragInteractionListener {

    // replace top fragment with this fragment
    interface OnMainFragInteractionListener {
        void onWifiFragReplace();
        void onHistoryFragReplace();
    }

    interface OnTeleFragInteractionListener {
        void onTeleFragReplace();
    }

    interface OnHistoryFragInteractionListener {
        void populateCursorToRcv(Cursor cursor);
    }

}
