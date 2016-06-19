package dhbk.android.wifi2.activities;

import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import dhbk.android.wifi2.R;
import dhbk.android.wifi2.fragments.MainFragment;
import dhbk.android.wifi2.fragments.historyFragments.HistoryFragment;
import dhbk.android.wifi2.fragments.historyOSMFragments.HistoryWithOsmMapFragment;
import dhbk.android.wifi2.fragments.mobileFragments.MobileFragment;
import dhbk.android.wifi2.fragments.wifiFragments.WifiFragment;
import dhbk.android.wifi2.interfaces.OnFragInteractionListener;
import dhbk.android.wifi2.utils.Constant;


public class MainActivity extends AppCompatActivity implements
        OnFragInteractionListener.OnMainFragInteractionListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        MainFragment mainFragment = (MainFragment) getSupportFragmentManager().findFragmentByTag(Constant.TAG_MAIN_FRAGMENT);
        if (mainFragment == null) {
            mainFragment = MainFragment.newInstance();
            getSupportFragmentManager().beginTransaction().add(R.id.main_container, mainFragment, Constant.TAG_MAIN_FRAGMENT).commit();
        }

        // nav
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
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
                    WifiFragment wifiFragment = (WifiFragment) getSupportFragmentManager().findFragmentByTag(Constant.TAG_WIFI_FRAGMENT);
                    if (wifiFragment == null) {
                        wifiFragment = WifiFragment.newInstance();
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, wifiFragment, Constant.TAG_WIFI_FRAGMENT).commit();
                    }

                } else if (id == R.id.mobile) {
                    getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    MobileFragment mobileFragment = (MobileFragment) getSupportFragmentManager().findFragmentByTag(Constant.TAG_MOBILE_FRAGMENT);
                    if (mobileFragment == null) {
                        mobileFragment = MobileFragment.newInstance();
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, mobileFragment, Constant.TAG_MOBILE_FRAGMENT).commit();
                    }

                } else if (id == R.id.history) {
                    getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    HistoryFragment historyFragment = (HistoryFragment) getSupportFragmentManager().findFragmentByTag(Constant.TAG_HISTORY_FRAGMENT);
                    if (historyFragment == null) {
                        historyFragment = HistoryFragment.newInstance();
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, historyFragment, Constant.TAG_HISTORY_FRAGMENT).commit();
                    }
                } else if (id == R.id.map) {
                    getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    HistoryWithOsmMapFragment historyOSMFragment = (HistoryWithOsmMapFragment) getSupportFragmentManager().findFragmentByTag(Constant.TAG_HISTORY_WITH_OSM_MAP_FRAGMENT);
                    if (historyOSMFragment == null) {
                        historyOSMFragment = HistoryWithOsmMapFragment.newInstance();
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, historyOSMFragment, Constant.TAG_HISTORY_WITH_OSM_MAP_FRAGMENT).commit();
                    }

                }
                DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }


    // When press "Back",
    @Override
    public void onBackPressed() {
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }

    // replace with wifi fragment
    @Override
    public void onWifiFragReplace() {
        final WifiFragment wifiFragment = WifiFragment.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_container, wifiFragment, Constant.TAG_WIFI_FRAGMENT)
                .addToBackStack(null)
                .commit();
    }

    // replace top frag with history fragment
    @Override
    public void onHistoryFragReplace() {
        final HistoryFragment historyFragment = HistoryFragment.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_container, historyFragment, Constant.TAG_HISTORY_FRAGMENT)
                .addToBackStack(null)
                .commit();
    }

    // replace top frag with HistoryWithOsmMapFragment
    @Override
    public void onHistoryWithOsmMapFragReplace() {
        final HistoryWithOsmMapFragment historyWithOsmMapFragment = HistoryWithOsmMapFragment.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_container, historyWithOsmMapFragment, Constant.TAG_HISTORY_WITH_OSM_MAP_FRAGMENT)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onMobileFragReplace() {
        final MobileFragment mobileFragment = MobileFragment.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_container, mobileFragment, Constant.TAG_MOBILE_FRAGMENT)
                .addToBackStack(null)
                .commit();
    }

    // : 6/17/2016 check historywithosmfrag and transmit cursor to it
    @Override
    public void onReturnCursorWifiHotspot(Cursor cursor) {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(Constant.TAG_HISTORY_WITH_OSM_MAP_FRAGMENT);
        if (fragment instanceof HistoryWithOsmMapFragment) {
            ((HistoryWithOsmMapFragment) fragment).onReturnWifiHotspot(cursor);
        }
    }
}
