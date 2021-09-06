package com.sbs.example.textBoard.service;

import java.sql.Connection;
import java.util.List;

import com.sbs.example.textBoard.dao.ArticleDao;
import com.sbs.example.textBoard.dto.Article;

public class ArticleService {

	private ArticleDao articleDao;

	public ArticleService(Connection conn) {
		
		articleDao = new ArticleDao(conn);
	}
	
	public int add(String title, String body) {

		return articleDao.add(title,body);
	}

	public boolean articleExists(int id) {

		return articleDao.articleExists(id);
	}

	public void delete(int id) {
		
		articleDao.delete(id);

	}

	public Article getArticleById(int id) {
		
		return articleDao.getArticleById(id);
	}
	
	public void update(int id, String title, String body) {
		articleDao.update(id,title,body);
	}

	public List<Article> getArticles() {
		return articleDao.getArticles();
	}

}
