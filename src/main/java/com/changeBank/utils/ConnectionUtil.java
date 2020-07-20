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

		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		String url = "jdbc:postgresql://localhost:5432/changeBank";
		String username = "postgres";
		String password = "root";

		return DriverManager.getConnection(url, username, password);

	}
}
