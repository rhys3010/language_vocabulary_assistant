package uk.ac.aber.dcs.cs31620.rhe24.lva.ui.practice;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import uk.ac.aber.dcs.cs31620.rhe24.lva.R;
import uk.ac.aber.dcs.cs31620.rhe24.lva.model.practice.PracticeAttempt;
import uk.ac.aber.dcs.cs31620.rhe24.lva.model.practice.PracticeViewModel;
import uk.ac.aber.dcs.cs31620.rhe24.lva.model.vocabulary.VocabularyEntry;
import uk.ac.aber.dcs.cs31620.rhe24.lva.ui.LVAMainActivity;

/**
 * PracticeOverviewFragment
 * @author Rhys Evans
 * @version 21/11/2018
 */
public class PracticeOverviewFragment extends Fragment {

    /**
     * The view model class to interface with the backend database
     */
    private PracticeViewModel practiceViewModel;


    public PracticeOverviewFragment() {
        // Required empty public constructor
    }


    /**
     * Called when fragment is created,
     * Get necessary data for overview from view model
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Create the view for this fragment by inflating the layuout
        final View view = inflater.inflate(R.layout.fragment_practice_overview, container, false);

        // Initialize view model
        practiceViewModel = ViewModelProviders.of(this).get(PracticeViewModel.class);

        setupLabelsAndProgress(view);

        // Setup button action
        Button submitButton = view.findViewById(R.id.practice_start_button);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start practice when clicked
                Intent intent = new Intent(getActivity(), PracticeActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    /**
     * Display the correct text in the score overview labels
     * and set the progress bars to correctly reflect these values
     * @param view
     */
    private void setupLabelsAndProgress(View view){

        // Initialize all view objects
        final TextView mostRecentPracticeTimeLabel = view.findViewById(R.id.last_practice_time_label);

        final TextView mostRecentPracticeScoreLabel = view.findViewById(R.id.most_recent_score_label);
        final ProgressBar mostRecentPracticeScoreProgress = view.findViewById(R.id.most_recent_score_progress);

        final TextView averagePracticeScoreLabel = view.findViewById(R.id.average_score_label);
        final ProgressBar averagePracticeScoreProgress = view.findViewById(R.id.average_score_progress);

        final TextView bestPracticeScoreLabel = view.findViewById(R.id.best_score_label);
        final ProgressBar bestPracticeScoreProgress = view.findViewById(R.id.best_score_progress);

        // Get data from viewmodel and add observer to most recent practice attempt
        practiceViewModel.getMostRecentPracticeAttempt().observe(this, new Observer<PracticeAttempt>() {
            @Override
            public void onChanged(@Nullable PracticeAttempt practiceAttempt) {
                if(practiceAttempt != null) {
                    // Display the date as nicely formatted and locale specific
                    String date = new SimpleDateFormat("dd/MM/yy - HH:mm", getResources().getConfiguration().locale).format(practiceAttempt.getDateCreated());
                    mostRecentPracticeTimeLabel.setText(date);
                    // Convert score to percentage
                    int score = (int)(((float)practiceAttempt.getScore() / (float)practiceAttempt.getMaxScore()) * 100);
                    mostRecentPracticeScoreLabel.setText(getString(R.string.practice_score_percentage, score));
                    mostRecentPracticeScoreProgress.setProgress(score);
                }else{
                    mostRecentPracticeScoreLabel.setText(getString(R.string.practice_score_0));
                    mostRecentPracticeScoreProgress.setProgress(0);
                }
            }
        });

        // Get data from viewmodel and add observer to best practice attempt
        practiceViewModel.getBestPracticeAttempt().observe(this, new Observer<PracticeAttempt>() {
            @Override
            public void onChanged(@Nullable PracticeAttempt practiceAttempt) {
                if(practiceAttempt != null){
                    // Convert score to percentage
                    int score = (int)(((float)practiceAttempt.getScore() / (float)practiceAttempt.getMaxScore()) * 100);
                    bestPracticeScoreLabel.setText(getString(R.string.practice_score_percentage, score));
                    bestPracticeScoreProgress.setProgress(score);
                }else{
                    bestPracticeScoreLabel.setText(getString(R.string.practice_score_0));
                    bestPracticeScoreProgress.setProgress(0);
                }
            }
        });

        // Get data from viewmodel and add observer to all practice attempts
        // needed to work out average
        practiceViewModel.getAllPracticeAttempts().observe(this, new Observer<List<PracticeAttempt>>() {
            @Override
            public void onChanged(@Nullable List<PracticeAttempt> practiceAttempts) {
                if(practiceAttempts != null){
                    // Sum up all the scores from each attempt
                    int totalScore = 0;
                    // Iterate over the list and add up all scores
                    for(PracticeAttempt attempt : practiceAttempts){
                        totalScore += (int)(((float)attempt.getScore() / (float)attempt.getMaxScore()) * 100);
                    }

                    int averageScore;

                    // Get the average
                    if(practiceAttempts.size() != 0){
                        averageScore = totalScore / practiceAttempts.size();
                    }else{
                        averageScore = 0;
                    }
                    averagePracticeScoreLabel.setText(getString(R.string.practice_score_percentage, averageScore));
                    averagePracticeScoreProgress.setProgress(averageScore);
                }
            }
        });
    }
}
