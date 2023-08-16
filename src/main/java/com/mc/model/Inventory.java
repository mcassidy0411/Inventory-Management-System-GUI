package com.mc.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Inventory class holds and modifies the allParts and allProducts lists
 * @author Michael Cassidy
 */
public class Inventory {
    private static final ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static final ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * @param part to add to allParts list
     */
    public static void addPart(Part part) {
        allParts.add(part);
    }

    /**
     * @param product to add to allProducts list
     */
    public static void addProduct(Product product) {
        allProducts.add(product);
    }

    /**
     * Looks up part in allParts list by int partId
     * @param partId to look up
     * @return the part if found or null if not found
     */
    public static Part lookupPart(int partId) {
        for (Part part : allParts) {
            if (part.getId() == partId) {
                return part;
            }
        }
        return null;
    }

    /**
     * Looks up part in allParts list by String partName
     * @param partName to look up
     * @return ObservableList of parts that contain the partName
     */
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> searchResults = FXCollections.observableArrayList();
        String searchLowerCase = partName.toLowerCase();
        for (Part part : allParts) {
            String nameLowerCase = part.getName().toLowerCase();
            if (nameLowerCase.contains(searchLowerCase)) {
                searchResults.add(part);
            }
        }
        return searchResults;
    }

    /**
     * Looks up product in allProducts list by int productId
     * @param productId to look up
     * @return product if found or null if not found
     */
    public static Product lookupProduct(int productId) {
        for (Product product : allProducts) {
            if (product.getId() == productId) {
                return product;
            }
        }
        return null;
    }

    /**
     * Looks up product in allProducts list by String productName
     * @param productName to look up
     * @return ObservableList of products that contain the productName
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> searchResults = FXCollections.observableArrayList();
        String searchLowerCase = productName.toLowerCase();
        for (Product product : allProducts) {
            String nameLowerCase = product.getName().toLowerCase();
            if (nameLowerCase.contains(searchLowerCase)) {
                searchResults.add(product);
            }
        }
        return searchResults;
    }

    /**
     * Updates Part in allParts list
     * @param index of part to update
     * @param part to update
     */
    public static void updatePart(int index, Part part) { allParts.set(index, part); }

    /**
     * Updates Product in all Products list
     * @param index of product to update
     * @param newProduct to update
     */
    public static void updateProduct(int index, Product newProduct) { allProducts.set(index, newProduct); }

    /**
     * Deletes part from allParts list
     * @param selectedPart part to delete
     * @return true if part deleted
     */
    public static boolean deletePart(Part selectedPart) {
        int index = allParts.indexOf(selectedPart);
        if(index >= 0) {
            allParts.remove(index);
            return true;
        }
        return false;
    }

    /**
     * Deletes product from allProducts list
     * @param selectedProduct product to delete
     * @return true if product deleted
     */
    public static boolean deleteProduct(Product selectedProduct) {
        int index = allProducts.indexOf(selectedProduct);
        if(index >= 0) {
            allProducts.remove(index);
            return true;
        }
        return false;
    }

    /**
     * @return allParts list
     */
    public static ObservableList<Part> getAllParts() { return allParts; }

    /**
     * @return allProducts list
     */
    public static ObservableList<Product> getAllProducts() { return allProducts; }

}
