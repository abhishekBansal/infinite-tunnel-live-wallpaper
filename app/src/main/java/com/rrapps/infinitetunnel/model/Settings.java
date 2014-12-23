package com.rrapps.infinitetunnel.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.rrapps.infinitetunnel.R;

/**
 * Created by abhishek on 21/12/14.
 * Singleton class for managing current settings for this live wallpaper
 */
public class Settings {

    private static String IS_TEXTURE_CHANGED_KEY = "IsTextureChangedPrefKey";
    private Settings(Context context) {
        mContext = context;
        mSharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    private static Settings _instance;

    private Context mContext;

    /**
     * sharedprefs instance
     */
    private SharedPreferences mSharedPrefs;

    public static Settings getInstance(Context context) {
        if(_instance == null)
            _instance = new Settings(context);

        return _instance;
    }

    public boolean isTextureChanged() {
        return mSharedPrefs.getBoolean(IS_TEXTURE_CHANGED_KEY, false);
    }

    public void setTextureChanged(boolean settingsChanged) {
        mSharedPrefs.edit().putBoolean(IS_TEXTURE_CHANGED_KEY, settingsChanged)
            .apply();
    }

    public int getCurrentTextureNo() {
        return Integer.parseInt(mSharedPrefs.getString(mContext.getString(R.string.texture_pref_key),
                                                      "-1"));
    }
}
