<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<HTML lang=zh-CN xmlns="http://www.w3.org/1999/xhtml">
<HEAD>
<TITLE>欢迎使用位置服务基础业务管理平台</TITLE>
<LINK rel=stylesheet type=text/css href="css/newStyle.css">
</HEAD>
<SCRIPT type="text/javascript">
	function sub(){
	
	 var form1 = window.document.getElementById("form1");
	 form1.action="./Gb_ecnameSelect.action";
	 form1.submit();
	
	}
</SCRIPT>
<BODY >
<DIV id=wrapperHome>
  <DIV id=main>
    <DIV id=banner></DIV>
    <DIV id=middlePlan align="center">
      <form id="form1" name="form1" method="post" action="Gb_userinfoSelectByOrdid.action">
        <table width="100%" border="1">
          <tr>
            <td colspan="4" align="left">成员列表 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;集团业务标识 :<input type="text" name="ordid"/><input type="Submit" name="Submit1" value="查询">
            	
             &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="Gb_ecnameSelect.action">返回</a> 
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
             &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
            <td align="center">
            <label>
              <input type="button" name="Submit" value="退出" onClick="location.href='Gb_loginOut.action'">
            </label></td>
          </tr>
           <tr>
            <td width="20%"><div align="center"> 手机号</div></td>
             <td width="25%"><div align="center">ECID</div></td>
            <td width="10%"><div align="center">状态</div></td>
            <td width="35%"><div align="center">认证状态</div></td>
            <td width="35%"><div align="center">添加时间</div></td>
          </tr>
          <s:iterator value="pageBean.list">
            <tr>
              <td><s:property value="MSISDN" /></td>
              <td><s:property value="ECID" /></td>
              <s:set name="listId1" value="OprCode" />
              <td><div align="center">
                  <s:if test="'03'==#listId1">定购</s:if>
                  <s:if test="'04'==#listId1"><a style="color: red">取消定购</a></s:if>
                  <s:if test="'05'==#listId1">暂停</s:if>
                  <s:if test="'06'==#listId1">恢复</s:if>
                  <s:if test="'08'==#listId1">变更</s:if>
                </div></td>
              <td><div align="center">
			     <% String ss="等待认证";%>
                  <s:set name="phoneId" value="MSISDN" />
                  <s:iterator value="listUserstateinfo">
                    <s:if test="MSISDN==#phoneId">
					 <%ss="";%>
                      <s:set name="listId2" value="Approbate" />
                      <s:if test="0==#listId2">不需认证</s:if>
                      <s:if test="1==#listId2">未认证</s:if>
                      <s:if test="2==#listId2">等待认证</s:if>
                      <s:if test="3==#listId2">认证通过</s:if>
                      <s:if test="4==#listId2">认证失败</s:if>
                    </s:if>
                  </s:iterator>
				   <%=ss%>
                </div></td>
              <td><div align="center">
                  <s:property value="tjtime" />
                </div></td>
            </tr>
          </s:iterator>
      

        </table>
      </form>
    </DIV>
  </DIV>
</DIV>
<DIV id=footer>?2001-2010 版权所有中国移动通信 </DIV>
</BODY>
</HTML>
