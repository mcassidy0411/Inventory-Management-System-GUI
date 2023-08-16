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
 * The controller class for the Modify Product scene.  Allows the user to modify the details of a product and its
 * associated parts.
 * @author Michael Cassidy
*/
public class ModifyProductController {
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
    private TextField idField;
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
    @FXML
    private Label partNotFoundLbl;
    private int index;
    private Product product;
    private ObservableList<Part> assoPartList = FXCollections.observableArrayList();

    /**
     * Sets the product and its associated parts to be displayed in the modify product screen.
     * @param product The product to be modified
     * @param index The index of the product in the product list
     */
    public void setProduct(Product product, int index) {
        this.index = index;
        this.product = product;
        this.assoPartList = product.getAllAssociatedParts();
        idField.setText(Integer.toString(product.getId()));
        nameField.setText(product.getName());
        priceField.setText(Double.toString(product.getPrice()));
        invField.setText(Integer.toString(product.getStock()));
        minField.setText(Integer.toString(product.getMin()));
        maxField.setText(Integer.toString(product.getMax()));

        assoPartTbl.setItems(assoPartList);
        assoIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        assoNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        assoInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        assoPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     * Initializes the controller class for the Main Menu.
     * Sets up the TableView for all parts in Inventory and configures the columns to display their respective data.
     * Adds a listener to the searchField to filter parts in real-time as the user types.
     */
    @FXML
    private void initialize() {
        allPartTbl.setItems(Inventory.getAllParts());
        allIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        allNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        allInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        allPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        searchField.textProperty().addListener((observable, oldValue, newValue) -> partSearchFieldListener());
    }

    /**
     * Listener method for the searchField in the AddProduct and ModifyProduct scenes.
     * Displays "Part Not Found" message if search text does not match any parts in the allPartTbl table.
     * Searches for a part by ID or name and populates the allPartTbl table with the search results.
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
     * Adds a selected part to the associated parts list of the product.
     * If no part is selected or if the selected part is already associated with the product,
     * it will notify the user accordingly.
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
     * Removes the selected associated part from the product and updates the associated parts table accordingly.
     * Notifies user when selected part is removed.  If no associated part is selected, it shows an error message.
     */
    @FXML
    private void onActionRemove() {
        Part selectedPart = assoPartTbl.getSelectionModel().getSelectedItem();
        if (selectedPart != null) {
            if(!product.deleteAssociatedPart(selectedPart)) {
                NotifyUser.notifyUnableToDeleteAssoPart();
            } else {
                NotifyUser.notifyPartRemoved(selectedPart);
            }
        } else {
            NotifyUser.notifyNotSelected("Part", "remove");
        }
    }

    /**
     * This method is called when the user clicks the "Save" button on the modify product screen. It validates the
     * user's input for the product fields and associated parts, creates a new Product object with the modified values,
     * adds the associated parts to it, updates the corresponding Product in the Inventory, and navigates the user back
     * to the main menu screen. If the input is not valid, it displays an error message.
     * @param event An ActionEvent representing the user's click on the "Save" button
     * @throws IOException if an I/O error occurs while navigating to the main menu screen
     */
    @FXML
    private void onActionSave(ActionEvent event) throws IOException {
        int id = product.getId();
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
        if(isValid) {
            Product product = new Product(id, name, price, inv, min, max);
            for(Part part : assoPartList) {
                product.addAssociatedPart(part);
            }
            Inventory.updateProduct(index, this.product);
            NotifyUser.notifyModified(this.product);
            NavigateToScene.goToMainMenu(event);
        } else {
            NotifyUser.notifyNotValid(errorMessage);
        }
    }

    /**
     * Returns user to the Main Menu and discards user input.
     * @param event the action event triggered by clicking the "Cancel" button
     * @throws IOException if there is an error navigating to the main menu scene
     */
    @FXML
    private void onActionCancel(ActionEvent event) throws IOException { NavigateToScene.goToMainMenu(event); }
}

