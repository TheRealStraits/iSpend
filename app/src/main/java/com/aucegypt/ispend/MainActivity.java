package com.aucegypt.ispend;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigation = (BottomNavigationView) findViewById(R.id.bottomNav);
        bottomNavigation.setOnNavigationItemSelectedListener(navListener);

        if (savedInstanceState == null) {
            bottomNavigation.setSelectedItemId(R.id.homeFragment);
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    Fragment selectedFragment = new homeFragment();

                    switch (item.getItemId())
                    {
                        case R.id.homeItem:
                            selectedFragment = new homeFragment();
                            break;
                        case R.id.statItem:
                            selectedFragment = new statsFragment();
                            break;
                        case R.id.settingsItem:
                            selectedFragment = new settingsFragment();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,
                            selectedFragment).commit();

                    return true;
                }
            };
}