package dhbk.android.wifi2.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import dhbk.android.wifi2.R;
import dhbk.android.wifi2.models.WifiModel;

/**
 * Created by phongdth.ky on 6/15/2016.
 */
public class HistoryWifiRecyclerViewAdapter extends
        CursorRecyclerViewAdapter<HistoryWifiRecyclerViewAdapter.ViewHolder>{

    public HistoryWifiRecyclerViewAdapter(Context context, Cursor cursor) {
        super(context, cursor);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Cursor cursor) {
        WifiModel myListItem = WifiModel.fromCursor(cursor);
        viewHolder.wifiStateHotspotTv.setText(myListItem.getState());
        viewHolder.wifiSsidHotspotTv.setText(myListItem.getSsid());
        viewHolder.wifiDateHotspotTv.setText(myListItem.getDate());
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
}
