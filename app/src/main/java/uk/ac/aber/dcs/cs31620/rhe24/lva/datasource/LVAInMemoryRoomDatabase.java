package uk.ac.aber.dcs.cs31620.rhe24.lva.datasource;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import uk.ac.aber.dcs.cs31620.rhe24.lva.model.practice.PracticeAttempt;
import uk.ac.aber.dcs.cs31620.rhe24.lva.model.practice.PracticeAttemptDao;
import uk.ac.aber.dcs.cs31620.rhe24.lva.model.vocabulary.VocabularyEntry;
import uk.ac.aber.dcs.cs31620.rhe24.lva.model.vocabulary.VocabularyEntryDao;

/**
 * LVAInMemoryRoomDatabase.java
 *
 * Create and initialize the in-memory room database
 *
 * @author Rhys Evans
 * @version 4/11/2018
 */
@Database(entities = {VocabularyEntry.class, PracticeAttempt.class}, version = 1)
public abstract class LVAInMemoryRoomDatabase extends RoomDatabase implements RoomDatabaseI {

    /**
     * Singleton instance of in memmory database
     */
    private static LVAInMemoryRoomDatabase INSTANCE;

    /**
     * The data access object for a vocabulary entry
     * @return
     */
    @Override
    public abstract VocabularyEntryDao getVocabularyEntryDao();

    /**
     * The data access object for practice attempt
     * @return
     */
    @Override
    public abstract PracticeAttemptDao getPracticeAttemptDao();

    /**
     * Close the database
     */
    @Override
    public void closeDb(){
        INSTANCE.close();
        INSTANCE = null;
    }

    /**
     * Returns the Room Database object
     * @param context
     * @return
     */
    public static LVAInMemoryRoomDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized(LVAInMemoryRoomDatabase.class){
                if (INSTANCE == null){
                    INSTANCE = Room.inMemoryDatabaseBuilder(context.getApplicationContext(), LVAInMemoryRoomDatabase.class).allowMainThreadQueries().build();
                }
            }
        }

        return INSTANCE;
    }


}
