package com.rrapps.infinitetunnel;

import android.preference.PreferenceManager;

import rajawali.wallpaper.Wallpaper;

public class Service extends Wallpaper {
	private InfiniteTunnelRenderer mRenderer;

	public Engine onCreateEngine() {
		mRenderer = new InfiniteTunnelRenderer(this);
		return new WallpaperEngine(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()),
                                    getApplicationContext(),
                                    mRenderer,
                                    false);
	}


}