package com.sbs.example.textBoard.dao;

import java.util.Map;

import com.sbs.example.textBoard.Container;
import com.sbs.example.textBoard.dto.Member;
import com.sbs.example.textBoard.util.DBUtil;
import com.sbs.example.textBoard.util.SecSql;

public class MemberDao {

	public boolean isLoginIdDup(String loginId) {
		SecSql sql = new SecSql();

		sql.append("SELECT COUNT(*) > 0");
		sql.append("FROM `member`");
		sql.append("WHERE loginId = ?", loginId);

		return DBUtil.selectRowBooleanValue(Container.conn, sql);

	}

	public int join(String loginId, String loginPw, String name) {
		SecSql sql = new SecSql();

		sql.append("INSERT INTO `member`");
		sql.append("SET regDate = NOW()");
		sql.append(", updateDate = NOW()");
		sql.append(", loginId = ?", loginId);
		sql.append(", loginPw = ?", loginPw);
		sql.append(", `name` = ?", name);

		int id = DBUtil.insert(Container.conn, sql);

		return id;

	}
	public Member getMemberByLoginId(String loginId) {
		SecSql sql = new SecSql();

		sql.append("SELECT *");
		sql.append("FROM `member`");
		sql.append("WHERE loginId = ?", loginId);

		Map<String, Object> memberMap = DBUtil.selectRow(Container.conn, sql);

		if (memberMap.isEmpty()) {
			return null;
		}

		return new Member(memberMap);
	}
}

