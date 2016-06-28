package dhbk.android.wifi2.models;

import android.support.annotation.DrawableRes;

/**
 * Created by huynhducthanhphong on 6/19/16.
 * contain an icon and an image in toolbar for viewpager and tablayout
 */
public class HistoryPageModel {
    private int resTitleIcon;
    private int resImage;

    public HistoryPageModel(@DrawableRes int resTitleIcon,@DrawableRes int resImage) {
        this.resTitleIcon = resTitleIcon;
        this.resImage = resImage;
    }

    public int getResTitleIcon() {
        return resTitleIcon;
    }

    public int getResImage() {
        return resImage;
    }
}
