package DBManager;
import java.sql.*;
import java.util.*;
import java.io.*;
/**
 * 
 */

/**
 * @author Antonio Martin
 *
 */
public class DBManager {
	
	// Conexi�n a la base de datos
    private static Connection conn = null;

    // Configuraci�n de la conexi�n a la base de datos
    private static final String DB_HOST = "localhost";
    private static final String DB_PORT = "3306";
    private static final String DB_NAME = "tienda";
    private static final String DB_URL = "jdbc:mysql://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME + "?serverTimezone=UTC";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "AntonioMartin";
    private static final String DB_MSQ_CONN_OK = "CONEXI�N CORRECTA";
    private static final String DB_MSQ_CONN_NO = "ERROR EN LA CONEXI�N";

    // Configuraci�n de la tabla Clientes
    private static final String DB_CLI = "clientes";
    private static final String DB_CLI_SELECT = "SELECT * FROM " + DB_CLI;
    private static final String DB_CLI_ID = "id";
    private static final String DB_CLI_NOM = "nombre";
    private static final String DB_CLI_DIR = "direccion";

    //////////////////////////////////////////////////
    // M�TODOS DE CONEXI�N A LA BASE DE DATOS
    //////////////////////////////////////////////////
    ;
    
    /**
     * Intenta cargar el JDBC driver.
     * @return true si pudo cargar el driver, false en caso contrario
     */
    public static boolean loadDriver() {
        try {
        	
            System.out.print("Cargando Driver...");
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            System.out.println("OK!");
            return true;
            
        } catch (ClassNotFoundException ex) {
        	
            ex.printStackTrace();
            return false;
            
        } catch (Exception ex) {
        	
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * Intenta conectar con la base de datos.
     *
     * @return true si pudo conectarse, false en caso contrario
     */
    public static boolean connect() {
        try {
        	
            System.out.print("Conectando a la base de datos...");
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            System.out.println("OK!");
            return true;
            
        } catch (SQLException ex) {
        	
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * Comprueba la conexi�n y muestra su estado por pantalla
     *
     * @return true si la conexi�n existe y es v�lida, false en caso contrario
     */
    public static boolean isConnected() {
        // Comprobamos estado de la conexi�n
        try {
        	
            if (conn != null && conn.isValid(0)) {
            	
                System.out.println(DB_MSQ_CONN_OK);
                return true;
                
            } else {
            	
                return false;
            }
            
        } catch (SQLException ex) {
        	
            System.out.println(DB_MSQ_CONN_NO);
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * Cierra la conexi�n con la base de datos
     */
    public static void close() {
        try {
        	
            System.out.print("Cerrando la conexi�n...");
            conn.close();
            System.out.println("OK!");
            
        } catch (SQLException ex) {
        	
            ex.printStackTrace();
        }
    }

    //////////////////////////////////////////////////
    // M�TODOS DE TABLA CLIENTES
    //////////////////////////////////////////////////
    ;
    
    // Devuelve 
    // Los argumentos indican el tipo de ResultSet deseado
    /**
     * Obtiene toda la tabla clientes de la base de datos
     * @param resultSetType Tipo de ResultSet
     * @param resultSetConcurrency Concurrencia del ResultSet
     * @return ResultSet (del tipo indicado) con la tabla, null en caso de error
     */
    public static ResultSet getTablaClientes(int resultSetType, int resultSetConcurrency) {
        try {
        	
            Statement stmt = conn.createStatement(resultSetType, resultSetConcurrency);
            ResultSet rs = stmt.executeQuery(DB_CLI_SELECT);
            return rs;
            
        } catch (SQLException ex) {
        	
            ex.printStackTrace();
            return null;
        }

    }

    /**
     * Obtiene toda la tabla clientes de la base de datos
     *
     * @return ResultSet (por defecto) con la tabla, null en caso de error
     */
    public static ResultSet getTablaClientes() {
        return getTablaClientes(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
    }

    /**
     * Imprime por pantalla el contenido de la tabla clientes
     */
    public static void printTablaClientes() {
        try {
        	
            ResultSet rs = getTablaClientes(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            
            while (rs.next()) {
            	
                int id = rs.getInt(DB_CLI_ID);
                String n = rs.getString(DB_CLI_NOM);
                String d = rs.getString(DB_CLI_DIR);
                System.out.println(id + "\t" + n + "\t" + d);
            }
            rs.close();
            
        } catch (SQLException ex) {
        	
            ex.printStackTrace();
        }
    }

    //////////////////////////////////////////////////
    // M�TODOS DE UN SOLO CLIENTE
    //////////////////////////////////////////////////
    ;
    
    /**
     * Solicita a la BD el cliente con id indicado
     * @param id id del cliente
     * @return ResultSet con el resultado de la consulta, null en caso de error
     */
    public static ResultSet getCliente(int id) {
        try {
        	
            // Realizamos la consulta SQL
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            String sql = DB_CLI_SELECT + " WHERE " + DB_CLI_ID + "='" + id + "';";

            ResultSet rs = stmt.executeQuery(sql);
            
            
            // Si no hay primer registro entonces no existe el cliente
            if (!rs.first()) {
                return null;
            }

            // Todo bien, devolvemos el cliente
            return rs;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * Comprueba si en la BD existe el cliente con id indicado
     *
     * @param id id del cliente
     * @return verdadero si existe, false en caso contrario
     */
    public static boolean existsCliente(int id) {
        try {
        	
            // Obtenemos el cliente
            ResultSet rs = getCliente(id);

            // Si rs es null, se ha producido un error
            if (rs == null) {
                return false;
            }

            // Si no existe primer registro
            if (!rs.first()) {
                rs.close();
                return false;
            }

            // Todo bien, existe el cliente
            rs.close();
            return true;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * Imprime los datos del cliente con id indicado
     *
     * @param id id del cliente
     */
    public static void printCliente(int id) {
        try {
        	
            // Obtenemos el cliente
            ResultSet rs = getCliente(id);
            if (rs == null || !rs.first()) {
                System.out.println("Cliente " + id + " NO EXISTE");
                return;
            }
            
            // Imprimimos su informaci�n por pantalla
            int cid = rs.getInt(DB_CLI_ID);
            String nombre = rs.getString(DB_CLI_NOM);
            String direccion = rs.getString(DB_CLI_DIR);
            System.out.println("Cliente " + cid + "\t" + nombre + "\t" + direccion);

        } catch (SQLException ex) {
            System.out.println("Error al solicitar cliente " + id);
            ex.printStackTrace();
        }
    }

    /**
     * Solicita a la BD insertar un nuevo registro cliente
     *
     * @param nombre nombre del cliente
     * @param direccion direcci�n del cliente
     * @return verdadero si pudo insertarlo, false en caso contrario
     */
    public static boolean insertCliente(String nombre, String direccion) {
        try {
        	
            // Obtenemos la tabla clientes
            System.out.print("Insertando cliente " + nombre + "...");
            ResultSet rs = getTablaClientes(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);

            // Insertamos el nuevo registro
            rs.moveToInsertRow();
            rs.updateString(DB_CLI_NOM, nombre);
            rs.updateString(DB_CLI_DIR, direccion);
            rs.insertRow();

            // Todo bien, cerramos ResultSet y devolvemos true
            rs.close();
            System.out.println("OK!");
            return true;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * Solicita a la BD modificar los datos de un cliente
     *
     * @param id id del cliente a modificar
     * @param nombre nuevo nombre del cliente
     * @param direccion nueva direcci�n del cliente
     * @return verdadero si pudo modificarlo, false en caso contrario
     */
    public static boolean updateCliente(int id, String nuevoNombre, String nuevaDireccion) {
        try {
        	
            // Obtenemos el cliente
            System.out.print("Actualizando cliente " + id + "... ");
            ResultSet rs = getCliente(id);

            // Si no existe el Resultset
            if (rs == null) {
                System.out.println("Error. ResultSet null.");
                return false;
            }

            // Si tiene un primer registro, lo eliminamos
            if (rs.first()) {
            	
                rs.updateString(DB_CLI_NOM, nuevoNombre);
                rs.updateString(DB_CLI_DIR, nuevaDireccion);
                rs.updateRow();
                rs.close();
                System.out.println("OK!");
                return true;
                
            } else {
            	
                System.out.println("ERROR. ResultSet vac�o.");
                return false;
            }
        } catch (SQLException ex) {
        	
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * Solicita a la BD eliminar un cliente
     *
     * @param id id del cliente a eliminar
     * @return verdadero si pudo eliminarlo, false en caso contrario
     */
    public static boolean deleteCliente(int id) {
        try {
        	
            System.out.print("Eliminando cliente " + id + "... ");

            // Obtenemos el cliente
            ResultSet rs = getCliente(id);

            // Si no existe el Resultset
            if (rs == null) {
                System.out.println("ERROR. ResultSet null.");
                return false;
            }

            // Si existe y tiene primer registro, lo eliminamos
            if (rs.first()) {
                rs.deleteRow();
                rs.close();
                System.out.println("OK!");
                return true;
            } else {
                System.out.println("ERROR. ResultSet vac�o.");
                return false;
            }

        } catch (SQLException ex) {
        	
            ex.printStackTrace();
            return false;
        }
	
    }
    
    /**
     * Metodo que crea una nueva tabal en la base de datos
     * @param nombre de tipo String
     * @param numColum de tipo int
     * @return true si se ha creado la tabla correctamente
     */
    public static boolean crearTabla(String nombre, int numColum) {
    	
    	try {
    		
    		Scanner s = new Scanner(System.in);
    		
    		String nombreColum, tipoDato;
    		
    		String consulta = "CREATE TABLE "+nombre+" (numeros INT)";
    		PreparedStatement pst = conn.prepareStatement(consulta);
    		
    		int rs = pst.executeUpdate();
    		
    		pst.close();
    		
    		for (int contador = 0; contador < numColum; contador++) {
    			
    			try {
    				
    				System.out.println("Introduzca el nombre de la columna: ");
    				nombreColum = s.nextLine();
    				
    				System.out.println("Introduzca el tipo de dato que almacenara la columna (int o String): ");
    				tipoDato = s.nextLine();
    				
    				switch (tipoDato) {
    				
    					case "int":
    					
	    					String consulta2 = "ALTER TABLE "+nombre+" ADD "+nombreColum+" INT NOT NULL FIRST";
	    					PreparedStatement pst2 = conn.prepareStatement(consulta2);
	    					
	    					int rs2 = pst2.executeUpdate();
	    					pst2.close();
	    					
	    					break;
    					
    					case "String":
    						
    						String consulta3 = "ALTER TABLE "+nombre+" ADD "+nombreColum+" VARCHAR(30) NOT NULL FIRST";
    						PreparedStatement pst3 = conn.prepareStatement(consulta3);
    						
    						int rs3 = pst3.executeUpdate();
    						pst3.close();
    						
    						break;
    				
    				}
    				
    				if (contador == 0) {
    					
    					String consulta4 = "ALTER TABLE "+nombre+" DROP COLUMN numeros";
    					PreparedStatement pst4 = conn.prepareStatement(consulta4);
    					
    					int rs4 = pst4.executeUpdate();
    					pst4.close();
    					
    				}
    				
    			}
    			catch (SQLException ex) {
    				
    				System.err.println("No se ha podido crear la columna");
    	    		ex.printStackTrace();
    	    		return false;
    				
    			}
    			catch (Exception ex) {
    				
    				System.err.println("No se ha podido crear la columna");
    	    		ex.printStackTrace();
    	    		return false;
    				
    			}
    			
    		}
    		
    		System.out.println("Tabla creada");
    		return true;
    		
    	}
    	catch (SQLException ex) {
    		
    		System.err.println("No se ha podido crear la tabla");
    		ex.printStackTrace();
    		return false;
    		
    	}
    	catch (Exception ex) {
    		
    		System.err.println("No se ha podido crear la tabla");
    		ex.printStackTrace();
    		return false;
    		
    	}
    	
    }
    
    /**
     * Metodo que filtra una fila de una tabla concreta
     * @param tabla de tipo String
     * @param id de tipo String
     * @return true si se muestra la fila correctamente
     */
    
    public static boolean filtrarFilaTabla(String tabla, String id) {
    	
    	try {
    		
    		String consulta = "SELECT * FROM " + DB_CLI + " WHERE " + DB_CLI_ID + " = ?";
    		PreparedStatement pst = conn.prepareStatement(consulta);
    		
    		pst.setString(1, id);
    		
    		ResultSet rs = pst.executeQuery();
    		
    		while(rs.next()) {
    			
    			int id1 = rs.getInt("id");
    			String nombre = rs.getString("nombre");
    			String direccion = rs.getString("direccion");
    			System.out.println(id1 + " , " + nombre + " , " + direccion);
    			
    		}
    		pst.close();
    		
    		return true;
    		
    		
    	}
    	catch (SQLException ex) {
    		
    		System.err.println("No se ha podido filtrar la fila de la tabla indicada");
    		ex.printStackTrace();
    		return false;
    		
    	}
    	catch (Exception ex) {
    		
    		System.err.println("No se ha podido filtrar la fila de la tabla indicada");
    		ex.printStackTrace();
    		return false;
    		
    	}
    	
    }
    
    /**
     * Metodo que permite pasar los datos de una tabla a un fichero de texto
     * @param nombreFichero de tipo String
     * @param baseDatos de tipo String
     * @param tabla de tipo String
     * @return true si se han pasado los datos al fichero de texto correctamente
     */
    public static boolean volcarFichero(String nombreFichero, String baseDatos, String tabla) {
    	
    	try {
    		
    		// Creamos el fichero y escribimos las dos primeras lineas
    		File fichero = new File(nombreFichero);
    		fichero.createNewFile();
    		FileWriter escribir = new FileWriter(fichero);
    		
    		escribir.write(baseDatos);
    		escribir.write("\n");
    		escribir.write("TABLA CLIENTES");
    		escribir.write("\n");
    		
    		String consulta = "SELECT * FROM " + DB_CLI;
    		Statement pst = conn.createStatement();
    		
    		ResultSet rs = pst.executeQuery(consulta);
    		
    		while(rs.next()) {
    			
    			int id = rs.getInt("id");
    			String nombre = rs.getString("nombre");
    			String direccion = rs.getString("direccion");
    			escribir.write(id + " , " + nombre + " , " + direccion);
    			escribir.write("\n");
    			
    		}
    		pst.close();
    		escribir.close();
    		
    		System.out.println("Tabla volcada al fichero " + nombreFichero);
    		return true;
    		
    	}
    	catch (SQLException ex) {
    		
    		ex.printStackTrace();
    		return false;
    		
    	}
    	catch (IOException ex) {
    		
    		System.err.println("No se ha podido volcar la informaci�n al fichero");
    		ex.printStackTrace();
    		return false;
    		
    	}
    	catch (Exception ex) {
    		
    		System.err.println("No se ha podido volcar la informaci�n al fichero");
    		ex.printStackTrace();
    		return false;
    		
    	}
    	
    }
    
    /**
     * Metodo que permite insertar nuevos clientes en la base de datos desde un fichero de texto
     * @param valores de tipo String
     * @return true si se han insertado los clientes correctamente
     */
    public static boolean insertarDesdeFichero(String valores) {
    	
    	try {
  
    		// Insertamos los clientes
    		
    		String consulta = "INSERT INTO "+DB_CLI+" (nombre, direccion) VALUES("+valores+")";
			PreparedStatement pst = conn.prepareStatement(consulta);
    		
			int rs = pst.executeUpdate();
			
    		pst.close(); 
    		
    		System.out.println("Insercion de nuevos clientes realizada");
    		return true;
    		
    		
    	}
    	catch (SQLException ex) {
    		
    		System.err.println("No se han podido insertar los clientes");
    		ex.printStackTrace();
    		return false;
    		
    	}
    	catch (Exception ex) {
    		
    		System.err.println("No se han podido insertar los clientes");
    		ex.printStackTrace();
    		return false;
    		
    	}
    	
    }
    
    /**
     * Metodo que permite actualizar la informacion de los clientes desde un fichero de texto
     * @param id de tipo String
     * @param nombre de tipo String
     * @param direccion de tipo String
     * @return true si se ha actualizado la informacion de los clientes correctamente
     */
    public static boolean actualizarDesdeFichero(String id, String nombre, String direccion) {
    	
    	try {
    		
    		// Actualizamos el nombre
    		String consulta = "UPDATE "+DB_CLI+" SET "+DB_CLI_NOM+" = "+nombre+" WHERE "+DB_CLI_ID+" = "+id;
    		PreparedStatement pst = conn.prepareStatement(consulta);
    		
    		int rs = pst.executeUpdate();
    		
    		pst.close();
    		
    		// Actualizamos la direccion
    		String consulta2 = "UPDATE "+DB_CLI+" SET "+DB_CLI_DIR+" = "+direccion+" WHERE "+DB_CLI_ID+" = "+id;
    		PreparedStatement pst2 = conn.prepareStatement(consulta2);
    		
    		int rs2 = pst2.executeUpdate();
    		
    		pst2.close();
    		
    		System.out.println("Actualizacion realizada");
    		return true;
    		
    	}
    	catch (SQLException ex) {
    		
    		System.err.println("No se ha podido actualizar los datos de los clientes");
    		ex.printStackTrace();
    		return false;
    		
    	}
    	catch (Exception ex) {
    		
    		System.err.println("No se ha podido actualizar los datos de los clientes");
    		ex.printStackTrace();
    		return false;
    		
    	}
    	
    }
    
    /**
     * Metodo que permite borrar clientes de la base de datos desde un fichero de texto
     * @param id de tipo String
     * @return true si se han borrado los clientes correctamente
     */
    public static boolean borrarDesdeFichero(String id) {
    	
    	try {
    		
    		String consulta = "DELETE FROM "+DB_CLI+" WHERE "+DB_CLI_ID+" = "+id;
    		PreparedStatement pst = conn.prepareStatement(consulta);
    		
    		int rs = pst.executeUpdate();
    		
    		pst.close();
    		
    		System.out.println("Cliente eliminado correctamente");
    		return true;
    		
    	}
    	catch (SQLException ex) {
    		
    		System.err.println("No se han podido borrar los clientes");
    		ex.printStackTrace();
    		return false;
    		
    	}
    	catch (Exception ex) {
    		
    		System.err.println("No se han podido borrar los clientes");
    		ex.printStackTrace();
    		return false;
    		
    	}
    	
    }
    
    /**
     * Metodo que ejecuta un procedimiento almacenado que muestra los clientes de Malaga
     * @return true si se ha mostrado la tabla correctamente
     */
    public static boolean procedimientoMostrarMalaga() {
    	
    	try {
    		
    		String consulta = "CALL mostrar_clientes_malaga()";
    		CallableStatement cst = conn.prepareCall(consulta);
    		
    		ResultSet rs = cst.executeQuery();
    		
    		while(rs.next()) {
    			
    			int id = rs.getInt("id");
    			String nombre = rs.getString("nombre");
    			String direccion = rs.getString("direccion");
    			System.out.println(id+" , "+nombre+" , "+direccion);
    			
    		}
    		cst.close();
    		
    		return true;
    		
    	}
    	catch (SQLException ex) {
    		
    		System.err.println("No se ha podido ejecutar el procedimiento almacenado");
    		ex.printStackTrace();
    		return false;
    		
    	}
    	catch (Exception ex) {
    		
    		System.err.println("No se ha podido ejecutar el procedimiento almacenado");
    		ex.printStackTrace();
    		return false;
    		
    	}
    	
    }
    
    
}
