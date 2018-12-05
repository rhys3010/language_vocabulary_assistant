package uk.ac.aber.dcs.cs31620.rhe24.lva.database_tests.util;

import java.util.ArrayList;
import java.util.List;

import uk.ac.aber.dcs.cs31620.rhe24.lva.model.practice.PracticeAttempt;
import uk.ac.aber.dcs.cs31620.rhe24.lva.model.vocabulary.VocabularyEntry;

/**
 * TestUtil.java
 *
 * Utility class to support database tests
 *
 * @author Rhys Evans
 * @version 4/12/2018
 */
public class TestUtil {

    /**
     * Create a list of entries to use for testing
     * @param num - the number of entries to create
     * @return
     */
    public List<VocabularyEntry> createVocabularyList(int num){
        List<VocabularyEntry> entries = new ArrayList<>();

        for(int i = 0; i < num; i++){
            entries.add(new VocabularyEntry("Hello", "Bonjour"));
        }

        return entries;
    }

    /**
     * Creates a list of practice attempts to use for testing
     * @param num
     * @return
     */
    public List<PracticeAttempt> createPracticeAttemptList(int num){
        List<PracticeAttempt> practiceAttempts = new ArrayList<>();

        for(int i = 0; i < num; i++){
            practiceAttempts.add(new PracticeAttempt(5, 10));
        }

        return practiceAttempts;
    }
}
