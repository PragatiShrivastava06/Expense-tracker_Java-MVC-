/**
 * @author Pragati Shrivastava
 * @version 1.0
 */
package com.shrivastava.assign3.logic;

import java.util.ArrayList;

public class BackendLogic {

	ScuDbConn dbConnection = new ScuDbConn();
	SelectQueries query = new SelectQueries();

	//Query to insert Expense for a category
	public boolean insertExpense(String category, String date, Double exp_value) {
		String sql = query.createSqlForAddExpense(category, date, exp_value);
		boolean isAdded = dbConnection.insertIntoDB(sql);
		return isAdded;
	}

	//Query to get sum of Expenses - individual and All categories
	public String getExpense(String category, String startDate, String endDate) {
		String sql;
		if (category == "All") {
			sql = query.createSqlForGetExpense_AllCategory(startDate, endDate);
		} else
			sql = query.createSqlForGetExpense(category, startDate, endDate);
		String isAdded = dbConnection.getValueFromSql(sql);
		return isAdded;
	}

	// Query to get categories between selected Dates
	public ArrayList<ResultTransport> getCategory(String startDate, String endDate) {
		String sql = query.createSqlForGetCategory_betweenDates(startDate, endDate);
		ArrayList<ResultTransport> isAdded = dbConnection.getExpenseTrackerForSearch(sql);
		return isAdded;
	}
	
	//Query to Save Limit and Expense Tracker Dates
	public boolean setLimit_SaveDate(String pickedStartDate, String pickedEndDate, Double saveLimit) {
		String sql = query.createSqlForSetLimit(pickedStartDate, pickedEndDate, saveLimit);
		boolean isAdded = dbConnection.insertIntoDB(sql);
		return isAdded;
	}
	
	//Query to clear Expense Limit, also gets called wehn user Reset Expense Limit
	public boolean clearLimit() {
		String sql_1 = query.createSqlForClearLimit_totalexpenselimit();
		String sql_2 = query.createSqlForClearLimit_expensetracker();
		boolean isCleared_1 = dbConnection.RemoveFromDB(sql_1);
		boolean isCleared_2 = dbConnection.RemoveFromDB(sql_2);

		if (isCleared_1 && isCleared_2) {
			return true;
		} else
			return false;
	}
	
	//Query to Get already set Limit
	public String getLimit(String pickedStartDate, String pickedEndDate) {
		String sql = query.createSqlForGetLimit(pickedStartDate, pickedEndDate);
		String isAdded = dbConnection.GetFromDB(sql);
		return isAdded;
	}

	// Query to get Limit if already set - called Expense tracker application is launched again
	public String getLimit_previous(String pickedStartDate, String pickedEndDate) {
		String sql = query.createSqlForGetLimit_previous(pickedStartDate, pickedEndDate);
		String isAdded = dbConnection.GetFromDB(sql);
		return isAdded;
	}
	
	//Query to get Start Date of Expense Tracker
	public String getInitialStartDate() {
		String sql = query.createSqlForInitialStartDate();
		String isAdded = dbConnection.GetFromDB(sql);
		return isAdded;
	}

	//Query to get End Date of Expense Tracker
	public String getInitialEndDate() {
		String sql = query.createSqlForInitialEndDate();
		String isAdded = dbConnection.GetFromDB(sql);
		return isAdded;
	}
}
