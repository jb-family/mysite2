package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javaex.dao.BoardDao;
import com.javaex.dao.UserDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.BoardVo;
import com.javaex.vo.UserVo;

@WebServlet("/bcr")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		
		if("list".equals(action)) {
			System.out.println("BoardController > list");
			
			//Dao만들기
			BoardDao boardDao = new BoardDao();
			List<BoardVo> bList = boardDao.getList();
			
			// request의 attribute에 bList의 정보를 넣어서 포워딩
			request.setAttribute("bList", bList);
			
			
			WebUtil.forward(request, response, "/WEB-INF/views/board/list.jsp");
		}else if("writeForm".equals(action)) {
			System.out.println("BoardController > writeForm");			
			
			// 로그인한 사용자의 no 값을 세션에서 가져오기
			HttpSession session = request.getSession();
			UserVo authUser = (UserVo) session.getAttribute("authUser");
			
			//파라미터 가져오기
			int no = authUser.getNo();
			
			// no으로 사용자 정보 가져오기
			UserDao userDao = new UserDao();
			UserVo userVo = userDao.getUser(no);
			
			// request의 attribute에 userVo의 정보를 넣어서 포워딩
			request.setAttribute("userVo", userVo);
			
			
			WebUtil.forward(request, response, "/WEB-INF/views/board/writeForm.jsp");
		}else if("write".equals(action)) {
			System.out.println("BoardController > write");
			
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			int hit = Integer.parseInt(request.getParameter("hit"));
			int no = Integer.parseInt(request.getParameter("no"));
			
			//Vo만들기
			BoardVo boardVo = new BoardVo();			
			boardVo.setTitle(title); 
			boardVo.setContent(content); 
			boardVo.setHit(hit);
			boardVo.setUserNo(no);
			
			//Dao만들기
			BoardDao boardDao = new BoardDao();
			boardDao.insert(boardVo);
			
			WebUtil.redirect(request, response, "/mysite2/bcr?action=list");
		}
		else if("delete".equals(action)) {
			System.out.println("BoardController > delete");
			
			// 로그인한 사용자의 no 값을 세션에서 가져오기
			HttpSession session = request.getSession();
			UserVo userVo = (UserVo)session.getAttribute("authUser");
			
			//users.no
			int no = userVo.getNo();
			System.out.println(no);
			
			//파라미터 가져오기
			int userNo = Integer.parseInt(request.getParameter("userNo"));
			int boardNo = Integer.parseInt(request.getParameter("boardNo"));
			System.out.println(userNo);
			System.out.println(boardNo);
			
			//Vo생성
			BoardVo boardVo = new BoardVo();
			boardVo.setUserNo(userNo);
			boardVo.setNo(boardNo);
			
			//Dao생성
			BoardDao boardDao = new BoardDao();
			
			if(no == userNo) {
				boardDao.delete(boardVo);
				WebUtil.redirect(request, response, "/mysite2/bcr?action=list");	
			}else {
				System.out.println("삭제가 불가능합니다.");
				WebUtil.redirect(request, response, "/mysite2/bcr?action=list");	
			}
			
		}else if("read".equals(action)) {
			System.out.println("BoardController > read");
			
			//파라미터 가져오기
			String title = request.getParameter("title");
			int hit = Integer.parseInt(request.getParameter("hit"));
			//Dao만들기
			BoardDao boardDao = new BoardDao();
			BoardVo bVo = boardDao.getList(title);
			
			/*
			//조회수 증가
			if("read".equals(action)) {
				hit++;
				System.out.println(hit);
			}
			
			request.setAttribute("hit", hit);
			*/
			request.setAttribute("bVo", bVo);
			WebUtil.forward(request, response, "/WEB-INF/views/board/read.jsp");
			
		}else if("modifyForm".equals(action)) {
			System.out.println("BoardController > modifyForm");
			
			
			
			
			WebUtil.forward(request, response, "/WEB-INF/views/board/modifyForm.jsp");
		}
		
	}


	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
