package com.example.ganeshshetty.yakshanidhi.Details;

import android.content.Context;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.example.ganeshshetty.yakshanidhi.Model.Mela_class;
import com.example.ganeshshetty.yakshanidhi.R;

public class MelaDetailActivity extends AppCompatActivity {
    TextView emailView,contactView,addressView;
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

}
