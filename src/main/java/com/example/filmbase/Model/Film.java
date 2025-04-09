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
            URL parsedUrl = new URL(url);
            String result = "";


            if(parsedUrl.getHost().equals("jut.su")){
                HttpURLConnection con = (HttpURLConnection) parsedUrl.openConnection();
                con.setRequestMethod("GET");


                InputStream response = con.getInputStream();

                String responseBody;
                try (Scanner scanner = new Scanner(response)) {
                    responseBody = scanner.useDelimiter("\\A").next();
                }

                Document doc = Jsoup.parse(responseBody);

                Element div = doc.selectFirst("div.all_anime_title");

                if (div != null) {
                    String style = div.attr("style");

                    Pattern pattern = Pattern.compile("url\\(['\"]?(.*?)['\"]?\\)");
                    Matcher matcher = pattern.matcher(style);

                    if (matcher.find()) {
                        result = matcher.group(1);
                    }
                }

                con.disconnect();


                System.out.println("JUT");
            } else if(parsedUrl.getHost().equals("uakino.me")){;
                HttpURLConnection con = (HttpURLConnection) parsedUrl.openConnection();
                con.setRequestMethod("GET");

                InputStream response = con.getInputStream();

                String responseBody;
                try (Scanner scanner = new Scanner(response)) {
                    responseBody = scanner.useDelimiter("\\A").next();
                }

                Document doc = Jsoup.parse(responseBody);
                Element imgElement = doc.selectFirst("div.film-poster img");

                if (imgElement != null) {
                    String src = imgElement.attr("src");
                    System.out.println("Image URL: " + src);

                    result = parsedUrl.getProtocol() + "://" + parsedUrl.getHost() + src;
                }


                con.disconnect();
            } else {
                System.out.println("EXIT");
                result = parsedUrl.getProtocol() + "://" + parsedUrl.getHost() + "/favicon.ico";
            }

            return result;
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
