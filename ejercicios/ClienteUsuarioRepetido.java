package jdbc;

import java.sql.*;
import java.util.Scanner;

public class ClienteUsuarioRepetido {

	public static void main(String[] args) {
		String uri = "jdbc:mysql://localhost:3306/almacen";
		String usuario = "prueba";
		String passwd = "antitodo";
		String consulta;
		String cliente;
		int cantidadColumnas;
		Scanner sc = new Scanner(System.in);
		
		Connection conexion = null;
		Statement instruccion = null;
		ResultSet cursor = null;
		ResultSetMetaData informacion = null;
		do {

			try {
				conexion = DriverManager.getConnection(uri, usuario, passwd);
				instruccion = conexion.createStatement();
				System.out.println("Introduce un cliente");
				cliente = sc.nextLine();
				consulta = "SELECT * FROM CLIENTES WHERE CLIENTE = " + cliente;
				cursor = instruccion.executeQuery(consulta);
				informacion = cursor.getMetaData();
				cantidadColumnas = informacion.getColumnCount();
				if(cursor.next()) {
					for (int i = 1; i <= cantidadColumnas; i++) {
						System.out.print(cursor.getString(i) + " ");
					}
					System.out.println();
				} else {
					System.out.println("Ese cliente no existe");
				}
				System.out.println("Quieres borrar el registro?");
				if (sc.nextLine().equalsIgnoreCase("si")) {
					consulta = "DELETE FROM CLIENTES WHERE CLIENTE = " + cliente;
					if (instruccion.executeUpdate(consulta) > 0) {
						System.out.println("Ha sido borrado satisfactoriamente");
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("Quieres volver a ejecutar el programa?");
		} while (sc.nextLine().equalsIgnoreCase("si"));

	}

}
