package dhbk.android.wifi2.adapters.historyAdapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import dhbk.android.wifi2.R;
import dhbk.android.wifi2.adapters.CursorRecyclerViewAdapter;
import dhbk.android.wifi2.models.MobileModel;

/**
 * Created by huynhducthanhphong on 6/19/16.
 * adapter for showing a list of mobile history
 */
public class MobileInfoRcvAdapter extends
        CursorRecyclerViewAdapter<MobileInfoRcvAdapter.ViewHolder> {


    public MobileInfoRcvAdapter(Context context, Cursor cursor) {
        super(context, cursor);
    }

    // gan tinh chat cua mobile vao adapter giong nhu wifi vay
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Cursor cursor) {
        MobileModel myListItem = MobileModel.fromCursor(cursor);
        viewHolder.wifiStateHotspotTv.setText(myListItem.getSpeed());
        viewHolder.wifiSsidHotspotTv.setText(myListItem.getNetworkName());
        viewHolder.wifiDateHotspotTv.setText(myListItem.getDate());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_history_mobile, parent, false);
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
