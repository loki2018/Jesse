package com.servlet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.support.WebApplicationContextUtils;

import com.gb.bean.Log;
import com.gb.service.LogService;

@SuppressWarnings("serial")
public class logServlet extends HttpServlet {
	private LogService logService;
	private Log log;

	/**
	 * Constructor of the object.
	 */
	public logServlet() {
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
		out
				.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
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
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("gbk");
		try {

			InputStream in = request.getInputStream();
			ByteArrayOutputStream out1 = new ByteArrayOutputStream(1024);
			byte[] buf = new byte[1024];
			int size = 0;
			while ((size = in.read(buf)) != -1) {
				out1.write(buf, 0, size);
			}
			in.close();
			out1.close();
			String b = out1.toString();
			// 编码转换
			String xmlString = URLDecoder.decode(b, "utf-8");
			
			//System.out.print(xmlString);

			logAdd(xmlString, "lbmp"); // 存日志

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occurs
	 */
	public void init() throws ServletException {
		logService = (LogService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(getServletContext()).getBean(
						"logService");
	}

	public void logAdd(String xmlString, String address) {
		// 取得当前时间
		Date currentTime = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String dateString = dateFormat.format(currentTime);

		log = new Log();
		log.setAddress(address);
		log.setLog(xmlString);
		log.setTjtime(dateString);
		logService.saveLog(log);
	}

}
