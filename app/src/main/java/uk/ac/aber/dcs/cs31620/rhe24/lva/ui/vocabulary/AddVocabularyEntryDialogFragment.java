package uk.ac.aber.dcs.cs31620.rhe24.lva.ui.vocabulary;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import uk.ac.aber.dcs.cs31620.rhe24.lva.R;

/**
 * AddVocabularyEntryDialogFragment.java
 *
 * A dialog modal to add new Vocabulary Entry into the vocabulary list
 * @author Rhys Evans
 * @version 30/11/2018
 */
public class AddVocabularyEntryDialogFragment extends DialogFragment {

    private static final String TITLE_KEY = "TITLE";


    // Empty Constructor
    public AddVocabularyEntryDialogFragment(){
    }

    /**
     * Create instance of dialog and provide title
     * @param title
     * @return
     */
    public static AddVocabularyEntryDialogFragment newInstance(String title){
        AddVocabularyEntryDialogFragment fragment = new AddVocabularyEntryDialogFragment();
        Bundle args = new Bundle();
        args.putString(TITLE_KEY, title);
        fragment.setArguments(args);

        return fragment;
    }

    /**
     * Inflate the dialog fragment's layout
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_add_vocabulary_entry, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        // Set dialog's title
        getDialog().setTitle(getArguments().getString(TITLE_KEY, ""));
    }
}
