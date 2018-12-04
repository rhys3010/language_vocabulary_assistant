package uk.ac.aber.dcs.cs31620.rhe24.lva.datasource;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import uk.ac.aber.dcs.cs31620.rhe24.lva.model.practice.PracticeAttempt;
import uk.ac.aber.dcs.cs31620.rhe24.lva.model.practice.PracticeAttemptDao;
import uk.ac.aber.dcs.cs31620.rhe24.lva.model.vocabulary.VocabularyEntry;
import uk.ac.aber.dcs.cs31620.rhe24.lva.model.vocabulary.VocabularyEntryDao;

/**
 * Create and initialize the Room Database using a singleton Room Database class
 * @author Rhys Evans
 * @version 29/11/2018
 */
@Database(entities = {VocabularyEntry.class, PracticeAttempt.class}, version = 2)
public abstract class LVARoomDatabase extends RoomDatabase {

    /**
     * The singleton instance of the LVA Room Database
     */
    private static LVARoomDatabase INSTANCE;

    /**
     * Get the Data Access Object for the vocabulary entry
     * @return
     */
    public abstract VocabularyEntryDao getVocabularyEntryDao();

    /**
     * Get the data access object for practice attempt
     */
    public abstract PracticeAttemptDao getPracticeAttemptDao();

    /**
     * Returns the Room Database object
     * @param context
     * @return
     */
    public static LVARoomDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized(LVARoomDatabase.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), LVARoomDatabase.class, "lva_database").addCallback(sRoomDatabaseCallback).addMigrations(MIGRATION_1_2).build();
                }
            }
        }

        return INSTANCE;
    }

    /**
     * Migrating the database from version 1 to 2
     */
    static final Migration MIGRATION_1_2 = new Migration(1, 2){

        @Override
        public void migrate(SupportSQLiteDatabase db){
            db.execSQL("CREATE TABLE IF NOT EXISTS `practice_attempts` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `score` INTEGER NOT NULL, `max_score` INTEGER NOT NULL, `created_at` INTEGER NOT NULL)");

        }
    };

    /**
     * Allows for database initialization
     */
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback(){

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db){
            super.onOpen(db);

            // Ensure databse is built in a seperate thread
            new PopulateDbAsync(INSTANCE).execute();
        }
    };

    /**
     * Inner Class to Populate the database initially
     */
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        /**
         * The Data Access Object for vocabulary entry
         */
        private final VocabularyEntryDao vocabularyEntryDao;
        private final PracticeAttemptDao practiceAttemptDao;

        PopulateDbAsync(LVARoomDatabase db){
            vocabularyEntryDao = db.getVocabularyEntryDao();
            practiceAttemptDao = db.getPracticeAttemptDao();
        }

        @Override
        protected Void doInBackground(final Void... params){

            // Nuke table
            vocabularyEntryDao.deleteAll();
            practiceAttemptDao.deleteAll();

            // TEMP
            // Populate Database with test data
            List<VocabularyEntry> vocabList = new ArrayList<>();

            vocabList.add(new VocabularyEntry("Complete", "Gorffen"));
            vocabList.add(new VocabularyEntry("House", "Ty"));
            vocabList.add(new VocabularyEntry("First", "Cyntaf"));
            vocabList.add(new VocabularyEntry("Look", "Edrych"));
            vocabList.add(new VocabularyEntry("New", "Newydd"));
            vocabList.add(new VocabularyEntry("Truck", "Tryc"));
            vocabList.add(new VocabularyEntry("Christmas", "Nadolig"));
            vocabList.add(new VocabularyEntry("Attempting", "Ymdrechu"));
            vocabList.add(new VocabularyEntry("Worked", "Gweithio"));
            vocabList.add(new VocabularyEntry("Working", "Gweithio"));
            vocabList.add(new VocabularyEntry("Job", "Swydd"));
            vocabList.add(new VocabularyEntry("Hurts", "Brifo"));
            vocabList.add(new VocabularyEntry("Cutest", "Delaf"));
            vocabList.add(new VocabularyEntry("Turkey", "Twrci"));
            vocabList.add(new VocabularyEntry("New House", "Ty Newydd"));
            vocabList.add(new VocabularyEntry("Compared to last time", "Cymharu i tro dwaethaf"));
            vocabList.add(new VocabularyEntry("Thank you", "Diolch"));
            vocabList.add(new VocabularyEntry("How are you?", "Syt wyt ti?"));
            vocabList.add(new VocabularyEntry("Singing", "Canu"));
            vocabList.add(new VocabularyEntry("Television", "Teledu"));

            vocabularyEntryDao.insertVocabularyEntry(vocabList);

            practiceAttemptDao.insertPracticeAttempt(new PracticeAttempt(6, 10));
            practiceAttemptDao.insertPracticeAttempt(new PracticeAttempt(3, 10));

            return null;
        }
    }
}
