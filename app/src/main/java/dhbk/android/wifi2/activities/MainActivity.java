package dhbk.android.wifi2.activities;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import dhbk.android.wifi2.R;
import dhbk.android.wifi2.fragments.MainFragment;
import dhbk.android.wifi2.fragments.historyFragments.HistoryPresenterFragment;
import dhbk.android.wifi2.fragments.mapFragments.MapPresenterFragment;
import dhbk.android.wifi2.fragments.wifiFragments.WifiPresenterFragment;
import dhbk.android.wifi2.interfaces.OnFragInteractionListener;
import dhbk.android.wifi2.utils.Constant;

/*
Show Main layout, implement navigation drawer and replace|add fragments.
 */
public class MainActivity extends AppCompatActivity implements
        OnFragInteractionListener.OnMainFragInteractionListener {

    @BindView(R.id.main_container)
    FrameLayout mMainContainer;
    @BindView(R.id.nav_view)
    NavigationView mNavView;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // add Main Layout
        MainFragment mainFragment = (MainFragment) getSupportFragmentManager().findFragmentByTag(Constant.TAG_MAIN_FRAGMENT);
        if (mainFragment == null) {
            mainFragment = MainFragment.newInstance();
            getSupportFragmentManager().beginTransaction().add(R.id.main_container, mainFragment, Constant.TAG_MAIN_FRAGMENT).commit();
        }

        // declare navigation drawer and actions when click one menu item.
        mNavView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.main) {
                    getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    MainFragment mainFragment = (MainFragment) getSupportFragmentManager().findFragmentByTag(Constant.TAG_MAIN_FRAGMENT);
                    if (mainFragment == null) {
                        mainFragment = MainFragment.newInstance();
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, mainFragment, Constant.TAG_MAIN_FRAGMENT).commit();
                    }

                } else if (id == R.id.wifi) {
                    getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    WifiPresenterFragment wifiPresenterFragment = (WifiPresenterFragment) getSupportFragmentManager().findFragmentByTag(Constant.TAG_WIFI_PRESENTER_FRAGMENT);
                    if (wifiPresenterFragment == null) {
                        wifiPresenterFragment = WifiPresenterFragment.newInstance();
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, wifiPresenterFragment, Constant.TAG_WIFI_PRESENTER_FRAGMENT).commit();
                    }

                } else if (id == R.id.history) {
                    getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    HistoryPresenterFragment historyPresenterFragment = (HistoryPresenterFragment) getSupportFragmentManager().findFragmentByTag(Constant.TAG_HISTORY_PRESENTER_FRAGMENT);
                    if (historyPresenterFragment == null) {
                        historyPresenterFragment = HistoryPresenterFragment.newInstance();
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, historyPresenterFragment, Constant.TAG_HISTORY_PRESENTER_FRAGMENT).commit();
                    }
                } else if (id == R.id.map) {
                    getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    MapPresenterFragment historyOSMFragment = (MapPresenterFragment) getSupportFragmentManager().findFragmentByTag(Constant.TAG_HISTORY_WITH_OSM_MAP_FRAGMENT);
                    if (historyOSMFragment == null) {
                        historyOSMFragment = MapPresenterFragment.newInstance();
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, historyOSMFragment, Constant.TAG_HISTORY_WITH_OSM_MAP_FRAGMENT).commit();
                    }

                }
                mDrawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }


    // When press "Back" button
    @Override
    public void onBackPressed() {
        // call wifi presenter to pop ShowWifiDetailFrag out of backstack
        Fragment presenterFragment = getSupportFragmentManager().findFragmentByTag(Constant.TAG_HISTORY_PRESENTER_FRAGMENT);
        if (presenterFragment instanceof HistoryPresenterFragment && presenterFragment.getChildFragmentManager().getBackStackEntryCount() > 0) {
            ((HistoryPresenterFragment) presenterFragment).popShowDetailFragment();
            return; // after pop out a nested fragment, not pop out again by run super.onBackPressed();
        } else {
            // pop out normal fragment by run super.onBackPressed
        }

        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    // replace with main Fragment
    @Override
    public void onMainFragReplace() {
        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        MainFragment mainFragment = (MainFragment) getSupportFragmentManager().findFragmentByTag(Constant.TAG_MAIN_FRAGMENT);
        if (mainFragment == null) {
            mainFragment = MainFragment.newInstance();
            getSupportFragmentManager().beginTransaction().replace(R.id.main_container, mainFragment, Constant.TAG_MAIN_FRAGMENT).commit();
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // REPLACE FRAGMENT
    // replace with wifi fragment
    @Override
    public void onWifiFragReplace() {
        final WifiPresenterFragment wifiPresenterFragment = WifiPresenterFragment.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_container, wifiPresenterFragment, Constant.TAG_WIFI_PRESENTER_FRAGMENT)
                .addToBackStack(null)
                .commit();
    }

    // replace top frag with history fragment to show history of wifi and mobile that the user has connected or disconnected
    @Override
    public void onHistoryFragReplace() {
        final HistoryPresenterFragment historyPresenterFragment = HistoryPresenterFragment.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_container, historyPresenterFragment, Constant.TAG_HISTORY_PRESENTER_FRAGMENT)
                .addToBackStack(null)
                .commit();
    }

    // replace top frag with MapPresenterFragment
    @Override
    public void onHistoryWithOsmMapFragReplace() {
        final MapPresenterFragment mapPresenterFragment = MapPresenterFragment.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_container, mapPresenterFragment, Constant.TAG_HISTORY_WITH_OSM_MAP_FRAGMENT)
                .addToBackStack(null)
                .commit();
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // END REPLACE FRAGMENT
}
