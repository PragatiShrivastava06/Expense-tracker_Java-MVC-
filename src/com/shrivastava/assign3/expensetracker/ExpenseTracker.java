/**
 * @author Pragati Shrivastava
 * @version 1.0
 */
/********************************************************************************************************
 * This is Left Panel of Expense tracker, left panel itself is divided into five sub-Panels				*
 * Panel-1 - Sets Expense Duration																		*
 * Panel-2 - Set Expense Limit																			*
 * Panel-3 - Allow user to enter expense per category for a given date									*
 * Panel-4 - Gives Expense check for item(s) for a day or period.									*
 * Panel-5 - Output screen for all the activities														*
 * 																										*
 * ******************************************************************************************************
 * 																										*
 * ******************************************************************************************************
 * ******/
package com.shrivastava.assign3.expensetracker;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.ItemSelectable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Observable;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import com.shrivastava.assign3.logic.BackendLogic;
import com.shrivastava.assign3.logic.DBConnection;

class ExpenseTracker extends Observable implements ActionListener {
	private JLabel labelTitle, labelSpan, labelStart, labelEnd, labelLimit,
			labelDate, labelCategory1, labelSpend, comboLabelCategory_panel4,
			labelStartDate_panel4, labelEndDate_panel4, labelSelectSpan;
	private JTextField textStart, textEnd, calendartextStartDate_panel4,
			calendartextEndDate_panel4, textExpLimit, textExpDate,
			textAmtSpend;
	private JButton btnSaveLimit, btnCalendar1, btnCalendar2, btnCalendar3,
			calendarStartDate_panel4, calendarEndDate_panel4, btnSaveExpenses,
			btnCheckExpenses_panel4, btnClearLimit;
	private JTextArea textDisplayOutput;
	private JComboBox comboSpan_panel1, comboExpCategory_panel3,
			comboCategory_panel4, comboCheckExpenses;
	private JPanel leftPanel, panel, panel1, panel2, panel3, panel4, panel5;
	private String pickedStartDate, pickedEndDate, timeSpanValue,
			timeSpanChkExpValue, expenseSelectDate, chkExp_pickStartDate,
			chkExp_pickEndDate, initialStartDate, initialEndDate, limit, timeSpanSelected;
	private String categoryType2[], individualExpense[];
	private Date start, end, selectedDate;
	private Double saveLimit, amtSpend;
	private ArrayList<String> myArray;
	BackendLogic sql_connect = new BackendLogic();
	DBConnection temp = new DBConnection();
	DateValidity date = new DateValidity();

