package jdbc;

import java.sql.*;
import java.util.Scanner;

public class CrearTablaClientes {

	public static int pedirCliente(Connection c, Statement i, ResultSet cu, String uri, String user, String pass) {
		String consulta;
		int cliente = 0;
		Scanner sc = new Scanner(System.in);
		ResultSetMetaData datos = null;

		do {
			try {
				System.out.println("Introduce id del cliente, tiene que ser mayor que 0");
				cliente = sc.nextInt();
				c = DriverManager.getConnection(uri, user, pass);
				i = c.createStatement();
				consulta = "SELECT * FROM CLIENTESIMPORTANTES WHERE CLIENTE = " + cliente;
				cu = i.executeQuery(consulta);
				datos = cu.getMetaData();
				
				if (cu.next()) {
					for (int j = 1; j <= datos.getColumnCount(); j++) {
						if (datos.getColumnCount() != j) {
							System.out.print(cu.getString(j) + " ");
						} else {
							System.out.println(cu.getString(j));
						}
					}
				} else {
					System.out.println("No hay resultados con ese cliente");
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			} catch (Exception e) {
				System.err.println("Error asegurate de meter un numero entero");
				sc.nextLine();
			}
		} while (cliente <= 0);

		return cliente;
	}

	public static int elegirMenu(int min, int max) {
		Scanner sc = new Scanner(System.in);
		int numero = 0;
		
		System.out.println("introduce una opcion entre " + min + " y " + max);
		do {
			try {
				numero = sc.nextInt();
				if (numero < min && numero > max) {
					System.out.println("Recuerda tiene que ser entre" + min + " y " + max);
				}
			} catch (Exception e) {
				sc.nextLine();
				System.err.println("No has introcido un numero");
			}
		} while (numero < min && numero > max);
		return numero;
	}

	public static void main(String[] args) {
		String uri = "jdbc:mysql://localhost:3306/almacen";
		String usuario = "prueba";
		String passwd = "antitodo";
		String consulta;
		final int MIN = 1;
		final int MAX = 3;
		int opcion;
		int cliente;
		Scanner sc = new Scanner(System.in);


		Connection conexion = null;
		Statement instruccion = null;
		ResultSet cursor = null;

		try {
			System.out.println("1. Crear tabla\n2. Actualizar registro\n3. Borrar registro");
			opcion = elegirMenu(MIN, MAX);
			conexion = DriverManager.getConnection(uri, usuario, passwd);
			instruccion = conexion.createStatement();
			if (opcion == 1) {
				consulta = "CREATE TABLE CLIENTESIMPORTANTES AS "
						+ " SELECT CLIENTE, EMPRESA, DIRECCION1, POBLACION, PROVINCIA "
						+ " FROM CLIENTES"
						+ " WHERE TOTAL_FACTURA > 1000000";
				if (instruccion.executeUpdate(consulta) > 0) {
					System.out.println("Creado la tabla correctamente");
				} else {
					System.out.println("No se ha creado la tabla");
				}
			} else if (opcion == 2) {
				cliente = pedirCliente(conexion, instruccion, cursor, uri, usuario, passwd);
				System.out.println("Introduce una direccion:");
				consulta = "UPDATE CLIENTESIMPORTANTES SET DIRECCION1 = '" + sc.nextLine()
						+ "' WHERE CLIENTE = " + cliente;
				if (instruccion.executeUpdate(consulta) > 0) {
					System.out.println("Actualizado correctamente");
				} else {
					System.out.println("No se ha actualizado");
				}
			} else {
				cliente = pedirCliente(conexion, instruccion, cursor, uri, usuario, passwd);
				System.out.println("Desea borrar el cliente?");
				if (sc.nextLine().equalsIgnoreCase("SI")) {
					consulta = "DELETE FROM CLIENTESIMPORTANTES WHERE CLIENTE = " + cliente;
					if (instruccion.executeUpdate(consulta) > 0) {
						System.out.println("Borrado satisfactoriamente");
					} else {
						System.out.println("No se ha podido borrar");
					}
				}
			}


		} catch (SQLException e) {
			e.printStackTrace();
		}


	}

}
