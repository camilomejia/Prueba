<?php

  $usuario  = "root";
  $clave    = "";
  $database = "empresa";

  $conexion = mysql_connect( "localhost", $usuario, $clave) or die("No se pudo conectar");
  mysql_select_db( "$database", $conexion );

  $sql = "SELECT id, nombre, apellido, email, password from usuarios";

  $datos = array();
  $result = mysql_query($sql, $conexion);

  while ($row = mysql_fetch_object($result)) {
  	# code...
  	$datos[] = $row;
  }

  echo json_encode($datos);

?>