package com.example.manivaandroapp.controllers.loginRegister;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.example.manivaandroapp.R;
import com.example.manivaandroapp.controllers.mainc.MainActivity;
import com.example.manivaandroapp.controllers.utils.FragmentHelper;
import com.example.manivaandroapp.controllers.utils.Helper;
import com.example.manivaandroapp.ui.login.LoginFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginRegisterActivity extends AppCompatActivity {

    //Properties
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

    //Add background animation to image view
    public void startImageAnimation(ImageView imageView){
        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        WindowManager wm=window.getWindowManager();
        Display display = wm.getDefaultDisplay();

        Point point = new Point();
        display.getSize(point);

        int weight=point.x;
        int height=point.y;

        //weight- hegiht rate is 1.67
        imageView.getLayoutParams().width= (int) (height*1.67);
        imageView.getLayoutParams().height=height;

        ObjectAnimator animator = ObjectAnimator.ofFloat(imageView,"x",0,-(height*1.67f-weight),0,-(height*1.67f-weight));
        animator.setDuration(210000);
        animator.setInterpolator(new LinearInterpolator());
        animator.start();
    }

    //initialize components and bind them
    private void init(){
        imageView = findViewById(R.id.log_reg_iV);
        mAuth = FirebaseAuth.getInstance();

    }

    //Load new screen after waiting for a second
    private void checkUser(){
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                FirebaseUser currentUser = mAuth.getCurrentUser();
                updateUI(currentUser);
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    //Update user interface with a new screen
    private void updateUI(FirebaseUser currentUser) {
        if (currentUser != null) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        } else {
            FragmentHelper.loadFragment(new LoginFragment(),this,R.id.log_reg_container_fL);
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