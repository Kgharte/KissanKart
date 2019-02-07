<?php  
	//http://localhost/kissankart/web_service/login.php?id=1&email=ankit&password=123123
	$response = array();
	require_once("../inc/connection.php");
	if(isset($_REQUEST['email'])==false || isset($_REQUEST['password'])==false)
	{
		array_push($response,array("error"=>"required inputs are missing. email,password must be given"));
	}
	else 
	{
		extract($_REQUEST);
		$FileName = $_SERVER['SCRIPT_NAME'];
		$password = md5($password);
		$sql = "select id,password,email from kkt_register where email='$email' and password='$password'";
		$result = mysqli_query($link,$sql) or die(LogError($sql,$FileName,$link));
		$total = mysqli_num_rows($result);
		if($total==0)
		{
			array_push($response,array("error"=>"invalid login attampt"));
		}
		else 
		{
			$row = mysqli_fetch_assoc($result);
			array_push($response,array("error"=>"no"));
			array_push($response,array("id"=>$row['id']));
		}
	}
	echo json_encode($response);
?>