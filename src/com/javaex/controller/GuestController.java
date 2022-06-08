package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.GuestDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.GuestVo;

@WebServlet("/guest")
public class GuestController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		
		if ("addList".equals(action)) {
			System.out.println("GuestController>addList");

			// 데이터 가져오기
			GuestDao guestDao = new GuestDao();
			List<GuestVo> guestList = guestDao.guestSelect();
			// request에 데이터 추가
			request.setAttribute("gList", guestList);

			WebUtil.forward(request, response, "WEB-INF/views/guestbook/addList.jsp");
		} else if ("add".equals(action)) {
			System.out.println("GuestController>add");

			String name = request.getParameter("name");
			String password = request.getParameter("password");
			String content = request.getParameter("content");

			GuestVo guestVo = new GuestVo();
			guestVo.setName(name);
			guestVo.setPassword(password);
			guestVo.setContent(content);
			GuestDao guestDao = new GuestDao();

			int count = guestDao.insert(guestVo);
			System.out.println(count);

			WebUtil.redirect(request, response, "/mysite2/guest?action=addList");
		} else if ("deleteForm".equals(action)) {
			System.out.println("GuestController>deleteForm");

			WebUtil.forward(request, response, "WEB-INF/views/guestbook/deleteForm.jsp");
		} else if ("delete".equals(action)) {
			System.out.println("GuestController>delete");

			int no = Integer.parseInt(request.getParameter("no"));
			String password = request.getParameter("password");

			GuestDao guestDao = new GuestDao();
			GuestVo guestVo = guestDao.guestSelect(no);

			if (guestVo.getPassword().equals(password)) {
				int count = guestDao.delete(guestVo);
				System.out.println(count);
				WebUtil.redirect(request, response, "/mysite2/guest?action=addList");
			} else {
				WebUtil.redirect(request, response, "/mysite2/guest?action=addList");
			}
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
