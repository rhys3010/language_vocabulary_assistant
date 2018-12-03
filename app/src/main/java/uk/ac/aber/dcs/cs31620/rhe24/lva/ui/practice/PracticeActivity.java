package uk.ac.aber.dcs.cs31620.rhe24.lva.ui.practice;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import uk.ac.aber.dcs.cs31620.rhe24.lva.R;

/**
 * PracticeActivity.java
 *
 * Run the vocab practice in a seperate activity
 * @author Rhys Evans
 * @version 3/12/2018
 */
public class PracticeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);
    }
}
