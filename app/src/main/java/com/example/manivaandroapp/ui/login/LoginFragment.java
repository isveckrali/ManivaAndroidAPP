package com.example.manivaandroapp.ui.login;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.manivaandroapp.R;
import com.example.manivaandroapp.controller.mainc.MainActivity;
import com.example.manivaandroapp.controller.utils.Helper;
import com.example.manivaandroapp.controller.utils.InternetController;
import com.example.manivaandroapp.ui.home.HomeFragment;
import com.example.manivaandroapp.ui.register.RegisterFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {

    private ImageView iVBackground;
    private View view;
    private TextInputEditText  txtIETMail, txtIETPass;
    private TextInputLayout txtILMail, txtILPass;
    private FloatingActionButton fabLogin;
    private Button btnRgister;
    private TextView tVForgetPassword;
    private CheckBox cBRememberMe;

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
        backAnimationManagement(iVBackground);
        return view;
    }

    //initiliza Uİ
    private void init(){
        iVBackground = view.findViewById(R.id.login_background_iV);
        txtIETMail = view.findViewById(R.id.login_mail_tIET);
        txtIETPass = view.findViewById(R.id.login_pass_tIET);

        fabLogin = view.findViewById(R.id.login_fab);
        btnRgister = view.findViewById(R.id.login_register_b);
        tVForgetPassword = view.findViewById(R.id.login_forget_pass_tV);
        cBRememberMe = view.findViewById(R.id.login_remember_me_cB);

        txtILMail = view.findViewById(R.id.login_mail_tIL);
        txtILPass = view.findViewById(R.id.login_pass_tIL);

        fabLogin.setOnClickListener(this);
        btnRgister.setOnClickListener(this);
        tVForgetPassword.setOnClickListener(this);


        firebaseAuth = FirebaseAuth.getInstance();

    }

    //Check wherher user connection is exist
    private void checkInternetAndGetData(){
        if (InternetController.isNetworkConnected(context)){
            //login();
            checkTextInputsAndGoToLogin();
        } else {
            Toast.makeText(context, R.string.check_internet_connection, Toast.LENGTH_LONG).show();
        }
    }

    //Add background animation to image view
    private void backAnimationManagement(ImageView imageView){
        Window window = getActivity().getWindow();
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

    //Check user's fields whether is correct input
    private void checkTextInputsAndGoToLogin(){
        fabLogin.setClickable(false);
        String mail = txtIETMail.getText().toString().trim();
        String password = txtIETPass.getText().toString().trim();
        if (Helper.isFilledField(txtIETMail, txtILMail, context) && Helper.isFilledField(txtIETPass, txtILPass, context)) {
            if (Helper.isEmailValid(mail)){
                login(mail,password);
            } else {
                txtILMail.setError(getResources().getText(R.string.not_mail));
                fabLogin.setClickable(true);
            }
            return;
        } else  {
            fabLogin.setClickable(true);
        }
    }

    //Login with firebase
    private void login(String mail, String pass){
        Toast.makeText(context, "Gerçekleşti1", Toast.LENGTH_SHORT).show();

        firebaseAuth.signInWithEmailAndPassword(mail, pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(context, "Gerçekleşti", Toast.LENGTH_SHORT).show();
                fabLogin.setClickable(true);
                if (cBRememberMe.isChecked()) {

                }
                ((MainActivity)getActivity()).launcMainPage();
                ((MainActivity)getActivity()).loadFragment(new HomeFragment());

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                fabLogin.setClickable(true);
                Toast.makeText(context, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
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