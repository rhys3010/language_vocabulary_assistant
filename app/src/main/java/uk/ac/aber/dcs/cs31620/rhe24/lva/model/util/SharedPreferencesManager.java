package uk.ac.aber.dcs.cs31620.rhe24.lva.model.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * SharedPreferencesManager.java
 *
 * An utility class to manage all Shared Preferences
 * used to prevent key duplication and cleaner handling of shared preferences
 * @author Rhys Evans
 * @version 30/11/2018
 */
public class SharedPreferencesManager {

    /**
     * Initialize instance of singleton
     */
    private static SharedPreferencesManager INSTANCE = null;

    /**
     * Shared Preferences object
     */
    private static SharedPreferences sharedPreferences;

    /**
     * The editor object of the shared preferences
     */
    private static SharedPreferences.Editor editor;

    /**
     * Key for primary language preference
     */
    private static final String PRIMARY_LANGUAGE = "PREF_PRIMARY_LANGUAGE";

    /**
     * Key for secondary language preference
     */
    private static final String SECONDARY_LANGUAGE = "PREF_SECONDARY_LANGUAGE";

    /**
     * Empty private constructor to prevent multiple instances being created
     */
    private SharedPreferencesManager(){}

    /**
     * Returns the instance of the Shared Preferences Manager
     * @param context
     * @return
     */
    public static synchronized SharedPreferencesManager getInstance(Context context){
        if(INSTANCE == null){
            // Initialize shared preferences and its editor
            sharedPreferences = context.getSharedPreferences(context.getPackageName(), Activity.MODE_PRIVATE);
            editor = sharedPreferences.edit();

            INSTANCE = new SharedPreferencesManager();
        }

        return INSTANCE;
    }

    /**
     * Save the primary language to the Shared Preferences
     * @param value
     */
    public void putPrimaryLanguage(String value){
        editor.putString(PRIMARY_LANGUAGE, value);
        editor.apply();
    }

    /**
     * Save the secondary language to the Shared Preferences
     * @param value
     */
    public void putSecondaryLanguage(String value){
        editor.putString(SECONDARY_LANGUAGE, value);
        editor.apply();
    }

    /**
     * Get the primary language from the shared preferences
     * @return
     */
    public String getPrimaryLanguage(){
        return sharedPreferences.getString(PRIMARY_LANGUAGE, "LANG_NOT_FOUND");
    }

    /**
     * Get the secondary language from the shared preferences
     * @return
     */
    public String getSecondaryLanguage(){
        return sharedPreferences.getString(SECONDARY_LANGUAGE, "LANG_NOT_FOUND");
    }

    /**
     * Gets wether or not there are language preferences saved
     * @return
     */
    public boolean isLanguageSaved(){
        return sharedPreferences.contains(PRIMARY_LANGUAGE) && sharedPreferences.contains(SECONDARY_LANGUAGE);
    }

    /**
     * Delete both languages from shared preferences
     */
    public void deleteLanguages(){
        editor.remove(PRIMARY_LANGUAGE);
        editor.remove(SECONDARY_LANGUAGE);
        editor.apply();
    }

    /**
     * Delete all shared preferences
     */
    public void deleteAll(){
        editor.clear();
        editor.apply();
    }
}
