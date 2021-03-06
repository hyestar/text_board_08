package com.sbs.example.textBoard.controller;

import java.util.List;

import com.sbs.example.textBoard.Container;
import com.sbs.example.textBoard.dto.Article;
import com.sbs.example.textBoard.service.ArticleService;

public class ArticleController extends Controller {
	
	private ArticleService articleService;
	
	public ArticleController() {
		articleService = Container.articleService;
	}
	
	public void add(String cmd) {
		System.out.printf("제목 : ");

		String title = sc.nextLine();
		System.out.printf("내용 : ");
		String body = sc.nextLine();

		int id = articleService.add(title, body);
		
		System.out.printf("%d번 게시물이 생성되었습니다.\n", id);
	}
	
	public void delete(String cmd) {
		int id = Integer.parseInt(cmd.split(" ")[2]);

		System.out.printf("== %d번 게시글 삭제 ==\n", id);

		boolean articleExists = articleService.articleExists(id);

		if (articleExists == false) {
			System.out.printf("%d번 게시글은 존재하지 않습니다.\n", id);
			return;
		}
		
		articleService.delete(id);

		System.out.printf("%d번 게시글이 삭제되었습니다.\n", id);
	}
	public void listDetail(String cmd) {
		int id = Integer.parseInt(cmd.split(" ")[2]);

		System.out.printf("== %d번 게시글 상세보기 ==\n", id);

		Article article = articleService.getArticleById(id);

		if (article == null) {
			System.out.printf("%d번 게시글은 존재하지 않습니다.\n", id);
			return;
		}

		System.out.printf("번호 : %d\n", article.id);
		System.out.printf("작성날짜 : %s\n", article.regDate);
		System.out.printf("수정날짜 : %s\n", article.updateDate);
		System.out.printf("제목 : %s\n", article.title);
		System.out.printf("내용 : %s\n", article.body);
	}
	
	public void modify(String cmd) {
		int id = Integer.parseInt(cmd.split(" ")[2]);
		System.out.printf("== %d번 게시글 수정 ==\n", id);
		System.out.printf("새 제목 : ");
		String title = sc.nextLine();
		System.out.printf("새 내용 : ");
		String body = sc.nextLine();

		articleService.update(id, title, body);
		
		System.out.printf("%d번 게시글이 수정되었습니다.\n", id);

	}
	public void list(String cmd) {
		
		List<Article> articles = articleService.getArticles();
		
		if (articles.size() == 0) {
			System.out.println("게시물이 존재하지 않습니다.");
			return;
		}
		for (Article article : articles) {
			System.out.printf("아이디 : %d   제목 : %s\n", article.id, article.title);
		}
	}
}
