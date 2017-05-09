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
            <td width="24%"><div align="center"> ECID</div></td>
            <td width="22%"><div align="center">集团名字</div></td>
            <td width="47%"><div align="center">认证状态</div></td>
            <td width="7%"><div align="center">定购时间</div></td>
          </tr>
          <s:iterator value="listEcinfo">
            <tr>
               <td><s:property value="ECID" />              </td>
              
                <td><div align="center">
         
                  <s:property value="ECName" />
                </div></td>
                <s:set name="listId1" value="OprCode" />
              <td><div align="center">
                  <s:if test="'01'==#listId1">定购</s:if>
                  <s:if test="'02'==#listId1">取消定购</s:if>
                </div></td>
              <td><div align="center">
                  <s:property value="tjtime" />
               
                </div></td>
            </tr>
          </s:iterator>
         

        </table>
</BODY>
</HTML>
