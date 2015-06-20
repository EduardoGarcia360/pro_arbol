package Clases;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class Archivo {
	
	public String AbrirARCHIVO(String ruta){
			File archivo = new File(ruta);
			String temp="", retorno="";
			try {
				FileReader leer = new FileReader(archivo);
				BufferedReader BR = new BufferedReader(leer);
				try {
					while((temp = BR.readLine()) != null){
						retorno = retorno + temp + "\n";
					}	
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, "No hay datos en el archivo seleccionado","Advertencia",1);
					e.printStackTrace();
				}
				try {
					BR.close();
				} catch (IOException e) {}
			} catch (FileNotFoundException e) {}
			return retorno;
	}
	
	public void Guardar(String texto, String Ruta, boolean abierto){
		if(abierto == true){
			try{
				File archivo = new File(Ruta);
				if(archivo.delete() == true){
					File reemplazo = new File(Ruta);
					PrintWriter printwriter = new PrintWriter(reemplazo);
	                printwriter.print(texto);
	                printwriter.close();
	                JOptionPane.showMessageDialog(null, "Archivo Guardado Exitosamente.","Notificacion", JOptionPane.INFORMATION_MESSAGE);
				}else{
					JOptionPane.showMessageDialog(null, "Error.","Notificacion", JOptionPane.WARNING_MESSAGE);
				}
			}catch(Exception e){}
		}else
			if(abierto == false){
				JFileChooser FC = new JFileChooser("C:\\Users\\Edu\\Desktop\\");
				FileNameExtensionFilter filtro = new FileNameExtensionFilter("lfp","ava");
		        FC.addChoosableFileFilter(filtro);
		        int s = FC.showSaveDialog(null);
		        try{
		            if (s == JFileChooser.APPROVE_OPTION){
		                File JFC = FC.getSelectedFile();
		                String ruta = JFC.getAbsolutePath();
		                PrintWriter printwriter = new PrintWriter(JFC);
		                printwriter.print(texto);
		                printwriter.close();
		                
		                if( !(ruta.endsWith(".ava")) && !(ruta.endsWith(".lfp")) ){
		                	File temp = new File(ruta+".lfp");
		                    JFC.renameTo(temp);
		                    JOptionPane.showMessageDialog(null, "El Archivo se Guardo con la extencion .lfp","Notificacion", JOptionPane.INFORMATION_MESSAGE);
		                }else{
		                	JOptionPane.showMessageDialog(null, "Archivo Guardado","Notificacion", JOptionPane.INFORMATION_MESSAGE);
		                }
		            }
		        }catch (Exception e){
		            JOptionPane.showMessageDialog(null,"Error al guardar", "Notificacion", JOptionPane.ERROR_MESSAGE);
		        }
			}
		
	}
	
	public void GuardarComo(String texto){
		JFileChooser FC = new JFileChooser("C:\\Users\\Edu\\Desktop\\");
		FC.setDialogTitle("Guardar como...");
        int s = FC.showSaveDialog(null);
        try{
            if (s == JFileChooser.APPROVE_OPTION){
                File JFC = FC.getSelectedFile();
                String ruta = JFC.getAbsolutePath();
                PrintWriter printwriter = new PrintWriter(JFC);
                printwriter.print(texto);
                printwriter.close();
                
                if( !(ruta.endsWith(".ava")) && !(ruta.endsWith(".lfp")) ){
                	File temp = new File(ruta+".lfp");
                    JFC.renameTo(temp);
                    JOptionPane.showMessageDialog(null, "El Archivo se Guardo con la extencion .lfp","Notificacion", JOptionPane.INFORMATION_MESSAGE);
                }else{
                	JOptionPane.showMessageDialog(null, "Archivo Guardado","Notificacion", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(null,"Error al guardar", "Notificacion", JOptionPane.ERROR_MESSAGE);
        }
	}
	
	public void CrearPDF() throws IOException, DocumentException{
		
		try {
			
			String Encabezado = "UNIVERSIDAD DE SAN CARLOS DE GUATEMALA"
					+"\nFACULTAD DE INGENIERIA"
					+"\nESCUELA DE CIENCIAS"
					+"\nINGENIERIA EN CIENCIAS Y SISTEMAS"
					+"\nLENGUAJES FORMALES Y DE PROGRAMACION";
			String saltodelinea = System.getProperty("line.separator");
			
			Document archi = new Document(PageSize.LETTER);
			PdfWriter escritor = PdfWriter.getInstance(archi, new FileOutputStream("salida.pdf"));
			Membrete Imagen_Encabezado = new Membrete();
			escritor.setPageEvent(Imagen_Encabezado);
			archi.open();
			
			archi.add(new Paragraph(Encabezado));
			archi.add(new Paragraph(saltodelinea));
			archi.add(new Paragraph("posicion de este texto"));
			archi.add(new Paragraph(saltodelinea));
			archi.add(new Paragraph(saltodelinea));
			archi.add(new Paragraph(saltodelinea));
			archi.add(new Paragraph("posicion de este texto 2"));
			
			archi.close();
		} catch (FileNotFoundException e) {e.printStackTrace();}
		  catch (DocumentException e) {e.printStackTrace();}
	}

}
