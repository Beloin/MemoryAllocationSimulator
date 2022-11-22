module com.github.beloin.memoryalocationsimulator {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.github.beloin.memoryalocationsimulator to javafx.fxml;
    exports com.github.beloin.memoryalocationsimulator;
}