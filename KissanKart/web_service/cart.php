<?php  
	//http://localhost/kissankart/web_service/cart.php?registerid=1
	$response = array();
	require_once("../inc/connection.php");
	if(isset($_REQUEST['registerid'])==false )
	{
		array_push($response,array("error"=>"required inputs are missing."));
	}
	else 
	{
		extract($_REQUEST);
		$FileName = $_SERVER['SCRIPT_NAME'];
		$sql = "select c.id 'id',productid,c.price 'price',c.quantity 'quantity',p.title 'title',p.image 'image' from kkt_cart c, kkt_product p where registerid=$registerid and product_orderid=0 and p.id=productid";
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