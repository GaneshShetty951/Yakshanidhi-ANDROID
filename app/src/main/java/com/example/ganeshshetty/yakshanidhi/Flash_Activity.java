package com.example.ganeshshetty.yakshanidhi;
/**
 * Author : Ganesh Shetty
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Flash_Activity extends Activity {
    private int SPLASH_TIME_OUT=3000;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_);
        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent mainIntent=new Intent(Flash_Activity.this,MainActivity.class);
                startActivity(mainIntent);

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);


    }
}
