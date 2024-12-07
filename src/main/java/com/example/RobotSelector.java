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
    public static void showRobotSelector(List<Robot> robots, Consumer<Robot> onRobotSelected) {
        Platform.runLater(() -> {
            Stage primaryStage = new Stage();
            primaryStage.setTitle("Select a Robot");

            ComboBox<Robot> robotComboBox = new ComboBox<>();
            robotComboBox.getItems().addAll(robots);

            Button selectButton = new Button("Select");
            selectButton.setOnAction(e -> {
                Robot selectedRobot = robotComboBox.getSelectionModel().getSelectedItem();
                if (selectedRobot != null) {
                    onRobotSelected.accept(selectedRobot);
                }
                primaryStage.close();
            });

            BorderPane root = new BorderPane();
            root.setCenter(robotComboBox);
            root.setBottom(selectButton);

            Scene scene = new Scene(root, 300, 200);
            primaryStage.setScene(scene);
            primaryStage.show();
        });
    }
}
