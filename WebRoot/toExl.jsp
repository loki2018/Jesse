<%@ page language="java" import="java.util.*" pageEncoding="GB2312"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<HTML lang=zh-CN xmlns="http://www.w3.org/1999/xhtml">
<HEAD>
<TITLE>欢迎使用位置服务基础业务管理平台</TITLE>
<meta http-equiv=Content-Type content="text/html; charset=gb2312">
</HEAD>
<BODY >
        <table width="100%" border="1">
   
          <tr>
            <td width="24%"><div align="center"> 手机号</div></td>
            <td width="22%"><div align="center">状态</div></td>
            <td width="47%"><div align="center">认证状态</div></td>
            <td width="7%"><div align="center">定购时间</div></td>
          </tr>
          <s:iterator value="listUserinfo">
            <tr>
              <td><s:property value="MSISDN" />              </td>
              <s:set name="listId1" value="OprCode" />
              <td><div align="center">
                  <s:if test="'03'==#listId1">定购</s:if>
                  <s:if test="'04'==#listId1">取消定购</s:if>
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
</BODY>
</HTML>
