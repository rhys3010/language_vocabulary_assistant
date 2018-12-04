package uk.ac.aber.dcs.cs31620.rhe24.lva.ui.practice;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import uk.ac.aber.dcs.cs31620.rhe24.lva.R;
import uk.ac.aber.dcs.cs31620.rhe24.lva.ui.LVAMainActivity;

/**
 * PracticeOverviewFragment
 * @author Rhys Evans
 * @version 21/11/2018
 */
public class PracticeOverviewFragment extends Fragment {


    public PracticeOverviewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Create the view for this fragment by inflating the layuout
        final View view = inflater.inflate(R.layout.fragment_practice_overview, container, false);

        // Setup button action
        Button submitButton = view.findViewById(R.id.practice_start_button);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Switch To Test Activity
                Intent intent = new Intent(getActivity(), PracticeActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
