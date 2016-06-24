package dhbk.android.wifi2.adapters.historyAdapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView wifiStateHotspotTv;
        public TextView wifiSsidHotspotTv;
        public TextView wifiDateHotspotTv;
        public ViewHolder(View itemView) {
            super(itemView);
            wifiStateHotspotTv = (TextView) itemView.findViewById(R.id.row_state_wifi_history);
            wifiSsidHotspotTv = (TextView) itemView.findViewById(R.id.row_ssid_wifi_history);
            wifiDateHotspotTv = (TextView) itemView.findViewById(R.id.row_date_wifi_history);
        }
    }

    private static Date date(String string) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.UK).parse(string);
        } catch (ParseException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
