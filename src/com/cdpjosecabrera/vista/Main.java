package com.cdpjosecabrera.vista;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

import com.cdpjosecabrera.controlador.LibroControler;
import com.cdpjosecabrera.modelo.Libro;
import com.cdpjosecabrera.utilidades.Fecha;
import com.cdpjosecabrera.utilidades.IsbnException;

public class Main {

	public static void main(String[] args) {
		// TODO Ap�ndice de m�todo generado autom�ticamente

		FrmBiblioteca ventana=new FrmBiblioteca();
		/*
		LibroControler biblioteca=new LibroControler();
	
		
		try {
			biblioteca.abrirConexion();
			ArrayList<String> autores=biblioteca.autores();
			for(int x=0;x<autores.size();x++){
				System.out.println(autores.get(x));
			}
			biblioteca.cerrarConexion();
		} catch (SQLException | ParseException | IsbnException | ClassNotFoundException e) {
			// TODO Bloque catch generado autom�ticamente
			e.printStackTrace();
		}
		*/
	}
}
