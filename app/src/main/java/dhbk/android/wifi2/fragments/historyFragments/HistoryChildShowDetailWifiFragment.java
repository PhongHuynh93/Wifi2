package dhbk.android.wifi2.fragments.historyFragments;


import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.format.Formatter;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.osmdroid.views.MapView;

import butterknife.BindView;
import butterknife.ButterKnife;
import dhbk.android.wifi2.R;
import dhbk.android.wifi2.interfaces.OnRevealAnimationListener;
import dhbk.android.wifi2.models.WifiModel;
import dhbk.android.wifi2.utils.Constant;
import dhbk.android.wifi2.utils.GUIUtils;
import dhbk.android.wifi2.utils.HelpUtils;

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

    @BindView(R.id.fab_wifi_show_detail)
    FloatingActionButton mFabWifiShowDetail;
    @BindView(R.id.container_wifi_show_detail)
    CoordinatorLayout mContainerWifiShowDetail;
    @BindView(R.id.main_content_show_wifi_detail)
    LinearLayout mMainContentShowWifiDetail;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.bssid)
    TextView mBssid;
    @BindView(R.id.rssi)
    TextView mRssi;
    @BindView(R.id.mac_address)
    TextView mMacAddress;
    @BindView(R.id.ip_address)
    TextView mIpAddress;
    @BindView(R.id.linkspeed)
    TextView mLinkspeed;
    @BindView(R.id.networkid)
    TextView mNetworkid;
    @BindView(R.id.date)
    TextView mDate;
    @BindView(R.id.state)
    TextView mState;
    @BindView(R.id.map)
    MapView mMap;
    @BindView(R.id.ssid)
    TextView mSsid;
    @BindView(R.id.btn_show_wifi_on_map)
    Button mBtnShowWifiOnMap;
    @BindView(R.id.tv_show_wifi_info)
    TextView mTvShowWifiInfo;

    private WifiModel mWifiModel;

    public HistoryChildShowDetailWifiFragment() {
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

        // if android version < Lolipop, show layout normal
        // if not, make a animation, when it ends, show layout.
        if (!(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)) {
            initViews();
        }

        // declare toolbar
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true);

        // change attributes of toolbar
        mToolbar.setTitle("Wifi Details");
        mToolbar.setTitleTextColor(ContextCompat.getColor(getActivity(), R.color.black));
        mToolbar.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.grey_light));

        // set placehold with long height for toolbar
        AppBarLayout.LayoutParams layoutParams = (AppBarLayout.LayoutParams) mToolbar.getLayoutParams();
        layoutParams.height = 200;
        mToolbar.setLayoutParams(layoutParams);

        // : 6/27/2016 change up button on toolbar from white to black
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            // because if Androdi M, this drawable is changed with new name "abc_ic_ab_back_material"
            final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
            upArrow.setColorFilter(getResources().getColor(R.color.grey_dark), PorterDuff.Mode.SRC_ATOP);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(upArrow);
        }
        return view;
    }

    // when press back button, close this fragment
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                closeFragment();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // make a circular reveal animation
    public void animateRevealShow() {
        // get the center point which the fab move to it.
        int cx = (mContainerWifiShowDetail.getLeft() + mContainerWifiShowDetail.getRight()) / 2;
        int cy = (mContainerWifiShowDetail.getTop() + mContainerWifiShowDetail.getBottom()) / 2;

        // circular with accent color
        GUIUtils.animateRevealShow(getContext(), mContainerWifiShowDetail, mFabWifiShowDetail.getWidth() / 2, R.color.colorAccent,
                cx, cy, new OnRevealAnimationListener() {
                    @Override
                    public void onRevealHide() {
                    }

                    // : 6/12/16 12  When the animation has ended, we are informing the listener to fade the views in.
                    @Override
                    public void onRevealShow() {
                        initViews();
                    }
                });
    }

    // call anim when close this fragment
    public void showAnimToClose() {
        GUIUtils.animateRevealHide(getContext(), mContainerWifiShowDetail, R.color.white, mFabWifiShowDetail.getWidth() / 2,
                new OnRevealAnimationListener() {
                    // : 6/12/16 16  When the animation ends, we have to inform the Activity with the OnRevealAnimationListener to call super.onBackPressed().
                    @Override
                    public void onRevealHide() {
                        backPressed();
                    }

                    @Override
                    public void onRevealShow() {

                    }
                });
    }

    //    make sure view run on ui thread
    private void initViews() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Animation animation = AnimationUtils.loadAnimation(getContext().getApplicationContext(), android.R.anim.fade_in);
                animation.setDuration(300);

                mMainContentShowWifiDetail.startAnimation(animation);
                mMainContentShowWifiDetail.setVisibility(View.VISIBLE);
                setHeightForToolbarToDefault();

