package com.service;

import java.sql.ResultSet;

import com.db.Conn;

public class Jesse {
	public Conn conn;
	
	/** lbmp收发接口 */
	public String backService(String phoneString) {
		String serviceId="";
     	try {
			
			String sql = "select * from userinfo where MSISDN='"+ phoneString + "'";
			conn = new Conn();
			ResultSet rs = (ResultSet) conn.getRs(sql);

			if (rs.next()) {
				
				serviceId=rs.getString("OrdID");
			}
			
		} catch (Exception e) {
			System.out.print(e);
		}
		return serviceId;

	}
	
	
}
