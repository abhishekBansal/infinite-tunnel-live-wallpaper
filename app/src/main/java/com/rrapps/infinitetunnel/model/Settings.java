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

    /**
     *
     * @return speed in range (0.2, 2)
     */
    public float getSpeed() {
        int speed = mSharedPrefs.getInt(mContext.getString(R.string.speed_pref_key), 5);
        if(speed <= 0)
            speed = 1;
        return (float) speed / 5.0f;
    }


    /**
     *
     * @return brightness in range of (0.3, 1.3)
     */
    public float getBrightness() {
        int brightness = mSharedPrefs.getInt(mContext.getString(R.string.brightness_pref_key), 5);
        return ( ((float) brightness / 10.0f) + 0.3f);
    }


    public boolean getSettingsOnDoubleTapPreference() {
        return mSharedPrefs.getBoolean(mContext.getString(R.string.pref_double_tap_key), true);
    }

    public boolean isSquareShapedTunnel() {
        return mSharedPrefs.getBoolean(mContext.getString(R.string.pref_is_square_key), false);
    }
}
