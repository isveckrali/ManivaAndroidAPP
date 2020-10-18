package com.example.manivaandroapp.controllers.utils;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class FragmentHelper {

    //Load fragment by tag and then get back them.
    static public boolean loadFragment(Fragment fragment, Context context, int fragmentContainer) {
        //switching fragment
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
}
