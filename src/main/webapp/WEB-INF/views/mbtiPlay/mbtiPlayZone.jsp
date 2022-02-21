<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>맙티</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/resources/css/mbtiPlay/mbtiPlayZone.css">
<script
	src="<%=request.getContextPath()%>/resources/js/jquery-3.6.0.min.js"></script>
<script
	src="<%=request.getContextPath()%>/resources/js/mbtiPlay/mbtiPlayZone.js" defer></script>

</head>
<body>
	<jsp:include page="/resources/incl/header.jsp"></jsp:include>
	<jsp:include page="/resources/incl/nav.jsp"></jsp:include>
	<div id="main">
		<h2 class="hidden">mbtiPlay</h2>
		<div id="introText">
            <p>다양한 문답을 만날 수 있는 <span>맙티플레이 !</span></p>
            <p>나와 같은 MBTI 유형은 어떤 선택을 했을까요?<br>직접 문답을 만들 수도 있답니다.</p>
            <p>문답 작성 시 30맙 적립, 하루 3번 제한</p>
        </div>
        <div id="deco">
            <div id="deco1"></div>
            <div id="deco2"></div>
            <div id="deco3"></div>
        </div>
        <div id="carouselSlide">
            <input type="radio" name="slider" id="s1" checked>
            <input type="radio" name="slider" id="s2">
            <input type="radio" name="slider" id="s3">
            <input type="radio" name="slider" id="s4">
            <input type="radio" name="slider" id="s5">

            <label for="s1" id="slide1">
                <div class="contentsBox">
                    <p>Q1. 친구들과 놀이동산을 간 당신, 놀이기구를 탈 때의 나는?</p>
                </div>
            </label>
            <label for="s2" id="slide2">
                <div class="contentsBox">
                    <p>Q2. 가게에 들어갔는데 점원이 나한테 오려고 눈치를 본다..</p>
                </div>
            </label>
            <label for="s3" id="slide3">
                <div class="contentsBox">
                    <p>Q3. 엘리베이터를 탔는데 옆사람이 말을 건다. 이어폰을 쓰고 있을 때 당신의 행동은?</p>
                </div>
            </label>
            <label for="s4" id="slide4">
                <div class="contentsBox">
                    <p>Q4. 친구한테 지금 내가 있는 카페 위치를 알려줄 때</p>
                </div>
            </label>
            <label for="s5" id="slide5">
                <div class="contentsBox">
                    <p>Q5. 알고 보니 내일이 공휴일이라면?</p>
                </div>
            </label>
        </div>

		<div id="btn">
			<c:choose>
				<c:when test="${sessionScope.loginId eq null }">
					<p id="memberGuide">해당 기능은 회원만 이용 가능합니다.</p>
					<a href="<%=request.getContextPath()%>/member/login" class="contentsBtn notMember">로그인 바로가기</a>
					<a href="<%=request.getContextPath()%>/member/addMember" class="contentsBtn notMember">회원가입 바로가기</a>
				</c:when>
				<c:otherwise>
					<form action="mbtiPlayContents" method="POST"
						class="playContentsBtn">
						<input type="submit" value="맙티플레이 GO" />
					</form>
					<form action="mbtiPlayMakeContents" method="POST"
						class="makeContentsBtn">
						<input type="submit" value="문답 만들기" />
					</form>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
</body>
</html>