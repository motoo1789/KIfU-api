package com.objetdirect.gwt.umlapi.server.yamazaki.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;



public abstract class DatabaseAccesser {

	private Connection connection;
	private HikariDataSource hikari;
	private Connection con;

	private final static String DRIVER_URL="jdbc:mysql://localhost:3306/kifu6?Unicode=true&characterEncoding=UTF8";
	private final static String CHAR_URL = "?Unicode=true&characterEncoding=UTF8";
	private final static String DRIVER_NAME="com.mysql.jdbc.Driver";
	static final String USER = "root";
	static final String PASSWORD = "root";
	static final String DATABASE_NAME = "kifu6";

	static final String url =  "jdbc:mysql://localhost/:3306/";

//	protected Connection createConnection()
//	{
//		try
//		{
//			Class.forName(DRIVER_NAME);
//			Connection con=DriverManager.getConnection(DRIVER_URL,USER,PASSWORD);
//			return con;
//		} catch(ClassNotFoundException e)
//		{
//			System.out.println("Can't Find JDBC Driver.\n");
//		} catch(SQLException e)
//		{
//			System.out.println("Connection Error.\n"+e);
//		}
//		return null;
//	}

	protected HikariConfig createHikariConfig()
	{
		HikariConfig config = new HikariConfig();
		System.out.println("HikariCP");
		config.setDriverClassName("com.mysql.jdbc.Driver");
		config.setJdbcUrl(DRIVER_URL);
		config.setUsername(USER);
		config.setPassword(PASSWORD);

		return config;
	}

	protected Connection createHikariConnection(String database)
	{
		HikariConfig config = new HikariConfig();
		System.out.println("HikariCP");
		config.setDriverClassName("com.mysql.jdbc.Driver");
		config.setJdbcUrl(database);
		config.setUsername(USER);
		config.setPassword(PASSWORD);
		hikari = new HikariDataSource(config);
		try {
			con = hikari.getConnection();
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		return con;
	}

	protected void closeConnection(Connection con){
		try
		{
			con.close();
		}catch(Exception ex){}
	}

	abstract public String getDatabaseElements();

}
