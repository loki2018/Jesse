package com.gb.action;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gb.bean.Ecinfo;
import com.gb.bean.PageBean;
import com.gb.bean.Userinfo;
import com.gb.bean.Userstateinfo;
import com.gb.service.LogService;
import com.gb.service.UserinfoService;
import com.gb.service.UserstateinfoService;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings( {"serial" })
public class AdminGbAction extends ActionSupport {

	private UserinfoService userinfoService;
	private Userinfo userinfo;
	private LogService logservice;
	private List<Ecinfo> listEcinfo;
	public List<Ecinfo> getListEcinfo() {
		return listEcinfo;
	}

	public void setListEcinfo(List<Ecinfo> listEcinfo) {
		this.listEcinfo = listEcinfo;
	}

	private List<Userinfo> listUserinfo;
	
	
	private UserstateinfoService userstateinfoService;
	private Userstateinfo userstateinfo;
	private List<Userstateinfo> listUserstateinfo; 
	private int page; // 第几页
	private PageBean pageBean; // 包含分布信息的bean
	
	private String userName;
	private String userPass;
	private String loginCode;
	private String backMess;
	private String xmlString;
	private String backString;
	private String userPhone;
	private String ecname;
	private String ecid;
	private String hid ;  
	private String ordid ; 
	private String tjtime;
	private String orpCode;
	private String gb_strUrl;// 本地gbhttp 接口

	public String getGb_strUrl() {
		return gb_strUrl;
	}

	public void setGb_strUrl(String gb_strUrl) {
		this.gb_strUrl = gb_strUrl;
	}

	public String getOrpCode() {
		return orpCode;
	}

	public void setOrpCode(String orpCode) {
		this.orpCode = orpCode;
	}

	public String getTjtime() {
		return tjtime;
	}

	public void setTjtime(String tjtime) {
		this.tjtime = tjtime;
	}

	public String getOrdid() {
		return ordid;
	}

	public void setOrdid(String ordid) {
		this.ordid = ordid;
	}

	public String getHid() {
		return hid;
	}

	public void setHid(String hid) {
		this.hid = hid;
	}

	public String getEcid() {
		return ecid;
	}

	public void setEcid(String ecid) {
		this.ecid = ecid;
	}

	public String getEcname() {
		return ecname;
	}

	public void setEcname(String ecname) {
		this.ecname = ecname;
	}
	 
	public String chk() throws Exception {
		String sessCode="";
		if(ServletActionContext.getRequest().getSession().getAttribute("logincode")==null || ServletActionContext.getRequest().getSession().getAttribute("logincode").equals(""))
		{
			backMess="验证码已过期，请重新登陆";
			return "login";
		}
		else
		{
			sessCode=ServletActionContext.getRequest().getSession().getAttribute("logincode").toString();
			if(sessCode.equals(loginCode))
			{  String back = null;
				if(userName.equals(userPass))
				{
					System.out.println("账号密码:"+userName+","+userPass);
					if(hid.equals("0")){
						this.pageBean = this.userinfoService.queryForPage(15, page);
						page=1;
						
						listUserstateinfo=this.userstateinfoService.findAllUserstateinfos();
						back="loginOk";
					}
					else if(hid.equals("1")){
						
						this.pageBean = this.userinfoService.queryForPagebyEcname(15, page,ecname);
						page=1;
						listUserstateinfo=this.userstateinfoService.findAllUserstateinfos();
						back="loginOk1";
					}
					else if(hid.equals("2")){
//						HttpServletRequest request = ServletActionContext.getRequest();
//						ecid=(String)request.getAttribute("ecid");
						this.pageBean = this.userinfoService.queryForPagebyEcid(15, page,ecid);
						page=1;
						listUserstateinfo=this.userstateinfoService.findAllUserstateinfos();
						back="userinfoOk";
					}
					
					return back;
				}else
				{
					backMess="用户名或密码错误";
					return "login";
				}
			}
			else
			{
				backMess="验证码错误";
				return "login";
			}
		}
		
	}
	public String Adduserinfo() throws Exception {   //手工同步成员
		System.out.println("手工同步成员"+userPhone);
		HttpServletRequest request = ServletActionContext.getRequest();
		Userinfo userinfo = new Userinfo();
		Userstateinfo userstateinfo = new Userstateinfo();
		userinfo.setMSISDN(userPhone);
		userinfo.setECID(ecid);
		userinfo.setOprCode(orpCode);
		userinfo.setOrdID(ordid);
		userinfo.setTjtime(tjtime.substring(0, 14));
		userinfo.setProdName("手工同步新增成员");
		userinfo.setOprSeq(tjtime);
		userinfo.setPayECID(ecid);
		userinfo.setEfftT(tjtime.substring(0, 8));
		userinfo.setOprT(tjtime.substring(0, 14));
		userinfo.setLocType("A");
		userinfo.setUEType("1");
		//Userstateinfo
		userstateinfo.setOrdID(ordid);
		userstateinfo.setMSISDN(userPhone);
		userstateinfo.setApprobate("0");
		userstateinfo.setOprSeq(tjtime);
		userstateinfo.setTjtime(tjtime.substring(0, 14));
		userstateinfo.setOprT(tjtime.substring(0, 14));
		this.pageBean = userinfoService.queryForPagebyPhoneno(15, 1,userPhone);  
		 if (pageBean.getAllRow()>0){	
			 request.setAttribute("state","1"); 
		}else{
			this.userinfoService.AddUserinfo(userinfo);
			this.userstateinfoService.AddUserstateinfo(userstateinfo);	
			request.setAttribute("state","0");
		}
		return "adduserinfo";	
	}
	public String userinfoSelect() throws Exception {   //集团下成员查询
		page=1;
		this.pageBean = userinfoService.queryForPagebyEcid(15, page,ecid);
		
		listUserstateinfo=userstateinfoService.findAllUserstateinfos();
		return "userinfoOk";	
	}
	
