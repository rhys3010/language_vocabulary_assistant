package uk.ac.aber.dcs.cs31620.rhe24.lva.model.practice;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import uk.ac.aber.dcs.cs31620.rhe24.lva.R;
import uk.ac.aber.dcs.cs31620.rhe24.lva.databinding.RecyclerViewPracticeEntryBinding;
import uk.ac.aber.dcs.cs31620.rhe24.lva.model.vocabulary.VocabularyEntry;
import uk.ac.aber.dcs.cs31620.rhe24.lva.ui.practice.PracticeActivity;

/**
 * PracticeRecyclerAdapter.java
 *
 * Recycler View Adapter to display vocabulary list on practice screen
 * @author Rhys Evans
 * @version 3/12/2018
 */
public class PracticeRecyclerAdapter extends RecyclerView.Adapter<PracticeRecyclerAdapter.ViewHolder>{

    /**
     * Application context
     */
    private final Context context;

    /**
     * The dataset to display (vocabulary entry list)
     */
    private List<VocabularyEntry> vocabularyList;

    /**
     * The viewmodel of the practice - used to interface with persistent data
     */
    private PracticeViewModel practiceViewModel;

    /**
     * Initializes Adapter
     * @param context
     */
    public PracticeRecyclerAdapter(Context context){
        this.context = context;

        // Initialize view model
        practiceViewModel = ViewModelProviders.of((PracticeActivity)context).get(PracticeViewModel.class);
    }

    /**
     * Inner class to obtain the individual views that make up the recycler list
     */
    public class ViewHolder extends RecyclerView.ViewHolder{

        /**
         * Data binding object for vocabulary entry
         */
        RecyclerViewPracticeEntryBinding vocabEntryBinding;

        ViewHolder(View practiceEntryView){
            super(practiceEntryView);
            vocabEntryBinding = DataBindingUtil.bind(practiceEntryView);
        }

        /**
         * Bind practice entry
         * @param vocabularyEntry
         */
        void bindDataSet(VocabularyEntry vocabularyEntry){
            vocabEntryBinding.setVocabEntry(vocabularyEntry);
        }
    }


    /**
     * Get the number of entries in the data
     */
    public int getItemCount(){
        if(vocabularyList != null){
            return vocabularyList.size();
        }else{
            return 0;
        }
    }

    /**
     * Called when the container for practice question entry is needed by the recycler view
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_practice_entry, parent, false);
        return new ViewHolder(view);
    }

    /**
     * Add content to practice entry view
     * @param holder
     * @param position
     */
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position){
        if(vocabularyList != null){
            holder.bindDataSet(vocabularyList.get(position));
        }
    }

    /**
     * If the dataset is reset/updated, inform adapter
     * @param vocabularyList
     */
    public void changeDataSet(List<VocabularyEntry> vocabularyList){
        this.vocabularyList = vocabularyList;
        notifyDataSetChanged();
    }
}
