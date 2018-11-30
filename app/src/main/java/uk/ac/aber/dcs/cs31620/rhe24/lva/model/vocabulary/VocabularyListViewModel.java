package uk.ac.aber.dcs.cs31620.rhe24.lva.model.vocabulary;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import uk.ac.aber.dcs.cs31620.rhe24.lva.datasource.LVARepository;

/**
 * Interfaces between the UI and the Database
 * @author Rhys Evans
 * @version 29/11/2018
 */
public class VocabularyListViewModel extends AndroidViewModel {

    /**
     * The Room Repository
     */
    private LVARepository repository;

    /**
     * All Vocabulary Entries
     */
    private LiveData<List<VocabularyEntry>> vocabularyList;

    /**
     * Recycler View Adapter for Vocabulary List
     */
    private VocabularyListRecyclerAdapter adapter;

    /**
     * Construct viewmodel, initialize repository and load data from database
     * @param application
     */
    public VocabularyListViewModel(@NonNull Application application){
        super(application);

        // Initialize repository
        repository = new LVARepository(application);

        // Load all vocabulary entries from DB
        vocabularyList = repository.getAllVocabularyEntries();
    }

    /**
     * Gets all vocabulary entries
     * @return
     */
    public LiveData<List<VocabularyEntry>> getVocabularyList(){
        return vocabularyList;
    }

    /**
     * Gets the recycler view adapter
     * @return
     */
    public VocabularyListRecyclerAdapter getAdapter(){
        return adapter;
    }

    /**
     * Sets the recycler view adapter
     * @param adapter
     */
    public void setAdapter(VocabularyListRecyclerAdapter adapter){
        this.adapter = adapter;
    }


}
