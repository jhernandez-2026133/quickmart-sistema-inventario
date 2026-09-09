package org.quickmartkinal.system.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class AlertInformation {


    public AlertInformation() {
    }

  
    public void viewAlert(String tipoAlerta, String titulo, String encabezado, String mensaje) {
        
        AlertType tipo= switch (tipoAlerta.toUpperCase()) {
            case "INFO", "INFORMATION" -> AlertType.INFORMATION;
            case "WARNING", "WARN" -> AlertType.WARNING;
            case "ERROR", "ERR" -> AlertType.ERROR;
            case "CONFIRMATION", "CONFIRM" -> AlertType.CONFIRMATION;
            case "NONE" -> AlertType.NONE;
            default -> AlertType.INFORMATION;
        }; 
        Alert alert = new Alert(tipo);

        alert.setTitle(titulo);
        alert.setHeaderText(encabezado);
        alert.setContentText(mensaje);
 
        alert.showAndWait();
    }
}
