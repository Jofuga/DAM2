package principal;

import javax.swing.JFrame;

import model.*;
import view.*;
import controller.*;

public class Principal {

	public static void main(String[] args) {		
	
		GestionDatos model = new GestionDatos();
		
		LaunchView view = new LaunchView();
		view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		view.setVisible(true);
		
		GestionEventos controller = new GestionEventos(model,view);
		controller.contol();
		
	}

}
