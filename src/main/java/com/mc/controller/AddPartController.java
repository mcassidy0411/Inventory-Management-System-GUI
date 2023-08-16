package com.mc.controller;

import com.mc.model.InHouse;
import com.mc.model.Inventory;
import com.mc.model.Outsourced;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

import java.io.IOException;

/**
 * The controller class for the Add Product screen. It allows the user to add a new product to the inventory by filling
 * out the appropriate fields and selecting associated parts. The user can save or cancel the addition of the new
 * product.
 * @author Michael Cassidy
 */
public class AddPartController {
    @FXML
    private TextField dynField;
    @FXML
    private Label dynLabel;
    @FXML
    private TextField invField;
    @FXML
    private TextField maxField;
    @FXML
    private TextField minField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField priceField;
    @FXML
    private RadioButton inHouseBtn;
    @FXML
    private RadioButton outsourcedBtn;
    private static int partId = 0;

    /**
     * On initialization, sets dynLabel to corresponding value of selected radio button
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
     * Sets dynLabel to "Machine ID" when In House button is selected
     */
    @FXML
    private void onActionInHouse() { dynLabel.setText("Machine ID"); }

    /**
     * Sets dynLabel to "Company Name" when Outsourced button is selected
     */
    @FXML
    private void onActionOutsourced() { dynLabel.setText("Company Name"); }

    /**
     * Called when the user clicks the "Save" button. It validates the input fields and saves a new part
     * to the inventory if the input is valid. If there is an error, an error message is displayed to the user
     * and the part is not saved.
     * RUNTIME ERROR - For any of the numeric fields, if the user entered a non-numeric value, the program would throw
     * a NumberFormatException when parsing the strings.  By surrounding the parse functions in try-catch blocks, I
     * am able to perform data validation and handle the errors at the same time.
     * @param event that triggered this method
     * @throws IOException if there is an error writing to the inventory file.
     */
    @FXML
    private void onActionSave(ActionEvent event) throws IOException {
        int id = generatePartId();
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
                Inventory.addPart(new InHouse(id, name, price, inv, min, max, machineId));
            }
        } else if (this.outsourcedBtn.isSelected()) {
            if(isValid) {
                String companyName = dynField.getText();
                Inventory.addPart(new Outsourced(id, name, price, inv, min, max, companyName));
            }
        }
        if(isValid) {
            NotifyUser.notifySaved("Part", id, name);
            NavigateToScene.goToMainMenu(event);
        } else {
            NotifyUser.notifyNotValid(errorMessage);
        }
    }

    /**
     * Called when the user clicks the "Cancel" button.  Inputs are not saved and the program returns to the main menu.
     * @param event that triggered this method
     * @throws IOException if there is an error returning to the main menu
     */
    @FXML
    private void onActionCancel(ActionEvent event) throws IOException { NavigateToScene.goToMainMenu(event); }

    /**
     * Increments partId so all parts have a unique id
     * @return part id
     */
    private static int generatePartId() { return ++partId; }

}

