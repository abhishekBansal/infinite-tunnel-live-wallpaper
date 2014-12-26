package com.rrapps.infinitetunnel;

import android.view.MotionEvent;
import android.view.SurfaceHolder;

import net.rbgrn.android.glwallpaperservice.GLWallpaperService;

public class MyWallpaperService extends GLWallpaperService {
	private InfiniteTunnelRenderer mRenderer;

	public Engine onCreateEngine() {
		return new WallpaperEngine();
	}

    private class WallpaperEngine extends GLEngine {

        WallpaperEngine() {
            super();
            mRenderer = new InfiniteTunnelRenderer();
            setRenderer(mRenderer);
            setRenderMode(RENDERMODE_CONTINUOUSLY);
        }

        @Override
        public void onVisibilityChanged(boolean visible) {
            super.onVisibilityChanged(visible);
        }

        @Override
        public void onSurfaceDestroyed(SurfaceHolder holder) {
            super.onSurfaceDestroyed(holder);
        }

        @Override
        public void onSurfaceChanged(SurfaceHolder holder, int format,
                                     int width, int height) {
            super.onSurfaceChanged(holder, format, width, height);
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            if (mRenderer != null) {
                mRenderer.release();
            }
            mRenderer = null;
        }

        @Override
        public void onTouchEvent(MotionEvent event) {
            super.onTouchEvent(event);
        }
    }
}