package rhe24.cs31620.dcs.aber.ac.uk.lva.ui;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import rhe24.cs31620.dcs.aber.ac.uk.lva.R;
import rhe24.cs31620.dcs.aber.ac.uk.lva.ui.tests_list.TestsListFragment;
import rhe24.cs31620.dcs.aber.ac.uk.lva.ui.vocabulary_list.VocabularyListFragment;

/**
 * LVAMainActivity
 * @author Rhys Evans
 * @version 21/11/2018
 */
public class LVAMainActivity extends AppCompatActivity {

    /**
     * Called when the main activity is created
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lvamain);

        // Set our toolbar to replace the app's default action bar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Attach pager adapter to ViewPager
        SectionsPagerAdapter pagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        ViewPager pager = findViewById(R.id.pager);
        pager.setAdapter(pagerAdapter);

        // Add tabs to app bar
        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(pager);
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
                case 0: return new VocabularyListFragment();
                case 1: return new TestsListFragment();
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
                case 0: return getResources().getText(R.string.vocabulary_list_tab);
                case 1: return getResources().getText(R.string.tests_list_tab);
            }
            return null;
        }

        /**
         * Returns the total number of pages within the pager
         * @return
         */
        @Override
        public int getCount(){
            return 2;
        }
    }
}
