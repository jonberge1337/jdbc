#!/usr/bin/perl -w

use strict;
use warnings;

use DBI;

my $dbname = 'almacen';
my $dbhost = 'localhost';
my $dbuser = 'prueba';
my $dbpwd = 'antitodo';

my $dbh;
my $stmt;
my $sth;
my $row;

## Conectarse a la base de datos
##
$dbh = DBI->connect("DBI:mysql:$dbname;host=$dbhost", $dbuser, $dbpwd)
    or die "Error de conexion: $DBI::errstr";


## Leer los registros de la tabla
##
$sth = $dbh->prepare("SELECT * FROM CLIENTES");
$sth->execute();
while ($row = $sth->fetchrow_hashref) {
    print "nombre: " . $row->{NOMBRE} . "\n";
    print "apellido: " . $row->{APELLIDOS} . "\n";
    print "provincia: " . $row->{PROVINCIA} . "\n";
}
## Desconectarse de la base de datos
##
if (! $dbh->disconnect) {
    warn "Error al desconectarse de la base de datos: $DBI::errstr";
}
