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

import butterknife.BindView;
import butterknife.ButterKnife;
import dhbk.android.wifi2.R;
import dhbk.android.wifi2.adapters.historyAdapters.HistoryWifiRecyclerViewAdapter;
import dhbk.android.wifi2.utils.DividerItemDecoration;

/*
get wifi data trong db and populate to recyclerview
 */
public class HistoryWifiFragment extends HistoryBaseFragment {
    private static final String TAG = HistoryWifiFragment.class.getSimpleName();
    @BindView(R.id.rcv_history)
    RecyclerView mRcvHistory;

    public HistoryWifiFragment() {
    }

    public static HistoryWifiFragment newInstance() {
        return new HistoryWifiFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history_child_wifi, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        HistoryWifiRecyclerViewAdapter adapter = new HistoryWifiRecyclerViewAdapter(getActivity(), null);

        mRcvHistory = (RecyclerView) getActivity().findViewById(R.id.rcv_history);
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

    //    a callback from presenter contain wifi data, so populate to recyclerview
    public void onPopulateWifiCursorToRcv(final Cursor cursor) {
        try {
            HistoryWifiRecyclerViewAdapter adapter = (HistoryWifiRecyclerViewAdapter) mRcvHistory.getAdapter();
            adapter.changeCursor(cursor);
        } catch (NullPointerException e) {
            Log.e(TAG, "populateCursorToRcv: " + e.toString());
        }
    }

}
