<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>맙티</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/resources/css/community/write.css">
<script
	src="<%=request.getContextPath()%>/resources/js/jquery-3.6.0.min.js"
	defer></script>
<script src="<%=request.getContextPath()%>/resources/js/community/write.js" defer></script>
</head>

<body>
	<!-- // 기본양식 -->
	<jsp:include page="/resources/incl/header.jsp"></jsp:include>
	<jsp:include page="/resources/incl/nav.jsp"></jsp:include>
	<div id="main">
		<!-- 작성 구역 -->
		<div id="writeWrap">
			<span id="writeTitle">글쓰기</span>
			<form action="successEdit" method="POST">
				<input type="text" name="boardId" value="${cb.id}" class="hidden">
				<span id="errorMsg1"></span><input type="text" id="title"
					name="title" placeholder="제목" value="${cb.title}"/><span id="typingCount"></span><span
					id="errorMsg2"></span>
				<textarea id="contents" name="contents"
					oninput="typingContents(this.form)" placeholder="내용">${cb.contents}</textarea>
				<a href="mainCommunity?type=reportingDate&q=&page=1&range=1" id="goContentsList">취소</a> <input
					type="submit" value="작성완료"
					onclick="editBoardSubmit(this.form); return false;">
			</form>
		</div>
	</div>
	<!-- 기본양식 // -->
</body>
</html>