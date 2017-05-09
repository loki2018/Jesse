package com.servlet;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

public class ToLbmpThread extends Thread {
	String xmlString = "";
	String transIDO = "";
	String backString = "";

	public void run() {
		try {
			//Thread.sleep(500);
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
				saveLog("TransIDO=" + transIDO + ",lbmp 没有返回请求信息");
			} else {
				saveLog("TransIDO=" + transIDO + "," + backString);
				System.out.println("TransIDO=" + transIDO + "," + backString);
			}

			// System.out.println(backString);

		} catch (Exception e) {
			e.printStackTrace();
			saveLog("TransIDO=" + transIDO + "," + e.toString());
		}
	}

	public void saveLog(String backString) {

		try {
			//String strUrlLog = "http://211.137.251.231:8088/gbService/logServlet";
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

	public String getTransIDO() {
		return transIDO;
	}

	public void setTransIDO(String transIDO) {
		this.transIDO = transIDO;
	}

}
