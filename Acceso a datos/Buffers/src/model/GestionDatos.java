package model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class GestionDatos {

	public GestionDatos() {

	}

	//Abre buffers y lee archivo con su excepción.
	private BufferedReader abrirFileBuffer(String archivo) throws FileNotFoundException {
		return new BufferedReader(new FileReader(archivo));
	}
	
	
	//Cierra buffers con su excepción
	private void cerrarFileBuffer(BufferedReader br) throws IOException {
		br.close();
	}
	
	public boolean compararContenido (String fichero1, String fichero2) throws IOException{
		
		//Compara fichero1 y 2, se crean dos strings		
		String str1;
		String str2;
		
		//Abre y lee cada fichero
		BufferedReader br1 = abrirFileBuffer(fichero1);
		BufferedReader br2 = abrirFileBuffer(fichero2);
		
		while ((str1=br1.readLine())!=null){
			if (str1!=(str2=br2.readLine())) {
				return false;
				}
		}
		
		//Cierra buffers.
		cerrarFileBuffer(br1);
		cerrarFileBuffer(br2);
		return true;
		
	}
	
	
	public int buscarPalabra (String fichero1, String palabra, boolean primera_aparicion) throws IOException{
		
		//Abre archivo
		BufferedReader br1 = abrirFileBuffer(fichero1);
		
		//Nombramos String, número de línea y contador para que cuente las líneas.
		String str1;
		int linea=-1;
		int cont=0;
		
		/*Mientras que no sea null (haya texto), suma 1 al nº de línea,
		si la línea contiene la palabra concreta, es decir, al primera aparición es true
		guarda nº línea a (int linea), devuelve que nº línea se encuentra la palabra. */
		while ((str1=br1.readLine())!=null){
			cont++;
			
			if (str1.contentEquals(palabra)){
				
				if(primera_aparicion==true){
					linea = cont;
					break;
				}
				linea = cont;
			}
		}
				
		return linea;
	}	

}
