import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
/**
 * 
 */

import DBManager.DBManager;

/**
 * @author Antonio Mart�n
 *
 */
public class GestionClientes {
	
	/**
	 * Metodo que muestra el menu principal del programa
	 * @return true si se introduce el numero 12
	 */
	public static boolean menuPrincipal() {
		
        System.out.println("");
        System.out.println("MENU PRINCIPAL");
        System.out.println("1. Listar clientes");
        System.out.println("2. Nuevo cliente");
        System.out.println("3. Modificar cliente");
        System.out.println("4. Eliminar cliente");
        System.out.println("5. Crear Tabla");
        System.out.println("6. Filtrar fila de una Tabla");
        System.out.println("7. Volcar Tabla a un fichero");
        System.out.println("8. Insertar clientes desde un fichero");
        System.out.println("9. Actualizar clientes desde un fichero");
        System.out.println("10. Borrar clientes desde un fichero");
        System.out.println("11. Mostrar Clientes de Malaga");
        System.out.println("12. Salir");
        
        Scanner in = new Scanner(System.in);
            
        int opcion = pideInt("Elige una opci�n: ");
        
        switch (opcion) {
            case 1:
                opcionMostrarClientes();
                return false;
            case 2:
                opcionNuevoCliente();
                return false;
            case 3:
                opcionModificarCliente();
                return false;
            case 4:
                opcionEliminarCliente();
                return false;
            case 5:
            	opcionCrearTabla();
            	return false;
            case 6:
            	opcionFiltrarFila();
            	return false;
            case 7:
            	opcionVolcarFichero();
            	return false;
            case 8:
            	opcionInsertarClientesFichero();
            	return false;
            case 9:
            	opcionActualizarClientesFichero();
            	return false;
            case 10:
            	opcionBorrarClientesFichero();
            	return false;
            case 11:
            	opcionMostrarClientesMalaga();
            	return false;
            case 12:
                return true;
            default:
                System.out.println("Opci�n elegida incorrecta");
                return false;
        }
        
    }
   
    public static int pideInt(String mensaje){
        
        while(true) {
        	
            try {
            	
                System.out.print(mensaje);
                Scanner in = new Scanner(System.in);
                int valor = in.nextInt();
                //in.nextLine();
                return valor;
                
            } catch (Exception e) {
            	
                System.out.println("No has introducido un n�mero entero. Vuelve a intentarlo.");
            }
        }
    }
    
    public static String pideLinea(String mensaje){
        
        while(true) {
        	
            try {
            	
                System.out.print(mensaje);
                Scanner in = new Scanner(System.in);
                String linea = in.nextLine();
                return linea;
                
            } catch (Exception e) {
            	
                System.out.println("No has introducido una cadena de texto. Vuelve a intentarlo.");
            }
        }
    }

    public static void opcionMostrarClientes() {
    	
        System.out.println("Listado de Clientes:");
        DBManager.printTablaClientes();
    }

    public static void opcionNuevoCliente() {
    	
        Scanner in = new Scanner(System.in);

        System.out.println("Introduce los datos del nuevo cliente:");
        String nombre = pideLinea("Nombre: ");
        String direccion = pideLinea("Direcci�n: ");

        boolean res = DBManager.insertCliente(nombre, direccion);

        if (res) {
        	
            System.out.println("Cliente registrado correctamente");
            
        } else {
        	
            System.out.println("Error :(");
        }
    }

    public static void opcionModificarCliente() {
    	
        Scanner in = new Scanner(System.in);

        int id = pideInt("Indica el id del cliente a modificar: ");

        // Comprobamos si existe el cliente
        if (!DBManager.existsCliente(id)) {
        	
            System.out.println("El cliente " + id + " no existe.");
            return;
        }

        // Mostramos datos del cliente a modificar
        DBManager.printCliente(id);

        // Solicitamos los nuevos datos
        String nombre = pideLinea("Nuevo nombre: ");
        String direccion = pideLinea("Nueva direcci�n: ");

        // Registramos los cambios
        boolean res = DBManager.updateCliente(id, nombre, direccion);

        if (res) {
        	
            System.out.println("Cliente modificado correctamente");
            
        } else {
        	
            System.out.println("Error :(");
        }
    }

    /**
     * Metodo que permite eliminar un cliente
     */
    public static void opcionEliminarCliente() {
    	
        Scanner in = new Scanner(System.in);

        int id = pideInt("Indica el id del cliente a eliminar: ");

        // Comprobamos si existe el cliente
        if (!DBManager.existsCliente(id)) {
        	
            System.out.println("El cliente " + id + " no existe.");
            return;
        }

        // Eliminamos el cliente
        boolean res = DBManager.deleteCliente(id);

        if (res) {
        	
            System.out.println("Cliente eliminado correctamente");
            
        } else {
        	
            System.out.println("Error :(");
        }
    }
    
    /**
     * Metodo que permite crear una tabla
     */
    public static void opcionCrearTabla() {
    	
    	Scanner s = new Scanner(System.in);
    	
    	String nombre;
    	int numColum;
    	
    	System.out.println("Introduzca el nombre de la tabla: ");
    	nombre = s.nextLine();
    	
    	System.out.println("Introduzca el numero de columnas quiera tener en la tabla: ");
    	numColum = s.nextInt();
    	
    	DBManager.crearTabla(nombre, numColum);
    	
    }
    
