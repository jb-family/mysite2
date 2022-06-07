package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.GuestVo;

public class GuestDao {
	
		// 0. import java.sql.*;
		private Connection conn = null;
		private PreparedStatement pstmt = null;
		private ResultSet rs = null;
	
		private String driver = "oracle.jdbc.driver.OracleDriver";
		private String url = "jdbc:oracle:thin:@localhost:1521:xe";
		private String id = "webdb";
		private String pw = "webdb";
	
		// 메소드 드라이버 연결
		public void getConnecting() {
			try {
				// 1. JDBC 드라이버 (Oracle) 로딩
				Class.forName(driver);
	
				// 2. Connection 얻어오기
				conn = DriverManager.getConnection(url, id, pw);
			} catch (Exception e) {
				System.out.println("error:" + e);
			}
		}
	
		public void close() {
			// 5. 자원정리
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}
		}
	
		public int insert(GuestVo guestVo) {
			int count = -1;
			getConnecting();
			try {
				// 3. SQL문준비/ 바인딩/ 실행
				// SQL문준비
				String query = "";
				query += " insert into guestbook ";
				query += " values (seq_users_no.nextval, ?, ?, ?, sysdate) ";

				// 바인딩
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, guestVo.getName());
				pstmt.setString(2, guestVo.getPassword());
				pstmt.setString(3, guestVo.getContent());

				// 실행
				count = pstmt.executeUpdate();
				// 4.결과처리

				System.out.println(count + "건이 추가되었습니다.");
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}
			close();

			return count;
		}	
		public int delete(GuestVo guestVo) {
			int count = -1;
			getConnecting();
			try {
			
			// 3. SQL문준비/ 바인딩/ 실행
			//SQL문준비
			String query= "";
			query += " delete from guestbook ";
			query += " where password = ? ";
			query += " and no = ? ";
				
			
			//바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, guestVo.getPassword());
			pstmt.setInt(2, guestVo.getNo());
			
			//실행	
			count = pstmt.executeUpdate();
			
			
			// 4.결과처리
			System.out.println(count + "건이 삭제되었습니다.");
			
			} catch (SQLException e) {
			System.out.println("error:" + e);
			}
			close();
			
			return count;
		}
		
		public int update(GuestVo guestVo) {
			int count = -1;
			getConnecting();
			try {
			
			// 3. SQL문준비/ 바인딩/ 실행
			//SQL문준비
			String query= "";
			query += " update guestbook ";
			query += " set name = ? ";
			query += "     ,password = ? ";
			query += "     ,content = ? ";
			query += "     ,reg_date = ? ";
			query += " where no = ? ";
				
			
			//바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1,guestVo.getName());	
			pstmt.setString(2,guestVo.getPassword());	
			pstmt.setString(3,guestVo.getContent());	
			pstmt.setString(4,guestVo.getRegDate());	
			pstmt.setInt(5,guestVo.getNo());	
			
			//실행	
			count = pstmt.executeUpdate();
			
			// 4.결과처리
			System.out.println(count + "건이 수정되었습니다.");
			
			} catch (SQLException e) {
			System.out.println("error:" + e);
			}
			close();
			
			return count;
		}
		
		public List<GuestVo> guestSelect() {
			List<GuestVo> guestList = new ArrayList<GuestVo>();
			
			getConnecting();
			try {
			
			// 3. SQL문준비/ 바인딩/ 실행
			//SQL문준비
			String query= "";
			query += " select  no ";
			query += "         ,name ";
			query += "         ,password ";
			query += "         ,content ";
			query += "         ,reg_date ";
			query += " from    guestbook ";
				
			//바인딩
			pstmt = conn.prepareStatement(query);
			
			//실행	
			rs = pstmt.executeQuery();
			
			// 4.결과처리
			while(rs.next()) {
				
				int no = rs.getInt("no");
				String name = rs.getString("name");
				String password = rs.getString("password");
				String content = rs.getString("content");
				String regDate = rs.getString("reg_date");
				
				GuestVo guestBookVo = new GuestVo(no, name, password, content, regDate);
				
				guestList.add(guestBookVo);
				
			}
			
			
			} catch (SQLException e) {
			System.out.println("error:" + e);
			}
			close();
			
			
			return guestList;
		}
	
		
		public GuestVo guestSelect(int no) {
			GuestVo guestVo = null;
			
			getConnecting();
			try {
			
			// 3. SQL문준비/ 바인딩/ 실행
			//SQL문준비
			String query= "";
			query += " select  no ";
			query += "         ,name ";
			query += "         ,password ";
			query += "         ,content ";
			query += "         ,reg_date ";
			query += " from    guestbook ";
			query += " where    no = ? ";
				
			//바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, no);
			
			//실행	
			rs = pstmt.executeQuery();
			
			// 4.결과처리
			while(rs.next()) {
				
				int guestNo = rs.getInt("no");
				String name = rs.getString("name");
				String password = rs.getString("password");
				String content = rs.getString("content");
				String regDate = rs.getString("reg_date");
				
				guestVo = new GuestVo(guestNo, name, password, content, regDate);
				
			}
			
			
			} catch (SQLException e) {
			System.out.println("error:" + e);
			}
			close();
			
			
			return guestVo;
		}
		
}
