package Clases;

import java.awt.Font;
import java.util.LinkedList;
import java.util.StringTokenizer;

import javax.swing.JOptionPane;

public class Analizar {
	
	
	public LinkedList<String> L1 = new LinkedList<String>();
	public LinkedList<String> L3 = new LinkedList<String>();
	public LinkedList<String> D1 = new LinkedList<String>();
	public LinkedList<String> D2 = new LinkedList<String>();
	public LinkedList<String> D3 = new LinkedList<String>();
	
	public void Separar(String Datos){
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
		
		//DIVIDIMOS USANDO ; POR SI HAY VARIOS EN LA MISMA LINEA
		for(int i=2; i<Variables.size()-1; i++){
			tmp += Variables.get(i).toString();
		}
		
		String[] auno = tmp.split(";");
		for(int j=0; j<auno.length; j++){
			temporal.add(auno[j]);
		}
		//A LA LISTA TEMPORAL LE AGREGAMOS ; AL FINAL A CADA ITERACION PARA SU POSTERIOR CONTEO
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
		System.out.println("mostrando resultado de L1 Y L3----- linea-------- ");
		for(int po=0; po<L1.size(); po++){
			System.out.println();
			System.out.println("a ->"+ L1.get(po) + "<- corresponde ->" + L3.get(po)+"<-");
		}
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
		System.out.println(Valores);
		
	}
	
	private void Metodo_Relacion(LinkedList<String> Relacion){
		
		for(int i=1; i<Relacion.size()-1; i++){
			String tmp = Relacion.get(i).toString();
			String actual = QuitarEspacios(tmp);
			if(EstructuraCorrecta(actual) == true){
				String[] Arreglo_Relacion = actual.split(":");
				String ID_I = agregarseparador(Arreglo_Relacion[0]);
				
				
			}else{
				//NO ESCRIBIO PUNTO Y COMA O DOS PUNTOS
			}
			
		}
	}
	
	private String agregarseparador(String palabra){
		char cara =0;
		String retorno="";
		for(int i=0; i<palabra.length(); i++){
			cara = palabra.charAt(i);
			if(cara == '(' || cara == ')'){
				retorno += Character.toString(cara) + "%";
			}else{
				retorno += Character.toString(cara);
			}
		}
		return retorno;
	}
	
	private boolean espalabrareservada(String Palabra){
		String[] Reservadas = {"abuelo","padre","madre","hijo","hermano"};
		boolean correcto = false;
		for(int i=0; i<Reservadas.length; i++){
			if(Reservadas[i].equals(Palabra)){
				correcto = true;
			}
		}
		return correcto;
	}
	
	private boolean tienecoma(String linea){
		char cara = 0;
		int c=0;
		for(int i=0; i<linea.length(); i++){
			cara = linea.charAt(i);
			if(cara == ','){
				c++;
			}
		}
		if(c > 1){
			return true;
		}else{
			return false;
		}
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
