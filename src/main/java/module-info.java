module com.example.practica3_ayed {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.practica3_ayed to javafx.fxml;
    exports com.example.practica3_ayed;
}