package Clases;

import javax.swing.JOptionPane;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

public class Membrete extends PdfPageEventHelper {
	
	 private Image imagen;
	 PdfPTable table = new PdfPTable(2);
	 public static String rutaImagen;
	 public static int PosX, PosY;
	 
	public Membrete(){
		 try
	        {
	            try{
	            	imagen = Image.getInstance(rutaImagen);
	            	//imagen.scaleToFit(PosX, PosY);
	            }catch(Exception e){
	            	imagen = Image.getInstance("escudo.png");
	            	//imagen.scaleToFit(PosX, PosY);
	            }
			 	
	            imagen.setAbsolutePosition(400, 590f);          
	            
	            table.setTotalWidth(350f);            
	            
	        }catch(Exception r)
	        {
	            JOptionPane.showMessageDialog(null, "Error al cargar la imagen","Advertencia - Error",JOptionPane.WARNING_MESSAGE);
	        }
	}
	
	public void onEndPage(PdfWriter writer, Document document) {

        try{            
            document.add(imagen);
            table.writeSelectedRows(0, -1, 140f, 700f, writer.getDirectContent());
            
         }catch(Exception doc)
         {
             doc.printStackTrace();
         }         
	}

}
