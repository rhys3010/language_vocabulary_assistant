package rhe24.cs31620.dcs.aber.ac.uk.lva.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import rhe24.cs31620.dcs.aber.ac.uk.lva.R;

/**
 * LVASetupActivity
 * @author Rhys Evans
 * @version 21/11/2018
 */
public class LVASetupActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences = null;

    /**
     * Called when the setup activity is created
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lvasetup);
    }

    /**
     * Declares the setup as being complete, closing down setup activity and moving to main activity
     * @param v
     */
    public void setSetupComplete(View v){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Intent intent = new Intent(this, LVAMainActivity.class);
        startActivity(intent);
        sharedPreferences.edit().putBoolean("PREF_NEED_SETUP", false).apply();
        finish();
    }
}
