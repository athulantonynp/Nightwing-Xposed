package com.athul.nightwing.recievers;

import android.annotation.SuppressLint;
import android.app.WallpaperManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.DisplayMetrics;
import android.util.Log;

import com.athul.nightwing.R;

import java.io.IOException;

/**
 * Created by athul on 16/2/18.
 */

public class WallpaperReciever extends BroadcastReceiver {
    @SuppressLint("ResourceType")
    @Override
    public void onReceive(Context context, Intent intent) {

        WallpaperManager myWallpaperManager
                = WallpaperManager.getInstance(context.getApplicationContext());

        Bitmap loadedImage= BitmapFactory.decodeResource(context.getResources(),R.drawable.entri_976);

        try {
            myWallpaperManager.setWallpaperOffsetSteps(1,1);
            myWallpaperManager.suggestDesiredDimensions(600, 976);
            myWallpaperManager.setBitmap(loadedImage);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public class BitmapHelper {

        public  Bitmap overlayIntoCentre(Bitmap bmp1, Bitmap bmp2) {
            Bitmap bmOverlay = Bitmap.createBitmap(bmp1.getWidth(), bmp1.getHeight(), bmp1.getConfig());
            Canvas canvas = new Canvas(bmOverlay);
            canvas.drawBitmap(bmp1, new Matrix(), null);//draw background bitmap

            //overlay the second in the centre of the first
            //(may experience issues if the first bitmap is smaller than the second, but then why would you want to overlay a bigger one over a smaller one?!)
            //EDIT: added Y offest fix - thanks @Jason Goff!
            canvas.drawBitmap(bmp2, (bmp1.getWidth()/180)-(bmp2.getWidth()/180), (bmp1.getHeight()/180)-(bmp2.getHeight()/180), null);

            return bmOverlay;
        }

        public  Bitmap createNewBitmap(int width, int height)
        {
            //create a blanks bitmap of the desired width/height
            return Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        }
    }
}
