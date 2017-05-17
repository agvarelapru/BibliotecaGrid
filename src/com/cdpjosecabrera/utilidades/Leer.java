/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cdpjosecabrera.utilidades;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


/**
 *
 * @author VARELA
 */
public class Leer {
    
    public static String cadena(){
        String adato="";
        try {
        InputStreamReader isr=new InputStreamReader(System.in);
        BufferedReader flujoE=new BufferedReader(isr);
        
            adato=flujoE.readLine();
        } catch (IOException e) {
                     
        System.out.println("Error: "+e.getMessage());
        }
                       
    return adato;    
    }
    public static int entero(){
        boolean sw=true;
        int x=0;
        while(sw){
            try{
                x=Integer.parseInt(cadena());
                sw=false;
            }catch(Exception ex){
                System.out.println("Vuelva a introducie el dato");            
            }                       
        }
  return x;  
}
 public static double decimal(){
        boolean sw=true;
        double x=0;
        while(sw){
            try{
                x=Double.parseDouble(cadena());
                sw=false;
            }catch(Exception ex){
                System.out.println("Vuelva a introducie el dato");            
            }                       
        }
  return x;  
}   

    public static Boolean boleano() {
       boolean sw=true;
        boolean x=true;
        while(sw){
            try{
                x=Boolean.valueOf(cadena());
                
                sw=false;
            }catch(Exception ex){
                System.out.println("Vuelva a introducie el dato");            
            }                       
        }
  return x;  
    }
    
}
