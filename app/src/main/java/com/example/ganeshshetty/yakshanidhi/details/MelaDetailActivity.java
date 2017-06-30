package com.example.ganeshshetty.yakshanidhi.details;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ganeshshetty.yakshanidhi.R;
import com.example.ganeshshetty.yakshanidhi.SearchOptionActivity;
import com.example.ganeshshetty.yakshanidhi.model.Mela_class;
import com.squareup.picasso.Picasso;

public class MelaDetailActivity extends AppCompatActivity {
    TextView emailView,contactView,addressView;
    ImageView melaPic;
    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mela_detail);

        emailView=(TextView)findViewById(R.id.email);
        contactView=(TextView)findViewById(R.id.contact);
        addressView=(TextView)findViewById(R.id.address);
        Mela_class mela_class = (Mela_class)getIntent().getSerializableExtra("Mela_model".trim());
        emailView.setText(mela_class.getEmail());
        contactView.setText(mela_class.getContact());
        addressView.setText(mela_class.getVillage()+"\n"+mela_class.getTaluk()+"\n"+mela_class.getDistrict()+"\n PIN-"+mela_class.getPinode());
        melaPic=(ImageView)findViewById(R.id.bgheader);
        Picasso.with(this).load(getString(R.string.url)+"/mela_images/"+mela_class.getThumbnail()).error(R.drawable.yakshanidhi).placeholder(R.drawable.yakshanidhi).into(melaPic);
        Log.i("Activity",mela_class.getThumbnail());
        Toolbar toolbar = (Toolbar) findViewById(R.id.MyToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String str=mela_class.getName();
        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapse_toolbar);
        if(mela_class!=null) {
            collapsingToolbar.setTitle(mela_class.getName().toString());
        }else {
            collapsingToolbar.setTitle(getResources().getString(R.string.app_name));
        }
        Context context=this;
        collapsingToolbar.setContentScrimColor(ContextCompat.getColor(context,R.color.colorAccent));

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
