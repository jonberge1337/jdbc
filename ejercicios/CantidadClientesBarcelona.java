package jdbc;

import java.sql.*;

public class CantidadClientesBarcelona {

	public static void main(String[] args) {
		try {
			Connection conexion = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/almacen", "prueba", "antitodo");
			Statement instruccion = conexion.createStatement();
			String consulta = "SELECT COUNT(*) AS CANTIDAD "
					+ " FROM CLIENTES "
					+ " INNER JOIN PROVINCIAS"
					+ " ON CLIENTES.PROVINCIA = PROVINCIAS.PROVINCIA "
					+ " WHERE UPPER(PROVINCIAS.DESCRIPCION) = 'BARCELONA'";
			ResultSet cursor = instruccion.executeQuery(consulta);
			cursor.next();
			System.out.println("Barcelona tiene " + cursor.getInt("CANTIDAD") + " clientes");
			cursor.close();
			instruccion.close();
			conexion.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
