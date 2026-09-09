/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.quickmartkinal.system.utils;

public class Sesion {


    private static Sesion instanciaSesion;
    private Usuario usuarioActual;

    private Sesion() {
    }

    public static Sesion getInstanciaSesion() {
        if (instanciaSesion == null) {
            instanciaSesion = new Sesion();
        }
        return instanciaSesion;
    }

    public Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public void setUsuarioActual(Usuario usuarioActual) {
        this.usuarioActual = usuarioActual;
    }

    public String getRolActual() {
        return usuarioActual != null ? usuarioActual.getNombreRol() : null;
    }

    public void cerrarSesion() {
        usuarioActual = null;
    }

}
