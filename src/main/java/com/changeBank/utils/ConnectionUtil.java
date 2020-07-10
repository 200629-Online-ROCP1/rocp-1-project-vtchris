package com.changeBank.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 
 * @author vtchr
 *
 */
public class ConnectionUtil {
	
	// Pass exception down stack
	public static Connection getConnection() throws SQLException {
		
		 String url = "jdbc:postgresql://localhost:5432/changeBank";
		 String username = "postgres";
		 String password = "root";
		 
		 return DriverManager.getConnection(url,username,password);
			
	}

	//test if the connection works
	public static void main(String[] args) {
		try(Connection conn = ConnectionUtil.getConnection()){
			System.out.println("Connection Successful");
		}
		catch(SQLException e) {
			System.out.println(e);
		}		
	}
}
