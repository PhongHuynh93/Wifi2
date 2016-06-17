package dhbk.android.wifi2.fragments;

//import android.app.Dialog;

//import android.app.Dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;

import dhbk.android.wifi2.R;

/**
 * Created by phongdth.ky on 6/13/2016.
 */
// TODO: 6/17/16 thieu 1 th khi user da bat gps truoc thi se ko hien cai bang nay, va xem nhu gps ko dc bat 
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
                            // TODO: 6/17/16 if yes, code to turn on location here
                            Fragment fragment = getParentFragment();
                            if (fragment instanceof WifiFragment) {
                                ((WifiFragment)fragment).hasTurnOnGPS(true);
                            }
                        }
                    })
                    .setNegativeButton(R.string.mes_wifi_cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                            Fragment fragment = getParentFragment();
                            if (fragment instanceof WifiFragment) {
                                ((WifiFragment)fragment).hasTurnOnGPS(false);
                            }
                        }
                    });
            // Create the AlertDialog object and return it
            return builder.create();
        }

}
