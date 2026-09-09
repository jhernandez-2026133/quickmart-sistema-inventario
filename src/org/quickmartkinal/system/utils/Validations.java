/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.quickmartkinal.system.utils;

/**
 *
 * @author informatica
 */
public class Validations {

    public Validations() {
    }

    public boolean validateTextEmpty(String text) {
        return text.isEmpty() || text.isBlank();
    }

    public boolean validateTextLength(String text, int maxLength) {
        return text.length() <= maxLength;
    }

    public boolean equalsText(String originalText, String compareText) {
        return originalText.equals(compareText);
    }

}
