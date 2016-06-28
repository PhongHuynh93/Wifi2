package dhbk.android.wifi2.utils;

import android.support.annotation.NonNull;

/**
 * Created by phongdth.ky on 6/27/2016.
 */
public class HelpUtils {
    @NonNull
    public static String removeChar(@NonNull String textBeforeRemove, @NonNull String charRemove) {
        return textBeforeRemove.replace(charRemove, "");
    }
}
