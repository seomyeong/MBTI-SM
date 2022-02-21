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
	href="<%=request.getContextPath()%>/resources/css/mbtiMatch/resultMbtiMatch.css">
<script
	src="<%=request.getContextPath()%>/resources/js/jquery-3.6.0.min.js"></script>
<script src="https://developers.kakao.com/sdk/js/kakao.js"></script>
<script>
	Kakao.init('8cc3104025014ea7f1160874240cf7ff');
	Kakao.isInitialized();
</script>
</head>
<body>
	<jsp:include page="/resources/incl/header.jsp"></jsp:include>
	<jsp:include page="/resources/incl/nav.jsp"></jsp:include>

	<div id="main">
		<form>
			<article id="typeWrap">

				<div class="typeWrap type1Wrap">
					<p>나의 MBTI</p>
					<input type="text" name="type01" value="${type01}" readonly>
				</div>
				<p class="plus">+</p>
				<div class="typeWrap type2Wrap">
					<p>상대방 MBTI</p>
					<input type="text" name="type02" value="${type02}" readonly>
				</div>
			</article>
			<article id="resultWrap">
				<h2>${matchResult}</h2>
				<c:choose>
					<c:when test="${result eq 1}">
						<p class="resultText">두 사람 모두가 무조건 배려해야 하는 관계</p>
						<img src="<c:url value='../resources/img/mbtiMatch/mbti1.png'/>" />
					</c:when>
					<c:when test="${result eq 2}">
						<p class="resultText">서로 타협하고 성숙해져야 유지되는 관계</p>
						<img src="<c:url value='../resources/img/mbtiMatch/mbti2.png'/>" />
					</c:when>
					<c:when test="${result eq 3}">	
						<p class="resultText">서로의 타협점을 찾으면 끈끈하게 유지될 수 있는 관계</p>
						<img src="<c:url value='../resources/img/mbtiMatch/mbti3.png'/>" />
					</c:when>
					<c:when test="${result eq 4}">	
						<p class="resultText">어려움이 있을 수는 있지만 원만히 해결되고 끈끈하게 유지되는 관계</p>
						<img src="<c:url value='../resources/img/mbtiMatch/mbti4.png'/>" />
					</c:when>
					<c:when test="${result eq 5}">
						<p class="resultText">문제가 잘 해결되고 자연스럽게 발전되는 관계</p>
						<img src="<c:url value='../resources/img/mbtiMatch/mbti5.png'/>" />
					</c:when>
				</c:choose>
				<h3 id="resultType">${type02}<span>는</span></h3>
				<div id="mbtiTip">
					<c:choose>
						<c:when test="${type02 eq 'ISTJ'}">
							i중에서 가장 사람을 좋아하고 활동적인 것을 좋아하는 유형입니다.<br>
							같이 있으면 좋은 자극을 받을 수 있는 성실하고 똑똑하며 밝은 사람을 좋아합니다.<br>
							알 수 없는 행동이나 이해할 수 없는 비도덕적인 행동을 정말 싫어합니다.<br>
						</c:when>
						<c:when test="${type02 eq 'ISFJ'}">
							착한 사람이 많은 유형이라 만만해보일 수 있지만 실은 참아주고 있을 가능성이 높습니다.<br>
							행동이나 노력에 대한 칭찬을 많이 해주고 진실된 모습을 꾸준히 보여준다면 친해질 수 있습니다.<br>
							급격한 변화를 좋아하지 않는 경우가 많아서 갑작스러운 만남이나 표현은 부담스러울 수 있습니다.<br>
						</c:when>
						<c:when test="${type02 eq 'INTJ'}">
							이 유형의 사람은 조언보다 위로를 필요로 하는 경우가 많습니다.<br>
							때문에 옆에서 한결같이 따뜻한 위로를 해주는 사람을 좋아합니다.<br>
						</c:when>
						<c:when test="${type02 eq 'INFJ'}">
							자신에게 의지하는 모습을 보이면 마음이 열려서 쉽게 친해질 수 있습니다.<br>
							남에게 상처주는 말을 함부로 뱉는 사람은 이 유형과 특히나 친해질 수 없습니다.<br>
						</c:when>
						<c:when test="${type02 eq 'ISTP'}">
							개인 영역을 존중해주고 부담스럽지 않게 자신의 부족함을 채워주는 사람에게 친밀감을 느낍니다.<br>
							감정적인 얘기, 생산성없는 얘기, 둘러말하는 것을 좋아하지 않는 경향이 있습니다.<br>
						</c:when>
						<c:when test="${type02 eq 'ISFP'}">
							다정하고 공감을 잘하며 겸손하지만 한편으로는 개인주의적이고 게으른 면이 있습니다.<br>
							뭐든 너무 과한 것을 싫어하는 경향이 있습니다.<br>
							단기간에 친해지려하거나 연락을 너무 자주하면 부담스러워합니다.<br>
						</c:when>
						<c:when test="${type02 eq 'INTP'}">
							관심사나 취향에 대한 이야기를 좋아하기 때문에 친해지려면 관심사와 취향을 아는 게 필수입니다.<br>
							예의없게 말하거나 무조건 친해지려고 부담스럽게 다가오는 사람과는 친해지기 어렵습니다.<br>
						</c:when>
						<c:when test="${type02 eq 'INFP'}">
							관심사가 같고 지지해주는 느낌을 준다면 금방 가까워질 수 있습니다.<br>
							관심사가 안 통하면 애초에 대화를 안 할 수도 있기 때문에 관심사가 맞는 게 좋습니다.<br>
							비도덕적이거나 선을 넘는 사람, 비판하는 사람은 친해지기 어렵습니다.<br>
						</c:when>
						<c:when test="${type02 eq 'ESTP'}">
							자신의 일을 잘하는 사람을 좋아하는데 실은, 무엇보다도 자기 맘에 드는 사람을 좋아합니다.<br>
							부정적인 면을 마주하기 어려워하는 경향이 있습니다.
							때문에 부정적인 사람 또는 부정적인 일에 대해 캐묻는 사람을 어려워합니다.<br>
						</c:when>
						<c:when test="${type02 eq 'ESFP'}">
							장난치기를 좋아하기 때문에 재밌는 사람을 좋아합니다.<br>
							자신의 얘기를 솔직하게 오픈하는 사람을 좋아합니다.<br>
						</c:when>
						<c:when test="${type02 eq 'ENTP'}">
							이 유형은 자부심이 뛰어난 경우가 많습니다.<br>
							자신의 의견에 동의해주고 자기 할 일 열심히 하는 멋진 사람을 좋아합니다.<br>
							오랫동안 옆에서 봐온 익숙한 사람일수록 친해지기 쉽습니다.<br>
							논쟁에서 지는 것을 좋아하지 않으며 무조건 우기고 반대하는 사람과는 친해지기 힘듭니다.<br>
						</c:when>
						<c:when test="${type02 eq 'ENFP'}">
							사람을 좋아하기 때문에 누구에게나 호의적으로 대하며 평화지향적인 인간관계를 형성합니다.<br>
							잘 들어주고 문제의 '해결'보다는 문제를 마주한 상대의 '감정'에 공감을 해주는 사람을 좋아합니다.<br>
							이기적이고 배려없는 사람을 싫어하고 대화가 잘 통하지 않는 사람과 함께 있는 것을 힘들어 합니다.<br>
						</c:when>
						<c:when test="${type02 eq 'ESTJ'}">
							자신의 일에 책임감있게 잘하며 성실하고 배울 점이 많은 사람을 좋아합니다.<br>
							관계를 통해 성장해나가길 원하기 때문에 자신이 챙겨줘야 하는 사람과는 친해지기 어렵습니다.<br>
						</c:when>
						<c:when test="${type02 eq 'ESFJ'}">
							개인주의적인 성향이 있지만 챙겨주고 표현하는 걸 좋아합니다.<br>
							또한 기댈 수 있고 이야기를 잘 들어주는 사람을 좋아합니다.<br>
							자신에게 맞지 많은 것을 요구하거나 통제하려고 하면 친해질 수 없습니다.<br>
							고마워할 줄 모르거나 거짓말을 자주하는 등 도덕적 기준이나 가치관에 반하면 친해지기 어렵습니다.<br>
						</c:when>
						<c:when test="${type02 eq 'ENTJ'}">
							타고난 리더쉽을 기반으로 활동적이고 에너지가 넘치며 자기 자신을 가장 중요하게 생각합니다.<br>
							이 유형의 사람이 가지고 있는 기대치의 지적 수준을 만족한다면 친해지기 수월합니다.<br>
							그렇다고 매사에 진지하기보다 적당히 유머스럽고 대화코드가 잘 맞는 사람을 좋아합니다.<br>
							한 번에 친해지는 경우는 거의 없고 게으른 모습을 보여주지 않는 것이 좋습니다.<br>
						</c:when>
						<c:when test="${type02 eq 'ENFJ'}">
							누군가에게 영향력을 끼치는 것을 좋아하는 유형입니다.<br>
							관심사가 같으면 금방 가까워질 수 있습니다.<br>
							가치관이 다르거나 잘못된 생각에 대해 고집 부리는 사람과 함께 있는 걸 힘들어합니다.<br>
						</c:when>
					</c:choose>
				</div>
			</article>
			<a id="create-kakao-link-btn" href="javascript:;">
  				<img src="https://developers.kakao.com/assets/img/about/logos/kakaolink/kakaolink_btn_medium.png"
    				alt="카카오링크 보내기 버튼"/>
			</a>
			<article id="commentWrap">
					<input type="text" class="mbtiComment" name="mbtiComment" placeholder="코멘트는 100자 내로 입력하세요."/> 
					<input type="submit" class="submitBtn" value="등록" onclick="addComment(this.form); return false;">
				<c:choose>
					<c:when test="${not empty mcInfo.size()}">
						<div id="cBoxWrap">
							<table>
								<thead>
									<tr>
										<th>프로필</th>
										<th>닉네임</th>
										<th>MBTI</th>
										<th>코멘트</th>
									</tr>
								</thead>
								<tbody id="tbody">
									<c:forEach var="commentTable" items="${mcInfo}">
										<tr>
											<td><div class="profileImg" style="background: url(${commentTable.member.profileImg}) 0 0 / cover"></div></td>								
											<td>${commentTable.member.nickName}</td>
											<td><span>${type01}</span> + <span>${type02}</span></td>
											<td>${commentTable.comment}</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
							<c:if test="${mcInfo.size() == 0}">
								<div class="nullComment">아직 등록된 코멘트가 없어요!<br>첫 코멘트를 작성해주세요.</div>
							</c:if>
						</div>
					</c:when>
				</c:choose>
			</article>
		</form>
	</div>

	<script src="resource/js/mbtiMatch/share.js" charset="utf-8"></script>
	<script>
		$('#nav li:nth-of-type(4)').addClass('clicked');
		
		var loginId;
		loginId = ${loginId}
		
		console.log(loginId);
		//등록하기 버튼 누르면 실행
		function addComment(form) {
			
			var comment = form.mbtiComment.value;
		
			if(loginId == "" || loginId == null) {
				
				alert("코멘트 기능은 로그인 후 이용이 가능합니다.");
				$('.mbtiComment').val('');
				
				return;
				
			} else {
				
				if(comment.length > 100) {
					alert("코멘트는 100자 이하로 작성하세요.");
				
				} else if(comment.length == 0 | comment.length == null) {
					alert("코멘트를 작성하세요.");
					
				} else{
					
					var type01 = "${type01}";
					var type02 = "${type02}";
					var json = {
						"loginId" : "${loginId}",
						"type01" : "${type01}",
						"type02" : "${type02}",
						"comment" : comment,
					}
			
					$.ajax({
						url : "/myapp/mbtiMatch/addComment",
						type : "post",
						data : JSON.stringify(json),
						contentType : "application/json; charset=UTF-8",
						success : function(data) {
							var mcInfo = data["mcInfo"];
			
							if(!(mcInfo == "")) {
								$("tbody").empty();
								$(mcInfo).each(function() {
									
									$("tbody").append('<tr><td><div class="profileImg" style="background: url('+ $(this)[0].member.profileImg + ') 0 0 / cover"></div></td>' +
														  "<td>" + $(this)[0].member.nickName + "</td>" + 
													"<td><span>" + $(this)[0].type01 + "</span>" +
													 " + <span>" + $(this)[0].type02 + "</span></td>" +
														  "<td>" + $(this)[0].comment + "</td>"
									);			
								});				
							}
						}
					});
					$('.mbtiComment').val('');
					$('.nullComment').remove();
				}
			}
		}
			
		Kakao.Link.createScrapButton({
			  container: '#create-kakao-link-btn',
			  requestUrl: 'http://localhost:8090/myapp/mbtiMatch/resultMbtiMatch?type01=&type02=ESFP',
			  templateId: 69529,
		});
		
		Kakao.API.request({
			  url: '/v2/api/talk/memo/scrap/send',
			  data: {
			    request_url: 'http://localhost:8090/myapp/mbtiMatch/resultMbtiMatch?type01=ENTP&type02=ESFP',
			    template_id: 69529,
			  },
			  success: function(response) {
			  },
			  fail: function(error) {
			  },
			});
		
	</script>

</body>
</html>