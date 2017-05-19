package com.cdpjosecabrera.controlador;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;

import com.cdpjosecabrera.modelo.Libro;
import com.cdpjosecabrera.utilidades.Fecha;
import com.cdpjosecabrera.utilidades.IsbnException;

public class LibroControler {

	private final static String drv="com.mysql.jdbc.Driver";
	private final static String db="jdbc:mysql://localhost:3306/biblioteca";
	private final static String userAndPass="root";
	Connection cn;
	Statement st;
	PreparedStatement pst;
	
	public ResultSet rs;
	private ArrayList<Libro> libros;
	private ArrayList<Libro> librosFiltro;
	private ArrayList<String> autores;
	
	public LibroControler(){
		super();
		libros=new ArrayList<Libro>();
		
	}
	public void abrirConexion() throws ClassNotFoundException, SQLException{
		
		Class.forName(drv);
		cn=DriverManager.getConnection(db,userAndPass,"");
		//System.out.println("La conexion se realizo con exito");
		
	}
	
	public void cerrarConexion() throws SQLException{
		
		if(rs!=null)rs.close();
		if(st!=null)st.close();
		if(pst!=null)pst.close();
		if(cn!=null)cn.close();
		//System.out.println("Conexion cerrada");
	}
	
	public ArrayList<Libro> getLibros(String sql) throws SQLException, ParseException, IsbnException{
		libros=new ArrayList<Libro>();
		PreparedStatement preparedStatement=cn.prepareStatement(sql);
		rs=preparedStatement.executeQuery();
		
		while(rs.next()){
			Fecha fechaPrestamo=new Fecha(rs.getDate("fechaPrestamo"));
			Fecha fechaDevolucion=new Fecha(rs.getDate("fechaDevolucion"));
			Libro libro=new Libro(rs.getInt("idLibros"),rs.getString("titulo"),rs.getString("autor"),rs.getString("editorial"),rs.getBoolean("prestado"),fechaPrestamo,fechaDevolucion,rs.getString("isbn"));
			libros.add(libro);
			libro=null;

		}
		
		preparedStatement=null;
		return libros;
		
	}
	
	public Boolean agregarLibro(Libro libro) throws SQLException{
		boolean agregado=false;
		String sql="insert into libros values (?,?,?,?,?,?,?,?)";
		PreparedStatement preparedStatement=cn.prepareStatement(sql);
		
		preparedStatement.setInt(1, libro.getIdLibros());
		preparedStatement.setString(2, libro.getTitulo());
		preparedStatement.setString(3, libro.getAutor());
		preparedStatement.setString(4, libro.getEditorial());	
		preparedStatement.setBoolean(5, libro.isPrestado());
		
		java.sql.Date fechaPrestamo =new java.sql.Date(libro.getFechaPrestamo().getDate().getTime());
		preparedStatement.setDate(6, fechaPrestamo);
		java.sql.Date fechaDevolucion =new java.sql.Date(libro.getFechaDevolucion().getDate().getTime());
		preparedStatement.setDate(7, fechaDevolucion);
		preparedStatement.setString(8, libro.getIsbn());
		preparedStatement.executeUpdate();
		agregado=true;
		preparedStatement=null;
		
		return agregado;
		

	}
	
	public Boolean modificarLibro(Libro libro, String isbnAnt) throws SQLException{
		Boolean modificado;
		
		String sql="Update libros Set titulo=?,autor=?,editorial=?,prestado=?,fechaDevolucion=?,isbn=? where isbn=?";
		PreparedStatement preparedStatement=cn.prepareStatement(sql);
		
		
		preparedStatement.setString(1, libro.getTitulo());
		preparedStatement.setString(2, libro.getAutor());
		preparedStatement.setString(3, libro.getEditorial());	
		preparedStatement.setBoolean(4, libro.isPrestado());
		
		java.sql.Date fechaDevolucion =new java.sql.Date(libro.getFechaDevolucion().getDate().getTime());
		preparedStatement.setDate(5, fechaDevolucion);
		preparedStatement.setString(6, libro.getIsbn());
		preparedStatement.setString(7, isbnAnt);
		preparedStatement.executeUpdate();
		modificado=true;
		preparedStatement=null;
		
		return modificado;
		
		
		
		
		
		
	}
	
	public Boolean borrarLibro(Libro libro) throws SQLException{
		
		Boolean borrado;
		String campo="isbn";
		String sql="Delete from libros where "+campo+"=?";
		PreparedStatement preparedStatement=cn.prepareStatement(sql);
		preparedStatement.setString(1, libro.getIsbn());
		preparedStatement.executeUpdate();
		borrado=true;
		preparedStatement=null;
		
		return borrado;
		
		
		
	}
	
	public ArrayList<Libro> buscarLibro(String campo, String campoBusqueda) throws SQLException, ParseException, IsbnException{
		librosFiltro=new ArrayList<Libro>();
	String sql="select * from libros where "+campo+" like ? order by idLibros";
	PreparedStatement preparedStatement	= cn.prepareStatement(sql);	
	preparedStatement.setString(1, campoBusqueda);
	rs = preparedStatement.executeQuery();
	
	rs.last();//con esto se va a la ultima linea buscada
	int tam=rs.getRow();//guarda el tamaño de filas en la busqueda
	rs.beforeFirst();//vuvuelve al principio
	if(tam>0){
	
	while(rs.next()){
		Fecha fechaPrestamo=new Fecha(rs.getDate("fechaPrestamo"));
		Fecha fechaDevolucion=new Fecha(rs.getDate("fechaDevolucion"));
		Libro libro=new Libro(rs.getInt("idLibros"),rs.getString("titulo"),rs.getString("autor"),rs.getString("editorial"),rs.getBoolean("prestado"),fechaPrestamo,fechaDevolucion,rs.getString("isbn"));
		librosFiltro.add(libro);
		libro=null;

	}
	}
	preparedStatement=null;
	return librosFiltro;

	}
	
	public ArrayList<String> autores() throws SQLException, ParseException, IsbnException{
		autores=new ArrayList<String>();
		String sql="select distinct autor from libros";
		PreparedStatement preparedStatement	= cn.prepareStatement(sql);	
		rs = preparedStatement.executeQuery();	
		
		rs.last();//con esto se va a la ultima linea buscada
		int tam=rs.getRow();//guarda el tamaño de filas en la busqueda
		rs.beforeFirst();//vuvuelve al principio
		if(tam>0){
			while(rs.next()){
		
				
				autores.add(rs.getString("autor"));
				
				
			}
		}
		preparedStatement=null;
		return autores;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
