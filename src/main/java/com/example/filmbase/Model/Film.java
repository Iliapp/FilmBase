package com.example.filmbase.Model;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


// Parser
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;


public class Film {
    private int id;
    private String icon;
    private String filmName;
    private String url;
    private String type;

    public Film() {}

    public Film(int id, String filmName, String url, String type, String icon) {
        this.id = id;
        this.filmName = filmName;
        this.url = url;
        this.type = type;
        this.icon = icon;
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
