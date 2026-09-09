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
    
    
}
