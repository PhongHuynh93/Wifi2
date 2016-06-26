package dhbk.android.wifi2.fragments.historyFragments;


import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dhbk.android.wifi2.R;
import dhbk.android.wifi2.interfaces.OnFragInteractionListener;
import dhbk.android.wifi2.interfaces.OnRevealAnimationListener;
import dhbk.android.wifi2.models.WifiModel;
import dhbk.android.wifi2.utils.GUIUtils;

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
    @BindView(R.id.action_close)
    ImageView mActionClose;
    @BindView(R.id.fab_wifi_show_detail)
    FloatingActionButton mFabWifiShowDetail;
    @BindView(R.id.container_wifi_show_detail)
    CoordinatorLayout mContainerWifiShowDetail;
    @BindView(R.id.main_content_show_wifi_detail)
    LinearLayout mMainContentShowWifiDetail;

    private WifiModel mWifiModel;
    private OnFragInteractionListener.OnHistoryShowWifiDetailFragInteractionListener mListener;


    public HistoryChildShowDetailWifiFragment() {
        // Required empty public constructor
    }

    public static HistoryChildShowDetailWifiFragment newInstance(WifiModel wifiModel) {
        HistoryChildShowDetailWifiFragment fragment = new HistoryChildShowDetailWifiFragment();
        Bundle args = new Bundle();
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
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragInteractionListener.OnHistoryShowWifiDetailFragInteractionListener) {
            mListener = (OnFragInteractionListener.OnHistoryShowWifiDetailFragInteractionListener) context;
        } else {
            throw new ClassCastException(context.toString() + " must implement OnRageComicSelected.");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
        View view = inflater.inflate(R.layout.fragment_history_child_show_detail_wifi, container, false);
        ButterKnife.bind(this, view);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            setupEnterAnimation();
        } else {
            initViews();
        }
        return view;
    }

    // : 6/25/16 set visible to the rootlayout
    public void animateRevealShow() {
        int cx = (mContainerWifiShowDetail.getLeft() + mContainerWifiShowDetail.getRight()) / 2;
        int cy = (mContainerWifiShowDetail.getTop() + mContainerWifiShowDetail.getBottom()) / 2;

        GUIUtils.animateRevealShow(getActivity().getApplicationContext(), mContainerWifiShowDetail, mFabWifiShowDetail.getWidth() / 2, R.color.colorAccent,
                cx, cy, new OnRevealAnimationListener() {
                    @Override
                    public void onRevealHide() {

                    }

                    // TODO: 6/12/16 12  When the animation has ended, we are informing the listener to fade the views in.
                    @Override
                    public void onRevealShow() {
                        initViews();
                    }
                });
    }

    private void initViews() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Animation animation = AnimationUtils.loadAnimation(getContext().getApplicationContext(), android.R.anim.fade_in);
                animation.setDuration(300);

                // The mLLContainer and mIvClose are the LinearLayout with icons and ImageView with close action icon.
                mMainContentShowWifiDetail.startAnimation(animation);
                mActionClose.startAnimation(animation);
                mMainContentShowWifiDetail.setVisibility(View.VISIBLE);
                mActionClose.setVisibility(View.VISIBLE);
            }
        });

    }


    // call anim when close this fragment
    public void showAnimToClose() {
        GUIUtils.animateRevealHide(getContext(), mContainerWifiShowDetail, R.color.colorAccent, mFabWifiShowDetail.getWidth() / 2,
                new OnRevealAnimationListener() {
                    // TODO: 6/12/16 16  When the animation ends, we have to inform the Activity with the OnRevealAnimationListener to call super.onBackPressed().
                    @Override
                    public void onRevealHide() {
                        backPressed();
                    }

                    @Override
                    public void onRevealShow() {

                    }
                });
    }

    private void backPressed() {
        mListener.callSuperBackPress();
    }

    @OnClick(R.id.action_close)
    public void onClick() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            showAnimToClose();
        } else {
            backPressed();
        }
    }
}
