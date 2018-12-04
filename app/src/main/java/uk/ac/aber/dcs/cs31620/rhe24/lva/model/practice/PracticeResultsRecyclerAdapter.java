package uk.ac.aber.dcs.cs31620.rhe24.lva.model.practice;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import uk.ac.aber.dcs.cs31620.rhe24.lva.R;
import uk.ac.aber.dcs.cs31620.rhe24.lva.databinding.RecyclerViewPracticeResultsBinding;

/**
 * PracticeResultsRecyclerAdapter.java
 *
 * Recycler Adapter to display and correct the user's incorrect answers
 * to practice questions in results dialog.
 *
 * @author Rhys Evans
 * @version 3/12/2018
 */
public class PracticeResultsRecyclerAdapter extends RecyclerView.Adapter<PracticeResultsRecyclerAdapter.ViewHolder>{

    /**
     * Application context
     */
    private final Context context;

    /**
     * List of all incorrect answers to display
     */
    private List<PracticeAnswer> answersList;

    /**
     * Constructor to initialize context
     * @param context
     */
    public PracticeResultsRecyclerAdapter(Context context){
        this.context = context;
    }

    /**
     * Inner class to reference the individual views for each item in the list
     */
    public class ViewHolder extends RecyclerView.ViewHolder{

        /**
         * Data binding object for Practice Answer
         */
        RecyclerViewPracticeResultsBinding practiceResultsBinding;

        ViewHolder(View itemView){
            super(itemView);
            practiceResultsBinding = DataBindingUtil.bind(itemView);
        }

        /**
         * Bind data from list to given item in list
         * @param practiceAnswer
         */
        void bindDataSet(PracticeAnswer practiceAnswer){
            practiceResultsBinding.setAnswer(practiceAnswer);
        }
    }

    /**
     * Called when container for list item is needed by the recycler view
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    @NonNull
    public PracticeResultsRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_practice_results, parent, false);

        return new ViewHolder(view);
    }

    /**
     * Returns the amount of items in the list
     * @return
     */
    @Override
    public int getItemCount(){
        if(answersList != null){
            return answersList.size();
        }else{
            return 0;
        }
    }

    /**
     * Set the contents of the list item's view
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position){
        if(answersList != null){
            holder.bindDataSet(answersList.get(position));
        }
    }

    /**
     * Update the dataset and notify observer
     * @param answersList
     */
    public void changeDataSet(List<PracticeAnswer> answersList){
        this.answersList = answersList;
        notifyDataSetChanged();
    }
}
