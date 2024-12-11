package com.example;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

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

            // Create a border pane layout and add the combo box and button
            BorderPane root = new BorderPane();
            root.setCenter(robotComboBox);
            root.setBottom(selectButton);

            // Create a scene with the border pane layout and set it to the stage
            Scene scene = new Scene(root, 300, 200);
            primaryStage.setScene(scene);
            primaryStage.show();
        });
    }
}
