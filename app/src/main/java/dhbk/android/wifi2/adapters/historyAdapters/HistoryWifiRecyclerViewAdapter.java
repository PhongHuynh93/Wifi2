package dhbk.android.wifi2.adapters.historyAdapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import butterknife.OnClick;
import dhbk.android.wifi2.R;
import dhbk.android.wifi2.adapters.CursorRecyclerViewAdapter;
import dhbk.android.wifi2.models.WifiModel;
import dhbk.android.wifi2.utils.Constant;
import dhbk.android.wifi2.utils.TimeStampFormatter;

/**
 * Created by phongdth.ky on 6/15/2016.
 */
public class HistoryWifiRecyclerViewAdapter extends
        CursorRecyclerViewAdapter<HistoryWifiRecyclerViewAdapter.ViewHolder> {

    private TimeStampFormatter mTimeStampFormatter = new TimeStampFormatter();

    public HistoryWifiRecyclerViewAdapter(Context context, Cursor cursor) {
        super(context, cursor);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Cursor cursor) {
        WifiModel myListItem = WifiModel.fromCursor(cursor);
        if (myListItem.getState().equals(Constant.WIFI_DISCONNECT)) {
            viewHolder.wifiStateHotspotTv.setBackgroundResource(R.drawable.bg_button_discnect);
        } else {
            viewHolder.wifiStateHotspotTv.setBackgroundResource(R.drawable.bg_button_cnect);
        }
        viewHolder.wifiStateHotspotTv.setText(myListItem.getState());
        viewHolder.wifiSsidHotspotTv.setText(myListItem.getSsid());
        viewHolder.wifiDateHotspotTv.setText(mTimeStampFormatter.format(date(myListItem.getDate())));
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_history_wifi, parent, false);
        ViewHolder vh = new ViewHolder(itemView);
        return vh;
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

        // TODO: 6/25/16 pass ssid and state of a row
        @OnClick(R.id.img_arrow_right)
        public void onClick() {
            String ssid = wifiSsidHotspotTv.getText().toString();
            String state = wifiStateHotspotTv.getText().toString();
            Log.i("phong", "onClick: ");
        }
    }
}
