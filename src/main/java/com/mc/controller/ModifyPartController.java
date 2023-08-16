package com.mc.controller;

import com.mc.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

import java.io.IOException;

/**
 * The controller class for the Modify Part screen. It handles user input and updates the Part data in the Inventory
 * system accordingly. It also provides error checking for user input and displays error messages when necessary.
 * @author Michael Cassidy
 */
public class ModifyPartController {
    @FXML
    private TextField dynField;
    @FXML
    private Label dynLabel;
    @FXML
    private TextField idField;
    @FXML
    private RadioButton inHouseBtn;
    @FXML
    private TextField invField;
    @FXML
    private TextField maxField;
    @FXML
    private TextField minField;
    @FXML
    private TextField nameField;
    @FXML
    private RadioButton outsourcedBtn;
    @FXML
    private TextField priceField;
    private Part part;
    private int index;

    /**
     * This method is called by JavaFX after the FXML file has been loaded and the root node has been
     * processed. It initializes the in-house and outsourced radio buttons and sets the appropriate
     * label for the text field based on the currently selected radio button.
     */
    @FXML
    private void initialize() {
        if (inHouseBtn.isSelected()) {
            dynLabel.setText("Machine ID");
        } else if (outsourcedBtn.isSelected()) {
            dynLabel.setText("Company Name");
        }
    }

    /**
     * Sets the selected part and its information to be modified.
     * @param part The part to be modified
     * @param index The index of the selected part in the table
     */
    public void setPart(Part part, int index) {
        this.index = index;
        this.part = part;
        idField.setText(Integer.toString(part.getId()));
        nameField.setText(part.getName());
        priceField.setText(Double.toString(part.getPrice()));
        invField.setText(Integer.toString(part.getStock()));
        minField.setText(Integer.toString(part.getMin()));
        maxField.setText(Integer.toString(part.getMax()));

        if(part instanceof InHouse) {
            inHouseBtn.setSelected(true);
            dynLabel.setText("Machine ID");
            dynField.setText(Integer.toString(((InHouse) part).getMachineId()));
        } else if(part instanceof Outsourced) {
            outsourcedBtn.setSelected(true);
            dynLabel.setText("Company Name");
            dynField.setText(((Outsourced) part).getCompanyName());
        }
    }

    /**
     * Handles the functionality of the Modify Part screen.
     * Allows the user to modify an existing part and saves the changes to the inventory.
     * Validates user input to ensure correct formatting and data types are used.
     * Displays error messages to the user if input is not valid.
     * @param event The event that triggers the method.
     * @throws IOException if an error occurs while loading the main menu screen.
     */
    @FXML
    private void onActionSave(ActionEvent event) throws IOException {
        int id = part.getId();
        String name = nameField.getText();
        String errorMessage = "";
        double price = 0;
        int inv = 0;
        int min = 0;
        int max = 0;
        boolean isValid = true;
        try {
            price = Double.parseDouble(priceField.getText());
            if(price < 0) { throw new NumberFormatException(); }
        } catch (NumberFormatException e) {
            errorMessage = "Price must be positive and in numeric format.\n";
            isValid = false;
        }
        try {
            inv = Integer.parseInt(invField.getText());
            if(inv < 0) { throw new NumberFormatException(); }
        } catch (NumberFormatException e) {
            errorMessage = errorMessage + "Inventory must be an integer between 0 and 999999999.\n";
            isValid = false;
        }
        try {
            min = Integer.parseInt(minField.getText());
            if(min < 0) { throw new NumberFormatException(); }
        } catch (NumberFormatException e) {
            errorMessage = errorMessage + "Min must be an integer between 0 and 999999999.\n";
            isValid = false;
        }
        try {
            max = Integer.parseInt(maxField.getText());
            if(max < 0) { throw new NumberFormatException(); }
        } catch (NumberFormatException e) {
            errorMessage = errorMessage + "Max must be an integer between 0 and 999999999.\n";
            isValid = false;
        }
        if(inv > max || inv < min) {
            errorMessage = errorMessage + "Inventory must be less than or equal to the Max value " +
                    "and greater than or equal to the Min value.\n";
            isValid = false;
        }
        if(min > max) {
            errorMessage = errorMessage + "Max value must be greater than or equal to the Min value.\n";
            isValid = false;
        }
        if (this.inHouseBtn.isSelected()) {
            int machineId = 0;
            try {
                machineId = Integer.parseInt(dynField.getText());
            } catch (NumberFormatException e) {
                errorMessage = errorMessage + "Machine ID must be an integer between 0 and 999999999.\n";
                isValid = false;
            }
            if(isValid) {
                this.part = new InHouse(id, name, price, inv, min, max, machineId);
            }
        } else if (this.outsourcedBtn.isSelected()) {
            if(isValid) {
                String companyName = dynField.getText();
                this.part = new Outsourced(id, name, price, inv, min, max, companyName);
            }
        }
        if(isValid) {
            Inventory.updatePart(index, this.part);
            NotifyUser.notifyModified(this.part);
            NavigateToScene.goToMainMenu(event);
        } else {
            NotifyUser.notifyNotValid(errorMessage);
        }
    }

    /**
     * Event handlers for the ModifyPartController class.
     */
    @FXML
    private void onActionCancel(ActionEvent event) throws IOException { NavigateToScene.goToMainMenu(event); }

    /**
     * Event handler for when the in-house radio button is selected.
     * Sets the dynamic label to "Machine ID".
     */
    @FXML
    private void onActionInHouse(ActionEvent event) { dynLabel.setText("Machine ID"); }

    /**
     * Event handler for when the outsourced radio button is selected.
     * Sets the dynamic label to "Company Name".
     */
    @FXML
    private void onActionOutsourced(ActionEvent event) {
        dynLabel.setText("Company Name");
    }
}
