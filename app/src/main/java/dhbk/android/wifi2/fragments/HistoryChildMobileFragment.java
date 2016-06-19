package dhbk.android.wifi2.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dhbk.android.wifi2.R;

public class HistoryChildMobileFragment extends Fragment {

    public HistoryChildMobileFragment() {
    }

    public static HistoryChildMobileFragment newInstance() {
        HistoryChildMobileFragment fragment = new HistoryChildMobileFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_history_child_mobile, container, false);
    }

}
