package dhbk.android.wifi2.fragments.wifiFragments;

//import android.app.Dialog;

//import android.app.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;

import dhbk.android.wifi2.R;

/**
 * Created by phongdth.ky on 6/13/2016.
 */
// show a GPS dialog, and pass true/false to fragment depend on what they click.
public class GpsChildTurnOnDialogFragment extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(R.string.mes_turn_on_gps_title)
                    .setMessage(R.string.mes_turn_on_gps_body)
                    .setPositiveButton(R.string.mes_wifi_turn_on, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // : 6/17/16 if yes, code to turn on location here
                            turnGPSOn();
                            Fragment fragment = getParentFragment();
                            if (fragment instanceof WifiPresenterFragment) {
                                ((WifiPresenterFragment)fragment).onHasUserTurnOnGps(true);
                            }
                        }
                    })
                    .setNegativeButton(R.string.mes_wifi_cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                            Fragment fragment = getParentFragment();
                            if (fragment instanceof WifiPresenterFragment) {
                                ((WifiPresenterFragment)fragment).onHasUserTurnOnGps(false);
                            }
                        }
                    });
            // Create the AlertDialog object and return it
            return builder.create();
        }

    // ko the bat propramming gps được
    // turn on location
//    public void turnGPSOn()
//    {
//        Intent intent = new Intent("android.location.GPS_ENABLED_CHANGE");
//        intent.putExtra("enabled", true);
//        Context appContext = getActivity().getApplicationContext();
//        appContext.sendBroadcast(intent);
//
//        String provider = Settings.Secure.getString(appContext.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
//        if(!provider.contains("gps")){ //if gps is disabled
//            final Intent poke = new Intent();
//            poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
//            poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
//            poke.setData(Uri.parse("3"));
//            appContext.sendBroadcast(poke);
//        }
//    }
//
    // let user manually turn on GPS
    public void turnGPSOn() {
        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
    }

    // automatic turn off the gps
    public void turnGPSOff()
    {
        Context appContext = getActivity().getApplicationContext();
        String provider = Settings.Secure.getString(appContext.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
        if(provider.contains("gps")){ //if gps is enabled
            final Intent poke = new Intent();
            poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
            poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
            poke.setData(Uri.parse("3"));
            appContext.sendBroadcast(poke);
        }
    }
}
