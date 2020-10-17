package com.example.manivaandroapp.models;

import java.util.ArrayList;

public class HomeModel {

    private ArrayList<String> imagesURL;
    private ArrayList<String> titles;
    private ArrayList<String> descriptions;

    public HomeModel() {
    }

    public HomeModel(ArrayList<String> imagesURL, ArrayList<String> titles, ArrayList<String> descriptions) {
        this.imagesURL = imagesURL;
        this.titles = titles;
        this.descriptions = descriptions;
    }

    public ArrayList<String> getImagesURL() {
        return imagesURL;
    }

    public void setImagesURL(ArrayList<String> imagesURL) {
        this.imagesURL = imagesURL;
    }

    public ArrayList<String> getTitles() {
        return titles;
    }

    public void setTitles(ArrayList<String> titles) {
        this.titles = titles;
    }

    public ArrayList<String> getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(ArrayList<String> descriptions) {
        this.descriptions = descriptions;
    }

    @Override
    public String toString() {
        return "HomeModel{" +
                "imagesURL=" + imagesURL +
                ", titles=" + titles +
                ", descriptions=" + descriptions +
                '}';
    }
}
