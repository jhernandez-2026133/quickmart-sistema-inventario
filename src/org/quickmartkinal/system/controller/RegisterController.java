/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.quickmartkinal.system.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.quickmartkinal.system.model.Usuario;
import org.quickmartkinal.system.service.RegistrationService;
import org.quickmartkinal.system.service.RegistrationStatus;
import org.quickmartkinal.system.utils.AlertInformation;
import org.quickmartkinal.system.utils.Validations;
import org.quickmartkinal.system.utils.ViewFactory;

public class RegisterController implements Initializable {

    private static final int USERNAME_MAX_LENGTH = 25;
    private static final int PASSWORD_MIN_LENGTH = 6;
    private static final int PASSWORD_MAX_LENGTH = 35;

    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtLastName;
    @FXML
    private TextField txtEmail;
    @FXML
    private PasswordField pwdPassword;
    @FXML
    private PasswordField pwdConfirmPassword;

    private final Validations validate = new Validations();
    private final AlertInformation alertInfo = new AlertInformation();
    private final RegistrationService registrationService = new RegistrationService();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    public void onCreateUser(ActionEvent event) {
        String username = txtUsername.getText().trim();
        String name = txtName.getText().trim();
        String lastName = txtLastName.getText().trim();
        String email = txtEmail.getText().trim();
        String password = pwdPassword.getText().trim();
        String confirmPassword = pwdConfirmPassword.getText().trim();

        if (!camposCompletos(username, name, lastName, email, password, confirmPassword)) {
            alertInfo.viewAlert("ERROR", "CAMPOS VACIOS", "ERROR DE CAMPOS", "Debes llenar todos los campos.");
            return;
        }

        if (!validate.validateTextLength(username, USERNAME_MAX_LENGTH)) {
            alertInfo.viewAlert("ERROR", "USUARIO INVALIDO", "ERROR DE VALIDACION",
                    "El usuario no puede tener mas de " + USERNAME_MAX_LENGTH + " caracteres.");
            return;
        }

        if (!validate.validateEmail(email)) {
            alertInfo.viewAlert("ERROR", "CORREO INVALIDO", "ERROR DE VALIDACION", "Ingresa un correo electronico valido.");
            return;
        }

        if (!validate.validateTextMinLength(password, PASSWORD_MIN_LENGTH) || !validate.validateTextLength(password, PASSWORD_MAX_LENGTH)) {
            alertInfo.viewAlert("ERROR", "CONTRASEÑA INVALIDA", "ERROR DE VALIDACION",
                    "La contraseña debe tener entre " + PASSWORD_MIN_LENGTH + " y " + PASSWORD_MAX_LENGTH + " caracteres.");
            return;
        }

        if (!validate.equalsText(password, confirmPassword)) {
            alertInfo.viewAlert("ERROR", "LAS CONTRASEÑAS NO COINCIDEN", "ERROR DE VALIDACION",
                    "La confirmacion de contraseña debe ser igual a la contraseña.");
            return;
        }

        Usuario nuevoUsuario = new Usuario(name, lastName, email, username, password);
        RegistrationStatus status = registrationService.register(nuevoUsuario);

        switch (status) {
            case USER_ALREADY_EXISTS ->
                alertInfo.viewAlert("ERROR", "USUARIO YA EXISTE", "NO SE PUDO REGISTRAR",
                        "Ya existe una cuenta con ese usuario o correo electronico.");
            case ROLE_NOT_FOUND ->
                alertInfo.viewAlert("ERROR", "ERROR DE CONFIGURACION", "NO SE PUDO REGISTRAR",
                        "No se encontro el rol por defecto. Contacta al administrador.");
            case REGISTRATION_FAILED ->
                alertInfo.viewAlert("ERROR", "ERROR AL REGISTRAR", "NO SE PUDO REGISTRAR",
                        "Ocurrio un error al guardar el usuario. Intenta nuevamente.");
            case REGISTRATION_SUCCESS -> {
                alertInfo.viewAlert("INFORMATION", "CUENTA CREADA", "REGISTRO EXITOSO",
                        "Tu cuenta se creo correctamente. Ahora puedes iniciar sesion.");
                ViewFactory viewFactory = new ViewFactory();
                viewFactory.viewLogin();
            }
        }
    }

    @FXML
    public void onCancel(ActionEvent event) {
        ViewFactory viewFactory = new ViewFactory();
        viewFactory.viewLogin();
    }

    private boolean camposCompletos(String... campos) {
        for (String campo : campos) {
            if (validate.validateTextEmpty(campo)) {
                return false;
            }
        }
        return true;
    }

}
