package uk.ac.aber.dcs.cs31620.rhe24.lva.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import uk.ac.aber.dcs.cs31620.rhe24.lva.R;
import uk.ac.aber.dcs.cs31620.rhe24.lva.model.util.SharedPreferencesManager;
import uk.ac.aber.dcs.cs31620.rhe24.lva.model.vocabulary.VocabularyListViewModel;
import uk.ac.aber.dcs.cs31620.rhe24.lva.ui.practice.PracticeFragment;
import uk.ac.aber.dcs.cs31620.rhe24.lva.ui.vocabulary.VocabularyListFragment;

/**
 * LVAMainActivity
 * The app's main activity, handles all fragments and nav drawer
 * @author Rhys Evans
 * @version 21/11/2018
 */
public class LVAMainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    /**
     * Store tabs as constants
     */
    private static final int VOCAB_LIST_TAB = 0;
    private static final int PRACTICE_TAB = 1;

    /**
     * The view model class to interface with the persistent data
     */
    private VocabularyListViewModel vocabularyListViewModel;

    /**
     * Called when the main activity is created
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lvamain);
        // Initialize View Model
        vocabularyListViewModel = ViewModelProviders.of(this).get(VocabularyListViewModel.class);

        // Check is there is already a language selection saved
        if(!vocabularyListViewModel.isLanguageSaved()){
            // If no language present, shows setup activity to prompt user to complete setup
            Intent intent = new Intent(this, LVASetupActivity.class);
            startActivity(intent);
            finish();
        }

        // Set toolbar to replace the app's default action bar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Add navigation drawer to hamburger icon
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.nav_open_drawer, R.string.nav_close_drawer);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // Attatch listener to navigation drawer
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Attach pager adapter to ViewPager
        SectionsPagerAdapter pagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        ViewPager pager = findViewById(R.id.pager);
        pager.setAdapter(pagerAdapter);
        pager.addOnPageChangeListener(addPageChangeListener());

        // Add tabs to app bar
        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(pager);
    }

    /**
     * Handle option selection from the navigation drawer
     * @param item
     * @return
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        switch(item.getItemId()){
            case R.id.nav_change_language:
                changeLanguagePreferences(this);
                break;

            case R.id.nav_delete_vocab:
                deleteVocabulary();
                break;

            case R.id.nav_about:
                break;
        }

        // Close nav drawer after selection is made
        drawer.closeDrawers();

        return true;
    }

    /**
     * Prompt for confirmation and delete the user's language preferences
     * (+ vocabulary)
     */
    private void changeLanguagePreferences(final AppCompatActivity activity){

        // Prompt user for confirmation first
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this, R.style.DialogTheme);
        dialogBuilder.setTitle(R.string.change_language_dialog_title);
        dialogBuilder.setMessage(R.string.change_language_dialog_message);
        // Set negative button to do nothing
        dialogBuilder.setNegativeButton(android.R.string.no, null);

        // Set positive button to delete language preferences and return to setup screen
        dialogBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Delete Language Preferences
                vocabularyListViewModel.deleteLanguages();
                // Delete vocabulary
                vocabularyListViewModel.deleteVocabularyList();

                // Change screen
                // Switch to setup screen
                Intent intent = new Intent(activity, LVASetupActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Show the dialog
        dialogBuilder.show();
    }

    /**
     * Prompt for confirmation and deletet the user's vocabulary.
     */
    private void deleteVocabulary(){
        // Prompt user for confirmation first
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this, R.style.DialogTheme);
        dialogBuilder.setTitle(R.string.delete_vocabulary_dialog_title);
        dialogBuilder.setMessage(R.string.delete_vocabulary_dialog_message);
        // Set negative button to do nothing
        dialogBuilder.setNegativeButton(android.R.string.no, null);

        // Set positive button to delete language preferences and return to setup screen
        dialogBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Delete vocabulary
                vocabularyListViewModel.deleteVocabularyList();

                // Display snackbar
                View contentView = findViewById(R.id.coordinator_layout);
                String snackbarMessage = getString(R.string.snackbar_vocab_deleted);

                Snackbar snackbar = Snackbar.make(contentView, snackbarMessage, Snackbar.LENGTH_LONG);

                snackbar.show();
            }
        });

        // Show the dialog
        dialogBuilder.show();
    }

    /**
     * Hide FAB on different pages using ViewPager events
     */
    private ViewPager.OnPageChangeListener addPageChangeListener(){
        return new ViewPager.OnPageChangeListener(){

            FloatingActionButton fab = findViewById(R.id.vocabulary_fab);

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels){
                // When page is scrolled, expose appbar again
                AppBarLayout appBarLayout = findViewById(R.id.app_bar_layout);
                appBarLayout.setExpanded(true);
            }

            /**
             * Hide/Show fab depending on current tab
             * @param position
             */
            @Override
            public void onPageSelected(int position){
                switch(position){
                    case VOCAB_LIST_TAB:
                        fab.show();
                        break;
                    default:
                        fab.hide();
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state){
            }

        };
    }

    /**
     * SectionsPagerAdapter
     * @author Rhys Evans
     * @version 21/11/2018
     */
    private class SectionsPagerAdapter extends FragmentPagerAdapter{

        /**
         * Constructor for the pager adapter
         * @param fm
         */
        public SectionsPagerAdapter(FragmentManager fm){
            super(fm);
        }

        /**
         * Get a given item in the pager
         * @param position
         * @return
         */
        @Override
        public Fragment getItem(int position){
            switch(position){
                case VOCAB_LIST_TAB: return new VocabularyListFragment();
                case PRACTICE_TAB: return new PracticeFragment();
            }

            return null;
        }

        /**
         * Get a title of a particular page within the pager
         * @param position
         * @return
         */
        public CharSequence getPageTitle(int position){
            switch(position){
                case VOCAB_LIST_TAB: return getResources().getText(R.string.vocabulary_list_tab);
                case PRACTICE_TAB: return getResources().getText(R.string.practice_tab);
            }
            return null;
        }

        /**
         * Returns the total number of pages within the pager
         * @return 2
         */
        @Override
        public int getCount(){
            return 2;
        }
    }
}
