package com.example.abhrecipes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.net.wifi.hotspot2.pps.HomeSp;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState();

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentHome()).commit();
        navigationView.setCheckedItem(R.id.nav_home);

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
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentHome()).commit();
                break;
            case R.id.nav_name:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentSearchByName()).commit();
                break;
            case R.id.nav_main_ingredient:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentSearchByMainIngredient()).commit();
                break;
            case R.id.nav_firstletter:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentSearchByLetter()).commit();
                break;
            case R.id.nav_catagory:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentSearchByCatagory()).commit();
                break;
            case R.id.nav_area:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentSearchByArea()).commit();
                break;
            case R.id.help:
                Toast.makeText(getApplicationContext(),"Service not available at the moment",Toast.LENGTH_SHORT).show();
                break;
            case R.id.aboutus:
                Toast.makeText(getApplicationContext(),"Service not available at the moment",Toast.LENGTH_SHORT).show();
                break;
            case R.id.exit:
                finish();
                System.exit(0);
                break;

        }
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }
}