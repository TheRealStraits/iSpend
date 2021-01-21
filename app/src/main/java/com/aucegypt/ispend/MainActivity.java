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

    Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle extras = getIntent().getExtras();

        BottomNavigationView bottomNavigation = (BottomNavigationView) findViewById(R.id.bottomNav);
        if (savedInstanceState == null) {
            if (extras != null) {
                Boolean value = extras.getBoolean("Home");
                if(value) {
                    bottomNavigation.setSelectedItemId(R.id.homeFragment);
                    Fragment selectedFragment = new homeFragment();
                    currentFragment = selectedFragment; // holds the current fragment for the bottomnNavigation Method to avoid going back to home for a sec when going from settings to stats
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,
                            selectedFragment).commit();
                }
                else {
                    bottomNavigation.setSelectedItemId(R.id.settingsItem);
                    Fragment selectedFragment = new settingsFragment();
                    currentFragment = selectedFragment;
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,
                            selectedFragment).commit();
                }
            }

            else{ //Default case if we aren't coming from the statistics page
                bottomNavigation.setSelectedItemId(R.id.homeFragment);
                Fragment selectedFragment = new homeFragment();
                currentFragment = selectedFragment;
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,
                        selectedFragment).commit();
            }
        }
        bottomNavigation.setOnNavigationItemSelectedListener(navListener);


    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    Fragment selectedFragment = currentFragment;

                    switch (item.getItemId())
                    {
                        case R.id.homeItem:
                            selectedFragment = new homeFragment();
                            break;
                        case R.id.statItem:
                           //selectedFragment = new statsFragment();
                            Intent intent = new Intent(getBaseContext(), StatsActivity.class);
                            startActivity(intent);
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