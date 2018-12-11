package com.example.driesvandievoort.android;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.driesvandievoort.android.Fragments.AccountFragment;
import com.example.driesvandievoort.android.Fragments.FavoritesFragment;
import com.example.driesvandievoort.android.Fragments.HomeFragment;
import com.example.driesvandievoort.android.Fragments.SearchFragment;

public class SecondActivity extends AppCompatActivity {

    BottomNavigationView bottomnNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        bottomnNav = findViewById(R.id.navigation);
        bottomnNav.setOnNavigationItemSelectedListener(navlistener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navlistener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;

                    switch (menuItem.getItemId()){
                        case R.id.menu_home:
                            selectedFragment = new HomeFragment();
                            break;
                        case R.id.menu_search:
                            selectedFragment = new SearchFragment();
                            break;
                        case R.id.menu_favorites:
                            selectedFragment = new FavoritesFragment();
                            break;
                        case R.id.menu_account:
                            selectedFragment = new AccountFragment();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();

                    return true;
                }
            };
}
