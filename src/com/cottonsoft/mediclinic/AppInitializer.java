package com.cottonsoft.mediclinic;

import com.cottonsoft.mediclinic.enums.UIPanes;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class AppInitializer extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        URL resource = getClass().getResource(UIPanes.LOGIN_FORM.getUiPath());
        Object load = FXMLLoader.load(resource);
        Scene scene = new Scene((Parent) load);
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        primaryStage.show();

    }
}
