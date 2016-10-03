<?php

  $usuario  = "root";
  $clave    = "";
  $database = "empresa";

  $conexion = mysql_connect( "localhost", $usuario, $clave) or die("No se pudo conectar");
  mysql_select_db( "$database", $conexion );

?>  