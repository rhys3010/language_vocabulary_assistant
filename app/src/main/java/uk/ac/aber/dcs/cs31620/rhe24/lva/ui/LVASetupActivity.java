package uk.ac.aber.dcs.cs31620.rhe24.lva.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.WordUtils;

import uk.ac.aber.dcs.cs31620.rhe24.lva.R;
import uk.ac.aber.dcs.cs31620.rhe24.lva.model.util.SharedPreferencesManager;

/**
 * LVASetupActivity
 * Activity to run on app's first time launch, to introduce user and get their preferred language
 * settings.
 * @author Rhys Evans
 * @version 21/11/2018
 */
public class LVASetupActivity extends AppCompatActivity{

    /**
     * Shared preference manager
     */
    private SharedPreferencesManager sharedPreferencesManager;

    /**
     * Called when the setup activity is created
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lvasetup);

        // Initialize shared preference manager
        sharedPreferencesManager = SharedPreferencesManager.getInstance(getApplicationContext());

        setupAutoComplete(findViewById(R.id.setup_view), R.id.primary_language_input, R.array.language_suggestions_array);
        setupAutoComplete(findViewById(R.id.setup_view), R.id.secondary_language_input,  R.array.language_suggestions_array);
    }

    /**
     * Confirm Setup by saving selected languages and switching to main activity.
     * @param v
     */
    public void submitLanguageSelection(View v){
        // Get the language selections from the imputs
        AutoCompleteTextView primaryLangInput = findViewById(R.id.primary_language_input);
        AutoCompleteTextView secondaryLangInput = findViewById(R.id.secondary_language_input);

        // Format string correctly before storing
        String primaryLanguage = WordUtils.capitalizeFully(primaryLangInput.getText().toString());
        String secondaryLanguage = WordUtils.capitalizeFully(secondaryLangInput.getText().toString());

        // If inputs still contain default value, inform user and exit function
        if(StringUtils.isBlank(primaryLanguage) || StringUtils.isBlank(secondaryLanguage)){
            // Show Error Message
            Toast.makeText(this, getString(R.string.invalid_language_input), Toast.LENGTH_SHORT).show();
            return;
        }

        // Move back to main activity
        Intent intent = new Intent(this, LVAMainActivity.class);
        startActivity(intent);

        // Update shared preferences to store languages
        sharedPreferencesManager.putPrimaryLanguage(primaryLanguage);
        sharedPreferencesManager.putSecondaryLanguage(secondaryLanguage);

        // Close down the activity
        finish();
    }

    /**
     * Set up the auto-complete for language selection inputs.
     */
    private void setupAutoComplete(View view, int inputResourceId, int arrayResourceId){
        AutoCompleteTextView languageInput = view.findViewById(inputResourceId);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(view.getContext(), arrayResourceId, android.R.layout.simple_dropdown_item_1line);
        languageInput.setAdapter(adapter);
    }
}
