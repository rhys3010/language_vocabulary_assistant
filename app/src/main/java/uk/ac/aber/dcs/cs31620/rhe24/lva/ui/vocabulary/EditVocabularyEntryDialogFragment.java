package uk.ac.aber.dcs.cs31620.rhe24.lva.ui.vocabulary;

import android.app.AlertDialog;
import android.app.Dialog;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import uk.ac.aber.dcs.cs31620.rhe24.lva.R;
import uk.ac.aber.dcs.cs31620.rhe24.lva.model.vocabulary.VocabularyListViewModel;

/**
 * EditVocabularyEntryDialogFragment.java
 *
 * A dialog to edit a vocabulary entry within the vocabulary list
 * @author Rhys Evans
 * @version 1/12/2018
 */
public class EditVocabularyEntryDialogFragment extends DialogFragment {

    /**
     * The key that the word in the primary language is saved under in saved instance state bundle
     */
    private static final String PRIMARY_LANGUAGE_WORD_KEY = "PRIMARY_LANGUAGE_WORD";

    /**
     * The key that the word in the secondary language is saved under in saved instance state bundle
     */
    private static final String SECONDARY_LANGUAGE_WORD_KEY = "SECONDARY_LANGUAGE_WORD";

    /**
     * The view model class to interface with the persistent data
     */
    private VocabularyListViewModel vocabularyListViewModel;

    /**
     * The view object of the primary word input
     */
    private EditText primaryWordInput;

    /**
     * The view object of the secondary word input
     */
    private EditText secondaryWordInput;

    // Empty Constructor
    public EditVocabularyEntryDialogFragment(){}

    /**
     * Create instance of dialog and pass the needed arguments
     * @return
     */
    public static EditVocabularyEntryDialogFragment newInstance(){
        return new EditVocabularyEntryDialogFragment();
    }

    /**
     * Inflate the dialog view and setup all views
     * @param savedInstanceState
     * @return
     */
    @Override
    @Nullable
    public Dialog onCreateDialog(Bundle savedInstanceState){
        // Initialize the view model
        vocabularyListViewModel = ViewModelProviders.of(this).get(VocabularyListViewModel.class);

        // Create the dialog builder
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.DialogTheme);
        LayoutInflater inflater =  getActivity().getLayoutInflater();

        // Create the dialog's view
        View view = inflater.inflate(R.layout.fragment_manage_vocabulary_entry, null);

        setupInputsAndLabels(view, savedInstanceState);

        builder.setView(view);

        // Add buttons
        // TODO: Add behaviour via viewmodel
        builder.setPositiveButton(R.string.dialog_edit_word, null);
        builder.setNegativeButton(R.string.dialog_cancel, null);

        return builder.create();
    }

    /**
     * When instance state is saved, add primary word and secondary word to bundle
     * @param outState
     */
    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);

        outState.putString(PRIMARY_LANGUAGE_WORD_KEY, primaryWordInput.getText().toString());
        outState.putString(SECONDARY_LANGUAGE_WORD_KEY, secondaryWordInput.getText().toString());
    }

    /**
     * Setup all the labels to display correct languages and populate word input
     * from either saved instance or from the contents of the current entry
     *
     * @param view
     * @param savedInstanceState
     */
    private void setupInputsAndLabels(View view, Bundle savedInstanceState){
        // Initialize all labels;
        TextView dialogTitle = view.findViewById(R.id.manage_vocabulary_entry_title);
        TextView primaryLanguageLabel = view.findViewById(R.id.primary_word_input_label);
        TextView secondaryLanguageLabel = view.findViewById(R.id.secondary_word_input_label);

        // Initialize the word inputs
        primaryWordInput = view.findViewById(R.id.primary_word_input);
        secondaryWordInput = view.findViewById(R.id.secondary_word_input);

        // Set title label
        dialogTitle.setText(R.string.dialog_edit_word_title);

        // Set the word input labels to contain correct languages
        primaryLanguageLabel.setText(getString(R.string.dialog_add_word_input_label, vocabularyListViewModel.getPrimaryLanguage()));
        secondaryLanguageLabel.setText(getString(R.string.dialog_add_word_input_label, vocabularyListViewModel.getSecondaryLanguage()));

        // If a savedInstanceState bundle contains the edit text contents, place them in there
        if(savedInstanceState != null){
            if(savedInstanceState.containsKey(PRIMARY_LANGUAGE_WORD_KEY)){
                primaryWordInput.setText(savedInstanceState.getString(PRIMARY_LANGUAGE_WORD_KEY));
            }

            if(savedInstanceState.containsKey(SECONDARY_LANGUAGE_WORD_KEY)){
                secondaryWordInput.setText(savedInstanceState.getString(SECONDARY_LANGUAGE_WORD_KEY));
            }
        }
    }
}
