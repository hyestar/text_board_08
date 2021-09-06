package com.sbs.example.textBoard;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

import com.sbs.example.textBoard.controller.ArticleController;
import com.sbs.example.textBoard.controller.MemberController;

public class App {
	public void run() {
		Scanner sc = new Scanner(System.in);

		while (true) {
			System.out.printf("명령어를 입력해주세요) ");
			String cmd = sc.nextLine().trim();

			// DB 연결 시작
			Connection conn = null;
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
			} catch (ClassNotFoundException e1) {
				System.err.println("예외 : MySQL 드라이버 클래스가 없습니다.");
				System.out.println("프로그램을 종료합니다.");
				break;
			}

			String url = "jdbc:mysql://127.0.0.1:3306/text_board?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";

			try {
				conn = DriverManager.getConnection(url, "root", "");
				int actionResult = action(conn, sc, cmd);
				if (actionResult == -1) {
					break;
				}
			} catch (SQLException e1) {
				System.err.println("예외 : DB에 연결할 수 없습니다.");
				System.out.println("프로그램을 종료합니다.");
				break;
			} finally { // try가 실행되든 catch가 연결되든 무조건 실행해서 꺼줘야 됨.
				try {
					if (conn != null && !conn.isClosed()) {
						conn.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		// DB 연결 끝
		sc.close();
	}

	private int action(Connection conn, Scanner sc, String cmd) {

		MemberController memberController = new MemberController(conn, sc);
		
		ArticleController articleController = new ArticleController(conn, sc);

		if (cmd.equals("member join")) {
			memberController.join(cmd);
		} else if(cmd.equals("member login")) {
			memberController.login(cmd);
		} else if (cmd.equals("add")) {
			articleController.add(cmd);
		} else if (cmd.equals("delete")) {
			articleController.delete(cmd);
		} else if (cmd.startsWith("article detail ")) {
			articleController.listDetail(cmd);
		} else if (cmd.startsWith("article modify")) {
			articleController.modify(cmd);
		} else if (cmd.equals("list")) {
			articleController.list(cmd);
		} else if (cmd.equals("exit"))

		{
			System.out.println("프로그램을 종료합니다.");
			return -1;
		}
		return 0;
	}
}
