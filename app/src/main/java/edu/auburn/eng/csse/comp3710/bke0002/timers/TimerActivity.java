package edu.auburn.eng.csse.comp3710.bke0002.timers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class TimerActivity extends FragmentActivity implements OptionsFragment.onFragmentInteractionListener,
        NoteInfoFragment.onFragmentInteractionListener, ReviewTimersFragment.onFragmentInteractionListener {
    int mMarkerNumber = 0;
    String mTimerInfoString = "";
    String timeString = "";
    String TAG = "Timers";
    StickyTimesTimer mTimer;
    long mNoteTime;
    boolean mIsTimerDone = false;

    private static final String KEY_TIMER_INFO = "infoString";
    private static final String KEY_MARKER_NUMBER = "markerNumber";
    private static final String KEY_TIMER_ID = "timerID";
    private static final String KEY_NOTE_TIME = "noteTime";
    private static final String KEY_TIMER_DONE = "isTimerDone";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        //Get the bundle (saved timer name and description)
        Bundle bundle = getIntent().getExtras();
        String timerName = bundle.getString("name");
        String timerDescription = bundle.getString("description");

        if (savedInstanceState != null) {
            mTimerInfoString = savedInstanceState.getString(KEY_TIMER_INFO);
            mMarkerNumber = savedInstanceState.getInt(KEY_MARKER_NUMBER);
            int timerID = savedInstanceState.getInt(KEY_TIMER_ID);
            mNoteTime = savedInstanceState.getLong(KEY_NOTE_TIME);
            mIsTimerDone = savedInstanceState.getBoolean(KEY_TIMER_DONE);
            StickyTimesTimer allTimers[] = StickyTimesTimer.GetTimers(getApplicationContext());
            for (StickyTimesTimer timer: allTimers) {
                if (timer.TimerId == timerID){
                    mTimer = timer;
                    Log.i(TAG, "old timer located");
                    break;
                }
            }

        }
        else {
            mTimer = new StickyTimesTimer
                    (getApplicationContext(), timerName, timerDescription, System.currentTimeMillis());
        }

        FragmentManager fm = getSupportFragmentManager();
        Fragment wholeFrag = fm.findFragmentById(R.id.wholeFragment);

        Log.i(TAG, "mIsTimerDone == " + mIsTimerDone);

        if (mIsTimerDone) {
            // review fragment loaded
//            fm.beginTransaction().remove(wholeFrag);
//            fm.executePendingTransactions();
//            ReviewTimersFragment reviewTimersFragment = new ReviewTimersFragment();
//            fm.beginTransaction().add(R.id.wholeFragment, reviewTimersFragment).commit();
//            fm.executePendingTransactions();

            // send array of markers to the new activity
            ((ReviewTimersFragment) wholeFrag).displayMarkers(mTimer);
            ((ReviewTimersFragment) wholeFrag).setButtonText("Menu");

        }
        else if(wholeFrag == null) {
            Log.i(TAG, "inside wholeFragment is null code");
            wholeFrag = new InterimFragment();
            fm.beginTransaction().add(R.id.wholeFragment, wholeFrag).commit();
            fm.executePendingTransactions();

            Fragment optFrag = new OptionsFragment();
            fm.beginTransaction()
                    .add(R.id.topContainerTimer, optFrag).commit();

            Fragment bottomFrag = new TimerInfoFragment();
            fm.beginTransaction()
                    .add(R.id.bottomContainerTimer, bottomFrag).commit();
            fm.executePendingTransactions();
        }
        else {

        }

        Log.i(TAG, "end of onCreate() TimerActivity");
    }

    public void onButtonPressed(String buttonName){

        if (buttonName.equals("marker")){
            mMarkerNumber++;
            // call getTime()
            long markerTime = mTimer.AddMarker(getApplicationContext(), "Marker " + mMarkerNumber, System.currentTimeMillis());

            String marker = "Marker " + mMarkerNumber;

            int numberOfSpaces = (35 - marker.length());
            Log.i(TAG, "Number of Spaces printed: " + numberOfSpaces);
            for (int i = 0; i < numberOfSpaces; i++) {
                marker += "_";
            }
            marker += markerTime/1000.0 + "\n" + mTimerInfoString;

            Fragment timerInfoFragment = getSupportFragmentManager().findFragmentById(R.id.bottomContainerTimer);
            ((TimerInfoFragment) timerInfoFragment).reDisplayNotes(marker);
            mTimerInfoString = marker;

        }
        else if (buttonName.equals("note")){

            mNoteTime = System.currentTimeMillis();

            // replace TimerInfoFragment with NoteInfoFragment
            Log.i(TAG, "before note frag loaded");
            FragmentManager fm = getSupportFragmentManager();
            NoteInfoFragment noteFrag = new NoteInfoFragment();
            fm.beginTransaction().replace(R.id.bottomContainerTimer, noteFrag).commit();
            fm.executePendingTransactions();

            Log.i(TAG, "after note frag loaded");
        }
        else {
            // stop timer and save end time
            // save all info to database
            mTimer.SetLength(System.currentTimeMillis());
            StickyTimesTimer.SaveTimer(getApplicationContext(), mTimer);

            // go to Review Timer
            FragmentManager fm = getSupportFragmentManager();
            fm.beginTransaction().remove(fm.findFragmentById(R.id.bottomContainerTimer)).commit();
            fm.beginTransaction().remove(fm.findFragmentById(R.id.topContainerTimer)).commit();

            ReviewTimersFragment reviewFrag = new ReviewTimersFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("timerId", mTimer.TimerId);
            reviewFrag.setArguments(bundle);
            fm.beginTransaction().replace(R.id.wholeFragment, reviewFrag).commit();
            fm.executePendingTransactions();

            mIsTimerDone = true;
            // send array of markers to the new activity
            ((ReviewTimersFragment) reviewFrag).setButtonText("Menu");

        }
    }

    public void onNoteDoneButtonPressed(String noteDescription) {
        // save note contents

        Log.i(TAG, "note done button pressed:  " + noteDescription);
        // go back to timerInfoFrag
        FragmentManager fm = getSupportFragmentManager();
        TimerInfoFragment timerInfoFrag = new TimerInfoFragment();
        fm.beginTransaction().replace(R.id.bottomContainerTimer, timerInfoFrag).commit();
        fm.executePendingTransactions();

        long noteOffset = mTimer.AddMarker(getApplicationContext(), noteDescription, mNoteTime);

        String noteName = noteDescription.trim();

        int numberOfSpaces = (35 - noteName.length());
        Log.i(TAG, "Number of Spaces printed: " + numberOfSpaces);
        for (int i = 0; i < numberOfSpaces; i++) {
            noteName += "_";
        }
        noteName += noteOffset/1000.0 + "\n" + mTimerInfoString;

        displayNotes(noteName, timerInfoFrag);
        mTimerInfoString = noteName;

    }

    public void onMenuButtonPressed() {
        Intent i = new Intent(TimerActivity.this, MainActivity.class);
        i.putExtra("IsFirstLaunch", false);
        startActivity(i);
    }

    private void displayNotes(String newNote, Fragment timerInfoFragment) {
        // call method in TimerInfoFragment

        ((TimerInfoFragment) timerInfoFragment).reDisplayNotes(newNote);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(KEY_TIMER_INFO, mTimerInfoString);
        outState.putInt(KEY_MARKER_NUMBER, mMarkerNumber);
        outState.putInt(KEY_TIMER_ID, mTimer.TimerId);
        outState.putLong(KEY_NOTE_TIME, mNoteTime);
        outState.putBoolean(KEY_TIMER_DONE, mIsTimerDone);
    }
}
