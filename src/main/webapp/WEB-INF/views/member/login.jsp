<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<meta charset="UTF-8">
<title>맙티</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/resources/css/member/login.css">
<script
	src="<%=request.getContextPath()%>/resources/js/jquery-3.6.0.min.js"
	defer></script>
</head>
<body>
	<jsp:include page="/resources/incl/nav.jsp"></jsp:include>
	<div id="main">
		<h1>Login</h1>
		<h2>${errorMsg}</h2>
		<div id="login_wrap">
			<form action="login" method="post">
				<table>
					<tr>
						<th>ID</th>
						<td><input type="text" placeholder="아이디를 입력하세요." name="email"<%-- value="${sessionScope.memberInfo.email}" --%>></td>
					</tr>
					<tr>
						<th>PW</th>
						<td><input type="password" placeholder="비밀번호를 입력하세요."
							name="pw"></td>
					</tr>
				</table>
				<div id="login_btn">
					<a href="/myapp/index">홈으로</a> <input type="submit" value="로그인"
						onclick="loginSubmit(this.form); return false" id="submit">
				</div>
			</form>
		</div>
		<div id="addMember">
			<a href="/myapp/member/addMember">회원가입</a>
		</div>
		<div id="isfp">
			<img
				src="<%=request.getContextPath()%>/resources/img/avatar/MBTI_ISFP.png"
				alt="isfp" class="avatar avatar14 isfp"></img>
		</div>
		<div id="loginImg"></div>
	</div>

</body>
</html>