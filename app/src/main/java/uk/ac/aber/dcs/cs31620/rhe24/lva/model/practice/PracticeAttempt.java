package uk.ac.aber.dcs.cs31620.rhe24.lva.model.practice;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import java.util.Date;

import uk.ac.aber.dcs.cs31620.rhe24.lva.model.util.DateTimeConverter;

/**
 * PracticeAttempt.java
 *
 * Defines a Practice Attempt by storing score and time of test
 *
 * @author Rhys Evans
 * @version 3/12/2018
 */

@Entity(tableName = "practice_attempts")
@TypeConverters({DateTimeConverter.class})
public class PracticeAttempt {

    /**
     * The primary key of the practice attempt entry
     */
    @NonNull
    @PrimaryKey(autoGenerate = true)
    private int id;

    /**
     * The score for a given practice attempt
     */
    @NonNull
    private int score;

    /**
     * The Maximum score available for a given attempt
     */
    @ColumnInfo(name="max_score")
    @NonNull
    private int maxScore;

    @ColumnInfo(name="created_at")
    @NonNull
    private Date dateCreated;

    /**
     * Constructor for a practice attempt object
     * @param score
     */
    public PracticeAttempt(@NonNull int score, @NonNull int maxScore){
        this.score = score;
        this.maxScore = maxScore;

        // Set created at date to now
        this.dateCreated = new Date();
    }

    /**
     * Returns the score of the practice attempt
     * @return
     */
    @NonNull
    public int getScore(){
        return score;
    }

    /**
     * Gets the id of the practice attempt
     * @return
     */
    @NonNull
    public int getId(){
        return id;
    }

    /**
     * Gets the max score of the practice attempt
     * @return
     */
    @NonNull
    public int getMaxScore(){
        return maxScore;
    }

    /**
     * Gets the date the practice attempt was made
     * @return
     */
    @NonNull
    public Date getDateCreated(){
        return dateCreated;
    }

    /**
     * Sets the id of the current attempt
     * @param id
     */
    public void setId(@NonNull int id) {
        this.id = id;
    }

    /**
     * Sets the score of the practice attempt
     * @param score
     */
    public void setScore(@NonNull int score) {
        this.score = score;
    }

    /**
     * Sets the max score of the practice attempt
     * @param maxScore
     */
    public void setMaxScore(@NonNull int maxScore) {
        this.maxScore = maxScore;
    }

    /**
     * Sets the date of the practice attempt
     * @param dateCreated
     */
    public void setDateCreated(@NonNull Date dateCreated) {
        this.dateCreated = dateCreated;
    }

}
