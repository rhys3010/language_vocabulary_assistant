package uk.ac.aber.dcs.cs31620.rhe24.lva.ui.vocabulary;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.lang3.StringUtils;

import uk.ac.aber.dcs.cs31620.rhe24.lva.R;
import uk.ac.aber.dcs.cs31620.rhe24.lva.model.vocabulary.VocabularyEntry;
import uk.ac.aber.dcs.cs31620.rhe24.lva.model.vocabulary.VocabularyListViewModel;

/**
 * AddVocabularyEntryDialogFragment.java
 *
 * A dialog modal to add a Vocabulary Entry to the vocabulary list
 * @author Rhys Evans
 * @version 30/11/2018
 */
public class AddVocabularyEntryDialogFragment extends DialogFragment {

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
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Initialize View Model
        vocabularyListViewModel = ViewModelProviders.of(this).get(VocabularyListViewModel.class);

        // The dialog builder
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.DialogTheme);
        // Get the activity's layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        // Create the dialog view by inflating the layout
        View view = inflater.inflate(R.layout.fragment_manage_vocabulary_entry, null);

        setupInputsAndLabels(view, savedInstanceState);

        // Build the dialog by assigning view
        builder.setView(view);

        // Add Buttons (declare positive button event handler as null for now)
        builder.setPositiveButton(R.string.dialog_add_word,null);
        builder.setNegativeButton(R.string.dialog_cancel, null);

        return builder.create();
    }

    /**
     * Add behaviour to buttons here to prevent automatic dismissal of dialog
     */
    @Override
    public void onStart(){
        super.onStart();
        AlertDialog dialog = (AlertDialog)getDialog();

        // Assign behaviour to positive button
        if(dialog != null){
            Button positiveButton = dialog.getButton(Dialog.BUTTON_POSITIVE);
            positiveButton.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    submitVocabularyEntry();
                }
            });

        }
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
     * from saved instance
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
        dialogTitle.setText(R.string.dialog_add_word_title);

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

    /**
     * Submit the vocabulary entry through the view model
     */
    private void submitVocabularyEntry(){
        // Get the word values
        String primaryWord = primaryWordInput.getText().toString();
        String secondaryWord = secondaryWordInput.getText().toString();

        // Verify they're valid (not empty)
        if(StringUtils.isBlank(primaryWord) || StringUtils.isBlank(secondaryWord)){
            // Show Error Message
            Toast.makeText(getActivity(), getString(R.string.invalid_word_input), Toast.LENGTH_SHORT).show();
        }else{
            // Add the new entry
            VocabularyEntry newEntry = new VocabularyEntry(primaryWord, secondaryWord);
            vocabularyListViewModel.insertVocabularyEntry(newEntry);

            // Dismiss the dialog
            dismiss();
        }

    }
}
