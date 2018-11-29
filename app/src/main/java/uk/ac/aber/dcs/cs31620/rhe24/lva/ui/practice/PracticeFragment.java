package uk.ac.aber.dcs.cs31620.rhe24.lva.ui.practice;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import uk.ac.aber.dcs.cs31620.rhe24.lva.R;

/**
 * PracticeFragment
 * @author Rhys Evans
 * @version 21/11/2018
 */
public class PracticeFragment extends Fragment {


    public PracticeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_practice, container, false);
    }
}
