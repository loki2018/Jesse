package com.servlet;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
public class Ceshi {

	  public static void main(String args[])
		{
		  try
		    {
			  	String backString="";
				String xmlString=readFile();
			  	//String xmlString="";
				//String strUrl="http://localhost:8080/Jesse/gbServlet";
				//String strUrl="http://211.137.251.231:8080/Jesse/gbServlet";
			   //String strUrl = "http://112.53.67.188:8088/ytJesse/gbServlet";// 移动gbhttp 接口
			  //String strUrl = "http://111.11.181.170:8080/gsJesse/gbServlet";// 移动gbhttp 接口
				 //String strUrl = "http://120.194.50.242:8080/Jesse/gbServlet";
			//	String strUrl = "http://221.180.145.51:8080/Jesse/gbServlet";
			  //String strUrl = "http://112.53.67.188:8088/ytJesse/gbServlet";
			//String strUrl = "http://218.200.48.230:8080/Jesse/gbServlet";
				//String strUrl = "http://120.194.50.242:8080/Jesse/gbServlet";
		String strUrl = "http://211.142.198.1:8088/gbServlet";
				URL url = new URL(strUrl);
				URLConnection urlConn =null;
				urlConn= url.openConnection();
				if(urlConn==null||"".equals(urlConn)){
					System.out.println("没有连接到远程端口或远程端口服务没有开启！");
				}else{
					System.out.println("------连接到远程服务！------");
				urlConn.setConnectTimeout(3000000); // 连接超时限制30秒
				urlConn.setReadTimeout(30000000); // 读取超时30
				urlConn.setDoOutput(true);
				urlConn.setRequestProperty("Pragma:", "no-cache");
				urlConn.setRequestProperty("Cache-Control", "no-cache");
				urlConn.setRequestProperty("Content-Type", "text/xml");
				OutputStreamWriter opw = new OutputStreamWriter(urlConn.getOutputStream(),"utf-8");
				opw.write(xmlString);
				opw.flush();
				opw.close();			
				System.out.println(xmlString);
				
				// 返回xml
				BufferedReader br1 = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));

				String line = "";
				for (line = br1.readLine(); line != null; line = br1.readLine()) {
//					XmlSerializer xmlns = new XMLSerializer();
//	                xmlns.Add(String.Empty, string.Empty); 
					backString = backString + line;
				}
				
				 System.out.println(backString);
				}
		    }
		    catch(Exception e)
		    {
		      System.out.println("Error:"+e);
		    }
		}
	  private static String readFile() {   
	        StringBuilder sb=new StringBuilder();   
	        try {   
	            BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream("E:/1.txt"),"utf8"));   
	            //这里的raw.file内容就是 上面那个anxml片段，就是读取内容，将请求的xml保存成字符串 进行post发送   
	            
	            String line="";   
	            for(line=br.readLine();line!=null;line=br.readLine()) {   
	                sb.append(line+"\n");   
	            }   
	               
	        } catch (FileNotFoundException e) {   
	            // TODO Auto-generated catch block   
	            e.printStackTrace();   
	        } catch (IOException e) {   
	            // TODO Auto-generated catch block   
	            e.printStackTrace();   
	        }   
	        return sb.toString();   
	    } 

}	
