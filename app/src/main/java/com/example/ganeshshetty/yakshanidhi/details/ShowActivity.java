package com.example.ganeshshetty.yakshanidhi.details;

import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ganeshshetty.yakshanidhi.R;
import com.example.ganeshshetty.yakshanidhi.adapters.CommentAdapter;
import com.example.ganeshshetty.yakshanidhi.authorisation.SessionManager;
import com.example.ganeshshetty.yakshanidhi.fragments.MelaFragment;
import com.example.ganeshshetty.yakshanidhi.model.Comment;
import com.example.ganeshshetty.yakshanidhi.model.Show_class;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ShowActivity extends AppCompatActivity {
    private ImageView melaImage;
    private TextView melaName,prodName,timeText,dateText,addressText,contact1,contact2;
    private Toolbar toolbar;
    private Show_class show;
    private RecyclerView commentRecycle;
    private CommentAdapter commentAdapter;
    private LinearLayoutManager commentLayout;
    private String next_Url;
    private String TAG="ShowActivity";
    private ArrayList<Comment> comments;
    private boolean isFirstTime=true;
    private Button saveComment;
    private EditText editComment;
    private int RESPONCE_CODE;
    private String comment_text;
    private int show_id,user_id;
    private final SessionManager session = SessionManager.getInstance();
    private NestedScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        session.setContext(getApplicationContext());
        show=(Show_class)getIntent().getSerializableExtra("show");
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        melaImage=(ImageView)findViewById(R.id.bgheader);
        melaName=(TextView) findViewById(R.id.melaName);
        prodName=(TextView)findViewById(R.id.prodName);
        timeText=(TextView)findViewById(R.id.time);
        dateText=(TextView)findViewById(R.id.date);
        contact1=(TextView)findViewById(R.id.contact_1);
        contact2=(TextView)findViewById(R.id.contact_2);
        addressText=(TextView)findViewById(R.id.address);
        commentRecycle=(RecyclerView)findViewById(R.id.commentRecycle);
        saveComment=(Button)findViewById(R.id.saveComment);
        saveComment.setEnabled(false);
        editComment=(EditText)findViewById(R.id.editComment);
        scrollView=(NestedScrollView)findViewById(R.id.scrollView);
        comments=new ArrayList<>();
        commentLayout=new LinearLayoutManager(this);
        String url=getString(R.string.url)+"/api/getComments/"+ Uri.encode(show.getPrasangha_name())+"/"+show.getShow_id();
        new CommentLoadTask().execute(url);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(show.getPrasangha_name());
        Picasso.with(this).load(getString(R.string.url)+"/mela_images/"+show.getMela_pic()).placeholder(R.drawable.yakshanidhi).into(melaImage);
        melaName.setText(show.getMela_name());
        prodName.setText(show.getShow_producer_name());
        timeText.setText(show.getShow_time());
        dateText.setText(show.getShow_date());
        contact1.setText(show.getContact1());
        contact2.setText(show.getContact2());
        addressText.setText(show.getVillage()+"\n"
        +show.getTaluk()+"\n"
        +show.getDistrict()+"\nPINCODE : "
        +show.getPincode());
        editComment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()>0)
                    saveComment.setEnabled(true);
                else
                    saveComment.setEnabled(false);
            }
        });
        saveComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editComment.getText().length()!=0)
                {
                    HashMap<String,String> user=session.getUserDetails();
                    user_id=Integer.parseInt(user.get(SessionManager.KEY_ID));
                    show_id=show.getShow_id();
                    comment_text=editComment.getText().toString();
                    new CommentSaveTask().execute(getString(R.string.url)+"/api/saveComments");
                    editComment.setText("");
                    saveComment.setEnabled(false);
                }
                else
                {

                }
            }
        });
        commentRecycle.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int last=commentLayout.findLastCompletelyVisibleItemPosition();
                if (next_Url != null ) {
                    if (last >= comments.size()-1) {
                        new CommentLoadTask().execute(next_Url);
                        isFirstTime=false;
                        return;
                    }
                }
            }
        });
    }

    public void Commentrefresh()
    {
        new CommentLoadTask().execute(getString(R.string.url)+"/api/getComments/"+ Uri.encode(show.getPrasangha_name())+"/"+show.getShow_id());
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:finish();
        }
        return super.onOptionsItemSelected(item);
    }
    public class CommentLoadTask extends AsyncTask<String,Void,Void>
    {

        @Override
        protected Void doInBackground(String... params) {
            Integer result = 0;
            if(android.os.Debug.isDebuggerConnected())
                android.os.Debug.waitForDebugger();
            HttpURLConnection urlConnection;
            try {
                URL url = new URL(params[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                int statusCode = urlConnection.getResponseCode();

                // 200 represents HTTP OK
                if (statusCode == 200) {
                    BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = r.readLine()) != null) {
                        response.append(line);
                    }
                    if(isFirstTime)
                    {
                        comments.clear();
                    }
                    parseResult(response.toString());
                    result = 1; // Successful
                } else {
                    result = 0; //"Failed to fetch data!";
                }
            } catch (Exception e) {
                Log.d(TAG, e.getLocalizedMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (isFirstTime)
            {
                commentAdapter=new CommentAdapter(comments,ShowActivity.this);
                commentRecycle.setLayoutManager(commentLayout);
                commentRecycle.setAdapter(commentAdapter);
                commentRecycle.setNestedScrollingEnabled(false);
                isFirstTime=false;
            }
            else
            {
                commentAdapter.add(comments);
            }
        }
    }

    private void parseResult(String s) {
        try {

            JSONObject response = new JSONObject(s);
            JSONObject posts = response.getJSONObject("posts");
                next_Url = posts.getString("next_page_url");
                JSONArray data = posts.getJSONArray("data");
                for (int i = 0; i < data.length(); i++) {
                    JSONObject commentObject = data.getJSONObject(i);
                    Comment comment = new Comment();
                    comment.setUser_id(commentObject.getInt("user_id"));
                    comment.setShow_id(commentObject.getInt("show_id"));
                    comment.setCommentText(commentObject.getString("comment_text"));
                    comment.setCommentedAt(commentObject.getString("created_at"));
                    comment.setName(commentObject.getString("name"));
                    comments.add(comment);
                }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public class CommentSaveTask extends AsyncTask<String,Void,Void>
    {

        @Override
        protected Void doInBackground(String... params) {
            OkHttpClient client = new OkHttpClient();

            MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
            RequestBody body = RequestBody.create(mediaType, "show_id="+show_id+"&user_id=" + user_id + "&comment=" +comment_text );
            Request request = new Request.Builder()
                    .url(params[0])
                    .post(body)
                    .addHeader("content-type", "application/x-www-form-urlencoded")
                    .addHeader("cache-control", "no-cache")
                    .build();
            try {
                Response response = client.newCall(request).execute();
                RESPONCE_CODE = response.code();
                if (RESPONCE_CODE == 200) {
                    Log.i("comment saved",response.body().toString());
                    isFirstTime=true;
                    Commentrefresh();

                } else {
                    Log.i("comment unsaved",response.code()+"");
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
