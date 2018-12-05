package uk.ac.aber.dcs.cs31620.rhe24.lva.datasource;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.RoomDatabase;
import android.os.AsyncTask;

import java.util.List;

import uk.ac.aber.dcs.cs31620.rhe24.lva.Injection;
import uk.ac.aber.dcs.cs31620.rhe24.lva.model.practice.PracticeAttempt;
import uk.ac.aber.dcs.cs31620.rhe24.lva.model.practice.PracticeAttemptDao;
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
     * The data access object for practice attempt
     */
    private PracticeAttemptDao practiceAttemptDao;


    /**
     * Construct the repository
     * @param application
     */
    public LVARepository(Application application){
        // Get database from a given injection
        RoomDatabaseI db = Injection.getDatabase(application);
        vocabularyEntryDao = db.getVocabularyEntryDao();
        practiceAttemptDao = db.getPracticeAttemptDao();
    }

    /**
     * Insert a vocabulary entry to the database using VocabularyEntryDAO
     */
    public void insertVocabularyEntry(VocabularyEntry vocabularyEntry){
        new InsertVocabularyEntryAsyncTask(vocabularyEntryDao).execute(vocabularyEntry);
    }

    /**
     * Insert a practice attempt into the database
     */
    public void insertPracticeAttempt(PracticeAttempt practiceAttempt){
        new InsertPracticeAttemptAsyncTask(practiceAttemptDao).execute(practiceAttempt);
    }

    /**
     * Get all Vocabulary Entries (default/unsorted)
     */
    public LiveData<List<VocabularyEntry>> getAllVocabularyEntries(){
        return vocabularyEntryDao.getAllVocabularyEntries();
    }

    /**
     * Get all vocabulary Entries (sorted by date created desc)
     * @return
     */
    public LiveData<List<VocabularyEntry>> getAllVocabularyEntriesByDateCreatedDesc(){
        return vocabularyEntryDao.getAllVocabularyEntriesByDateCreatedDesc();
    }

    /**
     * Get all vocabulary Entries (sorted by date created asc)
     * @return
     */
    public LiveData<List<VocabularyEntry>> getAllVocabularyEntriesByDateCreatedAsc(){
        return vocabularyEntryDao.getAllVocabularyEntriesByDateCreatedAsc();
    }

    /**
     * Get all vocabulary Entries (sorted alphabetically A-Z)
     */
    public LiveData<List<VocabularyEntry>> getAllVocabularyEntriesByAlphabetAsc(){
        return vocabularyEntryDao.getAllVocabularyEntriesByAlphabetAsc();
    }

    /**
     * Get all vocabulary Entries (sorted alphabetically Z-A)
     */
    public LiveData<List<VocabularyEntry>> getAllVocabularyEntriesByAlphabetDesc(){
        return vocabularyEntryDao.getAllVocabularyEntriesByAlphabetDesc();
    }

    /**
     * Get all practice attempts
     */
    public LiveData<List<PracticeAttempt>> getAllPracticeAttempts(){
        return practiceAttemptDao.getAllPracticeAttempts();
    }

    /**
     * Get most recent practice attempt
     */
    public LiveData<PracticeAttempt> getMostRecentPracticeAttempt(){
        return practiceAttemptDao.getMostRecentPracticeAttempt();
    }

    /**
     * Get best practice attempt
     */
    public LiveData<PracticeAttempt> getBestPracticeAttempt(){
        return practiceAttemptDao.getBestPracticeAttempt();
    }

    /**
     * Delete an individual vocabulary entry
     * @param vocabularyEntry
     */
    public void deleteVocabularyEntry(VocabularyEntry vocabularyEntry){
        new DeleteVocabularyEntryAsyncTask(vocabularyEntryDao).execute(vocabularyEntry);
    }

    /**
     * Delete entire list
     */
    public void deleteVocabularyList(){
        new DeleteAllVocabularyEntriesAsyncTask(vocabularyEntryDao).execute();
    }

    /**
     * Delete All Practice Attempts
     */
    public void deleteAllPracticeAttempts(){
        new DeleteAllPracticeAttemptsAsyncTask(practiceAttemptDao).execute();
    }

    /**
     * Make vocabulary entry deletions on a seperate thread
     */
    private static class DeleteVocabularyEntryAsyncTask extends AsyncTask<VocabularyEntry, Void, Void>{
        private VocabularyEntryDao mAsyncTaskDao;

        DeleteVocabularyEntryAsyncTask(VocabularyEntryDao vocabularyEntryDao){
            mAsyncTaskDao = vocabularyEntryDao;
        }

        @Override
        protected Void doInBackground(final VocabularyEntry... params){
            mAsyncTaskDao.deleteVocabularyEntry(params[0]);

            return null;
        }
    }

    /**
     * Remove all vocabulary entries on a seperate thread
     */
    private static class DeleteAllVocabularyEntriesAsyncTask extends AsyncTask<Void, Void, Void>{

        private VocabularyEntryDao mAsyncTaskDao;

        DeleteAllVocabularyEntriesAsyncTask(VocabularyEntryDao vocabularyEntryDao){
            mAsyncTaskDao = vocabularyEntryDao;
        }

        @Override
        protected Void doInBackground(final Void... params){
            mAsyncTaskDao.deleteAll();

            return null;
        }
    }

    /**
     * Remove all practice attempts on a seperate thread
     */
    private static class DeleteAllPracticeAttemptsAsyncTask extends AsyncTask<Void, Void, Void>{

        private PracticeAttemptDao mAsyncTaskDao;

        DeleteAllPracticeAttemptsAsyncTask(PracticeAttemptDao practiceAttemptDao){
            mAsyncTaskDao = practiceAttemptDao;
        }

        @Override
        protected Void doInBackground(final Void... params){
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }


    /**
     * Insert Vocabulary Entries on a seperate thread
     */
    private static class InsertVocabularyEntryAsyncTask extends AsyncTask<VocabularyEntry, Void, Void>{

        private VocabularyEntryDao mAsyncTaskDao;

        InsertVocabularyEntryAsyncTask(VocabularyEntryDao vocabularyEntryDao){
            mAsyncTaskDao = vocabularyEntryDao;
        }

        @Override
        protected Void doInBackground(final VocabularyEntry... params){
            mAsyncTaskDao.insertVocabularyEntry(params[0]);
            return null;
        }
    }

    /**
     * Insert Practice Attempts on a seperate thread
     */
    private static class InsertPracticeAttemptAsyncTask extends AsyncTask<PracticeAttempt, Void, Void>{

        private PracticeAttemptDao mAsyncTaskDao;

        InsertPracticeAttemptAsyncTask(PracticeAttemptDao practiceAttemptDao){
            mAsyncTaskDao = practiceAttemptDao;
        }

        @Override
        protected Void doInBackground(final PracticeAttempt... params){
            mAsyncTaskDao.insertPracticeAttempt(params[0]);
            return null;
        }
    }
}
