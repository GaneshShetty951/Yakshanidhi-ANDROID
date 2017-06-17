package com.example.ganeshshetty.yakshanidhi;

import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ganeshshetty.yakshanidhi.adapters.MelaRecyclerViewAdapter;
import com.example.ganeshshetty.yakshanidhi.model.Artist_class;
import com.example.ganeshshetty.yakshanidhi.model.Mela_class;
import com.example.ganeshshetty.yakshanidhi.model.Prasangha_class;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class SearchOptionActivity extends AppCompatActivity {
    LinearLayout showButton,artistButton,melaButton,prasanghaButton;
    ProgressBar progressBar;
    String div;
    private ArrayList<Mela_class> feedsList=new ArrayList<>();
    private ArrayList<Artist_class> artist_classList=new ArrayList<>();
    private ArrayList<Prasangha_class> prasangha_classList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_option);
        initializeUI();
        initializeListeners();
    }

    private void initializeUI() {
        progressBar=(ProgressBar)findViewById(R.id.progressbar);
        showButton=(LinearLayout)findViewById(R.id.show_button);
        artistButton=(LinearLayout)findViewById(R.id.artistbutton);
        melaButton=(LinearLayout)findViewById(R.id.mela_button);
        prasanghaButton=(LinearLayout)findViewById(R.id.prasangha_button);
    }

    private void initializeListeners() {
        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog=new Dialog(SearchOptionActivity.this);
                dialog.setContentView(R.layout.searchshow);
                ImageView imageView=(ImageView)dialog.findViewById(R.id.image);
                DatePicker editDate=(DatePicker) dialog.findViewById(R.id.editDate);
                final Button button=(Button)dialog.findViewById(R.id.submit);
                button.setEnabled(false);
                editDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        button.setEnabled(true);
                    }
                });
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        artistButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog=new Dialog(SearchOptionActivity.this);
                dialog.setContentView(R.layout.search_general);
                ImageView imageView=(ImageView)dialog.findViewById(R.id.image);
                final EditText editDate=(EditText) dialog.findViewById(R.id.editName);
                final Button button=(Button)dialog.findViewById(R.id.submit);
                button.setEnabled(false);
                editDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        button.setEnabled(true);
                    }
                });
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        Toast.makeText(SearchOptionActivity.this," came here",Toast.LENGTH_LONG).show();
                        new DownloadTask().execute(getString(R.string.url)+"/api/artist/search/"+editDate.getText().toString(),"artist");
                    }
                });
                dialog.show();
            }
        });
        melaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog=new Dialog(SearchOptionActivity.this);
                dialog.setContentView(R.layout.search_general);
                ImageView imageView=(ImageView)dialog.findViewById(R.id.image);
                final EditText editName=(EditText) dialog.findViewById(R.id.editName);
                final Button button=(Button)dialog.findViewById(R.id.submit);
                editName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        button.setEnabled(true);
                        return false;
                    }
                });
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        Toast.makeText(SearchOptionActivity.this," came here",Toast.LENGTH_LONG).show();
                        new DownloadTask().execute(getString(R.string.url)+"/api/mela/search/"+editName.getText().toString(),"mela");
                    }
                });
                dialog.show();
            }
        });
        prasanghaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog=new Dialog(SearchOptionActivity.this);
                dialog.setContentView(R.layout.search_general);
                ImageView imageView=(ImageView)dialog.findViewById(R.id.image);
                final EditText editName=(EditText) dialog.findViewById(R.id.editName);
                final Button button=(Button)dialog.findViewById(R.id.submit);
                button.setEnabled(false);
                editName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        button.setEnabled(true);
                    }
                });
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        new DownloadTask().execute(getString(R.string.url)+"/api/prasangha/search/"+editName.getText().toString(),"prasangha");
                    }
                });
                dialog.show();
            }
        });
    }

    public class DownloadTask extends AsyncTask<String, Void, Integer> {

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Integer doInBackground(String... params) {
            Integer result = 0;
            div=params[1];
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
                    if(div.equalsIgnoreCase("mela")) {
                        parseMelaResult(response.toString());
                    }
                    else if(div.equalsIgnoreCase("artist")) {
                        parseArtistResult(response.toString());
                    }else if(div.equalsIgnoreCase("prasangha"))
                    {
                        parsePrasanghaResult(response.toString());
                    }
                    result = 1; // Successful
                } else {
                    result = 0; //"Failed to fetch data!";
                }
            } catch (Exception e) {
                Log.d(getClass().getSimpleName(), e.getLocalizedMessage());
            }
            return result; //"Failed to fetch data!";
        }

        @Override
        protected void onPostExecute(Integer result) {
            progressBar.setVisibility(View.GONE);

            if (result == 1) {
                if(div.equalsIgnoreCase("mela")) {
                    Intent intent = new Intent(SearchOptionActivity.this, MainActivity.class);
                    intent.putExtra("name", "mela");
                    intent.putExtra("data", feedsList);
                    startActivity(intent);
                }
                else if(div.equalsIgnoreCase("artist"))
                {
                    Intent intent = new Intent(SearchOptionActivity.this, MainActivity.class);
                    intent.putExtra("name", "artist");
                    intent.putExtra("data", artist_classList);
                    startActivity(intent);
                }else if(div.equalsIgnoreCase("prasangha"))
                {
                    Intent intent = new Intent(SearchOptionActivity.this, MainActivity.class);
                    intent.putExtra("name", "prasangha");
                    intent.putExtra("data", prasangha_classList);
                    startActivity(intent);
                }
            }
            else
            {
                Toast.makeText(SearchOptionActivity.this, "Failed to fetch data!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void parseShowresult(String s) {
    }

    private void parsePrasanghaResult(String s) {
        try {
            JSONObject response = new JSONObject(s);
            JSONObject jsononject=response.optJSONObject("posts");
            JSONArray posts = jsononject.optJSONArray("data");
            prasangha_classList = new ArrayList<Prasangha_class>();

            for (int i = 0; i < posts.length(); i++) {
                JSONObject post = posts.optJSONObject(i);
                Prasangha_class item = new Prasangha_class();
                item.setName(post.optString("prasangha_name"));
                item.setAuthor(post.optString("prasangha_author"));
                item.setYear(post.optInt("prasangha_year"));
                prasangha_classList.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void parseArtistResult(String s) {
        try {
            JSONObject response = new JSONObject(s);
            JSONObject jsonobject=response.optJSONObject("posts");
            JSONArray posts = jsonobject.optJSONArray("data");
            artist_classList = new ArrayList<Artist_class>();

            for (int i = 0; i < posts.length(); i++) {
                JSONObject post = posts.optJSONObject(i);
                Artist_class item = new Artist_class();
                item.setFirst_name(post.optString("artist_first_name"));
                item.setSecond_name(post.optString("artist_second_name"));
                item.setPic(post.optString("artist_pic"));
                item.setType(post.optString("artist_type"));
                item.setPlace(post.optString("artist_place"));
                item.setMelaName(post.optString("mela_name"));
                item.setMelaPic(post.optString("mela_pic"));
                artist_classList.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void parseMelaResult(String s) {
        Toast.makeText(SearchOptionActivity.this, "Mela search data", Toast.LENGTH_SHORT).show();
        try {
            JSONObject response = new JSONObject(s);
            JSONObject object = response.optJSONObject("posts");
            JSONArray posts=object.optJSONArray("data");
            feedsList = new ArrayList<>();

            for (int i = 0; i < posts.length(); i++) {
                JSONObject post = posts.optJSONObject(i);
                Mela_class item = new Mela_class();
                item.setName(post.optString("mela_name"));
                item.setThumbnail(post.optString("mela_pic"));
                item.setEmail(post.optString("mela_email"));
                item.setContact(post.optString("contact"));
                item.setVillage(post.optString("village"));
                item.setTaluk(post.optString("taluk"));
                item.setDistrict(post.optString("district"));
                item.setPinode(post.optString("PINCODE"));
                feedsList.add(item);
            }
        }catch (Exception e)
        {

        }
    }
}
