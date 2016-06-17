package dhbk.android.wifi2.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import dhbk.android.wifi2.R;
import dhbk.android.wifi2.interfaces.OnFragInteractionListener;

public class MainFragment extends Fragment {
    private static final String TAG = MainFragment.class.getSimpleName();
    private OnFragInteractionListener.OnMainFragInteractionListener mListener;

    public MainFragment() {
    }

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }
    
    
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button scanWifiButton = (Button) view.findViewById(R.id.button_scan_wifi);
        try {
            scanWifiButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.onWifiFragReplace();
                    }
                }
            });
        } catch (NullPointerException e) {
            Log.e(TAG, "onViewCreated: " + e.toString());
        }

        // : 6/15/2016 listen when click HISTORY textview
        TextView historyTextView = (TextView) view.findViewById(R.id.tv_history);
        historyTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onHistoryFragReplace();
                }
            }
        });

        // : 6/15/2016 listen when click MOBILE textview
        TextView mobileTextView = (TextView) view.findViewById(R.id.tv_mobile);
        mobileTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onMobileFragReplace();
                }
            }
        });

        Button showWifiHotspotMap = (Button) view.findViewById(R.id.button_show_map);
        showWifiHotspotMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onHistoryWithOsmMapFragReplace();
                }
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragInteractionListener.OnMainFragInteractionListener) {
            mListener = (OnFragInteractionListener.OnMainFragInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


}
