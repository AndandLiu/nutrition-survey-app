package com.jla388.sfu.greenfoodchallenge;

import android.graphics.Bitmap;
import android.view.View;

public class Screenshot {
    public static Bitmap TakeScreenShot(View v){
        v.setDrawingCacheEnabled(true);
        v.buildDrawingCache(true);
        Bitmap b = Bitmap.createBitmap(v.getDrawingCache());
        v.setDrawingCacheEnabled(false);

        return b;
    }
    public static Bitmap TakeScreenShotOfRootView(View v){
        return TakeScreenShot(v.getRootView());
    }
}
