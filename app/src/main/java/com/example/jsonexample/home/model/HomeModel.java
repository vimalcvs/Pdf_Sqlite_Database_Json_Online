package com.example.jsonexample.home.model;

import androidx.annotation.NonNull;

public class HomeModel {
    private final int idHome;
    private final String title;
    private final String imageURL;
    private final String pdfURL;
    private final String time;

    public HomeModel(int idHome, String title, String imageURL, String pdfURL, String time) {
        this.idHome = idHome;
        this.title = title;
        this.imageURL = imageURL;
        this.pdfURL = pdfURL;
        this.time = time;
    }

    @NonNull
    @Override
    public String toString() {
        return "Title: " + title + "\n" +
                "Pdf URL: " + pdfURL + "\n" +
                "Home Id: " + idHome + "\n" +
                "Time: " + time + "\n" +
                "Image URL: " + imageURL + "\n";
    }

    public String getTitle() {
        return title;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getPdfURL() {
        return pdfURL;
    }

    public int getIdHome() {
        return idHome;
    }

    public String getTime() {
        return time;
    }
}