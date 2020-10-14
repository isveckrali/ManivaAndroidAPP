package com.example.manivaandroapp.ui.login;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.manivaandroapp.R;
import com.example.manivaandroapp.controller.mainc.MainActivity;
import com.example.manivaandroapp.controller.utils.InternetController;
import com.example.manivaandroapp.ui.home.HomeFragment;
import com.example.manivaandroapp.ui.register.RegisterFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {

    private ImageView iVBackground;
    private View view;
    private TextInputEditText  txtIETMail, txtIETPass;
    private FloatingActionButton fabLogin;
    private Button btnRgister;
    private TextView tVForgetPassword;

    private Context context;
    private FirebaseAuth firebaseAuth;
    private ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_login, container, false);
        context = inflater.getContext();
        init();
        backAnimationManagement();
        return view;
    }

    private void init(){
        iVBackground = view.findViewById(R.id.login_background_iV);
        txtIETMail = view.findViewById(R.id.login_mail_tIET);
        txtIETPass = view.findViewById(R.id.login_pass_tIET);

        fabLogin = view.findViewById(R.id.login_fab);
        btnRgister = view.findViewById(R.id.login_register_b);
        tVForgetPassword = view.findViewById(R.id.login_forget_pass_tV);

        fabLogin.setOnClickListener(this);
        btnRgister.setOnClickListener(this);
        tVForgetPassword.setOnClickListener(this);

        firebaseAuth = FirebaseAuth.getInstance();

    }

    private void backAnimationManagement(){
        Window window = getActivity().getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        WindowManager wm=window.getWindowManager();
        Display display = wm.getDefaultDisplay();

        Point point = new Point();
        display.getSize(point);

        int weight=point.x;
        int height=point.y;

        //weight- hegiht rate is 1.67
        iVBackground.getLayoutParams().width= (int) (height*1.67);
        iVBackground.getLayoutParams().height=height;

        ObjectAnimator animator = ObjectAnimator.ofFloat(iVBackground,"x",0,-(height*1.67f-weight),0,-(height*1.67f-weight));
        animator.setDuration(210000);
        animator.setInterpolator(new LinearInterpolator());
        animator.start();
    }

    private void checkInternetAndGetData(){
        if (InternetController.isNetworkConnected(context)){
            login();
        } else {
            Toast.makeText(context, R.string.check_internet_collection, Toast.LENGTH_LONG).show();
        }
    }

    private void login(){
        ((MainActivity)getActivity()).launcMainPage();
        ((MainActivity)getActivity()).loadFragment(new HomeFragment());

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_fab:
                checkInternetAndGetData();
                break;
            case R.id.login_register_b:
                ((MainActivity) getActivity()).loadFragment(new RegisterFragment());
                break;
            case R.id.login_forget_pass_tV:
                break;
            default:
                break;
        }

    }
}