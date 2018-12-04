package uk.ac.aber.dcs.cs31620.rhe24.lva.model.vocabulary;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.Date;

import uk.ac.aber.dcs.cs31620.rhe24.lva.model.practice.PracticeAnswer;
import uk.ac.aber.dcs.cs31620.rhe24.lva.model.util.DateTimeConverter;

/**
 * VocabularyEntry.java
 * Defines a Vocabulary Entry
 * The class implements parcelable and can therefore be bundled into state instance
 * @author Rhys Evans
 * @version 29/11/2018
 */
@Entity(tableName = "vocabulary_entries")
@TypeConverters({DateTimeConverter.class})
public class VocabularyEntry implements Parcelable {

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
     * Place all instance variables in parcel
     * @param out
     * @param flags
     */
    @Override
    public void writeToParcel(Parcel out, int flags){
        out.writeInt(id);
        out.writeString(wordPrimaryLanguage);
        out.writeString(wordSecondaryLanguage);
        out.writeSerializable(dateCreated);
    }

    /**
     * Priave constructor for parcelable
     * @param in
     */
    private VocabularyEntry(Parcel in){
        this.id = in.readInt();
        this.wordPrimaryLanguage = in.readString();
        this.wordSecondaryLanguage = in.readString();
        this.dateCreated = (Date)in.readSerializable();
    }

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
     * No description of contents required
     * @return
     */
    @Override
    public int describeContents(){
        return 0;
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

    /**
     * The CREATOR constant for parcelable
     */
    public static final Parcelable.Creator<VocabularyEntry> CREATOR = new Parcelable.Creator<VocabularyEntry>(){

        /**
         * Call private constructor to create object from parcel
         * @param parcel
         * @return
         */
        @Override
        public VocabularyEntry createFromParcel(Parcel parcel) {
            return new VocabularyEntry(parcel);
        }

        /**
         * Create as array
         * @param i
         * @return
         */
        @Override
        public VocabularyEntry[] newArray(int i) {
            return new VocabularyEntry[i];
        }
    };
}
