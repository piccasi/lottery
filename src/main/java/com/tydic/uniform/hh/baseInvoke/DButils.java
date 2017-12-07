package com.tydic.uniform.hh.baseInvoke;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tydic.uniform.hh.util.PropertiesUtil;

public class DButils {
	private static ResultSet rs;
	private static Statement st;
	private static Connection con;
	
	private static DButils mInstance;
	public static DButils getInstance() {
		if (null == mInstance) {
			mInstance = new DButils();
		}
		return mInstance;
	}

	public List executeSql(String sql) {
		String dburl = "jdbc:oracle:thin:@"
				+ PropertiesUtil.getPropertyValue("weixin_db_host");
		String dbname = PropertiesUtil.getPropertyValue("weixin_db_username");
		String dbpwd = PropertiesUtil.getPropertyValue("weixin_db_password");
//		 dburl = "jdbc:oracle:thin:@10.72.86.237:1521:testdb";
//		 dbname = "wap";
//		 dbpwd = "bssHh520";
		List list = new ArrayList();
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection(dburl, dbname, dbpwd);
			st = con.createStatement();// 创建sql执行对象
			rs = st.executeQuery(sql);// 执行sql语句并返回结果集
			ResultSetMetaData md = rs.getMetaData();
        	int columnCount = md.getColumnCount();
			while (rs.next())// 对结果集进行遍历输出
			{
				Map rowData = new HashMap();
	            for (int i = 1; i <= columnCount; i++) {
	                rowData.put(md.getColumnName(i), rs.getObject(i));
	            }
	            list.add(rowData);
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 关闭相关的对象
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (st != null) {
				try {
					st.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}
}
