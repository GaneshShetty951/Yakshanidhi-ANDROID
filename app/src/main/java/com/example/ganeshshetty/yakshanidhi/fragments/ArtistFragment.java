package com.example.ganeshshetty.yakshanidhi.fragments;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.ganeshshetty.yakshanidhi.R;
import com.example.ganeshshetty.yakshanidhi.adapters.ArtistAdapter;
import com.example.ganeshshetty.yakshanidhi.model.Artist_class;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArtistFragment extends Fragment {
    private List<Artist_class> artist_classList;
    private RecyclerView mRecyclerView;
    private ArtistAdapter adapter;
    private ProgressBar progressBar;

    public ArtistFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_artist, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.artist_recyclerview);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        progressBar = (ProgressBar)view.findViewById(R.id.progress_bar);


        String url = getString(R.string.url) + "/api/v1/artist";
        new DownloadTask().execute(url);

        return view;
    }

    private void parseResult(String result) {
        try {
            JSONObject response = new JSONObject(result);
            JSONArray posts = response.optJSONArray("posts");
            artist_classList = new ArrayList<>();

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

    public class DownloadTask extends AsyncTask<String, Void, Integer> {

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Integer doInBackground(String... params) {
            Integer result = 0;
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
            return result; //"Failed to fetch data!";
        }

        @Override
        protected void onPostExecute(Integer result) {
            progressBar.setVisibility(View.GONE);

            if (result == 1) {
                adapter = new ArtistAdapter(getContext(), artist_classList);
                mRecyclerView.setAdapter(adapter);
            } else {
                Toast.makeText(getContext(), "Failed to fetch data!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
