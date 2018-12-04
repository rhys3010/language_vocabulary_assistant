package uk.ac.aber.dcs.cs31620.rhe24.lva.model.practice;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * PracticeAnswer.java
 *
 * A class to represent an answer to a practice questions.
 * Stores the 'question' (word in primary language)
 * Stores the 'answer' (user's input)
 * Stores the 'correct answer' (word in secondary language)
 * The class implements parcelable and can therefore be bundled into state instance
 *
 * @author Rhys Evans
 * @version 3/12/2018
 */
public class PracticeAnswer implements Parcelable {


    /**
     * The 'question' - The word in user's primary language
     */
    private String question;

    /**
     * The 'correct answer' - The word in seccondary language
     */
    private String correctAnswer;

    /**
     * The 'answer' - User's input
     */
    private String answer;

    /**
     * Place all instance variables in parcel
     * @param out
     * @param flags
     */
    @Override
    public void writeToParcel(Parcel out, int flags){
        out.writeString( question);
        out.writeString(correctAnswer);
        out.writeString(answer);
    }

    /**
     * Parcelable private constructor
     * @param in
     */
    private PracticeAnswer(Parcel in){
        this.question = in.readString();
        this.correctAnswer = in.readString();
        this.answer = in.readString();
    }

    /**
     * Initialize object by getting all needed information
     * @param question
     * @param correctAnswer
     * @param answer
     */
    public PracticeAnswer(String question, String correctAnswer, String answer){
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.answer = answer;
    }

    /**
     * No description required
     * @return
     */
    @Override
    public int describeContents(){
        return 0;
    }

    /**
     * Returns whether or not the user's answer is correct
     * @return
     */
    public boolean isAnswerCorrect(){
        return answer.equals(correctAnswer);
    }

    /**
     * Returns the question
     * @return
     */
    public String getQuestion() {
        return question;
    }

    /**
     * Returns the correct answer to the question (secondary word)
     * @return
     */
    public String getCorrectAnswer() {
        return correctAnswer;
    }

    /**
     * Returns the user's input to the question
     * @return
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * The CREATOR constant for parcelable
     */
    public static final Parcelable.Creator<PracticeAnswer> CREATOR = new Parcelable.Creator<PracticeAnswer>(){

        /**
         * Call private constructor to create object from parcel
         * @param parcel
         * @return
         */
        @Override
        public PracticeAnswer createFromParcel(Parcel parcel) {
            return new PracticeAnswer(parcel);
        }

        /**
         * Create as array
         * @param i
         * @return
         */
        @Override
        public PracticeAnswer[] newArray(int i) {
            return new PracticeAnswer[i];
        }
    };
}
