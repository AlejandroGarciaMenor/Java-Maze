package com.example;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

public class Robot {
    private Maze maze = new Maze();
    private int id;
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

    public void paint(GraphicsContext gc) {
        int bodyWidth = width / 2;
        int bodyHeight = length / 2;
        int headSize = width / 4;
        int eyeSize = width / 8;
        int armWidth = width / 8;
        int armHeight = length / 2;
        int legWidth = width / 8;
        int legHeight = length / 2;

        // Draw the body of the robot
        gc.setFill(bodyColor);
        if (bodyShape.equals("rectangle")) {
            gc.fillRect(x_position + width / 4, y_position + length / 4, bodyWidth, bodyHeight);
        } else if (bodyShape.equals("oval")) {
            gc.fillOval(x_position + width / 4, y_position + length / 4, bodyWidth, bodyHeight);
        }

        // Draw the head of the robot
        gc.setFill(headColor);
        gc.fillRect(x_position + width / 2 - headSize / 2, y_position, headSize, headSize);

        // Draw the eyes of the robot
        gc.setFill(eyeColor);
        gc.fillOval(x_position + width / 2 - headSize / 4, y_position + headSize / 4, eyeSize, eyeSize);
        gc.fillOval(x_position + width / 2, y_position + headSize / 4, eyeSize, eyeSize);

        // Draw the arms of the robot
        gc.setFill(armColor);
        gc.fillRect(x_position + width / 4 - armWidth, y_position + length / 4, armWidth, armHeight);
        gc.fillRect(x_position + width / 4 + bodyWidth, y_position + length / 4, armWidth, armHeight);

        // Draw the legs of the robot
        gc.setFill(legColor);
        gc.fillRect(x_position + width / 4 + bodyWidth / 4, y_position + length / 4 + bodyHeight, legWidth, legHeight);
        gc.fillRect(x_position + width / 4 + bodyWidth / 2, y_position + length / 4 + bodyHeight, legWidth, legHeight);
    }

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