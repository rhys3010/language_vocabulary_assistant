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

import uk.ac.aber.dcs.cs31620.rhe24.lva.model.vocabulary.VocabularyEntry;
import uk.ac.aber.dcs.cs31620.rhe24.lva.model.vocabulary.VocabularyEntryDao;

/**
 * Create and initialize the Room Database using a singleton Room Database class
 * @author Rhys Evans
 * @version 29/11/2018
 */
@Database(entities = {VocabularyEntry.class}, version = 1)
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
     * Returns the Room Database object
     * @param context
     * @return
     */
    public static LVARoomDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized(LVARoomDatabase.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), LVARoomDatabase.class, "lva_database").addCallback(sRoomDatabaseCallback).build();
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

        }
    };

    /**
     * Allows for database initialization
     */
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback(){

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db){
            super.onCreate(db);

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

        PopulateDbAsync(LVARoomDatabase db){
            vocabularyEntryDao = db.getVocabularyEntryDao();
        }

        @Override
        protected Void doInBackground(final Void... params){
            // TEMP
            // Populate Database with test data
            List<VocabularyEntry> vocabList = new ArrayList<>();

            vocabList.add(new VocabularyEntry("Hello", "Bonjour"));
            vocabList.add(new VocabularyEntry("Hello", "Helo"));
            vocabList.add(new VocabularyEntry("Goodbye", "Au Revoir"));
            vocabList.add(new VocabularyEntry("Goodbye", "Hwyl Fawr"));
            vocabList.add(new VocabularyEntry("Thank You", "Merci"));
            vocabList.add(new VocabularyEntry("Thank You", "Diolch"));
            vocabList.add(new VocabularyEntry("A Very Long Phrase is entered here", "A very long translation is entered here"));

            vocabularyEntryDao.insertVocabularyEntry(vocabList);

            return null;
        }
    }
}
