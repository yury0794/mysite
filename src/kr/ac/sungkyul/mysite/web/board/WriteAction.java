package kr.ac.sungkyul.mysite.web.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.ac.sungkyul.mysite.dao.BoardDao;
import kr.ac.sungkyul.mysite.vo.BoardVo;
import kr.ac.sungkyul.mysite.vo.UserVo;
import kr.ac.sungkyul.web.Action;
import kr.ac.sungkyul.web.WebUtil;

public class WriteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 인증 여부 확인
		HttpSession session = request.getSession();
		if (session == null) {
			WebUtil.redirect("/mysite/main", request, response);
			return;
		}

		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if (authUser == null) {
			WebUtil.redirect("/mysite/main", request, response);
			return;
		}

		Long userNo = authUser.getNo();
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String pNo = request.getParameter("pNo");
		String groupNo = request.getParameter("groupNo");
		String depth = request.getParameter("depth");

		BoardDao dao = new BoardDao();
		BoardVo vo;
		if (pNo == null || "".equals(pNo)) {
			vo = new BoardVo();
			vo.setUserNo(userNo);
			vo.setTitle(title);
			vo.setContent(content);
			dao.insert(vo);
		} else {
			vo = new BoardVo();
			vo.setUserNo(userNo);
			vo.setTitle(title);
			vo.setContent(content);
			vo.setGroupNo(Long.parseLong(groupNo));
			vo.setDepth(Long.parseLong(depth));
			dao.replyInsert(vo);
		}

		WebUtil.redirect("/mysite/board?a=list", request, response);
	}
}