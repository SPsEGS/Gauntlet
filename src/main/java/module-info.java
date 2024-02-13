module com.teamf5.gauntlet {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.teamf5.gauntlet to javafx.fxml;
    exports com.teamf5.gauntlet;

    opens com.teamf5.gauntlet.Controller to javafx.fxml;
    exports com.teamf5.gauntlet.Controller;
}