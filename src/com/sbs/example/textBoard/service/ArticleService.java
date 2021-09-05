package com.sbs.example.textBoard.service;

import java.sql.Connection;

import com.sbs.example.textBoard.Article;
import com.sbs.example.textBoard.dao.ArticleDao;

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

}
