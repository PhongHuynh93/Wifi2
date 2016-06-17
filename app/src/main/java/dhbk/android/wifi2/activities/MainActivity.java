package dhbk.android.wifi2.activities;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import dhbk.android.wifi2.R;
import dhbk.android.wifi2.fragments.HistoryFragment;
import dhbk.android.wifi2.fragments.HistoryWithOsmMapFragment;
import dhbk.android.wifi2.fragments.MainFragment;
import dhbk.android.wifi2.fragments.MobileFragment;
import dhbk.android.wifi2.fragments.WifiFragment;
import dhbk.android.wifi2.interfaces.OnFragInteractionListener;
import dhbk.android.wifi2.utils.Constant;


public class MainActivity extends AppCompatActivity implements
        OnFragInteractionListener.OnMainFragInteractionListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        MainFragment mainFragment = (MainFragment) getSupportFragmentManager().findFragmentByTag(Constant.TAG_MAIN_FRAGMENT);
        if (mainFragment == null) {
            mainFragment = MainFragment.newInstance();
            getSupportFragmentManager().beginTransaction().add(R.id.main_container, mainFragment, Constant.TAG_MAIN_FRAGMENT).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
