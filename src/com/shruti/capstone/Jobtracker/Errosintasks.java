package com.shruti.capstone.Jobtracker;
/*
 * Capstone project by Shruti Gorde
 * 
 * 
 */
public class Errosintasks extends Exception {
	private static final long serialVersionUID = -613291414184862382L;

	public Errosintasks() {
	}

	public Errosintasks(String str1) {
		super(str1);
	}

	public Errosintasks(Throwable str1) {
		super(str1);
	}

	public Errosintasks(String str1, Throwable str2) {
		super(str1, str2);
	}

	public String getMessage() {

		Throwable t = getCause();
		String msg = super.getMessage();

		if (t != null) {
			msg = t.getMessage();
		}
		return msg;
	}
}
