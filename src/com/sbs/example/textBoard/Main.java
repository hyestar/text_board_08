package com.sbs.example.textBoard;

import com.sbs.example.textBoard.exception.SQLErrorException;

public class Main {

	public static void main(String[] args) {
		try {
			new App().run();
		} catch (SQLErrorException e) {
			System.err.println(e.getMessage());
			e.getOrigin().getStackTrace();
		}
	}

}
