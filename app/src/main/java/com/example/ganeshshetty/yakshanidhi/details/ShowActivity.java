package com.example.ganeshshetty.yakshanidhi.details;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ganeshshetty.yakshanidhi.R;
import com.example.ganeshshetty.yakshanidhi.model.Show_class;
import com.squareup.picasso.Picasso;

public class ShowActivity extends AppCompatActivity {
    private ImageView melaImage;
    private TextView melaName,prodName,timeText,dateText,addressText,contact1,contact2;
    private Toolbar toolbar;
    private Show_class show;
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
}
