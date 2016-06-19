package dhbk.android.wifi2.fragments;


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
import dhbk.android.wifi2.adapters.HistoryMobileRecyclerViewAdapter;
import dhbk.android.wifi2.interfaces.OnFragInteractionListener;
import dhbk.android.wifi2.utils.DividerItemDecoration;
import dhbk.android.wifi2.utils.NetworkDb;

public class HistoryChildMobileFragment extends Fragment implements OnFragInteractionListener.OnHistoryMobileFragInteractionListener{

    private static final String TAG = HistoryChildMobileFragment.class.getSimpleName();

    public HistoryChildMobileFragment() {
    }

    public static HistoryChildMobileFragment newInstance() {
        return new HistoryChildMobileFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_history_child_mobile, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        HistoryMobileRecyclerViewAdapter adapter = new HistoryMobileRecyclerViewAdapter(getActivity(), null);
        RecyclerView historyRcv = (RecyclerView) view.findViewById(R.id.rcv_history_mobile);
        historyRcv.setAdapter(adapter);
        historyRcv.setLayoutManager(new LinearLayoutManager(getActivity()));
        historyRcv.setHasFixedSize(true);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST);
        historyRcv.addItemDecoration(itemDecoration);

        // : 6/15/2016 get the cursor (id, state, ssid, date)
        NetworkDb networkDb = NetworkDb.getInstance(getActivity());
        networkDb.getCursorFromMobile(this);
    }

    // : 6/15/2016 populate the cursor, check null when rotate the screen
    @Override
    public void populateCursorToRcv(Cursor cursor) {
        try {
            RecyclerView historyRcv = (RecyclerView) getActivity().findViewById(R.id.rcv_history_mobile);
            HistoryMobileRecyclerViewAdapter adapter = (HistoryMobileRecyclerViewAdapter) historyRcv.getAdapter();
            adapter.changeCursor(cursor);

        } catch (NullPointerException e) {
            Log.e(TAG, "populateCursorToRcv: " + e.toString());
        }
    }

}
