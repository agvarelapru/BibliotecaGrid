/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cdpjosecabrera.utilidades;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Objects;

/**
 *
 * @author Modebi
 */
public class Fecha implements Comparable<Fecha>{
    
    private Calendar fecha;
    private int dia,mes,ano;//fecha descompuesta en 3 enteros

    public Fecha() {//para la fecha actual del sistema
        fecha=GregorianCalendar.getInstance(Locale.getDefault());
    }

    public Fecha(String date) throws FechaIncorrecta{//constructor con el formato de la fecha solicitada y la comprueba
        if (!isFechaValida(date)){
            throw new FechaIncorrecta("Introduce una fecha valida");
            
        }
        this.setFecha(date);
        
    }
    
    public Fecha(Date date) throws ParseException{//este constructor es para librocontroler de base de datos
    	
    	fecha=GregorianCalendar.getInstance();
    	fecha.setTime(date);   	
    }

    
    

    private void setFecha(String date) {
        //Rompe la cadena y la almacena en el calendario gregoriano
        String [] arrayFecha=date.split("/");
        this.fecha=new GregorianCalendar(Integer.parseInt(arrayFecha[2]),Integer.parseInt(arrayFecha[1])-1,Integer.parseInt(arrayFecha[0]));
        this.setAno(Integer.parseInt(arrayFecha[0]));
        this.setMes(Integer.parseInt(arrayFecha[1])-1);//por que los meses en la clase van de 0 a 11
        this.setDia(Integer.parseInt(arrayFecha[2]));
    }

    public int getDia() {
        return dia;
    }

    private void setDia(int dia) {
        this.dia=this.fecha.get(Calendar.DAY_OF_MONTH);
    }

    public int getMes() {
        return mes+1;
    }

    public void setMes(int mes) {
        this.mes =this.fecha.get((Calendar.MONTH));
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = fecha.get(Calendar.YEAR);
    }

    public Calendar getFecha() {
        return fecha;
    }

    public void setFecha(Calendar fecha) {
        this.fecha = fecha;
    }
    
    public Date getDate(){//transforma el cam,po fecha que es calendar a date que es otro
        Date date=null;
        if (fecha!=null){
            date=fecha.getTime();
            
        }
        return date;
    }
    
    public int getEdad(){
        Calendar hoy =Calendar.getInstance();
        int difYear=hoy.get(Calendar.YEAR)-fecha.get(Calendar.YEAR);
        int difMes=hoy.get(Calendar.MONTH)-fecha.get(Calendar.MONTH);
        int difDia=hoy.get(Calendar.DAY_OF_MONTH)-fecha.get(Calendar.DAY_OF_MONTH);
        if(difMes<0 || (difMes ==0 && difDia <0)){
            difYear--;
        }
        return difYear;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.fecha);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Fecha other = (Fecha) obj;
        if (!Objects.equals(this.fecha, other.fecha)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {//tostring sin nada seleccionado y modificado para que salga con elte formato de fecha
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(fecha.getTime());
    }
    
    
    
    
    public void agregarDias (int dias){
        this.fecha.add(Calendar.DAY_OF_MONTH,dias);
    }
    

    //Método que comprueba si la fecha es correcta.
    private static boolean isFechaValida(String fecha) {
        try {
            SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            formatoFecha.setLenient(false); //Evita que se corrija la fecha
            formatoFecha.parse(fecha);
        }
        catch (ParseException e) {
            return false;
        }
        return true;
    }
    
    

    @Override
    public int compareTo(Fecha f) {//devuelve un positivo un negativo o un 0
        Fecha fecha=(Fecha) f;
        return this.fecha.compareTo(f.fecha);
    } 
}



