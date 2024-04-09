module udelp.edu {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
	requires lombok;
	requires javafx.base;
	requires javafx.graphics;
    opens udelp.edu.PooGrafico to javafx.fxml;
    exports udelp.edu.PooGrafico;
    opens udelp.edu.PooGrafico.model to javafx.base;

}