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

    private static String IS_HIGHP_SUPPORTED_KEY = "IsHighPrecisionSupportedPrefKey";
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
            case 9:
                return R.drawable.blue_stars1;
            case 10:
                return R.drawable.blue_stars2;
            case 11:
                return R.drawable.camouflage3;
            case 12:
                return R.drawable.camouflage4;
            case 13:
                return R.drawable.lava;
            case 14:
                return R.drawable.snow;
            case 15:
                return R.drawable.grunge_stars1;
            case 16:
                return R.drawable.grunge_stars6;
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

    public boolean isCenterBright() {
        return mSharedPrefs.getBoolean(mContext.getString(R.string.pref_is_center_bright_key), false);
    }

    public void setCenterBright(boolean isCenterBright) {
        mSharedPrefs.edit()
                        .putBoolean(mContext.getString(R.string.pref_is_center_bright_key), isCenterBright)
                        .apply();
    }

    /**
     * returns stored information about high precision support in fragment shader
     */
    public boolean isHighPrecisionSupported() {
        return mSharedPrefs.getBoolean(IS_HIGHP_SUPPORTED_KEY, false);
    }

    public void setIsHighPrecisionSupported(boolean isHighPrecisionSupported) {
        mSharedPrefs.edit()
                .putBoolean(IS_HIGHP_SUPPORTED_KEY, isHighPrecisionSupported)
                .apply();
    }

    public boolean isWarpMode() {
        return mSharedPrefs.getBoolean(mContext.getString(R.string.pref_is_warp_mode_key), false);
    }
}
