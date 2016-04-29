
/**
 * @author Pragati Shrivastava
 * @version 1.0
 */
/********************************************************************************************************
 * This is Main file that loads all the other panels													*
 * 																										*
 * ******************************************************************************************************
 * */
package com.shrivastava.assign3.expensetracker;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class Assign3TestMain extends JFrame {

	private JPanel contentPane;
	private Visualization visualization;
	private ExpenseTracker leftPanel;
	private JPanel left;

	public Assign3TestMain()

	{
		super("EXPENSE TRACKER");
		setForeground(new Color(0, 51, 204));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1100, 825);
		contentPane = new JPanel();
		contentPane.setBackground(Color.CYAN);
		Border outline = BorderFactory.createLineBorder(Color.BLACK);
		((JComponent) contentPane).setBorder(outline);
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(1, 2));

		loadLeftPanel();
		loadRightPanel();
		leftPanel.addObserver(visualization);

		setVisible(true);
	}

	/**** UI for Left Panel ***********/
	private void loadLeftPanel() {
		leftPanel = new ExpenseTracker();
		left = leftPanel.createLeftPanel();
		contentPane.add(left);
	}

	/**** UI for Right Panel ***********/
	private void loadRightPanel() {
		visualization = new Visualization();
		contentPane.add(visualization);
	}

	public static void main(String[] args) {
		new Assign3TestMain().setVisible(true);
	}

}
