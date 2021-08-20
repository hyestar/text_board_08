package com.sbs.example.textBoard;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		List<Article> articles = new ArrayList<>();
		int lastArticleId = 0;

		while (true) {
			System.out.printf("명령어를 입력해주세요) ");
			String cmd = sc.nextLine().trim();

			if (cmd.equals("exit")) {
				System.out.println("프로그램을 종료합니다.");
				break;
			}

			else if (cmd.equals("add")) {
				int id = lastArticleId + 1;
				System.out.print("제목 : ");
				String title = sc.nextLine();
				System.out.print("내용 : ");
				String body = sc.nextLine();

				Article article = new Article(id, title, body);
				articles.add(article);

				Connection conn = null;
				PreparedStatement pstat = null;

				try {
					Class.forName("com.mysql.jdbc.Driver");
					String url = "jdbc:mysql://127.0.0.1:3306/text_board?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";

					conn = DriverManager.getConnection(url, "root", "");
					System.out.println("연결 성공!");

					String sql = "INSERT INTO article";
					sql += " SET regDate = NOW()";
					sql += ", updateDate = NOW()";
					sql += ", title = \'" + title + "\' ";
					sql += ", `body` = \'" + body + "\' ;";

					pstat = conn.prepareStatement(sql);
					int affectedRows = pstat.executeUpdate();

					System.out.println("affectedRows : " + affectedRows);

				} catch (ClassNotFoundException e) {
					System.out.println("드라이버 로딩 실패");
				} catch (SQLException e) {
					System.out.println("에러: " + e);
				} finally {
					try {
						if (conn != null && !conn.isClosed()) {
							conn.close();
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

				lastArticleId++;

				System.out.println(article);

			} else if (cmd.equals("list")) {
				if (articles.size() == 0) {
					System.out.println("게시물이 존재하지 않습니다.");
					continue;
				}
				for (Article article : articles) {
					System.out.printf("아이디 : %d   제목 : %s\n", article.id, article.title);
				}

			}
		}

	}

}
