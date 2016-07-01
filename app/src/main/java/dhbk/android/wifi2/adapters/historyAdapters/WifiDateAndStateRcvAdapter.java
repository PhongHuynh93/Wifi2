package dhbk.android.wifi2.adapters.historyAdapters;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.annotation.StringRes;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import dhbk.android.wifi2.R;
import dhbk.android.wifi2.adapters.CursorRecyclerViewAdapter;
import dhbk.android.wifi2.models.WifiStateAndDateModel;
import dhbk.android.wifi2.utils.Constant;

/**
 * Created by phongdth.ky on 6/15/2016.
 * Show a wifi state and date
 */
public class WifiDateAndStateRcvAdapter extends
        CursorRecyclerViewAdapter<WifiDateAndStateRcvAdapter.ViewHolder> {

    // set wifi signal max = 5, so system will get the strength of wifi signal from 0 -> 4
    private static final int MAX_WIFI_SIGNAL_LEVEL = 5;
    // from 0 -> 4 correspond to weak, fair, good, excellent wifi signal.
    private static final int WIFI_SIGNAL_EXCELLENT = 4;
    private static final int WIFI_SIGNAL_GOOD = 3;
    private static final int WIFI_SIGNAL_FAIR = 2;
    private static final int WIFI_SIGNAL_WEAK = 1;

    private static final String WIFI_SIGNAL_MES = "Wifi signal: ";

    private final Context mContext;

    public WifiDateAndStateRcvAdapter(Context context, Cursor cursor) {
        super(context, cursor);
        mContext = context;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Cursor cursor) {
        final WifiStateAndDateModel myListItem = WifiStateAndDateModel.fromCursorWifiStateAndDate(cursor);
        // set text to textview
        setFontAndTextToTv(viewHolder.mStateWifi, Constant.QUICKSAND_BOLD, myListItem.getState());
        setFontAndTextToTv(viewHolder.mDateWifi, Constant.QUICKSAND_BOLD, myListItem.getDate());
        setFontAndTextToTv(viewHolder.mIpAddWifi, Constant.QUICKSAND_LIGHT, R.string.title_message_ip_add, Formatter.formatIpAddress(myListItem.getIpAddress()));
        setFontAndTextToTv(viewHolder.mLinkSpeedWifi, Constant.QUICKSAND_LIGHT, R.string.title_message_link_speed, myListItem.getLinkSpeed() + " " + WifiInfo.LINK_SPEED_UNITS);
        // translate rssi to human readable.
        int wifiLevel = WifiManager.calculateSignalLevel(myListItem.getRssi(), MAX_WIFI_SIGNAL_LEVEL);
        switch (wifiLevel) {
            case WIFI_SIGNAL_EXCELLENT:
                setFontAndTextToTv(viewHolder.mRssiWifi, Constant.QUICKSAND_LIGHT, WIFI_SIGNAL_MES, R.string.show_message_wifi_signal_excellent);
                break;
            case WIFI_SIGNAL_GOOD:
                setFontAndTextToTv(viewHolder.mRssiWifi, Constant.QUICKSAND_LIGHT, WIFI_SIGNAL_MES, R.string.show_message_wifi_signal_good);
                break;
            case WIFI_SIGNAL_FAIR:
                setFontAndTextToTv(viewHolder.mRssiWifi, Constant.QUICKSAND_LIGHT, WIFI_SIGNAL_MES, R.string.show_message_wifi_signal_fair);
                break;
            case WIFI_SIGNAL_WEAK:
            case 0: // 0 belongs to wifi signal weak
                setFontAndTextToTv(viewHolder.mRssiWifi, Constant.QUICKSAND_LIGHT, WIFI_SIGNAL_MES, R.string.show_message_wifi_signal_weak);
                break;
            default:
                break;
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_wifi_state_and_date, parent, false);
        return new ViewHolder(itemView);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.state_wifi)
        TextView mStateWifi;
        @BindView(R.id.date_wifi)
        TextView mDateWifi;
        @BindView(R.id.rssi_wifi)
        TextView mRssiWifi;
        @BindView(R.id.linkSpeed_wifi)
        TextView mLinkSpeedWifi;
        @BindView(R.id.ip_add_wifi)
        TextView mIpAddWifi;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public void setFontToTv(TextView textView, String fontPath) {
        Typeface typeface = Typeface.createFromAsset(mContext.getAssets(), fontPath);
        textView.setTypeface(typeface);
    }

    public void setFontAndTextToTv(TextView textView, String fontPath, @StringRes int stringNeedBold, String stringNotNeedBold) {
        setFontToTv(textView, fontPath);
        textView.setText(Html.fromHtml("<b>" + mContext.getResources().getString(stringNeedBold) + "</b> " + stringNotNeedBold));
    }
    public void setFontAndTextToTv(TextView textView, String fontPath, String stringNeedBold , @StringRes int stringNotNeedBold) {
        setFontToTv(textView, fontPath);
        textView.setText(Html.fromHtml("<b>" + stringNeedBold + "</b> " +   mContext.getResources().getString(stringNotNeedBold)));
    }

    public void setFontAndTextToTv(TextView textView, String fontPath, String message) {
        setFontToTv(textView, fontPath);
        textView.setText(message);
    }
}
