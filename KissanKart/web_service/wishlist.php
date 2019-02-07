<?php  
	//http://localhost/android_batch_8/web_service/wishlist.php?registerid=1
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
		$sql = "select w.id 'id',title,price,photo from wishlist w,product p where productid=p.id and registerid=$registerid";
		$result = mysqli_query($link,$sql) or die(LogError($sql,$FileName,$link));
		array_push($response,array("error"=>"no"));
		array_push($response,array("count"=>mysqli_num_rows($result)));
		while($row = mysqli_fetch_assoc($result))
		{
			$response[]=$row;
		}
	}
	echo json_encode($response);
?>