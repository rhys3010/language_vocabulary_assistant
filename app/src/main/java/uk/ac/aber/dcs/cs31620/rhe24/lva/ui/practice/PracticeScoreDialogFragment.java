package uk.ac.aber.dcs.cs31620.rhe24.lva.ui.practice;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.app.AlertDialog;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import uk.ac.aber.dcs.cs31620.rhe24.lva.R;
import uk.ac.aber.dcs.cs31620.rhe24.lva.model.practice.PracticeAnswer;
import uk.ac.aber.dcs.cs31620.rhe24.lva.model.practice.PracticeResultsRecyclerAdapter;

/**
 * PracticeScoreDialogFragment.java
 *
 * A dialog to display the score of a practice attempt
 * and breakdown user's answers
 *
 * @author Rhys Evans
 * @version 3/12/2018
 */
public class PracticeScoreDialogFragment extends DialogFragment {

    /**
     * The recycler adapter for incorrect answers list
     */
    private PracticeResultsRecyclerAdapter practiceResultsRecyclerAdapter;

    // Empty Constructor
    public PracticeScoreDialogFragment(){}


    /**
     * Create a new instance of the dialog
     * @return
     */
    public static PracticeScoreDialogFragment newInstance(){
        return new PracticeScoreDialogFragment();
    }

    /**
     * Inflate the dialog view and add the dismiss button
     * @param savedInstanceState
     * @return
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.DialogTheme);
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Create the view by inflating layout
        View view = inflater.inflate(R.layout.fragment_practice_score, null, false);
        builder.setView(view);

        // Update Score label
        int score = ((PracticeActivity)getActivity()).getScore();
        // Place value in score label
        TextView scoreLabel = view.findViewById(R.id.score_label);
        scoreLabel.setText(getString(R.string.practice_score, score, ((PracticeActivity)getActivity()).getMaxScore()));

        // Create adapter
        if(practiceResultsRecyclerAdapter == null){
            practiceResultsRecyclerAdapter = new PracticeResultsRecyclerAdapter(getContext());
        }

        // Attach recycler adapter to recycler list view
        RecyclerView answersListRecyclerView = view.findViewById(R.id.incorrect_answers_list);
        answersListRecyclerView.setAdapter(practiceResultsRecyclerAdapter);
        answersListRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Set the dataset of the adapter
        List<PracticeAnswer> incorrectAnswersList = ((PracticeActivity)getActivity()).getIncorrectAnswers();
        practiceResultsRecyclerAdapter.changeDataSet(incorrectAnswersList);

        // Add positive button
        builder.setPositiveButton(android.R.string.ok, null);

        return builder.create();
    }

    /**
     * Handle dialog dismissal
     * @param dialog
     */
    @Override
    public void onDismiss(final DialogInterface dialog){
        super.onDismiss(dialog);
        // End Activity
        if(getActivity() != null){
            getActivity().finish();
        }
    }
}
