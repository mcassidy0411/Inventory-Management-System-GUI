package com.mc.controller;

import com.mc.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;

/**
 * The controller class for the AddProduct view. Allows the user to add a new product to the inventory system.
 * @author Michael Cassidy
 */
public class AddProductController {
    @FXML
    private TableColumn<Part, Integer> allIdCol;
    @FXML
    private TableColumn<Part, Integer> allInvCol;
    @FXML
    private TableColumn<Part, String> allNameCol;
    @FXML
    private TableView<Part> allPartTbl;
    @FXML
    private TableColumn<Part, Double> allPriceCol;
    @FXML
    private TableColumn<Part, Integer> assoIdCol;
    @FXML
    private TableColumn<Part, Integer> assoInvCol;
    @FXML
    private TableColumn<Part, String> assoNameCol;
    @FXML
    private TableView<Part> assoPartTbl;
    @FXML
    private TableColumn<Part, Double> assoPriceCol;
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
    private TextField searchField;
    private final ObservableList<Part> assoPartList = FXCollections.observableArrayList();
    @FXML
    private Label partNotFoundLbl;
    private static int productId = 1000;

    /**
     * Initializes the controller class.
     * Sets up the table views and search field listeners for the FXML scene.
     */
    @FXML
    private void initialize() {
        allPartTbl.setItems(Inventory.getAllParts());
        allIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        allNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        allInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        allPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        assoPartTbl.setItems(assoPartList);
        assoIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        assoNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        assoInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        assoPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        searchField.textProperty().addListener((observable, oldValue, newValue) -> partSearchFieldListener());
    }

    /**
     * Listens for changes to the text property of the searchField TextField and updates the allPartTbl TableView with
     * search results based on the text entered. If the search text is numeric, the lookup is performed based on part
     * ID.  Otherwise, the lookup is performed based on part name. If no results are found, a "Part Not Found" label is
     * displayed.
     */
    @FXML
    private void partSearchFieldListener() {
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (allPartTbl.getItems().isEmpty() && !newValue.isEmpty()) {
                partNotFoundLbl.setText("Part Not Found");
            } else {
                partNotFoundLbl.setText("");
            }
        });
        String searchText = searchField.getText();
        if (searchText.isEmpty()) {
            allPartTbl.setItems(Inventory.getAllParts());
            return;
        }
        ObservableList<Part> searchResults = FXCollections.observableArrayList();
        if (searchText.matches("\\d+")) {
            int partId = 0;
            try {
                partId = Integer.parseInt(searchText);
            } catch (NumberFormatException e) {
                NotifyUser.notifyIntOutOfBounds();
            }
            Part part = Inventory.lookupPart(partId);
            if (part != null) {
                searchResults.add(part);
            }
            allPartTbl.setItems(searchResults);
        } else {
            allPartTbl.setItems(Inventory.lookupPart(searchText));
        }
    }

    /**
     * This method is called when the user clicks the "Add" button on the form. It retrieves the selected Part from the
     * Parts table, checks if it is not null and not already added to the Associated Parts table, and adds it to the
     * Associated Parts list if it passes the checks.  If the selected Part is null or already added, it displays an
     * error message to the user using NotifyUser.notifyNotSelected() method.
     */
    @FXML
    private void onActionAdd() {
        Part selectedPart = allPartTbl.getSelectionModel().getSelectedItem();
        if (selectedPart != null && !assoPartList.contains(selectedPart)) {
            assoPartList.add(selectedPart);
        } else {
            NotifyUser.notifyNotSelected("Part", "add");
        }
    }

    /**
     * Removes the selected part from the associated parts list.  Notifies user when an associated part is removed.
     * If no part is selected, a notification message will be displayed.
     */
    @FXML
    private void onActionRemove() {
        Part selectedPart = assoPartTbl.getSelectionModel().getSelectedItem();
        if (selectedPart != null) {
            assoPartList.remove(selectedPart);
            NotifyUser.notifyPartRemoved(selectedPart);
        } else {
            NotifyUser.notifyNotSelected("Part", "remove");
        }
    }

    /**
     * Event handler for the "Save" button click.
     * Validates user input and creates a new Product object with the entered data,
     * then adds the associated parts to the product and saves it to the Inventory.
     * Displays a notification to the user upon successful save and navigates to the main menu.
     * @param event An ActionEvent representing the "Save" button click
     * @throws IOException if there is an error navigating to the main menu
     */
    @FXML
    private void onActionSave(ActionEvent event) throws IOException {
        // Initialize variables to hold the user-entered data
        int id = generateProductId();
        String name = nameField.getText();
        String errorMessage = "";
        double price = 0;
        int inv = 0;
        int min = 0;
        int max = 0;
        boolean isValid = true;

        // Parse and validate the user-entered data
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

        // Create and save the new Product object if the user-entered data is valid
        if(isValid) {
            Product product = new Product(id, name, price, inv, min, max);
            for(Part part : assoPartList) {
                product.addAssociatedPart(part);
            }
            Inventory.addProduct(product);
            NotifyUser.notifySaved("Product", id, name);
            NavigateToScene.goToMainMenu(event);
        } else {
            NotifyUser.notifyNotValid(errorMessage);
        }
    }

    /**
     * Handles the cancel button action by navigating to the main menu scene.
     * @param event the event that triggered the action
     * @throws IOException if an IO error occurs when navigating to the main menu scene
     */
    @FXML
    private void onActionCancel(ActionEvent event) throws IOException { NavigateToScene.goToMainMenu(event); }

    /**
     * @return product id
     */
    private static int generateProductId () {return ++productId;}
}

