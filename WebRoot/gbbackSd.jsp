<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>手动归档报文给GB程序</title>
  <script type="text/javascript">
var errorMsg="${backMess}";
if(errorMsg!=""){alert(errorMsg);}
</script>

  </head>
  
  <body>
手动归档报文给GB程序
	<form name="from1" action="Gb_backSd_gb.action" method="post">
		请输入归档GB服务(gbservlet)地址：<input size="50" name="gb_strUrl"  /><br>
		(请输入报文):<br>
	    <textarea rows="50" cols="60" name="xmlString"></textarea><input type="submit" value="提交 ">
		</form>
  </body>
</html>
