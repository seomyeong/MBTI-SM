<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>맙티</title>
<!-- <link rel="shortcut icon" href="favicon.ico" type="image/x-icon">
    <link rel="icon" href="img/favicon.ico" type="image/x-icon"> -->
    <script
	src="<%=request.getContextPath()%>/resources/js/jquery-3.6.0.min.js"></script>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/resources/css/common.css">
<script src="<%=request.getContextPath()%>/resources/js/common.js" defer></script>
</head>
<body>
	<nav id="nav" class="hiddenProfile">
		<span id="menuToggle" class="show"> <ion-icon name="menu-outline"></ion-icon></span>
			<c:if test="${not (sessionScope.loginId eq null)}">
				<span id="__mbtiInfo">${sessionScope.memberInfo.mbti}</span>
			</c:if>
		<ul>
			<c:choose>
				<c:when test="${sessionScope.loginId eq null}">
					<li id="profile" class="hiddenProfile"><a href="#"> <span class="icon"> <ion-icon
									name="person-circle-outline"></ion-icon>
						</span> <span class="title">회원이신가요?</span>
					</a></li>
					<a href="/myapp/member/login" id="login" class="hiddenProfile">로그인</a>
					<a href="/myapp/member/addMember" id="addUser" class="hiddenProfile">회원가입</a>
				</c:when>
				<c:otherwise>
					<li id="profile" class="hiddenProfile"><a href="#"> <span class="icon"> <span id="profileImg" class="hiddenProfile" style="background: url(${sessionScope.memberInfo.profileImg}) 0 0 / cover"></span>
						</span><span class="nickName_profile">Lv. ${sessionScope.memberInfo.level} &nbsp;&nbsp;${sessionScope.memberInfo.nickName}</span>
					</a></li>
					<span id="contentsCount" class="hiddenProfile">내가 쓴 총 게시물 수 &nbsp;&nbsp;${sessionScope.memberInfo.contentsCount}</span>
					<span id="commentsCount" class="hiddenProfile">내가 쓴 총 댓글 수 &nbsp;&nbsp;&nbsp;${sessionScope.memberInfo.commentsCount}</span>
					<span id="mabPoint" class="hiddenProfile">현재 보유한 맙 &nbsp;&nbsp;&nbsp;&nbsp;${sessionScope.memberInfo.mabPoint}</span>
					<span id="profileBack" class="hiddenProfile"></span>
				</c:otherwise>
			</c:choose>
			<li><a href="/myapp/"> <span class="icon"> <ion-icon
							name="home-outline"></ion-icon>
				</span> <span class="title">홈으로</span>
			</a></li>
			<li><a href="/myapp/community/mainCommunity_deleteSession"> <span class="icon"> <ion-icon
							name="people-outline"></ion-icon>
				</span> <span class="title">커뮤니티</span>
			</a></li>
			<li><a href="/myapp/mbtiMatch/mbtiMatch"> <span class="icon"> <ion-icon
							name="heart-outline"></ion-icon>
				</span> <span class="title">궁합도</span>
			</a></li>
			<li><a href="/myapp/mbtiPlay/mbtiPlayZone"> <span class="icon"> <ion-icon name="rocket-outline"></ion-icon>
				</span> <span class="title">맙티 플레이</span>
			</a></li>
			<c:choose>
				<c:when test="${sessionScope.loginId eq null}">
					<li><a href="#" onclick="javascript:navErrorMsg();  return false;"> <span class="icon"> <ion-icon
									name="settings-outline"></ion-icon>
						</span> <span class="title">개인설정</span>
					</a></li>
					<li><a href="#" onclick="javascript:navErrorMsg(); return false;"> <span class="icon"> <ion-icon
									name="log-out-outline"></ion-icon>
						</span> <span class="title">로그아웃</span>
					</a></li>
				</c:when>
				<c:otherwise>
					<li><a href="/myapp/member/updateMember"> <span class="icon"> <ion-icon
									name="settings-outline"></ion-icon>
						</span> <span class="title">개인설정</span>
					</a></li>
					<li><a href="/myapp/member/logout"> <span class="icon"> <ion-icon
									name="log-out-outline"></ion-icon>
						</span> <span class="title">로그아웃</span>
					</a></li>
				</c:otherwise>
			</c:choose>
		</ul>
		<small id="__copyright" class="hide">&copy; Copyright 2022. <br>WeAll-I All rights reserved.</small>
	</nav>
	<!-- <div id="main"></div> -->

	<script type="module"
		src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.esm.js"></script>
	<script nomodule
		src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.js"></script>

	<script>
		$('#menuToggle ion-icon').on('click', function() {
			if('${sessionScope.loginId}' == '') {
				$('#menuToggle ion-icon').toggleClass('show');
				$('#nav').toggleClass('hiddenProfile');
				$('#nav ul li:nth-child(1)').toggleClass('hiddenProfile');
				$('#main').toggleClass('extend');
				$('#login').toggleClass('hiddenProfile');
				$('#addUser').toggleClass('hiddenProfile');
				$('#__copyright').toggleClass('hide');
			} else {
				$('#menuToggle ion-icon').toggleClass('show');
				$('#nav').toggleClass('hiddenProfile');
				$('#nav ul li:nth-child(1)').toggleClass('hiddenProfile');
				$('#main').toggleClass('extend');
				$('#contentsCount').toggleClass('hiddenProfile');
				$('#commentsCount').toggleClass('hiddenProfile');
				$('#mabPoint').toggleClass('hiddenProfile');
				$('#profileImg').toggleClass('hiddenProfile');
				$('#profileBack').toggleClass('hiddenProfile');
				$('#__mbtiInfo').toggleClass('hidden');
				$('#__copyright').toggleClass('hide');
			}
		});
		
		if(!('${sessionScope.loginId}' == '' || '${sessionScope.loginId}' == null)) {
			
			setInterval(function() {
				var now_nickName = $('.nickName_profile').html();
				var now_contentsCount = $('#contentsCount').html();
				var now_commentsCount = $('#commentsCount').html();
				var now_mabPoint = $('#mabPoint').html();
				var now_profileImg = document.querySelector('#profileImg').style.backgroundImage;

				var param = {
						"loginId" : '${sessionScope.loginId}',
				}
				$.ajax({
					type: "post",
					data: JSON.stringify(param),
					url: "/myapp/liveProfile",
					contentType: "application/json; charset=UTF-8",
					success: function(data) {
						var m = data["m"];

						m_nickName = "Lv. " + m.level + "&nbsp;&nbsp;" + m.nickName;
						m_contentsCount = "내가 쓴 총 게시물 수 &nbsp;&nbsp;" + m.contentsCount;
						m_commentsCount = "내가 쓴 총 댓글 수 &nbsp;&nbsp;" + m.commentsCount;
						m_mabPoint = "현재 보유한 맙 &nbsp;&nbsp;&nbsp;&nbsp;" + m.mabPoint;
						m_profileImg = "url(\"" + m.profileImg + "\")";
						
						if(now_nickName != m_nickName) {
							$('.nickName_profile').html(m_nickName);							
						}
						if(now_contentsCount != m_contentsCount) {
							$('#contentsCount').html(m_contentsCount);							
						}
						if(now_commentsCount != m_commentsCount) {
							$('#commentsCount').html(m_commentsCount);							
						}
						if(now_mabPoint != m_mabPoint) {
							$('#mabPoint').html(m_mabPoint);							
						}
						if(now_profileImg != m_profileImg) {
							$('#profileImg').css("background-image", m_profileImg);
						}
					}
				});
			}, 300);
			
		}
		
	
	</script>
</body>
</html>