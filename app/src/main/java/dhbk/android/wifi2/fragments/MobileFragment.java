package dhbk.android.wifi2.fragments;


import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
        String mobileType = Integer.toString(info.getSubtype());
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
