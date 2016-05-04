package edu.auburn.eng.csse.comp3710.bke0002.timers;


import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ReviewTimersFragment extends Fragment {

    Button mMenuButton;
    Button mDescriptionButton;
    onFragmentInteractionListener mCallback;
    private View v;
    private ArrayAdapter<String> mMarkerArrayAdapter;
    private ListView mListView;
    String thisTimerName = "";
    String thisTimerDescription = "";

    String TAG = "Timers";

    public ReviewTimersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_review_timers, container, false);

        setRetainInstance(true);

        mMenuButton = (Button) v.findViewById(R.id.menuButton);
        mMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onMenuButtonPressed();
            }
        });
        mDescriptionButton = (Button) v.findViewById(R.id.descriptionButton);
        mDescriptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getContext(), thisTimerDescription , Toast.LENGTH_LONG).show();
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
        public void onMenuButtonPressed();
    }

    public void displayMarkers(StickyTimesTimer thisTimer) {
        StickyTimesMarker markers[] = thisTimer.GetMarkers();
        String titleTextString = thisTimer.Name.substring(5);

        TextView titleText = (TextView) v.findViewById(R.id.reviewTimerText);
        titleText.setText(titleTextString);
        thisTimerDescription = thisTimer.Description;
        thisTimerName = titleTextString;

        List<String> markerArray = new ArrayList<String>();
        Log.i(TAG, "String marker Array created");

        for (StickyTimesMarker marker:markers){
            markerArray.add("" + (marker.Offset/1000.0) + "   "  + marker.Description) ;
        }

        mMarkerArrayAdapter = new ArrayAdapter<String>
                (v.getContext(), android.R.layout.simple_list_item_1, markerArray);


        mListView = (ListView) v.findViewById(R.id.listView);
        if (v == null) {
            Log.i(TAG, "v is null");
        }
        mListView.setAdapter(mMarkerArrayAdapter);

    }

    public void setButtonText(String newButtonText) {
        Button button = (Button) v.findViewById(R.id.menuButton);
        button.setText(newButtonText);
    }
}
