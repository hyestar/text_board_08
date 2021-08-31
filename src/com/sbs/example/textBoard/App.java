package com.sbs.example.textBoard;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
	public void run() {
		Scanner sc = new Scanner(System.in);

		while (true) {
			System.out.printf("명령어를 입력해주세요) ");
			String cmd = sc.nextLine().trim();

			// DB 연결 시작
			Connection conn = null;
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e1) {
				System.err.println("예외 : MySQL 드라이버 클래스가 없습니다.");
				System.out.println("프로그램을 종료합니다.");
				break;
			}

			String url = "jdbc:mysql://127.0.0.1:3306/text_board?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";

			try {
				conn = DriverManager.getConnection(url, "root", "");
				int actionResult = doAction(conn, sc, cmd);
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

	private int doAction(Connection conn, Scanner sc, String cmd) {
		int lastArticleId = 0;
		if (cmd.equals("add")) {
			int id = lastArticleId + 1;
			System.out.printf("제목 : ");
			String title = sc.nextLine();
			System.out.printf("내용 : ");
			String body = sc.nextLine();

			// SecSql sql = new SecSql();

			PreparedStatement pstat = null;

			try {

				String sql = "INSERT INTO article";
				sql += " SET regDate = NOW()";
				sql += ", updateDate = NOW()";
				sql += ", title = \'" + title + "\' ";
				sql += ", `body` = \'" + body + "\' ;";

				pstat = conn.prepareStatement(sql);
				int affectedRows = pstat.executeUpdate();

				System.out.println("affectedRows : " + affectedRows);

			} catch (SQLException e) {
				System.out.println("에러: " + e);
			} finally {
				try {
					if (pstat != null && !pstat.isClosed()) {
						pstat.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			lastArticleId++;

		} else if (cmd.startsWith("article modify")) {
			int id = Integer.parseInt(cmd.split(" ")[2]);
			System.out.printf("== %d번 게시글 수정 ==\n", id);
			System.out.printf("새 제목 : ");
			String title = sc.nextLine();
			System.out.printf("새 내용 : ");
			String body = sc.nextLine();

			PreparedStatement pstat = null;

			try {

				String sql = "UPDATE article";
				sql += " SET updateDate = NOW()";
				sql += ", title = \'" + title + "\' ";
				sql += ", `body` = \'" + body + "\' ";
				sql += " WHERE id = " + id;

				pstat = conn.prepareStatement(sql);
				int affectedRows = pstat.executeUpdate();

				System.out.println("affectedRows : " + affectedRows);

			} catch (SQLException e) {
				System.out.println("에러: " + e);
			} finally {
				try {
					if (pstat != null && !pstat.isClosed()) {
						pstat.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			System.out.printf("%d번 게시글이 수정되었습니다.\n", id);

		}

		else if (cmd.equals("list")) {
			List<Article> articles = new ArrayList<>();

			PreparedStatement pstat = null;
			ResultSet rs = null; // sql 질의에 의해 생성된 테이블을 저장하는 객체

			try {

				String sql = "SELECT *";
				sql += " FROM article";
				sql += " ORDER BY id DESC;";

//						System.out.println(sql);

				pstat = conn.prepareStatement(sql);
				rs = pstat.executeQuery(sql);

				while (rs.next()) {
					int id = rs.getInt("id");
					String regDate = rs.getString("regDate");
					String updateDate = rs.getString("updateDate");
					String title = rs.getString("title");
					String body = rs.getString("body");

					Article article = new Article(id, regDate, updateDate, title, body);
					articles.add(article);
				}

			} catch (SQLException e) {
				System.out.println("에러: " + e);
			} finally { // 먼저 만들어 진 것을 나중에 꺼주는 것이 좋다
				try {
					if (rs != null && !rs.isClosed()) {
						rs.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				try {
					if (pstat != null && !pstat.isClosed()) {
						pstat.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}

			}
			if (articles.size() == 0) {
				System.out.println("게시물이 존재하지 않습니다.");
				return 0;
			}
			for (Article article : articles) {
				System.out.printf("아이디 : %d   제목 : %s\n", article.id, article.title);
			}

		} else if (cmd.equals("exit")) {
			System.out.println("프로그램을 종료합니다.");
			return -1;
		}
		return 0;
	}
}
