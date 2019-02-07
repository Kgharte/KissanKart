<?php  
	//http://localhost/kissankart/web_service/change-password.php?id=26&oldpassword=123123&newpassword=112233
	$response = array();
	require_once("../inc/connection.php");
	if(isset($_REQUEST['id'])==false || isset($_REQUEST['oldpassword'])==false && isset($_REQUEST['newpassword'])==false)
	{
		array_push($response,array("error"=>"required inputs is missing."));
	}
	else 
	{
		extract($_REQUEST);
		$FileName = $_SERVER['SCRIPT_NAME'];
		$oldpassword = md5($oldpassword);
		$sql = "select count(*) 'total' from kkt_register where id=$id and password='$oldpassword'";
		$result = mysqli_query($link,$sql) or die(LogError($sql,$FileName,$link));
		$row = mysqli_fetch_assoc($result);
		if($row['total']==0)
		{
			array_push($response,array("error"=>"invalid password"));
		}
		else 
		{
			//old password is valid
			$newpassword = sha1($newpassword);
			$sql = "update kkt_register set password='$newpassword' where id=$id";
			mysqli_query($link,$sql) or die(LogError($sql,$FileName,$link));
			array_push($response,array("error"=>"no"));
		}
	}
	echo json_encode($response);
?>