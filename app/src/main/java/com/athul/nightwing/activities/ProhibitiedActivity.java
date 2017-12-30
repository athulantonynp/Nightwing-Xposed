package com.athul.nightwing.activities;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.athul.nightwing.R;

public class ProhibitiedActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prohibitied);
        try {
            getActionBar().hide();
        }catch (Exception e){
            Log.e("WTKLV",e.getMessage());
        }

    }

    public void finish(View view){
        finish();
    }
}
