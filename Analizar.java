package Clases;

import java.awt.Font;
import java.util.LinkedList;
import java.util.StringTokenizer;

import javax.swing.JOptionPane;

public class Analizar {
	
	
	public LinkedList<String> L1 = new LinkedList<String>();
	public LinkedList<String> L3 = new LinkedList<String>();
	
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
					if(x != ' '){
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
				if(PersonaActivado == true){
					
					Metodo_Persona(paraPersona);
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
			System.out.println("esta en pos "+i +"<->"+Linea_Codigo);
			String Linea_Simbolo = Persona.get(i+5).toString();
			int pos = i + 5;
			System.out.println("simbolo "+pos +"<->"+Linea_Simbolo);
			
			if( Linea_Codigo.equals("Persona:{")){
				if( Linea_Simbolo.equals("}") ){
					System.out.println("------------------------------");
					for(int h=(i); h<=(i+4); h++){
						String Linea = QuitarEspacios(Persona.get(h));
						System.out.println();
						System.out.println("de quitar espacios "+Linea);
						
						String[] Arreglo_Linea = Linea.split(":");
						if(Arreglo_Linea.length == 2){
							if(Arreglo_Linea[0].equals("id")){
								System.out.println("si hay id ");
								
							}else if(Arreglo_Linea[0].equals("nombre")){
								System.out.println("si hay nombre ");
								
							}else if(Arreglo_Linea[0].equals("edad")){
								System.out.println("si hay edad");
								
							}else if(Arreglo_Linea[0].equals("parentesco")){
								System.out.println("si hay parentesco ");
								
							}else{
								//NO ES PALABRA RESERVADA
								System.out.println("nada bueno ");
							}
						}else{
							//NO INCLUYO DOS PUNTOS
							System.out.println("no incluyo dos puntos ");
						}
					}//FIN FOR
					System.out.println("----------------fin del for-----------------");
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
	
	private boolean TodoCorrectoParen(String[] valores){
		String[] Reservadas = {"padre","madre","hijo","hermano"};
		boolean correcto = false;
		for(int i=0; i<valores.length-1; i++){
			int h=0;
			while(valores[i] != Reservadas[h]){
				if(valores[i].equals(Reservadas[h])){
					correcto = true;
				}else{
					correcto = false;
					break;
				}
			}
			if(correcto = false){
				break;
			}
		}
		return correcto;
	}
	
	private boolean TieneComillas(String linea){
		char [] a = linea.toCharArray();
		int f = a.length;
		if(a[0] == '"' && a[f] == '"'){
			return true;
		}else{
			return false;
		}
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
	
	private boolean Estructura_Correcta(String linea){
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
		while(true){
			int pos=0;
			if( L1.get(pos).toString().equals(variable) ){
				encontrado = true;
				break;
			}else{
				pos++;
			}
		}
		
		return encontrado;
	}
	
	private int Obtener_PosL1(String variable){
		int posicion=0;
		while(true){
			if( L1.get(posicion).toString().equals(variable)){
				break;
			}else{
				posicion++;
			}
		}
		return posicion;
	}
	
	private String Retornar_L3(int posicion){
		String mandar = L3.get(posicion).toString();
		return mandar;
	}
	
	

}
