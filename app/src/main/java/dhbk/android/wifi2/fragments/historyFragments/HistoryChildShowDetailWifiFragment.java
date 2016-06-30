package dhbk.android.wifi2.fragments.historyFragments;


import android.database.Cursor;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
import butterknife.OnClick;
import dhbk.android.wifi2.R;
import dhbk.android.wifi2.interfaces.OnRevealAnimationListener;
import dhbk.android.wifi2.models.WifiModel;
import dhbk.android.wifi2.utils.Constant;
import dhbk.android.wifi2.utils.GUIUtils;
import dhbk.android.wifi2.utils.HelpUtils;

public class HistoryChildShowDetailWifiFragment extends HistoryBaseFragment {
    private static final String ARG_STATE = "state";
    private static final String ARG_SSID = "ssid";
    private static final String ARG_DATE = "date";
    private static final String ARG_BSSID = "bssid";
    private static final String ARG_RSSI = "rssi";
    private static final String ARG_MAC_ADD = "mac_add";
    private static final String ARG_IP_ADD = "ip_add";
    private static final String ARG_LINK_SPEED = "link_speed";
    private static final String ARG_NETWORK_ID = "network_id";
    private static final float TOOLBAR_HEIGHT_DP = 100;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.fab_wifi_show_detail)
    FloatingActionButton mFabWifiShowDetail;
    @BindView(R.id.ssid)
    TextView mSsidTv;
    @BindView(R.id.tv_show_wifi_info)
    TextView mShowWifiInfoTv;
    @BindView(R.id.bssid)
    TextView mBssidTv;
    @BindView(R.id.mac_address)
    TextView mMacAddressTv;
    @BindView(R.id.networkid)
    TextView mNetworkidTv;
    @BindView(R.id.btn_show_wifi_on_map)
    Button mBtnShowWifiOnMap;
    @BindView(R.id.map)
    MapView mMap;
    @BindView(R.id.main_content_show_wifi_detail)
    LinearLayout mMainContentShowWifiDetail;
    @BindView(R.id.container_wifi_show_detail)
    CoordinatorLayout mContainerWifiShowDetail;

    private WifiModel mWifiModel;

    public HistoryChildShowDetailWifiFragment() {
    }

    public static HistoryChildShowDetailWifiFragment newInstance(WifiModel wifiModel) {
        HistoryChildShowDetailWifiFragment fragment = new HistoryChildShowDetailWifiFragment();
        Bundle args = new Bundle();

//        args.putString(ARG_STATE, wifiModel.getState());
        args.putString(ARG_SSID, wifiModel.getSsid());
//        args.putString(ARG_DATE, wifiModel.getDate());
        args.putString(ARG_BSSID, wifiModel.getBssid());
//        args.putInt(ARG_RSSI, wifiModel.getRssi());
        args.putString(ARG_MAC_ADD, wifiModel.getMacAddress());
//        args.putInt(ARG_IP_ADD, wifiModel.getIpAddress());
//        args.putInt(ARG_LINK_SPEED, wifiModel.getLinkSpeed());
        args.putInt(ARG_NETWORK_ID, wifiModel.getNetworkId());

        fragment.setArguments(args);
        return fragment;
    }

    // get the para from HistoryWifiFragment
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mWifiModel = new WifiModel(
//                    getArguments().getString(ARG_STATE),
                    getArguments().getString(ARG_SSID),
//                    getArguments().getString(ARG_DATE),
                    getArguments().getString(ARG_BSSID),
//                    getArguments().getInt(ARG_RSSI),
                    getArguments().getString(ARG_MAC_ADD),
//                    getArguments().getInt(ARG_IP_ADD),
//                    getArguments().getInt(ARG_LINK_SPEED),
                    getArguments().getInt(ARG_NETWORK_ID)
            );
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history_child_show_detail_wifi, container, false);
        ButterKnife.bind(this, view);

        // if android version < Lolipop, show layout normal
        // if not, make a animation, when it ends, show layout.
        if (!(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)) {
            initViews();
        }

        declareToolbar(mToolbar);

        // change attributes of toolbar
        mToolbar.setTitle("Wifi Details");
        mToolbar.setTitleTextColor(ContextCompat.getColor(getActivity(), R.color.black));
        mToolbar.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.grey_light));
        changeToolbarHeight(mToolbar, TOOLBAR_HEIGHT_DP, false);

        // : 6/27/2016 change up button n toolbar from white to black
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            // because if Androdi M, this drawable is changed with new name "abc_ic_ab_back_material"
            final Drawable upArrow = ContextCompat.getDrawable(getContext(), R.drawable.abc_ic_ab_back_mtrl_am_alpha);
            upArrow.setColorFilter(ContextCompat.getColor(getContext(), R.color.grey_dark), PorterDuff.Mode.SRC_ATOP);
            if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
                ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(upArrow);
            }
        }

        // add a bottom sheet
        BottomSheetShowHistoryWifiFragment bottomSheetFragment = BottomSheetShowHistoryWifiFragment.newInstance();
        getChildFragmentManager()
                .beginTransaction()
                .add(R.id.history_wifi_bottom_sheets, bottomSheetFragment, Constant.TAG_BOTTOM_SHEET_WIFI_STATE_AND_DATE)
                .commit();
        return view;
    }

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
//                setDateStateWifi();
                setDefaultMapSetting(mMap);
            }
        });
    }

    // when click up button, if version >= 5, show anim when close
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
            ((HistoryPresenterFragment) fragment).popHistoryShowDetailWifiFragment();
        }
    }

    // set height for toolbar to default height
    private void setHeightForToolbarToDefault() {
        changeToolbarHeight(mToolbar, 0, true); // 0 because not use custome toolbar height
    }

    // Formatter.formatIpAddress(int) is predecated because it's not support ipv6 but we dont use it now.
    // change font, style of Wifi info textview
    @SuppressWarnings("deprecation")
    private void setWifiInfo() {
        // set new font to textview
        setFontToTv(mBtnShowWifiOnMap, Constant.QUICKSAND_REGULAR);
        setFontToTv(mShowWifiInfoTv, Constant.QUICKSAND_LIGHT);

        // set new font and change text in textview
        String ssidMes = HelpUtils.getStringAfterRemoveChar(mWifiModel.getSsid(), "\"");
        setFontAndTextToTv(mSsidTv, Constant.QUICKSAND_BOLD, ssidMes);
        setFontAndTextToTv(mBssidTv, Constant.QUICKSAND_LIGHT, R.string.title_message_bssid, mWifiModel.getBssid());
        setFontAndTextToTv(mMacAddressTv, Constant.QUICKSAND_LIGHT, R.string.title_message_mac_add, mWifiModel.getMacAddress());
//        setFontAndTextToTv(mIpAddressTv, QUICKSAND_LIGHT, R.string.title_message_ip_add, Formatter.formatIpAddress(mWifiModel.getIpAddress()));
//        setFontAndTextToTv(mLinkspeedTv, QUICKSAND_LIGHT, R.string.title_message_link_speed, mWifiModel.getLinkSpeed() + " " + WifiInfo.LINK_SPEED_UNITS);
        setFontAndTextToTv(mNetworkidTv, Constant.QUICKSAND_LIGHT, R.string.title_message_network_id, Integer.toString(mWifiModel.getNetworkId()));

        // 6/27/2016 set text and change color depend on wifi signal
//        int wifiLevel = WifiManager.calculateSignalLevel(mWifiModel.getRssi(), MAX_WIFI_SIGNAL_LEVEL);
//        switch (wifiLevel) {
//            case WIFI_SIGNAL_EXCELLENT:
//                setTextAndChangeBgColorTv(mRssiTv, R.string.show_message_wifi_signal_excellent, R.color.excellent);
//                break;
//            case WIFI_SIGNAL_GOOD:
//                setTextAndChangeBgColorTv(mRssiTv, R.string.show_message_wifi_signal_good, R.color.good);
//                break;
//            case WIFI_SIGNAL_FAIR:
//                setTextAndChangeBgColorTv(mRssiTv, R.string.show_message_wifi_signal_fair, R.color.fair);
//                break;
//            case WIFI_SIGNAL_WEAK:
//            case 0: // 0 belongs to wifi signal weak
//                setTextAndChangeBgColorTv(mRssiTv, R.string.show_message_wifi_signal_weak, R.color.weak);
//                break;
//            default:
//                break;
//        }
    }

    // set data and change background color depend on state in db.
