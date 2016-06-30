package dhbk.android.wifi2.fragments.historyFragments;

import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.support.annotation.ColorRes;
import android.support.annotation.StringRes;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.TypedValue;
import android.widget.TextView;

import org.osmdroid.api.IMapController;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

import dhbk.android.wifi2.utils.Constant;
import dhbk.android.wifi2.utils.DividerItemDecoration;

/**
 * Created by phongdth.ky on 6/28/2016.
 */
public abstract class HistoryBaseFragment extends Fragment {
    // declare a toolbar with icon back button and can listen when click icons in toolbar
    public void declareToolbar(Toolbar mToolbar) {
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        setHasOptionsMenu(true);
    }

    // change toolbar height depends on whether custome toolbar height or default toolbar height.
    public void changeToolbarHeight(Toolbar toolbar, float toolbarHeightDp, boolean isDefaultHeight) {
        TypedValue tv = new TypedValue();
        AppBarLayout.LayoutParams layoutParams = (AppBarLayout.LayoutParams) toolbar.getLayoutParams();
        if (!isDefaultHeight) {
            // change toolbarHeightDp to pixel
            layoutParams.height = (int) tv.applyDimension(TypedValue.COMPLEX_UNIT_DIP, toolbarHeightDp, getActivity().getResources().getDisplayMetrics());
        } else {
            // get the default height depends on device
            getActivity().getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true);
            layoutParams.height = getResources().getDimensionPixelSize(tv.resourceId);
        }
        toolbar.setLayoutParams(layoutParams);
    }

    // TODO: 6/29/2016 add mobile to this
    // reclare recyclerview, adapter, and get datas from db to show to it.
    public void declareRcvAndGetDataFromDb(RecyclerView recyclerView, RecyclerView.Adapter adapter, String wifiOrMobile) {
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        // add horizontal white divider between each row
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST);
        recyclerView.addItemDecoration(itemDecoration);

        // : 6/15/2016 get wifi data from database (id, state, ssid, date)
        Fragment parentFragment = getParentFragment();
        if (parentFragment instanceof HistoryWifiMobileFragment) {
            if (wifiOrMobile.equals(Constant.WIFI_RECYCLERVIEW)) {
                ((HistoryWifiMobileFragment) parentFragment).callPresenterToGetWifiDataFromDb();
            } else if (wifiOrMobile.equals(Constant.MOBILE_RECYCLERVIEW)){
                ((HistoryWifiMobileFragment) parentFragment).callPresenterToGetMobileDataFromDb();
            }
        }
    }

    // translate font file from ttf, otf to font that textview in layout can use
    // then set that font to textview
    public void setFontToTv(TextView textView, String fontPath) {
        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), fontPath);
        textView.setTypeface(typeface);
    }

    // get font from file and set message to textview
    public void setFontAndTextToTv(TextView textView, String fontPath, @StringRes int stringNeedBold, String stringNotNeedBold) {
        setFontToTv(textView, fontPath);
        textView.setText(Html.fromHtml("<b>" + getContext().getResources().getString(stringNeedBold) + "</b> " + stringNotNeedBold));
    }

    public void setFontAndTextToTv(TextView textView, String fontPath, String message) {
        setFontToTv(textView, fontPath);
        textView.setText(message);
    }


    //    set text and change background color of a textview
    public void setTextAndChangeBgColorTv(TextView textView, @StringRes int messageTv, @ColorRes int colorBg) {
        textView.setText(getActivity().getResources().getString(messageTv));
        textView.getBackground().setColorFilter(ContextCompat.getColor(getActivity(), colorBg), PorterDuff.Mode.SRC_ATOP);
    }

    //    set text and change background color of a textview
    public void setTextAndChangeBgColorTv(TextView textView, String messageTv, @ColorRes int colorBg) {
        textView.setText(messageTv);
        textView.getBackground().setColorFilter(ContextCompat.getColor(getActivity(), colorBg), PorterDuff.Mode.SRC_ATOP);
    }

    public void setDefaultMapSetting(MapView mapView) {
        mapView.setTileSource(TileSourceFactory.MAPNIK);
        mapView.setMultiTouchControls(true);
        IMapController iMapController = mapView.getController(); // map controller
        iMapController.setZoom(Constant.ZOOM);
        GeoPoint startPoint = new GeoPoint(Constant.START_LATITUDE, Constant.STATE_LONGITUDE);
        iMapController.setCenter(startPoint);
    }

}
