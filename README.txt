Name: Pragati Shrivastava
Course: OOAD (COEN 275)
Assignment3

Expense Tracker - 
- Allows the user to store a value for a limit on the amount of money the user wants to spend in a day or duration. 
- Application allows user to visualize the expenses per category. 
- All values are persistent and can be fetched on application re-launch.

Prerequisites
1. JDK version 1.7
2. Create MySQL DBname - "ooad_Assign3".
	- CREATE DATABASE ooad_Assign3

4. Create two tables
	- CREATE TABLE EXPENSETRACKER (Category VARCHAR(255) NOT NULL, Date date NOT NULL, Exp_Value DOUBLE(10,2) NOT NULL);
	- CREATE TABLE TOTALEXPENSELIMIT (StartDate date NOT NULL, EndDate date NOT NULL, Exp_Limit DOUBLE(10,2) NOT NULL);

Step#4 - Update "DBConnection.java" with MySQL user name and password

Execution steps - for reference please see "#5_CheckExpenseVisualization.png"

1. Select Start and End Date of Expense Tracker. 
2. Default value is set for duration, if desired it can be changed to "Day" (see Note below).
3. Enter Expense Limit and click Save Limit.
4. Select Expense Category, enter date, amount and click Save Expense.
5. Set start, end date and click Check Expenses - this will show graph to visualize expenses across different categories against set limit.
6. Values are persistent - so re-launching application will load some of the required values, others can be checked via Check Expenses.

Note - "Clear Limit/Expenses" Or Toggle between Duration and Day will reset Expense Tracker.