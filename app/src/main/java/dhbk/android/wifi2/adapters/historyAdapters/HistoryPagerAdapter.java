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

import dhbk.android.wifi2.R;
import dhbk.android.wifi2.fragments.historyFragments.HistoryChildMobileFragment;
import dhbk.android.wifi2.fragments.historyFragments.HistoryChildWifiFragment;
import dhbk.android.wifi2.models.HistoryPageModel;

/**
 * Created by huynhducthanhphong on 6/19/16.
 */
// tạo 2 fragment là wifi va mobile list
public class HistoryPagerAdapter extends FragmentPagerAdapter {
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
//        if (position >= 0 && position < TITLES.length) {
//            return TITLES[position].getTitle();
//        }
//        return null;

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

}
