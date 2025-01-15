package com.example;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Label;

import java.util.List;
import java.util.function.Consumer;

public class RobotSelector {

    // Method to show the robot selector window
    public static void showRobotSelector(List<Robot> robots, Consumer<Robot> onRobotSelected) {
        
        // We use the Platform.runLater method to run the code in the JavaFX thread
        Platform.runLater(() -> {

            // We create a new stage (window)
            Stage primaryStage = new Stage();
            primaryStage.setTitle("Select a Robot");

            // Create a VBox layout to hold the welcome message, instructions, ComboBox, and button
            VBox vbox = new VBox(10);
            vbox.setStyle("-fx-padding: 20; -fx-alignment: center;");

            // Create a welcome message label
            Label welcomeLabel = new Label("Welcome to the Maze Game!");
            welcomeLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

            // Create an instruction message label
            Label instructionLabel = new Label("Please select a robot from the list and press 'Start' to begin playing.");
            instructionLabel.setStyle("-fx-font-size: 14px;");

            // We create a ComboBox to display the list of robots
            ComboBox<Robot> robotComboBox = new ComboBox<>();
            robotComboBox.getItems().addAll(robots);

            // We create a button to select the robot
            Button selectButton = new Button("Select");
            selectButton.setOnAction(e -> {
                Robot selectedRobot = robotComboBox.getSelectionModel().getSelectedItem();
                if (selectedRobot != null) {
                    onRobotSelected.accept(selectedRobot);
                }
                primaryStage.close();
            });

            // Add the labels, ComboBox, and button to the VBox layout
            vbox.getChildren().addAll(welcomeLabel, instructionLabel, robotComboBox, selectButton);



            // Create a scene with the border pane layout and set it to the stage
            Scene scene = new Scene(vbox, 500, 300);
            primaryStage.setScene(scene);
            primaryStage.show();
        });
    }
}
