package jdbc;

import java.sql.*;

public class NumeroClientesProveedores {

	public static void main(String[] args) {
		String direccion = "jdbc:mysql://localhost/almacen";
		String usuario = "prueba";
		String contraseina = "antitodo";
		String consulta = null;
		int proveedores;
		int clientes;
		try {
			Connection conexion = DriverManager.getConnection(direccion, usuario, contraseina);
			Statement instruccion = conexion.createStatement();

			consulta = "SELECT COUNT(*) AS CANTIDAD FROM CLIENTES";
			ResultSet cursor = instruccion.executeQuery(consulta);
			clientes = cursor.next() ? cursor.getInt("CANTIDAD") : 0;

			consulta = "SELECT COUNT(*) AS CANTIDAD FROM PROVEEDORES";
			cursor = instruccion.executeQuery(consulta);
			proveedores = cursor.next() ? cursor.getInt("CANTIDAD") : 0;
			
			if (proveedores > clientes) {
				System.out.println("Hay " + (proveedores - clientes) + " proveedores mas que clientes");
			} else if (clientes > proveedores) {
				System.out.println("Hay " + (clientes - proveedores) + " clientes mas que proveedores");
			} else {
				System.out.println("Tengo igual número de clientes y proveedores");
			}

			cursor.close();
			instruccion.close();
			conexion.close();
		} catch (SQLException e) {
			System.out.println("Error en la ejecucion de sql");
		}

	}

}
