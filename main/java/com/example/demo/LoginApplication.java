package com.example.demo;


import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LoginApplication extends Application {

    private Map<String, String> users = new HashMap<>();
    private Label errorMessage;

    @Override
    public void start(Stage primaryStage) {
        loadUsersFromFile("/C:/Users/amina/Documents/OOPS!!!/LAB/demo/src/main/java/com/example/demo/users.txt");

        VBox root = new VBox(10);
        root.setPadding(new Insets(20));
        root.setSpacing(10);
        root.setAlignment(Pos.CENTER);

        root.setStyle("-fx-border-width: 5; -fx-border-color: BLACK; -fx-border-radius: 0;");


        ImageView imageView = new ImageView();
        imageView.setFitWidth(400);
        imageView.setFitHeight(400);
        imageView.setStyle("--border-color: BLACK; -fx-border-width: 10;");
        imageView.setPreserveRatio(true);
        imageView.setImage(new Image("C:\\Users\\amina\\Documents\\OOPS!!!\\LAB\\demo\\src\\main\\resources\\logi.jpg"));

        root.getChildren().add(imageView);


        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setAlignment(Pos.CENTER);

        Label usernameLabel = new Label("User Name");
        TextField usernameField = new TextField();

        Label passwordLabel = new Label("Password");
        PasswordField passwordField = new PasswordField();

        gridPane.add(usernameLabel, 0, 0);
        gridPane.add(usernameField, 1, 0);
        gridPane.add(passwordLabel, 0, 1);
        gridPane.add(passwordField, 1, 1);


        root.getChildren().add(gridPane);


        errorMessage = new Label();
        errorMessage.setStyle("-fx-text-fill: red;");
        root.getChildren().add(errorMessage);


        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);

        Button loginButton = new Button("Login");
        Button exitButton = new Button("Exit");

        exitButton.setOnAction(e -> primaryStage.close());
        loginButton.setOnAction(e -> handleLogin(usernameField.getText(), passwordField.getText(), primaryStage));

        buttonBox.getChildren().addAll(loginButton, exitButton);
        root.getChildren().add(buttonBox);


        Scene scene = new Scene(root, 700, 600);
        primaryStage.setTitle("Login Screen");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void loadUsersFromFile(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    users.put(parts[0], parts[1]);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading users: " + e.getMessage());
        }
    }

    private void handleLogin(String username, String password, Stage primaryStage) {

        errorMessage.setText("");

        if (!users.containsKey(username)) {
            errorMessage.setText("Username does not exist!");
            return;
        }

        if (!users.get(username).equals(password)) {
            errorMessage.setText("Invalid password!");
            return;
        }

        showLoggedInScreen(username, password, primaryStage);
    }

    private void showLoggedInScreen(String username, String password, Stage primaryStage) {
        VBox root = new VBox();
        root.setPadding(new Insets(20));
        root.setSpacing(10);
        root.setAlignment(Pos.CENTER);

        Label welcomeLabel = new Label("Welcome, " + username + "!");
        Label userDetailsLabel = new Label("Your credentials:");
        Label usernameLabel = new Label("Username: " + username);
        Label passwordLabel = new Label("Password: " + password);

        root.getChildren().addAll(welcomeLabel, userDetailsLabel, usernameLabel, passwordLabel);

        Button logoutButton = new Button("Logout");
        logoutButton.setOnAction(e -> start(primaryStage));

        root.getChildren().add(logoutButton);

        Scene scene = new Scene(root, 700, 600);
        primaryStage.setScene(scene);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
