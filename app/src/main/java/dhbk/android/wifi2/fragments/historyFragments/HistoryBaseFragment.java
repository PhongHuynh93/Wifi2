package dhbk.android.wifi2.fragments.historyFragments;

import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.text.Html;
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

    // get font from file and set message to textview
    public void setFontAndTextToTv(TextView textView, String fontPath, String message) {
        setFontToTv(textView, fontPath);
        textView.setText(message);
    }

    // bold a part of a message
    public String getBoldPartOfMessage(String stringNeedBold, String stringDontNeedBold) {
        return Html.fromHtml("<b>" + stringNeedBold + "</b> " + stringDontNeedBold).toString();
    }
}
