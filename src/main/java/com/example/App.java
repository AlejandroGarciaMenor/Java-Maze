package com.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.List;

public class App extends Application {
    private Maze maze;
    private Robot robot;

    public App() {
        this.maze = new Maze();
    }

    @Override
    public void start(Stage primaryStage) {
        List<Robot> robots = DatabaseManager.loadRobots();

        // Llama al selector de robots
        RobotSelector.showRobotSelector(robots, selectedRobot -> {
            this.robot = selectedRobot;

            // Configura el escenario principal después de seleccionar el robot
            Platform.runLater(() -> {
                Pane root = new Pane();
                Canvas canvas = new Canvas(940, 560);
                root.getChildren().add(canvas);
                Scene scene = new Scene(root);

                // Manejo de eventos de teclado
                scene.setOnKeyPressed(this::handleKeyPress);

                primaryStage.setTitle("Maze Game");
                primaryStage.setScene(scene);
                primaryStage.show();

                // Dibuja el laberinto y el robot
                GraphicsContext gc = canvas.getGraphicsContext2D();
                draw(gc);
            });
        });
    }

    private void handleKeyPress(KeyEvent event) {
        // Mover el robot basándote en el evento de la tecla presionada
        robot.move(event);
    
        // Obtener el GraphicsContext del Canvas
        Canvas canvas = (Canvas) ((Pane) ((Scene) event.getSource()).getRoot()).getChildren().get(0);
        GraphicsContext gc = canvas.getGraphicsContext2D();
    
        // Redibujar la escena
        draw(gc);
    }
    

    private void draw(GraphicsContext gc) {
        gc.clearRect(0, 0, 940, 560); // Limpia la pantalla
        maze.paint(gc); // Dibuja el laberinto
        robot.paint(gc); // Dibuja el robot
    }

    public static void main(String[] args) {
        launch(args);
    }
}
