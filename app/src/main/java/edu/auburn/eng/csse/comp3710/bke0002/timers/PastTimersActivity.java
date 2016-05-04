package edu.auburn.eng.csse.comp3710.bke0002.timers;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class PastTimersActivity extends AppCompatActivity implements OldTimersFragment.onFragmentInteractionListener,
        ReviewTimersFragment.onFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_timers);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = new OldTimersFragment();
        fm.beginTransaction()
                .add(R.id.pastTimers, fragment).commit();
    }

    public void onTimerSelected(StickyTimesTimer timerSelected) {
        FragmentManager fm = getSupportFragmentManager();
        ReviewTimersFragment reviewFrag = new ReviewTimersFragment();
        fm.beginTransaction().replace(R.id.pastTimers, reviewFrag).commit();
        fm.executePendingTransactions();

        // send array of markers to the new activity
        ((ReviewTimersFragment) reviewFrag).displayMarkers(timerSelected);
        ((ReviewTimersFragment) reviewFrag).setButtonText("Browse Timers");
    }

    public void onMenuButtonPressed() {
        FragmentManager fm = getSupportFragmentManager();
        OldTimersFragment oldTimersFrag = new OldTimersFragment();
        fm.beginTransaction().replace(R.id.pastTimers, oldTimersFrag).commit();
        fm.executePendingTransactions();
    }
}
