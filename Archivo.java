package Clases;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class Archivo {
	
	public void CrearPDF(){
		Document archi = new Document();
		try {
			String Encabezado = "UNIVERSIDAD DE SAN CARLOS DE GUATEMALA"
					+"\nFACULTAD DE INGENIERIA"
					+"\nESCUELA DE CIENCIAS"
					+"\nINGENIERIA EN CIENCIAS Y SISTEMAS"
					+"\nLENGUAJES FORMALES Y DE PROGRAMACION";
			PdfWriter.getInstance(archi, new FileOutputStream("Salida.pdf"));
			archi.open();
			archi.add(new Paragraph(Encabezado));
			archi.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
