<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>맙티</title>
</head>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/resources/css/member/updateMember.css">
<script
	src="<%=request.getContextPath()%>/resources/js/jquery-3.6.0.min.js"
	defer></script>
<script src="<%=request.getContextPath()%>/resources/js/member/updateMember.js"
	defer></script>
<body>
	<jsp:include page="/resources/incl/nav.jsp"></jsp:include>
	<div id="main">
		<main id="updateMember">
			<h1>정보수정</h1>
			<div id="updateMember_wrap">
				<form:form action="successUpdateMember" method="post"
					modelAttribute="memberCommand">
					<table>
						<tr>
							<th>이메일</th>
							<td><form:input type="text" maxlength="30" readonly="true"
									value="${sessionScope.memberInfo.email}" path="email1"
									autocomplete="off" oninput="emailCheck(this.form)" /></td>
							<td><span class="errorTxt"></span></td>
						</tr>
						<tr>
							<th>비밀번호</th>
							<td><form:password id="password1" placeholder="비밀번호"
									onpaste="return false;" oncopy="return false;"
									value="${sessionScope.memberInfo.pw}" path="pw" /></td>
							<td><span class="errorTxt"></span></td>
						</tr>
						<tr>
							<th>비밀번호 확인</th>
							<td><input type="password" id="password2"
								placeholder="비밀번호 재확인" onpaste="return false;"
								oncopy="return false;" /></td>
							<td><span class="errorTxt"></span></td>
						</tr>
						<tr>
							<th>이름</th>
							<td><form:input path="name" maxlength="30"
									value="${sessionScope.memberInfo.name}" autocomplete="off"
									readonly="true" /></td>
							<td><span class="errorTxt"></span></td>
						</tr>
						<tr>
							<th>닉네임</th>
							<td><form:input value="${sessionScope.memberInfo.nickName}"
									path="nickName" autocomplete="off" maxlength="10"
									oninput="nickNameCheck(this.form)" /></td>
							<td><span class="errorTxt"></span></td>
						</tr>
						<tr>
							<th>생년월일</th>
							<td><select id="year" name="birth"></select> 년 <select
								id="month" name="birth"></select> 월 <select id="day"
								name="birth"></select> 일</td>
							<td><span class="errorTxt"></span></td>
						</tr>
						<tr>
							<th>MBTI</th>
							<td><select id="mbti" name="mbti">
									<option value="ENTJ">ENTJ</option>
									<option value="ENTP">ENTP</option>
									<option value="ENFJ">ENFJ</option>
									<option value="ENFP">ENFP</option>
									<option value="ESTJ">ESTJ</option>
									<option value="ESTP">ESTP</option>
									<option value="ESFJ">ESFJ</option>
									<option value="ESFP">ESFP</option>
									<option value="INTJ">INTJ</option>
									<option value="INTP">INTP</option>
									<option value="INFJ">INFJ</option>
									<option value="INFP">INFP</option>
									<option value="ISTJ">ISTJ</option>
									<option value="ISTP">ISTP</option>
									<option value="ISFJ">ISFJ</option>
									<option value="ISFP">ISFP</option>
							</select> <a id="mbtiBtn"
								href="javascript:void(window.open('https://www.16personalities.com/ko/%EB%AC%B4%EB%A3%8C-%EC%84%B1%EA%B2%A9-%EC%9C%A0%ED%98%95-%EA%B2%80%EC%82%AC', 'popup','width=1080, height=1000, left=200, top=50'))">검사하기</a>
							<td><span class="errorTxt"></span></td>
						</tr>
						<tr>
							<th>성별(선택)</th>
							<td id="radioBtn">남자: <form:radiobutton class="radioBtn"
									path="gender" value="M" /> 여자: <form:radiobutton
									class="radioBtn" path="gender" value="F" /> 선택안함: <form:radiobutton
									class="radioBtn" path="gender" value="N" />
							</td>
							<td><span class="errorTxt"></span></td>
						</tr>
						<tr>
							<th>휴대전화</th>
							<td><form:input value="${sessionScope.memberInfo.phone}"
									type="text" path="phone" maxlength="11" minlength="11"
									placeholder="'-' 제외한 11자리 숫자" pattern="[0-9]+" /></td>

							<td><span class="errorTxt errorTxt2"></span></td>
						</tr>
					</table>
					<div id="updateMember_btn">
						<a href="/myapp/index">홈으로</a> <input type="submit" value="수정완료"
							onclick="checkPattern(this.form); return false" id="submit">
					</div>
				</form:form>
			</div>
			<div id="deco">
				<div id="deco1">
					<img
						src="<%=request.getContextPath()%>/resources/img/avatar/MBTI_INFP.png"
						alt="infp" class="avatar avatar10 infp"></img><img
						src="<%=request.getContextPath()%>/resources/img/avatar/MBTI_ESFJ.png"
						alt="esfj" class="avatar avatar5 esfj"></img>
				</div>
				<div id="deco2">
					<img
						src="<%=request.getContextPath()%>/resources/img/avatar/MBTI_INFJ.png"
						alt="infj" class="avatar avatar9 infj"></img>
				</div>
				<div id="deco3"></div>
			</div>
		</main>

	</div>
	<script>
		// 생년월일
		var now = new Date();
		var year = now.getFullYear();
		var mon = (now.getMonth() + 1) > 9 ? '' + (now.getMonth() + 1) : '0'
				+ (now.getMonth() + 1);
		var day = (now.getDate()) > 9 ? '' + (now.getDate()) : '0'
				+ (now.getDate());

		//년도 selectbox만들기 
		for (var i = 1950; i <= year; i++) {
			$('#year').append('<option value="' + i + '">' + i + '년</option>');
		}

		// 월별 selectbox 만들기 
		for (var i = 1; i <= 12; i++) {
			var mm = i > 9 ? i : "0" + i;
			$('#month').append(
					'<option value="' + mm + '">' + mm + '월</option>');
		}

		// 일별 selectbox 만들기 
		for (var i = 1; i <= 31; i++) {
			var dd = i > 9 ? i : "0" + i;
			$('#day').append('<option value="' + dd + '">' + dd + '일</option>');
		}

		//select, radioBtn 값 불러오기

		//MBTI
		var myMbti = '${sessionScope.memberInfo.mbti}';
		$('#mbti').val(myMbti).prop("selected", true);

		//성별
		var myGender = '${sessionScope.memberInfo.gender}';
		$(":radio[name='gender'][value='" + myGender + "']").attr('checked',
				true);

		//생년월일
		var myYear = "${sessionScope.memberInfoBirth[0]}";
		$('#year').val(myYear).prop("selected", true);

		var myMonth = '${sessionScope.memberInfoBirth[1]}';
		$('#month').val(myMonth).prop("selected", true);

		var myDay = '${sessionScope.memberInfoBirth[2]}';
		$('#day').val(myDay).prop("selected", true);

		//닉네임 중복검사
		var loginNickName = '${sessionScope.memberInfo.nickName}';
	</script>
</body>
</html>