	// LeftPanel of Expense Tracker
	public JPanel createLeftPanel() {

		// Main panel
		leftPanel = new JPanel();
		leftPanel.setBackground(Color.ORANGE);
		leftPanel.setPreferredSize(new Dimension(500, 400));
		leftPanel.setLayout(new FlowLayout());

		// Panel for title "EXPENSE TRACKER"
		panel = new JPanel();
		panel.setBackground(Color.ORANGE);
		panel.setPreferredSize(new Dimension(500, 60));
		leftPanel.add(panel);
		panel.setLayout(null);

		labelTitle = new JLabel("EXPENSE TRACKER");
		labelTitle.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
		labelTitle.setForeground(new Color(102, 0, 102));
		labelTitle.setBackground(new Color(0, 204, 153));
		labelTitle.setBounds(100, 15, 300, 30);
		// End of Left Main Panel

		// Sub Panel-1 for setting Expense Duration
		panel1 = new JPanel();
		panel1.setBackground(Color.ORANGE);
		panel1.setBorder(new TitledBorder(new EtchedBorder(),
				"Set Expense Duration"));
		panel1.setPreferredSize(new Dimension(500, 100));

		labelSpan = new JLabel("Time Span");
		labelSpan.setFont(new Font("Arial", Font.BOLD, 16));
		labelSpan.setForeground(new Color(0, 51, 255));
		labelSpan.setBackground(new Color(0, 204, 153));
		labelSpan.setBounds(8, 26, 120, 22);

		labelStart = new JLabel("Select Start Date");
		labelStart.setFont(new Font("Arial", Font.BOLD, 16));
		labelStart.setForeground(new Color(0, 51, 255));
		labelStart.setBackground(new Color(0, 204, 153));
		labelStart.setBounds(120, 26, 130, 22);

		btnCalendar1 = new JButton("Calendar");
		btnCalendar1.setBounds(258, 26, 100, 24);
		btnCalendar1.setForeground(new Color(50, 0, 200));
		btnCalendar1.setFont(new Font("Arial", Font.BOLD, 14));

		btnCalendar2 = new JButton("Calendar");
		btnCalendar2.setBounds(258, 70, 100, 24);
		btnCalendar2.setForeground(new Color(50, 0, 200));
		btnCalendar2.setFont(new Font("Arial", Font.BOLD, 14));

		// Text field for Start Date of Expense Duration
		textStart = new JTextField();
		textStart.setBounds(386, 26, 100, 22);
		textStart.setColumns(10);
		textStart.setEditable(false);

		String[] span = { "Duration", "Day" };
		comboSpan_panel1 = new JComboBox(span);
		comboSpan_panel1.setForeground(Color.BLUE);
		comboSpan_panel1.setBackground(Color.GREEN);
		comboSpan_panel1.setFont(new Font("Arial", Font.BOLD, 10));
		comboSpan_panel1.setBounds(8, 50, 100, 36);

		// Reset sub Panel-1 when user toggle between Day and Duration
		comboSpan_panel1.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent itemEvent) {
				int state = itemEvent.getStateChange();
				if (state == ItemEvent.SELECTED) {
					ItemSelectable is = itemEvent.getItemSelectable();
					Object selected[] = is.getSelectedObjects();
					if (selected.length != 0) {
						timeSpanSelected = (String) comboSpan_panel1
								.getSelectedItem();
						System.out.println("selected " + timeSpanSelected);
						if (timeSpanSelected == "Day") {
							comboCheckExpenses.setSelectedItem(comboSpan_panel1
									.getSelectedItem());
						}
						if (timeSpanSelected == "Duration") {
							comboCheckExpenses.setSelectedItem(comboSpan_panel1
									.getSelectedItem());
						}
						textStart.setText("");
						textEnd.setText("");
						textExpLimit.setText("");
						textExpDate.setText("");
						textAmtSpend.setText("");
						calendartextStartDate_panel4.setText("");
						calendartextEndDate_panel4.setText("");
						pickedStartDate = "";
						pickedEndDate = "";
						sql_connect.clearLimit();
						
						//Clear Visualization screen - Notify Observable events to Observer(Visualization panel)
						myArray = new ArrayList<String>();
						setChanged();
						notifyObservers(new ArrayList((myArray)));
						
						textDisplayOutput
								.setText("[Time Span] - changed, set new dates to begin");
					}
				}
			}
		});

		labelEnd = new JLabel("Select End Date");
		labelEnd.setFont(new Font("Arial", Font.BOLD, 16));
		labelEnd.setForeground(new Color(0, 51, 255));
		labelEnd.setBackground(new Color(0, 204, 153));
		labelEnd.setBounds(120, 70, 130, 22);

		textEnd = new JTextField();
		textEnd.setBounds(386, 70, 100, 22);
		textEnd.setColumns(10);
		textEnd.setEditable(false);

		// sub Panel-1 Action Listeners for button click events
		btnCalendar1.addActionListener(this);
		btnCalendar2.addActionListener(this);

		leftPanel.add(panel1);
		panel.add(labelTitle);
		panel1.add(labelSpan);
		panel1.add(labelStart);
		panel1.add(btnCalendar1);
		panel1.add(btnCalendar2);
		panel1.add(labelEnd);
		panel1.add(textStart);
		panel1.add(comboSpan_panel1);
		panel1.add(textEnd);
		panel1.setLayout(null);
		// Sub Panel-1 end here

		// Sub Panel-2 starts
		panel2 = new JPanel();
		panel2.setBackground(Color.ORANGE);
		panel2.setBorder(new TitledBorder(new EtchedBorder(),
				"Set Expense Limit"));
		panel2.setPreferredSize(new Dimension(500, 50));

		// Label Limit
		labelLimit = new JLabel("Expense Limit");
		labelLimit.setFont(new Font("Arial", Font.BOLD, 16));
		labelLimit.setForeground(new Color(0, 51, 255));
		labelLimit.setBackground(new Color(0, 204, 153));
		// labelLimit.setBounds(8, 22, 120, 22);
		labelLimit.setBounds(8, 22, 110, 22);

		// Text Field for setting limit
		textExpLimit = new JTextField();
		textExpLimit.setBounds(130, 16, 90, 30);
		textExpLimit.setColumns(10);

		btnSaveLimit = new JButton("Save Limit");
		// btnSaveLimit.setBounds(258, 14, 110, 32);
		btnSaveLimit.setBounds(230, 14, 110, 32);
		btnSaveLimit.setForeground(new Color(50, 0, 200));
		btnSaveLimit.setFont(new Font("Arial", Font.BOLD, 14));

		btnClearLimit = new JButton("Clear Limit / Expenses");
		btnClearLimit.setBounds(350, 14, 140, 32);
		btnClearLimit.setForeground(new Color(50, 0, 200));
		btnClearLimit.setFont(new Font("Arial", Font.LAYOUT_LEFT_TO_RIGHT, 10));

		// Action Listeners for sub panel-2 button events
		btnSaveLimit.addActionListener(this);
		btnClearLimit.addActionListener(this);

		panel2.setLayout(null);
		leftPanel.add(panel2);
		panel2.add(labelLimit);
		panel2.add(textExpLimit);
		panel2.add(btnSaveLimit);
		panel2.add(btnClearLimit);
		panel1.setLayout(null);
		// Sub Panel-2 ends

		// Sub Panel-3 starts
		panel3 = new JPanel();
		panel3.setBackground(Color.ORANGE);
		panel3.setBorder(new TitledBorder(new EtchedBorder(), "Store Expenses"));
		panel3.setPreferredSize(new Dimension(500, 100));

		labelCategory1 = new JLabel("Category");
		labelCategory1.setFont(new Font("Arial", Font.BOLD, 16));
		labelCategory1.setForeground(new Color(0, 51, 255));
		labelCategory1.setBackground(new Color(0, 204, 153));
		labelCategory1.setBounds(18, 18, 90, 28);

		labelDate = new JLabel("Select Date");
		labelDate.setFont(new Font("Arial", Font.BOLD, 16));
		labelDate.setForeground(new Color(0, 51, 255));
		labelDate.setBackground(new Color(0, 204, 153));
		labelDate.setBounds(136, 18, 100, 28);

		btnCalendar3 = new JButton("Calendar");
		btnCalendar3.setBounds(246, 18, 100, 28);
		btnCalendar3.setForeground(new Color(46, 0, 200));
		btnCalendar3.setFont(new Font("Arial", Font.BOLD, 14));

		textExpDate = new JTextField();
		textExpDate.setBounds(386, 18, 100, 28);
		textExpDate.setEditable(false);

		// Options to select between categories in sub-panel 3
		String[] categoryType1 = { "Grocery", "Bills", "Travel", "Fun", "Other" };
		comboExpCategory_panel3 = new JComboBox(categoryType1);
		comboExpCategory_panel3.setForeground(Color.BLUE);
		comboExpCategory_panel3.setBackground(Color.GREEN);
		comboExpCategory_panel3.setFont(new Font("Arial", Font.BOLD, 10));
		comboExpCategory_panel3.setBounds(8, 45, 96, 45);

		labelSpend = new JLabel("Amount Spend");
		labelSpend.setFont(new Font("Arial", Font.BOLD, 16));
		labelSpend.setForeground(new Color(0, 51, 255));
		labelSpend.setBackground(new Color(0, 204, 153));
		labelSpend.setBounds(115, 60, 116, 22);

		textAmtSpend = new JTextField();
		// textAmtSpend.setBounds(254, 60, 96, 30);
		textAmtSpend.setBounds(240, 60, 96, 30);
		textAmtSpend.setColumns(10);

		// Clear text fields on changing Store Expense category
		comboExpCategory_panel3.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent itemEvent) {
				int state = itemEvent.getStateChange();
				if (state == ItemEvent.SELECTED) {
					ItemSelectable is = itemEvent.getItemSelectable();
					Object selected[] = is.getSelectedObjects();
					if (selected.length != 0) {
						textAmtSpend.setText("");
						textExpDate.setText("");
					}
				}
			}
		});

		btnSaveExpenses = new JButton("Save Expense");
		btnSaveExpenses.setBounds(360, 60, 130, 28);
		btnSaveExpenses.setForeground(new Color(50, 0, 200));
		btnSaveExpenses
				.setFont(new Font("Arial", Font.LAYOUT_LEFT_TO_RIGHT, 14));

		// Panel-3 Action Listeners
		comboExpCategory_panel3.addActionListener(this);
		btnSaveExpenses.addActionListener(this);
		btnCalendar3.addActionListener(this);

		leftPanel.add(panel3);
		panel3.setLayout(null);
		panel3.add(labelCategory1);
		panel3.add(comboExpCategory_panel3);
		panel3.add(labelDate);
		panel3.add(btnCalendar3);
		panel3.add(labelSpend);
		panel3.add(textAmtSpend);
		panel3.add(textExpDate);
		panel3.add(btnSaveExpenses);
		panel3.setLayout(null);
		// sub-Panel-3 ends

		// sub-Panel-4 starts
		panel4 = new JPanel();
		panel4.setBackground(Color.ORANGE);
		panel4.setBorder(new TitledBorder(new EtchedBorder(),
				"Expense Statistics"));
		panel4.setPreferredSize(new Dimension(500, 140));

		labelSelectSpan = new JLabel("Select Time Span");
		labelSelectSpan.setFont(new Font("Arial", Font.BOLD, 16));
		labelSelectSpan.setForeground(new Color(0, 51, 255));
		labelSelectSpan.setBackground(new Color(0, 204, 153));
		labelSelectSpan.setBounds(8, 18, 180, 22);

		String[] selectSpan = { "Duration", "Day" };
		comboCheckExpenses = new JComboBox(selectSpan);
		comboCheckExpenses.setForeground(Color.BLUE);
		comboCheckExpenses.setBackground(Color.GREEN);
		comboCheckExpenses.setFont(new Font("Arial", Font.BOLD, 10));
		comboCheckExpenses.setBounds(8, 36, 130, 36);

		comboCheckExpenses.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent itemEvent) {
				int state = itemEvent.getStateChange();
				if (state == ItemEvent.SELECTED) {
					ItemSelectable is = itemEvent.getItemSelectable();
					Object selected[] = is.getSelectedObjects();
					if (selected.length != 0) {
						calendartextStartDate_panel4.setText("");
						calendartextEndDate_panel4.setText("");
					}
				}
			}
		});

		// Start date of check Expense (panel-4)
		labelStartDate_panel4 = new JLabel("Start Date");
		labelStartDate_panel4.setFont(new Font("Arial", Font.BOLD, 16));
		labelStartDate_panel4.setForeground(new Color(0, 51, 255));
		labelStartDate_panel4.setBackground(new Color(0, 204, 153));
		labelStartDate_panel4.setBounds(156, 18, 130, 22);

		calendarStartDate_panel4 = new JButton("Calendar");
		calendarStartDate_panel4.setBounds(260, 18, 100, 28);
		calendarStartDate_panel4.setForeground(new Color(50, 0, 200));
		calendarStartDate_panel4.setFont(new Font("Arial", Font.BOLD, 14));

		calendartextStartDate_panel4 = new JTextField();
		calendartextStartDate_panel4.setBounds(386, 16, 100, 28);
		calendartextStartDate_panel4.setColumns(10);
		calendartextStartDate_panel4.setEditable(false);

		// End date of check Expense (panel-4)
		labelEndDate_panel4 = new JLabel("End Date");
		labelEndDate_panel4.setFont(new Font("Arial", Font.BOLD, 16));
		labelEndDate_panel4.setForeground(new Color(0, 51, 255));
		labelEndDate_panel4.setBackground(new Color(0, 204, 153));
		labelEndDate_panel4.setBounds(158, 62, 130, 22);

		calendarEndDate_panel4 = new JButton("Calendar");
		calendarEndDate_panel4.setBounds(260, 62, 100, 28);
		calendarEndDate_panel4.setForeground(new Color(50, 0, 200));
		calendarEndDate_panel4.setFont(new Font("Arial", Font.BOLD, 14));

		calendartextEndDate_panel4 = new JTextField();
		calendartextEndDate_panel4.setBounds(386, 62, 100, 28);
		calendartextEndDate_panel4.setColumns(10);
		calendartextEndDate_panel4.setEditable(false);

		comboLabelCategory_panel4 = new JLabel("Category Type");
		comboLabelCategory_panel4.setFont(new Font("Arial", Font.BOLD, 16));
		comboLabelCategory_panel4.setForeground(new Color(0, 51, 255));
		comboLabelCategory_panel4.setBackground(new Color(0, 204, 153));
		comboLabelCategory_panel4.setBounds(10, 62, 140, 40);

		categoryType2 = new String[] { "All", "Grocery", "Bills", "Travel",
				"Fun", "Other" };
		comboCategory_panel4 = new JComboBox(categoryType2);
		comboCategory_panel4.setForeground(Color.BLUE);
		comboCategory_panel4.setBackground(Color.GREEN);
		comboCategory_panel4.setFont(new Font("Arial", Font.BOLD, 10));
		comboCategory_panel4.setBounds(8, 90, 120, 43);

		btnCheckExpenses_panel4 = new JButton("Check Expenses");
		btnCheckExpenses_panel4.setBounds(156, 98, 160, 32);
		btnCheckExpenses_panel4.setForeground(new Color(50, 0, 200));
		btnCheckExpenses_panel4.setFont(new Font("Arial", Font.BOLD, 14));

		// Panel-4 Action Listeners
		calendarStartDate_panel4.addActionListener(this);
		calendarEndDate_panel4.addActionListener(this);
		btnCheckExpenses_panel4.addActionListener(this);
		comboCategory_panel4.addActionListener(this);

		leftPanel.add(panel4);
		panel4.setLayout(null);
		panel4.add(labelSelectSpan);
		panel4.add(comboCheckExpenses);
		panel4.add(labelStartDate_panel4);
		panel4.add(calendarStartDate_panel4);
		panel4.add(calendartextStartDate_panel4);
		panel4.add(labelEndDate_panel4);
		panel4.add(calendarEndDate_panel4);
		panel4.add(calendartextEndDate_panel4);
		panel4.add(comboLabelCategory_panel4);
		panel4.add(comboCategory_panel4);
		panel4.add(btnCheckExpenses_panel4);
		panel4.setLayout(null);
		// sub Panel-4 ends here

		/****** Start of Output Panel ******/
		// sub Panel-5 starts
		panel5 = new JPanel();
		panel5.setBackground(Color.ORANGE);
		panel5.setBorder(new TitledBorder(new EtchedBorder(), "Output Screen"));
		panel5.setPreferredSize(new Dimension(500, 140));

		textDisplayOutput = new JTextArea();
		textDisplayOutput.setBounds(6, 26, 480, 100);
		textDisplayOutput.setEditable(false);
		textDisplayOutput.setBackground(Color.ORANGE);
		textDisplayOutput.setForeground(new Color(0, 51, 255));
		textDisplayOutput.setFont(new Font("Arial", Font.BOLD, 14));

		leftPanel.add(panel5);
		panel5.setLayout(null);
		panel5.add(textDisplayOutput);
		leftPanel.setVisible(true);
		leftPanel.requestFocus();
		// sub-Panel-5 ends

		// If Expense tracker is already configured, it will auto-populate
		// earlier values.
		initialStartDate = sql_connect.getInitialStartDate();
		initialEndDate = sql_connect.getInitialEndDate();

		if (initialStartDate != null && !"".equals(initialStartDate) && (initialEndDate != null && !"".equals(initialEndDate))) {
			
			String limitPrefetch = sql_connect.getLimit_previous(initialStartDate, initialEndDate);
			System.out.println("[Limit] - already configured " + limitPrefetch);
				textExpLimit.setText(limitPrefetch);
				textStart.setText(initialStartDate);
				textEnd.setText(initialEndDate);
				pickedStartDate = textStart.getText();
				pickedEndDate = textEnd.getText();
				try {
					start = new SimpleDateFormat("yyyy-MM-dd")
							.parse(pickedStartDate);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				try {
					end = new SimpleDateFormat("yyyy-MM-dd")
							.parse(pickedEndDate);
				} catch (ParseException e) {
					e.printStackTrace();
				}
		}
		return leftPanel;
		/****** End of Output Panel ******/
	}

	// Action Events for all the buttons
	public void actionPerformed(ActionEvent e) {

		// This will be day/duration depending upon user selection in
		// "Set Expense Duration"
		Object source = e.getSource();
		timeSpanValue = (String) comboSpan_panel1.getSelectedItem();

		// "Select Start Date" - this will be called when select calendar#1 of
		// sub-panel-1
		if (source == btnCalendar1) {
			JFrame f1 = new JFrame();
			pickedStartDate = new DatePicker(f1).setPickedDate();
			textStart.setText(pickedStartDate);

			try {
				start = new SimpleDateFormat("yyyy-MM-dd")
						.parse(pickedStartDate);
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			System.out.println("pickedStartDate - " + pickedStartDate);

			// Display user selection in Output screen
			textDisplayOutput.setText("[Start Date] selected is - "
					+ pickedStartDate);
		}

		// "Select End Date" - this will be called when select calendar#2 of
		// sub-panel-1
		else if (source == btnCalendar2) {
			JFrame f2 = new JFrame();

			if ("".equals(textStart.getText()) || textStart.getText() == null) {

				JOptionPane.showMessageDialog(null,
						"Please first select Start Date", "Note",
						JOptionPane.INFORMATION_MESSAGE);
				textEnd.setText("");
				pickedEndDate = textEnd.getText();

				// Display user selection in Output screen
				textDisplayOutput.setText("[End Date] - Not selected");
			} else {
				// If the duration time Span is "Day" then end date will be same
				// as start Date!
				if (timeSpanValue == "Day") {
					pickedEndDate = pickedStartDate;
					textEnd.setText(pickedEndDate);
					end = start;
					System.out.println("Checking value of end Date "
							+ end.compareTo(start));
				} else {
					pickedEndDate = new DatePicker(f2).setPickedDate();
					try {
						end = new SimpleDateFormat("yyyy-MM-dd")
								.parse(pickedEndDate);
						if (start.compareTo(end) >= 0) {
							JOptionPane
									.showMessageDialog(
											f2,
											"End date should be greater than Start date",
											"Error", JOptionPane.ERROR_MESSAGE);
						} else
							textEnd.setText(pickedEndDate);
					} catch (ParseException e1) {
						e1.printStackTrace();
					}
				}
				System.out.println("pickedEndDate - " + pickedEndDate);

				// Display user selection in Output screen
				textDisplayOutput.setText("[End Date] selected is - "
						+ pickedEndDate);
			}
		}

		/*********** Actions under Panel-2 *************/
		// "Save Limit" - will save Expense Limit for day/duration, Database
		// tables will be updated
		else if (source == btnSaveLimit) {

			// saveLimit = Double.parseDouble(textExpLimit.getText());

			if ("".equals(textStart.getText()) || textStart.getText() == null
					|| "".equals(textEnd.getText())
					|| textEnd.getText() == null
					|| "".equals(textExpLimit.getText())
					|| textExpLimit.getText() == null) {
				System.out.println("[Pragati] - " + textStart.getText());
				JOptionPane
						.showMessageDialog(
								null,
								"Please Enter values for Start Date, End Date and Expense Limit",
								"Note", JOptionPane.INFORMATION_MESSAGE);
				textStart.setText("");
				textEnd.setText("");
				textExpLimit.setText("");
				pickedStartDate = textStart.getText();
				pickedEndDate = textEnd.getText();
				// Display user selection in Output screen
				textDisplayOutput
						.setText("Enter values for Start Date, End Date and Expense Limit");
			}

			else {
				saveLimit = Double.parseDouble(textExpLimit.getText());
				limit = sql_connect.getLimit(pickedStartDate, pickedEndDate);
				System.out.println("[Saved Limit] - " + textStart.getText());

				if (limit == null || "".equals(limit)) {
					sql_connect.clearLimit();
					sql_connect.setLimit_SaveDate(pickedStartDate,
							pickedEndDate, saveLimit);
					textDisplayOutput.setText("[Saved Limit] - $" + saveLimit);
				} else if (limit != null && !"".equals(limit)) {
					int res = JOptionPane
							.showConfirmDialog(
									null,
									"Limit is already set for this period, you want to reset?",
									"Note", JOptionPane.OK_CANCEL_OPTION);
					if (res == 0) {
						sql_connect.clearLimit();
						sql_connect.setLimit_SaveDate(pickedStartDate,
								pickedEndDate, saveLimit);
						textDisplayOutput
								.setText("[Saved Limit] - New saved limit - $"
										+ saveLimit);
					} else {
						limit = sql_connect.getLimit_previous(pickedStartDate,
								pickedEndDate);
						JOptionPane.showMessageDialog(null,
								"Keeping limit to previous value - " + limit,
								"Note", JOptionPane.INFORMATION_MESSAGE);
						textExpLimit.setText(limit);
						saveLimit = Double.parseDouble(limit);
						System.out
								.println("Locally also setting value of saveLimit to limit - saveLimit = "
										+ saveLimit);
						textDisplayOutput
								.setText("[Saved Limit] - keeping to earlier saved limit - $"
										+ saveLimit);
					}
				} else {
					System.out.println("Something wrong try again");
					textDisplayOutput
							.setText("[Saved Limit] - Something wrong happened, Please set again.");
				}
			}
		}

		// "Clear button" - will reset Expense tracker, will also truncate
		// tables in database.
		else if (source == btnClearLimit) {
			textStart.setText("");
			textEnd.setText("");
			textExpLimit.setText("");
			textExpDate.setText("");
			textAmtSpend.setText("");
			calendartextStartDate_panel4.setText("");
			calendartextEndDate_panel4.setText("");
			pickedStartDate = "";
			pickedEndDate = "";
			if (!sql_connect.clearLimit()) {
				JOptionPane
						.showMessageDialog(
								null,
								"One of the tables could not be cleared - Press Clear limit again to conitnue",
								"Error", JOptionPane.ERROR_MESSAGE);
			}
			
			//Notify Observable events to Observer(Visualization panel)
			myArray = new ArrayList<String>();

			setChanged();
			notifyObservers(new ArrayList((myArray)));
			
			
			textDisplayOutput
					.setText("[Clear Limit] - Expense Tracker cleared");
		}

		// ******Action Panel-3*****/

		// "Select Date" - to enter Expense for a category
		else if (source == btnCalendar3) {
			JFrame f3 = new JFrame();
			expenseSelectDate = new DatePicker(f3).setPickedDate();
			textExpDate.setText(expenseSelectDate);
			System.out.println("Expense tracker is set for/between - "
					+ pickedStartDate + " AND " + pickedEndDate);

			try {
				selectedDate = new SimpleDateFormat("yyyy-MM-dd")
						.parse(expenseSelectDate);

				// If user select date outside start and end date, pop-up to
				// re-enter the date
				if (pickedStartDate == null || "".equals(pickedStartDate)
						|| pickedEndDate == null || "".equals(pickedEndDate)) {
					JOptionPane.showMessageDialog(f3,
							"Please choose between start & end date ", "Note",
							JOptionPane.INFORMATION_MESSAGE);
					textExpDate.setText("");
					textDisplayOutput.setText("[Expense Date] NOT selected");
				}
				// Date selected is in between
				else if ((start.compareTo(selectedDate) <= 0)
						&& (end.compareTo(selectedDate) >= 0)) {
					textExpDate.setText(expenseSelectDate);
					System.out
							.println("Date selected is between start and end date");
					textDisplayOutput.setText("[Expense Date] selected is - "
							+ expenseSelectDate);
				}

				else if (start.compareTo(end) == 0) {
					JOptionPane.showMessageDialog(f3,
							"Expense tracker was configured for ONLY "
									+ pickedStartDate
									+ " selecting same value...", "Note",
							JOptionPane.INFORMATION_MESSAGE);
					textExpDate.setText(pickedStartDate);
					System.out
							.println("Start and End date are same...user must have selected signle day");
					textDisplayOutput.setText("[Expense Date] selected is - "
							+ pickedStartDate);
				} else {
					JOptionPane.showMessageDialog(f3,
							"Please choose between start & end date ", "Note",
							JOptionPane.INFORMATION_MESSAGE);
					textExpDate.setText("");
					textDisplayOutput.setText("Please choose between start & end date");
				}
				
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
		}

		// "Save Expense" for a category on a particular day
		else if (source == btnSaveExpenses) {

			String limitcheck = sql_connect.getLimit(pickedStartDate,
					pickedEndDate);
			System.out.println("Checking if Limit is set" + limitcheck);

			if ("".equals(textAmtSpend.getText())
					|| textAmtSpend.getText() == null
					|| "".equals(textExpDate.getText())
					|| textExpDate.getText() == null || "".equals(limitcheck)
					|| limitcheck == null) {
				JOptionPane.showMessageDialog(null,
						"Please select Expense Limit, Amount and Date", "Note",
						JOptionPane.INFORMATION_MESSAGE);
				textAmtSpend.setText("");
				textExpDate.setText("");
				// Display user selection in Output screen
				textDisplayOutput
						.setText("Please select Expense Limit, Amount and Date");
			} else {
				String temp_value = sql_connect.getExpense("All",
						sql_connect.getInitialStartDate(),
						sql_connect.getInitialEndDate());

				if (temp_value == "") {
					amtSpend = Double.parseDouble(textAmtSpend.getText());
					System.out
							.println("Nothing spend so far, setting it for first time - "
									+ amtSpend);

				} else {
					amtSpend = Double.parseDouble(temp_value)
							+ Double.parseDouble(textAmtSpend.getText());
					System.out.println("Total amtSpend - " + amtSpend);
				}

				saveLimit = Double.parseDouble(limitcheck);
				System.out.println("amtSpend - " + amtSpend + " saveLimit - "
						+ saveLimit);

				// When Expense limit is approaching with less than $50
				if (amtSpend > saveLimit - 50) {
					// Amount is greater than Expense Limit
					if (amtSpend > saveLimit) {
						int result = JOptionPane
								.showConfirmDialog(
										btnSaveExpenses,
										"Amount spend is greater than Expense Limit - Press OK to continue or click CANCEL",
										"Message", JOptionPane.OK_CANCEL_OPTION);
						if (result == JOptionPane.OK_OPTION) {
							System.out
									.println("User pressed OK for Amount Spend greater than Saved Limit");

							// Insert amount to Table Expense Tracker when user
							// press OK
							if (sql_connect.insertExpense(
									comboExpCategory_panel3.getSelectedItem()
											.toString(), textExpDate.getText(),
									Double.parseDouble(textAmtSpend.getText()))) {
								textDisplayOutput.setText("[Saved Expense] - "
										+ textExpDate.getText()
										+ " Amount Spend on "
										+ comboExpCategory_panel3
												.getSelectedItem().toString()
										+ " - $"
										+ Double.parseDouble(textAmtSpend
												.getText()));
							}
						} else if (result == JOptionPane.CANCEL_OPTION)
							System.out
									.println("User pressed CANCEL - waiting for correct value");
						else
							System.out.println("Something not correct");
					}

					// Amount is approaching Expense Limit
					else {
						int result = JOptionPane
								.showConfirmDialog(
										btnSaveExpenses,
										"You are approaching Expense Limit - Press OK to continue or click CANCEL",
										"Message", JOptionPane.OK_CANCEL_OPTION);
						if (result == JOptionPane.OK_OPTION) {
							System.out
									.println("User pressed OK for Amount Spend greater than Saved Limit");

							// Inserting amount to Table Expense Tracker when
							// user
							// pressed
							// OK
							if (sql_connect.insertExpense(
									comboExpCategory_panel3.getSelectedItem()
											.toString(), textExpDate.getText(),
									Double.parseDouble(textAmtSpend.getText()))) {
								textDisplayOutput.setText("[Saved Expense] - "
										+ textExpDate.getText()
										+ " Amount Spend on "
										+ comboExpCategory_panel3
												.getSelectedItem().toString()
										+ " - $"
										+ Double.parseDouble(textAmtSpend
												.getText()));
							}
						} else if (result == JOptionPane.CANCEL_OPTION)
							System.out
									.println("User pressed CANCEL - waiting for correct value");
						else
							System.out.println("Something not correct");
					}
				}

				// If amtSpend is less than saveLimit then insert without prompt
				else {
					if (sql_connect.insertExpense(comboExpCategory_panel3
							.getSelectedItem().toString(), textExpDate
							.getText(), Double.parseDouble(textAmtSpend
							.getText()))) {
						textDisplayOutput.setText("[INSERT]- "
								+ textExpDate.getText()
								+ " Category - "
								+ comboExpCategory_panel3.getSelectedItem()
										.toString() + " Amount Spend - "
								+ Double.parseDouble(textAmtSpend.getText()));
					}
				}
			}
		}

		/*********** Actions under Panel-4 *************/

		// "Start Date" - Check Expense
		else if (source == calendarStartDate_panel4) {
			JFrame f4 = new JFrame();
			chkExp_pickStartDate = new DatePicker(f4).setPickedDate();
			try {
				if ((date.datePicker(start, end, chkExp_pickStartDate)) != "") {
					calendartextStartDate_panel4.setText(chkExp_pickStartDate);
				} else
					calendartextStartDate_panel4.setText("");

			} catch (ParseException e1) {
				e1.printStackTrace();
			}
		}

		// "End Date" - Check Expense
		else if (source == calendarEndDate_panel4) {
			JFrame f5 = new JFrame();
			chkExp_pickEndDate = new DatePicker(f5).setPickedDate();
			try {
				if ((date.datePicker(start, end, chkExp_pickEndDate)) != "") {
					calendartextEndDate_panel4.setText(chkExp_pickEndDate);
				} else
					calendartextEndDate_panel4.setText("");

			} catch (ParseException e1) {
				e1.printStackTrace();
			}
		}

		// "Category selection" - Check Expenses
		else if (source == comboExpCategory_panel3) {
			System.out.println(comboExpCategory_panel3.getSelectedItem()
					.toString());
		}

		// "Check Expenses" button selection
		else if (source == btnCheckExpenses_panel4) {

			if ("".equals(calendartextEndDate_panel4.getText())
					|| calendartextEndDate_panel4.getText() == null
					|| "".equals(calendartextStartDate_panel4.getText())
					|| calendartextStartDate_panel4.getText() == null) {
				JOptionPane.showMessageDialog(null,
						"Please select Start and Date dates", "Note",
						JOptionPane.INFORMATION_MESSAGE);
				calendartextStartDate_panel4.setText("");
				calendartextEndDate_panel4.setText("");
				// Display user selection in Output screen
				textDisplayOutput.setText("Please select Start and Date dates");
			} else {
				individualExpense = new String[6];
				for (int i = 0; i < categoryType2.length; i++) {
					individualExpense[i] = sql_connect.getExpense(
							categoryType2[i], chkExp_pickStartDate,
							chkExp_pickEndDate);
					if ((individualExpense[i]) == "")
						individualExpense[i] = " - Nothing spent";
					else
						individualExpense[i] = " - $" + individualExpense[i];
				}
				if (comboCategory_panel4.getSelectedItem().toString() == "All") {
					textDisplayOutput
							.setText("[Expense] - Amount across All categories - \n"
									+ categoryType2[1]
									+ individualExpense[1]
									+ "\n"
									+ categoryType2[2]
									+ individualExpense[2]
									+ "\n"
									+ categoryType2[3]
									+ individualExpense[3]
									+ "\n"
									+ categoryType2[4]
									+ individualExpense[4]
									+ "\n"
									+ categoryType2[5]
									+ individualExpense[5]
									+ "\n");
				} else {
					textDisplayOutput.setText("[Expense] - Amount spent on "
							+ comboCategory_panel4.getSelectedItem().toString()
							+ " - $"
							+ sql_connect.getExpense(comboCategory_panel4
									.getSelectedItem().toString(),
									chkExp_pickStartDate, chkExp_pickEndDate));
				}

				// Notify Observable events to Observer(Visualization panel)
				myArray = new ArrayList<String>();
				myArray.add(chkExp_pickStartDate);
				myArray.add(chkExp_pickEndDate);

				setChanged();
				notifyObservers(new ArrayList((myArray)));
			}
		}
	}// end of actionPerformed

	public JTextField getTextStart() {
		return textStart;
	}

	public void setTextStart(JTextField textStart) {
		this.textStart = textStart;
	}
}