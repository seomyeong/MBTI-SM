let commentLikesOr="";	
let commentDelOr="";
let commentIndex;

//댓글 n개 보기 클릭시, 슬라이드 토글효과
$('.commentWrap').hide();
$(document).on('click', '.dropComment', function(e){
	e.preventDefault();
	$(this).parents('.content').next().slideToggle(600);
})

//submit의 해당 index 번호(cultureboard) 알기

$(document).on('click', '.writeSubmit input', function(e){
	
	commentIndex = (($(this).parents('.commentWrap').index()) / 2) - 1;	
	e.preventDefault();
})


function writeCommentSubmit(form, boardId, loginId, e){
	let comment = form.comment.value;
	let boardIdForComment = boardId;
	let loginIdForComment = loginId;
	let passForComment = true;
	
	//댓글 글자수 없을때
	if(comment.length == 0){
		$(e).parent().prev().children('.commentErrorMsg').text("댓글을 입력해주세요");
		$(e).parent().prev().children('input').css('border-bottom','3px solid red'); 
		$(e).parent().prev().children('.commentErrorMsg').prev().attr("placeholder","글자를 작성해주세요");	
		passForComment = false;
	}
	else if(comment.length > 100){
		$(e).parent().prev().children('.commentErrorMsg').text("댓글은 100자 이내로 입력하세요");
		$(e).parent().prev().children('input').css('border-bottom','3px solid red');
		$(e).parent().prev().children('.commentErrorMsg').prev().attr("placeholder","글자를 작성해주세요");
		passForComment = false;
	}
	else{
		//$('#commentErrorMsg').prev().css(); //input태그 기본 보더바텀적용
		$(e).parent().prev().children('.commentErrorMsg').text("");
	}
	
	
	if(passForComment == true){
		alert("작성하신 댓글이 성공적으로 게시됩니다");
	
		let param = {
			'comment' : comment,
			'boardId' : boardIdForComment,
			'loginId' : loginIdForComment
		}
		
		$.ajax({
			type: "post",
			data: JSON.stringify(param),
			url: "/myapp/cultureBoard/successComment",
			contentType: "application/json; charset=UTF-8",
			success: function(data) {
				let cultureBoardComment = data["cultureBoardComment"];
				let loginId = data["loginId"];
				let boardId = data["boardId"];
				let commentNum = data["commentNum"];
				let likeComments = data["likeComments"];

				$('.commentReadWrap').eq(commentIndex).empty();
				
				$(e).parents('.commentWrap').prev().find('.dropComment').text("답글 " + commentNum + "개 보기");
				
				
				for(com in cultureBoardComment){
					
					let state03 = 0;
					for(lcm in likeComments){
						if(likeComments[lcm].id == cultureBoardComment[com].id){
									
						   	commentLikesOr = "<a href='#'" + " onclick='commentLikes("+ cultureBoardComment[com].id + ","+ loginId +",this); return false' class='cultureBoardComment_likes'></a>";
							state03 = 1;
						}	
					}
					if(state03 == 0){
		   				commentLikesOr = "<a href='#'" + " onclick='commentLikes("+ cultureBoardComment[com].id + ","+ loginId +",this); return false' class='cultureBoardComment_unlikes'></a>";
						state03 = 0;
					}	
					
					  
									
					
					if(loginId == cultureBoardComment[com].member.id){
					    commentDelOr =
					    "<div class='delIcon'>"+
					    "<a href='#' class='delCommentBtn' data-boardId ="+ boardId + " data-loginId="+ loginId + " data-commentId="+ cultureBoardComment[com].id +">X</a>"+
						"</div>";
					}else{
					    commentDelOr=
					    "<div class='delIcon'>"+
						"</div>";
					}					
					

					$('.commentReadWrap').eq(commentIndex).append(
						"<div class='commentRead'>" +
							"<div class='memberImg'"+
							"style='background: url(" + cultureBoardComment[com].member.profileImg + ")  0 0 / cover'></div>"+
						"<div class='memberWrapOfWrap'>"+
							"<div class='memberWrap'>" +
							"<div class='memberInfo'>" +
							"<span class='eachMember memberLv'>LV. " + cultureBoardComment[com].member.level + "</span>" +
							"<span class='eachMember memberNickname'>" + cultureBoardComment[com].member.nickName + "</span>" +
							"<span class='eachMember memberMbti'>" + cultureBoardComment[com].member.mbti + "</span>" +
							"</div>" +
							"<div class='memberComment'>" + cultureBoardComment[com].comment + "</div>" +	
							"</div>" +
							"<div class='memberLike'>" + 
							commentLikesOr +
							"<p class ='commentLikesCount'>" + cultureBoardComment[com].likes + "</p>" +
							"</div>"+
							"<div class='memberDelete'>"+
							commentDelOr
							  	
					);	

				}
			}
		});
	
	}	
}




/////댓글의 좋아요 기능////////////

