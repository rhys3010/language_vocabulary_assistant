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
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), LVARoomDatabase.class, "lva_database").addMigrations(MIGRATION_1_2).build();
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
}
