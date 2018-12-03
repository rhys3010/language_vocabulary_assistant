package uk.ac.aber.dcs.cs31620.rhe24.lva.ui.practice;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;
import java.util.Objects;

import uk.ac.aber.dcs.cs31620.rhe24.lva.R;
import uk.ac.aber.dcs.cs31620.rhe24.lva.model.practice.PracticeRecyclerAdapter;
import uk.ac.aber.dcs.cs31620.rhe24.lva.model.practice.PracticeViewModel;
import uk.ac.aber.dcs.cs31620.rhe24.lva.model.vocabulary.VocabularyEntry;

/**
 * PracticeActivity.java
 *
 * Run the vocab practice in a seperate activity
 * @author Rhys Evans
 * @version 3/12/2018
 */
public class PracticeActivity extends AppCompatActivity {

    /**
     * The view model to interface with database
     */
    private PracticeViewModel practiceViewModel;

    /**
     * The Practice Recycler adapter to handle list
     */
    private PracticeRecyclerAdapter practiceRecyclerAdapter;

    /**
     * The practice entry list
     */
    private LiveData<List<VocabularyEntry>> practiceQuestionList;

    /**
     * The previous entry list (handled by observer)
     */
    private LiveData<List<VocabularyEntry>> oldPracticeQuestionList;

    /**
     * Called when the activity is created
     */
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);

        // Initialize the view model
        practiceViewModel = ViewModelProviders.of(this).get(PracticeViewModel.class);

        // Initialize recycler view adapter
        practiceRecyclerAdapter = practiceViewModel.getAdapter();

        // If adapter has not yet been initialized/set, do it
        if(practiceRecyclerAdapter == null){
            practiceRecyclerAdapter = new PracticeRecyclerAdapter(this);
            practiceViewModel.setAdapter(practiceRecyclerAdapter);
        }

        // Initialize the list to be displayed
        // TODO: prune to 10
        practiceQuestionList = practiceViewModel.getVocabularyList();

        // Setup the recycler view
        RecyclerView practiceQuestionList = findViewById(R.id.practice_question_list);
        // Attach adapter to recycler view
        practiceQuestionList.setAdapter(practiceRecyclerAdapter);
        practiceQuestionList.setLayoutManager(new LinearLayoutManager(this));

        setupObserver();
    }

    /**
     * Setup the observer to handle the live data
     */
    private void setupObserver(){
        // Remove any pre-existing observers if the list of vocabulary has changed
        if(!Objects.equals(oldPracticeQuestionList, practiceQuestionList)){
            if(oldPracticeQuestionList != null){
                oldPracticeQuestionList.removeObservers(this);
            }
            oldPracticeQuestionList = practiceQuestionList;
        }

        // Add an observer to the live data if there doesn't already exist one
        if(!practiceQuestionList.hasActiveObservers()){
            practiceQuestionList.observe(this, new Observer<List<VocabularyEntry>>(){

                @Override
                public void onChanged(@Nullable List<VocabularyEntry> entries){
                    practiceRecyclerAdapter.changeDataSet(entries);
                }
            });
        }
    }
}
