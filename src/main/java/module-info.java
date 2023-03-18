module view.lab8 {
    requires javafx.controls;
    requires javafx.fxml;


    opens view.lab8 to javafx.fxml;
    exports view.lab8;
}