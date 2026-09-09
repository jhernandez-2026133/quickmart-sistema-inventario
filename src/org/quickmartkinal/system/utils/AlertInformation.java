/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.quickmartkinal.system.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class AlertInformation {

    /**
     * Constructor vacío público
     */
    public AlertInformation() {
    }

    /**
     * Muestra una ventana de alerta de JavaFX.
     *
     * @param tipoAlerta Texto que define el tipo de alerta (INFO, WARNING, ERROR, CONFIRMATION, NONE).
     * @param titulo El título de la ventana de la alerta.
     * @param encabezado El texto del encabezado (puede ser null si no se desea mostrar).
     * @param mensaje El mensaje principal o contenido de la alerta.
     */
    public void viewAlert(String tipoAlerta, String titulo, String encabezado, String mensaje) {

        AlertType tipo = switch (tipoAlerta.toUpperCase()) {
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