	public String userinfoSelectByOrdid() throws Exception {   //集团标识查询
			
			this.pageBean = userinfoService.queryForPagebyOrdid(15, page,ordid);
			page=1;
			listUserstateinfo=userstateinfoService.findAllUserstateinfos();
			return "userinfoOk";	
	}
		
	public String ecnameSelect() throws Exception {   //集团查询
		HttpServletRequest req = ServletActionContext.getRequest();// 放入Request
		this.pageBean = userinfoService.queryForPagebyEcname(15, page,ecname);
		//this.pageBean = userinfoService.queryForPagebyEcid(15, page,ecid);
		page=1;
		listUserstateinfo=userstateinfoService.findAllUserstateinfos();
		return "loginOk1";	
	}
	
	public String phoneSelect() throws Exception {  //电话查询
		
		this.pageBean = userinfoService.queryForPagebyPhoneno(15, page,userPhone);
		page=1;
		listUserstateinfo=userstateinfoService.findAllUserstateinfos();
		return "loginOk";		
	}
	public String checkLoginname() {     //检查电话号时候已经存在
		this.pageBean = userinfoService.queryForPagebyPhoneno(15, 1,userPhone);  
        if (pageBean.getAllRow()>0) {  
            renderText("电话号已存在，请更换!");  
        } else {  
            renderText("恭喜你，这个电话号可用!");  
        }  
        return null;
    }  
      public String renderText(String text) {  
        HttpServletResponse response = ServletActionContext.getResponse();  
        response.setContentType("text/plain;charset=UTF-8");  
        try {  
            response.getWriter().write(text);  
            response.getWriter().flush();  
        } catch (IOException e) {  
            throw new IllegalArgumentException(e); // 把受检异常转换为非受检异常  
        }  
        return null;
    }  
	public String toExl() throws Exception {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		String dateString = formatter.format(currentTime);
		String fileName=dateString+".xls";
		HttpServletResponse response = ServletActionContext.getResponse();
		
		String back=null;
		if("0".equals(hid)){
			listUserinfo=userinfoService.findAllUserinfos();
			back="toExl";
		}
		else if("1".equals(hid)){
			listEcinfo=userinfoService.findEcinfoByEcname();
			back="toExl1";
		}
		else if("2".equals(hid)){
			if(ordid!=null||!("".equals(ordid))){
				listUserinfo= userinfoService.findUserinfoByOrdid(ordid);
			}else
				
			listUserinfo= userinfoService.findUserinfoByEcid(ecid);
			back="toExl";
		}
	
		//response.setContentType("application/vnd.ms-excel;charset=UTF-8");
		//response.setHeader("Content-disposition", "attachment; filename="+ fileName);
		      
		response.setHeader("Content-disposition", "attachment; filename="
				+ new String(fileName.getBytes("GB2312"), "8859_1"));	
		
		//listUserstateinfo=userstateinfoService.findAllUserstateinfos();
		return back;	
	}
	

	public String loginOut() throws Exception {
		
		backMess="退出成功!";
		return "login";

	}
	
	public String backSd() throws Exception {
		backString="";
		String strUrl = "http://211.137.35.35:8087/gbhttp";// 移动gbhttp 接口
		//String strUrl = "http://61.189.53.223:1811/gbhttp"; // neusoft//
		URL url = new URL(strUrl);
		URLConnection urlConn = url.openConnection();
		urlConn.setConnectTimeout(30000); // 连接超时限制30秒
		urlConn.setReadTimeout(30000); // 读取超时30
		urlConn.setDoOutput(true);
		urlConn.setRequestProperty("Pragma:", "no-cache");
		urlConn.setRequestProperty("Cache-Control", "no-cache");
		urlConn.setRequestProperty("Content-Type", "text/xml");
		OutputStreamWriter opw = new OutputStreamWriter(urlConn
				.getOutputStream());
		opw.write(xmlString);
		opw.flush();
		opw.close();

		// 返回xml
		BufferedReader br1 = new BufferedReader(new InputStreamReader(
				urlConn.getInputStream()));

		String line = "";
		for (line = br1.readLine(); line != null; line = br1.readLine()) {
			backString = backString + line;
		}
		if (backString.equals("")) {
			saveLog("手动归档   lbmp 没有返回请求信息");
		} else {
			saveLog("手动归档    " + backString);
		}
		backMess="归档成功!";
		return "backSd";

	}
	
