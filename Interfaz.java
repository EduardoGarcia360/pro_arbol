package Clases;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JButton;

import com.itextpdf.text.DocumentException;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.JTextArea;
import javax.swing.JScrollBar;

public class Interfaz extends JFrame implements ActionListener {

	private JPanel contentPane;
	Archivo ar = new Archivo();
	Analizar analiza = new Analizar();
	JButton btnAbrir, btnAcerca, btnAnalizar, btnGuardarc, btnGuardar;
	JTextArea txtrTextarea;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Interfaz frame = new Interfaz();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Interfaz() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 587, 462);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		btnAbrir = new JButton("Abrir");
		btnAbrir.addActionListener(this);
		btnAbrir.setBounds(28, 44, 89, 23);
		contentPane.add(btnAbrir);
		
		btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(this);
		btnGuardar.setBounds(28, 89, 89, 23);
		contentPane.add(btnGuardar);
		
		btnGuardarc = new JButton("Guardarc");
		btnGuardarc.addActionListener(this);
		btnGuardarc.setBounds(28, 136, 89, 23);
		contentPane.add(btnGuardarc);
		
		btnAnalizar = new JButton("Analizar");
		btnAnalizar.addActionListener(this);
		btnAnalizar.setBounds(28, 185, 89, 23);
		contentPane.add(btnAnalizar);
		
		btnAcerca = new JButton("Acerca");
		btnAcerca.addActionListener(this);
		btnAcerca.setBounds(28, 233, 89, 23);
		contentPane.add(btnAcerca);
		
		txtrTextarea = new JTextArea();
		txtrTextarea.setText("textArea");
		txtrTextarea.setBounds(145, 21, 416, 392);
		contentPane.add(txtrTextarea);
	}
	
	void ObtenerRutaDeArchivo(){
		try{
			String ruta, datos;
			JFileChooser archivo = new JFileChooser("C:\\Users\\Edu\\Desktop\\");
			FileNameExtensionFilter filtro = new FileNameExtensionFilter("Archivos MSR", "msr");
			archivo.setFileFilter(filtro);
			archivo.showOpenDialog(archivo);
			ruta = archivo.getSelectedFile().getAbsolutePath();
			datos = ar.AbrirARCHIVO(ruta);
			txtrTextarea.setText(datos);
		}catch(Exception ex){
			
			JOptionPane.showMessageDialog(null,"No seleccionaste ningun archivo","Advertencia", JOptionPane.WARNING_MESSAGE);
		}
		
	}
	
	public void actionPerformed(ActionEvent e){
		if(e.getSource() == btnAbrir){
				ObtenerRutaDeArchivo();
		}
		
		if(e.getSource() == btnAnalizar){
			String codigo = txtrTextarea.getText();
			analiza.Separar(codigo);
		}
	}
}
