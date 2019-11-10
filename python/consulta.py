import mysql.connector

conexion = mysql.connector.connect(
    host = "localhost",
    user = "prueba",
    passwd = "antitodo",
    database = "almacen"
)

consulta = conexion.cursor()
consulta.execute("SELECT * FROM CLIENTES")

cursor = consulta.fetchall()

for i in cursor:
    print(i)
