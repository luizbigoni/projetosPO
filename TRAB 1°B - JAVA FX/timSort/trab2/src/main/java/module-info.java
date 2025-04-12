module fipp.com.trab2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens fipp.com.trab2 to javafx.fxml;
    exports fipp.com.trab2;
}