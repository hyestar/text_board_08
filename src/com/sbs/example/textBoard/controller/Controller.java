package com.sbs.example.textBoard.controller;

import java.util.Scanner;

public abstract class Controller {
	protected Scanner sc;

	public Controller(Scanner sc) {
		this.sc = sc;
	}
}
