package com.mc.model;

/**
 * Extends the Part class to create an InHouse Part object
 * @author Michael Cassidy
 */
public class InHouse extends Part {
    private int machineId;

    /**
     * Constructor for InHouse object
     * Calls Part constructor and sets the machine id
     * @param id the part id
     * @param name the part name
     * @param price the part price
     * @param stock the current inventory level of the part
     * @param min the minimum inventory level of the part
     * @param max the maximum inventory level of the part
     * @param machineId the machine id specific an InHouse part
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        setMachineId(machineId);
    }

    /**
     * @return the machine id
     */
    public int getMachineId() {
        return machineId;
    }

    /**
     *
     * @param machineId the machineId to set
     */
    public void setMachineId(int machineId) { this.machineId = machineId; }

}
