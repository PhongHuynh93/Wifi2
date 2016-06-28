package dhbk.android.wifi2.adapters.historyAdapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.SparseArray;
import android.view.ViewGroup;

import dhbk.android.wifi2.R;
import dhbk.android.wifi2.fragments.historyFragments.HistoryMobileFragment;
import dhbk.android.wifi2.fragments.historyFragments.HistoryWifiFragment;
import dhbk.android.wifi2.models.HistoryPageModel;

/**
 * Created by huynhducthanhphong on 6/19/16.
 */
// tạo 2 fragment là wifi va mobile list
public class HistoryPagerAdapter extends FragmentPagerAdapter {
    SparseArray<Fragment> registeredFragments = new SparseArray<>();

    // contain image and icon in tablayout
    private static final HistoryPageModel[] TITLES = {
            new HistoryPageModel(R.drawable.ic_wifi_24dp, R.drawable.wifi1),
            new HistoryPageModel(R.drawable.ic_phone_24dp, R.drawable.mobile1)
    };
    private final Context mContext;

    public HistoryPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.mContext = context.getApplicationContext();
    }

    @Nullable
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return HistoryWifiFragment.newInstance();
            case 1:
                return HistoryMobileFragment.newInstance();
        }
        return null;
    }

    @Override
    public int getCount() {
        return TITLES.length;
    }

    // rememeber fragment in viewpager to retrieve it's later (it likes findFragmentByTag)
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        registeredFragments.put(position, fragment);
        return fragment;
    }

    // get the memory back
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        registeredFragments.remove(position);
        super.destroyItem(container, position, object);
    }

    // get image icon in tablayaout instead of string
    @Override
    public CharSequence getPageTitle(int position) {
        Drawable image = ContextCompat.getDrawable(mContext, TITLES[position].getResTitleIcon());
        image.setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());
        SpannableString sb = new SpannableString(" ");
        ImageSpan imageSpan = new ImageSpan(image, ImageSpan.ALIGN_BOTTOM);
        sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sb;
    }

    @DrawableRes
    public int getImageId(int position) {
        return TITLES[position].getResImage();
    }

    // get the current fragment depends on position
    public Fragment getRegisteredFragment(int position) {
        return registeredFragments.get(position);
    }
}
