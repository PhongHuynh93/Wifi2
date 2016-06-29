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
import dhbk.android.wifi2.adapters.historyAdapters.HistoryMobileRecyclerViewAdapter;
import dhbk.android.wifi2.utils.Constant;

// contains a recyclerview to show a list of wifi history that a user has connected or disconnected
public class HistoryMobileFragment extends HistoryBaseFragment {
    private static final String TAG = HistoryMobileFragment.class.getSimpleName();
    @BindView(R.id.rcv_history_mobile)
    RecyclerView mRcvHistoryMobile;

    public HistoryMobileFragment() {
    }

    public static HistoryMobileFragment newInstance() {
        return new HistoryMobileFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history_child_mobile, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRcvHistoryMobile = (RecyclerView) view.findViewById(R.id.rcv_history_mobile);
        HistoryMobileRecyclerViewAdapter adapter = new HistoryMobileRecyclerViewAdapter(getActivity(), null);

        declareRcvAndGetDataFromDb(mRcvHistoryMobile, adapter, Constant.MOBILE_RECYCLERVIEW);
    }

    // populate data from db to recyclerview
    public void onPopulateMobileCursorToRcv(Cursor cursor) {
        try {
            HistoryMobileRecyclerViewAdapter adapter = (HistoryMobileRecyclerViewAdapter) mRcvHistoryMobile.getAdapter();
            adapter.changeCursor(cursor);
        } catch (NullPointerException e) {
            Log.e(TAG, "populateCursorToRcv: " + e.toString());
        }
    }
}