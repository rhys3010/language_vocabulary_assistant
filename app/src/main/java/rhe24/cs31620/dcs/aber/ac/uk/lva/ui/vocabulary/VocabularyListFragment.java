package rhe24.cs31620.dcs.aber.ac.uk.lva.ui.vocabulary;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import rhe24.cs31620.dcs.aber.ac.uk.lva.R;

/**
 * VocabularyListFragment
 * @author Rhys Evans
 * @version 21/11/2018
 */
public class VocabularyListFragment extends Fragment {


    public VocabularyListFragment() {
        // Required empty public constructor
    }


    /**
     * OnCreate Method to perform all operations needed on fragment creation
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

        // TEMP - Show languages in textview
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(container.getContext());

        TextView languageLabel = view.findViewById(R.id.language_label);
        languageLabel.setText("Primary Language: " + sharedPreferences.getString("PREF_PRIMARY_LANG", null) + " Secondary Language: " + sharedPreferences.getString("PREF_SECONDARY_LANG", null));


        return view;
    }

}
