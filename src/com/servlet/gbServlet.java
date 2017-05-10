package com.servlet;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.xml.sax.InputSource;

import com.gb.bean.Log;
import com.gb.bean.Userinfo;
import com.gb.bean.Userstateinfo;

import com.gb.service.LogService;
import com.gb.service.UserinfoService;
import com.gb.service.UserstateinfoService;

@SuppressWarnings("serial")
public class gbServlet extends HttpServlet {

	
	
	private UserinfoService userinfoService;
	private Userinfo userinfo;
	private List<Userinfo> listUserinfo;
	
	private UserstateinfoService userstateinfoService;
	private Userstateinfo userstateinfo;
	private List<Userstateinfo> listUserstateinfo;
	
	private LogService logService;
	private Log log;

	/**
	 * Constructor of the object.
	 */
	public gbServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    This is ");
		out.print(this.getClass());
		out.println(", using the GET method");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void writeLog(String value)
	{
		FileWriter fileWriter=null; 
		try
		{	
		fileWriter = new FileWriter("c:\\xmllog.txt", true);
		Date currentTime = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String dateString = dateFormat.format(currentTime);
		fileWriter.write(dateString+":" +value+"\r\n");			     
		}
		catch(Exception ee)
		{
		}
		finally
		{
			if (fileWriter !=null)
			{
				try
				{
			fileWriter.close();
				}
				catch(Exception es)
				{}
			}
		}
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	//	response.setContentType("text/html; charset=GBK");
        request.setCharacterEncoding("UTF-8");
		try {

			// 取得缓存区的xml流
			// BufferedReader br = request.getReader();
		/*	InputStream in = request.getInputStream();
			ByteArrayOutputStream out1 = new ByteArrayOutputStream(1024);
			byte[] buf = new byte[1024];
			int size = 0;
			while ((size = in.read(buf)) != -1) {
				out1.write(buf, 0, size);
			}
			in.close();
			out1.close();
			 writeLog(out1.toString());	
			String b = out1.toString("UTF-8");
			// 编码转换
			 */
			String b  = request.getParameter("$xmldata");
			String xmlString = URLDecoder.decode(b, "UTF-8");
	        writeLog(xmlString);			
			//String xmlString=b;
			logAdd(xmlString,"收到的原始报文");  //存日志
			
			xmlString = xmlString.replace("$xmldata=<?xml version=\"1.0\"?>","<?xml version=\"1.0\" encoding=\"GB2312\"?>");
			xmlString = xmlString.replace("$xmldata=<?xml version=\"1.0\" encoding=\"UTF-8\"?>","<?xml version=\"1.0\" encoding=\"GB2312\"?>");
			xmlString = xmlString.replace("xmlString=<?xml version=\"1.0\" encoding=\"UTF-8\"?>","<?xml version=\"1.0\" encoding=\"GB2312\"?>");
			xmlString = xmlString.replace("$xmldata=","");
			System.out.print(xmlString);
			logAdd(xmlString,"转义后报文");  //存日志
			StringReader read = new StringReader(xmlString);
			// 创建新的输入源SAX 解析器将使用 InputSource 对象来确定如何读取 XML 输入
			InputSource source = new InputSource(read);
			// / 创建一个新的SAXBuilder
			SAXBuilder sb = new SAXBuilder();
			// 通过输入源构造一个Document
			Document doc = sb.build(source);
			// 取的根元素
			Element InterBOSS = doc.getRootElement();

			// 判断业务类型
			// 1、EC信息同步(交易编码T4011024) 转到方法 ecInfomation()
			// 2、EC订购关系(交易编码T4011025) 转到方法 ecOrder()
			// 3、成员订购LBMP向业务系统同步EC成员订购信息(交易编码T4101043)、业务系统向LBMP同步EC成员订购确认信息(交易编码T4101044)
			// 转到方法 memberOrder()
			// 4、成员认证LBMP向业务系统同步成员认证状态信息(交易编码T4101045)、业务系统向LBMP同步成员认证状态应答信息(交易编码T4101044)
			// 转到方法memberCertification()
			String TransIDO=""; //数据同步流水号
			String activityCode = "";  //存储交易编码
			String backXml = ""; //存储返回lbmp xml字符串
			
			TransIDO= InterBOSS.getChildText("TransIDO"); //取得流水号
			activityCode = InterBOSS.getChildText("ActivityCode");
			
			logAdd(xmlString,"lbmp,TransIDO="+TransIDO+"");  //存日志
			

			
			if (activityCode.equals("T4011024")) {
				// ec信息同步
				
				
				//logAdd(backXml,"si,TransIDO="+TransIDO+"");  //存日志
				//writeLog("backxml:"+backXml);

			}else if (activityCode.equals("T4011025")) {
	 

			}else if (activityCode.equals("T4101043")) {
	 
				
				

			}else if (activityCode.equals("T4101045")) {
		 
				//writeLog("backxml:"+backXml);
				//logAdd(backXml,"si,TransIDO="+TransIDO+"");
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.print(e);
		}

	}
	public void acceptReply(String xmlString ,String transIDO) {
		try {

	        String backString = "";
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
				logAdd("TransIDO=" + transIDO   ,"lbmp 没有返回请求信息");
			} else {
				logAdd("TransIDO=" + transIDO   ,   backString);
				System.out.println("TransIDO=" + transIDO + "," + backString);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logAdd("TransIDO=" + transIDO  ,  e.toString());
		}
	}
	

	 
	public void logAdd(String xmlString,String address)
	{
		// 取得当前时间
		try
		{
		Date currentTime = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String dateString = dateFormat.format(currentTime);
		
		log=new Log();
		log.setAddress(address);
		log.setLog(xmlString);
		log.setTjtime(dateString);
	 	logService.saveLog(log);
		}
		catch(Exception ex)
		{
			writeLog(ex.getMessage());
			
		}
	}
	
	public void init() throws ServletException {
	
		
		
		userinfoService = (UserinfoService) WebApplicationContextUtils
		   .getRequiredWebApplicationContext(getServletContext())
		   .getBean("userinfoService");
		
		userstateinfoService = (UserstateinfoService) WebApplicationContextUtils
		   .getRequiredWebApplicationContext(getServletContext())
		   .getBean("userstateinfoService");
		
		logService = (LogService) WebApplicationContextUtils
		   .getRequiredWebApplicationContext(getServletContext())
		   .getBean("logService");
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



	public List<Userinfo> getListUserinfo() {
		return listUserinfo;
	}

	public void setListUserinfo(List<Userinfo> listUserinfo) {
		this.listUserinfo = listUserinfo;
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

	public List<Userstateinfo> getListUserstateinfo() {
		return listUserstateinfo;
	}

	public void setListUserstateinfo(List<Userstateinfo> listUserstateinfo) {
		this.listUserstateinfo = listUserstateinfo;
	}

	public LogService getLogService() {
		return logService;
	}

	public void setLogService(LogService logService) {
		this.logService = logService;
	}

	public Log getLog() {
		return log;
	}

	public void setLog(Log log) {
		this.log = log;
	}
	
	
	

}
