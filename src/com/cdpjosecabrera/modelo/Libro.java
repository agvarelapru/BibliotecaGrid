package com.cdpjosecabrera.modelo;

import com.cdpjosecabrera.utilidades.Fecha;
import com.cdpjosecabrera.utilidades.IsbnException;
import com.cdpjosecabrera.utilidades.Utilidades;

public class Libro {
	private int idLibros;
	private String titulo;
	private String autor;
	private String editorial;
	private boolean prestado;
	private Fecha fechaPrestamo;
	private Fecha fechaDevolucion;
	private String isbn;
	public Libro() {
		super();
		// TODO Apéndice de constructor generado automáticamente
	}
	public Libro(int idLibros, String titulo, String autor, String editorial, boolean prestado, Fecha fechaPrestamo,
			Fecha fechaDevolucion, String isbn) throws IsbnException {
		super();
		this.idLibros = idLibros;
		this.titulo = titulo;
		this.autor = autor;
		this.editorial = editorial;
		
		this.prestado = prestado;
		this.fechaPrestamo = fechaPrestamo;
		this.fechaDevolucion = fechaDevolucion;
		this.setIsbn(isbn);
	}
	public int getIdLibros() {
		return idLibros;
	}
	public void setIdLibros(int idLibros) {
		this.idLibros = idLibros;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getAutor() {
		return autor;
	}
	public void setAutor(String autor) {
		this.autor = autor;
	}
	public String getEditorial() {
		return editorial;
	}
	public void setEditorial(String editorial) {
		this.editorial = editorial;
	}
	public boolean isPrestado() {
		return prestado;
	}
	public void setPrestado(boolean prestado) {
		this.prestado = prestado;
	}
	public Fecha getFechaPrestamo() {
		return fechaPrestamo;
	}
	public void setFechaPrestamo(Fecha fechaPrestamo) {
		this.fechaPrestamo = fechaPrestamo;
	}
	public Fecha getFechaDevolucion() {
		return fechaDevolucion;
	}
	public void setFechaDevolucion(Fecha fechaDevolucion) {
		this.fechaDevolucion = fechaDevolucion;
	}
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) throws IsbnException {
		
		if(Utilidades.compruebaIsbn(isbn)){
		this.isbn = isbn;
		}else{
			throw new IsbnException(isbn);
		}
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((isbn == null) ? 0 : isbn.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Libro other = (Libro) obj;
		if (isbn == null) {
			if (other.isbn != null)
				return false;
		} else if (!isbn.equals(other.isbn))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Libro [idLibros=" + idLibros + ", titulo=" + titulo + ", autor=" + autor + ", editorial=" + editorial
				+ ", prestado=" + prestado + ", fechaPrestamo=" + fechaPrestamo + ", fechaDevolucion=" + fechaDevolucion
				+ ", isbn=" + isbn + "]";
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
