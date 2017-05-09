<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"  import="com.gb.bean.*"%>
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
      <form id="form1" name="form1" method="post" action="Gb_phoneSelect.action">
      <div><a style="color: red">注：&nbsp;&nbsp;</a>如果集团查询查不到，请点<a href="./userinfoByOrdId.jsp">这里</a>根据集团标识查询集团下成员！ &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      <a href="./userinfoByOrdId.jsp">集团标识查询</a>&nbsp;&nbsp;&nbsp;&nbsp;
      <a href="./Adduserinfo.jsp">手工同步人员</a>
      <br/></div>
        <table width="100%" border="1">
          <tr>
            <td colspan="4" align="left">成员列表 
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            	成员查询:<input type="text" name="userPhone"/><input type="Submit" name="Submit1" value="查询">
            	集团查询:<input type="text" name="ecname"/>
            	<input type="button" name="btn1" value="查询" onclick="sub()">
             &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input type="button" name="Submit" value="导出到exl" onClick="location.href='Gb_toExl.action?hid=1'"></td>
            <td align="center">
            <label>
              <input type="button" name="Submit" value="退出" onClick="location.href='Gb_loginOut.action'">
            </label></td>
          </tr>
          <tr>
            <td width="24%"><div align="center"> ECID</div></td>
            
            <td width="35%"><div align="center">集团名字</div></td>
            <td width="20%"><div align="center">认证状态</div></td>
            <td width="9%"><div align="center">添加时间</div></td>
            <td width="19%"><div align="center">操作</div>
           
            </td>
          </tr>
          <s:iterator value="pageBean.list">
         
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
             <td><div align="center">
              <a href="<s:url action="Gb_userinfoSelect"><s:param name="ecid" value="ECID"></s:param>
         	</s:url>">
				成员查询</a>  
                </div></td>
            </tr>
           
          </s:iterator>
          
          <tr>
            <td height="25" colspan="5"><div align="center"> 共
                <s:property value="pageBean.allRow" />
                条成员 共
                <s:property value="pageBean.totalPage" />
                页 当前第
                <s:property value="pageBean.currentPage" />
                页
                <s:if test="%{pageBean.currentPage == 1}"> 第一页 上一页</s:if>
                <s:else> <a href="Gb_chk.action?page=1&hid=1">第一页</a> <a
															href="Gb_chk.action?page=<s:property value="%{pageBean.currentPage-1}"/>&hid=1">上一页</a> </s:else>
                <s:if test="%{pageBean.currentPage != pageBean.totalPage}"> <a
															href="Gb_chk.action?page=<s:property value="%{pageBean.currentPage+1}"/>&hid=1">下一页</a> <a
															href="Gb_chk.action?page=<s:property value="pageBean.totalPage"/>&hid=1">最后一页</a> </s:if>
                <s:else>下一页 最后一页</s:else>
                跳到第
                <input type="text" id="text1" size="2">
                页
                <input type="button" value="Go" onClick="cutPage()">
              </div></td>
          </tr>

        </table>
      </form>
    </DIV>
  </DIV>
</DIV>
<DIV id=footer>?2001-2010 版权所有中国移动通信 </DIV>
</BODY>
</HTML>
