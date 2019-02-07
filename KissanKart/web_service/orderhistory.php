<?php  
	//http://localhost/kissankart/web_service/orderhistory.php?registerid=45
	$response = array();
	require_once("../inc/connection.php");
	if(isset($_REQUEST['registerid'])==false)
	{
		array_push($response,array("error"=>"required inputs are missing."));
	}
	else 
	{
		extract($_REQUEST);
		$FileName = $_SERVER['SCRIPT_NAME'];
		
		$sql = "select id from kkt_product";
		$sql = "SELECT pr.*,kp.title,kp.image FROM `product_order` pr, kkt_product kp where kp.id=pr.productid and pr.registerid={$_REQUEST['registerid']}";
		$result = mysqli_query($link,$sql) or die(LogError($sql,$FileName,$link));
		$count = mysqli_num_rows($result);
		array_push($response,array("error"=>"no"));
		array_push($response,array("count"=>$count));
		if($count>=1)
		{
			while($row = mysqli_fetch_assoc($result))
			{
				$response[] = $row;
			}
		}
	}
	echo json_encode($response);
?>