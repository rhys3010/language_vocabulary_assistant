package uk.ac.aber.dcs.cs31620.rhe24.lva.model.vocabulary;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverters;
import android.arch.persistence.room.Update;

import java.util.List;

import uk.ac.aber.dcs.cs31620.rhe24.lva.model.util.DateTimeConverter;

import static android.arch.persistence.room.OnConflictStrategy.*;

/**
 * VocabularyEntryDao.java
 * Perform the needed SQL Operations to support Room Databse interactions
 */
@Dao
@TypeConverters({DateTimeConverter.class})
public interface VocabularyEntryDao {

    /**
     * Insert a vocabulary entry into the database
     * @param vocabularyEntry
     */
    @Insert(onConflict = IGNORE)
    void insertVocabularyEntry(VocabularyEntry vocabularyEntry);

    /**
     * Overloaded method to insert multiple entries from List
     * @param vocabularyEntryList
     */
    @Insert(onConflict = IGNORE)
    void insertVocabularyEntry(List<VocabularyEntry> vocabularyEntryList);


    /**
     * Update a current vocabulary entry
     * @param vocabularyEntry
     */
    @Update(onConflict = REPLACE)
    void updateVocabularyEntry(VocabularyEntry vocabularyEntry);

    /**
     * Delete a given vocabulary entry
     * @param vocabularyEntry
     */
    @Delete
    void deleteVocabularyEntry(VocabularyEntry vocabularyEntry);

    /**
     * Delete all vocabulary entries
     */
    @Query("DELETE FROM vocabulary_entries")
    void deleteAll();

    /**
     * Get a given vocabulary entry by id
     * @param id
     * @return
     */
    @Query("SELECT * FROM vocabulary_entries WHERE id = :id")
    VocabularyEntry fetchVocabularyEntryById(int id);

    /**
     * Get all vocabulary entries (unordered)
     * @return
     */
    @Query("SELECT * FROM vocabulary_entries")
    LiveData<List<VocabularyEntry>> getAllVocabularyEntries();

    /**
     * Get all vocabulary entries (sorted by creation date descending)
     * @return
     */
    @Query("SELECT * FROM vocabulary_entries ORDER BY created_at DESC")
    LiveData<List<VocabularyEntry>> getAllVocabularyEntriesByDateCreatedDesc();

    /**
     * Get all vocabulary entries (sorted by creation date ascending)
     * @return
     */
    @Query("SELECT * FROM vocabulary_entries ORDER BY created_at ASC")
    LiveData<List<VocabularyEntry>> getAllVocabularyEntriesByDateCreatedAsc();

    /**
     * Get all vocabulary Entries (sorted alphabetically Z-A)
     */
    @Query("SELECT * FROM vocabulary_entries ORDER BY word_primary_language DESC")
    LiveData<List<VocabularyEntry>> getAllVocabularyEntriesByAlphabetDesc();

    /**
     * Get all vocabulary Entries (sorted alphabetically A-Z)
     */
    @Query("SELECT * FROM vocabulary_entries ORDER BY word_primary_language ASC")
    LiveData<List<VocabularyEntry>> getAllVocabularyEntriesByAlphabetAsc();
}
