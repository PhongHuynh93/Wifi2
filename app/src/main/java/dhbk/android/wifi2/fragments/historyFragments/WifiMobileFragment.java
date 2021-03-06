package dhbk.android.wifi2.fragments.historyFragments;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import dhbk.android.wifi2.R;
import dhbk.android.wifi2.adapters.historyAdapters.HistoryPagerAdapter;
import dhbk.android.wifi2.utils.PageChangeListener;

/*
show a viewpager with tablayout contains a list of wifi hotspot and mobile.
 */
public class WifiMobileFragment extends BaseFragment {
    private static final int POSITION_WIFI_FRAGMENT = 0;
    private static final int POSITION_MOBILE_FRAGMENT = 1;
    @BindView(R.id.toolbar_image_outgoing)
    ImageView mToolbarImageOutgoing;
    @BindView(R.id.toolbar_image)
    ImageView mToolbarImage;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tabs)
    TabLayout mTabLayout;
    @BindView(R.id.container)
    ViewPager mViewPagerContainer;
    @BindView(R.id.fab_reveal_detail_wifi)
    FloatingActionButton mFabRevealDetailWifi;

    public WifiMobileFragment() {
    }

    public static WifiMobileFragment newInstance() {
        return new WifiMobileFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mToolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        declareToolbar(mToolbar);
        mToolbar.setTitle("History");

        // declare viewpager with tablayout
        mToolbarImage = (ImageView) view.findViewById(R.id.toolbar_image);
        mToolbarImageOutgoing = (ImageView) view.findViewById(R.id.toolbar_image_outgoing);

        HistoryPagerAdapter historyPagerAdapter = new HistoryPagerAdapter(getActivity(), getChildFragmentManager());

        mViewPagerContainer = (ViewPager) view.findViewById(R.id.container);
        mViewPagerContainer.setAdapter(historyPagerAdapter);
        mViewPagerContainer.addOnPageChangeListener(PageChangeListener.newInstance(historyPagerAdapter, mToolbarImage, mToolbarImageOutgoing));

        mTabLayout = (TabLayout) view.findViewById(R.id.tabs);
        mTabLayout.setupWithViewPager(mViewPagerContainer);
    }

    // listen click on toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Fragment parentFragment = getParentFragment();
                if (parentFragment instanceof HistoryPresenterFragment) {
                    ((HistoryPresenterFragment) parentFragment).replaceWithMainFrag();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // call Presenter to get wifi data
    public void callPresenterToGetWifiDataFromDb() {
        Fragment parentFragment = getParentFragment();
        if (parentFragment instanceof HistoryPresenterFragment) {
            ((HistoryPresenterFragment) parentFragment).getWifiDataFromDb();
        }
    }

    // call Presenter to get mobile data
    public void callPresenterToGetMobileDataFromDb() {
        Fragment parentFragment = getParentFragment();
        if (parentFragment instanceof HistoryPresenterFragment) {
            ((HistoryPresenterFragment) parentFragment).getMobileDataFromDb();
        }
    }

    //        find the wifiFragment and pass cursor data to it.
    public void getWifiFragmentAndPassWifiCursor(Cursor cursor) {
        HistoryPagerAdapter adapter = (HistoryPagerAdapter) mViewPagerContainer.getAdapter();
        Fragment wifiFragment = adapter.getRegisteredFragment(POSITION_WIFI_FRAGMENT);
        if (wifiFragment instanceof WifiFragment) {
            ((WifiFragment)wifiFragment).onPopulateWifiCursorToRcv(cursor);
        }
    }

    //        find the wifiFragment and pass cursor data to it.
    // FIXME: 7/4/16 error because onCreateVIew of MobileFragment class will call the db to populate the rcv
    // but the current viewpager not the mobile fragment
    public void getMobileFragmentAndPassWifiCursor(Cursor cursor) {
        HistoryPagerAdapter adapter = (HistoryPagerAdapter) mViewPagerContainer.getAdapter();
        Fragment mobileFragment = adapter.getRegisteredFragment(POSITION_MOBILE_FRAGMENT);
        if (mobileFragment instanceof MobileFragment) {
            ((MobileFragment)mobileFragment).onPopulateMobileCursorToRcv(cursor);
        }
    }
}
