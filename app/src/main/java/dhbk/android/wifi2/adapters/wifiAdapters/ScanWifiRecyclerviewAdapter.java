package dhbk.android.wifi2.adapters.wifiAdapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import dhbk.android.wifi2.R;
import dhbk.android.wifi2.models.WifiScanWifiModel;

/**
 * Created by phongdth.ky on 6/14/2016.
 */
public class ScanWifiRecyclerviewAdapter extends
        RecyclerView.Adapter<ScanWifiRecyclerviewAdapter.ViewHolder> {

    // contain data
    private final ArrayList<WifiScanWifiModel> mWifiHotSpots;

    // contains list of wifi around you
    public ScanWifiRecyclerviewAdapter(ArrayList<WifiScanWifiModel> wifiScanWifiModels) {
        mWifiHotSpots = wifiScanWifiModels;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_wifi, parent, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.ssidTv.setText(mWifiHotSpots.get(position).getSsid());
        holder.encrypTv.setText(mWifiHotSpots.get(position).getEncryption());
    }

    @Override
    public int getItemCount() {
        return mWifiHotSpots.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView ssidTv;
        public TextView encrypTv;

        public ViewHolder(View itemView) {
            super(itemView);
            ssidTv = (TextView) itemView.findViewById(R.id.ssid);
            encrypTv = (TextView) itemView.findViewById(R.id.encryption);
        }
    }

    // : 6/16/2016 return model ssid and encryption
    public WifiScanWifiModel getWifiModelAtPosition(int position) {
        String ssid = mWifiHotSpots.get(position).getSsid();
        String encryption = mWifiHotSpots.get(position).getEncryption();
        return new WifiScanWifiModel(ssid, encryption);
    }
}
