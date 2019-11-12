package jdbc;

import java.sql.*;
import java.util.Scanner;


public class ActualizaPrefijoProvincia {

	public static void main(String[] args) {
		String uri = "jdbc:mysql://localhost:3306/almacen";
		String usuario = "prueba";
		String pass = "antitodo";
		String provincia;
		String prefijo;
		int provinciaID = -1;
		String consulta;
		Scanner sc = new Scanner(System.in);
		try {
			do {
				System.out.println("Introduce una provincia para cambiarle el prefijo");
				provincia = sc.nextLine();
				Connection conexion = DriverManager.getConnection(uri, usuario, pass);
				Statement instruccion = conexion.createStatement();
				consulta = "SELECT * "
						+ " FROM PROVINCIAS"
						+ " WHERE UPPER(DESCRIPCION) = '" + provincia + "'";
				ResultSet cursor = instruccion.executeQuery(consulta);
				if (cursor.next()) {
					provinciaID = cursor.getInt("PROVINCIA");
					System.out.println(provinciaID);
				} else {
					System.out.println("la provincia " + provincia + " no existe");
				}
				cursor.close();
				instruccion.close();
				conexion.close();
			} while (provinciaID == -1);
			System.out.println("Introduce un prefijo para la provincia seleccionado");
			prefijo = sc.nextLine();
			Connection conexion = DriverManager.getConnection(uri, usuario, pass);
			Statement instruccion = conexion.createStatement();
			consulta = "UPDATE PROVINCIAS SET PREFIJO = " + prefijo + " WHERE PROVINCIA = " + provinciaID;
			if (instruccion.executeUpdate(consulta) > 0) {
				System.out.println("Se ha actualizado correctamente");
			} else {
				System.out.println("no se ha actualizado");
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
