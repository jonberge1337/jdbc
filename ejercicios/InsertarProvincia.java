package jdbc;

import java.sql.*;

public class InsertarProvincia {

	public static void main(String[] args) {
		
		try {
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/almacen","prueba", "antitodo");
			Statement instruccion = conexion.createStatement();
			ResultSet cursor = null;
			String sentencia = "INSERT INTO PROVINCIAS VALUES(78, 'BIDASOA', 989)";
			instruccion.executeUpdate(sentencia);
			sentencia = "SELECT * FROM PROVINCIAS WHERE PROVINCIA = 78";
			cursor = instruccion.executeQuery(sentencia);
			if (cursor.next()) {
				System.out.println("Se ha introducido la provincia 78");
			} else {
				System.out.println("No se ha introducido la provincia");
			}
			cursor.close();
			instruccion.close();
			conexion.close();
		} catch (Exception e) {
			System.err.println("Error");
		}
		
	}

}
