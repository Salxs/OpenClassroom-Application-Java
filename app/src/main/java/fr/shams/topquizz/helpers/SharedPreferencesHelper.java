package fr.shams.topquizz.helpers;

import static android.content.Context.MODE_PRIVATE;

import android.content.ContextWrapper;

public class SharedPreferencesHelper {
    public SharedPreferencesHelper(ContextWrapper contextWrapper) {
        this.contextWrapper = contextWrapper;
    }

    static final String SHARED_PREF_USER_INFO = "SHARED_PREF_USER_INFO";
    public static final String SHARED_PREF_USER_NAME = "SHARED_PREF_USER_NAME";
    public static final String SHARED_PREF_USER_SCORE = "SHARED_PREF_USER_SCORE";

    private ContextWrapper contextWrapper;


    public void saveValueInSharedPref(String sharedPrefKey, String value) {
        contextWrapper.getSharedPreferences(SHARED_PREF_USER_INFO, MODE_PRIVATE)
                .edit()
                .putString(sharedPrefKey, value)
                .apply();

    }

    public String getValueFromSharedPref(String sharedPrefKey, String defaultValue) {
        return contextWrapper.getSharedPreferences(SHARED_PREF_USER_INFO, MODE_PRIVATE).getString(sharedPrefKey, defaultValue);
    }

}
