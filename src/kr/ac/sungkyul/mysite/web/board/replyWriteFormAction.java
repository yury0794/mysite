package kr.ac.sungkyul.mysite.web.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.ac.sungkyul.mysite.dao.BoardDao;
import kr.ac.sungkyul.mysite.vo.BoardVo;
import kr.ac.sungkyul.web.Action;
import kr.ac.sungkyul.web.WebUtil;

public class replyWriteFormAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pNo = request.getParameter("pNo");
		
		BoardDao dao = new BoardDao();
		BoardVo vo = new BoardVo();
		vo = dao.get(Long.parseLong(pNo));
		
		request.setAttribute("replyVo", vo);
		
		WebUtil.forward("/WEB-INF/views/board/write.jsp", request, response);
	}
}