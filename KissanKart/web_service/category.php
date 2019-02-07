<?php  
	//error_reporting(0);
	//include connection file 
	$response = array();
	require_once("../inc/connection.php");
	//step 3 Build Query and run it 
	$sql = "select * from kkt_category order by title";
	$FileName = $_SERVER['SCRIPT_NAME'];
	$result = mysqli_query($link,$sql) or die(LogError($sql,$FileName,$link));
	array_push($response,array("error"=>"no"));
	array_push($response,array("total"=>mysqli_num_rows($result)));
	while($row = mysqli_fetch_assoc($result))
	{
		$response[] = $row;
	}
	echo json_encode($response);
?>