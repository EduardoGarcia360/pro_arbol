package Clases;

import java.util.LinkedList;
import java.util.StringTokenizer;

public class Analizar {
	
	boolean EncabezadoActivado = false, VariablesActivado = false, ArbolActivado = false;
	LinkedList paraEncabezado = new LinkedList();
	LinkedList paraVariables = new LinkedList();
	
	public void Separar(String Datos){
		String[] TokensSeparar = Datos.split("\n");
		for(int i=0; i<TokensSeparar.length; i++){
			
			if( TokensSeparar[i].equals("ENCABEZADO")){
				int j=i;
				do{
					paraEncabezado.add(TokensSeparar[j]);
					j++;
					
				//}while(!TokensSeparar[j].equals("}"));
				}while(!TokensSeparar[j].equals("VARIABLES"));
				
			}else if( TokensSeparar[i].equals("VARIABLES")){
				int j=i;
				do{
					paraVariables.add(TokensSeparar[j]);
					j++;
					
				}while( j != TokensSeparar.length);
			}
		}
		System.out.println("encabezado");
		for(int i=0; i<paraEncabezado.size(); i++){
			System.out.println("posicion "+i+" - "+paraEncabezado.get(i));
		}
		System.out.println("variables");
		for(int i=0; i<paraVariables.size(); i++){
			System.out.println("posicion "+i+" - "+paraVariables.get(i));
		}
		VerificarEncabezado(paraEncabezado);
		VerificarVariables(paraVariables);
	}
	
	private void VerificarEncabezado(LinkedList ListadeEncabezado){
		String Linea="";
		for(int h=0; h<ListadeEncabezado.size(); h++){
			Linea += ListadeEncabezado.get(h);
		}
		//Linea += "}";
		
		int c=0;
		char caracter = 0;
		System.out.println("variable");
		for(int i=0; i<Linea.length(); i++){
			caracter = Linea.charAt(i);
			//System.out.println("posicion "+i+" - "+caracter);
			if( caracter == '{' || caracter == '}'){
				c++;
			}
		}
		
		if(c != 2){
			System.out.println("en encabezado falta una llave");
		}else{
			System.out.println("en encabezado texto correcto");
			//METODO PARA MANDAR LOS DATOS A LA ETIQUETA EN LA CLASE ARCHIVO.
		}
	}
	
	private void VerificarVariables(LinkedList ListadeVariables){
		String Linea="";
		for(int h=0; h<ListadeVariables.size(); h++){
			Linea += ListadeVariables.get(h);
		}
		//Linea += "}";
		
		int c=0;
		char caracter=0;
		System.out.println("variable");
		for(int i=0; i<Linea.length(); i++){
			caracter = Linea.charAt(i);
			//System.out.println("posicion "+i+" - "+caracter);
			if( caracter == '{' || caracter == '}'){
				c++;
			}
		}
		
		if( c != 2){
			System.out.println("en variable falta una llave");
		}else{
			System.out.println("en variable texto correcto");
			//METODO PARA MANDAR LOS DATOS A LA ETIQUETA EN LA CLASE ARCHIVO.
		}
	}
	
	

}
