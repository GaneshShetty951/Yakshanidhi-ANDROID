package com.example.ganeshshetty.yakshanidhi.authorisation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.ganeshshetty.yakshanidhi.MainActivity;

import java.util.HashMap;

/**
 * Created by aakash on 12/12/16.
 */
public class SessionManager extends Activity {
    // User name (make variable public to access from outside)
    public static final String KEY_ID="id";
    public static final String KEY_NAME = "name";
    // Email address (make variable public to access from outside)
    public static final String KEY_EMAIL = "email";
    // Sharedpref file name
    private static final String PREF_NAME = "yakshanidhiPref";
    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";
    private static final String USER_LANG="language";
    // LogCat tag
    private static String TAG = SessionManager.class.getSimpleName();
    private static SessionManager sessionManager = new SessionManager();

    //pusher
    // Shared Preferences
    SharedPreferences pref;
    // Editor for Shared preferences
    SharedPreferences.Editor editor;
    // Context
    Context _context;
    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Constructor
    private SessionManager() {

    }

    public static SessionManager getInstance() {
        return sessionManager;
    }

    public void setContext(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public boolean firstTimeRun() {
        boolean firstRun = pref.getBoolean("firstRun", true);

//        if(firstRun==false)//if running for first time
//        //Splash will load for first time
//        {
//        }

        return firstRun;
    }


    /**
     * Create login session
     */
    public void createLoginSession(String name, String email,String id) {
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        editor.putString(KEY_ID,id);

        // Storing name in pref
        editor.putString(KEY_NAME, name);

        // Storing email in pref
        editor.putString(KEY_EMAIL, email);

        // commit changes
        editor.commit();
    }

    public void setLogin(boolean isLoggedIn) {
        editor.putBoolean(IS_LOGIN, isLoggedIn);
        // commit changes
        editor.commit();
        Log.d(TAG, "User login session modified!");
    }

    public void setFirstTimeRun(boolean checkFirstFlag) {
        editor.putBoolean("firstRun", checkFirstFlag);
        editor.commit();
    }

    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     * */


    /**
     * Get stored session data
     */
    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        // user name
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));
        user.put(KEY_ID,pref.getString(KEY_ID,"1"));
        // user email id
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));

        // return user
        return user;
    }

    /**
     * Clear session details
     */
    public void logoutUser() {
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.putBoolean("firstRun", false);
        editor.commit();

        // After logout redirect user to Login Activity
        Intent i = new Intent(_context, LoginActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
        finish();
    }

    /**
     * Quick check for login
     **/
    // Get Login State
    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }

    public void setUserLang(String lang_code)
    {
        editor.putString(USER_LANG,lang_code);
        editor.commit();
    }
    public String getUserLang()
    {
        return pref.getString(USER_LANG,"kn");
    }
    public boolean isUserLangSet()
    {
        if(pref.getString(USER_LANG,"not").equalsIgnoreCase("not"))
            return false;
        else
            return true;
    }
}
