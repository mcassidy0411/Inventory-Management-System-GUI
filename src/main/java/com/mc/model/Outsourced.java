package com.mc.model;

/**
 * Extends the Part class to create an Outsourced Part object
 * @author Michael Cassidy
 */
public class Outsourced extends Part {
    private String companyName;

    /**
     * Constructor for Outsourced object
     * Calls Part constructor and sets the companyName
     * @param id the part id
     * @param name the part name
     * @param price the part price
     * @param stock the current inventory level of the part
     * @param min the minimum inventory level of the part
     * @param max the maximum inventory level of the part
     * @param companyName the company name specific an Outsourced part
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        setCompanyName(companyName);
    }

    /**
     * @return companyName
     */
    public String getCompanyName() { return companyName; }

    /**
     * @param companyName to set
     */
    public void setCompanyName(String companyName) { this.companyName = companyName; }
}
