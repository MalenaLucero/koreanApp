package com.koreanApp.util;

@SuppressWarnings("serial")
public class InvalidSearchWordException extends Exception{
	public InvalidSearchWordException() {
		super("Invalid search word");
	}
}
