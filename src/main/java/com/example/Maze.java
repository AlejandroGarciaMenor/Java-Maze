package com.example;

import javafx.scene.canvas.GraphicsContext;     // Packet and class to paint on the canvas
import javafx.scene.paint.Color;  // Packet and class to handle colors

public class Maze {

    // Maze dimensions and block size
    private int row = 0;
    private int column = 0;
    private final int numRows = 13;
    private final int numColumns = 23;
    private final int widthBlock = 40;
    private final int heightBlock = 40;

    // Method to paint the maze on the canvas
    public void paint(GraphicsContext gc) {
        int[][] maze = createMaze();
        Color blockColor = Color.rgb(41, 171, 135);

        for (row = 0; row < numRows; row++) {
            for (column = 0; column < numColumns; column++) {
                if (maze[row][column] == 1) {   // Paint the block if it is a wall
                    gc.setFill(blockColor);
                    gc.fillRect(column * widthBlock, row * heightBlock, widthBlock, heightBlock);
                    gc.setStroke(Color.BLACK);
                    gc.strokeRect(column * widthBlock, row * heightBlock, widthBlock, heightBlock);
                } else {
                    gc.setFill(Color.WHITE);    // Paint the block as white if it is a path
                    gc.fillRect(column * widthBlock, row * heightBlock, widthBlock, heightBlock);
                }
            }
        }
    }

    // Method to create the maze usifng a 2D array
    public int[][] createMaze() {
        int[][] maze = {
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 0, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1},
            {1, 1, 1, 0, 1, 1, 1, 0, 0, 0, 0, 1, 0, 0, 1, 0, 1, 1, 0, 1, 0, 1, 1},
            {1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1},
            {1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 1, 0, 1, 1, 1, 1, 0, 1, 1},
            {1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1, 1},
            {1, 1, 1, 0, 1, 1, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 1, 1},
            {1, 0, 0, 0, 1, 1, 0, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 0, 1, 1, 0, 1, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1},
            {1, 0, 0, 1, 0, 1, 0, 0, 0, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1, 1, 1},
            {1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1, 1, 0, 1, 0, 0, 0, 1, 0, 1, 1, 1},
            {1, 1, 1, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
        };
        return maze;
    }
}