<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<HTML lang=zh-CN xmlns="http://www.w3.org/1999/xhtml">
<HEAD>
<TITLE>欢迎使用JESSE司法社区矫正管理平台</TITLE>
<LINK rel=stylesheet type=text/css href="css/newStyle.css">
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js">
</script>
</HEAD>
<SCRIPT type="text/javascript">
jQuery(function (){
	//聚焦时触发的事件  
    $("#userPhone").focus(function(){  
        $("#msg").html("请输入有效的手机号");  
        $("#tjsub").attr('disabled',"true");
        $("#msg").css('color','blue');
    });  
    //点击按钮时触发事件  
    $("#userPhone").blur(function(){  
        var userPhone=$("#userPhone").val().replace(/\s/g,'');//去除头尾空格  
        if(userPhone==''){  
            $("#msg").html("手机号不能为空!"); 
            $("#msg").css('color','red');
            $("#userPhone").val("");      
            return false;  
        }   
        if(userPhone.lenght>11){  
            $("#msg").html("手机号超过长度!"); 
            $("#msg").css('color','red');
            return false;  
        }  
                //使用jQuery提交，回调函数function(data)里返回相应信息:data  
        $.post("Gb_checkLoginname.action", {"userPhone":userPhone}, function(data){  
           
            if(data=="电话号已存在，请更换!"){
            	$("#tjsub").attr('disabled',"true");
            	$("#msg").css('color','red');6.100
                }
            if(data=="恭喜你，这个电话号可用!"){
            	$("#msg").css('color','green');
            	$("#tjsub").removeAttr("disabled");
                }
            $("#msg").html(data); 
        });  
        return false;  
    });  
});  

</SCRIPT>	
<BODY >
<s:if test="#request.state==0">
<script >alert("添加成功!")</script></s:if>
<s:if test="#request.state==1">
<script >alert("以添加,请勿重复提交！")</script></s:if>
<DIV id=wrapperHome>
  <DIV id=main>
    <DIV id=banner></DIV>
    <DIV id=middlePlan align="center">
      <form id="form1" name="form1" method="post" action="Gb_Adduserinfo.action">
        <table width="100%" border="1">
          <tr>
            <td colspan="2" align="center">手工同步订购人员信息
             &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="Gb_phoneSelect.action">返回</a> </td>
            <td align="center">
            <label>
              <input type="button" name="Submit" value="退出" onClick="location.href='Gb_loginOut.action'">
            </label></td>
          </tr>
           <tr>
            <td width="20%"><div align="center"> 手机号</div></td>
            <td><input name='userPhone' id='userPhone' type='text' /><span  id='msg'></span></td>
           </tr>
           <tr>
            <td width="10%"><div align="center">集团企业标识</div></td>
             <td><input name='ordid' type='text' /></td>
            </tr>
            <tr>
             <td width="25%"><div align="center">企业代码</div></td>
              <td><input name='ecid' type='text' /></td>
             </tr>
             
            <tr>
            <td width="35%"><div align="center">开通状态</div></td>
             <td>
             	<select name='orpCode' id='orpCode'>
             		<option value='03' selected="selected" >定购</option>
             		
             		<option value='04'>取消定购</option>
             		<option value='05'>暂停</option>
             		<option value='06'>恢复</option>
             		<option value='08'>变更</option>
             	</select>
             </td>
            </tr>
            <tr>
            <td width="35%"><div align="center">添加时间</div></td>
            <% 
			java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyyMMddHHmmsssss");			
			java.util.Date currentTime = new java.util.Date();//得到当前系统时间
			String str_date1 = formatter.format(currentTime); //将日期时间格式化 
			String str_date2 = currentTime.toString(); //将Date型日期时间转换成字符串形式 
			%>
             <td><input name='tjtime' type='text' value='<%=str_date1%>'/></td>
          </tr>
           <tr>
             <td colspan="2" align="center"><input  id='tjsub' type='submit' value='提交该人员'/></td>
          </tr>
        </table>
      </form>
    </DIV>
  </DIV>
</DIV>
<DIV id=footer>Copyright (C) 2017 www.jszy.com, All Rights Reserved. <br> 2011-2017 版权所有北京锦绣致远科技有限责任公司 </DIV>
 
</BODY>
</HTML>
