package Clases;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.LinkedList;

import javax.swing.JOptionPane;

import com.itextpdf.text.DocumentException;

public class Analizar {
	
	public LinkedList<String> L1 = new LinkedList<String>();
	public LinkedList<String> L3 = new LinkedList<String>();
	public LinkedList<String> D1 = new LinkedList<String>();
	public LinkedList<String> D2 = new LinkedList<String>();
	public LinkedList<String> paraImprimir = new LinkedList<String>();
	Archivo ar = new Archivo();
	int contadorlexemas=0;
	String LexemasdeCodigo="";
	
	public void Separar(String Datos){
		LimpiarTodo();
		boolean EncabezadoActivado = false, VariablesActivado = false, ArbolActivado = false, cambio=false;
		LinkedList<String> paraEncabezado = new LinkedList<String>();
		LinkedList<String> paraVariables = new LinkedList<String>();
		LinkedList<String> paraArbol = new LinkedList<String>();
		String[] TokensSeparar = Datos.split("\n");
		
		
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
				
				while(!TokensSeparar[j].equals("ARBOL")){ //j != TokensSeparar.length
					paraVariables.add(TokensSeparar[j]);
					j++;
				}
				
				cambio = true;
				VariablesActivado = true;
			}else if( TokensSeparar[i].equals("ARBOL")){
				int j=i;
				while(j != TokensSeparar.length){
					paraArbol.add(TokensSeparar[j]);
					j++;
				}
				cambio = true;
				ArbolActivado = true;
			}
		}//FIN FOR
		if( cambio == true ){
			if( EncabezadoActivado == true){
				VerificarEncabezado(paraEncabezado);
			}
			if( VariablesActivado == true ){
				VerificarVariables(paraVariables);
			}
			if( ArbolActivado == true ){
				VerificarArbol(paraArbol);
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
			contadorlexemas++;
			LexemasdeCodigo += contadorlexemas + "%Segmento%Encabezado%SI%Inicio Segmento%";
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
			Metodo_Variables(ListadeVariables);
		}
	}
	
	private void VerificarArbol(LinkedList<String> ListadeArbol){
		String Linea = "";
		for(int h=0; h<ListadeArbol.size(); h++){
			Linea += ListadeArbol.get(h);
		}
		//Linea += "}";
		int c=0;
		int c2=0;
		char caracter = 0;
		for(int i=0; i<Linea.length(); i++){
			caracter = Linea.charAt(i);
			if( caracter == '{'){
				c++;
			}else if( caracter == '}'){
				c2++;
			}
		}
		
		if( c == c2){
			Metodo_Arbol(ListadeArbol);
			
		}else{
			//FALTA UNA LLAVE
			System.out.println("falta una llave arbol");
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
							//CONTENIDO[0] ES RUTA DE IMAGEN
							String rutaImagen = contenido[0];
							String tmp = rutaImagen.replace('/', '\\');
							String ruta = QuitarComillas(tmp);
							Membrete.rutaImagen = ruta;
							if(esnumero(contenido[1])){
								int enx = Integer.parseInt(contenido[1]);
								Membrete.PosX = enx;
							}else{
								
							}
							if(esnumero(contenido[2])){
								int eny = Integer.parseInt(contenido[2]);
								Membrete.PosY = eny;
							}else{
								
							}
							contadorlexemas++;
							LexemasdeCodigo += contadorlexemas + "%Declaracion%Imagen%SI%Declara imagen%";
							
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
							
							char[] textoentrecomillas = datos.toCharArray();
							int f = textoentrecomillas.length;
							if(textoentrecomillas[0] == 34 && textoentrecomillas[f-1] == 34){
								for(int txt=1; txt<f-1; txt++){
									texto += Character.toString(textoentrecomillas[txt]);
								}
								Archivo.TEXTO = texto;
								contadorlexemas++;
								LexemasdeCodigo += contadorlexemas + "%Declaracion%Texto%SI%Declara cadena%";
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
								Archivo.NEGRITA = "ON";
							}else if( datos.equals("OFF")){
								Archivo.NEGRITA = "OFF";
							}else{
								Archivo.NEGRITA = "OFF";
								//ERROR
							}
							contadorlexemas++;
							LexemasdeCodigo += contadorlexemas + "%Declaracion%Negrita%SI%Modifica texto a negrita%";
						}else{
							//INCORRECTO FALTA (
						}
						
					}else if(Tokens[0].equals("Subrayado")){
						
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
								Archivo.SUBRAYADO = "ON";
							}else if( datos.equals("OFF")){
								Archivo.SUBRAYADO = "OFF";
							}else{
								Archivo.SUBRAYADO = "OFF";
								//ERROR
							}
							contadorlexemas++;
							LexemasdeCodigo += contadorlexemas + "%Declaracion%Subrayado%SI%Modifica texto a subrayado%";
						}else{
							//INCORRECTO FALTA (
						}
						
					}else if(Tokens[0].equals("Cursiva")){
						
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
								Archivo.CURSIVA = "ON";
							}else if( datos.equals("OFF")){
								Archivo.CURSIVA = "OFF";
							}else{
								Archivo.CURSIVA = "OFF";
								//ERROR
							}
							contadorlexemas++;
							LexemasdeCodigo += contadorlexemas + "%Declaracion%Cursiva%SI%Modifica texto a cursivo%";
						}else{
							//INCORRECTO FALTA (
						}
						
					}else if(Tokens[0].equals("imprimir")){
						
						
						String nuevo = token.replaceFirst("\\(", "\\(%");
						String[] contenido_paraImprimir = nuevo.split("%");
						String condiciones = contenido_paraImprimir[1].substring(0, contenido_paraImprimir[1].length()-2);
						
						Imprimir(condiciones);
					}
				}else{
					System.out.println("imagen o texto o .. incorrecto");
					//IMAGEN O TEXTO O.. INCORRECTO
				}
			}else{
				System.out.println("no termina con punto y coma");
				//ERROR NO TERMINA CON PUNTO Y COMA
			}
		}//FIN FOR
		for(int g=0; g<paraImprimir.size(); g++){
			System.out.println();
			System.out.println("posicion " + g + "->"+paraImprimir.get(g)+"<-");
		}
	}
	
	private void Imprimir(String linea){
		LinkedList<String> almacenar = new LinkedList<String>();
		boolean fin = false;
		int estado = 0, indice = 0;
		char token;
		linea += "#";
		
		String lexema="", base="", exponente="", numero="", simbolo="";
		int cantidad=0, aux_multi=0;
		String Operando="";
		try{
			while(fin != true){
				token = linea.charAt(indice);
				switch ( estado ){
				case 0:
					if(Character.isWhitespace(token)){
						//PARA VER SI ES UN ESPACIO EN BLANCO
						indice++;
						estado=0;
						
					}else if(essimbolo(token) == true){
						/**
						 * SI ES UN SIMBOLO 
						 * ¿QUE SIMBOLO ES?
						 */
						if(token == '"'){
							indice++;
							estado = 1;
							
						}else if(token == '+'){
							/**
							 * SI VIENE UN '+' QUIERE DECIR QUE VA A CONCATENAR
							 * EJEMPLO (1): "TEXTO" + "CONCATENADO"
							 * EJEMPLO (2): "EL NUMERO ES"+50
							 */
							char siguiente_caracter = linea.charAt(indice+1);
							
							if(siguiente_caracter == '"'){
								indice++;
								estado = 0;
								
							}else if(Character.isDigit(siguiente_caracter)){
								indice++;
								estado = 2;
								
							}else if(Character.isWhitespace(siguiente_caracter)){
								indice++;
								estado=0;
							}
						}else if(token == '('){
							/**
							 * CONDICIONAR SI EL SIGUIENTE ES NUMERO
							 */
							indice++;
							estado=2;
							
						}else if(token == '#'){
							/**
							 * FINAL DE LINEA
							 */
							estado = 10;
						}else{
							/**
							 * ERROR NO ES UN SIMBOLO VALIDO
							 * PUEDE VENIR ASI
							 * %%%ALGO
							 */
						}
						
					}else if(Character.isDigit(token)){
						//PARA VER SI ES DIGITO
						indice++;
						estado = 2;
						lexema += Character.toString(token);
						
					}else if(Character.isLetter(token)){
						//PARA VER SI ES LETRA
						
						indice++;
						estado = 1;
						lexema += Character.toString(token);
						
						
					}else if(token == '#'){
						estado = 10;
					}
					break;
				case 1:
					/**
					 * CASE PARA LETRAS---------------------------------------------------------------------------------------------------------
					 */
					if(Character.isWhitespace(token)){
						/**
						 * PUEDE VENIR ASI
						 * "LA CASA ESTA EN"
						 */
						indice++;
						estado = 1;
						lexema += Character.toString(token);
						
					}else if(token == '"'){
						//QUIERE DECIR QUE FINALIZO EL TEXTO ENTRE COMILLAS
						char siguiente_caracter = linea.charAt(indice+1);
						if(siguiente_caracter == '+'){
							/**
							 * PUEDE VENIR DE ALGUNA DE ESTAS FORMAS:
							 * "LA CASA" + "ES ROJA"
							 * "EL NUMERO ES " + 35
							 */
							indice++;
							estado = 0;
							almacenar.add(lexema);
							/**
							 * UNA VEZ AGREGADO A LA LISTA PASAMOS A LIMPIAR EL STRING LEXEMA
							 * PARA QUE QUEDE ASI:
							 * [LA CASA][..]...[..]
							 * EN LA LISTA
							 */
							lexema="";
							
						}else if(siguiente_caracter == '#'){
							//SI ES EL FINAL DE LA LINEA
							almacenar.add(lexema);
							lexema = "";
							estado = 10;
							
						}else if(Character.isWhitespace(siguiente_caracter)){
							indice++;
							estado=0;
						}else{
							/**
							 * ES ERROR POR QUE NO SE PUEDE LO SIGUIENTE:
							 * "LA CASA"56
							 * "LA CASA"ABC
							 */
						}
					}else if(token == '#'){
						//SI ES EL FINAL DE LA LINEA
						estado = 10;
						
					}else if(essimbolo(token)){
						/**
						 * PUEDE VENIR ASI:
						 * "LA CASA DE JAVIER ES: VERDE CON ROJO"
						 */
						indice++;
						estado = 1;
						lexema += Character.toString(token);
						
					}else if(Character.isLetter(token)){
						//SI ES LETRA
						indice++;
						estado = 1;
						lexema += Character.toString(token);
						if(lexema.equals("sqrt")){ //--------------------------- VERIFICA POSIBLE PALABRA RESERVADA
							Operando = lexema;
							lexema="";
							estado = 0;
						}else if(lexema.equals("exp")){
							Operando = lexema;
							lexema="";
							estado=0;
						}else if(lexema.equals("fact")){
							Operando = lexema;
							lexema="";
							estado=0;
						}
					}else{
						/**
						 * ES ERROR
						 */
						System.out.println("error:"+lexema);
						estado = 10;
					}
					break;
				case 2:
					/**
					 * CASE PARA NUMEROS.---------------------------------------------------------------------------------------------------------
					 */
					if(Character.isWhitespace(token)){
						/**
						 * SI VIENE CON ESPACIO POR EJEMPLO:
						 * 5            6       4    +
						 * ENTONCES SOLO AVANZAMOS Y LO TOMAMOS ASI
						 * 564+
						 */
						indice++;
						estado = 2;
						
					}else if(Character.isDigit(token)){
						//SI ES NUMERO
						indice++;
						estado = 2;
						lexema += Character.toString(token);
						
					}else if(token == '+'){
						/**
						 * POR EJEMPLO VIENE ASI: ..3000+50
						 * ACA ESTARIAMOS EN EL SIGNO '+' ACTUALMENTE
						 */
						
						char siguiente_caracter = linea.charAt(indice+1);
						
						if(simbolo.equals("-")){
							//6+50-4
							cantidad = cantidad - Integer.parseInt(lexema);
							lexema="";
							char siguiente = linea.charAt(indice+1);
							if(siguiente == '"'){
								String tmp = Integer.toString(cantidad);
								almacenar.add(tmp);
								cantidad=0;
								indice++;
								estado=0;
								
							}else if(Character.isDigit(siguiente)){
								simbolo = Character.toString(token);
								indice++;
								estado=2;
							}
						}else if(simbolo.equals("+")){
							cantidad = cantidad + Integer.parseInt(lexema);
							lexema="";
							char siguiente = linea.charAt(indice+1);
							if(siguiente == '"'){ //4+3+"ALGO"
								String tmp = Integer.toString(cantidad);
								almacenar.add(tmp);
								cantidad=0;
								simbolo="";
								indice++;
								estado=0;
							}else if(Character.isDigit(siguiente)){ //4+3+2
								simbolo = Character.toString(token);
								indice++;
								estado=2;
							}else if(Character.isWhitespace(siguiente)){
								indice++;
								estado=0;
							}else{
								almacenar.add("error");
								estado=10;
							}
						}else if(Character.isDigit(siguiente_caracter)){
							simbolo = Character.toString(token);
							/**
							 * SI EL SIGUIENTE DEL SIGNO '+' ES DIGITO
							 * EJEMPLO: 3000+50
							 * ENTONCES CONVERTIMOS A INT LA CANTIDAD QUE YA TRAIAMOS
							 * CANTIDAD = 3000 DESPUES VACIAMOS LEXEMA Y COMO EL SIGUIENTE 
							 * ES NUMERO ENTONCES NOS QUEDAMOS EN EL CASE 2
							 */
							cantidad += (int) Integer.parseInt(lexema);
							lexema="";
							indice++;
							estado = 2;
							
						}else if(siguiente_caracter == '"'){
							/**
							 * SI EL SIGUIENTE DEL SIGNO '+' ES ' " '
							 * EJEMPLO (1): 3000+"CADENA"
							 * EJEMPLO (2): 5+4+"CADENA"
							 * EN EL CASO DEL EJEMPLO (2) PRIMERO VERIFICAMOS SI YA TRAEMOS
							 * ALGUNA CANTIDAD ANTERIORMENTE POR ESO LA LINEA DE 
							 * CANTIDAD += (INT)....
							 * SI CANTIDAD = 0 ENTONCES SIMPLEMENTE AGREGAMOS A LA LISTA YA QUE SERIA COMO EL EJEMPLO(1)
							 * SOLO ES UNA CANTIDAD, PERO SI CANTIDAD ES DIFERENTE A 0 OSEA QUE SI YA HIZIMOS UNA SUMA COMO
							 * EN EL EJEMPLO(2) ENTONCES LO QUE NECESITAMOS ES AGREGAR ESA SUMA.
							 */
							cantidad += (int) Integer.parseInt(lexema);
							if(cantidad == 0){
								almacenar.add(lexema);
							}else{
								String tmp = Integer.toString(cantidad);
								almacenar.add(tmp);
								cantidad = 0;
							}
							
							lexema = "";
							indice++;
							estado = 0;
						}else if(Character.isWhitespace(siguiente_caracter)){
							indice++;
							estado = 0;
						
						}else if(Character.isLetter(siguiente_caracter)){
							/**
							 * SI ES DE ESTA FORMA 5 + SQRT(100)
							 */
						}else{
							/**
							 *ES ERROR YA QUE SI VIENE UN SIGNO '+' ES PARA CONCATENAR
							 *EJEMPLO: 3000+50+//FINLINEA
							 * 
							 */
							almacenar.add("error");
							estado = 10;
						}
						
					}else if(token == '#'){
						/**
						 * LLEGO AL FINAL DE LA LINEA
						 * EJEMPLO (1): 3000+50#
						 * EJEMPLO (2): 50-4#
						 * ENTONCES SUMAMOS LAS CANTIDADES
						 */
						if(simbolo.equals("+")){
							//SI YA TENEMOS ALGUN DATO EN AUX_MULTI ENTRA EN EL PRIMER IF
							if(aux_multi != 0){
								int multiplicacion = Integer.parseInt(lexema) * aux_multi;
								cantidad = cantidad + multiplicacion;
								String tmp = Integer.toString(cantidad);
								almacenar.add(tmp);
								lexema="";
								aux_multi=0;
								cantidad=0;
								simbolo="";
								estado=10;
							}else{//SI NO TENEMOS NADA EN AUX_MULTI NO INGRESO ALGUNA MULTIPLICACION
								cantidad += (int) Integer.parseInt(lexema);
								String tmp = Integer.toString(cantidad);
								almacenar.add(tmp);
								lexema = "";
								simbolo="";
								estado = 10;
							}
							
						}else if(simbolo.equals("-")){
							cantidad = cantidad - Integer.parseInt(lexema);
							String tmp = Integer.toString(cantidad);
							almacenar.add(tmp);
							lexema="";
							simbolo="";
							estado=10;
						}else if(simbolo.equals("*")){
							cantidad = cantidad * Integer.parseInt(lexema);
							String tmp = Integer.toString(cantidad);
							almacenar.add(tmp);
							lexema="";
							simbolo="";
							estado=10;
						}else if(simbolo.equals("/")){
							
						}else{
							System.out.println("error");
							estado = 10;
						}
						
					}else if(token == ')'){ //-----------------------------------------CALCULAR RAIZ
						
						/**
						 * SI ENCUENTRA ')' ENTONCES ESTAMOS ACA:
						 * SQRT(100')' O EXP(2,3')' O FACT(4')'
						 * PROCEDEMOS A HACER LO QUE PIDE.
						 * EN LEXEMA: 100 
						 * LO SIGUIENTE QUE PUEDE VENIR SERIA
						 * EJEMPLO (1): SQRT(100)#
						 * EJEMPLO (2): SQRT(100)+5
						 * EJEMPLO (3): SQRT(100)+"TEXTO"
						 */
						if(Operando.equals("sqrt")){
							int cant = Integer.parseInt(lexema);
							Double Raiz_Cuadrada = Math.sqrt(cant);
							String almacenar_raiz = Double.toString(Raiz_Cuadrada);
							almacenar.add(almacenar_raiz);
							
						}else if(Operando.equals("exp")){
							exponente = lexema;
							int cant_base = Integer.parseInt(base);
							int cant_expo = Integer.parseInt(exponente);
							int potencia = (int) Math.pow(cant_base, cant_expo);
							String almacenar_potencia = Integer.toString(potencia);
							almacenar.add(almacenar_potencia);
							
						}else if(Operando.equals("fact")){
							numero = lexema;
							int cant_numero = Integer.parseInt(numero);
							if(cant_numero == 1){
								almacenar.add("1");
							}else{
								int fac=1;
								for(int t=cant_numero; t>1; t--){
									fac = fac * t;
								}
								String almacenar_factorial = Integer.toString(fac);
								almacenar.add(almacenar_factorial);
								
							}
						}
						
						char siguiente_caracter = linea.charAt(indice+1);
						
						if(siguiente_caracter == '#'){
							indice++;
							estado = 0;
							
						}else if(siguiente_caracter == '+'){
							indice++;
							estado = 0;
						}else if(Character.isWhitespace(siguiente_caracter)){
							indice++;
							estado = 0;
						}else{
							almacenar.add("error");
						}
						
						
						
						
					}else if(token == ','){
						base = lexema;
						indice++;
						estado=2;
						lexema="";
						
					}else if(token == '-'){
						System.out.println("entro en - estado 2");
						/**
						 * ACTUALMENTE ESTAMOS ACA .... '-'
						 * PUEDE VENIR UNO DE LOS SIGUIENTES CASOS:
						 * EJEMPLO (1):50-5
						 * EJEMPLO (2):6+50-4
						 * ERROR SERIA:
						 * EJEMPLO (1): 50-+
						 * EJEMPLO (2): 50-"ALGO"
						 */
						
						char siguiente_caracter = linea.charAt(indice+1);
						if(!simbolo.equals("")){
							System.out.equals("entro en diferente a nada");
							//VERIFICAMOS LO QUE TRAIAMOS ANTERIORMENTE
							if(simbolo.equals("+")){ //EJEMPLO (2)
								cantidad = cantidad + Integer.parseInt(lexema);
								simbolo = "-";
								lexema="";
								indice++;
								estado=2;
							}else if(simbolo.equals("*")){
								
							}else if(simbolo.equals("/")){
								
							}
						}else if(Character.isDigit(siguiente_caracter)){ // EJEMPLO (1)
							simbolo = Character.toString(token);
							cantidad = Integer.parseInt(lexema);
							lexema="";
							indice++;
							estado=2;
						}
						
					}else if(token == '*'){
						if(!simbolo.equals("")){
							if(simbolo.equals("+")){
								aux_multi = Integer.parseInt(lexema);
								lexema="";
								indice++;
								estado=2;
							}
						}else{
							/**
							 * TENEMOS 10*2
							 * HACEMOS
							 * CANTIDAD = 10
							 */
							cantidad = Integer.parseInt(lexema);
							simbolo = "*";
							indice++;
							lexema="";
							estado=2;
						}
						
					}else{//FIN TOKEN SIMBOLO
						//ERROR
						estado = 10;
					}
					break;
				case 10:
					fin = true;
					break;
				}
			}
		}catch(Exception e){
			System.out.println("BUG EN IMPRIMIR");
		}
		String texto_actual="";
		for(int i=0; i<almacenar.size(); i++){
			texto_actual += almacenar.get(i);
		}
		paraImprimir.add(texto_actual);
		
	}
	
	private void LimpiarTodo(){
		paraImprimir.clear();
		L1.clear();
		L3.clear();
		D1.clear();
		D2.clear();
	}
	
	private boolean Validar(String a){
		String [] Reservadas = {"Imagen:", "Texto", "Negrita", "Cursiva", "Subrayado", "imprimir","sqrt","SQRT","exp","EXP","fact","FACT"};
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
		
		//DIVIDIMOS USANDO ; POR SI HAY VARIOS EN LA MISMA LINEA
		for(int i=2; i<Variables.size()-1; i++){
			tmp += Variables.get(i).toString();
		}
		
		String[] auno = tmp.split(";");
		for(int j=0; j<auno.length; j++){
			temporal.add(auno[j]);
		}
		//A LA LISTA TEMPORAL LE AGREGAMOS ; AL FINAL A CADA ITERACION PARA SU POSTERIOR CONTEO
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
				if(ca == ':' || ca == '=' || ca == ';'){ //SI TIENE : Y = Y ; ENTONCES TIENE UNA CORRECTA ESCRITURA DE LA SENTENCIA.
					c++;
					LineaVariables += Character.toString(aChartmp[w]);
				}else{
					LineaVariables += Character.toString(aChartmp[w]);
				}
			}
			
			if(c == 3){
				
				//SI CUMPLE CON LAS CONDICIONES
				String[] Arreglo_TodasVariables = LineaVariables.split(":"); //ESTAN X1,X2,X3...
				String aee = Arreglo_TodasVariables[0];
				String[] Arreglo_SeparadasVariables = aee.split(",");//ESTAN [X1][X2][X3]....
				String ae = Arreglo_TodasVariables[1];
				String[] Arreglo_Tipo_Valor = ae.split("=");// ESTAN CADENA/ENTERO Y TEXTO/VALOR
				
				String v = Arreglo_Tipo_Valor[1];
				char x =0;
				char[] av = v.toCharArray();
				String valor="";
				for(int ww=0; ww<v.length(); ww++){
					x = av[ww];
					if(x != ' ' && x != ';' && x != '"'){
						valor += Character.toString(av[ww]);
					}
				}
				
				for(int pos=0; pos<Arreglo_SeparadasVariables.length; pos++){
					String variable = Arreglo_SeparadasVariables[pos];
					L1.add(variable);
					L3.add(valor);
				}
				
				
			}else{
				//LE FALTA : O = O ;
			}
		}//FIN FOR
		//MOSTRANDO
	}
	
	private void Metodo_Arbol(LinkedList<String> Arbol){
		boolean ParentescoActivado = false, PersonaActivado = false, cambio = false;
		LinkedList<String> paraParentesco = new LinkedList<String>();
		LinkedList<String> paraPersona = new LinkedList<String>();
		int u = Arbol.size();
		if( Arbol.get(1).toString().equals("{") || Arbol.get(u).toString().equals("}") ){
			for(int i=2; i<Arbol.size()-1; i++){
				if(Arbol.get(i).toString().equals("Persona:{")){
					int f=i;
					while( !Arbol.get(f).equals("Relacion:{") ){
						paraPersona.add(Arbol.get(f));
						f++;
					}
					PersonaActivado=true;
					cambio=true;
					i += (f-3);
					
				}else if( Arbol.get(i).toString().equals("Relacion:{")){
					
					int f=i;
					while(f != Arbol.size()-1){
						paraParentesco.add(Arbol.get(f));
						f++;
					}
					ParentescoActivado=true;
					cambio=true;
					i += f;
				}
			}//FIN FOR
			if(cambio == true){
				if(PersonaActivado == true && ParentescoActivado == true){
					Metodo_Persona(paraPersona);
					Metodo_Relacion(paraParentesco);
				}else{
					//NO PUEDE HABER PERSONA SIN PARENTESCO Y PARENTESCO SIN PERSONA
				}
			}else{
				//NO INGRESO NADA EN ARBOL
			}
			
		}else{
			//FALTA { O } ERROR
			System.out.println("falta { o } error");
		}
		
		
	}
	
	private void Metodo_Persona(LinkedList<String> Persona){
		String Valores="";
		
		for(int i=0; i<Persona.size(); i++){
			
			String Linea_Codigo = Persona.get(i).toString();
			String Linea_Simbolo = Persona.get(i+5).toString();
			
			if( Linea_Codigo.equals("Persona:{")){
				if( Linea_Simbolo.equals("}") ){
					for(int h=(i+1); h<=(i+4); h++){
						String Linea = QuitarEspacios(Persona.get(h));
						
						String[] Arreglo_Linea = Linea.split(":");
						if(Arreglo_Linea.length == 2){
							if(Arreglo_Linea[0].equals("id")){ //-------------------------------------------- ID
								
								String D_id = Arreglo_Linea[1];
								String Datos_id = QuitarEspacios(D_id);
								
								if(Buscar_L1(Datos_id) == true){
									int posicion = Obtener_PosicionL1(Datos_id);
									String valor = Retornar_L3(posicion);
									if(esnumero(valor) == true){
										Valores += valor +"%";
										String agregar = "ID"+valor;
										D2.add(agregar);
									}else{
										//INGRESA UNA VARIABLE TIPO ENTERO
									}
								}else if(esnumero(Datos_id) == true){
									Valores += Datos_id + "%";
									String agregar = "ID"+Datos_id;
									D2.add(agregar);
								}else{
									//INGRESA UN NUMERO O VARIABLE NUMERICA
								}
								
							}else if(Arreglo_Linea[0].equals("nombre")){ //-------------------------------------------- NOMBRE
								if(EstructuraCorrecta(Linea) == true){
									//QUITANDO PUNTO Y COMA DE POSICION 1
									String tmp = Arreglo_Linea[1];
									String Dato_valor = tmp.substring(0, tmp.length()-1);
									
									if(Buscar_L1(Dato_valor) == true){
										int posicion = Obtener_PosicionL1(Dato_valor);
										String valor = Retornar_L3(posicion);
										
										if(esnumero(Dato_valor) == false){
											Valores += valor + "%";
											D1.add(valor);
										}else{
											//INGRESA UNA VARIABLE TIPO CADENA
										}
									}else if(esnumero(Dato_valor) == false){
										if(TieneComillas(Dato_valor) == true){
											String valor = QuitarComillas(Dato_valor);
											Valores += valor + "%";
											D1.add(valor);
										}else{
											//LOS NOMBRES VAN ENTRE COMILLAS
										}
									}else{
										//VERIFICA LO INGRESADO EN NOMBRE
									}
									
								}else{
									//NO TIENE DOS PUNTOS O PUNTO Y COMA
								}
							}else if(Arreglo_Linea[0].equals("edad")){ //-------------------------------------------- EDAD
								if(EstructuraCorrecta(Linea) == true){
									String tmp = Arreglo_Linea[1];
									String Dato_edad = tmp.substring(0, tmp.length()-1);
									
									if(Buscar_L1(Dato_edad) == true){
										int posicion = Obtener_PosicionL1(Dato_edad);
										String valor = Retornar_L3(posicion);
										
										if(esnumero(valor) == true){
											Valores += valor + "%";
										}else{
											//INGRESA UNA VARIABLE TIPO ENTERO
										}
									}else if(esnumero(Dato_edad) == true){
										Valores += Dato_edad + "%";
									}else{
										//VERIFICA LO INGRESADO EN EDAD
									}
									
								}else{
									//FALTA DOS PUNTOS O PUNTO Y COMA
								}
								
							}else if(Arreglo_Linea[0].equals("parentesco")){ //-------------------------------------------- PARENTESCO
								if(EstructuraCorrecta(Linea) == true){
									String tmp = Arreglo_Linea[1];
									String D_p = tmp.substring(0, tmp.length()-1);
									
										String[] Parentesco = D_p.split(",");
										
										if(Parentesco.length == 0){
											
										}else{
											for(int x=0; x<Parentesco.length;x++){
												
												System.out.println();
												System.out.println("pos " + x + " tiene " + Parentesco[x]);
											}
										}
										
									
									
								}else{
									//FALTA DOS PUNTOS O PUNTO Y COMA
								}
								
							}else{
								//NO ES PALABRA RESERVADA
								System.out.println("nada bueno ");
							}
						}else{
							//NO INCLUYO DOS PUNTOS
							System.out.println("no incluyo dos puntos ");
						}
					}//FIN FOR
					i = i +5;
				}else{
					//NO LO CERRO ENTRE '}'
					System.out.println("error no cerro entre");
				}
			}else{
				System.out.println("error inis per");
				//ERROR EN INICIALIZAR PERSONA
			}
			
		}//FIN FOR
		
	}
	
	private void Metodo_Relacion(LinkedList<String> Relacion){
		String Agregando = "";
		for(int i=1; i<Relacion.size()-1; i++){
			String tmp = Relacion.get(i).toString();
			String actual = QuitarEspacios(tmp);
			if(EstructuraCorrecta(actual) == true){
				String[] Arreglo_Relacion = actual.split(":"); //LO DIVIDIMOS USANDO ':'
				
				String ID_I = agregarseparador(Arreglo_Relacion[0]); //POR LA IZQUIERDA LO DEJAMOS..(%X%)..
				String[] ValorID = ID_I.split("%");
				if(Buscar_D2(ValorID[1]) == true){
					
					int pos = Obtener_PosicionD2(ValorID[1]);
					String nombreID = Retornar_D1(pos);
					Agregando += nombreID;
					
				}else if(esnumero(ValorID[1]) == true){
					
					String l = "ID"+ValorID[1];
					int pos = Obtener_PosicionD2(l);
					String nombreID = Retornar_D1(pos);
					Agregando += nombreID;
					
				}else{
					Agregando += "NULO";
					//MANDAR A NODO NULL
				}
				
				String tmp2 = Arreglo_Relacion[1]; //POR LA DERECHA :X,Y;
				String VrsID = tmp2.substring(0, tmp2.length()-1); //QUITAMOS ';'
				
				String[] ValoresID = VrsID.split(",");
				
				if(ValoresID.length == 1){
					if(Buscar_D2(ValoresID[0]) == true){
						
						int pos = Obtener_PosicionD2(ValoresID[0]);
						String RelacionadoCon = Retornar_D1(pos);
						Agregando += " -> " + RelacionadoCon + ";";
						
					}else if(esnumero(ValoresID[0]) == true){
						
						String l = "ID"+ValoresID[0];
						int pos = Obtener_PosicionD2(l);
						String RelacionadoCon = Retornar_D1(pos);
						Agregando += " -> " + RelacionadoCon + ";";
						
					}else{
						//MANDAR A NULO2
						Agregando += " -> " + "NULO2" + ";";
					}
				}else{ //TIENE MAS RELACIONES
					for(int h=0; h<ValoresID.length; h++){
						
						String Relaciones_Derecha = ValoresID[h];
						
						if(h == 0){ //POSICION 0 AGREGAMOS LO QUE YA VIENE
							if(Buscar_D2(Relaciones_Derecha) == true){
								
								int pos = Obtener_PosicionD2(Relaciones_Derecha);
								String RelacionadoCon = Retornar_D1(pos);
								Agregando += " -> " + RelacionadoCon +";";
								
							}else if(esnumero(Relaciones_Derecha) == true){
								
								String l = "ID"+Relaciones_Derecha;
								int pos = Obtener_PosicionD2(l);
								String RelacionadoCon = Retornar_D1(pos);
								Agregando += " -> " + RelacionadoCon + ";";
								
							}else{
								Agregando += " -> NULO2;";
							}
						}else{ //DEMAS POSICIONES VALORID 1 + VALORESID[H]
							if(Buscar_D2(Relaciones_Derecha) == true){
								
								int pos = Obtener_PosicionD2(Relaciones_Derecha);
								String RelacionadoCon = Retornar_D1(pos);
								
								String le = "ID"+ValorID[1];
								int posi = Obtener_PosicionD2(le);
								String nombreID = Retornar_D1(posi);
								
								Agregando += nombreID + " -> " + RelacionadoCon + ";";
								
							}else if(esnumero(Relaciones_Derecha) == true){
								
								String l = "ID"+Relaciones_Derecha;
								int pos = Obtener_PosicionD2(l);
								String RelacionadoCon = Retornar_D1(pos);
								
								String le = "ID"+ValorID[1];
								int posi = Obtener_PosicionD2(le);
								String nombreID = Retornar_D1(posi);
								
								Agregando += nombreID + " -> " + RelacionadoCon + ";";
								
							}else{
								String le = "ID"+ValorID[1];
								int posi = Obtener_PosicionD2(le);
								String nombreID = Retornar_D1(posi);
								Agregando += nombreID + " -> NULO2;";
							}
						}
						
					}//FIN FOR
				}
				
			}else{
				//NO ESCRIBIO PUNTO Y COMA O DOS PUNTOS
			}
			
		}//FIN FOR
		ar.CrearGRAPHVIZ(Agregando);
		ar.crearHTML(LexemasdeCodigo);
		try {
			ar.CrearPDF();
		} catch (IOException e) {
			System.out.println("error no se encuentra la imagen");
		} catch (DocumentException e) {
			System.out.println("error no se encuentra el archivo");
		}
	}
	
	private String agregarseparador(String palabra){
		char cara =0;
		String retorno="";
		for(int i=0; i<palabra.length(); i++){
			cara = palabra.charAt(i);
			if(cara == '('){
				retorno += Character.toString(cara) + "%";
			}else if(cara == ')'){
				retorno += "%" + Character.toString(cara);
			}else{
				retorno += Character.toString(cara);
			}
		}
		return retorno;
	}
	
	private boolean essimbolo(char token){
		boolean aprovacion = false;
		char[] simbolos = {'"','(',')',';','+','-','/','*',':'};
		for(int i=0; i<simbolos.length;i++){
			char actual = simbolos[i];
			if(actual == token){
				aprovacion = true;
			}
		}
		return aprovacion;
	}
	
	private boolean TieneComillas(String linea){
		char [] a = linea.toCharArray();
		int f = a.length-1;
		if(a[0] == '"' && a[f] == '"'){
			return true;
		}else{
			return false;
		}
	}
	
	private String QuitarComillas(String linea){
		char caracter = 0;
		String retornar="";
		for(int i=0; i<linea.length(); i++){
			caracter = linea.charAt(i);
			if(caracter != '"'){
				retornar += Character.toString(caracter);
			}
		}
		return retornar;
	}
	
	private String QuitarEspacios(String linea){
		char caracter=0;
		String retorno="";
		for(int i=0; i<linea.length(); i++){
			caracter = linea.charAt(i);
			if( caracter != ' '){
				retorno += Character.toString(caracter);
			}
		}
		return retorno;
	}
	
	private boolean EstructuraCorrecta(String linea){
		int c=0;
		char caracter=0;
		
		for(int i=0; i<linea.length(); i++){
			caracter = linea.charAt(i);
			if( caracter == ':' || caracter == ';'){
				c++;
			}
		}
		if( c == 2){
			return true;
		}else{
			return false;
		}
	}
	
	private boolean Buscar_L1(String variable){
		boolean encontrado=false;
		try{
			for(int i = 0; i<L1.size(); i++){
				String Linea = L1.get(i).toString();
				if(Linea.equals(variable)){
					encontrado = true;
				}
			}
		}catch(Exception e){
			System.out.println("bug en buscar l1");
		}
		
		
		return encontrado;
	}
	
	private boolean Buscar_D2(String variable){
		boolean encontrado = false;
		try{
			for(int i=0; i<D2.size(); i++){
				String linea = D2.get(i).toString();
				if(linea.equals(variable)){
					encontrado = true;
				}
			}
		}catch(Exception e){
			System.out.println("bug en buscar d2");
		}
		return encontrado;
	}
	
	private int Obtener_PosicionL1(String variable){
		int posicion=0;
		try{
			for(int i =0; i<L1.size(); i++){
				String Linea = L1.get(i).toString();
				if(Linea.equals(variable)){
					posicion = i;
				}
			}
		}catch(Exception e){
			System.out.println("bug en obtener pos l1");
		}
		return posicion;
	}
	
	private int Obtener_PosicionD2(String variable){
		int pos=0;
		try{
			for(int i=0; i<D2.size(); i++){
				String linea = D2.get(i).toString();
				if(linea.equals(variable)){
					pos = i;
				}
			}
		}catch(Exception e){
			System.out.println("bug en obtener d2");
		}
		return pos;
	}
	
	private String Retornar_L3(int posicion){
		String mandar = L3.get(posicion).toString();
		return mandar;
	}
	
	private String Retornar_D1(int posicion){
		String mandar = D1.get(posicion).toString();
		return mandar;
	}

}
