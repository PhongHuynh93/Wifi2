package dhbk.android.wifi2.models;

import android.support.annotation.DrawableRes;

/**
 * Created by huynhducthanhphong on 6/19/16.
 */
public class HistoryPageModel {
    String title;
    int resId;

    public HistoryPageModel(String title,@DrawableRes int resId) {
        this.title = title;
        this.resId = resId;
    }

    public String getTitle() {
        return title;
    }

    public int getResId() {
        return resId;
    }
}
