package DBManager;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class DBManagerTest {

	@Test
	void testLoadDriver() {
		DBManager.loadDriver();
	}

	@Test
	void testConnect() {
		DBManager.connect();
	}

	@Test
	void testIsConnected() {
		DBManager.isConnected();
	}

	@Test
	void testClose() {
		DBManager.close();
	}

	@Test
	void testCrearTabla() {
		DBManager.crearTabla("proveedores", 4);
	}

	@Test
	void testFiltrarFilaTabla() {
		DBManager.filtrarFilaTabla("clientes", "6");
	}

	@Test
	void testVolcarFichero() {
		DBManager.volcarFichero("clientes.txt", "tienda", "clientes");
	}

	@Test
	void testInsertarDesdeFichero() {
		DBManager.insertarDesdeFichero("'16','Pedro','Marbella'");
	}

	@Test
	void testActualizarDesdeFichero() {
		DBManager.actualizarDesdeFichero("'6'", "'Juan'", "'Torremolinos'");
	}

	@Test
	void testBorrarDesdeFichero() {
		DBManager.borrarDesdeFichero("'2'");
	}

	@Test
	void testProcedimientoMostrarMalaga() {
		DBManager.procedimientoMostrarMalaga();
	}

}
