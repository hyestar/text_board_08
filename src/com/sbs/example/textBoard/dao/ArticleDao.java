package com.sbs.example.textBoard.dao;

import java.sql.Connection;
import java.util.Map;

import com.sbs.example.textBoard.Article;
import com.sbs.example.textBoard.util.DBUtil;
import com.sbs.example.textBoard.util.SecSql;

public class ArticleDao {
	private Connection conn;
	
	public ArticleDao(Connection conn) {
		this.conn = conn;
	}
	
	public int add(String title, String body) {
		SecSql sql = new SecSql();

		sql.append("INSERT INTO article");
		sql.append("SET regDate = NOW()");
		sql.append(", updateDate = NOW()");
		sql.append(", title = ?", title);
		sql.append(", `body` = ?", body);

		int id = DBUtil.insert(conn, sql);

		return id;
	}
	
	public boolean articleExists(int id) {

		SecSql sql = new SecSql();

		sql.append("SELECT COUNT(*) > 0");
		sql.append("FROM article");
		sql.append("WHERE id = ?", id);

		return DBUtil.selectRowBooleanValue(conn, sql);

	}
	
	public void delete(int id) {
		SecSql sql = new SecSql();
		sql = new SecSql();
		sql.append("DELETE FROM article");
		sql.append("WHERE id = ?", id);
		DBUtil.delete(conn, sql);
	}
	
	public Article getArticleById(int id) {
		SecSql sql = new SecSql();
		sql.append("SELECT *");
		sql.append("FROM article");
		sql.append("WHERE id = ?", id);
		Map<String, Object> articleMap = DBUtil.selectRow(conn, sql);
		
		if(articleMap.isEmpty()) {
			return null;
		}
		
		return new Article(articleMap);
		
	}
}
