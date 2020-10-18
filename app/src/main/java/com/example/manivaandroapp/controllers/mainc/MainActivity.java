package com.example.manivaandroapp.controllers.mainc;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.manivaandroapp.R;
import com.example.manivaandroapp.controllers.loginRegister.LoginRegisterActivity;
import com.example.manivaandroapp.controllers.utils.FragmentHelper;
import com.example.manivaandroapp.controllers.utils.Helper;
import com.example.manivaandroapp.ui.aboutUs.AboutUsFragment;
import com.example.manivaandroapp.ui.bookOnline.BookOnlineFragment;
import com.example.manivaandroapp.ui.home.HomeFragment;
import com.example.manivaandroapp.ui.portfolio.PortfolioFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.util.List;
import java.util.logging.LogRecord;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    //Properties
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

    //provide user to logout
    private void logout(){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(this, LoginRegisterActivity.class));
        finish();
    }

    //initialize compınents and bind them
    private void init() {
        navView = findViewById(R.id.nav_view);
        //getting bottom navigation view and attaching the listener
        BottomNavigationView navigation = findViewById(R.id.nav_view);
        navigation.setOnNavigationItemSelectedListener(this);
        FragmentHelper.loadFragment(new HomeFragment(), this, R.id.fragment_container);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_home:
                return FragmentHelper.loadFragment(new HomeFragment(), this, R.id.fragment_container);
            case R.id.navigation_about_us:
                return FragmentHelper.loadFragment(new AboutUsFragment(), this, R.id.fragment_container);
            case R.id.navigation_book_online:
                return FragmentHelper.loadFragment(new AboutUsFragment(), this, R.id.fragment_container);
            case R.id.navigation_portfolio:
                return FragmentHelper.loadFragment(new PortfolioFragment(), this, R.id.fragment_container);
            default:
                return false;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        int count = getSupportFragmentManager().getBackStackEntryCount();
        navView.getMenu().getItem(0).setChecked(true);
        if (count == 0) {
            finish();
        }
    }
}