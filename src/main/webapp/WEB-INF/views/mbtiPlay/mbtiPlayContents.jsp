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
	href="<%=request.getContextPath()%>/resources/css/mbtiPlay/mbtiPlayContents.css">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script
	src="<%=request.getContextPath()%>/resources/js/mbtiPlay/mbtiPlayContents.js"
	defer></script>
</head>
<body>
	<jsp:include page="/resources/incl/header.jsp"></jsp:include>
	<jsp:include page="/resources/incl/nav.jsp"></jsp:include>
	<div id="main">
		<h2 class="hidden">MbtiPlay</h2>
		<section id="playContents">

			<c:forEach var="content" items="${content }">
				<h3 class="hidden">문답 풀기</h3>
				<div id="question">
					<span id="questionTest"></span>

					<p>Q. ${content.question }</p>
					<div id="questionBar"></div>
				</div>
				<div id="answers">
					<ul>
						<li>1. ${content.answer01 }</li>
						<li>2. ${content.answer02 }</li>
						<li>3. ${content.answer03 }</li>
						<li>4. 마음에 드는 보기가 없으신가요? Click!</li>
					</ul>
				</div>
				<div id="tableInput">
					<form:form method="post" modelAttribute="mbtiPlayContentsAnswer">
						<span class="hidden" id="contentPk">${memberMbti }</span>
						<span class="hidden" id="contentId">${content.id }</span>
						<form:input path="memberMbti" id="memberMbti" />
						<form:input path="questionNum" id="questionNum" />
						<form:input path="choosenNum" id="chooseNum" />
						<form:input path="isSubjective" id="isSubjective" />
						<form:input path="subjectiveContent" id="subjectiveContent"
							autocomplete="off" placeholder="직접 입력하세요." />
						<form:input path="choosenNumCount" id="answerCount" />
						<input type="submit" value="전송"
							onclick="addAnswers(this.form); return false" id="answersSubmit">
					</form:form>
				</div>
			</c:forEach>
		</section>

		<!--전송버튼 누른 후 페이지 내 정보 전송-->
		<section id="statistics">
			<h2 class="hidden">통계</h2>
			<span id="questionTest2"></span>
			<table id="myMbti">
				<c:forEach var="memberMbti" items="${memberMbti }">
					<caption>
						<span>${memberMbti }</span> 유형은 이렇게 선택했어요.
					</caption>
				</c:forEach>
				<tr>
					<td class="answerCountNum1"></td>
					<td>1. <span class="answer answer01"></span></td>
				</tr>
				<tr>
					<td class="answerCountNum2"></td>
					<td>2. <span class="answer answer02"></span></td>
				</tr>
				<tr>
					<td class="answerCountNum3"></td>
					<td>3. <span class="answer answer03"></span></td>
				</tr>
			</table>
			<table id="otherMbtiSubjective">
				<caption>다른 유형들은 기타답변에 이렇게 얘기했어요.</caption>
			</table>

			<input type="submit" value="NEXT"
				onClick="reloadByRandomNum(this.form); return false" id="next">
		</section>
	</div>
	<script type="text/javascript">
		function addAnswers(form) {
			
			let memberMbti = form.memberMbti.value;
			let questionNum = form.questionNum.value;
			let choosenNum = form.choosenNum.value;
			let isSubjective = form.isSubjective.value;
			let subjectiveContent = form.subjectiveContent.value;
			let choosenNumCount = form.choosenNumCount.value;
			
			if(subjectiveContent.length == 0){
				alert("답변을 작성하세요.");
				return;
			} else if(subjectiveContent.length > 100){
				alert("100자 이내로 입력 가능합니다.")
				return;
			}

			
			let param = {
				'memberMbti' : memberMbti,
				'questionNum' : questionNum,
				'choosenNum' : choosenNum,
				'isSubjective' : isSubjective,
				'subjectiveContent' : subjectiveContent,
				'choosenNumCount' : choosenNumCount
			}

			$.ajax({
						type : 'POST',
						data : JSON.stringify(param),
						url : "/myapp/mbtiPlay/addAnswer",
						contentType : "application/json; charset=UTF-8",
						success : function(data) {
							    $('#playContents').animate({ marginTop: 80 }, function () {
							        $('#statistics').delay(300).animate({ opacity: 1 }, 2000)
							    })
							    $('#answersSubmit').hide()
							    $('#answers li').off('click')
							
							//객관식 데이터 객체
							let firstObjInfo = data.firstObj;
							let secondObjInfo = data.secondObj;
							let thirdObjInfo = data.thirdObj;

							//객관식 데이터 가공
							let firstObjCount = firstObjInfo.choosenNumCount;
							let secondObjCount = secondObjInfo.choosenNumCount;
							let thirdObjCount = thirdObjInfo.choosenNumCount;
							
							//--------------------------------------------------------------
							//선택자
							const otherMbtiSubjective = document.querySelector('#otherMbtiSubjective');
							
							//주관식 데이터 객체
							let nullSubMsg = data.nullSubMsg;
							let subList = data.subList;
							
							if (subList != null) {
								for (let i = 0; i < subList.length; i++) {
									let subId = subList[i].id;
									let subMemberMbti = subList[i].memberMbti;
									let subjectiveContent = subList[i].subjectiveContent;
									
									if(subList.length <= 3){
										let addSubMbti = subMemberMbti[i];
										let addSubjective = subjectiveContent[i];
										
										$('#otherMbtiSubjective').each(function(){
											let subTag = "<tr><td id='td-SubMbti'>"+subMemberMbti+"</td><td id='td-SubCon'>"+subjectiveContent+"</td></tr>";
											$(this).append(subTag);
										})
											
									}else if(subList.length > 3){
										let random = [];
										let ranSet = new Set();
										let subTotalNum = subList.length;
																				
										for(let i = 0; i<subTotalNum; i++){
											ranSet.add(Math.floor(Math.random()*subList.length));
											ranSet.add(Math.floor(Math.random()*subList.length));
										}
											random = [...ranSet]; //set의 array화
											for(let i = 0; i<3; i++){
												let firstSubTag = "<tr><td class='td-SubMbti'>"+subList[random[i]].memberMbti+"</td><td class='td-SubCon'>"+subList[random[i]].subjectiveContent+"</td></tr>";																						
												$('#otherMbtiSubjective').append(firstSubTag); 
											}
										if( $('#otherMbtiSubjective tr').eq(2) ){
											break;
										}
									}
								}//end of for
							} else {
								let addNullSubMsg = "<p id='nullSubMsg'>" + nullSubMsg+"</p>";
								otherMbtiSubjective.innerHTML=addNullSubMsg;
							}
							// 객관식에 대한 데이터가 없으면 값을 출력할 변수
							let nullObjMsg = "아직 선택받지 못했어요!";

							if (firstObjCount != 0) {
								$('.answerCountNum1').text(
										firstObjCount + "명이 선택했습니다.");
							} else {
								$('.answerCountNum1').text(nullObjMsg);
							}

							if (secondObjCount != 0) {
								$('.answerCountNum2').text(
										secondObjCount + "명이 선택했습니다.");
							} else {
								$('.answerCountNum2').text(nullObjMsg);
							}

							if (thirdObjCount != 0) {
								$('.answerCountNum3').text(
										thirdObjCount + "명이 선택했습니다.");
							} else {
								$('.answerCountNum3').text(nullObjMsg);
							}
						}
						
					}); //end of Ajax
			
			}//end of addAnswers(form)

		function reloadByRandomNum(form) {
			window.location.reload();
		}
		
		function duplNum(randomNum){
			return randomNum.find((e)=>(e===randomNum));
		}
	</script>
</body>
</html>