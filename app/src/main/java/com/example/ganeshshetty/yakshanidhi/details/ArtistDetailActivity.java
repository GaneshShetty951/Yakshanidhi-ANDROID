package com.example.ganeshshetty.yakshanidhi.details;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ganeshshetty.yakshanidhi.R;
import com.example.ganeshshetty.yakshanidhi.model.Artist_class;
import com.squareup.picasso.Picasso;

public class ArtistDetailActivity extends AppCompatActivity {
    TextView nameView, divisionView, placeView;
    ImageView picView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_detail);


        Artist_class artist_class = (Artist_class) getIntent().getSerializableExtra("Artist_model");
        nameView = (TextView) findViewById(R.id.artist_name);
        divisionView = (TextView) findViewById(R.id.division_name);
        placeView = (TextView) findViewById(R.id.artist_place_name);

        nameView.setText(artist_class.getFirst_name());
        divisionView.setText(artist_class.getType());
        placeView.setText(artist_class.getPlace());
        if (!TextUtils.isEmpty(artist_class.getPic())) {
            Picasso.with(ArtistDetailActivity.this).load(artist_class.getPic()).error(R.id.userimg).placeholder(R.id.userimg).into(picView);

        }
    }
}