//                set the text of textview in this layout
                setWifiInfo();
                setDateStateWifi();
            }
        });
    }

    // when click up button, if version >= 5, show anim when close
    // TODO: 6/28/2016 fix when press back button, fix anim when close fragment
    public void closeFragment() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            showAnimToClose();
        } else {
            backPressed();
        }
    }

    // remove this fragment out of child manager fragment
    private void backPressed() {
        Fragment fragment = getParentFragment();
        if (fragment instanceof HistoryPresenterFragment) {
            ((HistoryPresenterFragment)fragment).popHistoryShowDetailWifiFragment();
        }

    }

    // set height for toolbar to default height
    private void setHeightForToolbarToDefault() {
        AppBarLayout.LayoutParams layoutParams = (AppBarLayout.LayoutParams) mToolbar.getLayoutParams();
        TypedValue tv = new TypedValue();
        getActivity().getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true);
        int actionBarHeight = getResources().getDimensionPixelSize(tv.resourceId);
        layoutParams.height = actionBarHeight;
        mToolbar.setLayoutParams(layoutParams);
    }

    // TODO: 6/27/2016 where are many same template here, move this method to parent class
    // change font, style of Wifi info textview
    private void setWifiInfo() {
        // change font
        Typeface font_quicksand_regular = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Quicksand-Regular.otf");
        Typeface font_quicksand_light = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Quicksand-Light.otf");
        Typeface font_quicksand_bold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Quicksand-Bold.otf");

        mBtnShowWifiOnMap.setTypeface(font_quicksand_regular);
        mTvShowWifiInfo.setTypeface(font_quicksand_light);

//        wifi info
        String ssid = HelpUtils.removeChar(mWifiModel.getSsid(), "\"");
        mSsid.setTypeface(font_quicksand_bold);
        mSsid.setText(ssid);

        String bssid = "<b>" + "Bssid: " + "</b> " + mWifiModel.getBssid();
        mBssid.setTypeface(font_quicksand_light);
        mBssid.setText(Html.fromHtml(bssid));

        String macAddress = "<b>" + "Mac add: " + "</b> " + mWifiModel.getMacAddress();
        mMacAddress.setTypeface(font_quicksand_light);
        mMacAddress.setText(Html.fromHtml(macAddress));

        String ipAddress = Formatter.formatIpAddress(mWifiModel.getIpAddress());
        ipAddress = "<b>" + "Ip add: " + "</b> " + ipAddress;
        mIpAddress.setTypeface(font_quicksand_light);
        mIpAddress.setText(Html.fromHtml(ipAddress));

        String linkSpeed = "<b>" + "Link speed: " + "</b> " + mWifiModel.getLinkSpeed() + " " + WifiInfo.LINK_SPEED_UNITS;
        mLinkspeed.setTypeface(font_quicksand_light);
        mLinkspeed.setText(Html.fromHtml(linkSpeed));

        String networkId = "<b>" + "Network id: " + "</b> " + mWifiModel.getNetworkId();
        mNetworkid.setTypeface(font_quicksand_light);
        mNetworkid.setText(Html.fromHtml(networkId));

        // : 6/27/2016 set text and change color depend on wifi signal
        int wifiLevel = WifiManager.calculateSignalLevel(mWifiModel.getRssi(), 5);
        switch (wifiLevel) {
            case 4:
                mRssi.setText("Wifi Signal: Excellent");
                break;
            case 3:
                mRssi.setText("Wifi Signal: Good");
                mRssi.getBackground().setColorFilter(ContextCompat.getColor(getActivity(), R.color.good), PorterDuff.Mode.SRC_ATOP); // White Tint
                break;
            case 2:
                mRssi.setText("Wifi Signal: Fair");
                mRssi.getBackground().setColorFilter(ContextCompat.getColor(getActivity(), R.color.fair), PorterDuff.Mode.SRC_ATOP); // White Tint
                break;
            case 1:
            case 0:
                mRssi.setText("Wifi Signal: Weak");
                mRssi.getBackground().setColorFilter(ContextCompat.getColor(getActivity(), R.color.weak), PorterDuff.Mode.SRC_ATOP); // White Tint
                break;
            default:
                break;
        }
    }

    // set data and change background color depend on state in db.
    private void setDateStateWifi() {
//        set date
        mDate.setText(mWifiModel.getDate());
//        set state
        String state = mWifiModel.getState();
        mState.setText(state);
//        when disconnect, change background tint color
        if (state.equals(Constant.WIFI_DISCONNECT)) {
            mState.setBackgroundResource(R.drawable.bg_view);
            mState.getBackground().setColorFilter(ContextCompat.getColor(getActivity(), R.color.dark_orange), PorterDuff.Mode.SRC_ATOP);
        }
    }

}
