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
        void onHistoryWithOsmMapFragReplace();
        void onMobileFragReplace();
        void onReturnCursorWifiHotspot(Cursor cursor); // cursor containing wifi hotspot in db
        void onMainFragReplace();
    }

    interface OnTeleFragInteractionListener {
        void onTeleFragReplace();
    }

    // interface for wifi history
    interface OnHistoryFragInteractionListener {
        void onGetWifiHistoryCursor(Cursor cursor);
    }

    // interface for wifi history
    interface OnHistoryMobileFragInteractionListener {
        void populateCursorToRcv(Cursor cursor);
    }

    // interface for wifi presenter history fragment
    interface OnHistoryPresenterFragListener {
        void popHistoryChildShowDetailWifiFragment();
    }

}
