package com.example.ganeshshetty.yakshanidhi.fragments;

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

import com.example.ganeshshetty.yakshanidhi.R;
import com.example.ganeshshetty.yakshanidhi.adapters.ShowAdapter;
import com.example.ganeshshetty.yakshanidhi.model.Show_class;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShowFragment extends Fragment {


    private static final String TAG = "ShowFragment";
    private ProgressBar progressBar;
    private RecyclerView mRecyclerView;
    private ArrayList<Show_class> shows;
    private ShowAdapter showAdapter;
    private boolean isSearched = false;
    private String nexturl;

    public ShowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.show_recycle);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        if (getActivity().getIntent().getSerializableExtra("data")!= null) {
            isSearched=true;
            progressBar.setVisibility(View.GONE);
            shows= (ArrayList<Show_class>) getActivity().getIntent().getSerializableExtra("data");
            showAdapter = new ShowAdapter(getContext(), shows);
            mRecyclerView.setAdapter(showAdapter);
        } else {
            String url = getString(R.string.url) + "/api/v1/show";
            new DownloadTask().execute(url);
        }
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
            showAdapter = new ShowAdapter(getContext(), shows);
            mRecyclerView.setAdapter(showAdapter);
        }
    }


    private void parseResult(String s) {
        try {
            JSONObject response = new JSONObject(s);
            JSONArray posts = response.optJSONArray("posts");
            shows = new ArrayList<>();

            for (int i = 0; i < posts.length(); i++) {
                JSONObject post = posts.optJSONObject(i);
                Show_class item = new Show_class();
                item.setShow_id(post.getInt("show_id"));
                item.setShow_producer_name(post.getString("show_producer_name"));
                item.setShow_date(post.getString("show_date"));
                item.setShow_time(post.getString("show_time"));
                item.setContact1(post.getString("contact_1"));
                item.setContact2(post.getString("contact_2"));
                item.setVillage(post.getString("village"));
                item.setTaluk(post.getString("taluk"));
                item.setDistrict(post.getString("district"));
                item.setPincode(post.getString("PINCODE"));
                item.setMela_name(post.getString("mela_name"));
                item.setMela_pic(post.getString("mela_pic"));
                item.setPrasangha_name(post.getString("prasangha_name"));
                shows.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
