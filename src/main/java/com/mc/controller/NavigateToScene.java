package com.mc.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The NavigateToScene class provides static methods for navigating between views in the application.
 * @author Michael Cassidy
 */
public abstract class NavigateToScene {
    /**
     * Navigates to the main menu view of the application.
     * @param event The action event that triggered the navigation.
     * @throws IOException If there is an error loading the FXML file for the main menu view.
     */
    public static void goToMainMenu(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(NavigateToScene.class.getResource("/com/mc/view/MainMenu.fxml"));
        Parent newViewParent = loader.load();

        // Get the current stage
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Set the new view as the scene of the current stage
        Scene newViewScene = new Scene(newViewParent);
        stage.setScene(newViewScene);
        stage.show();
    }

    /**
     * Navigates to a specified view of the application.
     * @param event The action event that triggered the navigation.
     * @param fileName The name of the FXML file for the view to navigate to.
     * @throws IOException If there is an error loading the FXML file for the specified view.
     */
    public static void goToScene(ActionEvent event, String fileName) throws IOException {
        FXMLLoader loader = new FXMLLoader(NavigateToScene.class.getResource("/com/mc/view/" + fileName + ".fxml"));
        Parent newViewParent = loader.load();

        // Get the current stage
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Set the new view as the scene of the current stage
        Scene newViewScene = new Scene(newViewParent);
        stage.setScene(newViewScene);
        stage.show();
    }
}
