package jdbc;

import java.sql.*;
import java.util.Scanner;

public class ClienteTotalFactura {

	public static void main(String[] args) {
		String conex = "jdbc:mysql://localhost:3306/almacen";
		String usuario = "prueba";
		String pass = "antitodo";
		String consulta;
		String cliente;
		String total;
		
		Connection conexion = null;
		Statement instruccion = null;
		ResultSet cursor = null;
		Scanner sc = new Scanner(System.in);
		
		try {
			conexion = DriverManager.getConnection(conex, usuario, pass);
			instruccion = conexion.createStatement();
			System.out.println("Introduce el cliente que quieres actualizar");
			cliente = sc.nextLine();
			consulta = "SELECT * FROM ALBARANES WHERE CLIENTE = " + cliente;
			cursor = instruccion.executeQuery(consulta);
			if(cursor.next()) {
				consulta = "SELECT SUM(LINEAS.CANTIDAD * LINEAS.PRECIO * (LINEAS.DESCUENTO/100)) AS TOTAL"
						+ " FROM ALBARANES "
						+ " INNER JOIN LINEAS ON ALBARANES.ALBARAN = LINEAS.ALBARAN "
						+ " WHERE ALBARANES.CLIENTE = " + cliente;
				cursor = instruccion.executeQuery(consulta);
				if (cursor.next()) {
					total = cursor.getString("TOTAL");
					consulta = "UPDATE CLIENTES SET TOTAL_FACTURA = " + total + " WHERE CLIENTE = " + cliente;
					if (instruccion.executeUpdate(consulta) == 1) {
						System.out.println("Se ha actualizado correctamente");
					} else {
						System.out.println("No se ha actualizado");
					}
				}
			} else {
				System.out.println("Ese cliente o no existe o no tiene compras");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		

	}

}
