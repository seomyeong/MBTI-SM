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
	href="<%=request.getContextPath()%>/resources/css/community/mainCommunity.css">
<script
	src="<%=request.getContextPath()%>/resources/js/jquery-3.6.0.min.js"
	defer></script>
<script
	src="<%=request.getContextPath()%>/resources/js/community/mainCommunity.js" defer></script>
</head>

<body>
	<!-- // 기본양식 -->
	<jsp:include page="/resources/incl/header.jsp"></jsp:include>
	<jsp:include page="/resources/incl/nav.jsp"></jsp:include>
	<div id="main">
		<!-- 작성 구역 -->
		<div id="viewName">
			<c:choose>
				<c:when test="${(sessionScope.type eq 'reportingDate')}">
					전체글
				</c:when>
				<c:when test="${(sessionScope.type eq 'mbti')}">
					${sessionScope.q}
				</c:when>
				<c:when test="${(sessionScope.type eq 'likes')}">
					인기순
				</c:when>
				<c:when test="${(sessionScope.type eq 'views')}">
					조회순
				</c:when>
				<c:otherwise>
					"${sessionScope.q}"에 대한 검색결과
				</c:otherwise>
			</c:choose>
		</div>
		<div id="topWrap">
			<div id="searchBar">
				<form>
					<select name="selectType">
						<option>제목</option>
						<option>작성자</option>
					</select> <input type="text" name="searchContents" placeholder="검색" /> <input
						type="submit" value="검색"
						onclick="javascript:search(this.form); return false;" />
				</form>
			</div>

			<form>
				<a id="viewAll" href="mainCommunity_deleteSession">전체</a>
				<a id="viewHot" href="mainCommunity_hot">인기순</a>
				<a id="viewTop" href="mainCommunity_top">조회순</a>
				<div id="typeWrap">
					<div id="type1" class="toggle-switch">
						<input type="checkbox" name="type01" />
						<div class="switch"></div>
						<span class="front">E</span> <span class="back">I</span>
					</div>

					<div id="type2" class="toggle-switch">
						<input type="checkbox" name="type02" />
						<div class="switch"></div>
						<span class="front">N</span> <span class="back">S</span>
					</div>

					<div id="type3" class="toggle-switch">
						<input type="checkbox" name="type03" />
						<div class="switch"></div>
						<span class="front">F</span> <span class="back">T</span>
					</div>

					<div id="type4" class="toggle-switch">
						<input type="checkbox" name="type04" />
						<div class="switch"></div>
						<span class="front">P</span> <span class="back">J</span>
					</div>

				</div>
				<input type="submit" value="필터적용"
					onclick="javascript:checkMbti(); return false;" />
			</form>

		</div>
		<table>
			<tr>
				<th>추천</th>
				<th>MBTI</th>
				<th>작성자</th>
				<th>제목</th>
				<th>작성일</th>
				<th>조회</th>
			</tr>
			<c:forEach var="communityBoard" items="${cbList_hot}">
				<tr class="contents_hot">
					<td class="likes">${communityBoard.likes}</td>
					<td class="mbti"><span class="mbtiImg" style="background: url(${communityBoard.member.profileImg}) 0 0 / cover"></span>${communityBoard.member.mbti}</td>
					<td class="nickName">
					<c:choose>
						<c:when test="${communityBoard.member.level >= 70}">
							<span class="lv70">Lv.${communityBoard.member.level}</span>
						</c:when>
						<c:when test="${communityBoard.member.level < 70 and communityBoard.member.level >= 50}">
							<span class="lv50">Lv.${communityBoard.member.level}</span>
						</c:when>
						<c:when test="${communityBoard.member.level < 50 and communityBoard.member.level >= 30}">
							<span class="lv30">Lv.${communityBoard.member.level}</span>
						</c:when>
						<c:when test="${communityBoard.member.level < 30 and communityBoard.member.level >= 10}">
							<span class="lv10">Lv.${communityBoard.member.level}</span>
						</c:when>
						<c:when test="${communityBoard.member.level < 10}">
							<span class="level">Lv.${communityBoard.member.level}</span>
						</c:when>
					</c:choose>
					 ${communityBoard.member.nickName}</td>
					<td><span class="hotFlag_top">인기</span> <a href="board?boardId=${communityBoard.id}">${communityBoard.title}<span
							class="commentCount"> [${communityBoard.commentsCount}]</span></a></td>
					<td class="reportingDate">${communityBoard.reportingDate}</td>
					<td>${communityBoard.views}</td>
				</tr>
			</c:forEach>
			<c:forEach var="communityBoard" items="${cbList}">
				<tr class="contents">
					<td class="likes">${communityBoard.likes}</td>
					<td class="mbti">${communityBoard.member.mbti}<span class="mbtiImg" style="background: url(${communityBoard.member.profileImg}) 0 0 / cover"></span></td>
					<td class="nickName">
					<c:choose>
						<c:when test="${communityBoard.member.level >= 70}">
							<span class="lv70">Lv.${communityBoard.member.level}</span>
						</c:when>
						<c:when test="${communityBoard.member.level < 70 and communityBoard.member.level >= 50}">
							<span class="lv50">Lv.${communityBoard.member.level}</span>
						</c:when>
						<c:when test="${communityBoard.member.level < 50 and communityBoard.member.level >= 30}">
							<span class="lv30">Lv.${communityBoard.member.level}</span>
						</c:when>
						<c:when test="${communityBoard.member.level < 30 and communityBoard.member.level >= 10}">
							<span class="lv10">Lv.${communityBoard.member.level}</span>
						</c:when>
						<c:when test="${communityBoard.member.level < 10}">
							<span class="level">Lv.${communityBoard.member.level}</span>
						</c:when>
					</c:choose>
					
					 ${communityBoard.member.nickName}</td>
					<td><c:if test="${communityBoard.likes >= 20}"><span class="hotFlag">인기</span> </c:if><a href="board?boardId=${communityBoard.id}">${communityBoard.title}<span
							class="commentCount"> [${communityBoard.commentsCount}]</span></a></td>
					<td class="reportingDate">${communityBoard.reportingDate}</td>
					<td>${communityBoard.views}</td>
				</tr>
			</c:forEach>
		</table>
		<c:if test="${cbList eq '[]'}">
			<div id="noContents"><a href="#" id="noContents_img"></a>결과를 찾지 못했어요!</div>
		</c:if>

		<div id="bottomWrap">
			<div id="paging"
				style="width: ${(sessionScope.pagingVO.endPage - sessionScope.pagingVO.startPage) * 42 + 144}px">
				<c:choose>
					<c:when test="${sessionScope.pagingVO.prev}">
						<a
							href="mainCommunity?type=${sessionScope.type}&q=${sessionScope.q}&page=${sessionScope.pagingVO.startPage-1}&range=${sessionScope.pagingVO.range - 1}">이전</a>
					</c:when>
					<c:otherwise>
						<a href="#" onclick="alert('첫 페이지입니다.');">이전</a>
					</c:otherwise>
				</c:choose>

				<c:forEach var="i" begin="${sessionScope.pagingVO.startPage}"
					end="${sessionScope.pagingVO.endPage}" step="1">
					<c:choose>
						<c:when test="${sessionScope.pagingVO.page eq i}">
							<a
								href="mainCommunity?type=${sessionScope.type}&q=${sessionScope.q}&page=${i}&range=${sessionScope.pagingVO.range}"
								class="now">${i}</a>
						</c:when>
						<c:otherwise>
							<a
								href="mainCommunity?type=${sessionScope.type}&q=${sessionScope.q}&page=${i}&range=${sessionScope.pagingVO.range}">${i}</a>
						</c:otherwise>
					</c:choose>
				</c:forEach>

				<c:choose>
					<c:when test="${sessionScope.pagingVO.next}">
						<a
							href="mainCommunity?type=${sessionScope.type}&q=${sessionScope.q}&page=${sessionScope.pagingVO.endPage+1}&range=${sessionScope.pagingVO.range + 1}">다음</a>
					</c:when>
					<c:otherwise>
						<a href="#" onclick="alert('다음 페이지가 존재하지 않습니다.')">다음</a>
					</c:otherwise>
				</c:choose>
			</div>
			<a href="#" onclick="goWrite();" id="write"
				data-loginId="${sessionScope.loginId}">글쓰기</a>
		</div>

		<!-- 사용한 페이징 값들 -->
		<%-- <% session.invalidate(); %> --%>

		<%-- <form action="" id="testValue">
			<span>page = </span><input type=text
				value="${sessionScope.pagingVO.page}" /><br> <span>range
				= </span><input type=text value="${sessionScope.pagingVO.range}" /><br>
			<span>listCnt = </span><input type=text
				value="${sessionScope.pagingVO.listCnt}" /><br> <span>startPage
				= </span><input type=text value="${sessionScope.pagingVO.startPage}" /><br>
			<span>endPage = </span><input type=text
				value="${sessionScope.pagingVO.endPage}" /><br> <span>startList
				= </span><input type=text value="${sessionScope.pagingVO.startList}" /><br>
			<span>prev = </span><input type=text
				value="${sessionScope.pagingVO.prev}" /><br> <span>next
				= </span><input type=text value="${sessionScope.pagingVO.next}" /><br>
			<span>pageListSize = </span><input type=text
				value="${sessionScope.pagingVO.pageListSize}" /><br> <span>pageCnt
				= </span><input type=text value="${sessionScope.pagingVO.pageCnt}" /><br>
		</form> --%>
	</div>
	<!-- 기본양식 // -->
</body>
</html>