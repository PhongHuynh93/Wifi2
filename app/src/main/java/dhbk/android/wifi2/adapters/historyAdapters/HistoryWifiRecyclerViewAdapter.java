package dhbk.android.wifi2.adapters.historyAdapters;

import android.content.Context;
import android.database.Cursor;
import android.graphics.PorterDuff;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import dhbk.android.wifi2.R;
import dhbk.android.wifi2.activities.MainActivity;
import dhbk.android.wifi2.adapters.CursorRecyclerViewAdapter;
import dhbk.android.wifi2.fragments.historyFragments.HistoryPresenterFragment;
import dhbk.android.wifi2.models.WifiModel;
import dhbk.android.wifi2.utils.Constant;
import dhbk.android.wifi2.utils.TimeStampFormatter;

/**
 * Created by phongdth.ky on 6/15/2016.
 * Show a history of wifi hotspots that a user has connected or disconected in a recyclerview.
 */
public class HistoryWifiRecyclerViewAdapter extends
        CursorRecyclerViewAdapter<HistoryWifiRecyclerViewAdapter.ViewHolder> {

    private final Context mActivityContext;
    private TimeStampFormatter mTimeStampFormatter = new TimeStampFormatter();

    public HistoryWifiRecyclerViewAdapter(Context context, Cursor cursor) {
        super(context, cursor);
        this.mActivityContext = context;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Cursor cursor) {
        final WifiModel myListItem = WifiModel.fromCursor(cursor);
        if (myListItem.getState().equals(Constant.WIFI_DISCONNECT)) {
            viewHolder.wifiStateHotspotTv.setBackgroundResource(R.drawable.bg_view);
            viewHolder.wifiStateHotspotTv.getBackground().setColorFilter(ContextCompat.getColor(mActivityContext,R.color.disconnected), PorterDuff.Mode.SRC_ATOP); // White Tint
        } else {
            viewHolder.wifiStateHotspotTv.setBackgroundResource(R.drawable.bg_view);
            viewHolder.wifiStateHotspotTv.getBackground().setColorFilter(ContextCompat.getColor(mActivityContext,R.color.connected), PorterDuff.Mode.SRC_ATOP); // White Tint
        }
        viewHolder.wifiStateHotspotTv.setText(myListItem.getState());
        viewHolder.wifiSsidHotspotTv.setText(myListItem.getSsid());
        viewHolder.wifiDateHotspotTv.setText(mTimeStampFormatter.format(date(myListItem.getDate())));

//      call history presenter method to replace with wifi details fragment.
        viewHolder.mImgArrowRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mActivityContext instanceof MainActivity) {
                    Fragment historyPresenterfragment = ((MainActivity)mActivityContext).getSupportFragmentManager().findFragmentByTag(Constant.TAG_HISTORY_PRESENTER_FRAGMENT);
                    if (historyPresenterfragment instanceof HistoryPresenterFragment) {
                        ((HistoryPresenterFragment)historyPresenterfragment).replaceWithShowWifiDetailFrag(myListItem);
                    }
                }
            }
        });
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_history_wifi, parent, false);
        return new ViewHolder(itemView);
    }

    private static Date date(String string) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.UK).parse(string);
        } catch (ParseException e) {
            throw new IllegalArgumentException(e);
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.row_ssid_wifi_history)
        TextView wifiSsidHotspotTv;
        @BindView(R.id.row_state_wifi_history)
        TextView wifiStateHotspotTv;
        @BindView(R.id.row_date_wifi_history)
        TextView wifiDateHotspotTv;
        @BindView(R.id.img_arrow_right)
        ImageView mImgArrowRight;


        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
