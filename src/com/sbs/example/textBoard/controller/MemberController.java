package com.sbs.example.textBoard.controller;

import java.sql.Connection;
import java.util.Scanner;

import com.sbs.example.textBoard.service.MemberService;

public class MemberController extends Controller {
	
	private MemberService memberService;
	
	public MemberController(Connection conn, Scanner sc) {
		super(sc);
		memberService = new MemberService(conn);
	}

	public void join (String cmd) {
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
			
			boolean isLoginIdDup = memberService.isLoginIdDup(loginId);
			
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

		memberService.join(loginId, loginPw, name);

		System.out.printf("%s님 환영합니다.\n", name);
	}

}
