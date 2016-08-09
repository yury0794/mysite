package kr.ac.sungkyul.mysite.web.guestbook;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.ac.sungkyul.mysite.dao.GuestBookDao;
import kr.ac.sungkyul.mysite.vo.GuestBookVo;
import kr.ac.sungkyul.web.Action;
import kr.ac.sungkyul.web.WebUtil;

public class ListAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		GuestBookDao dao = new GuestBookDao();
		List<GuestBookVo> list = dao.getList();

		// request 범위(scope)에 list 객체를 저장
		request.setAttribute("list", list);

		WebUtil.forward("/WEB-INF/views/guestbook/list.jsp", request, response);
	}
}