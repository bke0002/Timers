package edu.auburn.eng.csse.comp3710.bke0002.timers;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 */
public class TitleFragment extends Fragment {

    Button mNewTimerButton;
    Button mOldTimersButton;
    onFragmentInteractionListener mCallback;


    public TitleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_title, container, false);

        setRetainInstance(true);

        mNewTimerButton = (Button) v.findViewById(R.id.newTimerButton);
        mNewTimerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onNewTimerButtonPressed();
            }
        });

        mOldTimersButton = (Button) v.findViewById(R.id.oldTimersButton);
        mOldTimersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onOldTimersButtonPressed();
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
        public void onNewTimerButtonPressed();
        public void onOldTimersButtonPressed();
    }


}