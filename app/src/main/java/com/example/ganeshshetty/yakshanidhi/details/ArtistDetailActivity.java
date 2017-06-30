package com.example.ganeshshetty.yakshanidhi.details;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ganeshshetty.yakshanidhi.R;
import com.example.ganeshshetty.yakshanidhi.SearchOptionActivity;
import com.example.ganeshshetty.yakshanidhi.model.Artist_class;
import com.squareup.picasso.Picasso;

public class ArtistDetailActivity extends AppCompatActivity {
    TextView nameView, divisionView, placeView,mela_name;
    ImageView picView,melaPic;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_detail);
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Artist_class artist_class = (Artist_class) getIntent().getSerializableExtra("Artist_model");
        nameView = (TextView) findViewById(R.id.artist_name);
        divisionView = (TextView) findViewById(R.id.division_name);
        placeView = (TextView) findViewById(R.id.artist_place_name);
        mela_name=(TextView)findViewById(R.id.artist_mela_name);
        picView=(ImageView)findViewById(R.id.userimg);
        melaPic=(ImageView)findViewById(R.id.mela_pic);
        nameView.setText(artist_class.getFirst_name());
        divisionView.setText(artist_class.getType());
        placeView.setText(artist_class.getPlace());
        mela_name.setText(artist_class.getMelaName());
        if (!TextUtils.isEmpty(artist_class.getPic())) {
            Picasso.with(this).load(getString(R.string.url)+"/artist_images/"+artist_class.getPic()).error(R.drawable.bg).placeholder(R.drawable.yakshanidhi).into(picView);
            Picasso.with(this).load(getString(R.string.url)+"/mela_images/"+artist_class.getMelaPic()).error(R.drawable.bg).placeholder(R.drawable.bg).into(melaPic);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:finish();
                break;
            case R.id.action_search:
                Intent intent=new Intent(this,SearchOptionActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
