package uk.ac.aber.dcs.cs31620.rhe24.lva.database_tests;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;

import uk.ac.aber.dcs.cs31620.rhe24.lva.Injection;
import uk.ac.aber.dcs.cs31620.rhe24.lva.database_tests.util.LiveDataTestUtil;
import uk.ac.aber.dcs.cs31620.rhe24.lva.database_tests.util.TestUtil;
import uk.ac.aber.dcs.cs31620.rhe24.lva.datasource.RoomDatabaseI;
import uk.ac.aber.dcs.cs31620.rhe24.lva.model.vocabulary.VocabularyEntry;
import uk.ac.aber.dcs.cs31620.rhe24.lva.model.vocabulary.VocabularyEntryDao;

/**
 * VocabularyEntryTests.java
 *
 * Tests all CRUD operations through the VocabularyEntryDao to ensure
 * they are performed successfully.
 *
 * @author Rhys Evans
 * @version 4/12/2018
 */
@RunWith(AndroidJUnit4.class)
public class VocabularyEntryTests {

    /**
     * Execute database queries etc, synchronously for testing purposes
     */
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    /**
     * The vocabulary entry dao
     */
    private VocabularyEntryDao vocabularyEntryDao;

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
    public void createDb(){
        Context context = InstrumentationRegistry.getTargetContext();
        db = Injection.getDatabase(context);
        vocabularyEntryDao = db.getVocabularyEntryDao();
    }

    /**
     * Close the database after the test is ran
     */
    @After
    public void closeDb(){
        db.closeDb();
    }

    /**
     * Test that inserting a vocabulary entry actually inserts it
     */
    @Test
    public void onInsertingVocabularyEntry_checkThat_vocabularyEntryWasInserted() throws Exception{
        List<VocabularyEntry> vocabularyEntries = testUtil.createVocabularyList(1);

        vocabularyEntryDao.insertVocabularyEntry(vocabularyEntries.get(0));

        LiveData<List<VocabularyEntry>> foundEntries = vocabularyEntryDao.getAllVocabularyEntries();
        Assert.assertEquals(1, LiveDataTestUtil.getValue(foundEntries).size());

    }

    /**
     * Test that deleting all entries actually deletes them
     */
    @Test
    public void onDeleteAllVocabularyEntries_checkThat_allVocabularyEntriesWereDeleted() throws Exception{
        // Create a list of 10 entries
        List<VocabularyEntry> vocabularyEntries = testUtil.createVocabularyList(10);

        // Insert them into the database
        vocabularyEntryDao.insertVocabularyEntry(vocabularyEntries);

        // Delete all entries
        vocabularyEntryDao.deleteAll();

        // Get all entries from the database
        LiveData<List<VocabularyEntry>> foundEntries = vocabularyEntryDao.getAllVocabularyEntries();
        // Verify that datbase is empty
        Assert.assertEquals(0, LiveDataTestUtil.getValue(foundEntries).size());
    }
}
