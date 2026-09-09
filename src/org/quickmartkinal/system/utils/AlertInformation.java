/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.quickmartkinal.system.utils;

import javafx.scene.control.Alert;

/**
 * Utilidad para mostrar alertas de JavaFX de forma centralizada.
 *
 * @author informatica
 */
public class AlertInformation {

    public void viewAlert(String type, String title, String header, String content) {
        Alert.AlertType alertType = switch (type.toUpperCase()) {
            case "ERROR" -> Alert.AlertType.ERROR;
            case "WARNING" -> Alert.AlertType.WARNING;
            case "CONFIRMATION" -> Alert.AlertType.CONFIRMATION;
            default -> Alert.AlertType.INFORMATION;
        };

        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

}
