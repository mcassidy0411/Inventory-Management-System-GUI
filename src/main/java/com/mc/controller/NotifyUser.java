package com.mc.controller;

import com.mc.model.Part;
import com.mc.model.Product;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 * This abstract class contains methods for displaying different types of notifications to the user.
 * @author Michael Cassidy
 */
public abstract class NotifyUser {
    private static final Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
    private static final Alert warningAlert = new Alert(Alert.AlertType.WARNING);

    /**
     * An alert box used to confirm a user action.
     */
    private static void warningPanel(String header, String content) {
        warningAlert.getDialogPane().getButtonTypes().remove(ButtonType.CANCEL);
        warningAlert.setTitle(header);
        warningAlert.setHeaderText(null);
        warningAlert.setContentText(content);
        warningAlert.showAndWait();
    }

    /**
     * An alert box used to warn the user of an error.
     */
    private static void confirmationPanel(String header, String content) {
        confirmationAlert.getDialogPane().getButtonTypes().remove(ButtonType.CANCEL);
        confirmationAlert.setTitle(header);
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText(content);
        confirmationAlert.showAndWait();
    }

    /**
     * Displays a confirmation message when an object is successfully deleted.
     * @param object the object that was deleted
     */
    public static void notifyDeleted(Object object) {
        String type = "";
        int id = 0;
        String name = "";

        if(object instanceof Part) {
            type = "Part";
            id = ((Part) object).getId();
            name = ((Part) object).getName();
        } else if (object instanceof Product) {
            type = "Product";
            id = ((Product) object).getId();
            name = ((Product) object).getName();
        }
        String header = type + " Successfully Deleted";
        String content = type + " ID " + id + " - " + name + " has been successfully deleted.";
        confirmationPanel(header, content);
    }

    /**
     * Displays a warning message when an object cannot be deleted.
     * @param object the object that could not be deleted
     */
    public static void notifyNotDeleted(Object object) {
        String type = "";
        int id = 0;
        String name = "";

        if(object instanceof Part) {
            type = "Part";
            id = ((Part) object).getId();
            name = ((Part) object).getName();
        } else if (object instanceof Product) {
            type = "Product";
            id = ((Product) object).getId();
            name = ((Product) object).getName();
        }
        String header = type + "Unable to Delete";
        String content = "Unable to delete " + type + " ID " + id + " - " + name;
        warningPanel(header, content);
    }

    /**
     * Displays a warning message when no object of a certain type is selected.
     * @param type the type of object that was not selected
     * @param action the action the user was attempting to perform
     */
    public static void notifyNotSelected(String type, String action) {
        String header = "No " + type + " Selected";
        String content = "Please select a " + type.toLowerCase() + " to " + action.toLowerCase() + ".";
        warningPanel(header, content);
    }

    /**
     * Displays a warning message when input is invalid.
     * @param content the content of the warning message
     */
    public static void notifyNotValid(String content) {
        String header = "Validation Error";
        warningPanel(header, content);
    }

    /**
     * Displays a confirmation message when an object is successfully modified.
     * @param object the object that was modified
     */
    public static void notifyModified(Object object) {
        String type = "";
        int id = 0;
        String name = "";

        if(object instanceof Part) {
            type = "Part";
            id = ((Part) object).getId();
            name = ((Part) object).getName();
        } else if (object instanceof Product) {
            type = "Product";
            id = ((Product) object).getId();
            name = ((Product) object).getName();
        }
        String header = type + " Successfully Modified";
        String content = type + " ID " + id + " - " + name + " has been successfully modified.";
        confirmationPanel(header, content);
    }

    /**
     * Notifies the user that an object has been successfully saved.
     * @param type the type of object that has been saved (e.g. "Part", "Product")
     * @param id the ID of the saved object
     * @param name the name of the saved object
     */
    public static void notifySaved(String type, int id, String name) {
        String header = type + " Successfully Saved";
        String content = type + " ID " + id + " - " + name + " has been successfully saved.";
        confirmationPanel(header, content);
    }

    /**
     * Displays a warning message notifying the user that the integer input is out of bounds.
     * The warning message includes a message informing the user that integer inputs must be between 0 and 999999999.
     */
    public static void notifyIntOutOfBounds() {
        warningPanel("Input Exceeds Maximum Allowed Value",
                "Integer inputs must be between 0 and 999999999.");
    }

    /**
     Displays a warning message indicating that it is unable to delete an associated part.
     */
    public static void notifyUnableToDeleteAssoPart () {
        warningPanel("Unable to Delete Associated Part", "Unable to delete associated part.");
    }

    /**
     * Displays a warning message that the selected product can't be deleted as it still has associated parts
     * attached.
     */
    public static void notifyUnableToDeleteProduct() {
        warningPanel("Unable to Delete Product",
                "Unable to delete product; please remove all associated parts and try again.");
    }

    /**
     * Notifies the user that an associated part was successfully removed
     * @param part the part that was removed
     */
    public static void notifyPartRemoved(Part part) {
        confirmationPanel("Associated Part Removed" ,
                "Associated Part ID " + part.getId() + " - " + part.getName() + " removed." );
    }
}
