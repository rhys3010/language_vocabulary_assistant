package uk.ac.aber.dcs.cs31620.rhe24.lva.model.vocabulary;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import uk.ac.aber.dcs.cs31620.rhe24.lva.datasource.LVARepository;
import uk.ac.aber.dcs.cs31620.rhe24.lva.model.util.SharedPreferencesManager;

/**
 * Interfaces between the UI and Persistent Data storage
 * @author Rhys Evans
 * @version 29/11/2018
 */
public class VocabularyListViewModel extends AndroidViewModel {

    /**
     * The Room Repository
     */
    private LVARepository repository;


    /**
     * Recycler View Adapter for Vocabulary List
     */
    private VocabularyListRecyclerAdapter adapter;

    /**
     * The shared preference manager
     */
    private SharedPreferencesManager sharedPreferencesManager;

    /**
     * Construct viewmodel, initialize repository and load data from database
     * @param application
     */
    public VocabularyListViewModel(@NonNull Application application){
        super(application);

        // Initialize repository
        repository = new LVARepository(application);

        // Initialize shared prefernce manager
        sharedPreferencesManager = SharedPreferencesManager.getInstance(application.getApplicationContext());
    }

    /**
     * Gets all vocabulary entries
     * @return
     */
    public LiveData<List<VocabularyEntry>> getVocabularyList(VocabularyListSortType sortType){

        // Return a different list depending on sort
        switch(sortType){

            case DATE_CREATED_DESC:
                return repository.getAllVocabularyEntriesByDateCreatedDesc();

            case DATE_CREATED_ASC:
                return repository.getAllVocabularyEntriesByDateCreatedAsc();

            case ALPHABETICAL_ASC:
                return repository.getAllVocabularyEntriesByAlphabetAsc();

            case ALPHABETICAL_DESC:
                return repository.getAllVocabularyEntriesByAlphabetDesc();

            // Default list (unsorted)
            case UNSORTED:
                return repository.getAllVocabularyEntries();

        }

        // Default list (unsorted)
        return repository.getAllVocabularyEntries();
    }

    /**
     * Gets the recycler view adapter
     * @return
     */
    public VocabularyListRecyclerAdapter getAdapter(){
        return adapter;
    }

    /**
     * Get the primary language from shared preferences
     * @return
     */
    public String getPrimaryLanguage(){
        return sharedPreferencesManager.getPrimaryLanguage();
    }

    /**
     * Get the secondary language from shared preferences
     * @return
     */
    public String getSecondaryLanguage(){
        return sharedPreferencesManager.getSecondaryLanguage();
    }


    /**
     * Get whether or not user has set language
     * @return
     */
    public boolean isLanguageSaved(){ return sharedPreferencesManager.isLanguageSaved(); }

    /**
     * Insert a vocabulary entry into the database
     * @param entry
     */
    public void insertVocabularyEntry(VocabularyEntry entry){
        repository.insertVocabularyEntry(entry);
    }

    /**
     * Delete language preferences
     */
    public void deleteLanguages(){
        sharedPreferencesManager.deleteLanguages();
    }

    /**
     * Delete all vocabulary entries
     */
    public void deleteVocabularyList(){
        repository.deleteVocabularyList();
    }

    /**
     * Delete a specific vocabulary entry
     * @param entry - The Entry to delete
     */
    public void deleteVocabularyEntry(VocabularyEntry entry){
        repository.deleteVocabularyEntry(entry);
    }

    /**
     * Sets the recycler view adapter
     * @param adapter
     */
    public void setAdapter(VocabularyListRecyclerAdapter adapter){
        this.adapter = adapter;
    }
}
