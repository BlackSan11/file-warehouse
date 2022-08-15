module org.example {
    requires javafx.controls;
    requires javafx.fxml;

    exports ru.gb.file.warehouse;
    opens ru.gb.file.warehouse to javafx.fxml;
}
