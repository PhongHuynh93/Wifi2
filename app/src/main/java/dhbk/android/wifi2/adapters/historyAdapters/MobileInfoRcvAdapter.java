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
import dhbk.android.wifi2.models.MobileModel;
import dhbk.android.wifi2.utils.Constant;

/**
 * Created by huynhducthanhphong on 6/19/16.
 * adapter for showing a list of mobile history
 */
public class MobileInfoRcvAdapter extends
        CursorRecyclerViewAdapter<MobileInfoRcvAdapter.ViewHolder> {
    private final Context mActivityContext;

    public MobileInfoRcvAdapter(Context context, Cursor cursor) {
        super(context, cursor);
        this.mActivityContext = context;
    }

    // gan tinh chat cua mobile vao adapter giong nhu wifi vay
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Cursor cursor) {
        final MobileModel myListItem = MobileModel.fromCursor(cursor);
        viewHolder.mRowGenerationMobileHistory.setText(myListItem.getGeneration());

        // listen the icon click
        viewHolder.mImgArrowRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mActivityContext instanceof MainActivity) {
                    Fragment historyPresenterfragment = ((MainActivity) mActivityContext).getSupportFragmentManager().findFragmentByTag(Constant.TAG_HISTORY_PRESENTER_FRAGMENT);
                    if (historyPresenterfragment instanceof HistoryPresenterFragment) {
                        ((HistoryPresenterFragment) historyPresenterfragment).replaceWithShowMobileDetailFrag(myListItem);
                    }
                }
            }
        });
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_history_mobile, parent, false);
        ViewHolder vh = new ViewHolder(itemView);
        return vh;
    }

    public class ViewHolder  extends RecyclerView.ViewHolder{
        @BindView(R.id.row_generation_mobile_history)
        TextView mRowGenerationMobileHistory;
        @BindView(R.id.img_arrow_right)
        ImageView mImgArrowRight;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
