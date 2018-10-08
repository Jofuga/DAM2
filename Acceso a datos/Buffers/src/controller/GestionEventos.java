package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import model.*;
import view.*;

public class GestionEventos {

	private GestionDatos model;
	private LaunchView view;
	private ActionListener actionListener_comparar, actionListener_buscar;
	String fich1;
	String fich2;
	String pal;
	
	public GestionEventos(GestionDatos model, LaunchView view) {
		this.model = model;
		this.view = view;
	}

	public void contol() {
		actionListener_comparar = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				// TODO: Llamar a la función call_compararContenido
				
				call_compararContenido();
				
				
			}
		};
		view.getComparar().addActionListener(actionListener_comparar);

		actionListener_buscar = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				
				//Llama a la función call_buscarPalabra
				call_buscarPalabra();
			}
		};
		view.getBuscar().addActionListener(actionListener_buscar);
	}

	private int call_compararContenido(){

		
		//Llama a la función compararContenido de GestionDatos
		call_Ficheros();
		
		
		
		//Gestiona excepciones, comprueba campos vacios.
		if(Fich1vacio()==0){
			return 1;
		}
		if(Fich2vacio()==0){
			return 1;
		}
		
		
		//Comprueba ficheros existen.
		fichExiste(fich1);
		fichExiste(fich2);
		
		
		/*Compara contenido pasándole las dos variables de ficheros,
		 * lo comprueba y devuelve true si son iguales o false si no.
		 */
		try{
			boolean iguales = model.compararContenido(fich1, fich2);
			
			if(iguales==true){
				view.getTextArea().setText("Los ficheros son iguales");
			}else{
				view.getTextArea().setText("Los ficheros son diferentes");
			}
		}catch (Exception e){
			view.getTextArea().setText("Error: No se ha podido comparar");
		}
		
		return 1;
	}
		
	

	private int call_buscarPalabra() {

		//Llama a la función buscarPalabra de GestionDatos
		call_Ficheros();
		
		//Comprueba el estado de la casilla "primera aparaición".
		boolean primPal = view.getPrimera().isSelected();
		File fich = new File(fich1);
		if(Fich1vacio()==0){
			return 1;
		}
		
		//Controla espacios vacios de "palabra".
		String espaciosVacios = pal.trim();
		if(espaciosVacios.length()==0 || pal.isEmpty()){
			view.showError("Rellene campo Palabra");
			return 1;
		}
		
		
		//Comprueba si el fichero existe y se puede leer.
		
		fichExiste(fich1);

		
		/*Llama a Buscarpalabra pasándole las variables, si se encuentra la palabra
		 * muestra línea, si es -1 no se encuentra la palabra.
		 */
		try {
			int linea = model.buscarPalabra(fich1, pal, primPal);
			
			if(linea == -1){
				view.getTextArea().setText("No se escuentra la palabra");
			}else{
				view.getTextArea().setText("En la línea: " + linea);
			}
		}catch (IOException e) {
			view.getTextArea().setText("No se pudo buscar");
		}
		return 1;		
	}
	
	
		//Llama a los parámetros introducidos de gestión datos.
		private void call_Ficheros() {
			
			fich1 = view.getFichero1().getText();
			fich2 = view.getFichero2().getText();
			pal = view.getPalabra().getText();
		}
		
		
		//Comprubea espacios vacíos en las casillas de ficheros.
		//Fichero 1.
		private int Fich1vacio(){
			String espaciosVacios = fich1.trim();
			if ( espaciosVacios.length() ==0 || fich1.isEmpty()){
				view.showError("Fichero 1 Vacio");
				return 0;
			}
			return 1;
		}
		
		//Fichero 2.
		private int Fich2vacio(){
			String espaciosVacios = fich2.trim();
			if ( espaciosVacios.length() ==0 || fich2.isEmpty()){
				view.showError("Fichero 2 Vacio");
				return 0;
			}
			return 1;
		}
		
		//Comprobamos si el fichero existe y tiene permisos.
		private void fichExiste(String nombre){
			File fich = new File(nombre);
			if(!fich.exists()){
				view.showError("El fichero " + nombre + " no existe.");
			}else{
				if(!fich.canRead()){
					view.showError("El fichero " + nombre + " no tiene permisos");
				}
			}
		}


}
