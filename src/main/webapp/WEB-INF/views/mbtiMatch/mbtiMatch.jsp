<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>맙티</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/mbtiMatch/mbtiMatch.css">
<script src="<%=request.getContextPath()%>/resources/js/jquery-3.6.0.min.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/mbtiMatch/mbtiMatch.js" defer></script>

</head>
<body>
	<jsp:include page="/resources/incl/header.jsp"></jsp:include>
	<jsp:include page="/resources/incl/nav.jsp"></jsp:include>
	
	<div id="main">
		<h2><span class="letter letter1">나</span>와 <span class="letter letter2">상대방</span>의 <span class="m">M</span><span class="b">B</span><span class="t">T</span><span class="i">I</span>로 알아보는 <span class="letter letter4">궁합</span></h2>
		<form action="resultMbtiMatch" method="POST" name="mbtiForm">
			<div class="typeWrap type1Wrap">
				<p>나</p>
				<table>
					<tr>
						<td>
							<label class="btn"><input type="radio" class="type01" name="type01" value="ISTJ">
                            <span>ISTJ</span></label>
						</td>
						<td>
							<label class="btn"><input type="radio" class="type01" name="type01" value="ISFJ">
                            <span>ISFJ</span></label>
						</td>
						<td>
							<label class="btn"><input type="radio" class="type01" name="type01" value="INTJ">
                            <span>INTJ</span></label>
						</td>
						<td>
							<label class="btn"><input type="radio" class="type01" name="type01" value="INFJ">
                            <span>INFJ</span></label>
						</td>
					</tr>
					<tr>
						<td>
							<label class="btn"><input type="radio" class="type01" name="type01" value="ISTP">
                            <span>ISTP</span></label>
						</td>
						<td>
							<label class="btn"><input type="radio" class="type01" name="type01" value="ISFP">
                            <span>ISFP</span></label>
						</td>
						<td>
							<label class="btn"><input type="radio" class="type01" name="type01" value="INTP">
                            <span>INTP</span></label>
						</td>
						<td>
							<label class="btn"><input type="radio" class="type01" name="type01" value="INFP">
                            <span>INFP</span></label>
						</td>
					</tr>
					<tr>
						<td>
							<label class="btn"><input type="radio" class="type01" name="type01" value="ESTP">
                            <span>ESTP</span></label>
						</td>
						<td>
							<label class="btn"><input type="radio" class="type01" name="type01" value="ESFP">
                            <span>ESFP</span></label>
						</td>
						<td>
							<label class="btn"><input type="radio" class="type01" name="type01" value="ENTP">
                            <span>ENTP</span></label>
						</td>
						<td>
							<label class="btn"><input type="radio" class="type01" name="type01" value="ENFP">
                            <span>ENFP</span></label>
						</td>
					</tr>
					<tr>
						<td>
							<label class="btn"><input type="radio" class="type01" name="type01" value="ESTJ">
                            <span>ESTJ</span></label>
						</td>
						<td>
							<label class="btn"><input type="radio" class="type01" name="type01" value="ESFJ">
                            <span>ESFJ</span></label>
						</td>
						<td>
							<label class="btn"><input type="radio" class="type01" name="type01" value="ENTJ">
                            <span>ENTJ</span></label>
						</td>
						<td>
							<label class="btn"><input type="radio" class="type01" name="type01" value="ENFJ">
                            <span>ENFJ</span></label>
						</td>
					</tr>
				</table>
			</div>
			
			<p class="typeWrap plus">+</p>
		
			<div class="typeWrap type2Wrap">
				<p>상대방</p>
				<table>
					<tr>
						<td>
							<label class="btn"><input type="radio" class="type02" name="type02" value="ISTJ">
                            <span>ISTJ</span></label>
						</td>
						<td>
							<label class="btn"><input type="radio" class="type02" name="type02" value="ISFJ">
                            <span>ISFJ</span></label>
						</td>
						<td>
							<label class="btn"><input type="radio" class="type02" name="type02" value="INTJ">
                            <span>INTJ</span></label>
						</td>
						<td>
							<label class="btn"><input type="radio" class="type02" name="type02" value="INFJ">
                            <span>INFJ</span></label>
						</td>
					</tr>
					<tr>
						<td>
							<label class="btn"><input type="radio" class="type02" name="type02" value="ISTP">
                            <span>ISTP</span></label>
						</td>
						<td>
							<label class="btn"><input type="radio" class="type02" name="type02" value="ISFP">
                            <span>ISFP</span></label>
						</td>
						<td>
							<label class="btn"><input type="radio" class="type02" name="type02" value="INTP">
                            <span>INTP</span></label>
						</td>
						<td>
							<label class="btn"><input type="radio" class="type02" name="type02" value="INFP">
                            <span>INFP</span></label>
						</td>
					</tr>
					<tr>
						<td>
							<label class="btn"><input type="radio" class="type02" name="type02" value="ESTP">
                            <span>ESTP</span></label>
						</td>
						<td>
							<label class="btn"><input type="radio" class="type02" name="type02" value="ESFP">
                            <span>ESFP</span></label>
						</td>
						<td>
							<label class="btn"><input type="radio" class="type02" name="type02" value="ENTP">
                            <span>ENTP</span></label>
						</td>
						<td>
							<label class="btn"><input type="radio" class="type02" name="type02" value="ENFP">
                            <span>ENFP</span></label>
						</td>
					</tr>
					<tr>
						<td>
							<label class="btn"><input type="radio" class="type02" name="type02" value="ESTJ">
                            <span>ESTJ</span></label>
						</td>
						<td>
							<label class="btn"><input type="radio" class="type02" name="type02" value="ESFJ">
                            <span>ESFJ</span></label>
						</td>
						<td>
							<label class="btn"><input type="radio" class="type02" name="type02" value="ENTJ">
                            <span>ENTJ</span></label>
						</td>
						<td>
							<label class="btn"><input type="radio" class="type02" name="type02" value="ENFJ">
                            <span>ENFJ</span></label>
						</td>
					</tr>
				</table>
			</div>

			<input type="submit" class="submitBtn" value="결과보기" onclick="return false;">
		</form>
		
	</div>

	<script>
	
	$('#nav li:nth-of-type(4)').addClass('clicked');
	
	var loginIdMbti = '${loginIdMbti}'; //controller에서 type01을 담은 변수 loginMbti 가져오기.
	
	console.log(loginIdMbti);
	
	// 로그인한 회원일 경우
	if(!(loginIdMbti == null || loginIdMbti == '')) { // if 로그인한 회원이라면,
			
		$('.type01').each(function() {
			if($(this).val() == loginIdMbti) {
				console.log($(this).val());
				$(this).prop("checked", "true");
			}
		});
	}
	
	</script>
</body>
</html>