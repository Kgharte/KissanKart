<?php  
	//http://localhost/kissankart/web_service/payment.php?registerid=45&fullname=ankit&address=waghawadiroad&pincode=364001&city=bhavnagar
	$response = array();
	require_once("../inc/connection.php");
	if(isset($_REQUEST['registerid'])==false || isset($_REQUEST['fullname'])==false || isset($_REQUEST['address'])==false || isset($_REQUEST['pincode'])==false || isset($_REQUEST['city'])==false)
	{
		array_push($response,array("error"=>"required inputs are missing."));
	}
	else 
	{
		extract($_REQUEST);
		$FileName = $_SERVER['SCRIPT_NAME'];
		
		//steps 
		/* 
		   1) add new record into product_order table with only registerid field
		   2) now get the id of newly inserted record in product_order table 
		   3) calculate total product order amount from cart table 
		   4) update cart table set product_orderid to id we got in second steps 
		   5) on completion of 4th step update product_order table with details given by user 
		*/
		//step -1 
		$sql = "insert into product_order (registerid) values ($registerid)";
		mysqli_query($link,$sql) or die(LogError($sql,$FileName,$link));
		
		//step -2 (max,min,average,sum,count)
		$sql = "select max(id) 'billno' from product_order where registerid=$registerid";
		$result= mysqli_query($link,$sql) or die(LogError($sql,$FileName,$link));
		$result_row=mysqli_fetch_array($result);
		$billno = $result_row['billno'];
		
		//step -3 
		$sql = "select sum(price*quantity) 'amount' from kkt_cart where product_orderid=0 and registerid=$registerid";
		$result2= mysqli_query($link,$sql) or die(LogError($sql,$FileName,$link));
		$total_row=mysqli_fetch_array($result2);
		$amount = $total_row['amount'];
		
		//step-4
		$sql = "update kkt_cart set product_orderid=$billno where registerid=$registerid and product_orderid=0";
		mysqli_query($link,$sql) or die(LogError($sql,$FileName,$link));
		
		//step -5 
		$orderdate = date("Y-m-d");
		$paymentmode=0; // 0=cash on delivery
		$paymentstatus=1; //1=unpaid
		
		$sql = "update product_order set amount=$amount,orderdate='$orderdate',fullname='$fullname',addressline1='$address',city='$city',pincode='$pincode',paymentmode=$paymentmode,paymentstatus=$paymentstatus where id=$billno";
		mysqli_query($link,$sql) or die(LogError($sql,$FileName,$link));
		
		array_push($response,array("error"=>"no"));
		array_push($response,array("msg"=>"Order placed sucessfully"));
	}
	echo json_encode($response);
?>