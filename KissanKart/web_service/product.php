<?php 
	//example how to call this service 
	//to get product with given price range & category 
	//http://localhost/android_batch_8/web_service/product.php?categoryid=1&price=110&mode=<=
	//to get product of only particular category 
	//http://localhost/android_batch_8/web_service/product.php?categoryid=1
	//to get list of product having given title 
	//http://localhost/android_batch_8/web_service/product.php?title=shirt
	// or to get all product 
	//http://localhost/android_batch_8/web_service/product.php
	//include database connectivity file 
	require_once("../inc/connection.php");
	//declare an array 
	$response = array();
	$error=NULL;
	//check whether there is any input or not 
	if(isset($_REQUEST['categoryid'])==true && isset($_REQUEST['price'])==true)
	{
		$categoryid = $_REQUEST['categoryid'];
		$price = $_REQUEST['price'];
		
	
		
			$sql = "select * from kkt_product where categoryid=$categoryid and price$mode$price order by title";
		
		
	}
	else if(isset($_REQUEST['categoryid'])==true)
	{
		$categoryid = $_REQUEST['categoryid'];
		$sql = "select * from kkt_product where categoryid=$categoryid order by title";
	}
	else if(isset($_REQUEST['title'])==true)
	{
		$title = $_REQUEST['title'];
		$sql = "select * from kkt_product where title like '%$title%' order by title";
	}
	else 
	{
		$sql = "select * from kkt_product order by title";
	}
	if($error==NULL)
	{
		//echo $sql;
		array_push($response,array("error"=>"no"));
		$FileName = $_SERVER['SCRIPT_NAME'];
		$result = mysqli_query($link,$sql) or die(LogError($sql,$FileName,$link));
		array_push($response,array("total"=>mysqli_num_rows($result)));
		while($row=mysqli_fetch_assoc($result))
		{
			$response[] = $row;
		}
	}
	echo json_encode($response);
?>