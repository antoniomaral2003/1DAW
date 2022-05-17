import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * 
 */

/**
 * @author Antonio Martín
 *
 */
public class PrincipalUpdate {

	public static void main(String[] args) {
		
		String ruta = "jdbc:mysql://localhost:3306/antonio";
		String usuario = "root";
		String contrasenna = "";
		
		try {
			
			// Instalamos el driver
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			
			// Creamos la conexion
			Connection conn = DriverManager.getConnection(ruta, usuario, contrasenna);
			
			// Creamos el preparedstatement
			String consulta = "INSERT INTO datos VALUES(?, ?, ?, ?, ?)";
			PreparedStatement pst = conn.prepareStatement(consulta);
			
			// Creamos el resultset
			pst.setInt(1, 11);
			pst.setString(2, "Luis");
			pst.setString(3, "670912874");
			pst.setString(4, "Malaga");
			pst.setString(5, "España");
			int salida = pst.executeUpdate();
			
			if (salida > 0) {
				
				System.out.println("Elemento insertado");
				
			} else {
				
				System.err.println("No se ha podido insertar el elemento");
				
			}
		
			
			conn.close();
			
			
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
