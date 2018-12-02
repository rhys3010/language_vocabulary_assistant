package uk.ac.aber.dcs.cs31620.rhe24.lva.ui.vocabulary;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.util.List;
import java.util.Objects;

import uk.ac.aber.dcs.cs31620.rhe24.lva.R;
import uk.ac.aber.dcs.cs31620.rhe24.lva.model.vocabulary.VocabularyEntry;
import uk.ac.aber.dcs.cs31620.rhe24.lva.model.vocabulary.VocabularyListRecyclerAdapter;
import uk.ac.aber.dcs.cs31620.rhe24.lva.model.vocabulary.VocabularyListSortType;
import uk.ac.aber.dcs.cs31620.rhe24.lva.model.vocabulary.VocabularyListViewModel;

/**
 * VocabularyListFragment
 * The fragment that displays all vocabulary entries using a recyclerview
 * @author Rhys Evans
 * @version 21/11/2018
 */
public class VocabularyListFragment extends Fragment{

    /**
     * The view model class to interface with the persistent data
     */
    private VocabularyListViewModel vocabularyListViewModel;

    /**
     * Recycler View adapter for the vocabulary list
     */
    private VocabularyListRecyclerAdapter vocabularyListAdapter;

    /**
     * The vocabulary list
     */
    private LiveData<List<VocabularyEntry>> vocabularyEntriesList;

    /**
     * The previous vocabulary list
     */
    private LiveData<List<VocabularyEntry>> oldVocabularyEntries;


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

        // Initialize adapter
        vocabularyListAdapter = vocabularyListViewModel.getAdapter();

        // If adapter has not yet been created
        if(vocabularyListAdapter == null){
            vocabularyListAdapter = new VocabularyListRecyclerAdapter(getContext());
            vocabularyListViewModel.setAdapter(vocabularyListAdapter);
        }

        vocabularyEntriesList = vocabularyListViewModel.getVocabularyList(VocabularyListSortType.UNSORTED);

        setupRecyclerView(view);
        setupFab(view);
        setupObserver();
        setupSortIcon(view);

        // Set list language preference headings from Shared Preferences
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

        // The recycler view
        VocabularyListRecyclerView vocabularyEntriesList = view.findViewById(R.id.vocabulary_list);
        // The recycler view's empty alternative
        View emptyVocabularyEntriesList = view.findViewById(R.id.vocabulary_list_empty);

        // Attach Adapter to Recycler View
        vocabularyEntriesList.setAdapter(vocabularyListAdapter);
        vocabularyEntriesList.setLayoutManager(new LinearLayoutManager(getContext()));
        // Assign the empty view
        vocabularyEntriesList.setEmptyView(emptyVocabularyEntriesList);

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
                addVocabDialog.show(getActivity().getSupportFragmentManager(),"fragment_add_vocabulary_entry");
            }
        });
    }

    /**
     * Setup the sort icon to display a popup menu
     */
    private void setupSortIcon(View view){

        // The sort icon
        final ImageView sortIcon = view.findViewById(R.id.vocab_list_sort_icon);
        // Open popup menu on click
        sortIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Create the popup menu
                PopupMenu popupMenu = new PopupMenu(getContext(), sortIcon);
                popupMenu.inflate(R.menu.menu_vocab_list_sort);

                // Add click listener for the popup menu
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch(menuItem.getItemId()){

                            // Sort entries by created date in ascending order and update observer
                            case R.id.vocab_list_sort_created_asc:
                                vocabularyEntriesList = vocabularyListViewModel.getVocabularyList(VocabularyListSortType.DATE_CREATED_ASC);
                                break;

                            // Sort entries by created date in descending order and update observer
                            case R.id.vocab_list_sort_created_desc:
                                vocabularyEntriesList = vocabularyListViewModel.getVocabularyList(VocabularyListSortType.DATE_CREATED_DESC);
                                break;

                            case R.id.vocab_list_sort_alphabet_asc:
                                vocabularyEntriesList = vocabularyListViewModel.getVocabularyList(VocabularyListSortType.ALPHABETICAL_ASC);
                                break;

                            case R.id.vocab_list_sort_alphabet_desc:
                                vocabularyEntriesList = vocabularyListViewModel.getVocabularyList(VocabularyListSortType.ALPHABETICAL_DESC);
                                break;

                        }

                        // Update Observer
                        setupObserver();

                        return false;
                    }
                });

                // Show the popup
                popupMenu.show();

            }
        });

    }
}
