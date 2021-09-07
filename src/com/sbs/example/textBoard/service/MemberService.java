package com.sbs.example.textBoard.service;

import com.sbs.example.textBoard.Container;
import com.sbs.example.textBoard.dao.MemberDao;
import com.sbs.example.textBoard.dto.Member;

public class MemberService {
	private MemberDao memberDao;

	public MemberService() {
		memberDao = Container.memberDao;
	}

	public boolean isLoginIdDup(String loginId) {
		return memberDao.isLoginIdDup(loginId);
	}

	public int join(String loginId, String loginPw, String name) {

		return memberDao.join(loginId, loginPw, name);

	}
	
	public Member getMemberByLoginId(String loginId) {
		return memberDao.getMemberByLoginId(loginId);
	}
}
