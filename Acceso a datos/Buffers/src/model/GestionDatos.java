package model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class GestionDatos {

	public GestionDatos() {

	}

	//Abre buffers y lee archivo con su excepci�n.
	private BufferedReader abrirFileBuffer(String archivo) throws FileNotFoundException {
		return new BufferedReader(new FileReader(archivo));
	}
	
	
	//Cierra buffers con su excepci�n
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
		
		//Nombramos String, n�mero de l�nea y contador para que cuente las l�neas.
		String str1;
		int linea=-1;
		int cont=0;
		
		/*Mientras que no sea null (haya texto), suma 1 al n� de l�nea,
		si la l�nea contiene la palabra concreta, es decir, al primera aparici�n es true
		guarda n� l�nea a (int linea), devuelve que n� l�nea se encuentra la palabra. */
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
