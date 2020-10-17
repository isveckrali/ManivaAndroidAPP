package com.example.manivaandroapp.controllers.loginRegister;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import com.example.manivaandroapp.R;
import com.example.manivaandroapp.controllers.mainc.MainActivity;
import com.example.manivaandroapp.controllers.utils.Helper;
import com.example.manivaandroapp.ui.login.LoginFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginRegisterActivity extends AppCompatActivity {

    private Context context = this;
    private FirebaseAuth mAuth;

    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 1000;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);

        init();

    }

    @Override
    protected void onStart() {
        super.onStart();
        checkUser();
    }

    private void init(){
        //Helper.loadFragment(new SplashFragment(), this, R.id.log_reg_container_fL);
        imageView = findViewById(R.id.log_reg_iV);
        mAuth = FirebaseAuth.getInstance();

    }

    private void checkUser(){
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                FirebaseUser currentUser = mAuth.getCurrentUser();
                updateUI(currentUser);
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    private void updateUI(FirebaseUser currentUser) {
        if (currentUser != null) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        } else {
            Helper.loadFragment(new LoginFragment(),this,R.id.log_reg_container_fL);
            //Helper.removeFragment(new SplashFragment(), this);
            imageView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count == 0) {
            finish();
        }
    }
}