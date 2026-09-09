/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.quickmartkinal.system.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import org.quickmartkinal.system.model.Usuario;
import org.quickmartkinal.system.utils.Sesion;
import org.quickmartkinal.system.utils.ViewFactory;

public class DashboardController implements Initializable {

    @FXML
    private Label lblBienvenida;
    @FXML
    private Label lblRol;

    private Usuario usuario;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
        if (usuario != null) {
            lblBienvenida.setText("Bienvenido, " + usuario.getNombreUsuario());
            lblRol.setText("Rol: " + usuario.getNombreRol());
        }
    }

    @FXML
    public void onLogout(MouseEvent event) {
        Sesion.getInstanciaSesion().cerrarSesion();
        ViewFactory viewFacto = new ViewFactory();
        viewFacto.viewLogin();
    }

}
