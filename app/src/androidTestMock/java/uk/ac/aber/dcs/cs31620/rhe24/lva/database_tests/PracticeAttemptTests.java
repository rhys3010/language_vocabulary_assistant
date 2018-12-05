package uk.ac.aber.dcs.cs31620.rhe24.lva.database_tests;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import uk.ac.aber.dcs.cs31620.rhe24.lva.Injection;
import uk.ac.aber.dcs.cs31620.rhe24.lva.database_tests.util.LiveDataTestUtil;
import uk.ac.aber.dcs.cs31620.rhe24.lva.database_tests.util.TestUtil;
import uk.ac.aber.dcs.cs31620.rhe24.lva.datasource.RoomDatabaseI;
import uk.ac.aber.dcs.cs31620.rhe24.lva.model.practice.PracticeAttempt;
import uk.ac.aber.dcs.cs31620.rhe24.lva.model.practice.PracticeAttemptDao;

/**
 * PracticeAttemptTests.java
 *
 * Tests all CRUD operations through the PracticeAttemptDao to ensure
 * they are performed successfully.
 *
 * @author Rhys Evans
 * @version 4/12/2018
 */
@RunWith(AndroidJUnit4.class)
public class PracticeAttemptTests {

    /**
     * Execute database queries etc, synchronously for testing purposes
     */
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    /**
     * The practice attempt  dao
     */
    private PracticeAttemptDao practiceAttemptDao;

    /**
     * The room database
     */
    private RoomDatabaseI db;

    /**
     * Utility class to help with testing
     */
    private TestUtil testUtil = new TestUtil();

    /**
     * Setup the in-memory database to run the tests with
     */
    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getTargetContext();
        db = Injection.getDatabase(context);
        practiceAttemptDao = db.getPracticeAttemptDao();
    }

    /**
     * Close the database after the test is ran
     */
    @After
    public void closeDb() {
        db.closeDb();
    }

    /**
     * Test that inserting a practice attempt actually inserts it
     */
    @Test
    public void onInsertingPracticeAttempt_checkThat_practiceAttemptWasInserted() throws Exception {
        List<PracticeAttempt> practiceAttemptList = testUtil.createPracticeAttemptList(1);

        // Insert the practice attempt into the database
        practiceAttemptDao.insertPracticeAttempt(practiceAttemptList.get(0));

        // Verify it was inserted
        LiveData<List<PracticeAttempt>> foundPracticeAttempts = practiceAttemptDao.getAllPracticeAttempts();
        Assert.assertEquals(1, LiveDataTestUtil.getValue(foundPracticeAttempts).size());

    }

    /**
     * Test that deleting all entries actually deletes them
     */
    @Test
    public void onDeleteAllVocabularyEntries_checkThat_allVocabularyEntriesWereDeleted() throws Exception {
        // Create a list of 3 pratice attempts
        List<PracticeAttempt> practiceAttemptList = testUtil.createPracticeAttemptList(3);

        // Insert them into the database
        practiceAttemptDao.insertPracticeAttempt(practiceAttemptList);

        // Delete all entries
        practiceAttemptDao.deleteAll();

        // Get all entries from the database
        LiveData<List<PracticeAttempt>> foundEntries = practiceAttemptDao.getAllPracticeAttempts();
        // Verify that datbase is empty
        Assert.assertEquals(0, LiveDataTestUtil.getValue(foundEntries).size());
    }
}
