package dhbk.android.wifi2.fragments.historyFragments;


import android.annotation.TargetApi;
import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import dhbk.android.wifi2.R;
import dhbk.android.wifi2.interfaces.OnFragInteractionListener;
import dhbk.android.wifi2.models.WifiModel;
import dhbk.android.wifi2.utils.Constant;
import dhbk.android.wifi2.utils.db.NetworkDb;

/*
contain tasks relating to background or get datas from db and show it to it's child fragment.
replace|add|pop child fragment
 */
public class HistoryPresenterFragment extends Fragment implements
        OnFragInteractionListener.OnHistoryFragInteractionListener{
    private static final String TAG = HistoryPresenterFragment.class.getSimpleName();
    private OnFragInteractionListener.OnMainFragInteractionListener mMainListener;

    @BindView(R.id.historyContainer)
    FrameLayout mHistoryContainer;

    public HistoryPresenterFragment() {
    }

    public static HistoryPresenterFragment newInstance() {
        return new HistoryPresenterFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragInteractionListener.OnMainFragInteractionListener) {
            mMainListener = (OnFragInteractionListener.OnMainFragInteractionListener) context;
        } else {
            throw new ClassCastException(context.toString() + " must implement OnRageComicSelected.");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mMainListener = null;
    }

    // make this method not to restart everytime config occurs
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    // add HistoryWifiMobileFragment to show a list of wifi and mobile history.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history_container, container, false);
        HistoryWifiMobileFragment historyWifiMobileFragment = new HistoryWifiMobileFragment();
        getChildFragmentManager()
                .beginTransaction()
                .add(R.id.historyContainer, historyWifiMobileFragment, Constant.TAG_HISTORY_WIFI_MOBILE_FRAGMENT)
                .commit();
        ButterKnife.bind(this, view);
        return view;
    }


    /*
    replace HistoryWifiMobileFragment with HistoryChildShowDetailWifiFragment to show a detail of a specific wifi hotspot
    after a user has clicked a right arrow icon with circular reveal animation.
     */
    public void replaceWithShowWifiDetailFrag(WifiModel myListItem) {
        HistoryChildShowDetailWifiFragment historyChildShowDetailWifiFragment = HistoryChildShowDetailWifiFragment.newInstance(myListItem);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // add arc motion when replace replace with another fragment
            Transition arcMotionTransition = TransitionInflater.from(getActivity()).inflateTransition(R.transition.changebounds_with_arcmotion); // chuyển cái nút từ bên dưới đi lên trên
            historyChildShowDetailWifiFragment.setSharedElementEnterTransition(arcMotionTransition);

            arcMotionTransition.addListener(new Transition.TransitionListener() {
                @Override
                public void onTransitionStart(Transition transition) {
                }

                // : 6/12/16 start the Circular Reveal Animation when arc motion ends.
                @TargetApi(Build.VERSION_CODES.KITKAT)
                @Override
                public void onTransitionEnd(Transition transition) {
                    transition.removeListener(this);
                    // after arc motion that makes fab move from bottom to center of the screen.
                    // make another animation called circular reveal.
                    Fragment topFrag = getChildFragmentManager().findFragmentByTag(Constant.TAG_HISTORY_WIFI_DETAIL_FRAGMENT);
                    if (topFrag instanceof HistoryChildShowDetailWifiFragment) {
                        ((HistoryChildShowDetailWifiFragment) topFrag).animateRevealShow();
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

            // Find the shared element (in Fragment A)
            FloatingActionButton fab = (FloatingActionButton) mHistoryContainer.findViewById(R.id.fab_reveal_detail_wifi);
            if (fab != null) {
                getChildFragmentManager()
                        .beginTransaction()
                        .addSharedElement(fab, fab.getTransitionName())
                        .replace(R.id.historyContainer, historyChildShowDetailWifiFragment, Constant.TAG_HISTORY_WIFI_DETAIL_FRAGMENT)
                        .addToBackStack(null)
                        .commit();
            } else {
                Log.e(TAG, "replaceWithShowWifiDetailFrag: Not find fab button in History Fragment");
            }
        } else {
            getChildFragmentManager()
                    .beginTransaction()
                    .replace(R.id.historyContainer, historyChildShowDetailWifiFragment, Constant.TAG_HISTORY_WIFI_DETAIL_FRAGMENT)
                    .addToBackStack(null)
                    .commit();
        }
    }

    // pop a top frag in backstack if have one.
    public void popHistoryShowDetailWifiFragment() {
        getChildFragmentManager().popBackStack();
    }

    // pop out HistoryCHildShowDetailWifiFragment and show animation when close
    public void popShowWifiDetailFragment() {
        Fragment wifiDetailFrag = getChildFragmentManager().findFragmentByTag(Constant.TAG_HISTORY_WIFI_DETAIL_FRAGMENT);
        if (wifiDetailFrag instanceof HistoryChildShowDetailWifiFragment) {
            ((HistoryChildShowDetailWifiFragment) wifiDetailFrag).closeFragment();
        }
    }

    // replace with MainFragment
    public void replaceWithMainFrag() {
        mMainListener.onMainFragReplace();
    }

    // get wifi data from db
    public void getWifiDataFromDb() {
        NetworkDb networkDb = NetworkDb.getInstance(getActivity());
        networkDb.getWifiHistory(this);
    }

    // get mobile data from db
    public void getMobileDataFromDb() {
        NetworkDb networkDb = NetworkDb.getInstance(getActivity());
        networkDb.getMobileHistory(this);
    }

    // a callback from db with data wifi history result.
    @Override
    public void onGetWifiHistoryCursor(Cursor cursor) {
        Fragment childFragment = getChildFragmentManager().findFragmentByTag(Constant.TAG_HISTORY_WIFI_MOBILE_FRAGMENT);
        if (childFragment instanceof HistoryWifiMobileFragment) {
            ((HistoryWifiMobileFragment)childFragment).getWifiFragmentAndPassWifiCursor(cursor);
        }
    }

    // a callback from db with data mobile history result.
    @Override
    public void onGetMobileHistoryCursor(Cursor cursor) {
        Fragment childFragment = getChildFragmentManager().findFragmentByTag(Constant.TAG_HISTORY_WIFI_MOBILE_FRAGMENT);
        if (childFragment instanceof HistoryWifiMobileFragment) {
            ((HistoryWifiMobileFragment)childFragment).getMobileFragmentAndPassWifiCursor(cursor);
        }
    }


}