<?php

  /*include  ("conexion.php");
  		$con=mysqli_connect("localhost","root","","empresa");
  		//sentencia INSERT
		$sql1 = "INSERT INTO usuarios ( usucod, usunom, usuape, usucc, usucla) VALUES( 'c04', 'andres', 'mejia', '1014', '12345' )";

		
	if (mysqli_query($con, $sql1))
	{
   		echo "Los valores se han insertado con éxito";
	}*/

    $usuario  = "root";
    $clave    = "";
    $database = "empresa";

	$conexion = mysql_connect( "localhost", $usuario, $clave) or die("No se pudo conectar");
	mysql_select_db( "$database", $conexion );

	$nombre=$_REQUEST['nombre'];
	$apellido=$_REQUEST["apellido"];
	$email=$_REQUEST["email"];
	$password=$_REQUEST["password"];

	if ($nombre!=" " || $apellido!=" " || $email!=" " || $password!=" ") {
		# code...
		$sql =  "INSERT INTO usuarios(nombre, apellido, email, password) VALUES('$nombre', '$apellido', '$email', '$password')";

		$result = mysql_query($sql);
		echo $result;
		echo "Usted se ha registrado correctamente."; 
	}else{
		echo "-1";
	}
 

?>