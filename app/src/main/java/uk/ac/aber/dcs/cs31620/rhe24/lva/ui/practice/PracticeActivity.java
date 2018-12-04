package uk.ac.aber.dcs.cs31620.rhe24.lva.ui.practice;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import uk.ac.aber.dcs.cs31620.rhe24.lva.R;
import uk.ac.aber.dcs.cs31620.rhe24.lva.model.practice.PracticeAnswer;
import uk.ac.aber.dcs.cs31620.rhe24.lva.model.practice.PracticeQuestionsRecyclerAdapter;
import uk.ac.aber.dcs.cs31620.rhe24.lva.model.practice.PracticeViewModel;
import uk.ac.aber.dcs.cs31620.rhe24.lva.model.vocabulary.VocabularyEntry;

import static java.lang.Math.min;

/**
 * PracticeActivity.java
 *
 * Run the vocab practice in a seperate activity
 * @author Rhys Evans
 * @version 3/12/2018
 */
public class PracticeActivity extends AppCompatActivity {

    /**
     * The number of entries to use in the practice
     * (10 by default)
     */
    private static final int NUMBER_OF_ENTRIES = 10;

    /**
     * Saved Instance state keys
     */
    private static final String CURRENT_LIST_KEY = "CURRENT_LIST";
    private static final String ANSWERS_KEY = "ANSWERS_KEY";

    /**
     * The view model to interface with database
     */
    private PracticeViewModel practiceViewModel;

    /**
     * The Practice Recycler adapter to handle list
     */
    private PracticeQuestionsRecyclerAdapter practiceQuestionsRecyclerAdapter;

    /**
     * The practice entry list
     */
    private LiveData<List<VocabularyEntry>> practiceQuestionList;

    /**
     * The previous entry list (handled by observer)
     */
    private LiveData<List<VocabularyEntry>> oldPracticeQuestionList;

    /**
     * Store the current list of 'questions' that was generated
     */
    private List<VocabularyEntry> currentList;

    /**
     * Store answers as key-value pair - Position in list and practice answer object
     */
    private SparseArray<PracticeAnswer> answers;

    /**
     * The user's practice attempt score
     */
    private int score;

    /**
     * A list of all the answers the user got wrong
     */
    private List<PracticeAnswer> incorrectAnswers;

