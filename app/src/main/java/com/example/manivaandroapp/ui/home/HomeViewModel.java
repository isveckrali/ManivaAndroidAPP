package com.example.manivaandroapp.ui.home;

import android.text.Html;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.manivaandroapp.models.HomeModel;

public class HomeViewModel extends ViewModel {


    private String imageUrl;
    private String title;
    private String sortDescription;

    //Dependency injection
    public HomeViewModel(String imageUrl, String title, String sortDescription) {
        this.imageUrl = imageUrl;
        this.title = title;
        this.sortDescription = sortDescription;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getSortDescription() {
        String[] sortDescArray = sortDescription.split(" ");
        if (sortDescArray.length < 15) {
            return sortDescription;
        } else {
            String newDesc = "";
            for (int i = 0; i < 15; i++) {
                newDesc = newDesc  +  sortDescArray[i] + " ";
            }
            return newDesc + "...   Read More ...";
        }
    }
}