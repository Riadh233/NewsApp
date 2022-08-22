package com.example.newsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.newsapp.fragments.GamesFragment;
import com.example.newsapp.fragments.MusicFragment;
import com.example.newsapp.fragments.PoliticsFragment;
import com.example.newsapp.fragments.ScienceFragment;
import com.example.newsapp.fragments.SportsFragment;
import com.example.newsapp.fragments.TechnologiesFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    ImageView previousButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        drawer = findViewById(R.id.drawer_layout);
        previousButton = findViewById(R.id.nav_previous);

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_view_open, R.string.navigation_view_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SportsFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_sports);
        }
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_sports:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SportsFragment()).commit();
                break;
            case R.id.nav_technologies:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new TechnologiesFragment()).commit();
                break;
            case R.id.nav_politics:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new PoliticsFragment()).commit();
                break;
            case R.id.nav_science:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ScienceFragment()).commit();
                break;
            case R.id.nav_games:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new GamesFragment()).commit();
                break;
            case R.id.nav_music:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MusicFragment()).commit();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.nav_next, menu);
        getMenuInflater().inflate(R.menu.nav_previous, menu);
        return true;
    }
}