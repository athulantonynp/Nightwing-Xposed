package com.athul.nightwing.recievers;

import android.app.WallpaperManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.Toast;

import com.athul.nightwing.R;

import java.io.IOException;

/**
 * Created by athul on 5/3/18.
 */

public class BootReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        WallpaperManager myWallpaperManager
                = WallpaperManager.getInstance(context.getApplicationContext());

        Bitmap loadedImage= BitmapFactory.decodeResource(context.getResources(), R.drawable.entri_976);

        try {
            myWallpaperManager.setWallpaperOffsetSteps(1,1);
            myWallpaperManager.suggestDesiredDimensions(600, 960);
            myWallpaperManager.setBitmap(loadedImage);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
