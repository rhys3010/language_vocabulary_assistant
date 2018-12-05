package uk.ac.aber.dcs.cs31620.rhe24.lva.espresso_tests;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import uk.ac.aber.dcs.cs31620.rhe24.lva.R;
import uk.ac.aber.dcs.cs31620.rhe24.lva.ui.LVAMainActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * AddNewVocabularyEntryDialogTest.java
 *
 * Test that pressing the FAB opens a add new vocabulary entry dialog
 *
 * @author Rhys Evans
 * @version 3/12/2018
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class AddNewVocabularyEntryDialogTest {

    @Rule
    public ActivityTestRule<LVAMainActivity> lvaMainActivityActivityTestRule = new ActivityTestRule<>(LVAMainActivity.class);

    @Test
    public void clickFab_opensAddNewVocabularyEntryDialog() throws Exception{
        // Click on the fab
        onView(withId(R.id.vocabulary_fab)).perform(click());

        // Check that add new vocabulary entry dialog is showing
        onView(withText(R.string.dialog_add_word_title)).check(matches(isDisplayed()));

    }
}
