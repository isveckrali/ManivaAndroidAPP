package com.example.manivaandroapp.ui.login;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
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
import com.example.manivaandroapp.controllers.loginRegister.LoginRegisterActivity;
import com.example.manivaandroapp.controllers.mainc.MainActivity;
import com.example.manivaandroapp.controllers.utils.FragmentHelper;
import com.example.manivaandroapp.controllers.utils.Helper;
import com.example.manivaandroapp.controllers.utils.InternetController;
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

    //Properties
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
        ((LoginRegisterActivity)getActivity()).startImageAnimation(iVBackground);
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
        progressBar = view.findViewById(R.id.login_pB);

        fabLogin.setOnClickListener(this);
        btnRgister.setOnClickListener(this);
        tVForgetPassword.setOnClickListener(this);


        firebaseAuth = FirebaseAuth.getInstance();

    }

    //Check wherher user connection is exist
    private void checkInternetAndGetData(){
        if (InternetController.isNetworkConnected(context)){
            checkTextInputsAndGoToLogin();
        } else {
            Toast.makeText(context, R.string.check_internet_connection, Toast.LENGTH_LONG).show();
        }
    }



    //Check user's fields whether is correct input
    private void checkTextInputsAndGoToLogin(){
        fabLogin.setClickable(false);
        progressBar.setVisibility(View.VISIBLE);
        String mail = txtIETMail.getText().toString().trim();
        String password = txtIETPass.getText().toString().trim();
        if (Helper.isFilledField(txtIETMail, txtILMail, context) && Helper.isFilledField(txtIETPass, txtILPass, context)) {
            if (Helper.isEmailValid(mail)){
                login(mail,password);
            } else {
                txtILMail.setError(getResources().getText(R.string.not_mail));
                fabLogin.setClickable(true);
                progressBar.setVisibility(View.GONE);
            }
            return;
        } else  {
            fabLogin.setClickable(true);
            progressBar.setVisibility(View.GONE);
        }
    }

    //Login with firebase
    private void login(String mail, String pass){

        firebaseAuth.signInWithEmailAndPassword(mail, pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                fabLogin.setClickable(true);
                progressBar.setVisibility(View.GONE);
                startActivity(new Intent(getActivity(),MainActivity.class));
                getActivity().finish();
                if (cBRememberMe.isChecked()) {
                //Save info to Local DB
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                fabLogin.setClickable(true);
                progressBar.setVisibility(View.GONE);
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
                FragmentHelper.loadFragment(new RegisterFragment(), context, R.id.log_reg_container_fL);
                break;
            case R.id.login_forget_pass_tV:
                break;
            default:
                break;
        }
    }
}