package uk.ac.aber.dcs.cs31620.rhe24.lva.ui.vocabulary;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

/**
 * VocabuaryListRecyclerView.java
 *
 * Custom recycler view to handle empty list display
 * Taken from google's example:
 * http://github.com/googlesamples/android-XYZTouristAttractions
 * @author Rhys Evans
 * @version 1/12/2018
 */
public class VocabularyListRecyclerView extends RecyclerView {

    /**
     * The view to be displayed if vocab list is empty
     */
    private View emptyView;

    /**
     * A data observer to check if recycer view has become empty or has become populated
     */
    private AdapterDataObserver dataObserver = new AdapterDataObserver() {

        @Override
        public void onChanged() {
            super.onChanged();

            // If data has changed update the view (if needed)
            updateEmptyView();
        }
    };

    /**
     * Call superclass's constructor
     * @param context
     */
    public VocabularyListRecyclerView(Context context){
        super(context);
    }

    /**
     * Overloaded constructor to account for attributes
     * @param context
     * @param attrs
     */
    public VocabularyListRecyclerView(Context context, AttributeSet attrs){
        super(context, attrs);
    }

    /**
     * Overloaded constructor
     * @param context
     * @param attrs
     * @param defStyle
     */
    public VocabularyListRecyclerView(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);
    }

    /**
     * Designate the view that should be displayed if the vocab list is empty
     * @param emptyView
     */
    public void setEmptyView(View emptyView){
        this.emptyView = emptyView;
    }

    /**
     * Override the recycler view's default set adapter method
     * @param adapter
     */
    @Override
    public void setAdapter(RecyclerView.Adapter adapter){

        if(getAdapter() != null){
            getAdapter().unregisterAdapterDataObserver(dataObserver);
        }

        if(adapter != null){
            adapter.registerAdapterDataObserver(dataObserver);
        }

        super.setAdapter(adapter);
        updateEmptyView();
    }

    /**
     * Update the view to show either the empty view or the recycler view
     */
    private void updateEmptyView(){
        if(emptyView != null && getAdapter() != null){
            // If recycler view is empty, set this value to true
            boolean showEmptyView = getAdapter().getItemCount() == 0;

            // Decide visibility of the empty view
            emptyView.setVisibility(showEmptyView ? VISIBLE : GONE);

            // Decide visibility of the recycler view
            setVisibility(showEmptyView ? GONE : VISIBLE);
        }
    }
}
