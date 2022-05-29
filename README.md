# 1DAW

En el proyecto de BBDD se han añadido 7 métodos nuevos a la clase DBManager, los cuáles son:
1. crearTabla(String nombre, int numColum)
2. filtrarFilaTabla(String tabla, String id)
3. volcarFichero(String nombreFichero, String baseDatos, String tabla)
4. insertarDesdeFichero(String valores)
5. actualizarDesdeFichero(String id, String nombre, String direccion)
6. borrarDesdeFcihero(String id)
7. procedimientoMostrarMalaga()

Luego de eso, se ha modificado la clase principal GestionClientes para poder utilizar estos métodos a la hora de ejecutar el programa.

Por último, se ha ido documentando con JavaDoc todos los métodos.
