package rhe24.cs31620.dcs.aber.ac.uk.lva.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import rhe24.cs31620.dcs.aber.ac.uk.lva.R;

/**
 * LVASetupActivity
 * Activity to run on app's first time launch, to introduce user and get their preferred language
 * settings.
 * @author Rhys Evans
 * @version 21/11/2018
 */
public class LVASetupActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener{

    SharedPreferences sharedPreferences = null;

    /**
     * Called when the setup activity is created
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lvasetup);

        // Set up the language selection spinners
        setupSpinner(findViewById(R.id.setup_view), R.id.primary_language_spinner, R.array.language_array);
        setupSpinner(findViewById(R.id.setup_view), R.id.secondary_language_spinner, R.array.language_array);
    }

    /**
     * Declares setup as being complete, closing down setup activity and moving to main activity
     * @param v
     */
    public void setSetupComplete(View v){
        // Get the shared preferences
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        // Move back to main activity
        Intent intent = new Intent(this, LVAMainActivity.class);
        startActivity(intent);
        // Update shared preferences to prevent having to 're-setup'
        sharedPreferences.edit().putBoolean("PREF_NEED_SETUP", false).apply();
        // Close down the activity
        finish();
    }

    /**
     * Perform necessary operations to setup and initialize the language
     * selection spinners
     * @param view
     * @param spinnerResourceId
     * @param arrayResourceId
     */
    private void setupSpinner(View view, int spinnerResourceId, int arrayResourceId){
        // Get the spinner and assign it's selected listener
        Spinner spinner = view.findViewById(spinnerResourceId);
        spinner.setSelection(1);
        spinner.setOnItemSelectedListener(this);

        // Initialise array adapter with custom spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(view.getContext(), arrayResourceId, R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Adding the ArrayAdapter to the spinner
        spinner.setAdapter(adapter);

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
