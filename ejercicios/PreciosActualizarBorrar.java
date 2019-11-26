package jdbc;

import java.sql.*;
import java.util.Scanner;

public class PreciosActualizarBorrar {
	public static int pedirExistencias() {
		int cantidad = 0;
		Scanner sc = new Scanner(System.in);
		
		do {
			try {
				System.out.println("Introduce la cantidad de existencias");
				cantidad = sc.nextInt();
			} catch (Exception e) {
				System.err.println("Solo numeros");
				sc.nextLine();
			}
		} while (cantidad <= 0);

		return cantidad;
	}

	public static void main(String[] args) {
		String uri = "jdbc:mysql://localhost:3306/almacen";
		String usuario = "prueba";
		String passwd = "antitodo";
		int cantidad;
		int prCost;
		int prVent;
		int borrados = 0;
		int actualizados = 0;
		String consulta;

		try {
			Connection conexion = DriverManager.getConnection(uri,usuario, passwd);
			Statement instruccion = conexion.createStatement();
			cantidad = pedirExistencias();
			consulta = "SELECT * FROM ARTICULOS WHERE EXISTENCIAS > " + cantidad;
			ResultSet cursor = instruccion.executeQuery(consulta);
			while (cursor.next()) {
				if (cursor.getInt("EXISTENCIAS") > cantidad) {
					prCost = cursor.getObject("PR_COST") != null ? cursor.getInt("PR_COST") : -1;
					prVent = cursor.getObject("PR_VENT") != null ? cursor.getInt("PR_VENT") : -1;
					if (cursor.getInt("PR_VENT") == -1 && cursor.getInt("PR_COST") == -1) {
						consulta = "DELETE FROM ARTICULOS WHERE ARTICULO = " + cursor.getInt("ARTICULO") + " AND PROVEEDOR = " + cursor.getInt("PROVEEDOR");
						borrados += instruccion.executeUpdate(consulta);
					} else if (prCost == -1){
						consulta = "UPDATE ARTICULOS SET PR_COST = PR_VENT WHERE ARTICULO = " + cursor.getInt("ARTICULO") + " AND PROVEEDOR  = " + cursor.getInt("PROVEEDOR");
						actualizados += instruccion.executeUpdate(consulta);
					} else if (prVent == -1) {
						consulta = "UPDATE ARTICULO SET PR_VENT = " + prCost * 1.05;
						actualizados += instruccion.executeUpdate(consulta);
					}
				}
			}
			
			System.out.println("Actualizados " + actualizados + " y borrados " + borrados);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
