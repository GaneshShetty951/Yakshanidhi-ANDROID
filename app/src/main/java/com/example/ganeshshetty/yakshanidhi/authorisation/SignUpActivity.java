package com.example.ganeshshetty.yakshanidhi.authorisation;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ganeshshetty.yakshanidhi.MainActivity;
import com.example.ganeshshetty.yakshanidhi.R;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SignUpActivity extends AppCompatActivity {
    private final SessionManager session = SessionManager.getInstance();
    EditText editName,editEmail,editPassword,editConfirmPassword;
    Button btnSignUp;
    String name,email,password,confirmpassword;
    RelativeLayout relativeLayout;
    ProgressBar progressBar;
    private int RESPONCE_CODE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        session.setContext(getApplicationContext());
        editName=(EditText)findViewById(R.id.editname);
        editEmail=(EditText)findViewById(R.id.editemail);
        editPassword=(EditText)findViewById(R.id.editpassword);
        editConfirmPassword=(EditText)findViewById(R.id.editconfirmpassword);
        btnSignUp=(Button)findViewById(R.id.btn_signup);
        relativeLayout=(RelativeLayout)findViewById(R.id.signup);
        progressBar=(ProgressBar)findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.INVISIBLE);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name=editName.getText().toString().trim();
                email=editEmail.getText().toString().trim().toLowerCase();
                password=editPassword.getText().toString().trim();
                confirmpassword=editConfirmPassword.getText().toString().trim();
                if(validateName()&&validateEmail()&&validatePassword())
                {
                    progressBar.setVisibility(View.VISIBLE);
                    postData();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private void postData() {
        new SignUpTask().execute();
    }

    private boolean validatePassword() {
        if(password.compareTo(confirmpassword)!=0)
        {
            editConfirmPassword.setError("Password does not matches");
            return false;
        }else if(password.isEmpty())
        {
            editPassword.setError("Enter password");
            return false;
        }
        else if(password.length()<6)
        {
            editPassword.setError("Password minimum of Six Character length");
            return false;
        }
        return true;
    }

    private boolean validateEmail() {
        if (email.isEmpty()) {
            editEmail.setError("Enter email id");
            return false;
        } else if (!email.contains("@")) {
            editEmail.setError("Enter valid email address");
            return false;
        }else if (email.contains(" ") || email.contains("$")) {
            editEmail.setError("Enter valid email address");
            return false;
        } else if (email.endsWith("@")) {
            editEmail.setError("Enter valid email address, should not end with @");
            return false;
        }
        return true;
    }

    private boolean validateName() {
        if(name.isEmpty())
        {
            editName.setError("Enter Name");
            return false;
        }
        return true;
    }

    private class SignUpTask extends AsyncTask<Void,Void,Void> {
        @Override
        protected Void doInBackground(Void... params) {
            OkHttpClient client = new OkHttpClient();

            MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
            RequestBody body = RequestBody.create(mediaType, "name="+name+"&email=" + email + "&password=" + password +"&password_confirmation="+confirmpassword);
            Request request = new Request.Builder()
                    .url(getString(R.string.url) + "/api/ajaxRegister")
                    .post(body)
                    .addHeader("content-type", "application/x-www-form-urlencoded")
                    .addHeader("cache-control", "no-cache")
                    .build();
            try {
                Response response = client.newCall(request).execute();
                RESPONCE_CODE = response.code();
                if (RESPONCE_CODE == 200) {
                    Snackbar snack = Snackbar.make(relativeLayout, "Welcome to Yakshanidhi", Snackbar.LENGTH_LONG);
                    snack.show();
                } else {
                    Snackbar snack = Snackbar.make(relativeLayout, "Credentials do not match", Snackbar.LENGTH_LONG);
                    snack.show();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progressBar.setVisibility(View.GONE);
            if (RESPONCE_CODE==200)
            {
                session.createLoginSession(name,email);
                Intent intent=new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }
    }
}
