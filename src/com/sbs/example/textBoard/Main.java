package com.sbs.example.textBoard;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		List<Article> articles = new ArrayList<>();
		
		while(true) {
			System.out.printf("명령어를 입력해주세요) ");
			String cmd = sc.nextLine().trim();
			
			if(cmd.equals("exit")) {
				System.out.println("프로그램을 종료합니다.");
				break;
			}
			
		}

	}

}
