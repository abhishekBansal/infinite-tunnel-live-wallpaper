package com.rrapps.infinitetunnel;

import android.content.Intent;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import com.rrapps.infinitetunnel.model.Settings;

import net.rbgrn.android.glwallpaperservice.GLWallpaperService;

public class MyWallpaperService extends GLWallpaperService {
	private InfiniteTunnelRenderer mRenderer;
    private GestureDetector mGestureDetecter;

	public Engine onCreateEngine() {
		return new WallpaperEngine();
	}

    private class WallpaperEngine extends GLEngine
                                  implements GestureDetector.OnDoubleTapListener,
                                             GestureDetector.OnGestureListener {

        WallpaperEngine() {
            super();
            setEGLContextClientVersion(2);
            mRenderer = new InfiniteTunnelRenderer(getApplicationContext());
            setRenderer(mRenderer);
            setRenderMode(RENDERMODE_CONTINUOUSLY);
        }

        @Override
        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);

            // Instantiate the gesture detector with the
            // application context and an implementation of
            // GestureDetector.OnGestureListener
            mGestureDetecter = new GestureDetector(MyWallpaperService.this, this);
            // Set the gesture detector as the double tap
            mGestureDetecter.setOnDoubleTapListener(this);
        }

        @Override
        public void onSurfaceCreated(SurfaceHolder holder) {
            super.onSurfaceCreated(holder);
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
            mGestureDetecter.onTouchEvent(event);
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            Log.v(InfiniteTunnelApplication.LogTag, "onSingleTap");
            return true;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            Log.d(InfiniteTunnelApplication.LogTag, "onDoubleTap");
            if(Settings.getInstance(MyWallpaperService.this).getSettingsOnDoubleTapPreference()) {
                Intent intent = new Intent(MyWallpaperService.this, SettingsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
            return true;
        }

        @Override
        public boolean onDoubleTapEvent(MotionEvent e) {
            Log.v(InfiniteTunnelApplication.LogTag, "onDoubleTapEvent");
            return true;
        }

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public void onShowPress(MotionEvent e) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            Log.v(InfiniteTunnelApplication.LogTag, "onSingleTapUp");
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {

        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return false;
        }
    }
}