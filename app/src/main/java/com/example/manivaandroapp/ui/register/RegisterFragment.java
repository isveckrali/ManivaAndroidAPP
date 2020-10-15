package com.example.manivaandroapp.ui.register;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.manivaandroapp.R;
import com.example.manivaandroapp.controller.mainc.MainActivity;
import com.example.manivaandroapp.controller.models.UserInfo;
import com.example.manivaandroapp.controller.utils.Child;
import com.example.manivaandroapp.controller.utils.Helper;
import com.example.manivaandroapp.controller.utils.InternetController;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
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


    private ImageView backgrounIV;
    private View view;
    private TextInputEditText txtIETName, txtIETMail, txtIETUsername, txtIETPass;
    private TextInputLayout tILName, tILMail, tILUsername, tILPass;
    private FloatingActionButton fabRegister;
    private ProgressBar progressBar;
    private Context context;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_register, container, false);
        context = inflater.getContext();
        init();
        backAnimationManagement();
        return view;

    }

    private void init(){
        backgrounIV = view.findViewById(R.id.register_background_iV);
        txtIETName = view.findViewById(R.id.register_name_tIET);
        txtIETMail = view.findViewById(R.id.register_mail_tIET);
        txtIETUsername = view.findViewById(R.id.register_username_tIET);
        txtIETPass = view.findViewById(R.id.register_pass_tIET);

        tILName = view.findViewById(R.id.register_name_tIL);
        tILMail = view.findViewById(R.id.register_name_tIL);
        tILUsername = view.findViewById(R.id.register_username_tIL);
        tILPass = view.findViewById(R.id.register_pass_tIL);

        fabRegister = view.findViewById(R.id.register_fab);
        progressBar = view.findViewById(R.id.register_pB);

        fabRegister.setOnClickListener(this);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();


    }

    private void checkInternetAndGetData(){
        if (InternetController.isNetworkConnected(context)){
            checkTextInputsAndGoToRgister();
            progressBar.setVisibility(View.VISIBLE);
        } else {
            Toast.makeText(context, R.string.check_internet_connection, Toast.LENGTH_LONG).show();
        }

    }

    //Check user's fields whether is correct input
    private void checkTextInputsAndGoToRgister(){
        fabRegister.setClickable(false);
        String mail = txtIETMail.getText().toString().trim();
        String password = txtIETPass.getText().toString().trim();
        String username = txtIETUsername.getText().toString().trim();
        String name = txtIETName.getText().toString().trim();

        if (Helper.isFilledField(txtIETMail, tILMail, context) && Helper.isFilledField(txtIETPass, tILPass, context)) {
            if (Helper.isEmailValid(mail)){
               // checkUsername(mail,password,username, name);
                registerUser(mail,password,username, name);
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

    private void checkUsername(final String email, final String pass, final String username, final String name){
        Query query = databaseReference.child(Child.USERS).orderByChild("username").equalTo(username);
        Log.i("Girdi ", " 1");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.i("Girdi ", " 2");
                if (snapshot.getChildrenCount() > 0) {
                    tILUsername.setError(getResources().getText(R.string.exist_username));
                    progressBar.setClickable(true);
                    fabRegister.setVisibility(View.GONE);
                    Log.i("Girdi ", " 3");
                    // 1 or more users exist which have the username property "usernameToCheckIfExists"
                }  else {
                    registerUser(email,pass,username, name);
                    Log.i("Girdi ", " 4");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i("Girdi ", " 5");
            }

        });
    }


    private void registerUser(final String email, final String pass, final String username, final String name){
        Log.i("Girdi ", " 6");
        firebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.i("Girdi ", " 7");
                if (task.isSuccessful()) {
                    Log.i("Girdi ", " 8");
                    databaseReference.child(Child.USERS).push().setValue(new UserInfo(email,pass,name,username,  firebaseAuth.getUid()));
                    fabRegister.setClickable(true);
                    progressBar.setVisibility(View.GONE);
                    ((MainActivity) getActivity()).launcMainPage();
                } else {
                    Log.i("Girdi ", " 9 " + task.getException().getMessage());
                    Toast.makeText(context, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    fabRegister.setClickable(true);
                    progressBar.setVisibility(View.GONE);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("Girdi ", " 10 " + e.getMessage() + " " + e.getLocalizedMessage());
                fabRegister.setClickable(true);
                progressBar.setVisibility(View.GONE);
            }
        });


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
        backgrounIV.getLayoutParams().width= (int) (height*1.67);
        backgrounIV.getLayoutParams().height=height;

        ObjectAnimator animator = ObjectAnimator.ofFloat(backgrounIV,"x",0,-(height*1.67f-weight),0,-(height*1.67f-weight));
        animator.setDuration(210000);
        animator.setInterpolator(new LinearInterpolator());
        animator.start();
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