package com.mc.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

/**
 * The InventorySystem class is responsible for initializing the application.
 * It extends the JavaFX Application class and overrides its start() method.
 * FUTURE ENHANCEMENT - One potential future enhancement to the program could be to add user authentication and
 * authorization functionality. This would involve creating user accounts with different levels of access and
 * permissions, and requiring users to log in to access certain parts of the program. This would increase security and
 * control over the data and actions within the program. Additionally, it could allow for auditing and tracking of
 * changes made by different users, which could be useful for accountability and troubleshooting purposes.
 * JAVADOC is located in the root of the archive of the project in the "Javadoc" folder.
 * @author Michael Cassidy
 */
public class InventorySystem extends Application {
    /**
     * This method is responsible for starting the JavaFX application by loading the MainMenu.fxml file and setting
     * the scene.
     * @param stage the stage to be displayed.
     * @throws IOException if the fxml file is not found.
     */
    @Override
    public void start(Stage stage) throws IOException {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(
                    "/com/mc/view/MainMenu.fxml")));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
    }

    /**
     * The main method launches the application.
     * @param args command line arguments.
     */
    public static void main(String[] args) { launch(); }
}
