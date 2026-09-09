/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.quickmartkinal.system.repository;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.quickmartkinal.system.config.ConexionDB;
import org.quickmartkinal.system.model.Usuario;

/**
 *
 * @author informatica
 */
public class AuthenticationRepository implements AuthenticationInterface {

    private CallableStatement callSP;
    private final ConexionDB conexionDB = ConexionDB.getInstanciaConexionDB();

    @Override
    public Usuario login(String nombreUsuario, String contrasena) {
        Usuario usuarioLogueado = null;
        try {
            callSP = conexionDB.getConnection().prepareCall("{call sp_validar_login(?,?)}");
            callSP.setString(1, nombreUsuario);
            callSP.setString(2, contrasena);

            boolean hayResultados = callSP.execute();
            if (hayResultados) {
                try (ResultSet resultSet = callSP.getResultSet()) {
                    if (resultSet.next()) {
                        usuarioLogueado = new Usuario(
                                resultSet.getString("nombre_usuario"),
                                resultSet.getString("nombre_rol")
                        );
                    }
                }
            }
            callSP.close();
        } catch (SQLException e) {
            System.out.println("Error al iniciar sesion");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return usuarioLogueado;
    }

}
