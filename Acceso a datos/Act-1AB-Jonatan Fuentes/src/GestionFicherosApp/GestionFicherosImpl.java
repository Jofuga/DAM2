package GestionFicherosApp;

import java.io.File;
import java.io.IOException;

import gestionficheros.FormatoVistas;
import gestionficheros.GestionFicheros;
import gestionficheros.GestionFicherosException;
import gestionficheros.TipoOrden;

public class GestionFicherosImpl implements GestionFicheros {
	private File carpetaDeTrabajo = null;
	private Object[][] contenido;
	private int filas = 0;
	private int columnas = 3;
	private FormatoVistas formatoVistas = FormatoVistas.NOMBRES;
	private TipoOrden ordenado = TipoOrden.DESORDENADO;

	public GestionFicherosImpl() {
		carpetaDeTrabajo = File.listRoots()[0];
		actualiza();
	}

	private void actualiza() {

		String[] ficheros = carpetaDeTrabajo.list(); // obtener los nombres
		// calcular el número de filas necesario
		filas = ficheros.length / columnas;
		if (filas * columnas < ficheros.length) {
			filas++; // si hay resto necesitamos una fila más
		}

		// dimensionar la matriz contenido según los resultados

		contenido = new String[filas][columnas];
		// Rellenar contenido con los nombres obtenidos
		for (int i = 0; i < columnas; i++) {
			for (int j = 0; j < filas; j++) {
				int ind = j * columnas + i;
				if (ind < ficheros.length) {
					contenido[j][i] = ficheros[ind];
				} else {
					contenido[j][i] = "";
				}
			}
		}
	}

	@Override
	public void arriba() {

		System.out.println("holaaa");
		if (carpetaDeTrabajo.getParentFile() != null) {
			carpetaDeTrabajo = carpetaDeTrabajo.getParentFile();
			actualiza();
		}

	}

	@Override
	public void creaCarpeta(String arg0) throws GestionFicherosException {
		File file = new File(carpetaDeTrabajo,arg0);
		
		
		// Utilizamos try y catch para engoblar y controlar  que se poseen 
		//permisos de escritura, si existe no creará el directorio, 
		//si no existe, lo creará y por último se actualiza la carpeta.
		
		try {
			
			if (file.canWrite()) {
					throw new GestionFicherosException("Este directorio no existe.");
			}			
			if (file.exists()) {
				 throw new GestionFicherosException();
			}				
			if (!file.mkdir()) {
				 throw new GestionFicherosException();
			}
			
		} catch (SecurityException e){
				
				System.out.println("NO tiene permisos para crear carpeta");	
				
		} finally {
			 
		actualiza(); }
		
		}
		
	

	@Override
	public void creaFichero(String arg0) throws GestionFicherosException {
		File file = new File(carpetaDeTrabajo,arg0);
		
		
		//Si el fichero no existe y tiene permiso de escritura, crea un nuevo fichero
		//controlando las excepciones, en particular IOException a la que hay que importar 
		//la clase y por último actualizamos.
		
		try {
			if (!file.exists());
			
			if (file.canWrite()) {
				throw new GestionFicherosException("Error al crear el archivo"); }
			
			if (file.createNewFile()) {
				throw new IOException(); }
			
			
		} catch (SecurityException | IOException e){
				
				System.out.println("NO puede crear el archivo");	
				
		} finally {
			 
		actualiza(); }

}

	
	@Override
	public void elimina(String arg0) throws GestionFicherosException {
		File file = new File(carpetaDeTrabajo, arg0);
		
		//Si no existe lanza excepción pero no logro 
		//hacer que borre carpetas con ficheros en su interior. He leído 
		//he intentado varias cosas pero  no lo consigo y (No me queda claro),

		if (!file.exists()) {
			throw new GestionFicherosException("Error: No existe.");
		}
		

		if (!file.delete()) {
			throw new SecurityException ("Error."); }
		actualiza();
	
	}
	

	@Override
	public void entraA(String arg0) throws GestionFicherosException {
		File file = new File(carpetaDeTrabajo, arg0);
		
		// se controla que el nombre corresponda a una carpeta existente
		if (!file.isDirectory()) {
			throw new GestionFicherosException("Error. Se ha encontrado "
					+ file.getAbsolutePath()
					+ " pero se esperaba un directorio");
		}
		
		// se controla que se tengan permisos para leer carpeta
		if (!file.canRead()) {
			throw new GestionFicherosException("Alerta. No se puede acceder a "
					+ file.getAbsolutePath() + ". No hay permiso");
		}
		
		// nueva asignación de la carpeta de trabajo
		carpetaDeTrabajo = file;
		
		// se requiere actualizar contenido
		actualiza();

	}

	@Override
	public int getColumnas() {
		return columnas;
	}

	@Override
	public Object[][] getContenido() {
		return contenido;
	}

	@Override
	public String getDireccionCarpeta() {
		return carpetaDeTrabajo.getAbsolutePath();
	}

	@Override
	public String getEspacioDisponibleCarpetaTrabajo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getEspacioTotalCarpetaTrabajo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getFilas() {
		return filas;
	}

