package uk.ac.aber.dcs.cs31620.rhe24.lva.model.practice;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;


import java.util.List;

import uk.ac.aber.dcs.cs31620.rhe24.lva.datasource.LVARepository;
import uk.ac.aber.dcs.cs31620.rhe24.lva.model.util.SharedPreferencesManager;
import uk.ac.aber.dcs.cs31620.rhe24.lva.model.vocabulary.VocabularyEntry;

/**
 * PracticeViewModel.java
 *
 * Interface between the UI and Persistent data storage
 * @author Rhys Evans
 * @version 3/12/2018
 */
public class PracticeViewModel extends AndroidViewModel {

    /**
     * The Room Repository
     */
    private LVARepository repository;

    /**
     * Recycler View Adapter for PracticeVocabList
     */
    PracticeQuestionsRecyclerAdapter adapter;

    /**
     * The shared preference manager
     */
    private SharedPreferencesManager sharedPreferencesManager;

    /**
     * Construct the viewmodel by initializing connection to repository and shared preferences
     * @param application
     */
    public PracticeViewModel(@NonNull Application application){
        super(application);

        // Initialize repository
        repository = new LVARepository(application);

        // Initialize Shared preference manager
        sharedPreferencesManager = SharedPreferencesManager.getInstance(application.getApplicationContext());
    }

    /**
     * Get a list of vocabulary entries
     * @returns - All vocabulary entries
     */
    public LiveData<List<VocabularyEntry>> getVocabularyList(){
        return repository.getAllVocabularyEntries();
    }

    /**
     * Gets the recycle view adapter
     * @return
     */
    public PracticeQuestionsRecyclerAdapter getAdapter(){
        return adapter;
    }

    /**
     * Gets the primary language preference of the user
     * @return - The primary language preference
     */
    public String getPrimaryLanguage(){
        return sharedPreferencesManager.getPrimaryLanguage();
    }

    /**
     * Gets the secondary language preference of the user
     * @return - The secondary language preference
     */
    public String getSecondaryLanguage(){
        return sharedPreferencesManager.getSecondaryLanguage();
    }

    /**
     * Get list of practice attempts
     */
    public LiveData<List<PracticeAttempt>> getAllPracticeAttempts(){
        return repository.getAllPracticeAttempts();
    }

    /**
     * Get the most recent practice attempt
     */
    public LiveData<PracticeAttempt> getMostRecentPracticeAttempt(){
        return repository.getMostRecentPracticeAttempt();
    }

    /**
     * Get best practice attempt
     */
    public LiveData<PracticeAttempt> getBestPracticeAttempt(){
        return repository.getBestPracticeAttempt();
    }

    /**
     * Insert practice attempt
     */
    public void insertPracticeAttempt(PracticeAttempt practiceAttempt){
        repository.insertPracticeAttempt(practiceAttempt);
    }

    /**
     * Delete all practice attempts
     */
    public void deleteAllPracticeAttempts(){
        repository.deleteAllPracticeAttempts();
    }


    /**
     * Set the recycle view adapter
     * @param adapter
     */
    public void setAdapter(PracticeQuestionsRecyclerAdapter adapter){
        this.adapter = adapter;
    }
}
