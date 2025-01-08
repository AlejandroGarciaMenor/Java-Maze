package com.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.util.List;

public class App extends Application {
    private Maze maze;
    private Robot robot;
    private Label timeLabel;

    // Constructor to initialize the maze
    public App() {
        this.maze = new Maze();
    }

    @Override
    public void start(Stage primaryStage) {

        // We first load the robots from the database into a list
        List<Robot> robots = DatabaseManager.loadRobots();

        // Call the robot selector window to select a robot
        RobotSelector.showRobotSelector(robots, selectedRobot -> {
            
            this.robot = selectedRobot;

            // After selecting the robot, we configure the main stage
            Platform.runLater(() -> {
                
                Pane root = new Pane(); // Container that has other nodes, like the Canvas
                Canvas canvas = new Canvas(940, 560);   // Node that allows us to draw graphics in a scene
                root.getChildren().add(canvas);
                
                // Create a label to display the time
                timeLabel = new Label("Time:");
                timeLabel.setLayoutX(10);
                timeLabel.setLayoutY(530);
                timeLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
                root.getChildren().add(timeLabel);                
                
                Scene scene = new Scene(root); // Container for all content in a scene graph

                // Handle keyboard events
                scene.setOnKeyPressed(this::handleKeyPress);

                primaryStage.setTitle("Maze Game");
                primaryStage.setScene(scene);
                primaryStage.show();

                // Draw the maze and the robot
                GraphicsContext gc = canvas.getGraphicsContext2D();
                draw(gc);
            });
        });
    }

    private void handleKeyPress(KeyEvent event) {
        // We call the move method of the robot to handle the key event
        robot.move(event);
    
        // Get the GraphicsContext from the Canvas
        Canvas canvas = (Canvas) ((Pane) ((Scene) event.getSource()).getRoot()).getChildren().get(0);
        GraphicsContext gc = canvas.getGraphicsContext2D();
    
        // Redraw the scene
        draw(gc);

        // Update the time label
        if (!robot.isTiming()) {
            timeLabel.setText("Time: " + robot.getTotalTime()/1000 + " s");

            // Show a popup congratulating the player
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Congratulations!");
            alert.setHeaderText(null);
            alert.setContentText("You have completed the maze in " + robot.getTotalTime() / 1000 + " seconds!");
            alert.showAndWait();
        }
    }
    
    // Method to draw the maze and the robot
    private void draw(GraphicsContext gc) {
        gc.clearRect(0, 0, 940, 560); // Clear the canvas
        maze.paint(gc); // Draw the maze calling the paint method of the Maze class
        robot.paint(gc); // Draw the robot calling the paint method of the Robot class
    }

    // Main method to launch the application
    public static void main(String[] args) {
        launch(args);
    }
}
