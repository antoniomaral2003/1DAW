En el proyecto de BBDD se han a�adido 7 m�todos nuevos a la clase DBManager, los cu�les son:

crearTabla(String nombre, int numColum)
filtrarFilaTabla(String tabla, String id)
volcarFichero(String nombreFichero, String baseDatos, String tabla)
insertarDesdeFichero(String valores)
actualizarDesdeFichero(String id, String nombre, String direccion)
borrarDesdeFcihero(String id)
procedimientoMostrarMalaga()

Luego de eso, se ha modificado la clase principal GestionClientes para poder utilizar estos m�todos a la hora de ejecutar el programa.

Se han realizado las correspondientes pruebas de los m�todos con el framework JUnit5

Por �ltimo, se ha ido documentando con JavaDoc todos los m�todos.