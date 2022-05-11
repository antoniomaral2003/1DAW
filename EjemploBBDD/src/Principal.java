import java.sql.*;
import java.sql.Date;
import java.util.*;
/**
 * 
 */

/**
 * @author Antonio Martín
 *
 */
public class Principal {
	
	public static void main(String args[]) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		
		String ruta = "jdbc:mysql://localhost:3306/instituto";
		String usuario = "root";
		String contrasenna = "";
		
		
		try {
			
			// Instalamos el driver
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			
			// Establecemos la conexion
			Connection conn = DriverManager.getConnection(ruta, usuario, contrasenna);
			
			// Creamos el statement
			Statement st = conn.createStatement();
			
			// Creamos el resultset
			String consulta = "SELECT * FROM alumno";
			ResultSet resultado = st.executeQuery(consulta);
			
			// Mostramos la consulta
			while (resultado.next()) {
				
				int id = resultado.getInt("id");
				String nombre = resultado.getString("nombre");
				String apellido1 = resultado.getString("apellido1");
				Date fecha_nacimiento = resultado.getDate("fecha_nacimiento");
				String es_repetidor = resultado.getString("es_repetidor");
				System.out.println(id + " , " + nombre + " , " + apellido1 + " , " + fecha_nacimiento + " , " + es_repetidor);
				
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
