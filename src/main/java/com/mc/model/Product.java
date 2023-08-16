package com.mc.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Class to create Product objects
 * @author Michael Cassidy
 */
public class Product {
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;
    private final ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    /**
     * Constructor for Product class
     * @param id product id
     * @param name product name
     * @param price product price
     * @param stock product current stock level
     * @param min product minimum stock level
     * @param max product maximum stock level
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * @return product id
     */
    public int getId() { return id; }

    /**
     * @param id to set
     */
    public void setId(int id) { this.id = id; }

    /**
     * @return product name
     */
    public String getName() { return name; }

    /**
     * @param name to set
     */
    public void setName(String name) { this.name = name; }

    /**
     * @return product price
     */
    public double getPrice() { return price; }

    /**
     * @param price to set
     */
    public void setPrice(double price) { this.price = price; }

    /**
     * @return stock
     */
    public int getStock() { return stock; }

    /**
     * @param stock to set
     */
    public void setStock(int stock) { this.stock = stock; }

    /**
     * @return minimum inventory
     */
    public int getMin() { return min; }

    /**
     * @param min inventory to set
     */
    public void setMin(int min) { this.min = min; }

    /**
     * @return maximum inventory
     */
    public int getMax() { return max; }

    /**
     * @param max inventory to set
     */
    public void setMax(int max) { this.max = max; }

    /**
     * @param part to add to associatedParts list
     */
    public void addAssociatedPart(Part part) { associatedParts.add(part); }

    /**
     * @param selectedAssociatedPart to delete from associatedParts list
     * @return true if part removed successfully
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        int index = associatedParts.indexOf(selectedAssociatedPart);
        if(index >= 0) {
            associatedParts.remove(index);
            return true;
        }
        return false;
    }

    /**
     * @return associatedParts list
     */
    public ObservableList<Part> getAllAssociatedParts() { return associatedParts; }
}
