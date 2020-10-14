package com.example.manivaandroapp.ui.register;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.manivaandroapp.R;
import com.example.manivaandroapp.controller.mainc.MainActivity;
import com.example.manivaandroapp.controller.utils.InternetController;
import com.example.manivaandroapp.ui.login.LoginFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class RegisterFragment extends Fragment implements View.OnClickListener{


    private ImageView backgrounIV;
    private View view;
    private TextInputEditText txtIETName, txtIETMail, txtIETUsername, txtIETPass;
    private FloatingActionButton fabRegister;
    private ProgressBar progressBar;
    private Context context;



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

        fabRegister = view.findViewById(R.id.register_fab);
        progressBar = view.findViewById(R.id.login_pB);

        fabRegister.setOnClickListener(this);

    }

    private void checkInternetAndGetData(){
        if (InternetController.isNetworkConnected(context)){

        } else {
            Toast.makeText(context, R.string.check_internet_collection, Toast.LENGTH_LONG).show();
        }

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