package dhbk.android.wifi2.adapters.historyAdapters;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import dhbk.android.wifi2.fragments.historyFragments.HistoryChildMobileFragment;
import dhbk.android.wifi2.fragments.historyFragments.HistoryChildWifiFragment;

/**
 * Created by huynhducthanhphong on 6/19/16.
 */
// tạo 2 fragment là wifi va mobile list
public class HistoryPagerAdapter extends FragmentPagerAdapter {
    private static final String[] TITLES = {"WIFI", "MOBILE"};

    public HistoryPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Nullable
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return HistoryChildWifiFragment.newInstance();
            case 1:
                return HistoryChildMobileFragment.newInstance();
        }
        return null;
    }

    @Override
    public int getCount() {
        return TITLES.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position >= 0 && position < TITLES.length) {
            return TITLES[position];
        }
        return null;
    }
}