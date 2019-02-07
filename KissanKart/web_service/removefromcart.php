<?php  
	//http://localhost/kissankart/web_service/removefromcart.php?cartid=1
	$response = array();
	require_once("../inc/connection.php");
	if(isset($_REQUEST['cartid'])==false)
	{
		array_push($response,array("error"=>"required inputs are missing."));
	}
	else 
	{
		extract($_REQUEST);
		$FileName = $_SERVER['SCRIPT_NAME'];
		$sql = "delete from kkt_cart where id=$cartid";
		mysqli_query($link,$sql) or die(LogError($sql,$FileName,$link));
		array_push($response,array("error"=>"no"));
		array_push($response,array("msg"=>"Product removed from cart"));
	}
	echo json_encode($response);
?>