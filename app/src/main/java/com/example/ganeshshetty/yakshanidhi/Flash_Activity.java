package com.example.ganeshshetty.yakshanidhi;
/**
 * Author : Ganesh Shetty
 */

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ganeshshetty.yakshanidhi.authorisation.LoginActivity;
import com.example.ganeshshetty.yakshanidhi.authorisation.SessionManager;

import java.util.Locale;

public class Flash_Activity extends Activity {
    private int SPLASH_TIME_OUT = 3000;
    private SessionManager session=SessionManager.getInstance();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_);
        session.setContext(getApplicationContext());
        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                if(!session.isUserLangSet()) {
                    setLanguage("en");
//                    final Dialog dialog = new Dialog(Flash_Activity.this);
//                    // Include dialog.xml file
//                    dialog.setContentView(R.layout.languagedialog);
//                    // Set dialog title
//                    dialog.setTitle("User Language");
//                    // set values for custom dialog components - text, image and button
//                    TextView text = (TextView) dialog.findViewById(R.id.title);
//                    final RadioButton radiokannada = (RadioButton) dialog.findViewById(R.id.kannada);
//                    final RadioButton radioenglish = (RadioButton) dialog.findViewById(R.id.english);
//                    dialog.show();
//                    Button done = (Button) dialog.findViewById(R.id.done_button);
//                    // if done button is clicked, close the custom dialog
//                    done.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            if (radiokannada.isChecked() == true) {
//                                session.setUserLang("kn");
//                                setLanguage("kn");
//                            } else if (radioenglish.isChecked() == true) {
//                                session.setUserLang("en");
//                                setLanguage("en");
//                            } else {
//                                Toast.makeText(Flash_Activity.this, "Select any one of the language", Toast.LENGTH_LONG).show();
//                            }
//                        }
//                    });
                }else{
                    setLanguage(session.getUserLang());
                }
            }
        }, SPLASH_TIME_OUT);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public void setLanguage(String language_code)
    {
        Locale locale = new Locale(language_code);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        if(session.isLoggedIn()) {
            Intent mainIntent = new Intent(Flash_Activity.this, MainActivity.class);
            startActivity(mainIntent);
            finish();
        }else{
            Intent mainIntent = new Intent(Flash_Activity.this, LoginActivity.class);
            startActivity(mainIntent);
            finish();
        }
    }
}