    /**
     * Metodo que muestra mostrar una fila de una tabla
     */
    public static void opcionFiltrarFila() {
    	
    	Scanner s = new Scanner(System.in);
    	
    	String tabla, id;
    	
    	System.out.println("Introduzca el nombre de la tabla: ");
    	tabla = s.nextLine();
    	
    	System.out.println("Introduzca el ID del cliente: ");
    	id = s.nextLine();
    	
    	DBManager.filtrarFilaTabla(tabla, id);
    	
    }
    
    /**
     * Metodo que permite pasar los datos de una tabla a un fichero de texto
     */
    public static void opcionVolcarFichero() {
    	
    	Scanner s = new Scanner(System.in);
    	
    	String baseDatos, tabla, nombreFichero;
    	
    	System.out.println("Introduzca el nombre que quiera ponerle al fichero (incluida la extensi�n): ");
    	nombreFichero = s.nextLine();
    	
    	System.out.println("Introduzca el nombre de la base de datos: ");
    	baseDatos = s.nextLine();
    	
    	System.out.println("Introduzca el nombre de la tabla que quiera volcar: ");
    	tabla = s.nextLine();
    	
    	DBManager.volcarFichero(nombreFichero, baseDatos, tabla);
    	
    }
    
    /**
     * Metodo que permite insertar nuevos clientes en la base de datos desde un fichero de texto
     */
    public static void opcionInsertarClientesFichero() {
    	
    	Scanner s = new Scanner(System.in);
    	
    	ArrayList<String> clientes = new ArrayList();
    	
    	String nombre;
    	
    	System.out.println("Introduzca el nombre del fichero (incluida su extension): ");
    	nombre = s.nextLine();
    	
    	File fichero = new File(nombre);
    	
    	try {
    		
			Scanner lectura = new Scanner(fichero);
			
			while(lectura.hasNext()) {
				
				clientes.add(lectura.nextLine());
				
			}
			
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
			
		}
    	
    	for (int contador = 2; contador < clientes.size(); contador++) {
			
			String datos = clientes.get(contador);
			
			String[] separados = datos.split(",");
			
			String comillas = "";
			
			for (int contador2 = 0; contador2 < separados.length; contador2++) {
				
				if (contador2 == separados.length - 1) {
					
					comillas += "'"+separados[contador2]+"'";
					
				} else {
					
					comillas += "'"+separados[contador2]+"',";
					
				}
				
			}
			
		
			DBManager.insertarDesdeFichero(comillas);
			
			
		}
    	
    	
    	
    }
    
    /**
     * Metodo que permite actualizar la informacion de los clientes desde un fichero de texto
     */
    public static void opcionActualizarClientesFichero() {
    	
    	Scanner s = new Scanner(System.in);
    	
    	ArrayList<String> clientes = new ArrayList();
    	
    	String nombre;
    	
    	System.out.println("Introduzca el nombre del fichero (incluida su extension): ");
    	nombre = s.nextLine();
    	
    	File fichero = new File(nombre);
    	
    	try {
    		
			Scanner lectura = new Scanner(fichero);
			
			while(lectura.hasNext()) {
				
				clientes.add(lectura.nextLine());
				
			}
			
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
			
		}
    	
    	for (int contador = 2; contador < clientes.size(); contador++) {
			
			String datos = clientes.get(contador);
			
			String[] separados = datos.split(",");
			
			String comillasId = "";
			String comillasNombre = "";
			String comillasDireccion = "";
			
			for (int contador2 = 2; contador2 < separados.length; contador2++) {
				
				if (contador2 == separados.length - 1) {
					
					comillasId += "'"+separados[0]+"'";
					comillasNombre += "'"+separados[1]+"'";
					comillasDireccion += "'"+separados[2]+"'";
					
				} else {
					
					comillasId += "'"+separados[0]+"',";
					comillasNombre += "'"+separados[1]+"',";
					comillasDireccion += "'"+separados[2]+"',";
					
				}
				
			}
			
		
			DBManager.actualizarDesdeFichero(comillasId, comillasNombre, comillasDireccion);
			
			
		}
    	
    }
    
    /**
     * Metodo que permite borrar clientes de la base de datos desde un fichero de texto
     */
    public static void opcionBorrarClientesFichero() {
    	
    	Scanner s = new Scanner(System.in);
    	
    	ArrayList<String> clientes = new ArrayList();
    	
    	String nombre;
    	
    	System.out.println("Introduzca el nombre del fichero (incluida su extension): ");
    	nombre = s.nextLine();
    	
    	File fichero = new File(nombre);
    	
    	try {
    		
			Scanner lectura = new Scanner(fichero);
			
			while(lectura.hasNext()) {
				
				clientes.add(lectura.nextLine());
				
			}
			
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
			
		}
    	
    	for (int contador = 2; contador < clientes.size(); contador++) {
			
			String datos = clientes.get(contador);
			
			String[] separados = datos.split(",");
			
			String comillas = "";
			
			for (int contador2 = 2; contador2 < separados.length; contador2++) {
				
				if (contador2 == separados.length - 1) {
					
					comillas += "'"+separados[0]+"'";
					
				} else {
					
					comillas += "'"+separados[0]+"',";
					
				}
				
			}
			
		 
			DBManager.borrarDesdeFichero(comillas);
			
			
		}
    	
    }
    
    /**
     * Metodo que ejecuta un procedimiento almacenado que muestra los clientes de Malaga
     */
    public static void opcionMostrarClientesMalaga() {
    	
    	DBManager.procedimientoMostrarMalaga();
    	
    }
	
	public static void main(String[] args) {
		
		DBManager.loadDriver();
        DBManager.connect();

        boolean salir = false;
        do {
        	
            salir = menuPrincipal();
            
        } while (!salir);

        DBManager.close();
		

	}

}
