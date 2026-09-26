/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.quickmartkinal.system.service;

import org.quickmartkinal.system.model.Rol;
import org.quickmartkinal.system.model.Usuario;
import org.quickmartkinal.system.repository.RegistrationRepository;

/**
 *
 * @author informatica
 */
public class RegistrationService {

    private static final String DEFAULT_ROLE = "Cliente";

    private final RegistrationRepository registrationRepository = new RegistrationRepository();

    public RegistrationStatus register(Usuario usuario) {

        if (registrationRepository.existeUsuario(usuario.getNombreUsuario(), usuario.getCorreo())) {
            return RegistrationStatus.USER_ALREADY_EXISTS;
        }

        Rol rolCliente = registrationRepository.obtenerRolPorNombre(DEFAULT_ROLE);
        if (rolCliente == null) {
            return RegistrationStatus.ROLE_NOT_FOUND;
        }

        boolean registrado = registrationRepository.registrarUsuario(usuario, rolCliente.getIdRol());

        return registrado ? RegistrationStatus.REGISTRATION_SUCCESS : RegistrationStatus.REGISTRATION_FAILED;
    }

}
