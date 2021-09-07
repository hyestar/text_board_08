package com.sbs.example.textBoard.controller;

import com.sbs.example.textBoard.Container;
import com.sbs.example.textBoard.dto.Member;
import com.sbs.example.textBoard.service.MemberService;

public class MemberController extends Controller {
	
	private MemberService memberService;
	
	public MemberController() {
		memberService = Container.memberService;
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

	public void login(String cmd) {
		String loginId;
		String loginPw;

		System.out.println("== 로그인 ==");
		// 로그인 아이디 입력
		while (true) {
			System.out.printf("로그인 아이디 : ");
			loginId = sc.nextLine().trim();

			if (loginId.length() == 0) {
				System.out.println("로그인 아이디를 입력해주세요.");
				continue;
			}

			boolean isLoginIdDup = memberService.isLoginIdDup(loginId);

			if (isLoginIdDup == false) {
				System.out.printf("%s(은)는 존재하지 않는 로그인 아이디입니다.\n", loginId);
				continue;
			}

			break;

		}

		Member member = memberService.getMemberByLoginId(loginId);

		int tryMaxCount = 3;
		int tryCount = 0;

		// 로그인 비밀번호 입력
		while (true) {
			if (tryCount >= tryMaxCount) {
				System.out.println("로그인 시도 횟수를 초과했습니다. 비밀번호 확인 후 다음에 다시 시도해주세요.");
				break;
			}

			System.out.printf("로그인 비밀번호 : ");
			loginPw = sc.nextLine().trim();

			if (loginPw.length() == 0) {
				System.out.println("로그인 비밀번호를 입력해주세요.");
				continue;
			}

			if (member.loginPw.equals(loginPw) == false) {
				tryCount++;
				System.out.println("비밀번호가 일치하지 않습니다.");
				continue;
			}

			System.out.printf("%s님 환영합니다.\n", member.name);

			break;

		}
		
	}

}
