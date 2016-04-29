/**
 * @author Pragati Shrivastava
 * @version 1.0
 */
/********************************************************************************************************
 * This JDBC file actually talks with database				*
 * 																										*
 * ******************************************************************************************************
 * */
package com.shrivastava.assign3.logic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ScuDbConn {

	public boolean deleteFromDB(String sql) {
		Statement stmt;
		boolean isDeleted;
		try {
			stmt = DBConnection.getConnection().createStatement();
			stmt.execute(sql);
			isDeleted = true;
		} catch (Exception e) {
			isDeleted = false;
			System.out.println("Error at deleteFromDB() in ScuDbConn.java");
			e.printStackTrace();
		}
		return isDeleted;
	}

	public boolean insertIntoDB(String sql) {
		Statement stmt;
		boolean isInsert;
		try {
			stmt = DBConnection.getConnection().createStatement();
			stmt.execute(sql);
			isInsert = true;
		} catch (Exception e) {
			isInsert = false;
			System.out.println("Error at insertIntoDB() in ScuDbConn.java ");
			e.printStackTrace();
		}
		return isInsert;
	}

	public String getValueFromSql(String sql) {
		String output = null;
		Statement stmt;
		try {
			stmt = DBConnection.getConnection().createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			if (rs != null) {
				while (rs.next()) {
					output = rs.getString(1);
					if (output == null || "".equals(output) || "NULL".equalsIgnoreCase(output)) {
						output = "";
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return output;
	}

	public String GetFromDB(String sql) {
		String output = null;
		Statement stmt = null;
		try {
			stmt = DBConnection.getConnection().createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				output = rs.getString(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return output;
	}

	public boolean RemoveFromDB(String sql) {
		Statement stmt;
		boolean isRemoved;
		try {
			stmt = DBConnection.getConnection().createStatement();
			stmt.execute(sql);
			isRemoved = true;
		} catch (Exception e) {
			isRemoved = false;
			System.out.println("Error at insertIntoDB() in ScuDbConn.java ");
			e.printStackTrace();
			// e.printStackTrace();
		}
		return isRemoved;
	}

	public Map<String, String> getKeyValueFromSql(String sqlavailability) {

		Map<String, String> output = new HashMap<String, String>();
		Statement stmt;
		try {
			stmt = DBConnection.getConnection().createStatement();
			ResultSet rs = stmt.executeQuery(sqlavailability);
			while (rs.next()) {
				output.put(rs.getString(1), rs.getString(2));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return output;

	}

	public ResultSet getResultSet(String sql) {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		String url = "jdbc:mysql://localhost:3306/Assign3_db";
		String user = "admin";
		String password = "admin123";

		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(url, user, password);
			st = con.createStatement();
			rs = st.executeQuery(sql);
		} catch (Exception ex) {
			System.out.println(ex);
		} finally {
			try {
				if (st != null) {
					st.close();
				}
				if (con != null) {
					con.close();
				}

			} catch (SQLException ex) {
				System.out.println(ex);
			}
		}
		return rs;
	}

	/*
	 * public ResultSet getValueFromSql_ResultSet(String sql) {
	 * 
	 * ResultSet output = null; Statement stmt; try { stmt =
	 * DBConnection.getConnection().createStatement(); ResultSet rs =
	 * stmt.executeQuery(sql); while (rs.next()) { output = rs;
	 * System.out.println(" output " + output); } } catch (Exception e) {
	 * e.printStackTrace(); }
	 * 
	 * return output; }
	 */

	/*
	 * public List<String> getValueFromSql_ResultSet(String sql) {
	 * ArrayList<String> outList = new ArrayList<String>(); Statement stmt; try
	 * { stmt = DBConnection.getConnection().createStatement(); ResultSet rs =
	 * stmt.executeQuery(sql); while (rs.next()) { outList.add(rs.getString(1));
	 * System.out.println(" outList - 1 " + outList);
	 * outList.add(rs.getString(2)); System.out.println(" outList - 2 " +
	 * outList); } } catch (Exception e) { e.printStackTrace(); } return
	 * outList; }
	 */
	public ArrayList<ResultTransport> getExpenseTrackerForSearch(String sql) {
		ArrayList<ResultTransport> outList = new ArrayList<ResultTransport>();
		Statement stmt;
		try {
			stmt = DBConnection.getConnection().createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				ResultTransport temp = new ResultTransport();
				temp.setCategory(rs.getString("CATEGORY"));
				temp.setSum(rs.getInt("SUM(EXP_VALUE)"));
				outList.add(temp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return outList;
	}
}