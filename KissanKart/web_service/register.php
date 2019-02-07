<?php  
	//http://localhost/kissankart/web_service/register.php?name=Kalpesh&password=123123&email=ghartekalpesh10@gmail.com&mobileno=1234567890&regid=0
	$response = array();
	require_once("../inc/connection.php");
	if(isset($_REQUEST['name'])==false || isset($_REQUEST['password'])==false || isset($_REQUEST['email'])==false || isset($_REQUEST['mobileno'])==false || isset($_REQUEST['regid'])==false)
	{
		array_push($response,array("error"=>"required inputs are missing. name,password,email,mobileno,regid must be given"));
	}
	else 
	{
		extract($_REQUEST);
		$FileName = $_SERVER['SCRIPT_NAME'];
		$sql = "select count(*) 'total' from kkt_register where email='$email' or mobileno='$mobileno' or name='$name'";
		$result = mysqli_query($link,$sql) or die(LogError($sql,$FileName,$link));
		$row = mysqli_fetch_assoc($result);
		if($row['total']>=1)
		{
			array_push($response,array("error"=>"duplicate details. name mobile & email address must be unique"));
		}
		else 
		{
			$password = md5($password);
			$sql = "insert into kkt_register (name,password,mobileno,email) values ('$name','$password','$mobileno','$email')";
			$result = mysqli_query($link,$sql) or die(LogError($sql,$FileName,$link));
			array_push($response,array("error"=>"no"));
			array_push($response,array("id"=>mysqli_insert_id($link)));
		}
	}
	echo json_encode($response);
?>