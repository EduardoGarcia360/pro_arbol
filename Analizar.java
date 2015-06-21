package Clases;

import java.awt.Font;
import java.util.LinkedList;
import java.util.StringTokenizer;

import javax.swing.JOptionPane;

public class Analizar {
	
	boolean EncabezadoActivado = false, VariablesActivado = false, ArbolActivado = false;
	
	
	public void Separar(String Datos){
		LinkedList<String> paraEncabezado = new LinkedList<String>();
		LinkedList<String> paraVariables = new LinkedList<String>();
		String[] TokensSeparar = Datos.split("\n");
		boolean cambio=false;
		for(int i=0; i<TokensSeparar.length; i++){
			
			if( TokensSeparar[i].equals("ENCABEZADO")){
				int j=i;
				
				while(!TokensSeparar[j].equals("VARIABLES")){
					paraEncabezado.add(TokensSeparar[j]);
					j++;
				}
				
				cambio = true;
				EncabezadoActivado = true;
			}else if( TokensSeparar[i].equals("VARIABLES")){
				
				int j=i;
				
				while(j != TokensSeparar.length){
					paraVariables.add(TokensSeparar[j]);
					j++;
				}
				
				cambio = true;
				VariablesActivado = true;
			}
		}
		if( cambio == true ){
			
			if( VariablesActivado == true ){
				VerificarVariables(paraVariables);
			}
			if( EncabezadoActivado == true){
				VerificarEncabezado(paraEncabezado);
			}
		}else{
			JOptionPane.showMessageDialog(null, "error");
		}
		
		
	}
	
	private void VerificarEncabezado(LinkedList<String> ListadeEncabezado){
		String Linea="";
		for(int j=0; j<ListadeEncabezado.size(); j++){
			Linea += ListadeEncabezado.get(j);
		}
		//Linea += "}";
		
		int c=0;
		char caracter = 0;
		for(int i=0; i<Linea.length(); i++){
			caracter = Linea.charAt(i);
			if( caracter == '{' || caracter == '}'){
				c++;
			}
		}
		
		if(c != 2){
			System.out.println("en encabezado falta una llave");
			//METODO ENCABEZADO DEFAULT
		}else{
			System.out.println("en encabezado texto correcto");
			Metodo_Encabezado(ListadeEncabezado);
		}
	}
	
	private void VerificarVariables(LinkedList<String> ListadeVariables){
		String Linea="";
		for(int h=0; h<ListadeVariables.size(); h++){
			Linea += ListadeVariables.get(h);
		}
		
		int c=0;
		char caracter=0;
		for(int i=0; i<Linea.length(); i++){
			caracter = Linea.charAt(i);
			if( caracter == '{' || caracter == '}'){
				c++;
			}
		}
		
		if( c != 2){
			System.out.println("en variable falta una llave");
		}else{
			System.out.println("en variable texto correcto");
			Metodo_Variables(ListadeVariables);
		}
	}
	
