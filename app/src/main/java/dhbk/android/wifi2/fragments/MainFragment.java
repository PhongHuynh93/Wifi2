package dhbk.android.wifi2.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
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
    @BindView(R.id.button_scan_wifi)
    Button mButtonScanWifi;
    @BindView(R.id.tv_mobile)
    TextView mTvMobile;
    @BindView(R.id.tv_history)
    TextView mTvHistory;
    @BindView(R.id.button_show_map)
    Button mButtonShowMap;
    @BindView(R.id.scan_wifi)
    FrameLayout mScanWifi;
    @BindView(R.id.tv_features)
    FrameLayout mTvFeatures;
    @BindView(R.id.scan_telephony)
    LinearLayout mScanTelephony;
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
        return view;
    }

    @OnClick({R.id.button_scan_wifi, R.id.tv_mobile, R.id.tv_history, R.id.button_show_map})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_scan_wifi:
                if (mListener != null) {
                    mListener.onWifiFragReplace();
                }
                break;
            case R.id.tv_mobile:
                if (mListener != null) {
                    mListener.onMobileFragReplace();
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
