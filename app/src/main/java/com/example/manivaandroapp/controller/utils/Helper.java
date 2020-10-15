package com.example.manivaandroapp.controller.utils;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.example.manivaandroapp.R;
import com.example.manivaandroapp.controller.mainc.MainActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class Helper {

    static public boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    static public boolean isFilledField(TextInputEditText editText, TextInputLayout textInputLayout, Context context){
        if (editText.getText().toString().isEmpty()) {
            textInputLayout.setError(context.getResources().getText(R.string.empty_field));
            return false;
        } else {
            return true;
        }
    }



}
