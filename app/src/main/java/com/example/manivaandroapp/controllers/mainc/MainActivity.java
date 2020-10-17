package com.example.manivaandroapp.controllers.mainc;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.manivaandroapp.R;
import com.example.manivaandroapp.controllers.loginRegister.LoginRegisterActivity;
import com.example.manivaandroapp.controllers.utils.Helper;
import com.example.manivaandroapp.ui.aboutUs.AboutUsFragment;
import com.example.manivaandroapp.ui.home.HomeFragment;
import com.example.manivaandroapp.ui.portfolio.PortfolioFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.logging.LogRecord;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private BottomNavigationView navView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Passing each menu ID as a set of Ids because eachx
        // menu should be considered as top level destinations.
        //loading the default fragment

        init();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                logout();
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void logout(){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(this, LoginRegisterActivity.class));
        finish();
    }

    private void init() {
        navView = findViewById(R.id.nav_view);
        //getting bottom navigation view and attaching the listener
        BottomNavigationView navigation = findViewById(R.id.nav_view);
        navigation.setOnNavigationItemSelectedListener(this);
        Helper.loadFragment(new HomeFragment(), this, R.id.fragment_container);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_home:
                return Helper.loadFragment(new HomeFragment(), this, R.id.fragment_container);
            case R.id.navigation_about_us:
                return Helper.loadFragment(new AboutUsFragment(), this, R.id.fragment_container);
            case R.id.navigation_book_online:
                return Helper.loadFragment(new AboutUsFragment(), this, R.id.fragment_container);
            case R.id.navigation_portfolio:
                return Helper.loadFragment(new PortfolioFragment(), this, R.id.fragment_container);
            default:
                return false;
        }
    }

}