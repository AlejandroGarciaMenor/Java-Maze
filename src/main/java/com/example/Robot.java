package com.example;

import javafx.scene.canvas.GraphicsContext; // Packet and class to paint on the canvas
import javafx.scene.input.KeyEvent; // Packet and class to handle key events
import javafx.scene.paint.Color;    // Packet and class to handle colors
import javafx.scene.control.Label;  // Packet and class to handle labels

public class Robot {
    // We create an instance of the Maze class to access the createMaze method
    private int id;
    private String name;

    // Robot attributes
    private Maze maze = new Maze();    
    private int x_position = 0;
    private int y_position = 0;
    private int movement = 40;
    private int width;
    private int length;
    private Color headColor;
    private Color bodyColor;
    private Color eyeColor;
    private Color armColor;
    private Color legColor;
    private String bodyShape;

    // Attributes to record the time
    private long startTime;
    private long endTime;
    private boolean isTiming;
    private Label timeLabel;

    // Constructor to initialize the robot with its attributes
    public Robot(int id, String name, int width, int length, Color bodyColor, Color headColor, Color eyeColor, Color armColor, Color legColor, String bodyShape) {
        this.id = id;
        this.name = name;
        this.width = width;
        this.length = length;
        this.headColor = headColor;
        this.bodyColor = bodyColor;
        this.eyeColor = eyeColor;
        this.armColor = armColor;
        this.legColor = legColor;
        this.bodyShape = bodyShape;
        this.isTiming = false;
    }

    // Method to set the time label
    public void setTimeLabel(Label timeLabel) {
        this.timeLabel = timeLabel;
    }

    // Override the toString method to return the name of the robot
    @Override
    public String toString() {
        return name;
    }

    // Method to paint the robot on the canvas
    public void paint(GraphicsContext gc) {
        int bodyWidth = width / 2;
        int bodyHeight = length / 2;
        int headSize = width / 4;
        int eyeSize = width / 8;
        int armWidth = width / 8;
        int armHeight = length / 2;
        int legWidth = width / 8;
        int legHeight = length / 2;

        // Body
        gc.setFill(bodyColor);
        if (bodyShape.equals("rectangle")) {
            gc.fillRect(x_position + width / 4, y_position + length / 4, bodyWidth, bodyHeight);
        } else if (bodyShape.equals("oval")) {
            gc.fillOval(x_position + width / 4, y_position + length / 4, bodyWidth, bodyHeight);
        }

        // Head
        gc.setFill(headColor);
        gc.fillRect(x_position + width / 2 - headSize / 2, y_position, headSize, headSize);

        // Eyes
        gc.setFill(eyeColor);
        gc.fillOval(x_position + width / 2 - headSize / 4, y_position + headSize / 4, eyeSize, eyeSize);
        gc.fillOval(x_position + width / 2, y_position + headSize / 4, eyeSize, eyeSize);

        // Arms
        gc.setFill(armColor);
        gc.fillRect(x_position + width / 4 - armWidth, y_position + length / 4, armWidth, armHeight);
        gc.fillRect(x_position + width / 4 + bodyWidth, y_position + length / 4, armWidth, armHeight);

        // Legs
        gc.setFill(legColor);
        gc.fillRect(x_position + width / 4 + bodyWidth / 4, y_position + length / 4 + bodyHeight, legWidth, legHeight);
        gc.fillRect(x_position + width / 4 + bodyWidth / 2, y_position + length / 4 + bodyHeight, legWidth, legHeight);
    }

    // Method to move the robot based on the key pressed. It will verify if there is a wall in the direction the robot is moving
    public void move(KeyEvent event) {
        int[][] mazeArray = maze.createMaze();

        if (!isTiming) {
            startTime = System.currentTimeMillis();
            isTiming = true;
        }

        switch (event.getCode()) {
            case LEFT:
                if (mazeArray[y_position / movement][(x_position - movement) / movement] == 0) {
                    x_position -= movement;
                }
                break;
            case UP:
                if (mazeArray[(y_position - movement) / movement][x_position / movement] == 0) {
                    y_position -= movement;
                }
                break;
            case RIGHT:
                if (mazeArray[y_position / movement][(x_position + movement) / movement] == 0) {
                    x_position += movement;
                }
                break;
            case DOWN:
                if (mazeArray[(y_position + movement) / movement][x_position / movement] == 0) {
                    y_position += movement;
                }
                break;
        }

        // Check if the robot has reached the final block
        if (hasReachedEnd(mazeArray)) {
            endTime = System.currentTimeMillis();
            long totalTime = endTime - startTime;
            if (timeLabel != null) {
                timeLabel.setText("Total time: " + totalTime + " ms");
            }
            DatabaseManager.updateRobotScore(id, totalTime); // Update the score in the database
            isTiming = false;
        }
    }

    // Method to check if the robot has reached the final block
    private boolean hasReachedEnd(int[][] mazeArray) {
        // Assuming the final block is at the bottom-right corner of the maze
        int finalX = (mazeArray[0].length - 1) * movement;
        int finalY = (mazeArray.length - 1) * movement;
        return x_position == finalX && y_position == finalY;
    }

    // Method to get the total time
    public long getTotalTime() {
        return endTime - startTime;
    }

    // Method to check if the timing is still active
    public boolean isTiming() {
        return isTiming;
    }
}