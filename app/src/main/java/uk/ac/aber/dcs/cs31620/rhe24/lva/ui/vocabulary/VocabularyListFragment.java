package uk.ac.aber.dcs.cs31620.rhe24.lva.ui.vocabulary;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.Objects;

import uk.ac.aber.dcs.cs31620.rhe24.lva.R;
import uk.ac.aber.dcs.cs31620.rhe24.lva.datasource.LVARepository;
import uk.ac.aber.dcs.cs31620.rhe24.lva.model.util.SharedPreferencesManager;
import uk.ac.aber.dcs.cs31620.rhe24.lva.model.vocabulary.VocabularyEntry;
import uk.ac.aber.dcs.cs31620.rhe24.lva.model.vocabulary.VocabularyListRecyclerAdapter;
import uk.ac.aber.dcs.cs31620.rhe24.lva.model.vocabulary.VocabularyListViewModel;

/**
 * VocabularyListFragment
 * The fragment that displays all vocabulary entries using a recyclerview
 * @author Rhys Evans
 * @version 21/11/2018
 */
public class VocabularyListFragment extends Fragment{

    /**
     * The view model class to interface with the database
     */
    private VocabularyListViewModel vocabularyListViewModel;

    /**
     * Recycler View adapter for the vocabulary list
     */
    private VocabularyListRecyclerAdapter vocabularyListAdapter;

    /**
     * The previous vocabulary list
     */
    private LiveData<List<VocabularyEntry>> oldVocabularyEntries;

    /**
     * The recycler view that lists all vocab entries
     */
    private RecyclerView vocabularyEntriesList;


    public VocabularyListFragment() {
        // Required empty public constructor
    }


    /**
     * OnCreate Method to perform all operations needed to create vocabulary list fragment
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Create view by inflating layout
        final View view = inflater.inflate(R.layout.fragment_vocabulary_list, container, false);

        // Initialize View Model
        vocabularyListViewModel = ViewModelProviders.of(this).get(VocabularyListViewModel.class);

        vocabularyListAdapter = vocabularyListViewModel.getAdapter();

        // If adapter has not yet been created
        if(vocabularyListAdapter == null){
            vocabularyListAdapter = new VocabularyListRecyclerAdapter(getContext());
            vocabularyListViewModel.setAdapter(vocabularyListAdapter);
        }

        setupRecyclerView(view);
        setupFab(view);
        setupObserver();

        // Set list language preference headings
        TextView primaryLanguageLabel = view.findViewById(R.id.language_preference_primary);
        TextView secondaryLanguageLabel = view.findViewById(R.id.language_preference_secondary);

        primaryLanguageLabel.setText(vocabularyListViewModel.getPrimaryLanguage());
        secondaryLanguageLabel.setText(vocabularyListViewModel.getSecondaryLanguage());

        return view;
    }


    /**
     * Handle the Live Data observer associated with the vocabulary list
     */
    private void setupObserver(){
        LiveData<List<VocabularyEntry>> vocabularyEntriesList = vocabularyListViewModel.getVocabularyList();

        // Remove any pre-existing observers if the list of vocabulary has changed
        if(!Objects.equals(oldVocabularyEntries, vocabularyEntriesList)){
            if(oldVocabularyEntries != null){
                oldVocabularyEntries.removeObservers(this);
            }
            oldVocabularyEntries = vocabularyEntriesList;
        }

        // Add an observer to the live data if there doesn't already exist one
        if(!vocabularyEntriesList.hasActiveObservers()){
            vocabularyEntriesList.observe(this, new Observer<List<VocabularyEntry>>(){

                @Override
                public void onChanged(@Nullable List<VocabularyEntry> vocabularyEntries){
                    vocabularyListAdapter.changeDataSet(vocabularyEntries);
                }
            });
        }
    }

    /**
     * Setup the recycler view
     * and accommodate fab dissapearing on scroll
     * (Code adapted from: https://stackoverflow.com/questions/31617398/floatingactionbutton-hide-on-list-scroll)
     */
    private void setupRecyclerView(View view){
        // A little bit hacky, should probably use some kind of interface to get views that belong to parent
        // activity, but for just one view it would be a little excessive.
        final FloatingActionButton fab = getActivity().findViewById(R.id.vocabulary_fab);

        // Initialize recycler view
        vocabularyEntriesList = view.findViewById(R.id.vocabulary_list);

        // Attach Adapter to Recycler View
        vocabularyEntriesList.setAdapter(vocabularyListAdapter);
        vocabularyEntriesList.setLayoutManager(new LinearLayoutManager(getContext()));

        // Handle Scrolling
        vocabularyEntriesList.addOnScrollListener(new RecyclerView.OnScrollListener() {

            /**
             * When recycle view is scrolled, hide the fab (if not already hidden)
             * @param recyclerView
             * @param dx
             * @param dy
             */
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy){
                // Check change in y axis scroll
                if(dy > 0 || dy < 0 && fab.isShown()){
                    fab.hide();
                }

                // If recycle view can scroll no further, show FAB and cancel current scroll
                // (fixes weird delay when recycle view is flung to top/bottom)
                if(!recyclerView.canScrollVertically(-1) || !recyclerView.canScrollVertically(1)){
                    fab.show();
                    recyclerView.stopScroll();
                }
            }

            /**
             * When recycle view is done scrolling, show the fab again
             * @param recyclerView
             * @param newState
             */
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                // If scrolling has stopped, show FAB
                if(newState == RecyclerView.SCROLL_STATE_IDLE){
                    fab.show();
                }


                super.onScrollStateChanged(recyclerView, newState);
            }
        });
    }

    /**
     * Setup the Floating Action Button to open new vocab entry
     * dialog when clicked
     * @param view
     */
    private void setupFab(View view){
        final FloatingActionButton fab = getActivity().findViewById(R.id.vocabulary_fab);

        fab.setOnClickListener(new View.OnClickListener(){

            /**
             * Open the custom add new vocab entry dialog when clicked
             * @param view
             */
            @Override
            public void onClick(View view){
                AddVocabularyEntryDialogFragment addVocabDialog = AddVocabularyEntryDialogFragment.newInstance();
                addVocabDialog.show(getActivity().getFragmentManager(), "fragment_add_vocabulary_entry");
            }
        });
    }

}
