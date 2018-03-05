package com.athul.nightwing.activities;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.athul.nightwing.R;
import com.athul.nightwing.module.Constants;
import com.athul.nightwing.recievers.WallpaperReciever;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.github.chrisbanes.photoview.PhotoView;

import java.io.File;
import java.util.List;
import java.util.logging.Logger;

import de.robv.android.xposed.XSharedPreferences;

public class Splash extends AppCompatActivity {

    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        btn=(Button)findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLauncherName();
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Splash.this.sendBroadcast(new Intent(Splash.this, WallpaperReciever.class));
            }
        });
    }

    private void getLauncherName() {

        SharedPreferences pref = getSharedPreferences("xposed", MODE_WORLD_READABLE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("sample", "TEST IT IS AVAILABLE");
        editor.apply();


    }

    private void printshared() {

    }

    private  void clear(){
        printshared();
    }


}
