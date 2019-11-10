package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Statement;

public class ClientesSoria {

	public static void main(String[] args) {
		try {
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/almacen", "prueba", "antitodo");
			Statement instruccion = (Statement) conexion.createStatement();
			String consulta = "SELECT `CLIENTES`.* "
					+ " FROM `CLIENTES` "
					+ " INNER JOIN `PROVINCIAS` ON `CLIENTES`.`PROVINCIA` = `PROVINCIAS`.`PROVINCIA`"
					+ " WHERE UPPER(`PROVINCIAS`.`DESCRIPCION`) = 'SORIA';";
			ResultSet cursor = instruccion.executeQuery(consulta);
			System.out.println("Listado de clientes de Soria");
			while (cursor.next()) {
				System.out.println("Cliente: " + cursor.getString("CLIENTES.NOMBRE"));
			}
			cursor.close();
			instruccion.close();
			conexion.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
