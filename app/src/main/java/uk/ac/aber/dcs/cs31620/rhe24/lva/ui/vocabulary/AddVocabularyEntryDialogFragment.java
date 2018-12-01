package uk.ac.aber.dcs.cs31620.rhe24.lva.ui.vocabulary;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import uk.ac.aber.dcs.cs31620.rhe24.lva.R;

/**
 * AddVocabularyEntryDialogFragment.java
 *
 * A dialog modal to add new Vocabulary Entry into the vocabulary list
 * @author Rhys Evans
 * @version 30/11/2018
 */
public class AddVocabularyEntryDialogFragment extends DialogFragment {

    // Empty Constructor
    public AddVocabularyEntryDialogFragment(){
    }

    /**
     * Create instance of dialog
     * @return
     */
    public static AddVocabularyEntryDialogFragment newInstance(){
        return new AddVocabularyEntryDialogFragment();
    }

    /**
     * Inflate the dialog view and add dialog buttons
     * @param savedInstanceState
     * @return
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.DialogTheme);

        // Get the activity's layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Build the dialog by inflating view and setting buttons
        builder.setView(inflater.inflate(R.layout.fragment_add_vocabulary_entry, null));

        // Add buttons
        // TODO: Add behaviour via viewmodel
        builder.setPositiveButton(R.string.dialog_add_word, null);
        builder.setNegativeButton(R.string.dialog_cancel, null);

        return builder.create();
    }
}
