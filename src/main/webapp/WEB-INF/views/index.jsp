<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>맙티</title> 
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/index.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/popup.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/comment.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/mbtiCard.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/indexForBanner.css">

<script src="<%=request.getContextPath()%>/resources/js/jquery-3.6.0.min.js" defer></script>
<script src="<%=request.getContextPath()%>/resources/js/indexForBanner.js" defer></script>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js" defer></script>
<script src="<%=request.getContextPath()%>/resources/js/jquery.bpopup-0.1.1.min.js" defer></script>
<script src="<%=request.getContextPath()%>/resources/js/popup.js" defer></script>
<script src="<%=request.getContextPath()%>/resources/js/like.js" defer></script>
<script src="<%=request.getContextPath()%>/resources/js/cultureBtn.js" defer></script>
<script src="<%=request.getContextPath()%>/resources/js/cultureBoardComment.js" defer></script>	
	
</head>
<body>
	<!-- // 기본양식 -->
	<jsp:include page="/resources/incl/nav.jsp"></jsp:include>
	<div id="main">
		<!-- 작성 구역 -->
		<article id="banner">
            <h2 class="hidden">배너</h2>
            <p>국내 최고<span>no.1</span> MBTI 커뮤니티 <span>맙티</span></p>
            <p id="subTitle">당신을 가장 이해할 수 있는 당신만의 BEST TEAM을 찾으세요.</p>            
            <div id="bannerAvatar">
                <img src="<%=request.getContextPath()%>/resources/img/avatar/MBTI_ENFJ.png" alt="enfj" class="avatar avatar1 enfj"></img>
                <img src="<%=request.getContextPath()%>/resources/img/avatar/MBTI_ENFP.png" alt="enfp" class="avatar avatar2 enfp"></img>
                <img src="<%=request.getContextPath()%>/resources/img/avatar/MBTI_ENTJ.png" alt="entj" class="avatar avatar3 entj"></img>
                <img src="<%=request.getContextPath()%>/resources/img/avatar/MBTI_ENTP.png" alt="entp" class="avatar avatar4 entp"></img>
                <img src="<%=request.getContextPath()%>/resources/img/avatar/MBTI_ESFJ.png" alt="esfj" class="avatar avatar5 esfj"></img>
                <img src="<%=request.getContextPath()%>/resources/img/avatar/MBTI_ESFP.png" alt="esfp" class="avatar avatar6 esfp"></img>
                <img src="<%=request.getContextPath()%>/resources/img/avatar/MBTI_ESTJ.png" alt="estj" class="avatar avatar7 estj"></img>
                <img src="<%=request.getContextPath()%>/resources/img/avatar/MBTI_ESTP.png" alt="estp" class="avatar avatar8 estp"></img>
                <img src="<%=request.getContextPath()%>/resources/img/avatar/MBTI_INFJ.png" alt="infj" class="avatar avatar9 infj"></img>
                <img src="<%=request.getContextPath()%>/resources/img/avatar/MBTI_INFP.png" alt="infp" class="avatar avatar10 infp"></img>
                <img src="<%=request.getContextPath()%>/resources/img/avatar/MBTI_INTJ.png" alt="intj" class="avatar avatar11 intj"></img>
                <img src="<%=request.getContextPath()%>/resources/img/avatar/MBTI_INTP.png" alt="intp" class="avatar avatar12 intp"></img>
                <img src="<%=request.getContextPath()%>/resources/img/avatar/MBTI_ISFJ.png" alt="isfj" class="avatar avatar13 isfj"></img>
                <img src="<%=request.getContextPath()%>/resources/img/avatar/MBTI_ISFP.png" alt="isfp" class="avatar avatar14 isfp"></img>
                <img src="<%=request.getContextPath()%>/resources/img/avatar/MBTI_ISTJ.png" alt="istj" class="avatar avatar15 istj"></img>
                <img src="<%=request.getContextPath()%>/resources/img/avatar/MBTI_ISTP.png" alt="istp" class="avatar avatar16 istp"></img>
            </div>
        </article>
		<div id="bottomSectionWrap">
			<section id="cultureContents">
				
				<div id="topBtn">
					<form id="cultureBtn" action="javascript:selectCulture()" method="post">
						<input type="submit" value="음악"/>
						<input type="submit" value="영화"/>
						<input type="submit" value="여행지"/>
					</form>
						
					<form action="javascript:ifSelectBtn()" method="POST" id="index02Toggle">
						<div id="type1" class="mbti-switch">
							<input type="checkbox" name="type01" />
							<div class="toggle"></div>
							<span class="front">E</span> <span class="back">I</span>
						</div>
						<div id="type2" class="mbti-switch">
							<input type="checkbox" name="type02" />
							<div class="toggle"></div>
							<span class="front">N</span> <span class="back">S</span>
						</div>
						<div id="type3" class="mbti-switch">
							<input type="checkbox" name="type03" />
							<div class="toggle"></div>
							<span class="front">F</span> <span class="back">T</span>
						</div>
						<div id="type4" class="mbti-switch">
							<input type="checkbox" name="type04" />
							<div class="toggle"></div>
							<span class="front">P</span> <span class="back">J</span>
						</div>
						<input type="submit" id="mbtiSubmit" class="mbtiSubmitOff" value="MBTI" />
					</form>
				</div>
				
				<div id="contentWrap">
					<div id="contentList">
						<div id="contentHead">
							<p>좋아요</p>
							<p>MBTI</p>
							<p>가수</p>
							<p>노래</p>
							<p>닉네임</p>
						</div>
							
						<c:forEach var="contents" items="${contents}">
							<div class="content">
								<div class="contentForm01">
									<div class="likeWrap">
										<!-- 해당 아이디로 추천유무로 추천아이콘 변경 -->
										<%
											int state01 = 0;
										%>
										<c:choose>
										
											<c:when test="${not (loginId eq null)}">
												
												<c:forEach var="likeContent" items="${likeContents}">
													
													<c:if test="${(likeContent.id eq contents.id)}">
														<a href="javascript:likes()" 
														data-boardId ="${contents.id}" 
														data-loginId="${loginId}"
														class="cultureBoard_likes"></a>
														
														<% state01 = 1; %>
													</c:if>
														
												</c:forEach>
												
													<%if(state01 == 0) {%>
														<a href="javascript:likes()" 
														data-boardId ="${contents.id}" 
														data-loginId="${loginId}"
														class="cultureBoard_unlikes"
														></a>
														<% state01 = 0; %>
													<%}%>
										
											</c:when>
											
											
											<c:otherwise>
												<a href="#" 
												class="cultureBoard_unlikes"></a>
											</c:otherwise>
										
										
										</c:choose>
										
										
										<p class="likesCount">${contents.likes}</p>
									</div>
									
									<div>${contents.member.mbti}</div>
									<div>${contents.contents01}</div>
									<div id="eachContent">${contents.contents02}</div>
									<div id="eachNick">${contents.member.nickName}</div>
								</div>
								
								<div class="contentForm02">
									<div class="commentTd">
										<a class="dropCommentWrap" href="#">
											<span class="angleDown"></span>
											<span class="dropComment">답글 ${contents.commentNum}개 보기</span>
										</a>
									</div>
									<div class="linkTd">
										<a class="linkTag" href="${contents.link}" target="_blank">
											<div class="contentMent">${contents.title}</div>
											<span class="linkIconMusic"></span>	
										</a>
									</div>
									<div class="cultureBoard_reportDate">${contents.reportingDate}</div>
								</div>
							</div>
							
							<!-- dropComment 누르면 보여지는 부분 -->
							<div class="commentWrap">
								<div class="comment">
									
									
									<div class="commentWrite">
										<c:choose>
											<c:when test="${not (memberInfo eq null)}">
												<div class="authorImg" style="background: url(${memberInfo.profileImg}) 0 0 / cover" ></div> 
											</c:when>
											<c:otherwise>
												<div class="authorImg"></div>
											</c:otherwise>
										</c:choose>		
										<div class="authorWrap">
											<div class="authorInfo">	
												<span class="authorLv">LV.${memberInfo.level}</span>
												<span class="authorNickname">${memberInfo.nickName}</span>
												<span class="authorMbti">${memberInfo.mbti}</span>
											</div>
											<form class="writeTextForm">
												<div class="writeText">
													<input type="text" name="comment" placeholder="공개글 100자 이내 작성하기" maxlength="102" autocomplete="off"/>
													<span class="commentErrorMsg"></span>
												</div>
												<c:if test="${not (memberInfo eq null)}">
													<div class="writeSubmit">
														<input type="button" value="댓글" onclick="writeCommentSubmit(this.form,'${contents.id}','${sessionScope.loginId}',this); return false"/>
													</div>
												</c:if>
												<div class="writeSubmit"></div>
											</form>
										</div>
									</div>
										
										
										

									<div class="commentReadWrap">
									<c:forEach var="cultureBoardComment" items="${cultureBoardComment}">
									<c:if test="${contents.id eq cultureBoardComment.cultureBoard.id}">
									<div class="commentRead">
										<div class="memberImg" style="background: url(${cultureBoardComment.member.profileImg}) 0 0 / cover"></div>
										<div class="memberWrapOfWrap">
											<div class="memberWrap">
												<div class="memberInfo">
													<span class="eachMember memberLv">LV. ${cultureBoardComment.member.level}</span>
													<span class="eachMember memberNickname">${cultureBoardComment.member.nickName}</span>
													<span class="eachMember memberMbti">${cultureBoardComment.member.mbti}</span>
													<span class="eachMember comment_reportDate">${cultureBoardComment.reportingDate}</span>
												</div>
												<div class="memberComment">${cultureBoardComment.comment}</div>	
											</div>
											
											<div class="memberLike">
											
											<%
											int state02 = 0;
											%>
											
											<c:choose>
												
												
												<c:when test="${not (memberInfo eq null)}">
												
													<c:forEach var="likeComment" items="${likeComments}">
													
														<c:if test="${(likeComment.id eq cultureBoardComment.id)}">
															<a href="#" 
															onclick="commentLikes('${cultureBoardComment.id}','${loginId}',this); return false"
															class="cultureBoardComment_likes">
															</a>
														
															<% state02 = 1; %>
														</c:if>
													
													</c:forEach>						
												
													<%if(state02 == 0) {%>
														<a href="#" 
														onclick="commentLikes('${cultureBoardComment.id}','${loginId}',this); return false"
														class="cultureBoardComment_unlikes"
														>
														</a>
														<% state02 = 0; %>
													<%}%>
																	
												</c:when>
												
												<c:otherwise>
													<a href="#"
													class="cultureBoardComment_unlikes"></a>	
												</c:otherwise>
											
											</c:choose>
											
											<p class="commentLikesCount">${cultureBoardComment.likes}</p>
											</div>
											
											
											<div class="memberDelete">
												<c:choose>
													<c:when test="${not (memberInfo eq null)}">
														
														<c:if test="${loginId eq cultureBoardComment.member.id}">
															<div class="delIcon">
																<a href="#" class="delCommentBtn" data-boardId = "${contents.id}" data-commentId ="${cultureBoardComment.id}" data-loginId="${sessionScope.loginId}">X</a>
															</div>
														</c:if>
														
													</c:when>
													<c:otherwise>
														<div class="delIcon"></div>
													</c:otherwise>
												</c:choose>
											</div>
										</div>
									</div>
									</c:if>
									</c:forEach>
									</div>
								</div>
							</div>
						</c:forEach>
						</div>
					</div>
				<div id="bottomBtn">
					<!-- cultureBoard/index GET에다가 session값 mav로 넘김 -->
					<a href="#" id="my-button" onclick="goWriteToLogin();" data-loginId="${loginId}">글쓰기</a>
					<div id="newbestBtn">
						<a href="javascript:selectBestComment()" id="selectNew">댓글순</a>
						<a href="javascript:orderLikes()" id="selectBest">추천순</a>
					</div>
				</div>
			</section>
            <section id="mbtiCard">
                <h3 class="hidden">mbtiCard</h3>
                <div id="mbtiCardTxt">
                    <!-- <p>What's your Best Team?</p> -->
                </div>
                <div id="mbtiImg"></div>
                <div id="mbtiDeco"></div>
                <ul class="cardGroup analysisType">
                    <li class="cardTitle">분석형
                        <ul>
                            <li>INTJ</li>
                            <li>INTP</li>
                            <li>ENTJ</li>
                            <li>ENTP</li>
                        </ul>
                    </li>
                </ul>
                <ul class="cardGroup diplomacyType">
                    <li class="cardTitle">외교형
                        <ul>
                            <li>INFJ</li>
                            <li>INFP</li>
                            <li>ENFJ</li>
                            <li>ENFP</li>
                        </ul>
                    </li>
                </ul>
                <ul class="cardGroup administratorType">
                    <li class="cardTitle">관리자형
                        <ul>
                            <li>ISTJ</li>
                            <li>ISFJ</li>
                            <li>ESTJ</li>
                            <li>ESFJ</li>
                        </ul>
                    </li>
                </ul>
                <ul class="cardGroup explorerType">
                    <li class="cardTitle">탐험가형
                        <ul>
                            <li>ISTP</li>
                            <li>ISFP</li>
                            <li>ESTP</li>
                            <li>ESFP</li>
                        </ul>
                    </li>
                </ul>
            </section>
		</div>

	</div>
	
