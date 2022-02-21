<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>맙티</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/resources/css/mbtiPlay/successAddMbtiPlayMakeContents.css">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/mbtiPlay/successAddMbtiPlayMakeContents.js" defer></script>
</head>
<body>
	<jsp:include page="/resources/incl/header.jsp"></jsp:include>
	<jsp:include page="/resources/incl/nav.jsp"></jsp:include>
    <div id="main">
        <h2 class="hidden">mbtiPlay</h2>
        <div id="success">
            <div class="doughnutWrap">
                <div class="successChkIcon">
                    <div class="chart doughnut1"><span class="center"></span></div>
                    <div class="chartOK"></div>
                </div>
            </div>
            <div id="successTxt">
                <p>맙티플레이 만들기 성공!</p>
                <p>회원님의 맙티플레이가 성공적으로 등록되었습니다.</p>
                <p id="addPoint">+ 30맙</p>
            </div>
            <div id="guideBtn">
                <a href="/myapp/mbtiPlay/mbtiPlayZone" class="contentsBtn">맙티 플레이 바로가기</a>
            </div>
        </div>
    </div>

</body>
</html>