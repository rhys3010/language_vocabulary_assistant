package rhe24.cs31620.dcs.aber.ac.uk.lva.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

import rhe24.cs31620.dcs.aber.ac.uk.lva.R;

/**
 * LVASetupActivity
 * Activity to run on app's first time launch, to introduce user and get their preferred language
 * settings.
 * @author Rhys Evans
 * @version 21/11/2018
 */
public class LVASetupActivity extends AppCompatActivity{

    /**
     * UI Element of primary spinner
     */
    private Spinner primarySpinner;

    /**
     * UI Element of secondary spinner
     */
    private Spinner secondarySpinner;


    /**
     * Called when the setup activity is created
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lvasetup);

        // Set up the language selection spinners
        setupSpinner(findViewById(R.id.setup_view), R.id.primary_language_spinner);
        setupSpinner(findViewById(R.id.setup_view), R.id.secondary_language_spinner);

        // Initialize spinnner and button references
        primarySpinner = findViewById(R.id.primary_language_spinner);
        secondarySpinner = findViewById(R.id.secondary_language_spinner);
    }

    /**
     * Confirm Setup by saving selected languages and switching to main activity.
     * @param v
     */
    public void submitLanguageSelection(View v){
        // If spinners still contain default value, inform user and exit function
        // (button should be disabled but best to be defensive)
        if(primarySpinner.getSelectedItemPosition() == 0 || secondarySpinner.getSelectedItemPosition() == 0){
            // Show Error Message
            Toast.makeText(this, getString(R.string.invalid_language_selection), Toast.LENGTH_SHORT).show();
            return;
        }

        // Get the language selections from the spinners
        String primaryLanguage = primarySpinner.getSelectedItem().toString();
        String secondaryLanguage = secondarySpinner.getSelectedItem().toString();

        // Get the shared preferences
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        // Move back to main activity
        Intent intent = new Intent(this, LVAMainActivity.class);
        startActivity(intent);

        // Update shared preferences to store languages
        sharedPreferences.edit().putString("PREF_PRIMARY_LANG", primaryLanguage).apply();
        sharedPreferences.edit().putString("PREF_SECONDARY_LANG", secondaryLanguage).apply();

        // Close down the activity
        finish();
    }


    /**
     * Perform necessary operations to setup and initialize the language
     * selection spinners
     * @param view
     * @param spinnerResourceId
     */
    private void setupSpinner(View view, int spinnerResourceId){
        // Get the spinner and set its default value
        Spinner spinner = view.findViewById(spinnerResourceId);
        spinner.setSelection(1);

        // Get all the available language options for the spinner
        ArrayList<String> spinnerOptions = initializeSpinnerOptions();

        // Initialise array adapter with custom spinner layout and language options
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(), R.layout.simple_spinner_item, spinnerOptions);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Adding the ArrayAdapter to the spinner
        spinner.setAdapter(adapter);

    }

    /**
     * Populate the array that contains all spinner options by converting locales to strings
     */
    private ArrayList<String> initializeSpinnerOptions(){
        Locale[] locales = Locale.getAvailableLocales();
        ArrayList<String> options = new ArrayList<>();
        // Add default selection to start of list
        options.add("Please Select");

        // Iterate through all locales, converting them to a string suitable for the spinner
        for(Locale l : locales){
            options.add(l.getDisplayName());
        }

        return options;
    }
}