	@Override
	public FormatoVistas getFormatoContenido() {
		return formatoVistas;
	}

	@Override
	public String getInformacion(String arg0) throws GestionFicherosException {
		
		StringBuilder strBuilder=new StringBuilder();
		File file = new File(carpetaDeTrabajo,arg0);
		
		
		//Controlar que existe. Si no, se lanzará una excepción
		if (!file.exists()) {
			throw new GestionFicherosException();
		}
		
		
		//Controlar que haya permisos de lectura. Si no, se lanzará una excepción
		if (!file.canRead()) {
			throw new GestionFicherosException(); 
		}
		
		
		//Título
		strBuilder.append("INFORMACIÓN DEL SISTEMA");
		strBuilder.append("\n\n");
		
		
		//Nombre
		strBuilder.append("Nombre: ");
		strBuilder.append(arg0);
		strBuilder.append("\n");
		
		
		//Tipo: fichero o directorio
		strBuilder.append("Tipo: "); 
		
		if (file.isDirectory()) {
			strBuilder.append("Directorio"); }
			else { strBuilder.append("Fichero");
		}	
		strBuilder.append("\n");
		
		
		//Ubicación		
		strBuilder.append("Ubicación: ");
		
		if (file.exists()) { 
			strBuilder.append(file.getAbsolutePath());
		}
		strBuilder.append("\n");
		
		
		//Fecha de última modificación. Hay que implementar la clase Calendar para modificar 
		//de milisegundos a minutos, dia, hora, etc,,		
		strBuilder.append("Modificado: ");
		
		if (file.exists()) {
			strBuilder.append(file.lastModified());
		}
		strBuilder.append("\n");
		
		
		//Si es un fichero oculto o no
		strBuilder.append("Oculto: ");
		
		if (file.isHidden()) { 
			strBuilder.append("SI"); }
		else { strBuilder.append("NO");
		}
		strBuilder.append("\n");
		
		
		//Si es directorio: Espacio libre, espacio disponible, espacio total
		// Si no es directorio,  es fichero tamaño en bytes.
		if (file.isDirectory()) {
			strBuilder.append("Espacio Libre: ");
			strBuilder.append(file.getFreeSpace());
			strBuilder.append("\n");
			
			strBuilder.append("Espacio Disponible: ");
			strBuilder.append(file.getUsableSpace());
			strBuilder.append("\n");
			
			strBuilder.append("Espacio Total: ");
			strBuilder.append(file.getTotalSpace());
			strBuilder.append("\n");
		}
		else {
			strBuilder.append("Tamaño fichero: ");
			strBuilder.append(file.length());
		}
		
		return strBuilder.toString();
	}
	
	
	@Override
	public boolean getMostrarOcultos() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getNombreCarpeta() {
		return carpetaDeTrabajo.getName();
	}

	@Override
	public TipoOrden getOrdenado() {
		return ordenado;
	}

	@Override
	public String[] getTituloColumnas() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getUltimaModificacion(String arg0)
			throws GestionFicherosException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String nomRaiz(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int numRaices() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void renombra(String arg0, String arg1) throws GestionFicherosException {
		
		//Se crea file2 con nueva ruta
		File file=new File(carpetaDeTrabajo, arg0);
		File file2=new File(carpetaDeTrabajo, arg1);
		
		// Si el archivo no existe, lanza excepción.
		if (!file.exists()) {
			throw new GestionFicherosException("Error: No existe.");
		}
		
		//Archivo lo renombra a archivo2 y actualiza.
		file.renameTo(file2);
		actualiza();
			
	}

	
	@Override
	public boolean sePuedeEjecutar(String arg0) throws GestionFicherosException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean sePuedeEscribir(String arg0) throws GestionFicherosException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean sePuedeLeer(String arg0) throws GestionFicherosException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setColumnas(int arg0) {
		columnas = arg0;

	}

	@Override
	public void setDirCarpeta(String arg0) throws GestionFicherosException {
		File file = new File(arg0);

		// se controla que la dirección exista y sea directorio
		if (!file.isDirectory()) {
			throw new GestionFicherosException("Error. Se esperaba "
					+ "un directorio, pero " + file.getAbsolutePath()
					+ " no es un directorio.");
		}

		// se controla que haya permisos para leer carpeta
		if (!file.canRead()) {
			throw new GestionFicherosException(
					"Alerta. No se puede acceder a  " + file.getAbsolutePath()
							+ ". No hay permisos");
		}

		// actualizar la carpeta de trabajo
		carpetaDeTrabajo = file;

		// actualizar el contenido
		actualiza();

	}

	@Override
	public void setFormatoContenido(FormatoVistas arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setMostrarOcultos(boolean arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setOrdenado(TipoOrden arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setSePuedeEjecutar(String arg0, boolean arg1)
			throws GestionFicherosException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setSePuedeEscribir(String arg0, boolean arg1)
			throws GestionFicherosException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setSePuedeLeer(String arg0, boolean arg1)
			throws GestionFicherosException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setUltimaModificacion(String arg0, long arg1)
			throws GestionFicherosException {
		// TODO Auto-generated method stub

	}

}
