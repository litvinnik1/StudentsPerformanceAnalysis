module com.example.kursova_oop {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.kursova_oop to javafx.fxml;
    exports com.example.kursova_oop;
}