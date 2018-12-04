package uk.ac.aber.dcs.cs31620.rhe24.lva.model.practice;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

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
     * Initializes Adapter
     * @param context
     */
    public PracticeRecyclerAdapter(Context context){
        this.context = context;
    }

    /**
     * Inner class to obtain the individual views that make up the recycler list
     */
    public class ViewHolder extends RecyclerView.ViewHolder{

        /**
         * The edit text view for answer input
         */
        private EditText answerInput;

        /**
         * The text listener object for each entry
         */
        public AnswerTextListener answerTextListener;


        /**
         * Data binding object for vocabulary entry
         */
        RecyclerViewPracticeEntryBinding vocabEntryBinding;

        ViewHolder(View practiceEntryView){
            super(practiceEntryView);
            vocabEntryBinding = DataBindingUtil.bind(practiceEntryView);

            // Initialize Answer Text listener
            answerTextListener = new AnswerTextListener();

            // Initialize answer input
            answerInput = practiceEntryView.findViewById(R.id.practice_answer_input);
            answerInput.addTextChangedListener(answerTextListener);
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
            // Update the text listerner's item so it can store the correct answer under the
            // correct key (position)
            holder.answerTextListener.setEntry(vocabularyList.get(position));
            holder.answerTextListener.setPosition(position);

            // Set the EditText contents to previously submitted answers
            // to preserve state after activity is re-created. (if exists)
            String oldAnswer =  ((PracticeActivity)context).getAnswer(position);
            holder.answerInput.setText(oldAnswer);
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

    /**
     * A text listener for each EditText view on the list to track user answers
     * and store them
     *
     * @author Rhys Evans
     * @version 3/12/2018
     */
    private class AnswerTextListener implements TextWatcher {

        /**
         * The vocabulary entry of the item
         */
        private VocabularyEntry entry;

        /**
         * The position of the entry in the list
         */
        private int position;

        /**
         * Set the vocabulary entry of the list position
         * @param entry
         */
        public void setEntry(VocabularyEntry entry){
            this.entry = entry;
        }

        /**
         * Update the position of the entry
         * @param position
         */
        public void setPosition(int position){
            this.position = position;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            // Do nothing
        }

        /**
         * Store user's answer when edit text content changes
         * @param charSequence
         * @param i
         * @param i1
         * @param i2
         */
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            // Update/Store answer in PracticeActivity
            PracticeAnswer newAnswer = new PracticeAnswer(entry.getWordPrimaryLanguage(), entry.getWordSecondaryLanguage(), charSequence.toString());

            ((PracticeActivity)context).storeAnswer(newAnswer, position);
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // Do nothing
        }
    }
}
