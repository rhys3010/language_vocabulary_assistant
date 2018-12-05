package uk.ac.aber.dcs.cs31620.rhe24.lva.datasource;


import uk.ac.aber.dcs.cs31620.rhe24.lva.model.practice.PracticeAttemptDao;
import uk.ac.aber.dcs.cs31620.rhe24.lva.model.vocabulary.VocabularyEntryDao;

/**
 * Interface for room database
 *
 * @author Rhys Evans
 * @version 3/12/2018
 */
public interface RoomDatabaseI {
    abstract VocabularyEntryDao getVocabularyEntryDao();
    abstract PracticeAttemptDao getPracticeAttemptDao();
    abstract void closeDb();
}
