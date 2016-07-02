package dhbk.android.wifi2.fragments.historyFragments;


import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import dhbk.android.wifi2.R;
import dhbk.android.wifi2.adapters.historyAdapters.WifiDateAndStateRcvAdapter;
import dhbk.android.wifi2.utils.Constant;
import dhbk.android.wifi2.utils.DividerItemDecoration;

/*
A bottom sheets in ChildShowDetailWifiFragment show a list of date and state of a wifi hotspot
 */
public class BottomSheetShowWifiFragment extends BaseFragment {
    private static final String TAG = BottomSheetShowWifiFragment.class.getSimpleName();
    @BindView(R.id.rcv_show_history_wifi)
    RecyclerView mRcvShowHistoryWifi;
    private ChildShowDetailWifiFragment mParentFragment;

    public BottomSheetShowWifiFragment() {
        // Required empty public constructor
    }

    public static BottomSheetShowWifiFragment newInstance() {
        return new BottomSheetShowWifiFragment();
    }

    // call parent fragment
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Fragment parentFragment = getParentFragment();
        if (parentFragment instanceof ChildShowDetailWifiFragment) {
            mParentFragment = (ChildShowDetailWifiFragment) parentFragment;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mParentFragment = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bottom_sheet_show_history_wifi, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // declare recyclerview
        WifiDateAndStateRcvAdapter adapter = new WifiDateAndStateRcvAdapter(getContext(), null);
        declareRcvAndGetDataFromDb(mRcvShowHistoryWifi, adapter, Constant.BOTTOMSHEET_WIFI_RECYCLERVIEW);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST, R.drawable.divider);
        mRcvShowHistoryWifi.addItemDecoration(itemDecoration);
    }


    public void populateDateToRecyclerView(Cursor cursor) {
        try {
            WifiDateAndStateRcvAdapter adapter = (WifiDateAndStateRcvAdapter) mRcvShowHistoryWifi.getAdapter();
            adapter.changeCursor(cursor);
        } catch (NullPointerException e) {
            Log.e(TAG, "populateCursorToRcv: " + e.toString());
        }
    }


}
