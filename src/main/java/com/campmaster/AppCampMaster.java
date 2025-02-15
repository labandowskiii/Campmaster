package com.campmaster;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class AppCampMaster extends Application {
    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        setRoot("LogInView");
        primaryStage.setTitle("CampMaster");
        Image icon = new Image(getClass().getResourceAsStream("/com/campmaster/resources/icon.png"));
        primaryStage.getIcons().add(icon);
        primaryStage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AppCampMaster.class.getResource("/com/campmaster/resources/" + fxml + ".fxml"));
        Parent root = fxmlLoader.load();
        primaryStage.setScene(new Scene(root));
        primaryStage.centerOnScreen();
    }

    public static void main(String[] args) {
        System.setProperty("javafx.modules", "javafx.controls,javafx.fxml");
        launch(args);
    }
}