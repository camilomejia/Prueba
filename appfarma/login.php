<?php
  
  $data = array();
  if( isset( $_REQUEST["usuario"]) && isset( $_REQUEST["password"]) ){
  include  ("conexion.php");
    $usuario = $_REQUEST["usuario"];
    $password   = $_REQUEST["password"];

    $sql1 = "select nombre, apellido, email from usuarios where id='$usuario' and password='$password' ";
    $cursor1 = mysql_query( $sql1, $conexion ); 
    if( $row1 = mysql_fetch_array( $cursor1 ) ){
      $data['mensaje'] = "Encontrado";
      $data['estado'] = "1";
      $data['nombre'] = trim( $row1['nombre']);
      $data['apellido'] = trim( $row1['apellido']);
      $data['email'] = trim( $row1['email']);
    }
    else{
      $data['mensaje'] = "No existe";
      $data['estado'] = "0";
  	  $data['nombre'] = "NO EXISTE....";
    }

  }
  else{
    $data['estado'] = "-1";
    $data['mensaje'] = "Debe digitar usuario y clave...";
  }
  echo json_encode($data);

?>