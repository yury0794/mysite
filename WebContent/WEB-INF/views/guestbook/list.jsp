<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="kr.ac.sungkyul.mysite.vo.GuestBookVo"%>
<%@page import="java.util.List"%>
<%
	List<GuestBookVo> list = (List<GuestBookVo>) request.getAttribute("list");
%>
<!doctype html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="/mysite/assets/css/guestbook.css" rel="stylesheet"
	type="text/css">
</head>
<body>
	<div id="container">
		<jsp:include page="/WEB-INF/views/include/header.jsp"></jsp:include>
		<div id="content">
			<div id="guestbook">
				<form action="/mysite/gb" method="post">
					<input type="hidden" name="a" value="add">
					<table>
						<tr>
							<td>이름</td>
							<td><input type="text" name="name"></td>
							<td>비밀번호</td>
							<td><input type="password" name="password"></td>
						</tr>
						<tr>
							<td colspan=4><textarea name="content" id="content"></textarea></td>
						</tr>
						<tr>
							<td colspan=4 align=right><input type="submit" VALUE=" 확인 "></td>
						</tr>
					</table>
				</form>

				<ul>
					<%
						int size = list.size();
						int index = 0;
						for (GuestBookVo vo : list) {
					%>
					<li>
						<table>
							<tr>
								<td>[<%=size - index%>]
								</td>
								<td><%=vo.getName()%></td>
								<td><%=vo.getRegDate()%></td>
								<td><a href="/mysite/gb?a=deleteform&no=<%=vo.getNo()%>">삭제</a></td>
							</tr>
							<tr>
								<td colspan=4><%=vo.getContent().replaceAll("\n", "<br>")%></td>
							</tr>
						</table> <br>
					</li>
					<%
						index++;
						}
					%>
				</ul>

			</div>
		</div>
		<jsp:include page="/WEB-INF/views/include/navi.jsp"></jsp:include>
		<jsp:include page="/WEB-INF/views/include/footer.jsp"></jsp:include>
	</div>
</body>
</html>