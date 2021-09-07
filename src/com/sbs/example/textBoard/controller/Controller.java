package com.sbs.example.textBoard.controller;

import java.util.Scanner;

import com.sbs.example.textBoard.Container;

public abstract class Controller {
	protected Scanner sc;

	public Controller() {
		this.sc = Container.sc;
	}
}
