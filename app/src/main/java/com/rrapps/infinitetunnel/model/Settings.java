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

    public int getCurrentTextureResId() {
        int tNo =  Integer.parseInt(mSharedPrefs.getString(mContext.getString(R.string.texture_pref_key),
                                                      "-1"));
        switch(tNo) {
            case 1:
                return R.drawable.brick_red;
            case 2:
                return R.drawable.bricks_stone;
            case 3:
                return R.drawable.metal_green;
            case 4:
                return R.drawable.nebula3;
            case 5:
                return R.drawable.round_brick_tilable;
            case 6:
                return R.drawable.tiles_blue_pattern;
            case 7:
                return R.drawable.tiles_green_white;
            case 8:
                return R.drawable.wood_fine_brown;
        }

        return R.drawable.brick_red;
    }
}
