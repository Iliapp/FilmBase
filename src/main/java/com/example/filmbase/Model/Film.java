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
    private int episode;
    private String time;

    public Film() {}

    public Film(int id, String filmName, String url, String type, String icon,  int episode, String time) {
        this.id = id;
        this.filmName = filmName;
        this.url = url;
        this.type = type;
        this.icon = icon;
        this.episode = episode;
        this.time = time;
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

    public int getEpisode() {return episode; }

    public void setEpisode(int episode) { this.episode = episode; }

    public String getTime() {return time;}

    public void setTime(String time) { this.time = time; }
}
