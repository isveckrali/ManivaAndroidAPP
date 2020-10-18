package com.example.manivaandroapp.ui.register;

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
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.manivaandroapp.R;
import com.example.manivaandroapp.controllers.loginRegister.LoginRegisterActivity;
import com.example.manivaandroapp.controllers.mainc.MainActivity;
import com.example.manivaandroapp.models.UserInfoModel;
import com.example.manivaandroapp.controllers.utils.Child;
import com.example.manivaandroapp.controllers.utils.Helper;
import com.example.manivaandroapp.controllers.utils.InternetController;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class RegisterFragment extends Fragment implements View.OnClickListener{

    //Properties
    private ImageView backgroundIV;
    private View view;
    private TextInputEditText txtIETName, txtIETMail, txtIETUsername, txtIETPass;
    private TextInputLayout tILName, tILMail, tILUsername, tILPass;
    private FloatingActionButton fabRegister;
    private ProgressBar progressBar;
    private Context context;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private CheckBox checkBox;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_register, container, false);
        context = inflater.getContext();
        init();
        ((LoginRegisterActivity)getActivity()).startImageAnimation(backgroundIV);
        return view;

    }

    //Define components and then bind them
    private void init(){
        backgroundIV = view.findViewById(R.id.register_background_iV);

        txtIETName = view.findViewById(R.id.register_name_tIET);
        txtIETMail = view.findViewById(R.id.register_mail_tIET);
        txtIETUsername = view.findViewById(R.id.register_username_tIET);
        txtIETPass = view.findViewById(R.id.register_pass_tIET);

        tILName = view.findViewById(R.id.register_name_tIL);
        tILMail = view.findViewById(R.id.register_mail_tIL);
        tILUsername = view.findViewById(R.id.register_username_tIL);
        tILPass = view.findViewById(R.id.register_pass_tIL);

        fabRegister = view.findViewById(R.id.register_fab);
        progressBar = view.findViewById(R.id.register_pB);
        checkBox = view.findViewById(R.id.register_remember_me_cB);

        fabRegister.setOnClickListener(this);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();


    }

    //Check whether internet connection is exist
    private void checkInternetAndGetData(){
        if (InternetController.isNetworkConnected(context)){
            checkTextInputsAndGoToRegister();
            progressBar.setVisibility(View.VISIBLE);
        } else {
            Toast.makeText(context, R.string.check_internet_connection, Toast.LENGTH_LONG).show();
        }

    }

    //Check user's fields whether is correct input
    private void checkTextInputsAndGoToRegister(){
        fabRegister.setClickable(false);
        String mail = txtIETMail.getText().toString().trim();
        String password = txtIETPass.getText().toString().trim();
        String username = txtIETUsername.getText().toString().trim();
        String name = txtIETName.getText().toString().trim();

        if (Helper.isFilledField(txtIETName, tILName, context) &&
                Helper.isFilledField(txtIETMail, tILMail, context) &&
                Helper.isFilledField(txtIETUsername, tILUsername, context) &&
                Helper.isFilledField(txtIETPass, tILPass, context)) {
            if (Helper.isEmailValid(mail)){
                checkUsername(mail,password,username, name);
               // registerUser(mail,password,username, name);
            } else {
                txtIETMail.setError(getResources().getText(R.string.not_mail));
                fabRegister.setClickable(true);
                progressBar.setVisibility(View.GONE);
            }
        } else  {
            fabRegister.setClickable(true);
            progressBar.setVisibility(View.GONE);
        }
    }

    //check whether username is exist in DB
    private void checkUsername(final String email, final String pass, final String username, final String name){
        Query query = databaseReference.child(Child.USERS).orderByChild(Child.USERNAME).equalTo(username);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getChildrenCount() > 0) {
                    tILUsername.setError(getResources().getText(R.string.exist_username));
                    progressBar.setClickable(true);
                    fabRegister.setVisibility(View.GONE);
                    // 1 or more users exist which have the username property "usernameToCheckIfExists"
                }  else {
                    registerUser(email,pass,username, name);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressBar.setClickable(true);
                fabRegister.setVisibility(View.GONE);
            }

        });
    }

    //Create user account and set data to our DB as well
    private void registerUser(final String email, final String pass, final String username, final String name){
        firebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    databaseReference.child(Child.USERS).child(firebaseAuth.getUid()).setValue(new UserInfoModel(email,pass,name,username));
                    fabRegister.setClickable(true);
                    progressBar.setVisibility(View.GONE);
                    if (checkBox.isChecked()) {
                        //Save info to Local DB by Shared Preference
                    }
                    startActivity(new Intent(getActivity(), MainActivity.class));
                    getActivity().finish();
                } else {
                    Toast.makeText(context, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    fabRegister.setClickable(true);
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.register_fab:
                checkInternetAndGetData();
                break;
            default:
                break;
        }
    }

}