package edu.auburn.eng.csse.comp3710.bke0002.timers;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 */
public class NoteInfoFragment extends Fragment {

    Button mDoneButton;
    onFragmentInteractionListener mCallback;
    EditText mNoteDescriptionField;
    String mNoteDescription = "";

    String TAG = "Timers";


    public NoteInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_note_info, container, false);

        setRetainInstance(true);

        mNoteDescriptionField = (EditText)v.findViewById(R.id.describeNoteHere);
        mNoteDescriptionField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // This intentially left blank
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                /* strips all whitespace and non letters from text entry, converts to lowercase
                   and saves entry for submission*/
                mNoteDescription = s.toString();
            }
            @Override
            public void afterTextChanged(Editable s) {
                // Left blank as well
            }
        });

        mDoneButton = (Button) v.findViewById(R.id.note_done_button);
        mDoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, mNoteDescription + "onNoteDoneButtonPressed");
                mCallback.onNoteDoneButtonPressed(mNoteDescription);

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
        public void onNoteDoneButtonPressed(String mNoteDescription);

    }

}
