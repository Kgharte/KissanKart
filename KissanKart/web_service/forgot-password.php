<?php  
	//http://localhost/kissankart/web_service/forgot-password.php?email=ankit3385@gmail.com
	$response = array();
	error_reporting(0);
	require_once("../inc/connection.php");
	if(isset($_REQUEST['email'])==false)
	{
		array_push($response,array("error"=>"required inputs are missing."));
	}
	else 
	{
		extract($_REQUEST);
		$FileName = $_SERVER['SCRIPT_NAME'];
		$sql = "select count(*) 'total' from kkt_register where email='$email'";
		$result = mysqli_query($link,$sql) or die(LogError($sql,$FileName,$link));
		$row = mysqli_fetch_assoc($result);
		if($row['total']==0)
		{
			array_push($response,array("error"=>"invalid email address"));
		}
		else 
		{
			$newpassword = rand(10,99) .rand(10,99) . rand(10,99);
			$EncryptedPassword = md5($newpassword);
			$sql = "update kkt_register set password='$EncryptedPassword' where email='$email'";
			mysqli_query($link,$sql) or die(LogError($sql,$FileName,$link));
			$subject = "Password recovered successfully";
			$content = "Congratulation, we have just recovered password and it is <b> $newpassword </b>";
			require_once("../inc/function.php");
			SendMail($email,$subject,$content);
			array_push($response,array("error"=>"no"));
		}
	}
	echo json_encode($response);
?>