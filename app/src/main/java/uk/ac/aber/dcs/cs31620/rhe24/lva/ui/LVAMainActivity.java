package uk.ac.aber.dcs.cs31620.rhe24.lva.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import uk.ac.aber.dcs.cs31620.rhe24.lva.R;
import uk.ac.aber.dcs.cs31620.rhe24.lva.model.util.SharedPreferencesManager;
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
     * Shared preference manager
     */
    private SharedPreferencesManager sharedPreferencesManager;

    /**
     * Called when the main activity is created
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lvamain);

        // Initialize shared preference manager
        sharedPreferencesManager = SharedPreferencesManager.getInstance(getApplicationContext());

        // Check is there is already a language selection saved
        if(!sharedPreferencesManager.isLanguageSaved()){
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

        // TODO: Actually Implement
        // If option is change language
        if(item.getItemId() == R.id.nav_change_language){
            // Remove language preferences from SharedPreferences and force user back to setup screen
            sharedPreferencesManager.deleteLanguages();

            Intent intent = new Intent(this, LVASetupActivity.class);
            startActivity(intent);
            finish();
        }

        return true;
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
