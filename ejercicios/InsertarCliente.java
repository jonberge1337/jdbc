package jdbc;

import java.sql.*;
import java.util.Scanner;

public class InsertarCliente {

	public static String existe(String frase, String condicion, String tabla) {
		Scanner sc = new Scanner(System.in);
		boolean existe = false;
		String consulta;
		String entrada = null;
		Connection conexion = null;
		Statement instruccion = null;
		ResultSet cursor = null;
		try {
				conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/almacen", "prueba", "antitodo");
				instruccion = conexion.createStatement();
			do {
				System.out.println(frase);
				entrada = sc.nextLine();
				consulta = "SELECT * "
						+ " FROM " + tabla
						+ " WHERE " + condicion + " = '" + entrada + "'";
				cursor = instruccion.executeQuery(consulta);
				existe = cursor.next();
				if (!existe) {
					System.out.println(entrada + " no existe");
				}
			} while (existe);
		} catch (SQLException e) {
			System.err.println("Error al ejecutar la consulta");
		} finally {
			try {
				if (cursor != null) {
					cursor.close();
				}
				if (instruccion != null) {
					instruccion.close();
				}
				if (conexion != null) {
					conexion.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}

		return entrada;
	}
	public static void main(String[] args) {
		int cliente;
		String nombre;
		String apellido;
		String empresa;
		String provincia;
		String formaPago;
		String consulta;
		String frase;
		int insertado;
		
		Scanner sc = new Scanner(System.in);
		try {
			System.out.println("Introduce el numero de cliente:");
			cliente = sc.nextInt();
			
			sc.nextLine(); //vaciado de buffer
			
			System.out.println("Introduce el nombre del cliente:");
			nombre = sc.nextLine();
			
			System.out.println("Introduce el apellido del cliente:");
			apellido = sc.nextLine();
			
			System.out.println("Introduce la empresa del cliente:");
			empresa = sc.nextLine();
			
			
			frase = "Introduce la provincia del cliente";
			provincia = existe(frase, "DESCRIPCION", "PROVINCIA");
			
			frase = "Introduce la forma de pago del cliente";
			formaPago = existe(frase, "FORMPAGO", "FOMPAGO");
			
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/almacen", "prueba", "antitodo");
			Statement instruccion = conexion.createStatement();
			consulta = "INSERT INTO CLIENTES(CLIENTE, NOMBRE, APELLIDOS, EMPRESA, PROVINCIA, FORMPAGO)"
					+ " VALUES(" + cliente + ", " + nombre + ", " + apellido + ", " + empresa + ", " + provincia + ", " + formaPago;
			insertado = instruccion.executeUpdate(consulta);

			System.out.println(insertado > 0 ? "Insertado" : "No se ha insertado");
			
			conexion.close();
			instruccion.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
