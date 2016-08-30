package dhbk.android.wifi2.fragments.wifiFragments;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;

import dhbk.android.wifi2.R;

/**
 * Created by phongdth.ky on 6/13/2016.
 * a dialog to help user turn on wifi
 */
// extends this class and only declare 1 method onCreateDialog
public class TurnOnWifiDialogFragment extends DialogFragment {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            /**
             * declare title
             * declare content
             * declare 2 button, disagree and agress
             */
            builder.setTitle(R.string.mes_turn_on_wifi_title)
                    .setMessage(R.string.mes_turn_on_wifi_body)
                    .setPositiveButton(R.string.mes_wifi_turn_on, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Fragment fragment = getParentFragment();
                            if (fragment instanceof WifiPresenterFragment) {
                                ((WifiPresenterFragment)fragment).turnOnWifi();
                            }
                        }
                    })
                    .setNegativeButton(R.string.mes_wifi_cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                        }
                    });
            // Create the AlertDialog object and return it
            return builder.create();
        }

}
