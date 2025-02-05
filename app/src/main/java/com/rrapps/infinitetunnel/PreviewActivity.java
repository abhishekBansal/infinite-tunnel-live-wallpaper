package com.rrapps.infinitetunnel;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;


public class PreviewActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent();

        if (Build.VERSION.SDK_INT >= 16) {
            /*
             * Open live wallpaper preview (API Level 16 or greater).
             */
            intent.setAction(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER);
            String pkg = MyWallpaperService.class.getPackage().getName();
            String cls = MyWallpaperService.class.getCanonicalName();
            intent.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT,
                    new ComponentName(pkg, cls));
        } else {
            /*
             * Open live wallpaper picker (API Level 15 or lower).
             *
             * Display a quick little message (toast) with instructions.
             */
            intent.setAction(WallpaperManager.ACTION_LIVE_WALLPAPER_CHOOSER);
            Resources res = getResources();
            String hint = res.getString(R.string.picker_toast_prefix)
                    + res.getString(R.string.lwp_name)
                    + res.getString(R.string.picker_toast_suffix);
            Toast toast = Toast.makeText(this, hint, Toast.LENGTH_LONG);
            toast.show();
        }

        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        finish();
    }
}
