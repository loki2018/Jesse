package com.db;

import java.sql.*;

@SuppressWarnings("finally")
public class Data {
	private Conn con = new Conn();
	private Statement stmt;
	private ResultSet rs;

	// 获取查询的行数

	public int getRowCount(String strSql) {
		int intCount = 0;
		System.out.print(strSql);
		try {
			stmt = con.getStamtread();
			rs = stmt.executeQuery("select count(*) from " + strSql);

			if (rs.next()) {
				intCount = rs.getInt(1);
			} else {
				intCount = -1;
			}
		} catch (Exception e) {
			intCount = -2;
			System.err.println(e.getMessage());
			e.printStackTrace();
		} finally {
			con.close();
			return intCount;
		}
	}

	// 插入
	public int insert(String sql) {
		int count = 0;
		System.out.print(sql);
		stmt = con.getStmt();
		try {
			count = stmt.executeUpdate(sql);
		} catch (Exception e) {
			count = -2;
			System.err.println(e.getMessage());
			e.printStackTrace();
		} finally {
			con.close();
			return count;
		}
	}

	// 更新
	public int update(String sql) {
		int count = 0;
		stmt = con.getStmt();
		try {
			count = stmt.executeUpdate(sql);
		} catch (Exception e) {
			count = -2;
			System.err.println(e.getMessage());
			e.printStackTrace();
		} finally {
			con.close();
			return count;
		}
	}

	// 删除
	public int delete(String sql) {
		int count = 0;
		stmt = con.getStmt();
		try {
			count = stmt.executeUpdate(sql);
		} catch (Exception e) {
			count = -2;
			System.err.println(e.getMessage());
			e.printStackTrace();
		} finally {
			con.close();
			return count;
		}
	}
}