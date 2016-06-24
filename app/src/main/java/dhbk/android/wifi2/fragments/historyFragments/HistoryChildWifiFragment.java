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
import dhbk.android.wifi2.interfaces.OnFragInteractionListener;
import dhbk.android.wifi2.utils.DividerItemDecoration;
import dhbk.android.wifi2.utils.db.NetworkDb;

public class HistoryChildWifiFragment extends Fragment implements OnFragInteractionListener.OnHistoryFragInteractionListener{
    private static final String TAG = HistoryChildWifiFragment.class.getSimpleName();

    public HistoryChildWifiFragment() {
        // Required empty public constructor
    }

    public static HistoryChildWifiFragment newInstance() {
        return new HistoryChildWifiFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history_child_wifi, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        HistoryWifiRecyclerViewAdapter adapter = new HistoryWifiRecyclerViewAdapter(getActivity(), null);
        RecyclerView historyRcv = (RecyclerView) getActivity().findViewById(R.id.rcv_history);
        historyRcv.setAdapter(adapter);
        historyRcv.setLayoutManager(new LinearLayoutManager(getActivity()));
        historyRcv.setHasFixedSize(true);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST);
        historyRcv.addItemDecoration(itemDecoration);

        // : 6/15/2016 get the cursor (id, state, ssid, date)
        NetworkDb networkDb = NetworkDb.getInstance(getActivity());
        networkDb.getCursor(this);

    }

    // : 6/15/2016 populate the cursor, check null when rotate the screen
    @Override
    public void populateCursorToRcv(Cursor cursor) {
        try {
            RecyclerView historyRcv = (RecyclerView) getActivity().findViewById(R.id.rcv_history);
            HistoryWifiRecyclerViewAdapter adapter = (HistoryWifiRecyclerViewAdapter) historyRcv.getAdapter();
            adapter.changeCursor(cursor);

        } catch (NullPointerException e) {
            Log.e(TAG, "populateCursorToRcv: " + e.toString());
        }
    }
}