    /**
     * Called when the activity is created
     */
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);

        // Initialize the view model
        practiceViewModel = ViewModelProviders.of(this).get(PracticeViewModel.class);

        // Initialize key-value storage of answers
        if(savedInstanceState == null){
            answers = new SparseArray<>();
        }else{
            // If saved instance state is available, get the answers from there
            answers = savedInstanceState.getSparseParcelableArray(ANSWERS_KEY);
        }


        // Initialize recycler view adapter
        practiceQuestionsRecyclerAdapter = practiceViewModel.getAdapter();

        // If adapter has not yet been initialized/set, do it
        if(practiceQuestionsRecyclerAdapter == null){
            practiceQuestionsRecyclerAdapter = new PracticeQuestionsRecyclerAdapter(this);
            practiceViewModel.setAdapter(practiceQuestionsRecyclerAdapter);
        }

        // Initialize the list to be displayed
        practiceQuestionList = practiceViewModel.getVocabularyList();

        // Setup the recycler view
        RecyclerView practiceQuestionList = findViewById(R.id.practice_question_list);

        practiceQuestionList.setHasFixedSize(true);
        // NOTE: Doing this basically defeats the purpose of a recycler view,
        // but because its such a small amount of data anyways it should be okay
        practiceQuestionList.setItemViewCacheSize(NUMBER_OF_ENTRIES);

        // Attach adapter to recycler view
        practiceQuestionList.setAdapter(practiceQuestionsRecyclerAdapter);
        practiceQuestionList.setLayoutManager(new LinearLayoutManager(this));

        setupObserver(savedInstanceState);
        setupLabelsAndButtons();
    }

    /**
     * Override back button press to prompt user for confirmation
     */
    @Override
    public void onBackPressed(){
        abandonPractice();
    }

    /**
     * Save the current question set so that it doesn't
     * reset every-time the screen is rotated.
     * Also save answers
     * @param outState
     */
    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);

        // Save question set (cast as ArrayList (for some reason it doesnt like List<> ??))
        ArrayList<VocabularyEntry> list = new ArrayList<>(currentList);
        outState.putParcelableArrayList(CURRENT_LIST_KEY, list);

        // Save answers to instance state
        outState.putSparseParcelableArray(ANSWERS_KEY, answers);
    }

    /**
     * Stores a given answer in the key-value store
     * @param answer - The answer object
     * @param key - The Key
     */
    public void storeAnswer(PracticeAnswer answer, int key) {
        // Put the item in the dictionary
        answers.put(key, answer);
    }

    /**
     * Get user's answer from dictionary
     * (used to preserve state)
     * @param key
     */
    public String getAnswer(int key){
        PracticeAnswer answer = answers.get(key);

        if(answer != null){
            return answer.getAnswer();
        }

        // Return emmpty string if no answer was found
        return "";
    }

    /**
     * Returns the score of the attempt
     * @return
     */
    public int getScore(){
        return score;
    }

    /**
     * Returns the max possible score
     * @return
     */
    public int getMaxScore(){
        return NUMBER_OF_ENTRIES;
    }

    /**
     * Returns all the answers the user got wrong - to display after practice ends
     * @return
     */
    public List<PracticeAnswer> getIncorrectAnswers(){
        return incorrectAnswers;
    }


    /**
     * Setup the observer to handle the live data
     *
     * @param savedInstanceState
     */
    private void setupObserver(final Bundle savedInstanceState){
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
                    // If savedinstancestate has contents, assign list to previously saved list
                    // (this prevents lists from being reset when orientation changes etc)
                    if(savedInstanceState != null){
                        currentList = savedInstanceState.getParcelableArrayList(CURRENT_LIST_KEY);
                    }else{
                        currentList = getRandomEntries(entries);
                    }

                    practiceQuestionsRecyclerAdapter.changeDataSet(currentList);
                }
            });
        }
    }

    /**
     * Selects N number of random entries (based on constant) to use in practice
     * @return
     */
    private List<VocabularyEntry> getRandomEntries(List<VocabularyEntry> allEntries){

        // Shuffle the list
        Collections.shuffle(allEntries);

        // Get N items from the shuffled list
        return allEntries.subList(0, min(allEntries.size(), NUMBER_OF_ENTRIES));
    }


    /**
     * Setup the language preference labels and add behaviour to buttons
     */
    private void setupLabelsAndButtons(){
        // Initialize textviews
        TextView primaryLanguageLabel = findViewById(R.id.language_preference_primary);
        TextView secondaryLanguageLabel = findViewById(R.id.language_preference_secondary);

        // Set textviews to language preferences
        primaryLanguageLabel.setText(practiceViewModel.getPrimaryLanguage());
        secondaryLanguageLabel.setText(practiceViewModel.getSecondaryLanguage());

        // Initialize buttons
        Button abandonButton = findViewById(R.id.practice_abandon_button);
        Button submitButton = findViewById(R.id.practice_submit_button);

        // Add behaviour to discard button
        abandonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get user confirmation then return to main activity
                abandonPractice();
            }
        });

        // Add behaviour to submit button
        submitButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                submitPractice();
            }
        });
    }

    /**
     * Abandon the current practice by prompting user first
     */
    private void abandonPractice(){

        // Create confirmation dialog
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this, R.style.DialogTheme);
        dialogBuilder.setTitle(R.string.practice_abandon_title);
        dialogBuilder.setMessage(R.string.practice_abandon_message);
        // Set negative button to do nothing
        dialogBuilder.setNegativeButton(android.R.string.no, null);

        // If user confirms actions, quit activity
        dialogBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Change activity
                finish();
            }
        });

        // Show the dialog
        dialogBuilder.show();
    }

    /**
     * Submit the practice attempt
     */
    private void submitPractice(){
        // The score for the practice attempt
        score = 0;

        // Initialize incorrect answers
        incorrectAnswers = new ArrayList<>();

        // Traverse dictionary to add up score
        for(int i = 0; i < answers.size(); i++){
            int key = answers.keyAt(i);

            // Increment score if answer was correct
            if(answers.get(key).isAnswerCorrect()){
                score++;

                // If wrong, add to incorrect answers list
            }else{
                incorrectAnswers.add(answers.get(key));
            }
        }

        // Display the score dialog
        PracticeScoreDialogFragment scoreDialog = PracticeScoreDialogFragment.newInstance();
        scoreDialog.show(getSupportFragmentManager(), "fragment_practice_score");
    }
}
