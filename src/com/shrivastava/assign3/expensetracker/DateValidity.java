/**
 * @author Pragati Shrivastava
 * @version 1.0
 *//********************************************************************************************************
 * This file checks validity of a picked date i.e.														*
 *  - End date is after start date																		*
 *  - Expense date is between date selected for Expense tracker											*
 *  - Expense statistics date(s) is between valid range													*
 * 																										*
 * ******************************************************************************************************
 * */

package com.shrivastava.assign3.expensetracker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;

class DateValidity {
	private Date chkExpStartDate;

	String datePicker(Date start, Date end, String chkExp_pickStartDate) throws ParseException {

		chkExpStartDate = new SimpleDateFormat("yyyy-MM-dd").parse(chkExp_pickStartDate);
		if ((start.compareTo(chkExpStartDate) <= 0) && (end.compareTo(chkExpStartDate) >= 0)) {
			System.out.println("Date enetered is valid");
			return chkExp_pickStartDate;
		} else {
			System.out.println("Date enetered is NOT valid");
			JOptionPane.showMessageDialog(null, "Invalid date, please select date between start and end date", "Note",
					JOptionPane.INFORMATION_MESSAGE);
			return "";
		}
	}
}
