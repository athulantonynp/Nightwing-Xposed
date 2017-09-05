package com.athul.nightwing.activities;

import android.Manifest;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.athul.nightwing.R;

import java.io.File;
import java.util.List;

public class Splash extends AppCompatActivity {

    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        btn = (Button) findViewById(R.id.btn_launch);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLauncherName();
            }
        });
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

        NotificationCompat.Builder mBuilder =   new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.drawer) // notification icon
                .setContentTitle("Notification!") // title for notification
                .setContentText("Hello word") // message for notification
                .setAutoCancel(true); // clear notification after click
        Intent intent = new Intent(this, Splash.class);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(0, mBuilder.build());

    }


}
