package com.doanAndroid.myapplication;

public class Film {
    private String title;
    private int image;
    private int timeFilm;
    private int theloai;
    private String date;
    private int soghe;
    private float price;

    public Film(String title, int image, int timeFilm, int theloai,String date , int soghe,float price) {
        this.title = title;
        this.image = image;
        this.timeFilm = timeFilm;
        this.theloai = theloai;
        this.date = date;
        this.soghe = soghe;
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public int getImage() {
        return image;
    }

    public int getTimeFilm() {
        return timeFilm;
    }

    public void setTimeFilm(int timeFilm) {
        this.timeFilm = timeFilm;
    }

    public int getTheloai() {
        return theloai;
    }

    public void setTheloai(int theloai) {
        this.theloai = theloai;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getSoghe() {
        return soghe;
    }

    public void setSoghe(int soghe) {
        this.soghe = soghe;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}