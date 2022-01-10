
package com.objetdirect.gwt.umlapi.server.dao;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;


public class DriverAccessor {

	//private final static String DRIVER_URL="jdbc:mysql://localhost:3306/kifu5?useUnicode=true&characterEncoding=Windows-31J";
	private final static String DRIVER_URL="jdbc:mysql://localhost:3306/kifu6?useUnicode=true&characterEncoding=Windows-31J";

	private final static String DRIVER_NAME="com.mysql.jdbc.Driver";


	//private final static String USER_NAME="takafumi";
	private final static String MAKE_YAMAZAKI_USER_NAME="root";


	//private final static String PASSWORD="";
	//private final static String PASSWORD="mysqlroot";
	//private final static String PASSWORD="takafumi";
	private final static String MAKE_YAMAZAKI_PASSWORD_="root";


	private HikariDataSource hikari;
	private Connection con;



	public Connection createConnection(){
//		HikariConfig config = new HikariConfig();
//		System.out.println("HikariCP");
//		config.setDriverClassName("com.mysql.jdbc.Driver");
//		config.setJdbcUrl(DRIVER_URL);
//		config.setUsername(MAKE_YAMAZAKI_USER_NAME);
//		config.setPassword(MAKE_YAMAZAKI_PASSWORD_);
//		hikari = new HikariDataSource(config);
//		try {
//			con = hikari.getConnection();
//		} catch (SQLException e) {
//			// TODO 自動生成された catch ブロック
//			e.printStackTrace();
//		}
//
//		return con;

		try{
			Class.forName(DRIVER_NAME);
			Connection con=DriverManager.getConnection(DRIVER_URL,MAKE_YAMAZAKI_USER_NAME,MAKE_YAMAZAKI_PASSWORD_);
			return con;
		}catch(ClassNotFoundException e){
			System.out.println("Can't Find JDBC Driver.\n");
		}catch(SQLException e){
			System.out.println("Connection Error.\n");
		}
		return null;
	}


	public void closeConnection(Connection con){
		try{
			con.close();
		}catch(Exception ex){}
	}
}
