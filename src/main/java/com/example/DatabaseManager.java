package com.example;

// Package sql and its classes to connect to the database and make queries
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private static final String URL = "jdbc:mysql://localhost:3306/robotdb"; 
    private static final String USER = "root";
    private static final String PASSWORD = "root"; 

    // Method to load the robots from the database
    public static List<Robot> loadRobots() {

        List<Robot> robots = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM robots")) {
                System.out.println("Database connection established");
            
            // We iterate over the result set to create Robot objects for each row of the table in the database
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int width = resultSet.getInt("width");
                int length = resultSet.getInt("length");
                int bodyColorInt = resultSet.getInt("bodyColor");
                int headColorInt = resultSet.getInt("headColor");
                int eyeColorInt = resultSet.getInt("eyeColor");
                int armColorInt = resultSet.getInt("armColor");
                int legColorInt = resultSet.getInt("legColor");

                // We extract the RGB values from the integer values stored in the database
                Color bodyColor = Color.rgb((bodyColorInt >> 16) & 0xFF, (bodyColorInt >> 8) & 0xFF, bodyColorInt & 0xFF);
                Color headColor = Color.rgb((headColorInt >> 16) & 0xFF, (headColorInt >> 8) & 0xFF, headColorInt & 0xFF);
                Color eyeColor = Color.rgb((eyeColorInt >> 16) & 0xFF, (eyeColorInt >> 8) & 0xFF, eyeColorInt & 0xFF);
                Color armColor = Color.rgb((armColorInt >> 16) & 0xFF, (armColorInt >> 8) & 0xFF, armColorInt & 0xFF);
                Color legColor = Color.rgb((legColorInt >> 16) & 0xFF, (legColorInt >> 8) & 0xFF, legColorInt & 0xFF);
                
                String bodyShape = resultSet.getString("bodyShape");
                
                // Add the new Robot object to the list
                robots.add(new Robot(id, name, width, length, bodyColor, headColor, eyeColor, armColor, legColor, bodyShape));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Robots loaded from database: " + robots.size());
        return robots;
    }

    // Method to update the score of a robot in the database
    public static void updateRobotScore(int robotId, long score) {
        String updateScoreSQL = "UPDATE robots SET score = ? WHERE id = ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(updateScoreSQL)) {

            preparedStatement.setLong(1, score);
            preparedStatement.setInt(2, robotId);

            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

