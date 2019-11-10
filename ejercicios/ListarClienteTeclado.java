package jdbc;

import java.sql.*;
import java.util.Scanner;

public class ListarClienteTeclado {

	public static void main(String[] args) {
		String provincia;
		String consulta;
		int numeroColumnas;
		String nombreColumnas;
		Scanner sc = new Scanner(System.in);
		System.out.println("Introduce el nombre de una provincia");
		provincia = sc.nextLine();
		
		try {
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/almacen", "prueba", "antitodo");
			Statement instruccion = conexion.createStatement();
			consulta = "SELECT CLIENTES.* "
					+ " FROM CLIENTES "
					+ " INNER JOIN PROVINCIAS "
					+ " ON PROVINCIAS.PROVINCIA = CLIENTES.PROVINCIA "
					+ " WHERE PROVINCIAS.DESCRIPCION = '" + provincia + "'";
			ResultSet cursor = instruccion.executeQuery(consulta);
			ResultSetMetaData datos = cursor.getMetaData();
			numeroColumnas = datos.getColumnCount();
			
			while (cursor.next()) {
				System.out.print("");
				for (int i = 1; i < numeroColumnas; i++) {
					nombreColumnas = datos.getColumnName(i);
					System.out.print(nombreColumnas + ": " + cursor.getString(i) + " ");
				}
				System.out.println();
			}
			cursor.close();
			instruccion.close();
			conexion.close();
			
		} catch (SQLException e) {
			System.out.println("Error en la ejecucion");
		}

	}

}
