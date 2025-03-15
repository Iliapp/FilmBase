package com.example.filmbase.Model;

public class Film {
    private int id;
    private String icon;
    private String filmName;
    private String url;
    private String type;

    // Default constructor
    public Film() {}

    public Film(int id, String filmName, String url, String type) {
        this.id = id;
        this.filmName = filmName;
        this.url = url;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public String getFilmName() {
        return filmName;
    }

    public String getUrl() {
        return url;
    }

    public String getType() {
        return type;
    }
}
