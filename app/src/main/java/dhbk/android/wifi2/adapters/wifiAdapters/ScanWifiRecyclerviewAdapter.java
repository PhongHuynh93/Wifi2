package dhbk.android.wifi2.adapters.wifiAdapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import dhbk.android.wifi2.R;
import dhbk.android.wifi2.models.WifiLocationModel;

/**
 * Created by phongdth.ky on 6/14/2016.
 */
public class ScanWifiRecyclerviewAdapter extends
        RecyclerView.Adapter<ScanWifiRecyclerviewAdapter.ViewHolder> {

    // contain data
    private final ArrayList<WifiLocationModel> mWifiScanWifiModels;

    // contains list of wifi around you
    public ScanWifiRecyclerviewAdapter(ArrayList<WifiLocationModel> wifiScanWifiModels) {
        this.mWifiScanWifiModels = wifiScanWifiModels;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_wifi, parent, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.ssidTv.setText(mWifiScanWifiModels.get(position).getSsid());
        holder.encrypTv.setText(mWifiScanWifiModels.get(position).getEncryption());
    }

    @Override
    public int getItemCount() {
        return mWifiScanWifiModels.size();
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
    public WifiLocationModel getWifiModelAtPosition(int position) {
        String ssid = mWifiScanWifiModels.get(position).getSsid();
        String encryption = mWifiScanWifiModels.get(position).getEncryption();
        String bssid = mWifiScanWifiModels.get(position).getBssid();
        return new WifiLocationModel(ssid, encryption, bssid);
    }
}
