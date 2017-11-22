package ru.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class MainApp extends Application {

    public static void main(String[] args) {
//        ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        Parent root = null;
        try {
            root = FXMLLoader.load(MainApp.class.getClassLoader().getResource("Main.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root,800,600);
        primaryStage.setScene(scene);
        primaryStage.setMaxHeight(720);
        primaryStage.setMaxWidth(1024);
        primaryStage.setMinHeight(220);
        primaryStage.setMinWidth(360);
        primaryStage.show();
    }
}
