package com.example.filmbase.Manager;


import com.example.filmbase.Model.Film;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataBaseManager {
    private String dataBaseStructure =
            "CREATE TABLE IF NOT EXISTS films (" +
                    "   id INTEGER PRIMARY KEY, " +
                    "   name TEXT, " +
                    "   url TEXT NOT NULL, " +
                    "   type TEXT NOT NULL" +
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
        System.out.println("Adding film: " + name + " " + url + " " + type);
        String query = "INSERT INTO films (name, url, type) VALUES (?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(connectionUrl);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, url);
            preparedStatement.setString(3, type);
            preparedStatement.executeUpdate();
            System.out.println("Added " + type + ": " + name);
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
                String url = resultSet.getString("url");
                String type = resultSet.getString("type");
                System.out.println("GET " + id + " " + name + " " + url + " " + type);
                films.add(new Film(id, name, url, type));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return films;
    }


}
