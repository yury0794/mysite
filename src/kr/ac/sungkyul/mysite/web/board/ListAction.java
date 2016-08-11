package kr.ac.sungkyul.mysite.web.board;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.ac.sungkyul.mysite.dao.BoardDao;
import kr.ac.sungkyul.mysite.vo.BoardVo;
import kr.ac.sungkyul.web.Action;
import kr.ac.sungkyul.web.WebUtil;

public class ListAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String page = request.getParameter("page");
		String keyword = request.getParameter("keyword");
		
		if (page == null || "".equals(page)) {
			page = "1";
		}
		
		BoardDao dao = new BoardDao();		
		List<BoardVo> list = null;
		int count = 0;
		int row = 5;
		
		count = dao.getList(keyword);
		list = dao.getList(Integer.parseInt(page), row, keyword);
		
		int totalPage = 1;
		int currentPage = Integer.parseInt(page);

		if (count % row != 0) {
			totalPage = count / row + 1;
		} else {
			totalPage = count / row;
		}

		int pageGroupNum = 1;
		int pageGroup = 5;
		int beginPage = 1;
		int endPage = 1;
		
		pageGroupNum = (int) Math.ceil((double)currentPage/pageGroup);
		
		if(pageGroupNum<1){
			pageGroupNum = pageGroupNum+1;
		}
		
		beginPage = (pageGroupNum - 1) * pageGroup + 1;
		endPage = pageGroupNum * pageGroup;
		
		if (totalPage < endPage) {
			endPage = totalPage;
		}

		// request 범위(scope)에 list 객체를 저장
		request.setAttribute("totalPage", totalPage);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("beginPage", beginPage);
		request.setAttribute("endPage", endPage);
		request.setAttribute("list", list);
		request.setAttribute("keyword", keyword);
		

		WebUtil.forward("/WEB-INF/views/board/list.jsp", request, response);
	}
}