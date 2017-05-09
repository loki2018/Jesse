<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<HTML lang=zh-CN xmlns="http://www.w3.org/1999/xhtml">
<HEAD>
<TITLE>欢迎使用JESSE司法社区矫正管理平台</TITLE>
<LINK rel=stylesheet type=text/css href="css/newStyle.css">
<script type="text/javascript">

var errorMsg="${backMess}";
if(errorMsg!=""){alert(errorMsg);}

function checkspace(checkstr) {
  var str = '';
  for(i = 0; i < checkstr.length; i++) {
    str = str + ' ';
  }
  return (str == checkstr);
}

function check()
{
	if(checkspace(document.loginform.userName.value))  {		
		alert("对不起，用户名不能为空，请重新输入！");
		document.loginform.userName.focus();
		return false;
	}
	
	if(checkspace(document.loginform.userPass.value))  {		
		alert("对不起，密码不能为空，请重新输入！");
		document.loginform.userPass.focus();
		return false;
	}
	
	if(checkspace(document.loginform.loginCode.value))  {		
		alert("对不起，验证码不能为空，请重新输入！");
		document.loginform.loginCode.focus();
		return false;
	}
	
	return true;
}
      
/*验证码上刷新 */ 
function chk_image(){ 
    var img = document.getElementById("pic"); 
    img.src = "code.jsp?" + Math.random(); 
} 
</script>
</HEAD>
<BODY >
<DIV id=wrapperHome style="width: 100%; height: 100%" >
  <DIV id=main>
    <DIV id=banner></DIV>
    <DIV id=middlePlan align="center">
    <form name="loginform" method="post" action="Gb_chk.action" onSubmit="return check();">
    <table width="297" border="0">
  <tr>
  
    <td colspan="3" align="left"><img src="images/til_Introduction.gif" width="150" height="40"></td>
    </tr>
  <tr>
    <td width="96" height="41" align="center">用户名：</td>
    <td colspan="2"><label>
      <input type="text" name="userName"  >
    </label></td>
  </tr>
  <tr>
    <td height="40" align="center">密&nbsp;&nbsp;&nbsp;码：</td>
    <td colspan="2"><label>
    <input name="hid" type="hidden" value="0"/>
      <input type="text" name="userPass" >
    </label></td>
  </tr>
  <tr>
    <td height="49" align="center">验证码：</td>
    <td width="88" align="center">
      <input name="loginCode" type="text" size="5" >      </td>
    <td width="99"><img id="pic" border=1 src="code.jsp?,Math.random();" onClick="return chk_image();" alt="点击图片刷新验证码" /></td>
  </tr>
  <tr>
    <td height="37" colspan="3" align="center">
      <input type="submit" name="Submit2" value="提交">&nbsp;&nbsp;&nbsp;
      <input type="reset" name="reset" value="重置"></td>
    </tr>
</table>
    </form>
 </DIV>
  </DIV>
  <DIV id=footer>Copyright (C) 2017 www.jszy.com, All Rights Reserved. <br> 2011-2017 版权所有北京锦绣致远科技有限责任公司 </DIV>
</DIV>

</BODY>
</HTML>
