package dhbk.android.wifi2.activities;

import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.MenuItem;

import dhbk.android.wifi2.R;
import dhbk.android.wifi2.fragments.MainFragment;
import dhbk.android.wifi2.fragments.historyFragments.HistoryChildShowDetailWifiFragment;
import dhbk.android.wifi2.fragments.historyFragments.HistoryFragment;
import dhbk.android.wifi2.fragments.historyOSMFragments.HistoryWithOsmMapFragment;
import dhbk.android.wifi2.fragments.mobileFragments.MobileFragment;
import dhbk.android.wifi2.fragments.wifiFragments.WifiFragment;
import dhbk.android.wifi2.interfaces.OnFragInteractionListener;
import dhbk.android.wifi2.models.WifiModel;
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

    // TODO: 6/25/16 add animation when replace fragment
    @Override
    public void onHistoryChildShowDetailWifiFragReplace(WifiModel wifiModel) {
        // get the fab of history top fragment
        // Find the shared element (in Fragment A), lúc này History dang trên top nên ta sẽ kiếm dc ID của fab
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_reveal_detail_wifi);

        final HistoryChildShowDetailWifiFragment historyChildShowDetailWifiFragment = HistoryChildShowDetailWifiFragment.newInstance(wifiModel);

        // add anim if >= Android 5
        // Check that the device is running lollipop
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // Inflate transitions to apply
            Transition transition = TransitionInflater.from(this).
                    inflateTransition(R.transition.changebounds_with_arcmotion); // chuyển cái nút từ bên dưới đi lên trên


            // Setup enter transition on second fragment
            historyChildShowDetailWifiFragment.setSharedElementEnterTransition(transition);

            transition.addListener(new Transition.TransitionListener() {
                @Override
                public void onTransitionStart(Transition transition) {

                }

                // TODO: 6/12/16 8 start the Circular Reveal Animation.
                @Override
                public void onTransitionEnd(Transition transition) {
                    transition.removeListener(this);
                    // chạy ham nay trong fragment show wifi detail
                    Fragment topFrag = getSupportFragmentManager().findFragmentByTag(Constant.TAG_HISTORY_WIFI_DETAIL_FRAGMENT);
                    if (topFrag instanceof HistoryChildShowDetailWifiFragment) {
                        ((HistoryChildShowDetailWifiFragment)topFrag).animateRevealShow();
                    }

                }

                @Override
                public void onTransitionCancel(Transition transition) {

                }

                @Override
                public void onTransitionPause(Transition transition) {

                }

                @Override
                public void onTransitionResume(Transition transition) {

                }
            });

            // chắc chắn phải tìm được nút fab mới animation được
            if (fab != null) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .addSharedElement(fab, fab.getTransitionName())
                        .replace(R.id.main_container, historyChildShowDetailWifiFragment, Constant.TAG_HISTORY_WIFI_DETAIL_FRAGMENT)
                        .addToBackStack(null)
                        .commit();
            }
        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_container, historyChildShowDetailWifiFragment, Constant.TAG_HISTORY_WIFI_DETAIL_FRAGMENT)
                    .addToBackStack(null)
                    .commit();
        }


    }
}
