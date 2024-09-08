package com.doanAndroid.myapplication;

public class CategoryFilm {
    private int Id;
    private String name;
    private int image;

    public CategoryFilm(int Id,String name, int image) {
        this.Id = Id;
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }
}
