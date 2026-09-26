/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.quickmartkinal.system.repository;

import org.quickmartkinal.system.model.Rol;
import org.quickmartkinal.system.model.Usuario;

/**
 *
 * @author informatica
 */
public interface RegistrationInterface {

    boolean existeUsuario(String nombreUsuario, String correo);

    Rol obtenerRolPorNombre(String nombreRol);

    boolean registrarUsuario(Usuario usuario, String idRol);

}
