# FUNCIONAMIENTO DEL JDBC

## jdbc:

- Permite acceder a bases de datos relacionales mediante SQL desde programas java.
- La API de java nos proporciona varios paquetes:
  - java.sql
  - javax.sql
Ejemplo:
```java
import java.sql.*;
import javax.sql.*;

// o si queremos mas especifico

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.DataSource;

```
---

## Conexion:

Para conectarnos necesitamos a la base de datos necesitaremos un driver dependiendo del gestor de bases de datos.
Tendremos que configurar nuestro entorno de desarrollo indicandole donde se encuentra el driver ofrecido por nuestro gestor de bases de datos.

- La conexión a la BD se hace con el método getConnection()
  - public static Connection getConnection(String url)
  - public static Connection getConnection(String url, String user, String password)
  - public static Connection getConnection(String url, Properties info)
  
    - Todos pueden lanzar la excepción SQLException

```java
try {
	Connection miconexion = DriverManager.getConnection("jdbc:Nuestro gesto://servidor:puerto/nombre de BBDD", "usuario", "contraseña");
	System.out.println("Conectado");
	miconexion.close();
} catch (SQLException e) {
	e.printStackTrace();
}

```

---

## Statement

- Encapsula las instrucciones SQL a la BD
- Se crea a partir de la:

    ```java
        instruccion =conexion.createStatement();
    ```
- Métodos:
  - executeQuery(String sql)
    - Ejecucion de consultas: SELECT
    - Devuelve un objeto ResultSet
  - executeUpdate(String sql)
    - Modificaciones en la BD: INSERT, UPDATE, DELETE
    - Devuelve el número de columnas afectadas
  - execute(String sql)
    - Ejecución de instrucciones que pueden devolver varios conjuntos de resultados
    - Requiere usar luego `getResulSet()` o `getUpdateCount()` para recuperar los resultados, y `getMoreResult()`

## ResultSet
- Encapsula el conjunto del resultado
- Para obtener el valor de cada campo hay que usar el método getX("campo")
  
Método | Tipo   
---------|----------
getInt | INTEGER  
getLong | BIG INT
getFloat | REAL
getDouble | FLOAT  
getBignum | DECIMAL
getBoolean | BIT
getString | VARCHAR  
getString | CHAR  
getDate | DATE  
getTime | TIME  
getTimesstamp | TIME STAMP
getObject | Cualquier otro tipo  
- Para pasar al siguiente registro se usa el método next()
  - Devuelve false cuando no hay más registros

ejemplo:
```java
try{
    Statement instruccion =conexion.createStatement();
    Stringquery="SELECT * FROM clientes WHERE nombre LIKE \"Empresa%\"";
    ResultSet resultados=instruccion.executeQuery(query);
    System.out.println("Listadodeclientes:");
    while (resultados.next()){
        System.out.println("Cliente"+resultados.getString("nif")
        +",Nombre:" +resultados.getString("nombre")
        +",Teléfono: "+resultados.getString("telefono"));
        }
}catch(Exception ex){
    e.printStackTrace();
}
```
---

## Excepciones

- SQLException
  - Es obligatorio capturar estas excepciones
  - Se puede obtener información adicional sobre el error
    - getMessage()
      - Mensaje de error de la excepción
    - getSQLState()
      - Texto de SQLstate según la convención X/Open o SQL:2003
    - getErrorCode()
      - Código de error (entero) específico del vendedor
    - Hay muchas subclases: BatchUpdateException, RowSetWarning, SerialException, SQLClientInfoException, SQLNonTransientException, SQLRecoverableException, SQLTransientException, SQLWarning, SyncFactoryException, SyncProviderException.
## SQLWarning
- No es obligatorio capturar estas excepciones
- Errores leves de objetos Connection, Statement, o ResultSet
- DataTruncation
  - Subclase de SQLWarning
  - Avisos de truncado de datos en operaciones de lectura o escritura
---

## Cierre del conexion
Para liberar la conexion siempre la cerramos como lo haciamos con los ficheros con el metodo `close()`. Como los grifos lo que se abre se cierra.
ejemplo:
```java
finally {
    try{
        if (resultados != null){ //liberar los ResultSet
            resultados.close();
            }if (instruccion != null){//liberar los Statement 
            instruccion.close();
            }if (conexion !=null){ //liberar la conexión a la BD 
            conexion.close();
            }
    }catch(Exception e){
        e.printStackTrace();
    }
}
```