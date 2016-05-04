package edu.auburn.eng.csse.comp3710.bke0002.timers;


import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;


/**
 * A simple {@link Fragment} subclass.
 */
public class OptionsFragment extends Fragment {

    Button mMarkerButton;
    Button mNoteButton;
    Button mStopButton;
    onFragmentInteractionListener mCallback;
    Chronometer mChronometer;
    long savedTime =0;
    private static final String KEY_TIME = "time";

    public OptionsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_options, container, false);

        setRetainInstance(true);

        if (savedInstanceState != null) {
            savedTime = savedInstanceState.getLong(KEY_TIME);
        }

        mChronometer = (Chronometer) v.findViewById(R.id.chronometer);
        mChronometer.setBase(SystemClock.elapsedRealtime());
        mChronometer.start();

        mMarkerButton = (Button) v.findViewById(R.id.marker_button);
        mMarkerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onButtonPressed("marker");
            }
        });
        mNoteButton = (Button) v.findViewById(R.id.note_button);
        mNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onButtonPressed("note");
            }
        });
        mStopButton = (Button) v.findViewById(R.id.stop_button);
        mStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mChronometer.stop();
                //String stopTimer = "" + System.currentTimeMillis();
                mCallback.onButtonPressed("stop");
            }
        });

        return v;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mCallback = (onFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement onFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    public interface onFragmentInteractionListener {
        public void onButtonPressed(String buttonName);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putLong(KEY_TIME, mChronometer.getBase());
    }
}