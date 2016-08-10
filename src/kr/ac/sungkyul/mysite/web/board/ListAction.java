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
		BoardDao dao = new BoardDao();
		List<BoardVo> list = dao.getList();

		String page = request.getParameter("page");

		if (page == null || "".equals(page)) {
			page = "1";
		}

		int row = 5;
		list = dao.getList(Integer.parseInt(page), row);
		
		int pageGroup = 5;
		int total=1;
		int pageGroupTotal = 1;
		int currentPage = Integer.parseInt(page);
		
		if(list.size()%row<list.size()/row){
			total = list.size()/row + 1;
		} else{
			total = list.size()/row;
		}
		
		if(total%pageGroup<total/pageGroup){
			pageGroupTotal = total/pageGroup + 1;
		} else{
			pageGroupTotal = total/pageGroup;
		}
		
		int beginPage = (pageGroupTotal-1)*pageGroup+1;
		int endPage = pageGroupTotal*pageGroup;

		// request 범위(scope)에 list 객체를 저장
		request.setAttribute("pageGroupTotal", pageGroupTotal);
		request.setAttribute("total", total);
		request.setAttribute("currentPage", currentPage);		
		request.setAttribute("beginPage", beginPage);
		request.setAttribute("endPage", endPage);
		request.setAttribute("list", list);

		WebUtil.forward("/WEB-INF/views/board/list.jsp", request, response);
	}
}