function commentLikes(boardId, loginId, e){
	let commentId = boardId;
	let commentLoginId = loginId;
	
	let param = {
		'commentId' : commentId,
		'loginId' : commentLoginId
	}
	
	$.ajax({
			type: "post",
			data: JSON.stringify(param),
			url: "/myapp/cultureBoard/commentLikes",
			contentType: "application/json; charset=UTF-8",
			success: function(data) {
				
				if(data["likeCheck"] == "false"){
					$(e).addClass('cultureBoardComment_likes');
					$(e).removeClass('cultureBoardComment_unlikes');
					$(e).next().text(data["likes"]);
				}else{
					$(e).addClass('cultureBoardComment_unlikes');
					$(e).removeClass('cultureBoardComment_likes');
					$(e).next().text(data["likes"]);
					
				}
			}
	});
}




//////댓글 삭제기능///////
$(document).on('click', '.delCommentBtn', function(e){
	e.preventDefault();
	let commentIndex = (($(this).parents('.commentWrap').index()) / 2) - 1;
	
	let boardId = $(this).attr("data-boardId");
	let commentId = $(this).attr("data-commentId");
	let loginId = $(this).attr("data-loginId");
	
	
	let param = {
		'boardId' : boardId,
		'loginId' : loginId,
		'commentId' : commentId
	}
	
	$.ajax({
		type: 'post',
		data: JSON.stringify(param),
		url: "/myapp/cultureBoard/delComment",
		contentType: "application/json; charset=UTF-8",
		success: function(data){
				let cultureBoardComment = data["cultureBoardComment"];
				let loginId = data["loginId"];
				let boardId = data["boardId"];
				let likeComments = data["likeComments"];
				
				
				$('.commentReadWrap').eq(commentIndex).empty();
				
				for(com in cultureBoardComment){
					
					let state03 = 0;
					for(lcm in likeComments){
						if(likeComments[lcm].id == cultureBoardComment[com].id){
									
						   	commentLikesOr = "<a href='#'" + " onclick='commentLikes("+ cultureBoardComment[com].id + ","+ loginId +",this); return false' class='cultureBoardComment_likes'></a>";
							state03 = 1;
						}	
					}
					if(state03 == 0){
		   				commentLikesOr = "<a href='#'" + " onclick='commentLikes("+ cultureBoardComment[com].id + ","+ loginId +",this); return false' class='cultureBoardComment_unlikes'></a>";
						state03 = 0;
					}	
									
					
					if(loginId == cultureBoardComment[com].member.id){
					  	commentDelOr =
					    "<div class='delIcon'>"+
					    "<a href='#' class='delCommentBtn' data-boardId ="+ boardId + " data-loginId="+ loginId + " data-commentId="+ cultureBoardComment[com].id +">X</a>"+
						"</div>";
					}else{
					    commentDelOr =
					    "<div class='delIcon'>"+
						"</div>";
					}					
					

					$('.commentReadWrap').eq(commentIndex).append(
						"<div class='commentRead'>" +
							"<div class='memberImg'"+
							"style='background: url(" + cultureBoardComment[com].member.profileImg + ")  0 0 / cover'></div>"+
						"<div class='memberWrapOfWrap'>"+
							"<div class='memberWrap'>" +
							"<div class='memberInfo'>" +
							"<span class='eachMember memberLv'>LV. " + cultureBoardComment[com].member.level + "</span>" +
							"<span class='eachMember memberNickname'>" + cultureBoardComment[com].member.nickName + "</span>" +
							"<span class='eachMember memberMbti'>" + cultureBoardComment[com].member.mbti + "</span>" +
							"</div>" +
							"<div class='memberComment'>" + cultureBoardComment[com].comment + "</div>" +	
							"</div>" +
							"<div class='memberLike'>" + 
							commentLikesOr +
							"<p class ='commentLikesCount'>" + cultureBoardComment[com].likes + "</p>" +
							"</div>"+
							"<div class='memberDelete'>"+
							commentDelOr
							  	
					);	

				}			
		}
	})
	
})



function timeForToday02(value) {
	const today = new Date();
	const timeValue = new Date(value);

	const betweenTime = Math.floor((today.getTime() - timeValue.getTime()) / 1000 / 60);
	if (betweenTime < 1) return '방금 전';
	if (betweenTime < 60) {
		return `${betweenTime}분 전`;
	}

	const betweenTimeHour = Math.floor(betweenTime / 60);
	if (betweenTimeHour < 24) {
		return `${betweenTimeHour}시간 전`;
	}

	const betweenTimeDay = Math.floor(betweenTime / 60 / 24);
	if (betweenTimeDay < 365) {
		return `${betweenTimeDay}일 전`;
	}

	return `${Math.floor(betweenTimeDay / 365)}년 전`;
}

$('.cultureBoard_reportDate').each(function() {
	
	let formatDate = timeForToday02($(this).text());
	$(this).text(formatDate);
});


$('.comment_reportDate').each(function() {
	let formatDate = timeForToday02($(this).text());
	$(this).text(formatDate);
});








