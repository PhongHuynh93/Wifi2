package dhbk.android.wifi2.fragments.historyFragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dhbk.android.wifi2.R;

public class ShowMobileStateFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_GEN = "gen";

    // TODO: Rename and change types of parameters
    private String mGeneration;


    public ShowMobileStateFragment() {
    }

    public static ShowMobileStateFragment newInstance(String generation) {
        ShowMobileStateFragment fragment = new ShowMobileStateFragment();
        Bundle args = new Bundle();
        args.putString(ARG_GEN, generation);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mGeneration = getArguments().getString(ARG_GEN);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_show_mobile_state, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // TODO: 7/5/2016 use generation to get the record in column
    }
}
