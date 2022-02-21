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
	href="<%=request.getContextPath()%>/resources/css/mbtiPlay.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/resources/css/mbtiPlay/mbtiPlayMakeContents.css">

<script
	src="<%=request.getContextPath()%>/resources/js/jquery-3.6.0.min.js"></script>
</head>
<body>
	<jsp:include page="/resources/incl/header.jsp"></jsp:include>
	<jsp:include page="/resources/incl/nav.jsp"></jsp:include>
	<div id="main">
		<h2 class="hidden">문답 등록</h2>
		<p id="introTxt">
			<span>맙티 유저들과 함께 만들어가는 맙티 플레이!</span><br> 내가 만든 흥미로운 문답을 유저들이 풀
			수 있어요!<br> 문답을 만들면 맙포인트까지 얻을 수 있습니다.
		</p>

		<div id="QnA">
			<form:form action="successAddMbtiPlayMakeContents" method="post"
				modelAttribute="mbtiPlayContents" autocomplete="off" id="pathDiv">
				<div id="addQuestion">
					<p>
						<span class="stepTxt">STEP 1. </span> 질문을 작성하세요.
					</p>
					<form:input path="question" class="userInput"
						oninput="countQuestion(this.form)" />
					<span id="questionValue"></span> <span id="questionCount"></span>
				</div>
				<span class="nextIcon"></span>
				<div id="addAnswers">
					<p>
						<span class="stepTxt">STEP 2. </span> 유저가 선택할 보기 3개를 작성하세요.
					</p>
					<div id="answer01div">
						<form:input path="answer01" class="userInput"
							oninput="countAnswer01(this.form)" />
						<span class="answerValue answer01Value"></span> <span
							class="answerCount answer01Count"></span>
					</div>
					<div id="answer02div">
						<form:input path="answer02" class="userInput"
							oninput="countAnswer02(this.form)" />
						<span class="answerValue answer02Value"></span> <span
							class="answerCount answer02Count"></span>
					</div>
					<div id="answer03div">
						<form:input path="answer03" class="userInput"
							oninput="countAnswer03(this.form)" />
						<span class="answerValue answer03Value"></span> <span
							class="answerCount answer03Count"></span>
					</div>
				</div>
				<div id="makeBtn">
					<c:choose>
						<c:when test="${zeroCount == 0 }">
							<p class="count">금일 생성 횟수 0 / 3</p>
							<input type="submit" value="등록"
						onclick="makeContents(this.form); return false">
						</c:when>
						<c:when test="${contentsCount != 3}">
							<p class="count">금일 생성 횟수 ${contentsCount } / 3</p>
							<input type="submit" value="등록"
						onclick="makeContents(this.form); return false">
						</c:when>
						<c:when test="${contentsCount == 3 }">
							<p id="last">금일 생성 횟수 ${contentsCount } / 3</p>
							<p>금일 생성 가능한 횟수를 초과하였습니다. 매일 밤 12시에 횟수가 초기화됩니다.</p>
						</c:when>
					</c:choose>
				</div>
				<div id="deco">
					<div id="deco1"></div>
					<div id="deco2"></div>
					<div id="deco3"></div>
				</div>
			</form:form>
		</div>
	</div>
	<script type="text/javascript">
		function makeContents(form) {
			let question = form.question.value;
			let answer01 = form.answer01.value;
			let answer02 = form.answer02.value;
			let answer03 = form.answer03.value;
			let state = true;

			if (question.length == 0) {
				$("#questionValue").text("내용을 입력하세요.");
				state = false;
			} else if (question.length > 500) {
				$("#questionValue").text("500자 이내로 입력 해주세요.");
				state = false;
			} else {
				$("#questionValue").text("");
			}

			//01
			if (answer01.length == 0) {
				$(".answer01Value").text("내용을 입력하세요.");
				state = false;
			} else if (answer01.length > 100) {
				$(".answer01Value").text("100자 이내로 입력 해주세요.");
				state = false;
			} else {
				$(".answer01Value").text("");
			}

			//02
			if (answer02.length == 0) {
				$(".answer02Value").text("내용을 입력하세요.");
				state = false;
			} else if (answer02.length > 100) {
				$(".answer02Value").text("100자 이내로 입력 해주세요.");
				state = false;
			} else {
				$(".answer02Value").text("");
			}

			//03
			if (answer03.length == 0) {
				$(".answer03Value").text("내용을 입력하세요.");
				state = false;
			} else if (answer03.length > 100) {
				$(".answer03Value").text("100자 이내로 입력 해주세요.");
				state = false;
			} else {
				$(".answer03Value").text("");
			}

			// 하루 3번 제한 시
			/*if(contentsCount == 3 ){
				alert("금일 생성 가능한 횟수를 초과하였습니다. 매일 밤 12시에 횟수가 초기화됩니다.");
				state = false;
			}*/

			if (state == true) {
				let submitBtn = document.getElementById("form");
				submitBtn.submit();
			}
		}
		function countQuestion(form) {
			let question = form.question.value;
			if (!(question.length == 0)) {
				$("#questionCount").text(question.length + " 자 / 500자");
				if (question.length > 500) {
					$("#questionCount").text("질문은 500자 이내로 입력 가능합니다.");
				}
			} else {
				$("#questionCount").text("");
			}
		}

		function countAnswer01(form) {
			let answer01 = form.answer01.value;
			if (!(answer01.length == 0)) {
				$(".answer01Count").text(answer01.length + " 자 / 100자");
				if (answer01.length > 100) {
					$(".answer01Count").text("보기작성은 100자 이내로 입력 가능합니다.");
				}
			} else {
				$(".answer01Count").text("");
			}
		}

		function countAnswer02(form) {
			let answer02 = form.answer02.value;
			if (!(answer02.length == 0)) {
				$(".answer02Count").text(answer02.length + " 자 / 100자");
				if (answer02.length > 100) {
					$(".answer02Count").text("보기작성은 100자 이내로 입력 가능합니다.");
				}
			} else {
				$(".answer02Count").text("");
			}
		}

		function countAnswer03(form) {
			let answer03 = form.answer03.value;
			if (!(answer03.length == 0)) {
				$(".answer03Count").text(answer03.length + " 자 / 100자");
				if (answer03.length > 100) {
					$(".answer03Count").text("보기작성은 100자 이내로 입력 가능합니다.");
				}
			} else {
				$(".answer03Count").text("");
			}
		}
	</script>
</body>
</html>