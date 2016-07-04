package dhbk.android.wifi2.interfaces;

import android.database.Cursor;

import java.util.ArrayList;

import dhbk.android.wifi2.models.WifiLocationModel;

/**
 * Created by phongdth.ky on 6/13/2016.
 */
public interface OnFragInteractionListener {

    // replace top fragment with this fragment
    interface OnMainFragInteractionListener {
        void onWifiFragReplace();
        void onHistoryFragReplace();
        void onHistoryWithOsmMapFragReplace();
        void onMainFragReplace();
    }

    // interface for WifiPresenterFragment
    interface OnWifiScanFragInteractionListener {
        void onGetDataAfterScanWifi(ArrayList<WifiLocationModel> list);
        void onAllowToSaveWifiHotspotToDb(String ssid, String pass, String encryption, String bssid);
    }

    // interface for HistoryPresenterFragment
    interface OnHistoryFragInteractionListener {
        // get wifi info
        void onGetWifiHistoryCursor(Cursor cursor);
        // get mobile network generation
        void onGetMobileHistoryCursor(Cursor cursor);
        // get wifi state and date
        void onGetWifiStateAndDateCursor(Cursor cursor);
    }

    interface OnMapFragInteractionListerer {
        void onGetWifiLocationCursor(Cursor cursor);
    }
}
