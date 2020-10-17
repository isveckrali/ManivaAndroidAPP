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

    static public boolean loadFragment(Fragment fragment, Context context, int fragmentContainer) {
        //switching fragment
        // Fragment frag = fManager.findFragmentByTag("uniqueTag");
        FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment isFragmentExist =  fragmentManager.findFragmentByTag(fragment.getClass().getName());

        if (isFragmentExist == null) {
            fragmentTransaction.add(fragmentContainer, fragment, fragment.getClass().getName());
            fragmentTransaction.addToBackStack(fragment.getClass().getName());
            fragmentTransaction.commit();
        } else {
            fragmentTransaction.show(isFragmentExist);
        }
        return true;
    }

    static public boolean removeFragment(Fragment fragment, Context context) {
        //switching fragment
        // Fragment frag = fManager.findFragmentByTag("uniqueTag");
        FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment isFragmentExist =  fragmentManager.findFragmentByTag(fragment.getClass().getName());

        if (isFragmentExist != null) {
            fragmentTransaction.remove(isFragmentExist);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }

        return true;
    }

}
