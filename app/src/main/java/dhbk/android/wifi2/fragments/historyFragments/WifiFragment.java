package dhbk.android.wifi2.fragments.historyFragments;


import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import dhbk.android.wifi2.R;
import dhbk.android.wifi2.adapters.historyAdapters.WifiInfoRcvAdapter;
import dhbk.android.wifi2.utils.Constant;

/*
// contains a recyclerview to show a list of wifi history that a user has connected or disconnected
 */
public class WifiFragment extends BaseFragment {
    private static final String TAG = WifiFragment.class.getSimpleName();
    @BindView(R.id.rcv_history)
    RecyclerView mRcvHistory;

    public WifiFragment() {
    }

    public static WifiFragment newInstance() {
        return new WifiFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history_child_wifi, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    // declare recyclerview and get data from db to show to it
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        WifiInfoRcvAdapter adapter = new WifiInfoRcvAdapter(getActivity(), null);
        declareRcvAndGetDataFromDb(mRcvHistory, adapter, Constant.WIFI_RECYCLERVIEW);
    }

    //    a callback from presenter contain wifi data
    public void onPopulateWifiCursorToRcv(final Cursor cursor) {
        try {
            WifiInfoRcvAdapter adapter = (WifiInfoRcvAdapter) mRcvHistory.getAdapter();
            adapter.changeCursor(cursor);
        } catch (NullPointerException e) {
            Log.e(TAG, "populateCursorToRcv: " + e.toString());
        }
    }
}
