package uk.ac.aber.dcs.cs31620.rhe24.lva.model.vocabulary;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;

import java.util.List;

import uk.ac.aber.dcs.cs31620.rhe24.lva.R;
import uk.ac.aber.dcs.cs31620.rhe24.lva.databinding.RecyclerViewVocabEntryBinding;
import uk.ac.aber.dcs.cs31620.rhe24.lva.ui.LVAMainActivity;
import uk.ac.aber.dcs.cs31620.rhe24.lva.ui.vocabulary.AddVocabularyEntryDialogFragment;
import uk.ac.aber.dcs.cs31620.rhe24.lva.ui.vocabulary.EditVocabularyEntryDialogFragment;

/**
 * Recycler View Adapter to display vocabulary list
 * @author Rhys Evans
 * @version 29/11/2018
 */
public class VocabularyListRecyclerAdapter extends RecyclerView.Adapter<VocabularyListRecyclerAdapter.ViewHolder> {

    /**
     * Application Context
     */
    private final Context context;
    /**
     * The vocabulary entry list (dataset)
     */
    private List<VocabularyEntry> vocabularyEntryList;

    /**
     * Initializes Adapter
     * @param context
     */
    public VocabularyListRecyclerAdapter(Context context){
        this.context = context;
    }

    /**
     * Inner Class to reference the individual views for each item within the recyclerview
     * @author Rhys Evans
     * @version 29/11/2018
     */
    public class ViewHolder extends RecyclerView.ViewHolder {

        /**
         * The popup menu icon for every entry list
         */
        private ImageView popupMenuIcon;

        /**
         * The data binding object for vocab entry
         */
        RecyclerViewVocabEntryBinding vocabEntryBinding;

        ViewHolder(View entryView) {
            super(entryView);
            vocabEntryBinding = DataBindingUtil.bind(entryView);

            // Initialize popup menu icon
            popupMenuIcon = entryView.findViewById(R.id.vocab_entry_popup_menu);
        }

        /**
         * Bind entry view to row from database.
         *
         * @param vocabEntry
         */
        void bindDataSet(VocabularyEntry vocabEntry) {
            vocabEntryBinding.setVocabEntry(vocabEntry);
        }

    }

    /**
     * Returns the number of entries in the data
     *
     * @return
     */
    @Override
    public int getItemCount() {
        if (vocabularyEntryList != null) {
            return vocabularyEntryList.size();
        } else {
            return 0;
        }
    }


    /**
     * Called when container for vocab entry is needed by recycler view
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        // Create a new vocab entry view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_vocab_entry, parent, false);

        view.findViewById(R.id.vocab_entry_popup_menu);

        return new ViewHolder(view);
    }

    /**
     * Change the contents of the vocab entry view
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position){
        if(vocabularyEntryList != null){
            holder.bindDataSet(vocabularyEntryList.get(position));
        }

        // Add behaviour to popup menu icon
        holder.popupMenuIcon.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                // Create the popup menu
                PopupMenu popupMenu = new PopupMenu(context, holder.popupMenuIcon);
                popupMenu.inflate(R.menu.menu_vocab_entry);

                // Add click listener for the popup menu
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch(menuItem.getItemId()){

                            // Open the edit entry dialog
                            case R.id.vocab_entry_menu_edit:
                                EditVocabularyEntryDialogFragment editVocabDialog = EditVocabularyEntryDialogFragment.newInstance();
                                FragmentManager fm = ((LVAMainActivity)context).getSupportFragmentManager();
                                editVocabDialog.show(fm, "fragment_edit_vocabulary_entry");
                                break;

                            case R.id.vocab_entry_menu_delete:
                                break;
                        }

                        return false;
                    }
                });

                // Show the popup
                popupMenu.show();
            }

        });
    }

    /**
     * If the vocabulary list (dataset) is reset, inform the adapter
     * @param vocabularyEntryList
     */
    public void changeDataSet(List<VocabularyEntry> vocabularyEntryList){
        this.vocabularyEntryList = vocabularyEntryList;
        notifyDataSetChanged();
    }
}
