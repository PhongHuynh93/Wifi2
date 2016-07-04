package dhbk.android.wifi2.fragments.historyFragments;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import dhbk.android.wifi2.R;
import dhbk.android.wifi2.models.MobileModel;
import dhbk.android.wifi2.utils.Constant;

public class ChildShowDetailMobileFragment extends BaseFragment {

    private static final String ARG_GENERATION = "arg_generation";
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.fab_mobile_show_detail)
    FloatingActionButton mFabMobileShowDetail;
    @BindView(R.id.title_generation)
    TextView mTitleGeneration;
    @BindView(R.id.title_mess_mobile_info)
    TextView mTitleMessMobileInfo;
    @BindView(R.id.mobile_type)
    TextView mMobileType;
    @BindView(R.id.mobile_speed)
    TextView mMobileSpeed;
    @BindView(R.id.mobile_date_and_state_frag)
    FrameLayout mMobileDateAndStateFrag;
    @BindView(R.id.main_content_show_mobile_detail)
    LinearLayout mMainContentShowMobileDetail;
    @BindView(R.id.container_coordinator)
    CoordinatorLayout mContainerCoordinator;
    private HistoryPresenterFragment mHistoryPresenterFragment;
    private String mGeneration;

    public ChildShowDetailMobileFragment() {
    }

    public static ChildShowDetailMobileFragment newInstance(MobileModel mobileModel) {
        ChildShowDetailMobileFragment fragment = new ChildShowDetailMobileFragment();
        Bundle args = new Bundle();
        // if we want more infor, get more infor here from object wifiLocationModel
        args.putString(ARG_GENERATION, mobileModel.getGeneration());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Fragment parentFragment = getParentFragment();
        if (parentFragment instanceof HistoryPresenterFragment) {
            mHistoryPresenterFragment = (HistoryPresenterFragment) parentFragment;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mHistoryPresenterFragment = null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mGeneration = getArguments().getString(ARG_GENERATION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_child_show_detail_mobile, container, false);
        ButterKnife.bind(this, view);

        // if android version < Lolipop, show layout normal
        // if not, make a animation, when it ends, show layout.
        if (!(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)) {
            initViews();
        }

        declareToolbar(mToolbar);
        mToolbar.setTitle(getActivity().getResources().getString(R.string.title_message_wifi_toolbar));
        changeToolbarHeightBigger(mToolbar);
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


    public void animateRevealShow() {
        super.animateRevealShow(mContainerCoordinator, mFabMobileShowDetail);
    }

    @Override
    protected void initViews() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                // add animation fade 0.5s before main content shows.
                Animation animation = AnimationUtils.loadAnimation(getContext().getApplicationContext(), android.R.anim.fade_in);
                animation.setDuration(getActivity().getResources().getInteger(R.integer.animation_duration));
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    // because add fragment require connect to db and get the cursor which cans slow the anim
                    // if the anim doesn't end.
                    // So if the anim end, we call add the bottom sheet
                    @Override
                    public void onAnimationEnd(Animation animation) {
                        // connect to db to laod mobile state and date
//
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });

                // show content when the anim start
                mMainContentShowMobileDetail.startAnimation(animation);
                mMainContentShowMobileDetail.setVisibility(View.VISIBLE);
                setHeightForToolbarToDefault(mToolbar);

//                set the text of textview in this layout
                setMobileInfo();

            }
        });
    }

    private void setMobileInfo() {
        // set new font to textview
        setFontToTv(mTitleMessMobileInfo, Constant.QUICKSAND_LIGHT);

        // set new font and change text in textview
        setFontAndTextToTv(mTitleGeneration, Constant.QUICKSAND_BOLD, mGeneration);
//        setFontAndTextToTv(mMobileType, Constant.QUICKSAND_LIGHT, R.string.title_message_bssid, );
//        setFontAndTextToTv(mMobileSpeed, Constant.QUICKSAND_LIGHT, R.string.title_message_mac_add, );
    }

    @Override
    public void backPressed() {
        if (mHistoryPresenterFragment != null) {
            mHistoryPresenterFragment.popHistoryShowDetailMobileFragment();
        }

    }
}
