package dhbk.android.wifi2.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import dhbk.android.wifi2.R;

/**
 * Created by phongdth.ky on 6/16/2016.
 */

// : 6/16/2016 copy this dialogfragment to sublime text
public class ChildConnectWifiDialogFragment extends DialogFragment {
    private static final String ARG_SSID = "ssid";
    private static final String ARG_POSITION = "position";
    private String mSsid;
    private int mPosition;

    //  create a instance contructor to get ssid, and position
    public static ChildConnectWifiDialogFragment newInstance(@NonNull String ssid, int position) {
        ChildConnectWifiDialogFragment f = new ChildConnectWifiDialogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_SSID, ssid);
        args.putInt(ARG_POSITION, position);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mSsid = getArguments().getString(ARG_SSID);
            mPosition = getArguments().getInt(ARG_POSITION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_wifi_input_pass, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView ssidTv = (TextView) view.findViewById(R.id.ssid);
        ssidTv.setText(mSsid);

        final EditText edtPass= (EditText) view.findViewById(R.id.pass);

        // : 6/16/2016 listen two button
        Button agreeBtn = (Button) view.findViewById(R.id.agree);
        agreeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment parentParent = getParentFragment();
                if (parentParent instanceof WifiFragment) {
                    String pass = edtPass.getText().toString();
                    ((WifiFragment) parentParent).onAuthen(pass, mPosition);
                }
                dismiss();
            }
        });

        final Button disagreeBtn = (Button) view.findViewById(R.id.disagree);
        disagreeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
