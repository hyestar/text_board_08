package com.sbs.example.textBoard;

import java.sql.Connection;
import java.util.Scanner;

import com.sbs.example.textBoard.controller.ArticleController;
import com.sbs.example.textBoard.controller.MemberController;
import com.sbs.example.textBoard.dao.ArticleDao;
import com.sbs.example.textBoard.dao.MemberDao;
import com.sbs.example.textBoard.service.ArticleService;
import com.sbs.example.textBoard.service.MemberService;
import com.sbs.example.textBoard.session.Session;

public class Container {

	public static ArticleController articleController;
	public static MemberController memberController;

	public static ArticleService articleService;
	public static MemberService memberService;

	public static ArticleDao articleDao;
	public static MemberDao memberDao;

	public static Session session;

	public static Scanner sc;

	public static void init(Connection conn, Scanner sc) {
		Container.sc = sc;
		session = new Session();

		articleDao = new ArticleDao(conn);
		memberDao = new MemberDao(conn);

		articleService = new ArticleService();
		memberService = new MemberService();

		articleController = new ArticleController();
		memberController = new MemberController();
	}
}
