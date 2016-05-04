package edu.auburn.eng.csse.comp3710.bke0002.timers;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends FragmentActivity
        implements TitleFragment.onFragmentInteractionListener, NewTimerFragment.onFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.mainFrag);

        if (fragment == null) {
            fragment = new TitleFragment();
            fm.beginTransaction()
                    .add(R.id.mainFrag, fragment).commit();
            fm.executePendingTransactions();

            final Handler handler = new Handler();

            Bundle b = getIntent().getExtras();
            if (b == null || b.getBoolean("IsFirstLaunch")) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        FrameLayout splashScreen = (FrameLayout) findViewById(R.id.splashScreen);
                        if (splashScreen != null) {
                            splashScreen.setVisibility(View.GONE);
                        }
                    }
                }, 3000);
            }
            else
            {
                FrameLayout splashScreen = (FrameLayout) findViewById(R.id.splashScreen);
                if (splashScreen != null) {
                    splashScreen.setVisibility(View.GONE);
                }
            }
        }
        else
        {
            FrameLayout splashScreen = (FrameLayout) findViewById(R.id.splashScreen);
            if (splashScreen != null) {
                splashScreen.setVisibility(View.GONE);
            }
        }
    }

    public void onNewTimerButtonPressed() {

        FragmentManager fm = getSupportFragmentManager();
        Fragment newTimerFrag = new NewTimerFragment();

        fm.beginTransaction().replace(R.id.mainFrag, newTimerFrag).commit();
        fm.executePendingTransactions();

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
