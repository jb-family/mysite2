package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.BoardVo;
import com.javaex.vo.GuestVo;
import com.javaex.vo.UserVo;

public class BoardDao {
	//0. import java.sql.*;
			private Connection conn = null;
			private PreparedStatement pstmt= null;
			private ResultSet rs= null;
			
			private String driver = "oracle.jdbc.driver.OracleDriver";
			private String url = "jdbc:oracle:thin:@localhost:1521:xe";
			private String id = "webdb";
			private String pw = "webdb";
			
		//메소드 드라이버 연결
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
			
			//회원가입 --> 회원정보 저장
			public int insert(BoardVo boardVo) {

				int count = -1;
				getConnecting();
				try {
					// 3. SQL문준비/ 바인딩/ 실행
					// SQL문준비
					String query = "";
					query += " insert into board ";
					query += " values(seq_board_no.nextval, ?, ?, ?, SYSDATE, ?) ";
					
					
					// 바인딩
					pstmt = conn.prepareStatement(query);
					pstmt.setString(1, boardVo.getTitle());
					pstmt.setString(2, boardVo.getContent());
					pstmt.setInt(3, boardVo.getHit());
					pstmt.setInt(4, boardVo.getUserNo());
					
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
			
			public List<BoardVo> getList() {
				List<BoardVo> boardList = new ArrayList<BoardVo>();
				getConnecting();
				try {

					// 3. SQL문준비/ 바인딩/ 실행
					// SQL문준비
					String query = "";
					query += " select  board.no ";
					query += "         ,board.title ";
					query += "         ,users.name ";
					query += "         ,board.hit ";
					query += "         ,board.reg_date ";
					query += "         ,board.user_no ";
					query += " from board, users ";
					query += " where board.user_no = users.no ";
					query += " order by board.reg_date desc ";

					// 바인딩
					pstmt = conn.prepareStatement(query);
					
					// 실행
					rs = pstmt.executeQuery();

					// 4.결과처리
					while (rs.next()) {
						int no = rs.getInt(1);
						String title = rs.getString(2);
						String name = rs.getString(3);
						int hit = rs.getInt(4);
						String regDate = rs.getString(5);
						int userNo = rs.getInt(6);
						
						BoardVo boardVo = new BoardVo();
						
						boardVo.setNo(no);
						boardVo.setTitle(title);
						boardVo.setName(name);
						boardVo.setHit(hit);
						boardVo.setRegDate(regDate);
						boardVo.setUserNo(userNo);
						
						boardList.add(boardVo);
					}
				} catch (SQLException e) {
					System.out.println("error:" + e);
				}
				close();

				return boardList;
				
			}
			
			public BoardVo getList(String userTitle) {
				BoardVo boardVo = null;
				
				getConnecting();
				try {
					// 3. SQL문준비/ 바인딩/ 실행
					// SQL문준비
					String query = "";
					query += " select  board.title ";
					query += "         ,board.no ";
					query += "         ,users.name ";
					query += "         ,board.hit ";
					query += "         ,board.reg_date ";
					query += "         ,board.content ";
					query += "         ,board.user_no ";
					query += " from board, users ";
					query += " where board.user_no = users.no ";
					query += " and     board.title = ? ";

					// 바인딩
					pstmt = conn.prepareStatement(query);
					pstmt.setString(1, userTitle);
					
					// 실행
					rs = pstmt.executeQuery();

					// 4.결과처리
					while (rs.next()) {
						String title = rs.getString(1);
						int no = rs.getInt(2);
						String name = rs.getString(3);
						int hit = rs.getInt(4);
						String regDate = rs.getString(5);
						String content = rs.getString(6);
						int userNo = rs.getInt(7);
						
						boardVo = new BoardVo();
						
						boardVo.setNo(no);
						boardVo.setTitle(title);
						boardVo.setName(name);
						boardVo.setHit(hit);
						boardVo.setRegDate(regDate);
						boardVo.setContent(content);
						boardVo.setUserNo(userNo);

					}

				} catch (SQLException e) {
					System.out.println("error:" + e);
				}
				close();
				
				return boardVo;
			}
			
			public BoardVo getList(int no) {
				BoardVo boardVo = null;
				
				getConnecting();
				try {
					// 3. SQL문준비/ 바인딩/ 실행
					// SQL문준비
					String query = "";
					query += " select  board.title ";
					query += "         ,board.no ";
					query += "         ,users.name ";
					query += "         ,board.hit ";
					query += "         ,board.reg_date ";
					query += "         ,board.content ";
					query += "         ,board.user_no ";
					query += " from board, users ";
					query += " where board.user_no = users.no ";
					query += " and     board.no = ? ";

					// 바인딩
					pstmt = conn.prepareStatement(query);
					pstmt.setInt(1, no);
					
					// 실행
					rs = pstmt.executeQuery();

					// 4.결과처리
					while (rs.next()) {
						String title = rs.getString(1);
						int no1 = rs.getInt(2);
						String name = rs.getString(3);
						int hit = rs.getInt(4);
						String regDate = rs.getString(5);
						String content = rs.getString(6);
						int userNo = rs.getInt(7);
						
						boardVo = new BoardVo();
						
						boardVo.setNo(no1);
						boardVo.setTitle(title);
						boardVo.setName(name);
						boardVo.setHit(hit);
						boardVo.setRegDate(regDate);
						boardVo.setContent(content);
						boardVo.setUserNo(userNo);

					}

				} catch (SQLException e) {
					System.out.println("error:" + e);
				}
				close();
				
				return boardVo;
			}
			
			
			
			public int delete(BoardVo boardVo) {
				int count = -1;
				getConnecting();
				try {
				
				// 3. SQL문준비/ 바인딩/ 실행
				//SQL문준비
				String query= "";
				query += " delete from board ";
				query += " where user_no = ? ";
				query += " and no = ? ";
				
				//바인딩
				pstmt = conn.prepareStatement(query);
				pstmt.setInt(1, boardVo.getUserNo());
				pstmt.setInt(2, boardVo.getNo());
				
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
			
			public int update(BoardVo boardVo) {
				int count = -1;
				getConnecting();
				try {
					// 3. SQL문준비/ 바인딩/ 실행
					// SQL문준비
					String query = "";
					query += " UPDATE board ";
					query += " SET title = ? ";
					query += "     ,content = ? ";
					query += " where no = ? ";
					
					// 바인딩
					pstmt = conn.prepareStatement(query);
					pstmt.setString(1, boardVo.getTitle());
					pstmt.setString(2, boardVo.getContent());
					pstmt.setInt(3, boardVo.getNo());

					// 실행
					count = pstmt.executeUpdate();
					// 4.결과처리

					System.out.println(count + "건이 변경되었습니다.");
				} catch (SQLException e) {
					System.out.println("error:" + e);
				}
				close();

				return count;
			}
			
			
			public int hitUpdate(int no) {
				int count = -1;
				getConnecting();
				try {
					// 3. SQL문준비/ 바인딩/ 실행
					// SQL문준비
					String query = "";
					query += " UPDATE board ";
					query += " SET hit = hit + 1 ";
					query += " where no = ? ";
					
					// 바인딩
					pstmt = conn.prepareStatement(query);
				//	pstmt.setInt(1, boardVo.getHit());
					pstmt.setInt(1, no);

					// 실행
					count = pstmt.executeUpdate();
					// 4.결과처리

					System.out.println(count + "건이 변경되었습니다.");
				} catch (SQLException e) {
					System.out.println("error:" + e);
				}
				close();

				return count;
			}
			
			
			
}
