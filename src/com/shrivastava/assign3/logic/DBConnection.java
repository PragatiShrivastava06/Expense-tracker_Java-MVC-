/**
 * @author Pragati Shrivastava
 * @version 1.0
 */
/**
 * @author Pragati Shrivastava
 * @version 1.0
 */
package com.shrivastava.assign3.logic;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

	public static Connection getConnection() throws Exception {
		
		// Configure with your own DB name, user name and password
		String url = "jdbc:mysql://localhost:3306/ooad_assign3";
		String user = "root";
		String password = "pragati";
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = null;
		con = DriverManager.getConnection(url, user, password);
		System.out.println("[Database connection] - successfully established!");
		return con;
	}
}
