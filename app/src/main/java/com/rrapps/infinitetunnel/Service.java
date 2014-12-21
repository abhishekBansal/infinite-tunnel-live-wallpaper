package com.rrapps.infinitetunnel;

import android.content.Context;

import rajawali.wallpaper.Wallpaper;

public class Service extends Wallpaper {
	private InfiniteTunnelRenderer mRenderer;

	public Engine onCreateEngine() {
		mRenderer = new InfiniteTunnelRenderer(this);
		return new WallpaperEngine(this.getSharedPreferences(Settings.SHARED_PREF_NAME,
				                                            Context.MODE_PRIVATE),
                                                            getBaseContext(),
                                                            mRenderer,
                                                            false);
	}
}