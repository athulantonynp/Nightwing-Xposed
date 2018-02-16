package com.athul.nightwing.recievers;

import android.annotation.SuppressLint;
import android.app.WallpaperManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.athul.nightwing.R;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by athul on 16/2/18.
 */

public class WallpaperReciever extends BroadcastReceiver {
    @SuppressLint("ResourceType")
    @Override
    public void onReceive(Context context, Intent intent) {

        Log.e("WTKLV","RECIEVED EVENY");
        WallpaperManager myWallpaperManager
                = WallpaperManager.getInstance(context.getApplicationContext());
        try {
            myWallpaperManager.setResource(R.drawable.image2);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
