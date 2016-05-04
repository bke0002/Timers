package edu.auburn.eng.csse.comp3710.bke0002.timers;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class TimerInfoFragment extends Fragment {

    private TextView mNotesText;
    private String mNotesString = "";
    String TAG = "Timers";
    private View mView;
    String mMarkerString;

    private static final String KEY_MARKERS= "markers";

    public TimerInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView = inflater.inflate(R.layout.fragment_timer_info, container, false);

        setRetainInstance(true);
        mNotesText = (TextView) mView.findViewById(R.id.outputNotes);

//        if (savedInstanceState != null) {
//            mMarkerString = savedInstanceState.getString(KEY_MARKERS);
//            reDisplayNotes(mMarkerString);
//        }

        return mView;
    }

    public void reDisplayNotes(String newNotes) {
        Log.i(TAG, "reDisplayNotes is called " + newNotes);
        mNotesText.setText(newNotes);
    }

//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//
//        outState.putString("KEY_MARKERS", mMarkerString);
//
//    }

}
