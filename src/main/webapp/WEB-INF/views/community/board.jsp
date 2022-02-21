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
	href="<%=request.getContextPath()%>/resources/css/community/board.css">
<script
	src="<%=request.getContextPath()%>/resources/js/jquery-3.6.0.min.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/community/board.js" defer></script>
</head>
<body>
<jsp:include page="/resources/incl/header.jsp"></jsp:include>
	<jsp:include page="/resources/incl/nav.jsp"></jsp:include>
	<div id="main">
		<div id="boardInfoWrap">
			<h2 id="board_title">${board.title}</h2>
			<div id="board_info">
				<span id="board_mbti"><span class="mbtiImg" style="background: url(${board.member.profileImg}) 0 0 / cover"></span>${board.member.mbti}</span> <span
					id="board_level">Lv. ${board.member.level}</span> <span
					id="board_nickName">${board.member.nickName}</span>
				<div id="rightInfo">
					<span id="board_views">조회 ${board.views}</span> <span
						id="board_reportingDate">${board.reportingDate}</span>
					<c:if test="${board.member.id eq sessionScope.loginId}">
						<span id="board_edit"><a href="${board.id}">수정</a></span>
						<span id="board_delete"><a href="${board.id}">삭제</a></span>
					</c:if>
				</div>
			</div>
			<div id="contentsWrap">
				<pre id="board_contents">${board.contents}</pre>
			</div>
			<a href="javascript:likes()" id="board_likes"><span><ion-icon
						name="thumbs-up-outline"></ion-icon></span><span id="likesCount">${board.likes}</span></a>
			<span id="errorMsg"></span> <a
				href="mainCommunity?type=${sessionScope.type}&q=${sessionScope.q}&page=${sessionScope.pagingVO.page}&range=${sessionScope.pagingVO.range}"
				id="contentsList">목록으로</a>
			<div id="ad1">
				<a
					href="https://play.google.com/store/apps/details?id=com.varxyz.demofico"
					id="download"></a>
			</div>
			<div id="comments">
				<span data-boardId="${board.id}">댓글</span> 총 <span id="board_commentsCount">${board.commentsCount}</span> 개 <a id="refresh"><ion-icon  id="refresh_outline" name="refresh-outline"></ion-icon></a>
			</div>

			<!-- 댓글 영역 -->
			<div id="commentsWrap">
				<c:forEach var="comment" items="${comments}">
					<div class="comment">
						<div class="commentInfo">
							<span class="comment_mbti"><span class="mbtiImg" style="background: url(${comment.member.profileImg}) 0 0 / cover"></span>${comment.member.mbti}</span> <span
								class="comment_profile"><span class="comment_level">Lv.
									${comment.member.level}</span><span class="comment_nickName">${comment.member.nickName}</span></span>
							<span class="comment_reportingDate">${comment.reportingDate}</span>
							<c:if test="${board.member.id eq comment.member.id}">
								<span class="comment_me">작성자</span>
							</c:if>
							<c:if test="${not (sessionScope.loginId eq null)}">
								<span class="comment_plusCommentToggle"><a href="${comment.id}" class="comment_plusCommentToggleA">답글 쓰기</a></span>
							</c:if>
							<span class="commentInfo_right"> <c:if
									test="${sessionScope.loginId eq comment.member.id}">
									<span class="comment_delete"><a href="${comment.id}"><ion-icon
												name="close-outline"></ion-icon></a></span>
								</c:if> <%-- <span>추천 ${comment.likes}</span> --%>
							</span>
						</div>
						<p class="comment_text">${comment.comments}</p>
						<c:forEach var="plus" items="${commentsPlus}">
							<c:if test="${plus.communityComments.id eq comment.id}">
								<div class="plusCommentView">
									<ion-icon name="return-down-forward-outline"></ion-icon>
									<span class="plusCommentView_mbti"><span class="mbtiImg" style="background: url(${plus.member.profileImg}) 0 0 / cover"></span>${plus.member.mbti}</span> 
									<span class="plusCommentView_profile">
										<span class="plusCommentView_level">Lv. ${plus.member.level}</span>
										<span class="plusCommentView_nickName">${plus.member.nickName}</span>
									</span>
									<span class="plusCommentView_reportingDate">${plus.reportingDate}</span>
									<c:if test="${board.member.id eq plus.member.id}">
										<span class="comment_me">작성자</span>
									</c:if>
									<span class="plusCommentInfo_right"> <c:if
											test="${sessionScope.loginId eq plus.member.id}">
											<span class="plusComment_delete"><a href="${plus.id}"><ion-icon
														name="close-outline"></ion-icon></a></span>
										</c:if> <%-- <span>추천 ${comment.likes}</span> --%>
									</span>
									<div class="plusCommentView_comments" style="white-space:pre;">${plus.comments}</div>
								</div>						
							</c:if>
						</c:forEach>				
					</div>
				</c:forEach>
			</div>

			<hr>

			<!-- 댓글 작성 -->
			<div id="comment_write">
				<c:choose>
					<c:when test="${loginMemberInfo eq null}">
						<div id="comment_info">
							<span id="comment_write_plzLogin"><span>댓글작성</span>은 <span>로그인</span>
								후 이용가능합니다.</span>
						</div>
					</c:when>
					<c:otherwise>
						<div id="comment_info">
							<span id="comment_info_first">${loginMemberInfo.mbti}</span> <span
								id="comment_info_second">Lv. ${loginMemberInfo.level}</span> <span
								id="comment_info_third">${loginMemberInfo.nickName}</span><span
								id="typingCount"></span>
						</div>
						<form>
							<input type="text" id="comment_text" name="comment_text"
								placeholder="댓글을 달아보세요!" oninput="typingContents(this.form)" />
							<input type="submit" value="등록"
								onclick="addComment(this.form); return false" />
						</form>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</div>

	<script>
		// EL의 값을 써야하기때문에 아래에서 작성
		function likes() {
			if ('${sessionScope.loginId}' == "" || '${sessionScope.loginId}' == null) {
				alert("추천기능은 로그인 후 이용이 가능합니다.");
				return;
			}

			var boardId = {
				'loginId' : '${sessionScope.loginId}',
				'boardId' : '${board.id}'
			};

			$.ajax({
				type : "post",
				data : JSON.stringify(boardId),
				url : "likes",
				contentType : "application/json; charset=UTF-8",
				success : function(data) {
					if (data["likeCheck"] == "false") {
						$('#likesCount').text(data["likes"]);
						$('#board_likes').find('span:eq(0)').find('ion-icon').css("top", "5px");
						setTimeout(function() {
							$('#board_likes').find('span:eq(0)').find('ion-icon').css("top", "0");
						}, 200);
					} else {
						$('#errorMsg').text("같은 글은 최대 3번까지 추천가능합니다.");
					}
					
				}
			});
		}

		// 댓글 작성시
		function addComment(form) {
			var comment = form.comment_text.value;
			if (comment.length == 0 || comment == null) {
				alert("작성할 내용이 없습니다.");
				return;
			} else if (comment.length > 200) {
				alert("댓글은 최대 200자까지 입력이 가능합니다.");
				return;
			}
			
			var param = {
				'loginId' : '${sessionScope.loginId}',
				'boardId' : '${board.id}',
				'comment' : comment,
			}

			// deleteComment부분 a태그 comment.id 설정
			$.ajax({
				type : "post",
				data : JSON.stringify(param),
				url : "addComment",
				contentType : "application/json; charset=UTF-8",
				success : function(data) {
					load_comments(data);
					
					$('#comment_text').val('');
					$('#typingCount').html('0 자 / 200 자');
					alert("댓글 작성으로 30맙이 적립되었습니다.");
				}
			});
		}

		
		
		// 대댓글 기능
		
		$(document).on('click', '.comment_plusCommentToggleA', function(e) {
			e.preventDefault();
			
			if(!($(this).parent().parent().parent().hasClass("plusCommentExtends"))) {
				var plusCommentBox = '<div class="plusCommentBox"><ion-icon name="return-down-forward-outline"></ion-icon><span class="plusComment_mbti">' + '${loginMemberInfo.mbti}' + '</span> <span class="plusComment_profile"><span class="plusComment_level">Lv.' + '${loginMemberInfo.level}' + '</span> <span class="plusComment_nickName">' + '${loginMemberInfo.nickName}' + '</span></span><form><input type="text" name="plusComment" placeholder="댓글을 입력해보세요!"/><input type="submit" onclick="return false;"/><a href="#" class="plusComment_submit">등록</a></form></div>';
			
				$(this).parent().parent().parent().append(plusCommentBox);
				$(this).parent().parent().parent().addClass("plusCommentExtends");
			} else {
				$(this).parent().parent().parent().removeClass("plusCommentExtends");
				$(this).parent().parent().parent().find('.plusCommentBox').remove();
			}
			
		});
		
		// 대댓글 등록

		$(document).on('click', '.plusComment_submit', function(e) {
			e.preventDefault();
			var commentId = $(this).parent().parent().parent().find('.commentInfo').find('.comment_plusCommentToggle').find('.comment_plusCommentToggleA').attr("href");
			var memberId = '${sessionScope.loginId}';
			var comments = $(this).parent().find('input').val();
			
			var param = {
				'boardId' : '${board.id}',
				'commentId' : commentId,
				'memberId' : memberId,
				'comments' : comments,
			}
			
			$.ajax({
				type: "post",
				data: JSON.stringify(param),
				url: "addPlusComment",
				contentType: "application/json; charset=UTF-8",
				success: function(data) {
					load_comments(data);
					
					$('#comment_text').val('');
					alert("댓글 작성으로 30맙이 적립되었습니다.");
				}
			});

		});
		
		// 댓글 삭제

		$(document).on('click', '.comment_delete a', function(e) {
			e.preventDefault();
			var boardId = $('#comments span').attr("data-boardId");
			var commentId = $(this).attr("href");

			var answer = confirm("해당 댓글을 삭제할까요?");

			if (answer == true) {

				var param = {
					'boardId' : boardId,
					'commentId': commentId,
				}

				$.ajax({
					type: "post",
					data: JSON.stringify(param),
					url: "deleteComment",
					contentType: "application/json; charset=UTF-8",
					success: function(data) {
						load_comments(data);
						
						alert("삭제가 완료되었습니다.");
					}
				});
			}
		});

		// 대댓글 삭제
		$(document).on('click', '.plusComment_delete a', function(e) {
			e.preventDefault();
			var boardId = $('#comments span').attr("data-boardId");
			var plusCommentId = $(this).attr("href");

			var answer = confirm("해당 댓글을 삭제할까요?");

			if (answer == true) {

				var param = {
					'boardId' : boardId,
					'plusCommentId': plusCommentId,
				}

				$.ajax({
					type: "post",
					data: JSON.stringify(param),
					url: "deletePlusComment",
					contentType: "application/json; charset=UTF-8",
					success: function(data) {
						load_comments(data);
						
						alert("삭제가 완료되었습니다.");
					}
				});
			}
		});
		
		// refresh 버튼 클릭시
		$('#refresh_outline').on('click', function() {
			var refresh_param = {
					'boardId' : ${board.id},
			}
			
			$.ajax({
				type : "post",
				data : JSON.stringify(refresh_param),
				url : "refresh",
				contentType : "application/json; charset=UTF-8",
				success : function(data) {
					load_comments(data);
					alert("댓글이 새로고침 되었습니다.");
				}
			});
		});
		
		// 실시간 추천
		
		var liveParam = {
			'boardId' : ${board.id},
		}
		
		setInterval(function() {
			var now_likes = $('#likesCount').html();
			$.ajax({
				type: "post",
				data: JSON.stringify(liveParam),
				url: "liveLikes",
				contentType: "application/json; charset=UTF-8",
				success: function(data) {
					if(now_likes != data["likes"]) {
						$('#likesCount').html(data["likes"]);
						$('#board_likes').find('span:eq(0)').find('ion-icon').css("top", "5px");
						setTimeout(function() {
							$('#board_likes').find('span:eq(0)').find('ion-icon').css("top", "0").css("background", "#fff");
						}, 200);
					}
				}
			});
		}, 500);
		
		// 댓글 부분만 다시 불러오기
		function load_comments(data) {
			$('#commentsWrap').empty();
			
			var datacc = data["cc"];
			var dataccp = data["ccp"];
			var commentsWrap = "";

			if(datacc.length != 0) {
				for(var cc in datacc) {
					commentsWrap += '<div class="comment"><div class="commentInfo"><span class="comment_mbti"><span class="mbtiImg" style="background: url('
											+ datacc[cc].member.profileImg
											+ ') 0 0 / cover"></span>'
											+ datacc[cc].member.mbti
											+ '</span> <span class="comment_profile"><span class="comment_level">Lv.'
											+ datacc[cc].member.level
											+ '</span><span class="comment_nickName">'
											+ datacc[cc].member.nickName
											+ '</span></span><span class="comment_reportingDate">'
											+ datacc[cc].reportingDate
											+ '</span>';
					if('${board.member.id}' == datacc[cc].member.id) {
						commentsWrap += '<span class="comment_me">작성자</span>';
					}
					commentsWrap += '<span class="comment_plusCommentToggle"><a href="'
											+ datacc[cc].id
											+ '" class="comment_plusCommentToggleA">답글 쓰기</a></span><span class="commentInfo_right"><span class="comment_delete"><a href="'
											+ datacc[cc].id 
											+ '"><ion-icon name="close-outline"></ion-icon></a></span></span></div><p class="comment_text">'
											+ datacc[cc].comments
											+ '</p>';
					if(dataccp.length != 0) {
						for(var ccp in dataccp) {
							if(dataccp[ccp].communityComments.id == datacc[cc].id) {
								commentsWrap += '<div class="plusCommentView"><ion-icon name="return-down-forward-outline"></ion-icon><span class="plusCommentView_mbti"><span class="mbtiImg" style="background: url('
									+ dataccp[ccp].member.profileImg
									+ ') 0 0 / cover"></span>'
									+ dataccp[ccp].member.mbti
									+ '</span><span class="plusCommentView_profile"><span class="plusCommentView_level">Lv. '
									+ dataccp[ccp].member.level
									+ '</span><span class="plusCommentView_nickName">'
									+ dataccp[ccp].member.nickName
									+ '</span></span><span class="plusCommentView_reportingDate">'
									+ dataccp[ccp].reportingDate
									+ '</span>';
								if(dataccp[ccp].member.id == '${board.member.id}') {
									commentsWrap += '<span class="comment_me">작성자</span>';
								}
								commentsWrap += '<span class="plusCommentInfo_right"> ';
								if('${sessionScope.loginId}' == dataccp[ccp].member.id) {
									commentsWrap += '<span class="plusComment_delete"><a href="'
												+ dataccp[ccp].id
												+ '"><ion-icon name="close-outline"></ion-icon></a></span>';
												
								}
								commentsWrap += '</span><div class="plusCommentView_comments">'
											+ dataccp[ccp].comments
											+ '</div></div>';
							}
						}
					}
					commentsWrap += '</div></div>';
				}
			}
			$('#commentsWrap').append(commentsWrap);
			$('#board_commentsCount').html(data["cb"].commentsCount);
			changeDate_comment();
			
		}
		
	</script>
</body>
</html>