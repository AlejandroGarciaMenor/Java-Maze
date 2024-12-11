package com.example;

import javafx.scene.canvas.GraphicsContext; // Packet and class to paint on the canvas
import javafx.scene.input.KeyEvent; // Packet and class to handle key events
import javafx.scene.paint.Color;    // Packet and class to handle colors

public class Robot {
    // We create an instance of the Maze class to access the createMaze method
    private int id;

    // Robot attributes
    private Maze maze = new Maze();    
    private int x_position = 40;
    private int y_position = 40;
    private int movement = 40;
    private int width;
    private int length;
    private Color headColor;
    private Color bodyColor;
    private Color eyeColor;
    private Color armColor;
    private Color legColor;
    private String bodyShape;

    // Constructor to initialize the robot with its attributes
    public Robot(int id, int width, int length, Color bodyColor, Color headColor, Color eyeColor, Color armColor, Color legColor, String bodyShape) {
        this.id = id;
        this.width = width;
        this.length = length;
        this.headColor = headColor;
        this.bodyColor = bodyColor;
        this.eyeColor = eyeColor;
        this.armColor = armColor;
        this.legColor = legColor;
        this.bodyShape = bodyShape;
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
    }
}