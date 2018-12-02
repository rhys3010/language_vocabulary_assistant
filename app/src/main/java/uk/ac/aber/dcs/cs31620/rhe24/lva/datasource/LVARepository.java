package uk.ac.aber.dcs.cs31620.rhe24.lva.datasource;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

import uk.ac.aber.dcs.cs31620.rhe24.lva.model.vocabulary.VocabularyEntry;
import uk.ac.aber.dcs.cs31620.rhe24.lva.model.vocabulary.VocabularyEntryDao;

/**
 * LVARepository.java
 * Class to provide a layer of abstraction between the UI and data source
 *
 * @author Rhys Evans
 * @version 29/11/2018
 */
public class LVARepository {

    /**
     * The Data Access Object of the vocabulary entry
     */
    private VocabularyEntryDao vocabularyEntryDao;

    /**
     * Construct the repository
     * @param application
     */
    public LVARepository(Application application){
        LVARoomDatabase db = LVARoomDatabase.getDatabase(application);
        vocabularyEntryDao = db.getVocabularyEntryDao();
    }

    /**
     * Insert a vocabulary entry to the database using VocabularyEntryDAO
     */
    public void insertVocabularyEntry(VocabularyEntry vocabularyEntry){
        new InsertAsyncTask(vocabularyEntryDao).execute(vocabularyEntry);
    }

    /**
     * Get all Vocabulary Entries
     */
    public LiveData<List<VocabularyEntry>> getAllVocabularyEntries(){
        return vocabularyEntryDao.getAllVocabularyEntries();
    }

    /**
     * Get a given vocabulary entry by ID
     * @param id
     * @return
     */
    public VocabularyEntry getVocabularyEntryById(int id){
        return vocabularyEntryDao.fetchVocabularyEntryById(id);
    }

    /**
     * Delete an individual vocabulary entry
     * @param vocabularyEntry
     */
    public void deleteVocabularyEntry(VocabularyEntry vocabularyEntry){
        new DeleteAsyncTask(vocabularyEntryDao).execute(vocabularyEntry);
    }

    /**
     * Delete entire list
     */
    public void deleteVocabularyList(){
        new DeleteAllAsyncTask(vocabularyEntryDao).execute();
    }

    /**
     * Make deletions on a seperate thread
     */
    private static class DeleteAsyncTask extends AsyncTask<VocabularyEntry, Void, Void>{
        private VocabularyEntryDao mAsyncTaskDao;

        DeleteAsyncTask(VocabularyEntryDao vocabularyEntryDao){
            mAsyncTaskDao = vocabularyEntryDao;
        }

        @Override
        protected Void doInBackground(final VocabularyEntry... params){
            mAsyncTaskDao.deleteVocabularyEntry(params[0]);

            return null;
        }
    }

    /**
     * Perform database clear on seperate thread
     */
    private static class DeleteAllAsyncTask extends AsyncTask<Void, Void, Void>{

        private VocabularyEntryDao mAsyncTaskDao;

        DeleteAllAsyncTask(VocabularyEntryDao vocabularyEntryDao){
            mAsyncTaskDao = vocabularyEntryDao;
        }

        @Override
        protected Void doInBackground(final Void... params){
            mAsyncTaskDao.deleteAll();

            return null;
        }
    }

    /**
     * Make database insertions on a seperate thread
     */
    private static class InsertAsyncTask extends AsyncTask<VocabularyEntry, Void, Void>{

        private VocabularyEntryDao mAsyncTaskDao;

        InsertAsyncTask(VocabularyEntryDao vocabularyEntryDao){
            mAsyncTaskDao = vocabularyEntryDao;
        }

        @Override
        protected Void doInBackground(final VocabularyEntry... params){
            mAsyncTaskDao.insertVocabularyEntry(params[0]);

            return null;
        }
    }
}
