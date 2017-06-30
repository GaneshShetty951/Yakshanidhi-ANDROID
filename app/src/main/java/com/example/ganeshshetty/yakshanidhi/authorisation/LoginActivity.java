package com.example.ganeshshetty.yakshanidhi.authorisation;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.LoggingBehavior;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.ganeshshetty.yakshanidhi.MainActivity;
import com.example.ganeshshetty.yakshanidhi.R;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = "LoginActivity";
    private static int RESPONCE_CODE;
    private static final int RC_SIGN_IN = 9001;
    private static int SOCIAL_TYPE=0;
    private final SessionManager session = SessionManager.getInstance();

    EditText editEmail, editPassword;
    String id,email,name, password;
    AppCompatButton loginButton, signupButton;
    ScrollView scrollView;
    ProgressBar progressBar;
    LoginButton fbLoginButton;
    SignInButton googleSignInButton;
    CallbackManager callbackManager;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        session.setContext(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_login);
        editEmail = (EditText) findViewById(R.id.input_email);
        editPassword = (EditText) findViewById(R.id.input_password);
        loginButton = (AppCompatButton) findViewById(R.id.btn_login);
        signupButton = (AppCompatButton) findViewById(R.id.btn_signup);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        fbLoginButton=(LoginButton)findViewById(R.id.fb_login);
        googleSignInButton=(SignInButton)findViewById(R.id.sign_in_button);
        FacebookSdk.addLoggingBehavior(LoggingBehavior.REQUESTS);
        fbLoginButton.setReadPermissions(Arrays.asList("email","user_birthday"));

        // Configure sign-in to request the user's ID, email address, and basic
// profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(LoginActivity.this /* FragmentActivity */,LoginActivity.this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        googleSignInButton.setOnClickListener(LoginActivity.this);

        fbLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                setFacebookData(loginResult);
                Log.i("result","success");
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

        fbLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SOCIAL_TYPE=1;
                LoginManager.getInstance().registerCallback(callbackManager,
                        new FacebookCallback<LoginResult>() {
                            @Override
                            public void onSuccess(LoginResult loginResult) {
                                setFacebookData(loginResult);
                                Log.i("result","success");
                            }

                            @Override
                            public void onCancel() {
                                Log.i("Exception","cancelled");
                            }

                            @Override
                            public void onError(FacebookException exception) {
                                // App code
                                Log.i("Exception",exception.getMessage());
                            }
                        });

            }
        });

        progressBar.setVisibility(View.INVISIBLE);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginToServer();
            }
        });
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signUpIntent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(signUpIntent);
            }
        });

    }

    private void setFacebookData(final LoginResult loginResult) {
        GraphRequest request = GraphRequest.newMeRequest(
                loginResult.getAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        // Application code
                        try {
                            Log.i("Response",response.toString());

                            email = response.getJSONObject().getString("email");
                            name = response.getJSONObject().getString("first_name");
                            name = name+ " " +response.getJSONObject().getString("last_name");
                            String gender = response.getJSONObject().getString("gender");
                            String bday= response.getJSONObject().getString("birthday");

                            Profile profile = Profile.getCurrentProfile();
                            id = profile.getId();
                            String link = profile.getLinkUri().toString();
                            Log.i("Link",link);
                            if (Profile.getCurrentProfile()!=null)
                            {
                                Log.i("Login", "ProfilePic" + Profile.getCurrentProfile().getProfilePictureUri(200, 200));
                            }
                            new SocialLogin().execute();
                            Log.i("Login" + "Email", email);
                            Log.i("Login"+ "FirstName", name);
                            Log.i("Login" + "Gender", gender);
                            Log.i("Login" + "Bday", bday);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,email,first_name,last_name,gender,birthday");
        request.setParameters(parameters);
        request.executeAsync();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(SOCIAL_TYPE==1) {
            super.onActivityResult(requestCode, resultCode, data);
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
        else if(SOCIAL_TYPE==2)
        {
            if (requestCode == RC_SIGN_IN) {
                GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                handleSignInResult(result);
            }
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            Log.d(TAG,"Name : "+acct.getDisplayName()+"Email : "+acct.getEmail()+"User Id : "+acct.getId());
            id=acct.getId();
            name=acct.getDisplayName();
            email=acct.getEmail();
            new SocialLogin().execute();
//            mStatusTextView.setText(getString(R.string.signed_in_fmt, acct.getDisplayName()));
//            updateUI(true);
        } else {
            // Signed out, show unauthenticated UI.
            //updateUI(false);
            Log.d(TAG,"Login Unsuccessful");
        }
    }

    private void socialLogin(String provider, String id, String displayName, String email) {
        OkHttpClient client = new OkHttpClient();
        Response response = null;
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, "email=" + email + "&id="+id+"&name="+displayName+"&provider="+provider);
        Request request = new Request.Builder()
                .url(getString(R.string.url) + "/api/socialLogin")
                .post(body)
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .addHeader("cache-control", "no-cache")
                .build();

        try {
            response = client.newCall(request).execute();

            RESPONCE_CODE = response.code();
            if (RESPONCE_CODE == 200) {
                Snackbar snack = Snackbar.make(scrollView, "Welcome to Yakshanidhi", Snackbar.LENGTH_LONG);
                snack.show();
                try {
                    String s=response.body().string().toString();
                    JSONObject object=new JSONObject(s);
                    JSONObject resource=object.getJSONObject("resource");
                    name=resource.optString("name");
                    String ids=resource.optString("id");
                    email=resource.optString("email");
                    session.createLoginSession(name,email,ids);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                Snackbar snack = Snackbar.make(scrollView, "Credentials do not match", Snackbar.LENGTH_LONG);
                snack.show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    private void loginToServer() {
        if (validateEmail() && validatePassword()) {
            email = editEmail.getText().toString().toLowerCase().trim();
            password = editPassword.getText().toString().trim();
            progressBar.setVisibility(View.VISIBLE);
            new LoginTask().execute();
        }
    }

    private void postData() {
        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, "email=" + email + "&password=" + password);
        Request request = new Request.Builder()
                .url(getString(R.string.url) + "/api/ajaxAuthenticate")
                .post(body)
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .addHeader("cache-control", "no-cache")
                .build();

        try {
            Response response = client.newCall(request).execute();

            RESPONCE_CODE = response.code();
            if (RESPONCE_CODE == 200) {
                try {
                    JSONObject object=new JSONObject(response.body().string().toString());
                    JSONObject resource=object.getJSONObject("resource");
                    name=resource.getString("name");
                    id=resource.getString("id");
                    email=resource.getString("email");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Snackbar snack = Snackbar.make(scrollView, "Welcome to Yakshanidhi", Snackbar.LENGTH_LONG);
                snack.show();
            } else {
                Snackbar snack = Snackbar.make(scrollView, "Credentials do not match", Snackbar.LENGTH_LONG);
                snack.show();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean validatePassword() {
        if (editPassword.getText().length() == 0) {
            editPassword.setError("Enter password valid password");
            return false;
        } else if (editPassword.getText().length() < 6) {
            editPassword.setError("Password must be minimum 6 letters of length");
            return false;
        }
        return true;
    }

    private boolean validateEmail() {
        if (editEmail.getText().toString().trim().length() == 0) {
            editEmail.setError("Enter email id");
            return false;
        } else if (!editEmail.getText().toString().trim().contains("@")) {
            editEmail.setError("Enter valid email address");
            return false;
        }else if (editEmail.getText().toString().trim().contains(" ")) {
            editEmail.setError("Enter valid email address,contains space in between");
            return false;
        } else if (editEmail.getText().toString().trim().endsWith("@")) {
            editEmail.setError("Enter valid email address, should not end with @");
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        SOCIAL_TYPE=2;
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private class LoginTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            postData();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progressBar.setVisibility(View.GONE);
            if (RESPONCE_CODE == 200) {
                session.createLoginSession(name,email,id);
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }

    private class SocialLogin extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... params) {
            if(SOCIAL_TYPE==1)
            {
                socialLogin("facebook",id,name,email);
            }
            else if(SOCIAL_TYPE==2)
            {
                socialLogin("google",id,name,email);
            }
            return null;
        }
    }
}
