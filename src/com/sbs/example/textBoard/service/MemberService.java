package com.sbs.example.textBoard.service;

import java.sql.Connection;

import com.sbs.example.textBoard.dao.MemberDao;

public class MemberService {
	private MemberDao memberDao;

	public MemberService(Connection conn) {
		memberDao = new MemberDao(conn);
	}

	public boolean isLoginIdDup(String loginId) {
		return memberDao.isLoginIdDup(loginId);
	}

	public int join(String loginId, String loginPw, String name) {

		return memberDao.join(loginId, loginPw, name);

	}
}
