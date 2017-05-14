package com.example.ganeshshetty.yakshanidhi.details;

import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ganeshshetty.yakshanidhi.R;
import com.example.ganeshshetty.yakshanidhi.adapters.CommentAdapter;
import com.example.ganeshshetty.yakshanidhi.fragments.MelaFragment;
import com.example.ganeshshetty.yakshanidhi.model.Comment;
import com.example.ganeshshetty.yakshanidhi.model.Show_class;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
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
                isFirstTime=false;
            }
            else
            {

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


}
