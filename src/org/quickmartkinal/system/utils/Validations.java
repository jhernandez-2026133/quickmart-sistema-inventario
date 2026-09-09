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

    public Boolean validatetextEmpty(String text) {
        boolean isEmpty = false;

        if (text.isEmpty() == true || text.isBlank() == true) {
            isEmpty = true;
        }
        return isEmpty;
    }

    public Boolean validateTextLenght(String text, int textmax) {
        return text.length() <= textmax;
    }

    public Boolean equalsText(String textoOriginal, String textCompare) {
        return textoOriginal.equals(textCompare);
    }

}
