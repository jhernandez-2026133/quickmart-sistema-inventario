/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.quickmartkinal.system.service;

import org.quickmartkinal.system.model.Usuario;
import org.quickmartkinal.system.repository.AuthenticationRepository;

/**
 *
 * @author informatica
 */
public class AuthenticationService {

    private final AuthenticationRepository authRepository = new AuthenticationRepository();
    private Usuario authenticatedUser;

    public AuthenticationStatus login(String nombreUsuario, String contrasena) {

        Usuario usuarioLogueado = authRepository.login(nombreUsuario, contrasena);

        if (usuarioLogueado == null) {
            return AuthenticationStatus.INVALID_CREDENTIALS;
        }

        authenticatedUser = usuarioLogueado;
        return AuthenticationStatus.LOGIN_SUCCESS;
    }

    public Usuario getAuthenticatedUser() {
        return authenticatedUser;
    }

}
