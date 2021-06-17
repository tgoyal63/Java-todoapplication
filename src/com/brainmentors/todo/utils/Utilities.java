package com.brainmentors.todo.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Utilities {
	private Utilities() {
	}

	public static final Scanner scanner = new Scanner(System.in);

	private static CommandLineTable cltInstance;

	private static SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");

	public static String convertDateToString(Date date) {
		return formatter.format(date);
	}

	public static Date convertStringToDate(String date) throws ParseException {
		return formatter.parse(date);
	}

	public static CommandLineTable getCltInstance() {
		if (cltInstance == null) {
			cltInstance = new CommandLineTable();
		}
		return cltInstance;
	}

	public static void deleteCltInstance() {
		if (cltInstance != null)
			cltInstance = null;
	}
}
