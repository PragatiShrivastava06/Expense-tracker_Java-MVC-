/**
 * @author Pragati Shrivastava
 * @version 1.0
 */
package com.shrivastava.assign3.logic;

public class ResultTransport implements Comparable {

	private String category;
	private int sum;

	public ResultTransport(int sum, String category) {
		this.sum = sum;
		this.category = category;
	}

	public ResultTransport() {}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getSum() {
		return sum;
	}

	public void setSum(int sum) {
		this.sum = sum;
	}

	@Override
	public String toString() {
		return category + "\t  |  " + sum;
	}

	public int compareTo(Object second) // signature same as interface
	{ // return -1, 0 , 1
		ResultTransport other = (ResultTransport) second; // casting is
															// necessary as
															// Comparable
															// interface is non
															// generic
		if (sum < other.sum)
			return -1;
		else if (sum == other.sum)
			return 0;
		else
			return 1;
	}
}
