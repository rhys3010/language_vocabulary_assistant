package uk.ac.aber.dcs.cs31620.rhe24.lva.model.vocabulary;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import java.util.Date;

import uk.ac.aber.dcs.cs31620.rhe24.lva.model.util.DateTimeConverter;

/**
 * VocabularyEntry.java
 * Defines a Vocabulary Entry
 * @author Rhys Evans
 * @version 29/11/2018
 */
@Entity(tableName = "vocabulary_entries")
@TypeConverters({DateTimeConverter.class})
public class VocabularyEntry {

    /**
     * Unique ID for each vocabulary entry
     */
    @NonNull
    @PrimaryKey(autoGenerate = true)
    private int id;

    /**
     * The entry in the user's primary language
     */
    @NonNull
    @ColumnInfo(name = "word_primary_language")
    private String wordPrimaryLanguage;

    /**
     * The entry in the user's secondary language
     */
    @ColumnInfo(name = "word_secondary_language")
    @NonNull
    private String wordSecondaryLanguage;
    /**
     * The date the entry was created
     */
    @ColumnInfo(name = "created_at")
    private Date dateCreated;

    /**
     * Construct VocabularyEntry object and assign all attributes
     * @param wordPrimaryLanguage
     * @param wordSecondaryLanguage
     */
    public VocabularyEntry(@NonNull String wordPrimaryLanguage, @NonNull  String wordSecondaryLanguage){
        this.wordPrimaryLanguage = wordPrimaryLanguage;
        this.wordSecondaryLanguage = wordSecondaryLanguage;
        // Created now
        this.dateCreated = new Date();
    }

    /**
     * Returns the vocabulary entry's word in primary language
     * @return
     */
    @NonNull
    public String getWordPrimaryLanguage() {
        return wordPrimaryLanguage;
    }

    /**
     * Returns the vocabulary entry's word in secondary language
     * @return
     */
    @NonNull
    public String getWordSecondaryLanguage() {
        return wordSecondaryLanguage;
    }

    /**
     * Returns the date the vocabulary entry was created
     * @return
     */
    public Date getDateCreated() {
        return dateCreated;
    }

    /**
     * Returns the unique id of the vocabulary entry
     * @return
     */
    public int getId(){ return id; }

    /**
     * Sets the vocabulary entry's word in primary language
     * @param wordPrimaryLanguage
     */
    public void setWordPrimaryLanguage(@NonNull String wordPrimaryLanguage) {
        this.wordPrimaryLanguage = wordPrimaryLanguage;
    }

    /**
     * Sets the vocabulary entry's word in secondary language
     * @param wordSecondaryLanguage
     */
    public void setWordSecondaryLanguage(@NonNull String wordSecondaryLanguage) {
        this.wordSecondaryLanguage = wordSecondaryLanguage;
    }

    /**
     * Sets the date created for a given entry
     * @param dateCreated
     */
    public void setDateCreated(Date dateCreated){
        this.dateCreated = dateCreated;
    }

    /**
     * Sets the id for a given entry
     * @param id
     */
    public void setId(int id){
        this.id = id;
    }

    /**
     * Represent a Vocabulary Entry as a String
     * @return
     */
    @Override
    public String toString() {
        return "VocabularyEntry{" +
                "wordPrimaryLanguage='" + wordPrimaryLanguage + '\'' +
                ", wordSecondaryLanguage='" + wordSecondaryLanguage + '\'' +
                ", dateCreated=" + dateCreated +
                '}';
    }
}
