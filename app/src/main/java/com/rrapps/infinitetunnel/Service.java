package com.rrapps.infinitetunnel;

import rajawali.wallpaper.Wallpaper;
import android.content.Context;

public class Service extends Wallpaper {
	private InfiniteTunnelRenderer mRenderer;

	public Engine onCreateEngine() {
		mRenderer = new InfiniteTunnelRenderer(this);
		return new WallpaperEngine(this.getSharedPreferences(SHARED_PREFS_NAME,
				Context.MODE_PRIVATE), getBaseContext(), mRenderer, false);
	}
}