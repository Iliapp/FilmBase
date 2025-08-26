    package com.example.filmbase.Manager;


    import com.example.filmbase.Model.Film;
    import org.jsoup.Jsoup;
    import org.jsoup.nodes.Document;
    import org.jsoup.nodes.Element;

    import java.io.InputStream;
    import java.net.HttpURLConnection;
    import java.net.URL;
    import java.nio.file.Files;
    import java.nio.file.Path;
    import java.nio.file.Paths;
    import java.sql.*;
    import java.util.ArrayList;
    import java.util.List;
    import java.util.Scanner;
    import java.util.regex.Matcher;
    import java.util.regex.Pattern;

    public class DataBaseManager {
        private String dataBaseStructure =
                "CREATE TABLE IF NOT EXISTS films (" +
                        "   id INTEGER PRIMARY KEY, " +
                        "   name TEXT, " +
                        "   url TEXT NOT NULL, " +
                        "   icon TEXT, " +
                        "   type TEXT NOT NULL," +
                        "   episode INTEGER," +
                        "   time TEXT" +
                        ");";


        private String dataBaseName = "database.db";

        private Path resourceFolder = Paths.get("src/main/resources/database");
        private Path databaseFile = resourceFolder.resolve(dataBaseName);
        private final String connectionUrl = "jdbc:sqlite:" + databaseFile.toAbsolutePath();


        public DataBaseManager() {
            initDataBase();
        }

        private void initDataBase() {
            try {
                if (Files.notExists(resourceFolder)) {
                    Files.createDirectories(resourceFolder);
                }

                if (Files.notExists(databaseFile)) {
                    try (Connection connection = DriverManager.getConnection("jdbc:sqlite:" + databaseFile.toAbsolutePath())) {
                        try (Statement statement = connection.createStatement()) {
                            statement.execute(dataBaseStructure);
                        }
                    }
                    System.out.println("Database created and initialized at: " + databaseFile.toAbsolutePath());
                } else {
                    System.out.println("Database exists: " + databaseFile.toAbsolutePath());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        public void addFilm(String name, String url, String type) {
            String query = "INSERT INTO films (name, url, type, icon) VALUES (?, ?, ?, ?)";

            try (Connection connection = DriverManager.getConnection(connectionUrl);
                 PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, url);
                preparedStatement.setString(3, type);
                preparedStatement.setString(4, getIcon(url));
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


        public void deleteFilm(int id) {
            String query = "DELETE FROM films WHERE id = ?";
            try (Connection connection = DriverManager.getConnection(connectionUrl);
                 PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, id);
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Film with ID " + id + " deleted.");
                } else {
                    System.out.println("Film with ID " + id + " not found.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public List<Film> getAllFilmInfo(){
            List<Film> films = new ArrayList<>();
            String query = "SELECT * FROM films";
            try (Connection connection = DriverManager.getConnection(connectionUrl);
                 Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {

                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    String icon = resultSet.getString("icon");
                    String url = resultSet.getString("url");
                    String type = resultSet.getString("type");
                    int episode = resultSet.getInt("episode");
                    String time = resultSet.getString("time");
                    films.add(new Film(id, name, url, type, icon,episode,time));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return films;
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


                } else if(parsedUrl.getHost().equals("uakino.best")){;
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
                    result = parsedUrl.getProtocol() + "://" + parsedUrl.getHost() + "/favicon.ico";
                }

                return result;
            } catch (Exception e) {
                return "/favicon.ico";
            }
        }


        public void updateProgress(int id, int episode, String time) {
            String query = "UPDATE films SET episode = ?, time = ? WHERE id = ?";
            try (Connection connection = DriverManager.getConnection(connectionUrl);
                 PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, episode);
                preparedStatement.setString(2, time);
                preparedStatement.setInt(3, id);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


    }
