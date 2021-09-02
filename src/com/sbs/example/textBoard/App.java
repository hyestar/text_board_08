package com.sbs.example.textBoard;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.sbs.example.textBoard.util.DBUtil;
import com.sbs.example.textBoard.util.SecSql;

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
		if (cmd.equals("member join")) {
			String loginId;
			String loginPw;
			String loginPwConfirm;
			String name;

			System.out.println("== 회원가입 ==");
			// 로그인 아이디 입력
			while (true) {
				System.out.printf("로그인 아이디 : ");
				loginId = sc.nextLine().trim();

				if (loginId.length() == 0) {
					System.out.println("로그인 아이디를 입력해주세요.");
					continue;
				}
				
				SecSql sql = new SecSql();
				
				sql.append("SELECT COUNT(*) > 0");
				sql.append("FROM `member`");
				sql.append("WHERE loginId = ?", loginId);
				
				boolean isLoginIdDup = DBUtil.selectRowBooleanValue(conn, sql);
				
				if(isLoginIdDup) {
					System.out.printf("%s는 이미 사용중인 로그인 아이디 입니다.");
					continue;
				}
				break;

			}
			// 로그인 비밀번호 입력
			while (true) {
				System.out.printf("로그인 비밀번호 : ");
				loginPw = sc.nextLine().trim();

				if (loginPw.length() == 0) {
					System.out.println("로그인 비밀번호를 입력해주세요.");
				}
				// 로그인 비밀번호 확인 입력
				boolean loginPwConfirmIsSame = true;

				while (true) {
					System.out.printf("로그인 비밀번호 확인 : ");
					loginPwConfirm = sc.nextLine().trim();

					if (loginPwConfirm.length() == 0) {
						System.out.println("로그인 비밀번호 확인을 입력해주세요.");
						continue;
					}
					if (loginPw.equals(loginPwConfirm) == false) {
						System.out.println("로그인 비밀번호가 일치하지 않습니다. 다시 입력해주세요.");
						loginPwConfirmIsSame = false;
						break;
					}

					break;
				}
				// 로그인 비밀번호와 로그인 비밀번호 확인이 일치한다면 입력 완료.
				if (loginPwConfirmIsSame) {
					break;
				}
			}
			// 이름 입력
			while (true) {
				System.out.printf("이름 : ");
				name = sc.nextLine().trim();

				if (name.length() == 0) {
					System.out.println("이름을 입력해주세요.");
					continue;
				}
				break;

			}

			SecSql sql = new SecSql();

			sql.append("INSERT INTO `member`");
			sql.append("SET regDate = NOW()");
			sql.append(", updateDate = NOW()");
			sql.append(", loginId = ?", loginId);
			sql.append(", loginPw = ?", loginPw);
			sql.append(", `name` = ?", name);

			int id = DBUtil.insert(conn, sql);

			System.out.printf("%s님 환영합니다.\n", name);
		}

		else if (cmd.equals("add")) {
			System.out.printf("제목 : ");
			String title = sc.nextLine();
			System.out.printf("내용 : ");
			String body = sc.nextLine();

			SecSql sql = new SecSql();

			sql.append("INSERT INTO article");
			sql.append("SET regDate = NOW()");
			sql.append(", updateDate = NOW()");
			sql.append(", title = ?", title);
			sql.append(", `body` = ?", body);

			int id = DBUtil.insert(conn, sql);

			System.out.printf("%d번 게시물이 생성되었습니다.\n", id);

		} else if (cmd.equals("delete")) {
			int id = Integer.parseInt(cmd.split(" ")[2]);

			System.out.printf("== %d번 게시글 삭제 ==\n", id);

			SecSql sql = new SecSql();
			sql.append("SELECT COUNT(*) AS cnt");
			sql.append("FROM article");
			sql.append("WHERE id = ?", id);
			int articlesCount = DBUtil.selectRowIntValue(conn, sql);

			if (articlesCount == 0) {
				System.out.printf("%d번 게시글은 존재하지 않습니다.\n", id);
				return 0;
			}

			sql = new SecSql();
			sql.append("DELETE FROM article");
			sql.append("WHERE id = ?", id);

			DBUtil.delete(conn, sql);

			System.out.printf("%d번 게시글이 삭제되었습니다.\n", id);

		} else if (cmd.startsWith("article detail ")) {
			int id = Integer.parseInt(cmd.split(" ")[2]);

			System.out.printf("== %d번 게시글 상세보기 ==\n", id);

			SecSql sql = new SecSql();
			sql.append("SELECT *");
			sql.append("FROM article");
			sql.append("WHERE id = ?", id);
			Map<String, Object> articleMap = DBUtil.selectRow(conn, sql);

			if (articleMap.isEmpty()) {
				System.out.printf("%d번 게시글은 존재하지 않습니다.\n", id);
				return 0;
			}

			Article article = new Article(articleMap);

			System.out.printf("번호 : %d\n", article.id);
			System.out.printf("작성날짜 : %s\n", article.regDate);
			System.out.printf("수정날짜 : %s\n", article.updateDate);
			System.out.printf("제목 : %s\n", article.title);
			System.out.printf("내용 : %s\n", article.body);

		} else if (cmd.startsWith("article modify")) {
			int id = Integer.parseInt(cmd.split(" ")[2]);
			System.out.printf("== %d번 게시글 수정 ==\n", id);
			System.out.printf("새 제목 : ");
			String title = sc.nextLine();
			System.out.printf("새 내용 : ");
			String body = sc.nextLine();

			SecSql sql = new SecSql();

			sql.append("UPDATE article");
			sql.append("SET updateDate = NOW()");
			sql.append(", title = ?", title);
			sql.append(", `body` = ?", body);
			sql.append(" WHERE id = ?", id);

			DBUtil.update(conn, sql);
			System.out.printf("%d번 게시글이 수정되었습니다.\n", id);

		}

		else if (cmd.equals("list"))

		{
			List<Article> articles = new ArrayList<>();
			SecSql sql = new SecSql();

			sql.append("SELECT *");
			sql.append("FROM article");
			sql.append("ORDER BY id DESC");

			List<Map<String, Object>> articlesListMap = DBUtil.selectRows(conn, sql);

			for (Map<String, Object> articleMap : articlesListMap) {
				articles.add(new Article(articleMap));
			}
			if (articles.size() == 0) {
				System.out.println("게시물이 존재하지 않습니다.");
				return 0;
			}
			for (Article article : articles) {
				System.out.printf("아이디 : %d   제목 : %s\n", article.id, article.title);
			}

		} else if (cmd.equals("exit"))

		{
			System.out.println("프로그램을 종료합니다.");
			return -1;
		}
		return 0;
	}
}
