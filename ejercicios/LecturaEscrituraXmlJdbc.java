package jdbc;

import java.io.File;
import java.io.IOException;
import java.sql.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class LecturaEscrituraXmlJdbc {

	public static void main(String[] args) {
		String uri = "jdbc:mysql://localhost:3306/almacen";
		String usuario = "jon";
		String passwd = "antitodo";
		String consulta;

		Alumno personas[] = new Alumno[10];
		personas[0] = new Alumno("1DM3", "Jon", "Bergerandi", "Loidi", "Zarautz", "Masculino");
		personas[1] = new Alumno("1DM3", "Kevin", "Blanco", "Matamoros", "Irun", "Masculino");
		personas[2] = new Alumno("1DM3", "Unax", "Arretxe", "Egaña", "Irun", "Masculino");
		personas[3] = new Alumno("1DM3", "Mikel", "Seara", "Garcia", "Andoain", "Masculino");
		personas[4] = new Alumno("1DM3", "Gloria", "Nieves", "Del Olmo", "Huelva", "Femenino");
		personas[5] = new Alumno("2DM3", "Lander", "Ucin", "Olazabal", "Getaria", "Masculino");
		personas[6] = new Alumno("2DM3", "Diego", "Sanchez", "Aguirregomezcorta", "Guayakil", "Masculino");
		personas[7] = new Alumno("2DM3", "Ander", "Almandoz", "Keregeta", "San Sebastian", "Masculino");
		personas[8] = new Alumno("2DM3", "Luis", "Garcia", "Lezertua", "Pasajes", "Masculino");
		personas[9] = new Alumno("2DM3", "Iñaki", "Alzaga", "Berasategui", "Trintxerpe", "Masculino");
		
		Connection conexion = null;
		Statement instruccion = null;

		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			DOMImplementation dom = builder.getDOMImplementation();
			Document documento = dom.createDocument(null, "alumnos", null);
			
			File archivo = new File("/home/jb/Deskargak/alumnos.xml");
			
			for (Alumno alumno : personas) {
				Element curso = documento.createElement("curso");
				Attr atributo = documento.createAttribute("id");
				atributo.setValue(alumno.getCurso());
				curso.setAttributeNode(atributo);
				documento.getDocumentElement().appendChild(curso);
				
				Element nombre = documento.createElement("nombre");
				nombre.appendChild(documento.createTextNode(alumno.getNombre()));
				curso.appendChild(nombre);
				
				Element apellido1 = documento.createElement("apellido1");
				apellido1.appendChild(documento.createTextNode(alumno.getApellido1()));
				curso.appendChild(apellido1);
				
				Element apellido2 = documento.createElement("apellido2");
				apellido2.appendChild(documento.createTextNode(alumno.getApellido2()));
				curso.appendChild(apellido2);
				
				Element ciudad = documento.createElement("ciudad");
				ciudad.appendChild(documento.createTextNode(alumno.getLugarNacimiento()));
				curso.appendChild(ciudad);
				
				Element genero = documento.createElement("genero");
				genero.appendChild(documento.createTextNode(alumno.getGenero()));
				curso.appendChild(genero);
				
				TransformerFactory transFactoria = TransformerFactory.newInstance();
				Transformer transformador = transFactoria.newTransformer();
				
				Source source = new DOMSource(documento);
				Result resultado = new StreamResult(archivo);
				
				transformador.setOutputProperty(OutputKeys.INDENT, "yes");
				transformador.transform(source, resultado);

			}

			conexion = DriverManager.getConnection(uri, usuario, passwd);
			instruccion = conexion.createStatement();
			consulta = "CREATE TABLE IF NOT EXISTS ALUMNOS("
					+ "ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,"
					+ "CURSO VARCHAR(8),"
					+ "NOMBRE VARCHAR(50),"
					+ "APELLIDO1 VARCHAR(50),"
					+ "APELLIDO2 VARCHAR(50),"
					+ "CIUDAD VARCHAR(50),"
					+ "GENERO VARCHAR(15)"
					+ ");";
			instruccion.executeUpdate(consulta);
			System.out.println("No he cascado");
			documento = builder.parse(archivo);
			NodeList listaAlumnos = documento.getElementsByTagName("curso");
			for (int i = 0; i < listaAlumnos.getLength(); i++) {
				Node nodo = listaAlumnos.item(i);
				if (nodo.getNodeType() == Node.ELEMENT_NODE) {
					Element elemento = (Element) nodo;
					consulta = "INSERT INTO ALUMNOS(CURSO, NOMBRE, APELLIDO1, APELLIDO2, CIUDAD, GENERO) "
							+ " VALUES('"
							+ elemento.getAttribute("id") + "', '"
							+ elemento.getElementsByTagName("nombre").item(0).getTextContent() + "', '"
							+ elemento.getElementsByTagName("apellido1").item(0).getTextContent() + "', '"
							+ elemento.getElementsByTagName("apellido2").item(0).getTextContent() + "', '"
							+ elemento.getElementsByTagName("ciudad").item(0).getTextContent() + "', '"
							+ elemento.getElementsByTagName("genero").item(0).getTextContent() + "')";
					instruccion.executeUpdate(consulta);
				}
			}
			consulta = "SELECT * FROM ALUMNOS";
			ResultSet cursor = instruccion.executeQuery(consulta);
			ResultSetMetaData rsmd = cursor.getMetaData();
			String resultado;
			while (cursor.next()) {
				resultado = "";
				for (int i = 0; i < rsmd.getColumnCount(); i++) {
				resultado += rsmd.getColumnName(i+1) + ": ";
				resultado += cursor.getString(i+1) + " ";
				}
				System.out.println(resultado);
			}
		} catch (ParserConfigurationException | TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}


class Alumno{
	private String curso;
	private String nombre;
	private String apellido1;
	private String apellido2;
	private String lugarNacimiento;
	private String genero;

	public Alumno(String curso, String nombre, String apellido1, String apellido2, String lugarNacimiento, String genero) {
		this.curso = curso;
		this.nombre = nombre;
		this.apellido1 = apellido1;
		this.apellido2 = apellido2;
		this.lugarNacimiento = lugarNacimiento;
		this.genero = genero;
	}

	public String getCurso() {
		return curso;
	}
	public void setCurso(String curso) {
		this.curso = curso;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido1() {
		return apellido1;
	}
	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}
	public String getApellido2() {
		return apellido2;
	}

	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}
	public String getLugarNacimiento() {
		return lugarNacimiento;
	}
	public void setLugarNacimiento(String lugarNacimiento) {
		this.lugarNacimiento = lugarNacimiento;
	}
	public String getGenero() {
		return genero;
	}
	public void setGenero(String genero) {
		this.genero = genero;
	}
	
}