	public String backSd_gb()  {
		backString="";
		try
		{
		//String strUrl = "http://211.137.35.35:8087/gbhttp"; // neusoft//
		URL url = new URL(gb_strUrl);
		URLConnection urlConn = url.openConnection();
		if(urlConn==null||"".equals(urlConn)){
			System.out.println("没有连接到远程端口或远程端口服务没有开启！");
		}else{
			System.out.println("------连接到远程服务！------");
		urlConn.setConnectTimeout(30000); // 连接超时限制30秒
		urlConn.setReadTimeout(30000); // 读取超时30
		urlConn.setDoOutput(true);
		urlConn.setRequestProperty("Pragma:", "no-cache");
		urlConn.setRequestProperty("Cache-Control", "no-cache");
		urlConn.setRequestProperty("Content-Type", "text/xml");
		OutputStreamWriter opw = new OutputStreamWriter(urlConn.getOutputStream(),"gbk");
		opw.write(xmlString);
		opw.flush();
		opw.close();
		System.out.println(xmlString);
		// 返回xml
		BufferedReader br1 = new BufferedReader(new InputStreamReader(
				urlConn.getInputStream()));

		
		String line = "";
		for (line = br1.readLine(); line != null; line = br1.readLine()) {
			backString = backString + line;
		}
		if (backString.equals("")) {
			saveLog("手动归档给GB程序   没有返回请求信息");
		} else {
			saveLog("手动归档给GB程序 " + backString);
		}
		backMess="归档给GB成功!";
		}
		}
		catch (Exception e) {
			e.printStackTrace(); 
		}
		return "backSd";
		
	}
	
	public void saveLog(String backString) {

		try {
		   //String strUrlLog = "http://211.137.251.231:8088/Jesse/logServlet";
	    	String strUrlLog = "http://localhost:8081/Jesse/logServlet";
			URL urlLog = new URL(strUrlLog);
			URLConnection connLog = urlLog.openConnection();
			connLog.setDoOutput(true);
			connLog.setRequestProperty("Pragma:", "no-cache");
			connLog.setRequestProperty("Cache-Control", "no-cache");
			connLog.setRequestProperty("Content-Type", "text/xml");
			OutputStreamWriter outlog = new OutputStreamWriter(connLog
					.getOutputStream());
			outlog.write(backString);
			outlog.flush();
			outlog.close();

			BufferedReader br = new BufferedReader(new InputStreamReader(
					connLog.getInputStream()));

			String line = "";
			String listString = "";
			for (line = br.readLine(); line != null; line = br.readLine()) {
				listString = listString + line;
			}
			//System.out.println(listString);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	
	public UserinfoService getUserinfoService() {
		return userinfoService;
	}

	public void setUserinfoService(UserinfoService userinfoService) {
		this.userinfoService = userinfoService;
	}

	public Userinfo getUserinfo() {
		return userinfo;
	}

	public void setUserinfo(Userinfo userinfo) {
		this.userinfo = userinfo;
	}

	public UserstateinfoService getUserstateinfoService() {
		return userstateinfoService;
	}

	public void setUserstateinfoService(UserstateinfoService userstateinfoService) {
		this.userstateinfoService = userstateinfoService;
	}

	public Userstateinfo getUserstateinfo() {
		return userstateinfo;
	}

	public void setUserstateinfo(Userstateinfo userstateinfo) {
		this.userstateinfo = userstateinfo;
	}
	public LogService getLogservice() {
		return logservice;
	}

	public void setLogservice(LogService logservice) {
		this.logservice = logservice;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public PageBean getPageBean() {
		return pageBean;
	}

	public void setPageBean(PageBean pageBean) {
		this.pageBean = pageBean;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPass() {
		return userPass;
	}

	public void setUserPass(String userPass) {
		this.userPass = userPass;
	}

	public String getLoginCode() {
		return loginCode;
	}

	public void setLoginCode(String loginCode) {
		this.loginCode = loginCode;
	}

	public String getBackMess() {
		return backMess;
	}

	public void setBackMess(String backMess) {
		this.backMess = backMess;
	}

	public List<Userstateinfo> getListUserstateinfo() {
		return listUserstateinfo;
	}

	public void setListUserstateinfo(List<Userstateinfo> listUserstateinfo) {
		this.listUserstateinfo = listUserstateinfo;
	}

	public String getXmlString() {
		return xmlString;
	}

	public void setXmlString(String xmlString) {
		this.xmlString = xmlString;
	}

	public String getBackString() {
		return backString;
	}

	public void setBackString(String backString) {
		this.backString = backString;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public List<Userinfo> getListUserinfo() {
		return listUserinfo;
	}

	public void setListUserinfo(List<Userinfo> listUserinfo) {
		this.listUserinfo = listUserinfo;
	}


}
