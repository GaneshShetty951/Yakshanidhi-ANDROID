package com.example.ganeshshetty.yakshanidhi.authorisation;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import com.example.ganeshshetty.yakshanidhi.MainActivity;
import com.example.ganeshshetty.yakshanidhi.R;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private static int RESPONCE_CODE;

    EditText editEmail, editPassword;
    String email, password;
    AppCompatButton loginButton, signupButton;
    ScrollView scrollView;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editEmail = (EditText) findViewById(R.id.input_email);
        editPassword = (EditText) findViewById(R.id.input_password);
        loginButton = (AppCompatButton) findViewById(R.id.btn_login);
        signupButton = (AppCompatButton) findViewById(R.id.btn_signup);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
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

    private void loginToServer() {
        if (validateEmail() && validatePassword()) {
            email = editEmail.getText().toString().trim();
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
        if (editEmail.getText().length() == 0) {
            editEmail.setError("Enter email id");
            return false;
        } else if (!editEmail.getText().toString().contains("@")) {
            editEmail.setError("Enter valid email address");
            return false;
        }
        return true;
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
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        }
    }
}
