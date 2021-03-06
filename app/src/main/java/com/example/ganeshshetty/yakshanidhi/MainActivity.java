package com.example.ganeshshetty.yakshanidhi;
/**
 * Author : Ganesh Shetty
 */

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.ganeshshetty.yakshanidhi.authorisation.SessionManager;
import com.example.ganeshshetty.yakshanidhi.fragments.ArtistFragment;
import com.example.ganeshshetty.yakshanidhi.fragments.MelaFragment;
import com.example.ganeshshetty.yakshanidhi.fragments.PrasanghaFragment;
import com.example.ganeshshetty.yakshanidhi.fragments.ShowFragment;
import com.example.ganeshshetty.yakshanidhi.model.Mela_class;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private final SessionManager session = SessionManager.getInstance();
    NavigationView navigationView=null;
    Toolbar toolbar=null;
    Bundle savedInstanceState;
    String key;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.savedInstanceState=savedInstanceState;
        setContentView(R.layout.activity_main);
        session.setContext(getApplicationContext());
        if(getIntent().getStringExtra("name")!=null)
        {
            key=getIntent().getStringExtra("name");
            String next=getIntent().getStringExtra("url");
            callFragment();
        }
        else if(getIntent().getStringExtra("name")==null)
        {
            MelaFragment melaFragment=new MelaFragment();
            FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container,melaFragment);
            fragmentTransaction.commit();
        }
        else if(getIntent().getExtras()!=null)
        {
            ShowFragment showFragment=new ShowFragment();
            FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container,showFragment);
            fragmentTransaction.commit();
        }

        // Setting initial fragment
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle(getResources().getString(R.string.app_name));


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void callFragment() {
        if (key.equalsIgnoreCase("mela")) {
            MelaFragment melaFragment = new MelaFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, melaFragment);
            fragmentTransaction.commit();
        }
        else if (key.equalsIgnoreCase("artist"))
        {
            ArtistFragment artistFragment=new ArtistFragment();
            FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container,artistFragment);
            fragmentTransaction.commit();
        }
        else if(key.equalsIgnoreCase("prasangha"))
        {
            PrasanghaFragment prasanghaFragment=new PrasanghaFragment();
            FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container,prasanghaFragment);
            fragmentTransaction.commit();
        }
        else if(key.equalsIgnoreCase("show"))
        {
            ShowFragment showFragment=new ShowFragment();
            FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container,showFragment);
            fragmentTransaction.commit();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch(id)
        {
            case R.id.action_search:
                Intent intent=new Intent(this,SearchOptionActivity.class);
                startActivity(intent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_mela) {
            MelaFragment melaFragment=new MelaFragment();
            FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container,melaFragment);
            fragmentTransaction.commit();
        } else if (id == R.id.nav_prasangha) {
            PrasanghaFragment prasanghaFragment=new PrasanghaFragment();
            FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container,prasanghaFragment);
            fragmentTransaction.commit();
        } else if (id == R.id.nav_kalavidaru) {
            ArtistFragment artistFragment=new ArtistFragment();
            FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container,artistFragment);
            fragmentTransaction.commit();
        } else if (id == R.id.nav_pradarshana) {
            ShowFragment showFragment=new ShowFragment();
            FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container,showFragment);
            fragmentTransaction.commit();
        } else if(id==R.id.logout) {
            session.logoutUser();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
