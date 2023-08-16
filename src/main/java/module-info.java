module com.mc.InventorySystem {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.mc.main to javafx.fxml;
    opens com.mc.model to javafx.base;
    exports com.mc.main;
    exports com.mc.controller;
    exports com.mc.model;
    opens com.mc.controller to javafx.fxml;
}