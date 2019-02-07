<?php  
	//http://localhost/kissankart/web_service/addtocart.php?registerid=1&productid=1&price=500
	$response = array();
	require_once("../inc/connection.php");
	if(isset($_REQUEST['registerid'])==false 
	|| isset($_REQUEST['productid'])==false 
	|| isset($_REQUEST['price'])==false)
	{
		array_push($response,array("error"=>"required inputs are missing."));
	}
	else 
	{
		extract($_REQUEST);
		$FileName = $_SERVER['SCRIPT_NAME'];
		$sql = "select count(*) 'total' from kkt_cart where registerid=$registerid and productid=$productid and product_orderid=0";
		$result = mysqli_query($link,$sql) or die(LogError($sql,$FileName,$link));
		$row = mysqli_fetch_assoc($result);
		if($row['total']>=1)
		{
			$sql = "update kkt_cart set quantity=quantity+1 where registerid=$registerid and productid=$productid and product_orderid=0";
		}
		else 
		{
			$sql = "insert into kkt_cart (registerid,productid,price,quantity,product_orderid) values ($registerid,$productid,$price,1,0)";
		}
		mysqli_query($link,$sql) or die(LogError($sql,$FileName,$link));
		array_push($response,array("error"=>"no"));
		array_push($response,array("msg"=>"Product added into Cart"));
	}
	echo json_encode($response);
?>