<?php 
	//example how to call this service 
	//to get product with given price range & category 
	//http://localhost/kissankart/web_service/product-detail.php?id=1
	require_once("../inc/connection.php");
	//declare an array 
	$response = array();
	//check whether there is any input or not 
	if(isset($_REQUEST['id'])==true)
	{
		$id = $_REQUEST['id'];
		$sql = "select * from kkt_product where id=$id";
		$FileName = $_SERVER['SCRIPT_NAME'];
		$result = mysqli_query($link,$sql) or die(LogError($sql,$FileName,$link));
		array_push($response,array("error"=>"no"));
		array_push($response,array("count"=>mysqli_num_rows($result)));
		$row=mysqli_fetch_assoc($result);
		$response[] = $row;
	}
	else 
	{
		array_push($response,array("error"=>"input missing"));
	}
	echo json_encode($response);
?>