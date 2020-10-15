package com.example.manivaandroapp.controller.mainc;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import com.example.manivaandroapp.R;
import com.example.manivaandroapp.ui.login.LoginFragment;
import com.example.manivaandroapp.ui.register.RegisterFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.StorageReference;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    private BottomNavigationView navView;
    private Context context = this;
    private FirebaseAuth firebaseAuth;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Passing each menu ID as a set of Ids because eachx
        // menu should be considered as top level destinations.

        //loading the default fragment
        init();

        loadFragment(new LoginFragment());
    }

    private void init(){
        navView = findViewById(R.id.nav_view);
        navView.setVisibility(View.GONE);
        //getting bottom navigation view and attaching the listener
        BottomNavigationView navigation = findViewById(R.id.nav_view);
        navigation.setOnNavigationItemSelectedListener(this);
    }

    public void launcMainPage(){
        navView.setVisibility(View.VISIBLE);

    }

    public void clearBackStack(){
        FragmentManager fm = getSupportFragmentManager();
        while (fm.getBackStackEntryCount() > 0) {
            fm.popBackStackImmediate();
        }
    }

    public boolean loadFragment(Fragment fragment) {
        //switching fragment

       // Fragment frag = fManager.findFragmentByTag("uniqueTag");
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment isFragmentExist =  fragmentManager.findFragmentByTag(fragment.getClass().getName());

        if (isFragmentExist == null) {
            fragmentTransaction.add(R.id.fragment_container, fragment, fragment.getClass().getName());
            fragmentTransaction.addToBackStack(fragment.getClass().getName());
            fragmentTransaction.commit();
        } else {
            fragmentTransaction.show(isFragmentExist);
        }
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return loadFragment(new LoginFragment());
    }


}