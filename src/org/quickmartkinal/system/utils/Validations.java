/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.quickmartkinal.system.utils;

import java.util.regex.Pattern;

/**
 *
 * @author informatica
 */
public class Validations {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w.+-]+@[\\w-]+\\.[a-zA-Z]{2,}$");

    public Validations() {
    }

    public boolean validateTextEmpty(String text) {
        return text.isEmpty() || text.isBlank();
    }

    public boolean validateTextLength(String text, int maxLength) {
        return text.length() <= maxLength;
    }

    public boolean validateTextMinLength(String text, int minLength) {
        return text.length() >= minLength;
    }

    public boolean equalsText(String originalText, String compareText) {
        return originalText.equals(compareText);
    }

    public boolean validateEmail(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }

}
