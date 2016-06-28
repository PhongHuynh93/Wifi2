package dhbk.android.wifi2.fragments.historyFragments;


import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dhbk.android.wifi2.R;
import dhbk.android.wifi2.adapters.historyAdapters.HistoryWifiRecyclerViewAdapter;
import dhbk.android.wifi2.utils.DividerItemDecoration;

/*
get wifi data trong db and populate to recyclerview
 */
public class HistoryWifiFragment extends HistoryBaseFragment {
    private static final String TAG = HistoryWifiFragment.class.getSimpleName();

    public HistoryWifiFragment() {
    }

    public static HistoryWifiFragment newInstance() {
        return new HistoryWifiFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_history_child_wifi, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        HistoryWifiRecyclerViewAdapter adapter = new HistoryWifiRecyclerViewAdapter(getActivity(), null);
        RecyclerView mRcvHistory = (RecyclerView) getActivity().findViewById(R.id.rcv_history);
        mRcvHistory.setAdapter(adapter);
        mRcvHistory.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRcvHistory.setHasFixedSize(true);
        // add horizontal white divider between each row
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST);
        mRcvHistory.addItemDecoration(itemDecoration);

        // : 6/15/2016 get wifi data from database (id, state, ssid, date)
        Fragment parentFragment = getParentFragment();
        if (parentFragment instanceof HistoryWifiMobileFragment) {
            ((HistoryWifiMobileFragment) parentFragment).callPresenterToGetWifiDataFromDb();
        }
    }

    // TODO: 6/28/2016 error make null recyclerview
    //    a callback from presenter contain wifi data
    public void onPopulateWifiCursorToRcv(final Cursor cursor) {
        try {
            RecyclerView historyRcv = (RecyclerView) getActivity().findViewById(R.id.rcv_history);
            HistoryWifiRecyclerViewAdapter adapter = new HistoryWifiRecyclerViewAdapter(getActivity(), cursor);
            historyRcv.setAdapter(adapter);
        } catch (NullPointerException e) {
            Log.e(TAG, "populateCursorToRcv: " + e.toString());
        }
    }
}
