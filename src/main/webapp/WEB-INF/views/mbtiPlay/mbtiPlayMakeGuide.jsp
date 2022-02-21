<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>맙티</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/mbtiPlay/mbtiPlayMakeGuide.css">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/mbtiPlay/mbtiPlayMakeGuide.js" defer></script>
</head>
<body>
<jsp:include page="/resources/incl/header.jsp"></jsp:include>
<jsp:include page="/resources/incl/nav.jsp"></jsp:include>
    <div id="main">
        <h2 class="hidden">mbtiPlay</h2>
        <div id="guide">
            <div id="deco">
                <div id="deco1"></div>
                <div id="deco2"></div>
                <div id="deco3"></div>
            </div>
            <div id="guideTxt">
                <p>대단해요!</p>
                <p>맙티플레이를 전부 푸셨습니다!</p>
                <p>맙티플레이는 맙티 회원들의 참여로 이루어집니다.</p>
                <p>새로운 문답이 등록될 때 까지 기다려주세요.</p>
                <p>직접 맙티플레이를 만들 수도 있어요!</p>
            </div>
            <div id="guideBtn">
                <a href="/myapp/mbtiPlay/mbtiPlayZone" class="contentsBtn">맙티 플레이 바로가기</a>
                <form action="mbtiPlayMakeContents" method="post">
                    <input type="submit" value="문답 만들기" class="contentsBtn" />
                </form>
            </div>
        </div>
    </div>
</body>
</html>