<!--****************************************팝업 폼****************************************-->
		<div id="element_to_pop_up">
			<div id="popupHead">
				<div id="popupBtnWrap">
					<a href="#" class="popupBtn" id="musicBtn" >음악</a>
					<a href="#" class="popupBtn" id="movieBtn">영화</a>
					<a href="#" class="popupBtn" id="tripBtn">여행지</a>
				</div>
			</div>
			
			<form action="successWrite" method="POST" class="popupLayout">
				<div id="popupMain">
					<input type="text" name="contentType" value="M" class="hidden">
					<label for="content01"># 아티스트</label>
					<div class="forAfter"><input type="text" name="contents01" id="content01" maxlength="50"></div>
					<span id="errorMsgArtist"></span>
					<label for="content02"># 노래</label>
					<div class="forAfter"><input type="text" name="contents02" id="content02" maxlength="50"></div>
					<span id="errorMsgSong"></span>
					<label for="link"># 공유URL</label>
					<div class="forAfter"><input type="text" name="link" class="link"></div>
					<span class="errorMsgLink"></span>
					<label for="title"># 추천멘트</label>
					<textarea name="title" id="title" cols="30" rows="5" placeholder="100자 내외로 작성해주세요"></textarea>	
					<span class="errorMsgMent"></span>
				</div>
				<div id="popupFooter">
					<input type="submit" class="write" value="글쓰기" onclick="writeSubmit(this.form); return false" />
				</div>		
			</form>
			
			<form action="successWrite" method="POST" class="popupLayout">
				<div id="popupMain">
					<input type="text" name="contentType" value="C" class="hidden">
					<label for="content01"># 영화</label>
					<div class="forAfter"><input type="text" name="contents01" id="content01" maxlength="50"></div>
					<span id="errorMsgMovie"></span>
					<label for="link"># 공유URL</label>
					<div class="forAfter"><input type="text" name="link" class="link"></div>
					<span class="errorMsgLink"></span>
					<label for="title"># 추천멘트</label>
					<textarea name="title" id="title" cols="30" rows="5" placeholder="100자 내외로 작성해주세요"></textarea>
					<span class="errorMsgMent"></span>
				</div>
				<div id="popupFooter">
					<input type="submit" class="write" value="글쓰기" onclick="writeSubmit(this.form); return false" />
				</div>
			</form>

			<form action="successWrite" method="POST" class="popupLayout">
				<div id="popupMain">
					<input type="text" name="contentType" value="T" class="hidden">
					<label for="content01"># 여행지</label>
					<div class="forAfter"><input type="text" name="contents01" id="content01" maxlength="50"></div>
					<span id="errorMsgTrip"></span>
					<label for="link"># 공유URL</label>
					<div class="forAfter"><input type="text" name="link" class="link"></div>
					<span class="errorMsgLink"></span>
					<label for="title"># 추천멘트</label>
					<textarea name="title" id="title" cols="30" rows="5" placeholder="100자 내외로 작성해주세요"></textarea>
					<span class="errorMsgMent"></span>
				</div>
					<div id="popupFooter">
					<input type="submit" class="write" value="글쓰기" onclick="writeSubmit(this.form); return false" />
				</div>
			</form>
			<a href="index" class="b-close" id="popupClose">X</a>
		</div>
	
		<!--***********팝업 폼******************-->
	
	<script type="module"
		src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.esm.js"></script>
	<script nomodule
		src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.js"></script>

</body>
</html>