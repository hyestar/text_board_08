// ctrl + shift + f 정렬
package com.sbs.example.textBoard.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JDBCInsertTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
			Connection conn = null;
			PreparedStatement pstat = null;

			try {
				Class.forName("com.mysql.jdbc.Driver");
				String url = "jdbc:mysql://127.0.0.1:3306/text_board?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";

				conn = DriverManager.getConnection(url, "root", "");
				System.out.println("연결 성공!");
				
				
				String sql = "INSERT INTO article"; 
				sql +=	" SET regDate = NOW()";
				sql +=	", updateDate = NOW()" ;
				sql +=	", title = CONCAT('제목', ROUND(RAND()*10))";
				sql +=	", `body` = CONCAT('내용', ROUND(RAND()*10));";
				
				pstat = conn.prepareStatement(sql);
				int affectedRows = pstat.executeUpdate();
				
				System.out.println("affectedRows : " + affectedRows);

			} catch (ClassNotFoundException e) {
					System.out.println("드라이버 로딩 실패");
			} catch (SQLException e) {
					System.out.println("에러: " + e);
			} finally {
				try {
					if (conn != null && !conn.isClosed()) {
						conn.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				try {
					if (pstat != null && !pstat.isClosed()) {
						pstat.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
		
	}

}
