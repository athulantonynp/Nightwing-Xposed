package com.athul.nightwing.activities;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
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
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.athul.nightwing.R;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.github.chrisbanes.photoview.PhotoView;

import java.io.File;
import java.util.List;

public class Splash extends AppCompatActivity {

    Button btn,btn2;

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
        btn2=(Button)findViewById(R.id.btn2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clear();
            }
        });
    }

    private void getLauncherName() {
        /*SharedPreferences sharedpreferences = getSharedPreferences("my", Context.MODE_WORLD_READABLE);

        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putBoolean("unlock",true);
        editor.apply();
        editor.commit();
        Log.e("WTKLV","UNLOCK"); */
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.drawer)
                        .setContentTitle("My notification")
                        .setContentText("Hello World!");

        NotificationManager mNotifyMgr =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mNotifyMgr.notify(100, mBuilder.build());


    }
    private  void clear(){
        SharedPreferences sharedpreferences = getSharedPreferences("my", Context.MODE_WORLD_READABLE);

        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putBoolean("unlock",false);
        editor.apply();
        editor.commit();
        Log.e("WTKLV","LOCK");
    }


}
