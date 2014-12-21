package com.rrapps.infinitetunnel.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.rrapps.infinitetunnel.R;
import com.rrapps.infinitetunnel.SettingsActivity;

/**
 * Created by abhishek on 21/12/14.
 * Singleton class for managing current settings for this live wallpaper
 */
public class Settings {

    private Settings(Context context) {
        mContext = context;
        readSettings();
    }

    private static Settings _instance;

    private Context mContext;

    public static Settings getInstance(Context context) {
        if(_instance == null)
            _instance = new Settings(context);

        return _instance;
    }

    public boolean isSettingsChanged() {
        return mSettingsChanged;
    }

    public void setSettingsChanged(boolean settingsChanged) {
        mSettingsChanged = settingsChanged;
    }

    private boolean mSettingsChanged = false;

    public int getCurrentTextureNo() {
        return mCurrentTextureNo;
    }

    private int mCurrentTextureNo = 2;

    // reads and refreshes current settings
    public void readSettings() {
        SharedPreferences settings =
                mContext.getSharedPreferences(SettingsActivity.SHARED_PREF_NAME, Context.MODE_PRIVATE);

        mCurrentTextureNo =
                Integer.parseInt(settings.getString(mContext.getString(R.string.texture_pref_key),
                                                    "-1"));
    }
}
