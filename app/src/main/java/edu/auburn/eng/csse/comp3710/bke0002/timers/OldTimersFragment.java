package edu.auburn.eng.csse.comp3710.bke0002.timers;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class OldTimersFragment extends Fragment {

    ArrayAdapter<String> mTimerArrayAdapter;
    ListView mTimerListView;
    onFragmentInteractionListener mCallback;
    String TAG = "Timers";
    public static final int ORIENTATION_LANDSCAPE = 2;

    public OldTimersFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_old_timers, container, false);

        setRetainInstance(true);

        int width;
        if (getResources().getConfiguration().orientation == ORIENTATION_LANDSCAPE) {
            width = 60;
        }
        else {
            width = 30;
        }

        final StickyTimesTimer timers[] = StickyTimesTimer.GetTimers(getContext());
        List<String> timerList = new ArrayList<String>();
        for (StickyTimesTimer timer:timers){
            String timerName = (timer.Name.substring(6)).trim();
            int numberOfSpaces = (width - timerName.length());
            //Log.i(TAG, "Number of Spaces printed: " + numberOfSpaces);
            for (int i = 0; i < numberOfSpaces; i++) {
                timerName += "_";
            }
            timerList.add(timerName + timer.Length/1000.0);
        }

        mTimerArrayAdapter = new ArrayAdapter<String>
                (v.getContext(), android.R.layout.simple_list_item_1, timerList);

        mTimerListView = (ListView) v.findViewById(R.id.pastTimersListView);
        mTimerListView.setAdapter(mTimerArrayAdapter);

        mTimerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // get timer object from earlier array.
                StickyTimesTimer aTimer = timers[position];
                StickyTimesMarker timerMarkers[] = aTimer.GetMarkers();

                mCallback.onTimerSelected(aTimer);
            }
        });

        mTimerListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {

                StickyTimesTimer aTimer = timers[position];
                String timerDescription = aTimer.Description;

                Toast.makeText(getContext(), timerDescription, Toast.LENGTH_LONG).show();

                return true;
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
        public void onTimerSelected(StickyTimesTimer timerSelected);
    }



}
