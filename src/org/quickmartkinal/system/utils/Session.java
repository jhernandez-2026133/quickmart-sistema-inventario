package org.quickmartkinal.system.utils;

import org.quickmartkinal.system.model.User;

import org.quickmartkinal.system.model.User;

/**
 * Guarda en memoria el usuario que inició sesión, para poder
 * usarlo (por ejemplo, mostrar su nombre) en el DashboardView.
 */
public class Session {

    private static Session instanciaSession;
    private User userActual;

    private Session() {
    }

    public static Session getInstanciaSession() {
        if (instanciaSession == null) {
            instanciaSession = new Session();
        }
        return instanciaSession;
    }

    public User getUserActual() {
        return userActual;
    }

    public void setUserActual(User userActual) {
        this.userActual = userActual;
    }

    public void cerrarSesion() {
        this.userActual = null;
    }

}
