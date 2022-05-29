En el proyecto de BBDD se han añadido 7 métodos nuevos a la clase DBManager, los cuáles son:

crearTabla(String nombre, int numColum)
filtrarFilaTabla(String tabla, String id)
volcarFichero(String nombreFichero, String baseDatos, String tabla)
insertarDesdeFichero(String valores)
actualizarDesdeFichero(String id, String nombre, String direccion)
borrarDesdeFcihero(String id)
procedimientoMostrarMalaga()

Luego de eso, se ha modificado la clase principal GestionClientes para poder utilizar estos métodos a la hora de ejecutar el programa.

Se han realizado las correspondientes pruebas de los métodos con el framework JUnit5

Por último, se ha ido documentando con JavaDoc todos los métodos.