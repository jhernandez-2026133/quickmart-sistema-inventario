/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.quickmartkinal.system.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author informatica
 */
public class ConexionDB {

    private static ConexionDB instanciaConexionDB;
    private Connection connection;

    private ConexionDB() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://" + Environment.LOCATION_SERVICE + "/" + Environment.DATA_BASE,
                    Environment.USER,
                    Environment.PASSWORD);
        } catch (ClassNotFoundException classNotFound) {
            System.out.println("Error: clase no encontrada.");
	} catch (SQLException sqlException) {
	System.out.println("Error de conexion a la base de datos: " + sqlException.getMessage());
	sqlException.printStackTrace();
	} catch (Exception e) {
            System.out.println("Error inesperado: " + e.getMessage());
        }
    }

    public static ConexionDB getInstanciaConexionDB() {
        if (instanciaConexionDB == null) {
            instanciaConexionDB = new ConexionDB();
        }
        return instanciaConexionDB;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

}
