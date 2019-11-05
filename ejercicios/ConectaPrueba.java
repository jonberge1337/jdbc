package jdbc;

import java.sql.*;


public class ConectaPrueba {

	public static void main(String[] args) {
		try {
			Connection miconexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/almacen", "prueba", "antitodo");
			Statement instruccion = miconexion.createStatement();
			String query = "SELECT cliente, nombre, poblacion FROM CLIENTES";
			ResultSet resultados = instruccion.executeQuery(query);
			System.out.println("Listado de clientes");
			while (resultados.next()) {
				System.out.println("Cliente: " + resultados.getString("cliente")
				+ ", Nombre: " + resultados.getString("nombre")
				+ ", Poblacion: " + resultados.getString("poblacion"));
			}
			resultados.close();
			instruccion.close();
			miconexion.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
