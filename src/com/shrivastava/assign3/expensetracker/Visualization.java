/**
 * @author Pragati Shrivastava
 * @version 1.0
 */
/********************************************************************************************************
 * This is Right Panel for Visualization																*
 * 																										*
 * ******************************************************************************************************
 * 																										*
 * ******************************************************************************************************
 * ******/

package com.shrivastava.assign3.expensetracker;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import com.shrivastava.assign3.logic.BackendLogic;
import com.shrivastava.assign3.logic.ResultTransport;

//Observer class that take events from Observable class
class Visualization extends JPanel implements Observer {

	BackendLogic sql_connect = new BackendLogic();
	private ArrayList leftPanelValues;
	private int x, y, coordinate[];
	private Double[] arr_sum;
	private String arr_category[];
	private List<ResultTransport> total_Exp;
	private String limit;
	private ResultTransport element_limit;
	private Color pickColor[];

	public Visualization() {
		setBackground(Color.CYAN);
		setLayout(null);
		setVisible(true);
	}

	public void update(Observable observable, Object object) {

		leftPanelValues = (ArrayList) object;

		if(leftPanelValues.size()>0){
		
			// Gets list of unique categories between selected dates
			total_Exp = sql_connect.getCategory((String) leftPanelValues.get(0), (String) leftPanelValues.get(1));

			//Gets limit set Expense Tracker
			limit = sql_connect.getLimit(sql_connect.getInitialStartDate(), sql_connect.getInitialEndDate());

			System.out.println("Exp Limit - " + limit);
			System.out.println("total_Exp - from update" + total_Exp.size());

			//Add "Expense Limit" to total_Exp per category list
			element_limit = new ResultTransport();
			element_limit.setSum((int) Double.parseDouble(limit));
			element_limit.setCategory("Limit");
			total_Exp.add(element_limit);

			// Sorted List of individual category, limit and cumulative expenses
			Collections.sort(total_Exp);

			System.out.println("total_Exp " + total_Exp);
			System.out.println("SUM " + " Category ");
			for (ResultTransport temp : total_Exp) {
				System.out.print(temp.getCategory());
				System.out.println("   " + temp.getSum());
			}
			repaint();
			
		}
		else
			repaint();
	}

	public void paintComponent(Graphics g1) {
		super.paintComponents(g1);
		Graphics2D g = (Graphics2D) g1;
		// compute frame relative to screen
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setColor(Color.BLACK);
		g.drawLine(0, 0, 0, 1200);
		g.drawLine(1, 0, 1, 1200);

		g.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
		g.setColor(new Color(102, 0, 102));
		g.drawString("VISUALIZATION", getWidth() / 3, 50);

		if (total_Exp != null && leftPanelValues.size()>0) {
			draw(g);
		}
	}

	public void draw(Graphics2D g) {

		arr_sum = new Double[total_Exp.size()];
		arr_category = new String[total_Exp.size()];
		coordinate = new int[arr_sum.length];

		for (int j = 0; j < total_Exp.size(); j++) {
			arr_sum[j] = (double) total_Exp.get(j).getSum();
			arr_category[j] = total_Exp.get(j).getCategory();
		}

		if (getWidth() > getHeight()) {
			x = getHeight() / 2;
			y = x;
		} else {
			x = getWidth() / 2;
			y = x;
		}

		System.out.println("x - " + x);
		System.out.println("y - " + y);

		//Calculate coordinates to draw circles for each category
		for (int i = 0; i < arr_sum.length; i++) {
			coordinate[i] = (int) (x * (arr_sum[i] / arr_sum[arr_sum.length - 1]));

			/*
			 * If two or more categories are of same value, circles will
			 * override to avoid that making sure gap between consecutive
			 * circles is at least 7 pixels
			 */
			coordinate[i] = coordinate[i] + 7 * i;
			System.out.println(" coordinate - " + i + " " + coordinate[i]);
		}

		System.out.println("coordinate.length - " + coordinate.length);

		int temp_y = y / 5;
		pickColor=new Color[]{ Color.MAGENTA,Color.BLUE, Color.BLACK, Color.GREEN, Color.YELLOW,Color.RED };
		for (int k = (coordinate.length - 1); k >= 0; k--)
		{
		
			g.setPaint(pickColor[k]);
			
			//Draw concentric circles for each category, including Expense Limit
			g.fillArc(x - ((coordinate[k] - coordinate[0]) / 2), y - ((coordinate[k] - coordinate[0]) / 2),
					coordinate[k], coordinate[k], 0, 360);

			Font font = new Font("SansSerif", Font.BOLD, 15);
			g.setFont(font);
			FontMetrics metrics = getFontMetrics(font);
			int ascent = metrics.getMaxAscent();
			int offsetY = ascent + 2;

			temp_y = temp_y + 25;
			g.fillRect(2 * x - 90, temp_y, 20, 20);
			g.setColor(Color.BLACK);
			
			//Show Legends for each category and Expense Limit
			g.drawString(arr_category[k], 2 * x - 65, temp_y + 15);
			System.out.println("text coordinate - " + (x - ((coordinate[k] - coordinate[0]) / 2) + coordinate[k]) / 2);
		}
	}
}
