<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	pageContext.setAttribute("newLine", "\n");
%> 
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="/mysite/assets/css/board.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url='/WEB-INF/views/include/header.jsp'/>
		<div id="content">
			<div id="board">
				<form id="search_form" action="" method="post">
					<input type="text" id="kwd" name="kwd" value="">
					<input type="submit" value="찾기">
				</form>
				<form method="post" action="/mysite/board">
					<table class="tbl-ex">
						<tr>
							<th>번호</th>
							<th>제목</th>
							<th>글쓴이</th>
							<th>조회수</th>
							<th>작성일</th>
							<th>&nbsp;</th>
						</tr>
						<c:set var="countList" value="${fn:length(list)}"/>
						<c:forEach var='vo' items='${list}' varStatus='s'>
						<tr>
							<td>${countList-s.index}</td>
							<td><a href="/mysite/board?a=view&no=${vo.no}">${vo.title}</a></td>
							<td>${vo.name}</td>
							<td>${vo.viewCount}</td>
							<td>${vo.regDate}</td>
							<c:choose>
								<c:when test='${empty sessionScope.authUser}'>
									<td></td>
								</c:when>
								<c:otherwise>
									<c:choose>
										<c:when test='${vo.userNo == authUser.no}'>
											<td><a href="/mysite/board?a=delete&no=${vo.no}" class="del">삭제</a></td>
										</c:when>
										<c:otherwise>
											<td></td>
										</c:otherwise>
									</c:choose>
								</c:otherwise>
							</c:choose>
						</tr>
						</c:forEach>
					</table>
				</form>
				<!-- begin:paging -->
            	<div class="pager">
            	${pageGroupTotal}${total}
            	<br>
               		<ul>
                  		<li><a href="/mysite/board?a=list&page=${beginPage-1}">◀</a></li>                  
                  		<c:forEach begin='1' end='${endPage}' step='1' var='i'>
                  			<c:choose>
                  				<c:when test='${currentPage == i}'>
                  					<li class="selected">${i}</li>
                  				</c:when>
                  				<c:otherwise>
                  					<li><a href="/mysite/board?a=list&page=${i}">${i}</a></li>
                  				</c:otherwise>
                  			</c:choose>
                  		</c:forEach>
                  			<li><a href="/mysite/board?a=list&page=${endPage+1}">▶</a></li>                  		
               		</ul>
            	</div>
            	<!-- end:paging -->
            	<c:if test = '${not empty sessionScope.authUser}'>
            		<div class="bottom">
               			<a href="/mysite/board?a=writeform" id="new-book">글쓰기</a>
            		</div>
            	</c:if>
         	</div>
      	</div>
		<c:import url='/WEB-INF/views/include/navi.jsp'/>
		<c:import url='/WEB-INF/views/include/footer.jsp'/>
	</div>
</body>
</html>