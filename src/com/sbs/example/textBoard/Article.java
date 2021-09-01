package com.sbs.example.textBoard;

import java.time.LocalDateTime;
import java.util.Map;

public class Article extends Object {
	public int id;
	public LocalDateTime regDate;
	public LocalDateTime updateDate;
	public String title;
	public String body;
	
	public Article(int id, String title, String body) {
		this.id = id;
		this.title = title;
		this.body = body;
	}
	
	public Article(int id, LocalDateTime regDate, LocalDateTime updateDate, String title, String body) {
		this.id = id;
		this.regDate = regDate;
		this.updateDate = updateDate;
		this.title = title;
		this.body = body;
	}

	public Article(Map<String, Object> articleMap) {
		this.id = (int) articleMap.get("id");
		this.title = (String) articleMap.get("title");
		this.body = (String) articleMap.get("body");
		this.regDate = (LocalDateTime) articleMap.get("regDate");
		this.updateDate = (LocalDateTime) articleMap.get("updateDate");
	}

	@Override
	public String toString() {
		return "Article [id=" + id + ", regDate=" + regDate + ", updateDate=" + updateDate + ", title=" + title
				+ ", body=" + body + "]";
	}

	
	
}
