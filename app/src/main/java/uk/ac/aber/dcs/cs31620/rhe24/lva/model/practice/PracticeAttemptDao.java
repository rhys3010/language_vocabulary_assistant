package uk.ac.aber.dcs.cs31620.rhe24.lva.model.practice;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;

import java.util.List;

import uk.ac.aber.dcs.cs31620.rhe24.lva.model.util.DateTimeConverter;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

/**
 * PracticeAttemptDao.java
 *
 * The room data access object for the PracticeAttempt table
 *
 * @author Rhys Evans
 * @version 3/12/2018
 */
@Dao
@TypeConverters({DateTimeConverter.class})
public interface PracticeAttemptDao {

    /**
     * Insert a practice attempt into the database
     */
    @Insert(onConflict = IGNORE)
    void insertPracticeAttempt(PracticeAttempt practiceAttempt);

    /**
     * Overload insert method to support inserting lists
     * (useful for testing)
     */
    @Insert(onConflict = IGNORE)
    void insertPracticeAttempt(List<PracticeAttempt> practiceAttemptList);

    /**
     * Get all practice attempts from the database
     */
    @Query("SELECT * FROM practice_attempts")
    LiveData<List<PracticeAttempt>> getAllPracticeAttempts();

    /**
     * Get the most recent practice attempt from the database
     */
    @Query("SELECT * FROM practice_attempts ORDER BY created_at DESC LIMIT 1")
    LiveData<PracticeAttempt> getMostRecentPracticeAttempt();

    /**
     * Get the best practice attempt from the database
     */
    @Query("SELECT * FROM practice_attempts ORDER BY score DESC LIMIT 1")
    LiveData<PracticeAttempt> getBestPracticeAttempt();

    /**
     * Delete all practice attempts from the database
     */
    @Query("DELETE FROM practice_attempts")
    void deleteAll();

}
