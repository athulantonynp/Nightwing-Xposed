package com.athul.nightwing.activities;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
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

    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        SubsamplingScaleImageView photoView = (SubsamplingScaleImageView) findViewById(R.id.photo_view);
        //photoView.setImageResource(R.drawable.image);
        photoView.setImage(ImageSource.resource(R.drawable.image2));
    }

    private void getLauncherName() {

        /*File[] storages = ContextCompat.getExternalFilesDirs(this, null);
        Log.e("WTKLV",String.valueOf(storages.length));
        for(File file: storages){

            Log.e("WTKLV",file.getParent().toString());
        }
        if (storages.length > 1 && storages[0] != null && storages[1] != null)
            Log.e("WTKLV","EXTERNAL");
        else
            Log.e("WTKLV","INTERNAL");*/

        Notification n  = new Notification.Builder(this)
                .setContentTitle("New mail from " + "test@gmail.com")
                .setContentText("Subject")
                .setSmallIcon(R.drawable.drawer)
                .setAutoCancel(true)
                .build();



        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        notificationManager.notify(0, n);

    }


}
