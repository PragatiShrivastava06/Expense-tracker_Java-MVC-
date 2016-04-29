/**
 * @author Pragati Shrivastava
 * @version 1.0
 */
/********************************************************************************************************
 * SQL queries for methods in BackendLogic.java					*
 * 																										*
 * ******************************************************************************************************
 * */

package com.shrivastava.assign3.logic;

public class SelectQueries {

	public String createSqlForAddExpense(String category, String date, Double exp_value) {
		String sql = "INSERT INTO EXPENSETRACKER (Category, Date, Exp_Value) VALUES ('" + category + "','" + date
				+ "','" + exp_value + "');";
		System.out.println(sql);
		return sql;
	}

	public String createSqlForSetLimit(String pickedStartDate, String pickedEndDate, Double saveLimit) {
		String sql = "INSERT INTO totalexpenselimit (StartDate, EndDate, Exp_Limit) VALUES ('" + pickedStartDate + "','"
				+ pickedEndDate + "','" + saveLimit + "');";
		System.out.println(sql);
		return sql;
	}

	public String createSqlForClearLimit_expensetracker() {
		String sql = "TRUNCATE expensetracker";
		System.out.println(sql);
		return sql;
	}

	public String createSqlForClearLimit_totalexpenselimit() {
		String sql = "TRUNCATE totalexpenselimit";
		System.out.println(sql);
		return sql;
	}

	public String createSqlForGetLimit(String pickedStartDate, String pickedEndDate) {
		String sql = "SELECT Exp_Limit FROM totalexpenselimit WHERE StartDate = '" + pickedStartDate
				+ "' AND EndDate = '" + pickedEndDate + "';";
		System.out.println(sql);
		return sql;
	}

	public String createSqlForGetLimit_previous(String pickedStartDate, String pickedEndDate) {
		String sql = "SELECT Exp_Limit FROM totalexpenselimit WHERE StartDate = '" + pickedStartDate
				+ "' AND EndDate = '" + pickedEndDate + "';";
		System.out.println(sql);
		return sql;
	}

	public String createSqlForInitialStartDate() {
		String sql = "SELECT StartDate FROM totalexpenselimit";
		System.out.println(sql);
		return sql;
	}

	public String createSqlForInitialEndDate() {
		String sql = "SELECT EndDate FROM totalexpenselimit";
		System.out.println(sql);
		return sql;
	}

	public String createSqlForInitialExpLimit() {
		String sql = "SELECT Exp_Limit FROM totalexpenselimit";
		System.out.println(sql);
		return sql;
	}

	public String createSqlForGetExpense(String category, String start, String end) {
		String sql = "SELECT SUM(EXP_VALUE) FROM EXPENSETRACKER Where CATEGORY= '" + category + "'AND DATE BETWEEN'"
				+ start + "'AND'" + end + " ';";
		System.out.println(sql);
		return sql;
	}

	// This is called to Expenses between selected Dates
	public String createSqlForGetExpense_AllCategory(String start, String end) {
		String sql = "SELECT SUM(EXP_VALUE) FROM EXPENSETRACKER Where DATE BETWEEN'" + start + "'AND'" + end + " ';";
		System.out.println(sql);
		return sql;
	}

	// This is called to get Categories between selected Dates
	public String createSqlForGetCategory_betweenDates(String startDate, String endDate) {
		String sql = "SELECT CATEGORY, SUM(EXP_VALUE) FROM EXPENSETRACKER Where DATE BETWEEN '" + startDate + "'AND'"
				+ endDate + "'GROUP BY CATEGORY;";
		System.out.println(sql);
		return sql;
	}
}
