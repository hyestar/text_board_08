package com.sbs.example.textBoard;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		List<Article> articles = new ArrayList<>();
		int lastArticleId = 0;
		
		while(true) {
			System.out.printf("명령어를 입력해주세요) ");
			String cmd = sc.nextLine().trim();
			
			if(cmd.equals("exit")) {
				System.out.println("프로그램을 종료합니다.");
				break;
			}
			
			else if(cmd.equals("add")) {
				int id = lastArticleId + 1;
				System.out.print("제목 : ");
				String title = sc.nextLine();
				System.out.print("내용 : ");
				String body = sc.nextLine();
				
				Article article = new Article(id, title, body);
				articles.add(article);
				lastArticleId++;
				
				System.out.println(article);
				
			}
			else if(cmd.equals("list")) {
				if(articles.size()==0) {
					System.out.println("게시물이 존재하지 않습니다.");
					continue;
				}
				for(Article article : articles) {
					System.out.printf("아이디 : %d   제목 : %s\n", article.id, article.title);
				}
				
			}
		}

	}

}
