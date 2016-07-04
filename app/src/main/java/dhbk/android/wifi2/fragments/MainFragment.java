package dhbk.android.wifi2.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dhbk.android.wifi2.R;
import dhbk.android.wifi2.interfaces.OnFragInteractionListener;

/**
 * A Main Fragment listen touch events from user.
 * COntains 4 features:
 * + scan and save pass, location of wifi
 * + show history of wifi and mobile network
 * + show a maps contain all wifi hotspot.
 */
public class MainFragment extends Fragment {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.scan_wifi)
    FrameLayout mScanWifi;
    @BindView(R.id.tv_features)
    FrameLayout mTvFeatures;
    @BindView(R.id.tv_history)
    TextView mTvHistory;
    @BindView(R.id.scan_telephony)
    FrameLayout mScanTelephony;
    @BindView(R.id.button_show_map)
    Button mButtonShowMap;
    @BindView(R.id.map_history)
    FrameLayout mMapHistory;

    private OnFragInteractionListener.OnMainFragInteractionListener mListener;

    public MainFragment() {
    }

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragInteractionListener.OnMainFragInteractionListener) {
            mListener = (OnFragInteractionListener.OnMainFragInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        setHasOptionsMenu(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Main");

        // make a hamberger in toolbar
        DrawerLayout drawerLayout = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                getActivity(), drawerLayout, mToolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close
        );
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true); //disable "hamburger to arrow" drawable
        actionBarDrawerToggle.syncState();

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @OnClick({R.id.button_scan_wifi, R.id.tv_history, R.id.button_show_map})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_scan_wifi:
                if (mListener != null) {
                    mListener.onWifiFragReplace();
                }
                break;
            case R.id.tv_history:
                if (mListener != null) {
                    mListener.onHistoryFragReplace();
                }
                break;
            case R.id.button_show_map:
                if (mListener != null) {
                    mListener.onHistoryWithOsmMapFragReplace();
                }
                break;
        }
    }
}
