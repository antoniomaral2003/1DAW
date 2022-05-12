import java.sql.*;
import java.util.*;
/**
 * 
 */

/**
 * @author Antonio Martín
 *
 * 12 may. 2022
 */
public class Principal {

	public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		
		String ruta = "jdbc:mysql://localhost:3306/instituto";
		String usuario = "root";
		String contrasenna = "AntonioMartin";
		
		try {
			
			// Instalamos el driver
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			
			// Creamos la conexion
			Connection conn = DriverManager.getConnection(ruta, usuario, contrasenna);
			
			// Creamos el preparedStatement
			String consulta = "SELECT * FROM alumno WHERE id = ?";
			PreparedStatement pst = conn.prepareStatement(consulta);
			
			// Creamos el resultset
			pst.setInt(1, 3);
			ResultSet salida = pst.executeQuery();
			
			// Mostramos la salida por pantalla
			while (salida.next()) {
				
				int id = salida.getInt("id");
				String nombre = salida.getString("nombre");
				String apellido1 = salida.getString("apellido1");
				String apellido2 = salida.getString("apellido2");
				System.out.println(id + " , " + nombre + " , " + apellido1 + " , " + apellido2);
				
			}
			
			
		}
		catch (SQLException ex) {
			
			ex.printStackTrace();
			
		}
		catch (InstantiationException ex) {
			
			ex.printStackTrace();
			
		}
		catch (IllegalAccessException ex) {
			
			ex.printStackTrace();
			
		}
		catch (ClassNotFoundException ex) {
			
			ex.printStackTrace();
			
		}
		

	}

}
