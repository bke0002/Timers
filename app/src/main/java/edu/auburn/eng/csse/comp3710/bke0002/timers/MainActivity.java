package edu.auburn.eng.csse.comp3710.bke0002.timers;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class MainActivity extends FragmentActivity
        implements TitleFragment.onFragmentInteractionListener, NewTimerFragment.onFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = new TitleFragment();
        fm.beginTransaction()
                .add(R.id.mainFrag, fragment).commit();

    }

    public void onNewTimerButtonPressed() {

        FragmentManager fm = getSupportFragmentManager();
        Fragment newTimerFrag = new NewTimerFragment();

        fm.beginTransaction().replace(R.id.mainFrag, newTimerFrag).commit();

    }

    public void onOldTimersButtonPressed() {
        // Start PastTimersActivity
        Intent i = new Intent(MainActivity.this, PastTimersActivity.class);
        startActivity(i);
    }

    public void onStartTimerButtonPressed(String timerName, String timerDescription) {
        // Start Timer Activity
        Intent i = new Intent(MainActivity.this, TimerActivity.class);
        //Create a new bundle
        Bundle bundle = new Bundle();
        bundle.putString("name", timerName);
        bundle.putString("description", timerDescription);
        i.putExtras(bundle);

        startActivity(i);
    }

}
