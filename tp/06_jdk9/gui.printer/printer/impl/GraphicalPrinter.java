package printer.impl;

import printer.IPrinter;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.application.Application;
import javafx.stage.Stage;

public class GraphicalPrinter extends Application implements IPrinter {
    private static String msgToPrint;
    public void print(String msg) {
	msgToPrint = msg;	
	Application.launch(msg);	
    }
    public void start(Stage stage) {
	Alert alert = new Alert(AlertType.INFORMATION);
	alert.setTitle("Information Dialog");
	alert.setHeaderText(null);
	alert.setContentText(msgToPrint);
	alert.showAndWait();
    }
}

