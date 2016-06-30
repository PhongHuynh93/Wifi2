package dhbk.android.wifi2.adapters.historyAdapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import dhbk.android.wifi2.R;
import dhbk.android.wifi2.activities.MainActivity;
import dhbk.android.wifi2.adapters.CursorRecyclerViewAdapter;
import dhbk.android.wifi2.fragments.historyFragments.HistoryPresenterFragment;
import dhbk.android.wifi2.models.WifiModel;
import dhbk.android.wifi2.utils.Constant;

/**
 * Created by phongdth.ky on 6/15/2016.
 * Show a wifi hotspots info that a user has connected or disconected in a recyclerview.
 */
public class WifiInfoRcvAdapter extends
        CursorRecyclerViewAdapter<WifiInfoRcvAdapter.ViewHolder> {

    private final Context mActivityContext;

    public WifiInfoRcvAdapter(Context context, Cursor cursor) {
        super(context, cursor);
        this.mActivityContext = context;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Cursor cursor) {
        final WifiModel myListItem = WifiModel.fromCursor(cursor);
        viewHolder.wifiSsidHotspotTv.setText(myListItem.getSsid());

//      call history presenter method to replace with wifi details fragment.
        viewHolder.mImgArrowRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mActivityContext instanceof MainActivity) {
                    Fragment historyPresenterfragment = ((MainActivity) mActivityContext).getSupportFragmentManager().findFragmentByTag(Constant.TAG_HISTORY_PRESENTER_FRAGMENT);
                    if (historyPresenterfragment instanceof HistoryPresenterFragment) {
                        ((HistoryPresenterFragment) historyPresenterfragment).replaceWithShowWifiDetailFrag(myListItem);
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

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.row_ssid_wifi_history)
        TextView wifiSsidHotspotTv;
        @BindView(R.id.img_arrow_right)
        ImageView mImgArrowRight;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
