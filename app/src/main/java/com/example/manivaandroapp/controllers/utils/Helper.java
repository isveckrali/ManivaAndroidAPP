package com.example.manivaandroapp.controllers.utils;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.manivaandroapp.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class Helper {

    //Check whether input is mail
    static public boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    //check whether input is empty
    static public boolean isFilledField(TextInputEditText editText, TextInputLayout textInputLayout, Context context){
        if (editText.getText().toString().isEmpty()) {
            textInputLayout.setError(context.getResources().getText(R.string.empty_field));
            return false;
        } else {
            return true;
        }
    }
}
