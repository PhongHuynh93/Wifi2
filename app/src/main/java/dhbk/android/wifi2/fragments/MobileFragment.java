package dhbk.android.wifi2.fragments;


import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import dhbk.android.wifi2.R;
import dhbk.android.wifi2.utils.Connectivity;

// TODO: 6/17/2016 add feature to save to database
public class MobileFragment extends Fragment {
    public MobileFragment() {
    }

    public static MobileFragment newInstance() {
        MobileFragment fragment = new MobileFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mobile, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (Connectivity.isConnectedMobile(getActivity().getApplicationContext())) {
            showMobileProperty();
        } else {
            // because you can't enable mobile network on non root phone, so make a text
            helpUserTurnOnMobileNetwork();
        }

        // listen scan mobile network button
        Button scanMobileNetwork = (Button) view.findViewById(R.id.scan_mobile);
        scanMobileNetwork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMobileProperty();
            }
        });
    }

    private void helpUserTurnOnMobileNetwork() {
        // set network type in textview
        TextView typeTv = (TextView) getActivity().findViewById(R.id.network_type);
        typeTv.setText("You need to turn on mobile network.");
    }

    private void showMobileProperty() {
        NetworkInfo info = Connectivity.getNetworkInfo(getActivity().getApplicationContext());
        // get name of subtype
        int mobileTypeInt = info.getSubtype();
        String mobileType;
        switch (mobileTypeInt) {
            case TelephonyManager.NETWORK_TYPE_1xRTT:
                mobileType = "1xRTT"; break;// ~ 50-100 kbps
            case TelephonyManager.NETWORK_TYPE_CDMA:
                mobileType = "CDMA"; break;// ~ 14-64 kbps
            case TelephonyManager.NETWORK_TYPE_EDGE:
                mobileType = "EDGE"; break;// ~ 50-100 kbps
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
                mobileType = "EVDO_0"; break;// ~ 400-1000 kbps
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
                mobileType = "EVDO_A"; break;// ~ 600-1400 kbps
            case TelephonyManager.NETWORK_TYPE_GPRS:
                mobileType = "GPRS"; break;// ~ 100 kbps
            case TelephonyManager.NETWORK_TYPE_HSDPA:
                mobileType = "HSDPA"; break;// ~ 2-14 Mbps
            case TelephonyManager.NETWORK_TYPE_HSPA:
                mobileType = "HSPA"; break;// ~ 700-1700 kbps
            case TelephonyManager.NETWORK_TYPE_HSUPA:
                mobileType = "HSUPA"; break;// ~ 1-23 Mbps
            case TelephonyManager.NETWORK_TYPE_UMTS:
                mobileType = "UMTS"; break;// ~ 400-7000 kbps
            /*
			 * Above API level 7, make sure to set android:targetSdkVersion
			 * to appropriate level to use these
			 */
            case TelephonyManager.NETWORK_TYPE_EHRPD: // API level 11
                mobileType = "EHRPD"; break;// ~ 1-2 Mbps
            case TelephonyManager.NETWORK_TYPE_EVDO_B: // API level 9
                mobileType = "EVDO_B"; break;// ~ 5 Mbps
            case TelephonyManager.NETWORK_TYPE_HSPAP: // API level 13
                mobileType = "HSPAP"; break;// ~ 10-20 Mbps
            case TelephonyManager.NETWORK_TYPE_IDEN: // API level 8
                mobileType = "IDEN"; break;// ~25 kbps
            case TelephonyManager.NETWORK_TYPE_LTE: // API level 11
                mobileType = "LTE"; break;// ~ 10+ Mbps
                // Unknown
            case TelephonyManager.NETWORK_TYPE_UNKNOWN:
            default:
                mobileType = "UNKNOWN";
        }
        boolean isFast = Connectivity.isConnectedFast(getActivity().getApplicationContext());

        // set network type in textview
        TextView typeTv = (TextView) getActivity().findViewById(R.id.network_type);
        typeTv.setText(mobileType);

        TextView speedTv = (TextView) getActivity().findViewById(R.id.network_speed);
        if (isFast) {
            speedTv.setText("Speed: Fast");
        } else {
            speedTv.setText("Speed: Slow");
        }
    }

}