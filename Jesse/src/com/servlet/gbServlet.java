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

import com.gb.bean.Bizinfo;
import com.gb.bean.Ecinfo;
import com.gb.bean.Log;
import com.gb.bean.Userinfo;
import com.gb.bean.Userstateinfo;
import com.gb.service.BizinfoService;
import com.gb.service.EcinfoService;
import com.gb.service.LogService;
import com.gb.service.UserinfoService;
import com.gb.service.UserstateinfoService;

@SuppressWarnings("serial")
public class gbServlet extends HttpServlet {
	private EcinfoService ecinfoService;
	private Ecinfo ecinfo;
	private List<Ecinfo> listEcinfo;
	
	private BizinfoService bizinfoService;
	private Bizinfo bizinfo;
	private List<Bizinfo> listBizinfo;
	
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
				backXml = ecInfomation(xmlString);
				// 应答
				PrintWriter out = response.getWriter();
				out.println(backXml);
				out.flush();
				out.close();
				
				logAdd(backXml,"si,TransIDO="+TransIDO+"");  //存日志
				writeLog("backxml:"+backXml);

			}else if (activityCode.equals("T4011025")) {
				//EC订购关系
				backXml = ecOrder(xmlString);
				// 应答
				PrintWriter out = response.getWriter();
				out.println(backXml);
				out.flush();
				out.close();
				
				logAdd(backXml,"si,TransIDO="+TransIDO+"");  //存日志
				writeLog("backxml:"+backXml);

			}else if (activityCode.equals("T4101043")) {
				backXml=memberOrderOne(xmlString); //接收的报文
				// 应答
				PrintWriter out = response.getWriter();
				out.println(backXml);
				out.flush();
				out.close();
				System.out.println(backXml);
				logAdd(backXml,"si,TransIDO="+TransIDO+"");  //存日志
			
				//第二步 处理返回处理结果
				backXml="";
				backXml = memberOrder(xmlString);  //成员订购操作后返回xml

				logAdd(backXml,"si,TransIDO="+TransIDO+"");  //存日志
				writeLog("backxml:"+backXml);
				//调用线程发给lbmp
				acceptReply(backXml,TransIDO);
			//	ToLbmpThread tl=new ToLbmpThread();
			//	tl.xmlString=backXml;
			//	tl.transIDO=TransIDO;
			//	tl.start();
				
				

			}else if (activityCode.equals("T4101045")) {
				//成员认证
				backXml = memberCertification(xmlString);
				// 应答
				PrintWriter out = response.getWriter();
				out.println(backXml);
				out.flush();
				out.close();
				writeLog("backxml:"+backXml);
				logAdd(backXml,"si,TransIDO="+TransIDO+"");
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
	
    //集团订购
	public String ecInfomation(String xmlString) {
		String backXml="";
		try {
			StringReader read = new StringReader(xmlString);
			// 创建新的输入源SAX 解析器将使用 InputSource 对象来确定如何读取 XML 输入
			InputSource source = new InputSource(read);
			// / 创建一个新的SAXBuilder
			SAXBuilder sb = new SAXBuilder();
			// 通过输入源构造一个Document
			Document doc = sb.build(source);
			// 取的根元素
			Element InterBOSS = doc.getRootElement();

			// 获得Routing子元素
			String routeType = "";
			String routeValue = "";
			Element routing = InterBOSS.getChild("Routing");
			routeType = routing.getChildText("RouteType");
			routeValue = routing.getChildText("RouteValue");

			// 取出SvcCont接点内容 做为一个新的XML进行处理
			StringReader read1 = new StringReader(InterBOSS
					.getChildText("SvcCont"));
			InputSource source1 = new InputSource(read1);
			SAXBuilder sb1 = new SAXBuilder();
			Document doc1 = sb1.build(source1);
			Element ECInfo = doc1.getRootElement();
			
			// 取得当前时间
			Date currentTime = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			String dateString = dateFormat.format(currentTime);
			
			// 返回码
			String backMa = "00";

			// 存入数据库gbdatabase中的ecinfo表
			
			try
			{
			ecinfo=new Ecinfo();
			ecinfo.setOprCode(ECInfo.getChildText("OprCode"));
			ecinfo.setECID(ECInfo.getChildText("ECID"));
			ecinfo.setProvECID(ECInfo.getChildText("ProvECID"));
			ecinfo.setECName(ECInfo.getChildText("ECName"));
			ecinfo.setECEName(ECInfo.getChildText("ECEName"));
			ecinfo.setOprTime(ECInfo.getChildText("OprTime"));
			ecinfo.setEffTime(ECInfo.getChildText("EffTime"));
			ecinfo.setECTel(ECInfo.getChildText("ECTel"));
			ecinfo.setContractTel(ECInfo.getChildText("ContractTel"));
			ecinfo.setContractName(ECInfo.getChildText("ContractName"));
			ecinfo.setTjtime(dateString);
		 
			listEcinfo=ecinfoService.findEcinfoByEcid(ECInfo.getChildText("ECID"));
			if(listEcinfo.size()>0)
			{
				Ecinfo ecinfo1=new Ecinfo();
				ecinfo1=listEcinfo.get(0);
				ecinfo.setId(ecinfo1.getId());
				ecinfoService.updateEcinfo(ecinfo);
			}
			else
			{
				ecinfoService.saveEcinfo(ecinfo);
			}
			}
			catch (Exception es)
			{
				writeLog(es.getMessage());
			}
			

			backXml = "<?xml version='1.0' encoding='UTF-8'?><InterBOSS><OrigDomain>"
					+ InterBOSS.getChildText("OrigDomain")
					+ "</OrigDomain><HomeDomain>"
					+ InterBOSS.getChildText("HomeDomain")
					+ "</HomeDomain><BIPCode>"
					+ InterBOSS.getChildText("BIPCode")
					+ "</BIPCode><BIPVer>"
					+ InterBOSS.getChildText("BIPVer")
					+ "</BIPVer><ActivityCode>"
					+ InterBOSS.getChildText("ActivityCode")
					+ "</ActivityCode><ActionCode>1</ActionCode><Routing><RouteType>"
					+ routeType
					+ "</RouteType><RouteValue>"
					+ routeValue
					+ "</RouteValue></Routing><ProcID>"
					+ InterBOSS.getChildText("ProcID")
					+ "</ProcID><TransIDO>"
					+ InterBOSS.getChildText("TransIDO")
					+ "</TransIDO><TransIDH>"
					+ InterBOSS.getChildText("TransIDH")
					+ "</TransIDH><ProcessTime>"
					+ InterBOSS.getChildText("ProcessTime")
					+ "</ProcessTime><TestFlag>"
					+ InterBOSS.getChildText("TestFlag")
					+ "</TestFlag><MsgSender>"
					+ InterBOSS.getChildText("MsgSender")
					+ "</MsgSender><MsgReceiver>"
					+ InterBOSS.getChildText("MsgReceiver")
					+ "</MsgReceiver><SvcContVer>"
					+ InterBOSS.getChildText("SvcContVer")
					+ "</SvcContVer><SvcCont><![CDATA[<?xml version='1.0' encoding='UTF-8'?><!DOCTYPE ECInfoRsp><ECInfoRsp><OprTime>"
					+ dateString
					+ "</OprTime><ECID>"
					+ ECInfo.getChildText("ECID")
					+ "</ECID><RspCode>"
					+ backMa
					+ "</RspCode><RspDesc/></ECInfoRsp>]]></SvcCont></InterBOSS>";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return backXml;
	}

	//集团业务
	public String ecOrder(String xmlString) {
		String backXml="";
		try {
			StringReader read = new StringReader(xmlString);
			// 创建新的输入源SAX 解析器将使用 InputSource 对象来确定如何读取 XML 输入
			InputSource source = new InputSource(read);
			// / 创建一个新的SAXBuilder
			SAXBuilder sb = new SAXBuilder();
			// 通过输入源构造一个Document
			Document doc = sb.build(source);
			// 取的根元素
			Element InterBOSS = doc.getRootElement();

			// 获得Routing子元素
			String routeType = "";
			String routeValue = "";
			Element routing = InterBOSS.getChild("Routing");
			routeType = routing.getChildText("RouteType");
			routeValue = routing.getChildText("RouteValue");

			// 取出SvcCont接点内容 做为一个新的XML进行处理
			StringReader read1 = new StringReader(InterBOSS
					.getChildText("SvcCont"));
			InputSource source1 = new InputSource(read1);
			SAXBuilder sb1 = new SAXBuilder();
			Document doc1 = sb1.build(source1);
			Element SyncInfo = doc1.getRootElement();
			
			Element ECBizInfo = SyncInfo.getChild("ECBizInfo");
			
			Element BizInfo = ECBizInfo.getChild("BizInfo");
			// 返回码
			String backMa = "00";
			
			// 取得当前时间
			Date currentTime = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			String dateString = dateFormat.format(currentTime);
			
			// 存入数据库进行处理 jdbc 连接数据库 OprCode 0
			try
			{
			bizinfo=new Bizinfo();
			bizinfo.setECID(ECBizInfo.getChildText("ECID"));
			
			bizinfo.setOprCode(BizInfo.getChildText("OprCode"));
			bizinfo.setOprSeq(BizInfo.getChildText("OprSeq"));
			bizinfo.setOrdID(BizInfo.getChildText("OrdID"));
			bizinfo.setBizName(BizInfo.getChildText("BizName"));
			
			bizinfo.setBizType(BizInfo.getChildText("BizType"));
			bizinfo.setOrgProvID(BizInfo.getChildText("OrgProvID"));
			bizinfo.setHomeCity(BizInfo.getChildText("HomeCity"));
			bizinfo.setSubBizType(BizInfo.getChildText("SubBizType"));
			
			bizinfo.setBizStatus(BizInfo.getChildText("BizStatus"));
			bizinfo.setPayType(BizInfo.getChildText("PayType"));
			bizinfo.setProdOrdMod(BizInfo.getChildText("ProdOrdMod"));
			bizinfo.setOprEffTime(BizInfo.getChildText("OprEffTime"));
			
			bizinfo.setNetType(BizInfo.getChildText("NetType"));
			bizinfo.setAdminAcct(BizInfo.getChildText("AdminAcct"));
			bizinfo.setAdminPasswd(BizInfo.getChildText("AdminPasswd"));
			bizinfo.setAdminMSISDN(BizInfo.getChildText("AdminMSISDN"));
			bizinfo.setLocPlatformType(BizInfo.getChildText("LocPlatformType"));
			bizinfo.setTjtime(dateString);
			
			listBizinfo=bizinfoService.findBizinfoByOrdid(BizInfo.getChildText("OrdID"));
			if(listBizinfo.size()>0)
			{
				Bizinfo bizinfo1=new Bizinfo();
				bizinfo1=listBizinfo.get(0);
				bizinfo.setId(bizinfo1.getId());
				bizinfoService.updateBizinfo(bizinfo);
			}
			else
			{
				bizinfoService.saveBizinfo(bizinfo);
			}
			
			}
			catch(Exception ex)
			{
				writeLog(ex.getMessage());
			}

			
			
			backXml = "<?xml version='1.0' encoding='UTF-8'?><InterBOSS><OrigDomain>"
					+ InterBOSS.getChildText("OrigDomain")
					+ "</OrigDomain><HomeDomain>"
					+ InterBOSS.getChildText("HomeDomain")
					+ "</HomeDomain><BIPCode>"
					+ InterBOSS.getChildText("BIPCode")
					+ "</BIPCode><BIPVer>"
					+ InterBOSS.getChildText("BIPVer")
					+ "</BIPVer><ActivityCode>"
					+ InterBOSS.getChildText("ActivityCode")
					+ "</ActivityCode><ActionCode>1</ActionCode><Routing><RouteType>"
					+ routeType
					+ "</RouteType><RouteValue>"
					+ routeValue
					+ "</RouteValue></Routing><ProcID>"
					+ InterBOSS.getChildText("ProcID")
					+ "</ProcID><TransIDO>"
					+ InterBOSS.getChildText("TransIDO")
					+ "</TransIDO><TransIDH>"
					+ InterBOSS.getChildText("TransIDH")
					+ "</TransIDH><ProcessTime>"
					+ InterBOSS.getChildText("ProcessTime")
					+ "</ProcessTime><TestFlag>"
					+ InterBOSS.getChildText("TestFlag")
					+ "</TestFlag><MsgSender>"
					+ InterBOSS.getChildText("MsgSender")
					+ "</MsgSender><MsgReceiver>"
					+ InterBOSS.getChildText("MsgReceiver")
					+ "</MsgReceiver><SvcContVer>"
					+ InterBOSS.getChildText("SvcContVer")
					+ "</SvcContVer><SvcCont><![CDATA[<?xml version='1.0' encoding='UTF-8'?><!DOCTYPE ECInfoRsp><ECInfoRsp><OprTime>"
					+ dateString
					+ "</OprTime><ECID>"
					+ ECBizInfo.getChildText("ECID")
					+ "</ECID><RspCode>"
					+ backMa
					+ "</RspCode><RspDesc/></ECInfoRsp>]]></SvcCont></InterBOSS>";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return backXml;
	}
    
	//成员订购关系收到
	public String memberOrderOne(String xmlString) {
		String backXml="";
		try {
			
			StringReader read = new StringReader(xmlString);
			// 创建新的输入源SAX 解析器将使用 InputSource 对象来确定如何读取 XML 输入
			InputSource source = new InputSource(read);
			// / 创建一个新的SAXBuilder
			SAXBuilder sb = new SAXBuilder();
			// 通过输入源构造一个Document
			Document doc = sb.build(source);
			// 取的根元素
			Element InterBOSS = doc.getRootElement();

			// 获得Routing子元素
			String routeType = "";
			String routeValue = "";
			Element routing = InterBOSS.getChild("Routing");
			routeType = routing.getChildText("RouteType");
			routeValue = routing.getChildText("RouteValue");
			//fix bug by liujun 2016/12/20
			// 取得当前时间
			Date currentTime = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");			
			String dateString = dateFormat.format(currentTime);
			// 取出SvcCont接点内容 做为一个新的XML进行处理
			StringReader read1 = new StringReader(InterBOSS
					.getChildText("SvcCont"));
			InputSource source1 = new InputSource(read1);
			SAXBuilder sb1 = new SAXBuilder();
			Document doc1 = sb1.build(source1);
			Element ECInfo = doc1.getRootElement();
			// 返回码
			String backMa = "00";
			//fix bug by liujun 2016/12/20
			
			backXml = "<?xml version='1.0' encoding='UTF-8'?><InterBOSS><OrigDomain>"
					+ InterBOSS.getChildText("OrigDomain")
					+ "</OrigDomain><HomeDomain>"
					+ InterBOSS.getChildText("HomeDomain")
					+ "</HomeDomain><BIPCode>"
					+ InterBOSS.getChildText("BIPCode")
					+ "</BIPCode><BIPVer>"
					+ InterBOSS.getChildText("BIPVer")
					+ "</BIPVer><ActivityCode>T4101043</ActivityCode><ActionCode>0</ActionCode><Routing><RouteType>"
					+ routeType                
					+ "</RouteType><RouteValue>"
					+ routeValue
					+ "</RouteValue></Routing><ProcID>"
					+ InterBOSS.getChildText("ProcID")
					+ "</ProcID><TransIDO>"
					+ InterBOSS.getChildText("TransIDO")
					+ "</TransIDO><TransIDH>"
					+ InterBOSS.getChildText("TransIDH")
					+ "</TransIDH><ProcessTime>"
					+ InterBOSS.getChildText("ProcessTime")
					+ "</ProcessTime><TestFlag>"
					+ InterBOSS.getChildText("TestFlag")
					+ "</TestFlag><MsgSender>"
					+ InterBOSS.getChildText("MsgSender")
					+ "</MsgSender><MsgReceiver>"
					+ InterBOSS.getChildText("MsgReceiver")
					+ "</MsgReceiver><SvcContVer>"
					+ InterBOSS.getChildText("SvcContVer")
					//+ "</SvcContVer></SvcCont></InterBOSS>";
					//fix bug by liujun 2016/12/20
					+ "</SvcContVer><SvcCont><![CDATA[<?xml version='1.0' encoding='UTF-8'?><!DOCTYPE ECInfoRsp><ECInfoRsp><OprTime>"
					+ dateString
					+ "</OprTime><ECID>"
					+ ECInfo.getChildText("ECID")
					+ "</ECID><RspCode>"
					+ backMa
					+ "</RspCode><RspDesc/></ECInfoRsp>]]></SvcCont></InterBOSS>";				
					//fix bug by liujun 2016/12/20
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return backXml;
	}
	
	//成员订购关系操作
	@SuppressWarnings("unchecked")
	public String memberOrder(String xmlString) {
		String backXml="";
		try {
			
			StringReader read = new StringReader(xmlString);
			// 创建新的输入源SAX 解析器将使用 InputSource 对象来确定如何读取 XML 输入
			InputSource source = new InputSource(read);
			// / 创建一个新的SAXBuilder
			SAXBuilder sb = new SAXBuilder();
			// 通过输入源构造一个Document
			Document doc = sb.build(source);
			// 取的根元素
			Element InterBOSS = doc.getRootElement();

			// 获得Routing子元素
			String routeType = "";
			String routeValue = "";
			Element routing = InterBOSS.getChild("Routing");
			routeType = routing.getChildText("RouteType");
			routeValue = routing.getChildText("RouteValue");

			// 取出SvcCont接点内容 做为一个新的XML进行处理
			StringReader read1 = new StringReader(InterBOSS
					.getChildText("SvcCont"));
			InputSource source1 = new InputSource(read1);
			SAXBuilder sb1 = new SAXBuilder();
			Document doc1 = sb1.build(source1);
			Element BizInfo = doc1.getRootElement();
			
			Element UserInfo = BizInfo.getChild("UserInfo");
			
			List UserDatas=UserInfo.getChildren("UserData");
			
			// 取得当前时间
			Date currentTime = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			String dateString = dateFormat.format(currentTime);
			try
			{
			for(int i=0;i<UserDatas.size();i++)
			{	
				Element UserData= (Element) UserDatas.get(i);
				userinfo=new Userinfo();
				userinfo.setECID(UserInfo.getChildText("ECID"));
				userinfo.setOrdID(UserInfo.getChildText("OrdID"));
				//userinfo.setProdName(UserInfo.getChildText("ProdName"));
				//System.out.println("ProvECID:"+UserInfo.getChildText("ProvECID"));
				if("".equals(UserInfo.getChildText("ProdName"))||UserInfo.getChildText("ProdName")==null){
					if("03".equals(UserData.getChildText("OprCode"))){
						userinfo.setProdName("Add User");
					}else if("04".equals(UserData.getChildText("OprCode"))){
						userinfo.setProdName("Delete User");
					}	
				}else{
					userinfo.setProdName(UserInfo.getChildText("ProdName"));
				}
				userinfo.setOprSeq(UserInfo.getChildText("OprSeq"));
				userinfo.setMSISDN(UserData.getChildText("MSISDN"));
				userinfo.setOprCode(UserData.getChildText("OprCode"));
				userinfo.setPayECID(UserData.getChildText("PayECID"));
				userinfo.setEfftT(UserData.getChildText("EfftT"));
				userinfo.setOprT(UserData.getChildText("OprT"));
				userinfo.setLocType(UserData.getChildText("LocType"));
				userinfo.setUEType(UserData.getChildText("UEType"));
				userinfo.setTjtime(dateString);
				
				listUserinfo=userinfoService.findBizinfoByEcidAndOrdidAndPhone(UserInfo.getChildText("ECID"), UserInfo.getChildText("OrdID"), UserData.getChildText("MSISDN"));
				if(listUserinfo.size()>0)
				{
					Userinfo userinfo1=new Userinfo();
					userinfo1=listUserinfo.get(0);
					userinfo.setId(userinfo1.getId());
					userinfoService.updateUserinfo(userinfo);
				}
				else
				{
					userinfoService.saveUserinfo(userinfo);
				}
			}
			}
			catch (Exception ex)
			{
				writeLog(ex.getMessage());
				
			}
			// 返回码
			String backMa = "00";

			backXml = "<?xml version='1.0' encoding='UTF-8'?><InterBOSS><OrigDomain>"
					+ InterBOSS.getChildText("OrigDomain")
					+ "</OrigDomain><HomeDomain>"
					+ InterBOSS.getChildText("HomeDomain")
					+ "</HomeDomain><BIPCode>"
					+ InterBOSS.getChildText("BIPCode")
					+ "</BIPCode><BIPVer>"
					+ InterBOSS.getChildText("BIPVer")
					+ "</BIPVer><ActivityCode>T4101044</ActivityCode><ActionCode>"
					+ InterBOSS.getChildText("ActionCode")
					+ "</ActionCode><Routing><RouteType>"
					+ routeType
					+ "</RouteType><RouteValue>"
					+ routeValue
					+ "</RouteValue></Routing><ProcID>"
					+ InterBOSS.getChildText("ProcID")
					+ "</ProcID><TransIDO>"
					+ InterBOSS.getChildText("TransIDO")
					+ "</TransIDO><TransIDH>"
					+ InterBOSS.getChildText("TransIDH")
					+ "</TransIDH><ProcessTime>"
					+ InterBOSS.getChildText("ProcessTime")
					+ "</ProcessTime><TestFlag>"
					+ InterBOSS.getChildText("TestFlag")
					+ "</TestFlag><MsgSender>"
					+ InterBOSS.getChildText("MsgSender")
					+ "</MsgSender><MsgReceiver>"
					+ InterBOSS.getChildText("MsgReceiver")
					+ "</MsgReceiver><SvcContVer>"
					+ InterBOSS.getChildText("SvcContVer")
					+ "</SvcContVer><SvcCont><![CDATA[<?xml version='1.0' encoding='UTF-8'?><!DOCTYPE BizInfoRsp><BizInfoRsp><OprTime>"
					+ dateString
					+ "</OprTime><ECID>"
					+ UserInfo.getChildText("ECID")
					+ "</ECID><OrdID>"
					+ UserInfo.getChildText("OrdID")
					+ "</OrdID><OprSeq>"
					+ UserInfo.getChildText("OprSeq")
					+ "</OprSeq><RspCode>"
					+ backMa
					+ "</RspCode></BizInfoRsp>]]></SvcCont></InterBOSS>";
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return backXml;
	}

	//成员认证
	public String memberCertification(String xmlString) {
		String backXml="";
		try {
			StringReader read = new StringReader(xmlString);
			// 创建新的输入源SAX 解析器将使用 InputSource 对象来确定如何读取 XML 输入
			InputSource source = new InputSource(read);
			// / 创建一个新的SAXBuilder
			SAXBuilder sb = new SAXBuilder();
			// 通过输入源构造一个Document
			Document doc = sb.build(source);
			// 取的根元素
			Element InterBOSS = doc.getRootElement();

			// 获得Routing子元素
			String routeType = "";
			String routeValue = "";
			Element routing = InterBOSS.getChild("Routing");
			routeType = routing.getChildText("RouteType");
			routeValue = routing.getChildText("RouteValue");

			// 取出SvcCont接点内容 做为一个新的XML进行处理
			StringReader read1 = new StringReader(InterBOSS
					.getChildText("SvcCont"));
			InputSource source1 = new InputSource(read1);
			SAXBuilder sb1 = new SAXBuilder();
			Document doc1 = sb1.build(source1);
			Element UserStateInfo = doc1.getRootElement();
			
			Element UserInfo = UserStateInfo.getChild("UserInfo");
			
			Element UserStateData = UserInfo.getChild("UserStateData");
			
			// 取得当前时间
			Date currentTime = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			String dateString = dateFormat.format(currentTime);
			
			// 存入数据库进行处理 jdbc 连接数据库 OprCode 0
			try
			{
			userstateinfo=new Userstateinfo();
			userstateinfo.setOrdID(UserInfo.getChildText("OrdID"));
			
			userstateinfo.setMSISDN(UserStateData.getChildText("MSISDN"));
			userstateinfo.setApprobate(UserStateData.getChildText("Approbate"));
			userstateinfo.setOprT(UserStateData.getChildText("OprT"));
			userstateinfo.setOprSeq(UserInfo.getChildText("OprSeq"));
			userstateinfo.setTjtime(dateString);
			
			listUserstateinfo=userstateinfoService.findBizinfoByPhone(UserStateData.getChildText("MSISDN"));
			if(listUserstateinfo.size()>0)
			{
				Userstateinfo userstateinfo1=new Userstateinfo();
				userstateinfo1=listUserstateinfo.get(0);
				userstateinfo.setId(userstateinfo1.getId());
				userstateinfoService.updateUserstateinfo(userstateinfo);
			}
			else
			{
				userstateinfoService.saveUserstateinfo(userstateinfo);
			}
			
			}
			catch(Exception ex)
			{
				
				writeLog(ex.getMessage());
			}
			

			// 返回码
			String backMa = "00";

			backXml = "<?xml version='1.0' encoding='UTF-8'?><InterBOSS><OrigDomain>"
					+ InterBOSS.getChildText("OrigDomain")
					+ "</OrigDomain><HomeDomain>"
					+ InterBOSS.getChildText("HomeDomain")
					+ "</HomeDomain><BIPCode>"
					+ InterBOSS.getChildText("BIPCode")
					+ "</BIPCode><BIPVer>"
					+ InterBOSS.getChildText("BIPVer")
					+ "</BIPVer><ActivityCode>T4101045</ActivityCode><ActionCode>1</ActionCode><Routing><RouteType>"
					+ routeType
					+ "</RouteType><RouteValue>"
					+ routeValue
					+ "</RouteValue></Routing><ProcID>"
					+ InterBOSS.getChildText("ProcID")
					+ "</ProcID><TransIDO>"
					+ InterBOSS.getChildText("TransIDO")
					+ "</TransIDO><TransIDH>"
					+ InterBOSS.getChildText("TransIDH")
					+ "</TransIDH><ProcessTime>"
					+ InterBOSS.getChildText("ProcessTime")
					+ "</ProcessTime><TestFlag>"
					+ InterBOSS.getChildText("TestFlag")
					+ "</TestFlag><MsgSender>"
					+ InterBOSS.getChildText("MsgSender")
					+ "</MsgSender><MsgReceiver>"
					+ InterBOSS.getChildText("MsgReceiver")
					+ "</MsgReceiver><SvcContVer>"
					+ InterBOSS.getChildText("SvcContVer")
					+ "</SvcContVer><SvcCont><![CDATA[<?xml version='1.0' encoding='UTF-8'?><!DOCTYPE UserStateInfoRsp><UserStateInfoRsp><OprTime>"
					+ dateString
					+ "</OprTime><OrdID>"
					+ UserInfo.getChildText("OrdID")
					+ "</OrdID><RspCode>"
					+ backMa
					+ "</RspCode><OprSeq>"
					+ UserInfo.getChildText("OprSeq")
					+ "</OprSeq></UserStateInfoRsp>]]></SvcCont></InterBOSS>";
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return backXml;
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
		ecinfoService = (EcinfoService) WebApplicationContextUtils
		   .getRequiredWebApplicationContext(getServletContext())
		   .getBean("ecinfoService");
		
		bizinfoService = (BizinfoService) WebApplicationContextUtils
		   .getRequiredWebApplicationContext(getServletContext())
		   .getBean("bizinfoService");
		
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
	
	public EcinfoService getEcinfoService() {
		return ecinfoService;
	}

	public void setEcinfoService(EcinfoService ecinfoService) {
		this.ecinfoService = ecinfoService;
	}

	public Ecinfo getEcinfo() {
		return ecinfo;
	}

	public void setEcinfo(Ecinfo ecinfo) {
		this.ecinfo = ecinfo;
	}

	public BizinfoService getBizinfoService() {
		return bizinfoService;
	}

	public void setBizinfoService(BizinfoService bizinfoService) {
		this.bizinfoService = bizinfoService;
	}

	public Bizinfo getBizinfo() {
		return bizinfo;
	}

	public void setBizinfo(Bizinfo bizinfo) {
		this.bizinfo = bizinfo;
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

	public List<Ecinfo> getListEcinfo() {
		return listEcinfo;
	}

	public void setListEcinfo(List<Ecinfo> listEcinfo) {
		this.listEcinfo = listEcinfo;
	}

	public List<Bizinfo> getListBizinfo() {
		return listBizinfo;
	}

	public void setListBizinfo(List<Bizinfo> listBizinfo) {
		this.listBizinfo = listBizinfo;
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
