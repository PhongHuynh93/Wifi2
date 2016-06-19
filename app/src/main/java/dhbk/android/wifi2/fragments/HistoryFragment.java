package dhbk.android.wifi2.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dhbk.android.wifi2.R;
import dhbk.android.wifi2.adapters.HistoryPagerAdapter;

public class HistoryFragment extends Fragment {
    private static final String TAG = HistoryFragment.class.getSimpleName();

    public HistoryFragment() {
    }

    public static HistoryFragment newInstance() {
        return new HistoryFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_history, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //  toolbar
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);
        toolbar.setTitle("History");

        // TODO view pager
        // sử dụng viewpager kết hợp với tablayout
        HistoryPagerAdapter historyPagerAdapter = new HistoryPagerAdapter(getChildFragmentManager());

        ViewPager viewPager = (ViewPager) view.findViewById(R.id.container);
        viewPager.setAdapter(historyPagerAdapter);

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

    }


}
