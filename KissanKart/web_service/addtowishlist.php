<?php  
	//http://localhost/android_batch_8/web_service/addtowishlist.php?registerid=1&productid=1
	$response = array();
	require_once("../inc/connection.php");
	if(isset($_REQUEST['registerid'])==false || isset($_REQUEST['productid'])==false)
	{
		array_push($response,array("error"=>"required inputs are missing."));
	}
	else 
	{
		extract($_REQUEST);
		$FileName = $_SERVER['SCRIPT_NAME'];
		$sql = "select count(*) 'total' from wishlist where registerid=$registerid and productid=$productid";
		$result = mysqli_query($link,$sql) or die(LogError($sql,$FileName,$link));
		$row = mysqli_fetch_assoc($result);
		if($row['total']>=1)
		{
			array_push($response,array("error"=>"product Allready added in Wishlist"));
		}
		else 
		{
			$today = date("Y-m-d");
			$sql = "insert into wishlist (registerid,productid,wishlistdate) values ($registerid,$productid,'$today')";
			mysqli_query($link,$sql) or die(LogError($sql,$FileName,$link));
			array_push($response,array("error"=>"no"));
			array_push($response,array("msg"=>"Product added into wishlist"));
		}
	}
	echo json_encode($response);
?>