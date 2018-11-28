package rhe24.cs31620.dcs.aber.ac.uk.lva.ui;

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
import android.widget.Toast;

import rhe24.cs31620.dcs.aber.ac.uk.lva.R;
import rhe24.cs31620.dcs.aber.ac.uk.lva.ui.tests_list.TestsListFragment;
import rhe24.cs31620.dcs.aber.ac.uk.lva.ui.vocabulary_list.VocabularyListFragment;

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
    private static final int TESTS_TAB = 1;

    /**
     * Called when the main activity is created
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lvamain);

        // Check if first time startup, if so display setup activity
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        // Check Shared Preferences for saved language
        if(!sharedPreferences.contains("PREF_PRIMARY_LANG") || !sharedPreferences.contains("PREF_SECONDARY_LANG")){
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
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            sharedPreferences.edit().clear().apply();

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
                case TESTS_TAB: return new TestsListFragment();
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
                case TESTS_TAB: return getResources().getText(R.string.tests_list_tab);
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
