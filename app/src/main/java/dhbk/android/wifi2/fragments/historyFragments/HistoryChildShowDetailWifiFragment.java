package dhbk.android.wifi2.fragments.historyFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dhbk.android.wifi2.R;
import dhbk.android.wifi2.models.WifiModel;

public class HistoryChildShowDetailWifiFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private static final String ARG_PARAM4 = "param4";
    private static final String ARG_PARAM5 = "param5";
    private static final String ARG_PARAM6 = "param6";
    private static final String ARG_PARAM7 = "param7";
    private static final String ARG_PARAM8 = "param8";
    private static final String ARG_PARAM9 = "param9";

    // TODO: Rename and change types of parameters
    private WifiModel mWifiModel;


    public HistoryChildShowDetailWifiFragment() {
        // Required empty public constructor
    }

    public static HistoryChildShowDetailWifiFragment newInstance(WifiModel wifiModel) {
        HistoryChildShowDetailWifiFragment fragment = new HistoryChildShowDetailWifiFragment();
        Bundle args = new Bundle();
        // TODO: 6/25/16 wifimodel to para and add to args
        String state = wifiModel.getState();
        String ssid = wifiModel.getSsid();
        String date = wifiModel.getDate();
        String mBssid = wifiModel.getBssid();
        int mRssi = wifiModel.getRssi();
        String mMacAddress = wifiModel.getMacAddress();
        int mIpAddress = wifiModel.getIpAddress();
        int mLinkSpeed = wifiModel.getLinkSpeed();
        int mNetworkId = wifiModel.getNetworkId();

        args.putString(ARG_PARAM1, state);
        args.putString(ARG_PARAM2, ssid);
        args.putString(ARG_PARAM3, date);
        args.putString(ARG_PARAM4, mBssid);
        args.putInt(ARG_PARAM5, mRssi);
        args.putString(ARG_PARAM6, mMacAddress);
        args.putInt(ARG_PARAM7, mIpAddress);
        args.putInt(ARG_PARAM8, mLinkSpeed);
        args.putInt(ARG_PARAM9, mNetworkId);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String state = getArguments().getString(ARG_PARAM1);
            String ssid = getArguments().getString(ARG_PARAM2);
            String date = getArguments().getString(ARG_PARAM3);
            String mBssid = getArguments().getString(ARG_PARAM4);
            int mRssi = getArguments().getInt(ARG_PARAM5);
            String mMacAddress = getArguments().getString(ARG_PARAM6);
            int mIpAddress = getArguments().getInt(ARG_PARAM7);
            int mLinkSpeed = getArguments().getInt(ARG_PARAM8);
            int mNetworkId = getArguments().getInt(ARG_PARAM9);

            mWifiModel = new WifiModel(state, ssid, date, mBssid, mRssi, mMacAddress, mIpAddress, mLinkSpeed, mNetworkId);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history_child_show_detail_wifi, container, false);
    }

}
