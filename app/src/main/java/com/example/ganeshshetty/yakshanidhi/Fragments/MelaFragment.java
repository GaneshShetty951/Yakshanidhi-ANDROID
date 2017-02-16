package com.example.ganeshshetty.yakshanidhi.Fragments;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.ganeshshetty.yakshanidhi.Details.MelaDetailActivity;
import com.example.ganeshshetty.yakshanidhi.Model.Mela_class;
import com.example.ganeshshetty.yakshanidhi.Adapters.MelaRecyclerViewAdapter;
import com.example.ganeshshetty.yakshanidhi.ItemClickListener.MelaOnItemClickListener;
import com.example.ganeshshetty.yakshanidhi.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MelaFragment extends Fragment {
    private static final String TAG = "RecyclerViewExample";
    private List<Mela_class> feedsList;
    private RecyclerView mRecyclerView;
    private MelaRecyclerViewAdapter adapter;
    private ProgressBar progressBar;

    public MelaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mela, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        progressBar = (ProgressBar)view.findViewById(R.id.progress_bar);


        String url = "http://192.168.0.101/api/v1/mela";
        new DownloadTask().execute(url);

        return view;
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
                adapter = new MelaRecyclerViewAdapter(getContext(), feedsList);
                mRecyclerView.setAdapter(adapter);
                adapter.setMelaOnItemClickListener(new MelaOnItemClickListener() {
                    @Override
                    public void onItemClick(Mela_class item) {
                        Intent mainIntent=new Intent(getActivity(),MelaDetailActivity.class);
                        mainIntent.putExtra("Mela_model",item);
                        startActivity(mainIntent);
                    }
                });
            } else {
                Toast.makeText(getContext(), "Failed to fetch data!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void parseResult(String result) {
        try {
            JSONObject response = new JSONObject(result);
            JSONArray posts = response.optJSONArray("posts");
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
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
