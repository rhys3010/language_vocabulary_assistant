package rhe24.cs31620.dcs.aber.ac.uk.lva.ui.vocabulary_list;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import rhe24.cs31620.dcs.aber.ac.uk.lva.R;

/**
 * VocabularyListFragment
 * @author Rhys Evans
 * @version 21/11/2018
 */
public class VocabularyListFragment extends Fragment {


    public VocabularyListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_vocabulary_list, container, false);
    }

}
