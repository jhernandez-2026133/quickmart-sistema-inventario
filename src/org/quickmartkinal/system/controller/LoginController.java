/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.quickmartkinal.system.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import org.quickmartkinal.system.service.AuthenticationService;
import org.quickmartkinal.system.service.AuthenticationStatus;
import org.quickmartkinal.system.utils.AlertInformation;
import org.quickmartkinal.system.utils.Sesion;
import org.quickmartkinal.system.utils.Validations;
import org.quickmartkinal.system.utils.ViewFactory;

public class LoginController implements Initializable {

    @FXML
    private TextField txtUsuario;
    @FXML
    private PasswordField pwdContrasena;

    private final Validations validate = new Validations();
    private final AlertInformation alertInfo = new AlertInformation();
    private final AuthenticationService authService = new AuthenticationService();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    public void onLogin(MouseEvent event) {
        String usuario = txtUsuario.getText().trim();
        String contrasena = pwdContrasena.getText().trim();

        if (validate.validateTextEmpty(usuario) || validate.validateTextEmpty(contrasena)) {
            alertInfo.viewAlert("ERROR", "CAMPOS VACIOS", "ERROR DE CAMPOS", "Debes ingresar usuario y contraseña.");
            return;
        }

        AuthenticationStatus status = authService.login(usuario, contrasena);

        switch (status) {
            case INVALID_CREDENTIALS ->
                alertInfo.viewAlert("ERROR", "ERROR DE INICIO DE SESION", "CREDENCIALES INCORRECTAS",
                        "El usuario o la contraseña no son correctos. Intenta nuevamente.");
            case LOGIN_SUCCESS -> {
                Sesion.getInstanciaSesion().setUsuarioActual(authService.getAuthenticatedUser());
                ViewFactory viewFactory = new ViewFactory();
                viewFactory.viewDashboard(authService.getAuthenticatedUser());
            }
        }
    }

    @FXML
    public void onRegister(KeyEvent event) {
        // TODO: navegar a la vista de registro (loginRegisterMarket.fxml)
        // cuando el flujo de creacion de cuenta este implementado.
    }

}
