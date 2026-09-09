/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.quickmartkinal.system.repository;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.quickmartkinal.system.config.ConexionDB;
import org.quickmartkinal.system.model.Rol;
import org.quickmartkinal.system.model.Usuario;

/**
 *
 * @author informatica
 */
public class RegistrationRepository implements RegistrationInterface {

    private final ConexionDB conexionDB = ConexionDB.getInstanciaConexionDB();

    @Override
    public boolean existeUsuario(String nombreUsuario, String correo) {
        boolean existe = false;
        try (CallableStatement callSP = conexionDB.getConnection().prepareCall("{call sp_existe_usuario(?,?)}")) {
            callSP.setString(1, nombreUsuario);
            callSP.setString(2, correo);

            if (callSP.execute()) {
                try (ResultSet resultSet = callSP.getResultSet()) {
                    if (resultSet.next()) {
                        existe = resultSet.getInt("total") > 0;
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al verificar si el usuario existe: " + e.getMessage());
        }
        return existe;
    }

    @Override
    public Rol obtenerRolPorNombre(String nombreRol) {
        Rol rol = null;
        try (CallableStatement callSP = conexionDB.getConnection().prepareCall("{call sp_obtener_rol_por_nombre(?)}")) {
            callSP.setString(1, nombreRol);

            if (callSP.execute()) {
                try (ResultSet resultSet = callSP.getResultSet()) {
                    if (resultSet.next()) {
                        rol = new Rol(
                                resultSet.getString("id_rol"),
                                resultSet.getString("nombre_rol"),
                                resultSet.getString("descripcion")
                        );
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener el rol: " + e.getMessage());
        }
        return rol;
    }

    @Override
    public boolean registrarUsuario(Usuario usuario, String idRol) {
        boolean registrado = false;
        try (CallableStatement callSP = conexionDB.getConnection().prepareCall("{call sp_create_users(?,?,?,?,?,?)}")) {
            callSP.setString(1, usuario.getNombre());
            callSP.setString(2, usuario.getApellido());
            callSP.setString(3, usuario.getCorreo());
            callSP.setString(4, usuario.getNombreUsuario());
            callSP.setString(5, usuario.getContrasena());
            callSP.setString(6, idRol);

            callSP.execute();
            registrado = true;
        } catch (SQLException e) {
            System.out.println("Error al registrar el usuario: " + e.getMessage());
        }
        return registrado;
    }

}
