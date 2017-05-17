package com.cdpjosecabrera.utilidades;

public class IsbnException extends Exception{
	
	
	
	
	
	 public IsbnException(String isbn)  {
	        super("el numero introducido, "+isbn+" no es correcto");
	        
	        
	    }
	
	

}
