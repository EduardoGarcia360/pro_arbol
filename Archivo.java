package Clases;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.TabStop.Alignment;
import com.itextpdf.text.pdf.PdfWriter;

public class Archivo {
	
	public boolean usoArchivo = false;
	public String NombreArchivo="";
	public static String TEXTO, NEGRITA, CURSIVA, SUBRAYADO;
	
	public String AbrirARCHIVO(String ruta){
		
		String ree = ruta.replace('\\', '%');
		String[] nomarchi = ree.split("%");
		int ultimo = nomarchi.length-1;
		NombreArchivo = nomarchi[ultimo];
		
			File archivo = new File(ruta);
			
			String temp="", retorno="";
			try {
				FileReader leer = new FileReader(archivo);
				BufferedReader BR = new BufferedReader(leer);
				try {
					while((temp = BR.readLine()) != null){
						retorno = retorno + temp + "\n";
					}
					usoArchivo = true;
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
			
			String saltodelinea = System.getProperty("line.separator");
			
			Document archi = new Document(PageSize.LETTER);
			PdfWriter escritor = PdfWriter.getInstance(archi, new FileOutputStream("ArchivodeArbol.pdf"));
			
			Membrete Imagen_Encabezado = new Membrete();
			escritor.setPageEvent(Imagen_Encabezado);
			Image imagen = Image.getInstance("C:\\Users\\Edu\\Desktop\\grafo1.png");
			imagen.setAlignment(Element.ALIGN_CENTER);
			archi.open();
			Paragraph txt = new Paragraph(TEXTO);
			if(NEGRITA.equals("ON")){
				txt.getFont().setStyle(Font.BOLD);	
			}
			if(CURSIVA.equals("ON")){
				txt.getFont().setStyle(Font.ITALIC);
			}
			if(SUBRAYADO.equals("ON")){
				txt.getFont().setStyle(Font.STRIKETHRU);
			}
			txt.getFont().setSize(25);
			txt.setAlignment(Element.ALIGN_CENTER);
			archi.add(txt);
			
			archi.add(new Paragraph(saltodelinea));
			archi.add(new Paragraph(saltodelinea));
			archi.add(new Paragraph(saltodelinea));
			archi.add(new Paragraph(saltodelinea));
			archi.add(new Paragraph(saltodelinea));
			archi.add(new Paragraph(saltodelinea));
			archi.add(new Paragraph(saltodelinea));
			archi.add(new Paragraph(saltodelinea));
			//archi.add(new Paragraph("posicion de este texto 2"));
			archi.add(new Paragraph(saltodelinea));
			archi.add(new Paragraph(saltodelinea));
			archi.add(imagen);
			archi.close();
		} catch (FileNotFoundException e) {e.printStackTrace();}
		  catch (DocumentException e) {e.printStackTrace();}
		try{
		//File elpdf = new File("ArchivodeArbol.pdf");
			//Desktop des = Desktop.getDesktop();
			//if(elpdf.exists()){
				//des.open(elpdf);
			//}
		}catch(Exception e){
			
		}
	}
	
	public void CrearGRAPHVIZ(String contenido){
		
		try {
			String textoGraphviz = "digraph G{" + contenido + "}";
			File Archivo_Graphviz = new File("nuevo.gv");
			FileWriter escritor = new FileWriter(Archivo_Graphviz);
			BufferedWriter bw = new BufferedWriter(escritor);
			PrintWriter pw = new PrintWriter(bw);
			
			pw.write(textoGraphviz);
			
			pw.close();
			bw.close();
		} catch (IOException e) {
			System.out.println("error" + e);
		}
		
		try{
			String rutaDot = "C:\\E\\Progra\\Librerias_Externas\\release\\bin\\dot.exe";
			String archivoGraphviz = "C:\\E\\Progra\\EclipseProjects\\ArbolGenealogico\\nuevo.gv";
			String archivoImagen = "C:\\Users\\Edu\\Desktop\\grafo1.png";
			String tParam = "-Tpng";
		    String tOParam = "-o";
		    
		    String[] cmd = new String[5];
		      cmd[0] = rutaDot;
		      cmd[1] = tParam;
		      cmd[2] = archivoGraphviz;
		      cmd[3] = tOParam;
		      cmd[4] = archivoImagen;
		      
		      Runtime ruti = Runtime.getRuntime();
		      
		      ruti.exec(cmd);
		}catch(Exception e){
			System.out.println("error" + e);
		}
		
	}
	
	public void crearHTML(String datos){
		
		String parahtml="";
		if(usoArchivo == true){
			parahtml = NombreArchivo;
		}else{
			parahtml = "creado en area para codigo.";
		}
		
		String head = "<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />"
				+"<title>Tabla de Lexemas</title></head><body  style=\"background:#CCC\" ><p><img src=\"escudo.png\" "
				+" alt=\"\" style=\"float:right;\"/><h2>";
		String mitad = "<font color=\"green\">UNIVERSIDAD DE SAN CARLOS<br/>FACULTAD DE INGENIERIA<br/>ESCUELA DE CIENCIAS<br/>"
				+"INGENIERIA EN CIENCIAS Y SISTEMAS<br/>LENGUAJES FORMALES Y DE PROGRAMACION</font></h2><br/><br/><br/><br/>";
		
		String nombreFuente = "<center><h3>Archivo Fuente: <font color=\"red\">"+parahtml+"</font><br/><br/><br/>";
		
		String nombreSalida = "Archivo Salida: <font color=\"red\">tabla de lexemas.html</font></h3></center></p><center>";
		
		String miTabla = "<table bordercolor=\"blue\" rules=\"all\">"
		+"<tr><td>No. Token</td> <td>Token</td> <td>Lexema</td> <td>Palabra Reservada</td> <td>Tipo de Token</td></tr>";
		
		//FOR
		String datosv2 = datos.substring(0, datos.length()-1);
		String [] LexemasenCodigo = datosv2.split("%");
		String contenidoFila="";
		int PosFinal = LexemasenCodigo.length;
			for(int i=0; i<PosFinal; i++){
				
				contenidoFila += "<tr><td>"+LexemasenCodigo[i]+"</td><td>"+LexemasenCodigo[i+1]+"</td><td>"+LexemasenCodigo[i+2]+"</td><td>"+LexemasenCodigo[i+3]+"</td>"+"<td>"+LexemasenCodigo[i+4]+"</td></tr>";
				i = i + 4;
			}
		
		String finalHead = "</table></center></body></html>";
		
		String Pagina = head + mitad + nombreFuente + nombreSalida + miTabla + contenidoFila + finalHead;
		
		try{
			File correcto = new File("tabla de lexemas.html");
			FileWriter escritor = new FileWriter(correcto);
			BufferedWriter BW = new BufferedWriter(escritor);
			PrintWriter salida = new PrintWriter(BW);
			
			salida.write(Pagina);
			
			salida.close();
			BW.close();
			
			//Desktop des = Desktop.getDesktop();
			//if(correcto.exists()){
				//des.open(correcto);
			//}
		}catch(Exception e){
			JOptionPane.showMessageDialog(null,"Error al guardar", "Notificacion", JOptionPane.ERROR_MESSAGE);
		}
		
		
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
