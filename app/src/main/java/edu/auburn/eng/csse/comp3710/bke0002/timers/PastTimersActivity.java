package edu.auburn.eng.csse.comp3710.bke0002.timers;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class PastTimersActivity extends FragmentActivity implements OldTimersFragment.onFragmentInteractionListener,
        ReviewTimersFragment.onFragmentInteractionListener {

    int mTimerID;
    public static final String KEY_ID = "timerID";
    public static final String KEY_FRAG = "isRevFrag";
    StickyTimesTimer mTimer;
    boolean mIsReviewTimerFrag = false;
    private static final String TAG = "Timers";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_timers);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.pastTimers);

        if (savedInstanceState != null) {
            mTimerID = savedInstanceState.getInt(KEY_ID);
            StickyTimesTimer allTimers[] = StickyTimesTimer.GetTimers(getApplicationContext());
            mIsReviewTimerFrag = savedInstanceState.getBoolean(KEY_FRAG);
            for (StickyTimesTimer timer: allTimers) {
                if (timer.TimerId == mTimerID){
                    mTimer = timer;
                    break;
                }
            }

        }

        if (fragment == null) {
            fragment = new OldTimersFragment();
            fm.beginTransaction()
                    .add(R.id.pastTimers, fragment).commit();
        }
        else {
            if (mIsReviewTimerFrag){
                Log.i(TAG, "reloading review timer frag");
                ((ReviewTimersFragment)fragment).displayMarkers(mTimer);
                ((ReviewTimersFragment)fragment).setButtonText("Browse Timers");
            }
        }
    }

    public void onTimerSelected(StickyTimesTimer timerSelected) {
        mIsReviewTimerFrag = true;
        mTimerID = timerSelected.TimerId;
        FragmentManager fm = getSupportFragmentManager();
        ReviewTimersFragment reviewFrag = new ReviewTimersFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("timerId", timerSelected.TimerId);
        reviewFrag.setArguments(bundle);
        fm.beginTransaction().replace(R.id.pastTimers, reviewFrag).commit();
        fm.executePendingTransactions();



//        // send array of markers to the new activity
//        ((ReviewTimersFragment) reviewFrag).displayMarkers(timerSelected);
        ((ReviewTimersFragment) reviewFrag).setButtonText("Browse Timers");
    }

    public void onMenuButtonPressed() {
        mIsReviewTimerFrag = false;
        FragmentManager fm = getSupportFragmentManager();
        OldTimersFragment oldTimersFrag = new OldTimersFragment();
        fm.beginTransaction().replace(R.id.pastTimers, oldTimersFrag).commit();
        fm.executePendingTransactions();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putBoolean(KEY_FRAG, mIsReviewTimerFrag);
        outState.putInt(KEY_ID, mTimerID);

    }
}
