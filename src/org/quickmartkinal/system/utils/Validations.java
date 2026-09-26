package org.quickmartkinal.system.utils;

/**
 * Clase de utilidades para validación de datos en QuickMart Kinal.
 * 
 * @author informatica
 */
public class Validations {

    public Validations() {
    }
	public boolean esCorreoValido(String correo) {
	// 1. Verificar que no esté vacío y no sea nulo
	if (correo == null || correo.trim().isEmpty()) {
		return false;
    }

	// 2. Debe tener un '@' y un '.'
	if (!correo.contains("@") || !correo.contains(".")) {
		return false;
    }

	// 3. Bloquear puntos consecutivos (ej: .. o ...)
	if (correo.contains("..")) {
		return false;
    }

	// 4. Obtener posiciones clave
	int posicionAt = correo.indexOf("@");
	int posicionPunto = correo.lastIndexOf(".");

	// 5. Validaciones de posición:
	// - No empezar con '@' ni con '.'
	// - El punto debe estar después del '@' y no inmediatamente pegado (ej: @.)
	// - La extensión final debe tener al menos 2 letras tras el último punto
	if (posicionAt < 1 || correo.startsWith(".") || posicionPunto <= posicionAt + 1 || posicionPunto >= correo.length() - 2) {
        return false;
    }

    return true;
}

    public boolean validateTextEmpty(String text) {
        return text == null || text.isEmpty() || text.isBlank();
    }

    public boolean validateTextLength(String text, int maxLength) {
        return text != null && text.length() <= maxLength;
    }

    public boolean validateTextMinLength(String text, int minLength) {
        return text != null && text.length() >= minLength;
    }

    public boolean equalsText(String originalText, String compareText) {
        if (originalText == null || compareText == null) {
            return false;
        }
        return originalText.equals(compareText);
    }
    public boolean validateEmail(String email) {
        return esCorreoValido(email);
    }
}