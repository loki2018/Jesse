package com.db;
import java.sql.*;
public class Conn{
	private static Connection con;
	private Statement stmt;
	private ResultSet rs;
	private static String drivername="com.mysql.jdbc.Driver";
	//数据库连接URL
	private static String urll="jdbc:mysql://192.168.1.88:3306/Jesse_db";
	//private static String urll="jdbc:mysql://127.0.0.1:3307/gbdatabase";

	//获取数据库的连接
	private static String user="root";
    private static String password="123456";
	//private static String password="hn@sqjz123";
	
	public static synchronized Connection getConn() throws Exception{
	    try{
	       //加载驱动程序
	       Class.forName(drivername);
	       con=DriverManager.getConnection(urll,user,password);
	    	 return con;
	    }catch(SQLException e){
	       System.err.println(e.getMessage());
	       throw e;
	    }
	}
	//获取Statement只能用于查询
	public Statement getStamtread(){
      try{
          con=getConn();
          //创建Statement对象
          stmt=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
          return stmt;
      }catch(Exception e){
          System.err.println(e.getMessage());
	        e.printStackTrace();
      }
      return null;
	}
	//获取ResultSet
	public ResultSet getRs(String sql){
	    try{
	        stmt=getStamtread();
	        //创建ResultSet对象
	        rs=stmt.executeQuery(sql);
	        return rs;
	    }catch(Exception e){
          System.err.println(e.getMessage());
	        e.printStackTrace();
      }
		  return null;
	}
	//获取Statement用于更新,删除,添加的sql语句
  public Statement getStmt(){
      try{
          con=getConn();
          //创建Statement对象
          stmt=con.createStatement();
          return stmt;
      }catch(Exception e){
          System.err.println(e.getMessage());
	        e.printStackTrace();
      }
      return null;
	}
	//关闭数据库连接
	public synchronized void close(){
		  try{
		  	  if(rs!=null){
		  	     rs.close();
		  	     rs=null;
		  	  }
		  }catch(Exception e){
		      System.err.println(e.getMessage());
			    e.printStackTrace();
      }
		  try{
		  	  if(stmt!=null){
		  	     stmt.close();
		  	     stmt=null;
		  	  }
		  }catch(Exception e){
		      System.err.println(e.getMessage());
			    e.printStackTrace();
      }
		  try{
		  	  if(con!=null){
		  	     con.close();
		  	     con=null;
		  	  }
		  }catch(Exception e){
		      System.err.println(e.getMessage());
			    e.printStackTrace();
      }
	}
}

