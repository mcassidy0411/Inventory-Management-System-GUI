package com.mc.controller;

import com.mc.model.Inventory;
import com.mc.model.Part;
import com.mc.model.Product;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * The controller class for the Main Menu scene.
 * Handles user interactions with the main menu screen, such as navigating to other screens and
 * searching for parts and products.
 * @author Michael Cassidy
 */
public class MainMenuController implements Initializable {
    @FXML
    public TableView<Part> partTable;
    @FXML
    private TableColumn<Part, Integer> partIdCol;
    @FXML
    private TableColumn<Part, String> partNameCol;
    @FXML
    private TableColumn<Part, Integer> partInvLvlCol;
    @FXML
    private TableColumn<Part, Double> partPriceCol;
    @FXML
    private TableView<Product> prodTable;
    @FXML
    private TableColumn<Product, Integer> prodIdCol;
    @FXML
    private TableColumn<Product, String> prodNameCol;
    @FXML
    private TableColumn<Product, Integer> prodInvLvlCol;
    @FXML
    private TableColumn<Product, Double> prodPriceCol;
    @FXML
    private TextField partField;
    @FXML
    private TextField prodField;
    @FXML
    private Label partNotFoundLbl;
    @FXML
    private Label prodNotFoundLbl;

    /**
     * Initializes the controller class.
     * Sets the items of the partTable and prodTable to all parts and all products in Inventory respectively.
     * Sets the cell value factories for each column in the tables.
     * Adds listeners to the partField and prodField to trigger a search filter when the text changes.
     * @param location The location of the FXML file.
     * @param resources The ResourceBundle for the FXML file.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Set the items of the partTable to all parts in Inventory and set cell value factories for each column
        partTable.setItems(Inventory.getAllParts());
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvLvlCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        // Set the items of the prodTable to all products in Inventory and set cell value factories for each column
        prodTable.setItems(Inventory.getAllProducts());
        prodIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        prodNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        prodInvLvlCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        prodPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        // Add listeners to the partField and prodField to trigger a search filter when the text changes
        partField.textProperty().addListener((observable, oldValue, newValue) -> partSearchFieldListener());
        prodField.textProperty().addListener((observable, oldValue, newValue) -> prodSearchFieldListener());
    }

    /**
     * Listener method for the search field in the Parts table.
     * Updates the table to display the search results.
     */
    @FXML
    private void partSearchFieldListener() {
        partField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (partTable.getItems().isEmpty() && !newValue.isEmpty()) {
                partNotFoundLbl.setText("Part Not Found");
            } else {
                partNotFoundLbl.setText("");
            }
        });
        String searchText = partField.getText();
        if (searchText.isEmpty()) {
            partTable.setItems(Inventory.getAllParts());
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
            partTable.setItems(searchResults);
        } else {
            partTable.setItems(Inventory.lookupPart(searchText));
        }
    }

    /**
     * Listener method for the search field in the Products table.
     * Updates the table to display the search results.
     */
    @FXML
    private void prodSearchFieldListener() {
        // Listen for changes in the search field
        prodField.textProperty().addListener((observable, oldValue, newValue) -> {
            // Check if the table view is empty and the search field has input
            if (prodTable.getItems().isEmpty() && !newValue.isEmpty()) {
//                prodTable.setPlaceholder(new Label("Product not found"));
                prodNotFoundLbl.setText("Product Not Found");
            } else {
//                prodTable.setPlaceholder(new Label("No content in table"));
                prodNotFoundLbl.setText("");
            }
        });

        String searchText = prodField.getText();
        if (searchText.isEmpty()) {
            prodTable.setItems(Inventory.getAllProducts());
//            partNotFoundLbl.setText("");
            return;
        }
        ObservableList<Product> searchResults = FXCollections.observableArrayList();
        if (searchText.matches("\\d+")) { // check if input is a valid integer
            int prodId = 0;
            try {
                prodId = Integer.parseInt(searchText);
            } catch (NumberFormatException e) {
                NotifyUser.notifyIntOutOfBounds();
            }
            Product product = Inventory.lookupProduct(prodId);
            if (product != null) {
                searchResults.add(product);
            }
            prodTable.setItems(searchResults);
        } else { //if input is not a valid integer
            prodTable.setItems(Inventory.lookupProduct(searchText));
        }

    }

    /**
     * Opens the "Add Part" window when the "Add" button is clicked.
     * @param event the event that triggered this method
     * @throws IOException if an I/O error occurs
     */
    @FXML
    private void onActionAddPart(ActionEvent event) throws IOException {
        NavigateToScene.goToScene(event, "AddPart");
    }

    /**
     * Event handler for the "Add Product" button.
     * Navigates to the AddProduct.fxml scene.
     * @param event the ActionEvent triggered by the user clicking the "Add Product" button
     * @throws IOException if an error occurs when loading the AddProduct.fxml file
      */
    @FXML
    private void onActionAddProduct(ActionEvent event) throws IOException {
        NavigateToScene.goToScene(event, "AddProduct");
    }

    /**
     * Handles the "Delete" button action in the Parts tab. Deletes the selected part from the Inventory and
     * notifies the user if the part was deleted successfully or not.
     * If no part is selected, it displays a message to the user to select a part.
     */
    @FXML
    private void onActionDeletePart() {
        // Get the selected part from the table
        Part selectedPart = partTable.getSelectionModel().getSelectedItem();
        if(selectedPart == null) {
            // If no part is selected, display a message to the user and return
            NotifyUser.notifyNotSelected("Part", "Delete");
            return;
        }
        // Try to delete the part from the inventory
        if (Inventory.deletePart(selectedPart)) {
            // If the part was deleted successfully, display a message to the user
            NotifyUser.notifyDeleted(selectedPart);
        } else {
            // If the part could not be deleted, display a message to the user
            NotifyUser.notifyNotDeleted(selectedPart);
        }
    }

    /**
     * Deletes the selected product from the product table and the Inventory.
     * Notifies the user with an alert message of whether the product was deleted or not.
     */
    @FXML
    private void onActionDeleteProduct() {
        Product selectedProduct = prodTable.getSelectionModel().getSelectedItem();
        if(selectedProduct == null) {
            NotifyUser.notifyNotSelected("Product", "Delete");
            return;
        } else if(!selectedProduct.getAllAssociatedParts().isEmpty()) {
            NotifyUser.notifyUnableToDeleteProduct();
            return;
        }
        if (Inventory.deleteProduct(selectedProduct)) {
            NotifyUser.notifyDeleted(selectedProduct);
        } else {
            NotifyUser.notifyNotDeleted(selectedProduct);
        }
    }

    /**
     * Event handler for modifying a part.
     * Gets the selected part from the table and loads the ModifyPart.fxml scene to modify the part.
     * Sets the part and its index to ModifyPartController.
     * Shows the new scene to the user.
     * @param event The event that triggers the method call.
     */
    @FXML
    private void onActionModifyPart(ActionEvent event) {
        Part selectedPart = partTable.getSelectionModel().getSelectedItem();
        int index = partTable.getSelectionModel().getSelectedIndex();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mc/view/ModifyPart.fxml"));
            Parent root = loader.load();
            ModifyPartController controller = loader.getController();
            controller.setPart(selectedPart, index);
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch(Exception e) {
            NotifyUser.notifyNotSelected("Part", "Modify");
        }
    }

    /**
     * This method opens the ModifyProduct.fxml view and sets the selected product's information to be displayed in the
     * fields for editing. It takes an ActionEvent parameter, which is the event generated by the user clicking the
     * "Modify" button on the main screen. It then uses the selected product to set the initial values of the fields in
     * the ModifyProduct.fxml view, so that the user can edit the product's information. The method catches any
     * exceptions that may occur and notifies the user if a product has not been selected for modification.
     * @param event The event that triggers the method call.
     */
    @FXML
    private void onActionModifyProduct(ActionEvent event) {
        Product selectedProduct = prodTable.getSelectionModel().getSelectedItem();
        int index = prodTable.getSelectionModel().getSelectedIndex();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mc/view/ModifyProduct.fxml"));
            Parent root = loader.load();
            ModifyProductController controller = loader.getController();
            controller.setProduct(selectedProduct, index);
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch(Exception e) {
            NotifyUser.notifyNotSelected("Product", "Modify");
        }
    }

    /**
     * Exits application
     */
    @FXML
    private void onActionExit() {
        Platform.exit();
    }
}
