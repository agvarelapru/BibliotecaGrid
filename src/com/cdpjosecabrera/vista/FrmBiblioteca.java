package com.cdpjosecabrera.vista;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.HeadlessException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.cdpjosecabrera.controlador.LibroControler;
import com.cdpjosecabrera.modelo.Libro;
import com.cdpjosecabrera.utilidades.Fecha;
import com.cdpjosecabrera.utilidades.FechaIncorrecta;
import com.cdpjosecabrera.utilidades.IsbnException;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JComboBox;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class FrmBiblioteca extends JFrame {

	private JPanel contentPane;
	private JTable table;
	JPanel panel;
	JScrollPane scrollPane;
	DefaultTableModel dtm;
	ArrayList<Libro> libros=new ArrayList<Libro>();
	ArrayList<String> autores=new ArrayList<String>();
	LibroControler biblioteca=new LibroControler();
	String [] campos={"IDLIBROS","TITULO","AUTOR","EDITORIAL","PRESTADO","FECHA PRESTAMO","FECHA DEVOLUCION","ISBN"};
	Libro libro=new Libro();
	JPanel panelLibros;
	private JPanel panelMantenimiento;
	private JPanel panelNavegador;
	private JLabel lblIdlibro,lblTitulo,lblAutor,lblEditorial,lblIsbn,lblFechaprestamo,lblFechadevolucion;	
	JCheckBox chckbxPrestado;
	ImageIcon imaNav=new ImageIcon();
	private JTextField txtIdLibros,txtTitulo,txtAutor,txtIsbn,txtEditorial,txtFechaPrestamo,txtFechaDevolucion;	
	private JButton btnAgregar,btnEditar,btnBorrar,btnGuardar,btnDeshacer;	
	private JButton btnInicio,btnAnterior,btnSiguiente,btnFinal;
	int cursor=0;
	boolean habilitado=true;Boolean escribir=false;Boolean navegar=true;Boolean agregado=false;
	Fecha fechaPrestamo=new Fecha();
	Fecha fechaDevolucion=new Fecha();
	Boolean modificar=false;Boolean modificado=false;Boolean borrado=false;
	String isbnAnt="";
	JLabel lblFiltroAutor;
	JComboBox cmbAutores;
	DefaultComboBoxModel dc;
	String autor,campo, cadenaBusqueda,id;
	JLabel lblBuscarLibros;
	private JTextField txtBusca;
	public FrmBiblioteca() {
		setTitle("Biblioteca");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 790, 507);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		
		definirVentana();
		definirEvento();
		
		this.setVisible(true);
	}


	private void definirEvento() {
		// TODO Apéndice de método generado automáticamente
		btnInicio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cursor=0;
				cargarCursor(cursor);
			}
		});
		btnAnterior.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(cursor>0){
				cursor=cursor-1;
				cargarCursor(cursor);
				}else{
					cursor=0;
					cargarCursor(cursor);
				}
				
				
			}
		});
		btnSiguiente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(cursor<libros.size()-1){
				cursor=cursor+1;
				cargarCursor(cursor);
				}else{
					cursor=0;
					cargarCursor(cursor);
				}
				
				
			}
		});
		btnFinal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cursor=libros.size()-1;
				cargarCursor(cursor);
			}
		});
		btnAgregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			navegar=false;habilitado=false;escribir=true;
			habilitarNavegador(navegar)	;
			habilitarPanelMantenilmiento(habilitado);
			editarPanelLibros(escribir)	;
			limpiarCajasTexto();	
			modificar=false;
				
				
			}

			
		});	
		btnDeshacer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				navegar=true;habilitado=true;escribir=false;modificar=false;
				cargarCursor(cursor);
				editarPanelLibros(escribir)	;
				habilitarPanelMantenilmiento(habilitado);
				habilitarNavegador(navegar)	;
				txtIsbn.setBackground(Color.white);
				txtFechaDevolucion.setBackground(Color.white);
			}

		});
		
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//guardar la modificacion
				if(modificar){

					
					try {
						fechaDevolucion=new Fecha(txtFechaDevolucion.getText());
						libro=new Libro(Integer.parseInt(txtIdLibros.getText()),txtTitulo.getText(),txtAutor.getText(),txtEditorial.getText(),chckbxPrestado.isSelected(),fechaPrestamo,fechaDevolucion,txtIsbn.getText());
						
					
					
					if(!txtTitulo.getText().equals("") || !txtAutor.getText().equals("") || !txtEditorial.getText().equals("") || !txtIsbn.getText().equals("") || !fechaDevolucion.equals("")){
						if(modificarLibro(libro,isbnAnt)){
							JOptionPane.showMessageDialog(table, "Libro modificado correctamente");
							navegar=true;habilitado=true;escribir=false;
							
							editarPanelLibros(escribir)	;
							habilitarPanelMantenilmiento(habilitado);
							habilitarNavegador(navegar)	;
							cargarLista();
							cursor=0;				
							cargarCursor(cursor);
							libro=null;
							//cargarCursor(cursor);
							modificar=false;
						}
							}else{
								JOptionPane.showMessageDialog(table, "No puede haber ningun campo vacio");
							}
							
						}catch (IndexOutOfBoundsException e3) {
							// TODO Bloque catch generado automáticamente
							JOptionPane.showMessageDialog(table,"No existe ningun libro");
						}
					
						catch (FechaIncorrecta e3) {
							// TODO Bloque catch generado automáticamente
							JOptionPane.showMessageDialog(table, e3.getMessage());
							txtFechaDevolucion.setBackground(Color.RED);
						}
					
						catch (IsbnException e2) {
							// TODO Bloque catch generado automáticamente
							JOptionPane.showMessageDialog(table, "ISBN "+txtIsbn.getText()+" incorrecto");
							txtIsbn.setBackground(Color.RED);
						}
						
						catch (NumberFormatException | SQLException | ClassNotFoundException |  ParseException  e1) {
							// TODO Bloque catch generado automáticamente
							JOptionPane.showMessageDialog(table, e1.getMessage());
							//e1.printStackTrace();
						
						}
						}
					
					
					
				
			
				else{
				
			try {
				
				libro=new Libro(Integer.parseInt(txtIdLibros.getText()),txtTitulo.getText(),txtAutor.getText(),txtEditorial.getText(),chckbxPrestado.isSelected(),fechaPrestamo,fechaDevolucion,txtIsbn.getText());
				
			if(!txtTitulo.getText().equals("") || !txtAutor.getText().equals("") || !txtEditorial.getText().equals("") || !txtIsbn.getText().equals("")){
				if(agregarLibro(libro)){
				JOptionPane.showMessageDialog(table, "Libro agregado correctamente");
				navegar=true;habilitado=true;escribir=false;
			
				editarPanelLibros(escribir)	;
				habilitarPanelMantenilmiento(habilitado);
				habilitarNavegador(navegar)	;
				cargarLista();
				cursor=libros.size()-1;
				cargarCursor(cursor);
				}
			}else{
					JOptionPane.showMessageDialog(table, "No puede haber ningun campo vacio");
			}
				
			}catch (IndexOutOfBoundsException e2) {
				// TODO Bloque catch generado automáticamente
				JOptionPane.showMessageDialog(table,"No existe ningun libro");
			}
			catch (IsbnException e2) {
				// TODO Bloque catch generado automáticamente
				JOptionPane.showMessageDialog(table, "ISBN "+txtIsbn.getText()+" incorrecto");
				txtIsbn.setBackground(Color.RED);
			}
			
			catch (NumberFormatException | SQLException | ClassNotFoundException |  ParseException  e1) {
				// TODO Bloque catch generado automáticamente
				JOptionPane.showMessageDialog(table, e1.getMessage());
				//e1.printStackTrace();
			
			}
			}

			}

			
		});
		
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				isbnAnt=txtIsbn.getText();
				modificar=true;
				
				navegar=false;habilitado=false;escribir=true;
				habilitarNavegador(navegar)	;
				habilitarPanelMantenilmiento(habilitado);
				editarPanelLibros(escribir)	;
				txtFechaDevolucion.setEditable(escribir);
				
				
			}
		});	
		
		btnBorrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				libro=libros.get(cursor);
				int confirmado = JOptionPane.showConfirmDialog(
						contentPane,"¿Borrar el libro '"+libro.getTitulo()+"' ?");

						if(JOptionPane.OK_OPTION==confirmado){
							try {
								if(borrarLibro(libro)){
									JOptionPane.showMessageDialog(table, "Libro borrado correctamente");
									cargarLista();
									cursor=0;
									cargarCursor(cursor);
									cargarAutores();
									cargarListaAutores();
								}
							} catch (IndexOutOfBoundsException e) {
								// TODO Bloque catch generado automáticamente
								JOptionPane.showMessageDialog(table,"No existe ningun libro");
							}
							catch (HeadlessException | ClassNotFoundException | SQLException | ParseException | IsbnException e) {
								// TODO Bloque catch generado automáticamente
								JOptionPane.showMessageDialog(table, e.getMessage());
							}
						}else{
							
						}
				
				
			}

			
		});	
		
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				
				int raton=table.rowAtPoint(arg0.getPoint());
				cursor=raton;
				cargarCursor(cursor);
				
			}
		});	
		
		cmbAutores.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				
				try {
					
					cadenaBusqueda=cmbAutores.getSelectedItem().toString();
					if(cadenaBusqueda.equals("Todos")){
					cargarLista();
					cursor=0;
					cargarCursor(cursor);
					}else{
						campo="autor";
					buscarLibros(campo,cadenaBusqueda);					
					cargarDatos(libros);
					cursor=0;
					cargarCursor(cursor);}
				} catch (ClassNotFoundException | SQLException | ParseException | IsbnException | IndexOutOfBoundsException e) {
					// TODO Bloque catch generado automáticamente
					JOptionPane.showMessageDialog(table, e.getMessage());
				}
				cadenaBusqueda=null;
			}
			
		});
		
		txtBusca.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				
				try {
					cadenaBusqueda=txtBusca.getText()+"%";
					campo="titulo";
					buscarLibros(campo,cadenaBusqueda);
					cargarDatos(libros);
					cursor=0;
					cargarCursor(cursor);
				} catch (IndexOutOfBoundsException e) {
					// TODO Bloque catch generado automáticamente
					JOptionPane.showMessageDialog(table,"No existe ningun libro");
				}	catch (ClassNotFoundException | SQLException | ParseException | IsbnException  e) {
					// TODO Bloque catch generado automáticamente
					JOptionPane.showMessageDialog(table, e.getMessage());
				}				
				
				
			}
		});
		
	}


	private void definirVentana() {
		// TODO Apéndice de método generado automáticamente
		
		
		
		panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 255), 3));
		panel.setBounds(10, 251, 754, 164);
		contentPane.add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		scrollPane = new JScrollPane();
		panel.add(scrollPane, BorderLayout.CENTER);
		
		dtm=new DefaultTableModel();
		table = new JTable(dtm);		
		scrollPane.setViewportView(table);	
		
		//etiquetas libros para egregar modificar etc.
		
		panelLibros = new JPanel();
		panelLibros.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 255), 3), "Libros", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 255)));
		panelLibros.setBounds(10, 0, 342, 234);
		contentPane.add(panelLibros);
		panelLibros.setLayout(null);
		
		lblIdlibro = new JLabel("IdLibro");
		lblIdlibro.setBounds(20, 33, 68, 14);
		panelLibros.add(lblIdlibro);
		
		lblTitulo = new JLabel("Titulo");
		lblTitulo.setBounds(20, 58, 46, 14);
		panelLibros.add(lblTitulo);
		
		lblAutor = new JLabel("Autor");
		lblAutor.setBounds(20, 83, 46, 14);
		panelLibros.add(lblAutor);
		
		lblEditorial = new JLabel("Editorial");
		lblEditorial.setBounds(20, 108, 46, 14);
		panelLibros.add(lblEditorial);
		
		lblIsbn = new JLabel("Isbn");
		lblIsbn.setBounds(20, 133, 46, 14);
		panelLibros.add(lblIsbn);
		
		lblFechaprestamo = new JLabel("FechaPrestamo");
		lblFechaprestamo.setBounds(20, 158, 92, 14);
		panelLibros.add(lblFechaprestamo);
		
		lblFechadevolucion = new JLabel("FechaDevolucion");
		lblFechadevolucion.setBounds(21, 183, 107, 14);
		panelLibros.add(lblFechadevolucion);
		
		chckbxPrestado = new JCheckBox("Prestado");
		chckbxPrestado.setForeground(SystemColor.infoText);
		chckbxPrestado.setBounds(20, 204, 97, 23);
		panelLibros.add(chckbxPrestado);
		
		txtIdLibros = new JTextField();
		txtIdLibros.setBackground(Color.WHITE);
		txtIdLibros.setBounds(127, 30, 190, 20);
		panelLibros.add(txtIdLibros);
		txtIdLibros.setColumns(10);
		
		txtTitulo = new JTextField();
		txtTitulo.setBackground(Color.WHITE);
		txtTitulo.setBounds(127, 55, 190, 20);
		panelLibros.add(txtTitulo);
		txtTitulo.setColumns(10);
		
		txtAutor = new JTextField();
		txtAutor.setBackground(Color.WHITE);
		txtAutor.setBounds(127, 80, 190, 20);
		panelLibros.add(txtAutor);
		txtAutor.setColumns(10);
		
		txtIsbn = new JTextField();
		txtIsbn.setBackground(Color.WHITE);
		txtIsbn.setBounds(127, 130, 190, 20);
		panelLibros.add(txtIsbn);
		txtIsbn.setColumns(10);
		
		txtEditorial = new JTextField();
		txtEditorial.setBackground(Color.WHITE);
		txtEditorial.setBounds(127, 105, 190, 20);
		panelLibros.add(txtEditorial);
		txtEditorial.setColumns(10);
		
		txtFechaPrestamo = new JTextField();
		txtFechaPrestamo.setBackground(Color.WHITE);
		txtFechaPrestamo.setBounds(127, 155, 190, 20);
		panelLibros.add(txtFechaPrestamo);
		txtFechaPrestamo.setColumns(10);
		
		txtFechaDevolucion = new JTextField();
		txtFechaDevolucion.setBackground(Color.WHITE);
		txtFechaDevolucion.setBounds(127, 180, 190, 20);
		panelLibros.add(txtFechaDevolucion);
		txtFechaDevolucion.setColumns(10);
		
		//Botones panel mantenimiento
		
		panelMantenimiento = new JPanel();
		panelMantenimiento.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 255), 3), "Mantenimiento Libros", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLUE));
		panelMantenimiento.setBounds(377, 0, 387, 112);
		contentPane.add(panelMantenimiento);
		panelMantenimiento.setLayout(null);
		
		imaNav=new ImageIcon("imagenes/botonAgregar.jpg");
		imaNav=new ImageIcon(imaNav.getImage().getScaledInstance(60, 60,java.awt.Image.SCALE_DEFAULT));		
		btnAgregar = new JButton("",imaNav);		
		btnAgregar.setBounds(21, 30, 61, 57);
		panelMantenimiento.add(btnAgregar);
		
		imaNav=new ImageIcon("imagenes/botonEditar.jpg");
		imaNav=new ImageIcon(imaNav.getImage().getScaledInstance(60, 60,java.awt.Image.SCALE_DEFAULT));	
		btnEditar = new JButton("",imaNav);		
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnEditar.setBounds(92, 30, 61, 57);
		panelMantenimiento.add(btnEditar);
		
		imaNav=new ImageIcon("imagenes/borrar.jpg");
		imaNav=new ImageIcon(imaNav.getImage().getScaledInstance(60, 60,java.awt.Image.SCALE_DEFAULT));	
		btnBorrar = new JButton("", imaNav);		
		btnBorrar.setBounds(163, 30, 61, 57);
		panelMantenimiento.add(btnBorrar);
		
		imaNav=new ImageIcon("imagenes/botonGuardar.jpg");
		imaNav=new ImageIcon(imaNav.getImage().getScaledInstance(60, 60,java.awt.Image.SCALE_DEFAULT));	
		btnGuardar = new JButton("",imaNav);		
		btnGuardar.setBounds(234, 30, 61, 57);
		panelMantenimiento.add(btnGuardar);
		btnGuardar.setEnabled(false);
		
		imaNav=new ImageIcon("imagenes/botonDeshacer.jpg");
		imaNav=new ImageIcon(imaNav.getImage().getScaledInstance(60, 60,java.awt.Image.SCALE_DEFAULT));	
		btnDeshacer = new JButton("",imaNav);		
		btnDeshacer.setBounds(305, 30, 61, 57);
		panelMantenimiento.add(btnDeshacer);
		btnDeshacer.setEnabled(false);
		
		panelNavegador = new JPanel();
		panelNavegador.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 255), 3), "Navegador", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 255)));
		panelNavegador.setBounds(377, 122, 387, 112);
		contentPane.add(panelNavegador);
		panelNavegador.setLayout(null);
		
		imaNav=new ImageIcon("imagenes/navPri.jpg");
		imaNav=new ImageIcon(imaNav.getImage().getScaledInstance(80, 60,java.awt.Image.SCALE_DEFAULT));	
		btnInicio = new JButton("",imaNav);		
		btnInicio.setBounds(22, 30, 77, 60);
		panelNavegador.add(btnInicio);
		
		imaNav=new ImageIcon("imagenes/navIzq.jpg");
		imaNav=new ImageIcon(imaNav.getImage().getScaledInstance(80, 60,java.awt.Image.SCALE_DEFAULT));	
		btnAnterior = new JButton("",imaNav);
		btnAnterior.setBounds(109, 30, 77, 60);
		panelNavegador.add(btnAnterior);
		
		
		imaNav=new ImageIcon("imagenes/navDer.jpg");
		imaNav=new ImageIcon(imaNav.getImage().getScaledInstance(80, 60,java.awt.Image.SCALE_DEFAULT));	
		btnSiguiente = new JButton("",imaNav);		
		btnSiguiente.setBounds(196, 30, 77, 60);
		panelNavegador.add(btnSiguiente);
		
		imaNav=new ImageIcon("imagenes/navUlt.jpg");
		imaNav=new ImageIcon(imaNav.getImage().getScaledInstance(80, 60,java.awt.Image.SCALE_DEFAULT));	
		btnFinal = new JButton("",imaNav);
		btnFinal.setBounds(288, 30, 77, 60);
		panelNavegador.add(btnFinal);
		
		lblFiltroAutor = new JLabel("Filtro autor");
		lblFiltroAutor.setBounds(10, 426, 94, 14);
		contentPane.add(lblFiltroAutor);
		
		
		
	
		dc=new DefaultComboBoxModel<>();
		cmbAutores = new JComboBox(dc);
		cmbAutores.setBounds(108, 423, 179, 20);
		contentPane.add(cmbAutores);
		cargarAutores();
		cargarListaAutores();
		
		
		lblBuscarLibros = new JLabel("Buscar Libros");
		lblBuscarLibros.setBounds(404, 426, 110, 14);
		contentPane.add(lblBuscarLibros);
		
		txtBusca = new JTextField();		
		txtBusca.setBounds(524, 423, 240, 20);
		contentPane.add(txtBusca);
		txtBusca.setColumns(10);
		
		
		
		try {
			cargarLista();
			
		} catch (ClassNotFoundException | SQLException | ParseException | IsbnException e) {
			// TODO Bloque catch generado automáticamente
			JOptionPane.showMessageDialog(table, e.getMessage());
		}
		
		
	}


	private void cargarListaAutores() {
		// TODO Apéndice de método generado automáticamente
		
		dc.addElement("Todos");
		for(int x=0;x<autores.size();x++){
			String autor=autores.get(x);
			dc.addElement(autor);
			
		}
		
	}


	private void cargarAutores() {
		// TODO Apéndice de método generado automáticamente
		try {
			biblioteca.abrirConexion();
			autores=new ArrayList<String>();
			autores=biblioteca.autores();
			biblioteca.cerrarConexion();
		} catch (SQLException | ParseException | IsbnException | ClassNotFoundException e1) {
			// TODO Bloque catch generado automáticamente
			JOptionPane.showMessageDialog(table, e1.getMessage());
		}
		
	}


	private void cargarLista() throws ClassNotFoundException, SQLException, ParseException, IsbnException {
		// TODO Apéndice de método generado automáticamente
		libros=new ArrayList<Libro>();
			biblioteca.abrirConexion();
			String sql="select * from libros order by idlibros";
			libros=biblioteca.getLibros(sql);
			biblioteca.cerrarConexion();
	
		
		cargarDatos(libros);
	}


	private void cargarDatos(ArrayList<Libro> libros) {
		// TODO Apéndice de método generado automáticamente
		
		dtm.setRowCount(0);
		dtm.setColumnCount(0);
		dtm.setColumnIdentifiers(campos);
		
		for(int x=0;x<libros.size();x++){
			libro=libros.get(x);
			Object[]fila={libro.getIdLibros(),libro.getTitulo(),libro.getAutor(),libro.getEditorial(),libro.isPrestado(),libro.getFechaPrestamo(),libro.getFechaDevolucion(),libro.getIsbn()};
			dtm.addRow(fila);
			libro=null;
		}
		
		
	cargarCursor(cursor);	
	}


	private void cargarCursor(int cursor) {
		// TODO Apéndice de método generado automáticamente
	
		
		libro=libros.get(cursor);
		txtIdLibros.setText(Integer.toString(libro.getIdLibros()));
		txtTitulo.setText(libro.getTitulo());
		txtAutor.setText(libro.getAutor());
		txtEditorial.setText(libro.getEditorial());
		txtIsbn.setText(libro.getIsbn());
		chckbxPrestado.setSelected(libro.isPrestado());
		txtFechaPrestamo.setText(libro.getFechaPrestamo().toString());
		txtFechaDevolucion.setText(libro.getFechaDevolucion().toString());
		
		libro=null;
		
		txtIdLibros.setEditable(false);
		txtTitulo.setEditable(false);
		txtAutor.setEditable(false);
		txtEditorial.setEditable(false);
		txtIsbn.setEditable(false);
		chckbxPrestado.setEnabled(false);
		txtFechaPrestamo.setEditable(false);
		txtFechaDevolucion.setEditable(false);
		
	}
	
	private void habilitarNavegador(Boolean navegar) {
		// TODO Apéndice de método generado automáticamente
		btnInicio.setEnabled(navegar);	btnAnterior.setEnabled(navegar);btnSiguiente.setEnabled(navegar);btnFinal.setEnabled(navegar);
	}


	private void habilitarPanelMantenilmiento(boolean habilitado) {
		// TODO Apéndice de método generado automáticamente	
		
		btnAgregar.setEnabled(habilitado);
		btnEditar.setEnabled(habilitado);
		btnBorrar.setEnabled(habilitado);
		btnGuardar.setEnabled(!habilitado);
		btnDeshacer.setEnabled(!habilitado);
	}
	
	private void editarPanelLibros(Boolean escribir) {
		// TODO Apéndice de método generado automáticamente
		
		//txtIdLibros.setEditable(escribir);
		txtTitulo.setEditable(escribir);
		txtAutor.setEditable(escribir);
		txtEditorial.setEditable(escribir);
		txtIsbn.setEditable(escribir);
		chckbxPrestado.setEnabled(escribir);
		//txtFechaPrestamo.setEditable(escribir);
		//txtFechaDevolucion.setEditable(escribir);
		txtIsbn.setBackground(Color.white);
		txtFechaDevolucion.setBackground(Color.white);
	}
	
	private void limpiarCajasTexto() {
		// TODO Apéndice de método generado automáticamente
		fechaDevolucion.agregarDias(5);
		
		libro=libros.get(cursor);
		txtIdLibros.setText(Integer.toString(0));
		txtTitulo.setText("");
		txtAutor.setText("");
		txtEditorial.setText("");
		txtIsbn.setText("");
		chckbxPrestado.setSelected(false);
		txtFechaPrestamo.setText(fechaPrestamo.toString());
		txtFechaDevolucion.setText(fechaDevolucion.toString());
		//txtFechaPrestamo.setText("");
		//txtFechaDevolucion.setText("");

	}
	private Boolean agregarLibro(Libro libro) throws ClassNotFoundException, SQLException {
		// TODO Apéndice de método generado automáticamente
		
		biblioteca.abrirConexion();
		agregado=biblioteca.agregarLibro(libro);
		biblioteca.cerrarConexion();
		
		return agregado;
	}
	private boolean modificarLibro(Libro libro, String isbnAnt) throws ClassNotFoundException, SQLException {
		biblioteca.abrirConexion();
		modificado=biblioteca.modificarLibro(libro, isbnAnt);
		biblioteca.cerrarConexion();
		
		return modificado;
	
	}
	
	private boolean borrarLibro(Libro libro) throws ClassNotFoundException, SQLException {
		// TODO Apéndice de método generado automáticamente
		
		biblioteca.abrirConexion();
		borrado=biblioteca.borrarLibro(libro);
		biblioteca.cerrarConexion();
		
		return borrado;

	}
	private void buscarLibros(String campo, String cadenaBusqueda) throws ClassNotFoundException, SQLException, ParseException, IsbnException {
		// TODO Apéndice de método generado automáticamente
		libros=new ArrayList<Libro>();		
		biblioteca.abrirConexion();
		libros=biblioteca.buscarLibro(campo, cadenaBusqueda);
		biblioteca.cerrarConexion();
		
		
	}

}
