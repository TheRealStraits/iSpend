package com.aucegypt.ispend;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigation = findViewById(R.id.bottomNav);
        bottomNavigation.setOnNavigationItemSelectedListener(navListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

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