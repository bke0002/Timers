package edu.auburn.eng.csse.comp3710.bke0002.timers;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewTimerFragment extends Fragment {
    EditText mTimerNameField;
    EditText mTimerDescriptionField;
    String mTimerName = "";
    String mTimerDescription = "";
    Button mStartTimerButton;
    onFragmentInteractionListener mCallback;

    public NewTimerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_new_timer, container, false);

        setRetainInstance(true);

        mTimerNameField = (EditText)v.findViewById(R.id.enterNameHere);
        mTimerNameField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // This intentially left blank
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                /* strips all whitespace and non letters from text entry, converts to lowercase
                   and saves entry for submission*/
                mTimerName = s.toString();
            }
            @Override
            public void afterTextChanged(Editable s) {
                // Left blank as well
            }
        });

        mTimerDescriptionField = (EditText)v.findViewById(R.id.describeTimerHere);
        mTimerDescriptionField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // This intentially left blank
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                /* strips all whitespace and non letters from text entry, converts to lowercase
                   and saves entry for submission*/
                mTimerDescription = s.toString();
            }
            @Override
            public void afterTextChanged(Editable s) {
                // Left blank as well
            }
        });

        mStartTimerButton = (Button) v.findViewById(R.id.startTimerButton);
        mStartTimerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTimerName.equals("")) {
                    // make toast
                    Toast.makeText(getContext(), "You must enter a\nTimer Name", Toast.LENGTH_SHORT).show();
                }
                // TODO check if timer name already exists
                else {
                    mCallback.onStartTimerButtonPressed(mTimerName, mTimerDescription);
                }
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
        public void onStartTimerButtonPressed(String timerName, String timerDescription);
    }

}
