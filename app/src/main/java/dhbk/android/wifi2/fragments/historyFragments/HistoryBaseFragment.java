package dhbk.android.wifi2.fragments.historyFragments;

import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.widget.TextView;

/**
 * Created by phongdth.ky on 6/28/2016.
 */
public abstract class HistoryBaseFragment extends Fragment{
    // translate font file from ttf, otf to font that textview in layout can use
    // then set that font to textview
    public void setFontToTv(TextView textView, String fontPath) {
        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), fontPath);
        textView.setTypeface(typeface);
    }
}
