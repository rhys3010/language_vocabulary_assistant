package uk.ac.aber.dcs.cs31620.rhe24.lva.ui.vocabulary;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import uk.ac.aber.dcs.cs31620.rhe24.lva.R;
import uk.ac.aber.dcs.cs31620.rhe24.lva.model.vocabulary.VocabularyListRecyclerAdapter;

/**
 * VocabularyListFragment
 * The fragment that displays all vocabulary entries using a recyclerview
 * @author Rhys Evans
 * @version 21/11/2018
 */
public class VocabularyListFragment extends Fragment {

    /**
     * Recycler View adapter for the vocabulary list
     */
    private VocabularyListRecyclerAdapter vocabularyListAdapter;


    public VocabularyListFragment() {
        // Required empty public constructor
    }


    /**
     * OnCreate Method to perform all operations needed on fragment creation
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Create view by inflating layout
        final View view = inflater.inflate(R.layout.fragment_vocabulary_list, container, false);

        // Initialize recycler view
        RecyclerView vocabularyList = view.findViewById(R.id.vocabulary_list);
        vocabularyList.setHasFixedSize(true);
        vocabularyList.setLayoutManager(new LinearLayoutManager(getContext()));

        vocabularyListAdapter = new VocabularyListRecyclerAdapter(getContext());

        // Attach Adapter to Recycler View
        vocabularyList.setAdapter(vocabularyListAdapter);

        return view;
    }

}
