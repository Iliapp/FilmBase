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
        this.icon = getIcon(url);
    }

    private String getIcon(String url) {
        try {
            java.net.URL parsedUrl = new java.net.URL(url);
            return parsedUrl.getProtocol() + "://" + parsedUrl.getHost() + "/favicon.ico";
        } catch (Exception e) {
            return "/favicon.ico";
        }
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

    public String getIcon() {
        return icon;
    }
}
