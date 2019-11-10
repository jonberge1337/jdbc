#include <stdio.h>
#include <stdlib.h>
#include <mysql/mysql.h>

static char *host = "localhost";
static char *user = "prueba";
static char *pass = "antitodo";
static char *dbname = "almacen";

unsigned int port = 3306;
static char *unix_socket = NULL;
unsigned int flag = 0;

int main(){
    MYSQL *conn;

    conn = mysql_init(NULL);

    if (!(mysql_real_connect(conn, host, user, pass, dbname, port, unix_socket, flag))){
        puts("Error a la hora de conectar");
        exit(1);
    }
    puts("Conectado correctamente");

    return 0;
}