	private void Metodo_Encabezado(LinkedList<String> Encabezado){
		String texto="";
		for(int i=2; i<Encabezado.size()-2; i++){
			String token = Encabezado.get(i).toString();
			
			if(token.endsWith(";")){ //SI TERMINA CON ;
				
				String[] Tokens = token.split("\\(");
				if(Validar(Tokens[0])==true){
					
					if(Tokens[0].equals("Imagen:")){
						char caracter=0;
						int c=0;
						for(int pos=0; pos<Tokens[1].length(); pos++){
							caracter = Tokens[1].charAt(pos);
							if( caracter == ')'){
								c++;
							}
						}
						if( c==1 ){
							String datos = Tokens[1].substring(0, Tokens[1].length()-2);
							String[] contenido = datos.split(",");
							System.out.println();
							System.out.println(".- " + contenido[0]);
							System.out.println();
							System.out.println(".- " + contenido[1]);
							System.out.println();
							System.out.println(".- " + contenido[2]);
						}else{
							//INCORRECTO FALTA (
						}
						
					}else if(Tokens[0].equals("Texto")){
						char caracter=0;
						int c=0;
						for(int pos=0; pos<Tokens[1].length(); pos++){
							caracter = Tokens[1].charAt(pos);
							if( caracter == ')'){
								c++;
							}
						}
						if( c==1 ){
							String datos = Tokens[1].substring(0, Tokens[1].length()-2);
							System.out.println("datos: "+datos);
							char[] textoentrecomillas = datos.toCharArray();
							int f = textoentrecomillas.length;
							//String texto="";
							if(textoentrecomillas[0] == 34 && textoentrecomillas[f-1] == 34){
								for(int txt=1; txt<f-1; txt++){
									texto += Character.toString(textoentrecomillas[txt]);
								}
								String[] algo = texto.split("%");
								System.out.println("de arreglo algo "+ algo[0]);
								System.out.println("lo que esta en comillas es " + texto);
							}else{
								//NO TIENE COMILLAS
							}
						}else{
							//INCORRECTO FALTA (
						}
					}else if(Tokens[0].equals("Negrita")){
						char caracter=0;
						int c=0;
						for(int pos=0; pos<Tokens[1].length(); pos++){
							caracter = Tokens[1].charAt(pos);
							if( caracter == ')'){
								c++;
							}
						}
						if( c== 1){
							String datos = Tokens[1].substring(0, Tokens[1].length()-2);
							if(datos.equals("ON")){
								Font negrita = new Font("Arial", Font.BOLD, 12);
								System.out.println("tex: " + "\033[1mArbol");
								//System.out.println("texto en negrita " + )
							}else if( !datos.equals("OFF")){
								System.out.println("escribio otra cosa");
							}
							
						}else{
							//INCORRECTO FALTA (
						}
					}else if(Tokens[0].equals("Subrayado")){
						
					}else if(Tokens[0].equals("Cursiva")){
						
					}
				}else{
					System.out.println("imagen o texto o .. incorrecto");
					//IMAGEN O TEXTO O.. INCORRECTO
				}
			}else{
				System.out.println("no termina con punto y coma");
				//ERROR NO TERMINA CON PUNTO Y COMA
			}
		}
	}
	
	private boolean Validar(String a){
		String [] Reservadas = {"Imagen:", "Texto", "Negrita", "Cursiva", "Subrayado"};
		boolean correcto = false;
		for(int i=0; i<Reservadas.length; i++){
			if(a.equals( Reservadas[i] ) ){
				correcto = true;
			}
		}
		return correcto;
	}
	
	private boolean esnumero(String dato){
        try{
            Integer.parseInt(dato);
        }catch(NumberFormatException nfe){
            return false;
        }
        return true;
    }
	
	private void Metodo_Variables(LinkedList<String> Variables){
		LinkedList<String> temporal = new LinkedList<String>();
		LinkedList<String> ListadeVariables = new LinkedList<String>();
		String tmp ="";
		for(int i=2; i<Variables.size()-1; i++){
			tmp += Variables.get(i).toString();
		}
		
		String[] auno = tmp.split(";");
		for(int j=0; j<auno.length; j++){
			temporal.add(auno[j]);
		}
		System.out.println("de lista temporal");
		for(int h=0; h<temporal.size(); h++){
			String linea = temporal.get(h).toString() + ";";
			ListadeVariables.add(linea);
		}
		
		for(int p=0; p<ListadeVariables.size(); p++){
			String Token = ListadeVariables.get(p);
			char[] aChartmp = Token.toCharArray();
			int c=0;
			char ca=0;
			String LineaVariables="";
			for(int w=0; w<aChartmp.length; w++){
				ca = aChartmp[w];
				if(ca == ':' || ca == '=' || ca == ';'){
					c++;
					LineaVariables += Character.toString(aChartmp[w]);
				}else{
					LineaVariables += Character.toString(aChartmp[w]);
				}
			}
			
			if(c == 3){
				//SI CUMPLE CON LAS CONDICIONES
				String[] arreglo1 = LineaVariables.split(":");
				String ae = arreglo1[1];
				String[] arreglo2 = ae.split("=");
				System.out.println("pos 0"+ arreglo1[0]);
				System.out.println("pos 1"+ arreglo2[0]);
				System.out.println("pos 2"+ arreglo2[1]);
			}else{
				//LE FALTA : O = O ;
			}
		}
	}

}
