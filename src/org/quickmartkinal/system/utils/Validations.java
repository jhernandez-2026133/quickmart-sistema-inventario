<<<<<<< HEAD
package org.quickmartkinal.system.utils;



public class Validations {
 
    public Validations(){ 
    
    }
    
    public Boolean validateTextEmpty(String text){
        
        boolean isEmpty = false;
        
        if( text.isEmpty() == true || text.isBlank() == true )
            isEmpty = true;
        return isEmpty;
    }
    
    public Boolean validateTextLength( String text, int textMax){
        
        return text.length()<= textMax;
    }
    
    public Boolean equalsText(String textOriginal,
                              String textCompare){
        
    return textOriginal.equals(textCompare);
    }
    
    public Boolean validateEmail(String email){
        
        int dotCount = 0 , arrobeCount=0;
        
        for( int index = 0; index < email.length(); index++ ){
            if( email.charAt(index) == '.' )
                dotCount++;
            if( dotCount >1 )
                return false;
        }
        
            //validar cantidad arrobas
        for( int index = 0; index < email.length(); index++ ){
            if( email.charAt(index) == '@' )
                arrobeCount++;
        }
        
         if( arrobeCount != 1 )
                return false;
        
      return true;  
    }
    
    
=======
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

>>>>>>> 05be444258df5c84736a3d2340d725e86c358462
}