//    private void setDateStateWifi() {
//        setFontAndTextToTv(mDateTv, QUICKSAND_LIGHT, mWifiModel.getDate());
////        mDateTv.setText(mWifiModel.getDate());
//        String state = mWifiModel.getState();
//        if (state.equals(Constant.WIFI_DISCONNECT)) {
//            setTextAndChangeBgColorTv(mStateTv, state, R.color.disconnected);
//        } else {
//            setTextAndChangeBgColorTv(mStateTv, state, R.color.connected);
//        }
//    }

    // when click "View Wifi Hotspots" button, show a wifi hotspot on the map by go to db and get location
    // seach db by "ssid"
    // TODO: 6/30/2016 when click, show a bottom sheet to show a list of history
    @OnClick(R.id.btn_show_wifi_on_map)
    public void onClick() {
        View bottomSheetFragment = getActivity().findViewById(R.id.history_wifi_bottom_sheets);
        BottomSheetBehavior<View> bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetFragment); //bottomSheetFragment.getBottomSheetBehavior();
        // if bottom is hiding - STATE_COLLAPSED, we show t part of it
        if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        }
    }

    public void callPresenterToGetWifiStateAndDateFromDb() {
        Fragment parentFragment = getParentFragment();
        if (parentFragment instanceof HistoryPresenterFragment) {
            ((HistoryPresenterFragment) parentFragment).getWifiStateAndDateFromDb(mWifiModel);
        }
    }

    // a callback from db with data wifi state and date history result.
    public void passWifiStateAndDateToBottomSheetCursor(Cursor cursor) {
        Fragment fragment = getChildFragmentManager().findFragmentByTag(Constant.TAG_BOTTOM_SHEET_WIFI_STATE_AND_DATE);
        if (fragment instanceof BottomSheetShowHistoryWifiFragment) {
            ((BottomSheetShowHistoryWifiFragment) fragment).populateDateToRecyclerView(cursor);
        }
    }
}
