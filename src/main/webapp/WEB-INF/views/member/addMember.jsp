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
	href="<%=request.getContextPath()%>/resources/css/member/addMember.css">
<script
	src="<%=request.getContextPath()%>/resources/js/jquery-3.6.0.min.js"
	defer></script>
<script
	src="<%=request.getContextPath()%>/resources/js/member/addMember.js"
	defer></script>
</head>
<body>
	<jsp:include page="/resources/incl/nav.jsp"></jsp:include>
	<div id="main">
		<main id="addCustomer">
			<h1>회원가입</h1>
			<div id="addCustomer_wrap">
				<form:form action="successAddMember" method="post"
					modelAttribute="memberCommand">
					<table>
						<tr>
							<th>이메일</th>
							<td><form:input type="text" maxlength="30"
									placeholder="5자리 이상 영문, 숫자" path="email1" autocomplete="off"
									autofocus="autofocus" oninput="emailCheck(this.form)" /> @ <select
								name="email2" style="width: 90px; height: 25px">
									<option value="@naver.com">naver.com</option>
									<option value="@daum.net">daum.net</option>
									<option value="@gmail.com">gmail.com</option>
									<option value="@nate.com">nate.com</option>
							</select></td>
							<td><span class="errorTxt"></span></td>
						</tr>
						<tr>
							<th>비밀번호</th>
							<td><form:password id="password1" onpaste="return false;"
									oncopy="return false;" placeholder="8자리 이상 문자, 숫자, 특수문자"
									path="pw" /></td>
							<td><span class="errorTxt"></span></td>
						</tr>
						<tr>
							<th>비밀번호 확인</th>
							<td><form:password id="password2" placeholder="비밀번호 재확인"
									path="" /></td>
							<td><span class="errorTxt"></span></td>
						</tr>
						<tr>
							<th>이름</th>
							<td><form:input placeholder="2자리 이상 한글" path="name"
									maxlength="30" autocomplete="off" /></td>
							<td><span class="errorTxt"></span></td>
						</tr>
						<tr>
							<th>닉네임</th>
							<td><form:input placeholder="2자리 이상 문자, 숫자" path="nickName"
									autocomplete="off" maxlength="10"
									oninput="nickNameCheck(this.form)" /></td>
							<td><span class="errorTxt"></span></td>
						</tr>
						<tr>
							<th>생년월일</th>
							<td><form:select class="year" path="birth"
									style="width:70px; height:25px" /> 년 <select class="month"
								name="birth" style="width: 60px; height: 25px"></select> 월 <select
								class="day" name="birth" style="width: 60px; height: 25px"></select>
								일</td>
							<td><span class="errorTxt"></span></td>
						</tr>
						<tr>
							<th>MBTI</th>
							<td><select name="mbti" style="width: 65px; height: 25px">
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
								href="javascript:void(window.open('https://www.16personalities.com/ko/%EB%AC%B4%EB%A3%8C-%EC%84%B1%EA%B2%A9-%EC%9C%A0%ED%98%95-%EA%B2%80%EC%82%AC', 'popup','width=1080, height=1000, left=200, top=150'))">검사하기</a>
							<td><span class="errorTxt"></span></td>
						</tr>
						<tr>
							<th>성별(선택)</th>
							<td id="radioBtn">남자: <form:radiobutton path="gender"
									value="M" /> 여자: <form:radiobutton path="gender" value="F" />
								선택안함: <form:radiobutton path="gender" value="N"
									checked="checked" />
							</td>
							<td><span class="errorTxt"></span></td>
						</tr>
						<tr>
							<th>휴대전화</th>
							<td><form:input type="text" path="phone" maxlength="11"
									minlength="11" placeholder="'-' 제외한 11자리 숫자" pattern="[0-9]+" />
							</td>

							<td><span class="errorTxt errorTxt2"></span></td>
						</tr>
					</table>
					<div id="addCustomer_btn">
						<a href="/myapp/index">홈으로</a> 
						<input type="submit" value="가입완료"
							onclick="checkPattern(this.form); return false" id="submit">
					</div>
				</form:form>
			</div>
			<div id="deco">
				<div id="deco1">
					<img
						src="<%=request.getContextPath()%>/resources/img/avatar/MBTI_ENFP.png"
						alt="enfp" class="avatar avatar16 enfp"></img> <img
						src="<%=request.getContextPath()%>/resources/img/avatar/MBTI_ISFJ.png"
						alt="isfj" class="avatar avatar16 isfj"></img>
				</div>
				<div id="deco2">
					<img
						src="<%=request.getContextPath()%>/resources/img/avatar/MBTI_ISTP.png"
						alt="istp" class="avatar avatar16 istp"></img>
				</div>
				<div id="deco3"></div>
			</div>
		</main>
	</div>
</body>
</html>