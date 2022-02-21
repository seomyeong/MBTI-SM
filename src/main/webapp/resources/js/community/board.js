$('#nav li:nth-of-type(3)').addClass('clicked');

// date 표시 바꾸기

function timeForToday(value) {
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


// date 표시 바꾸기 - 게시글, 댓글, 대댓글
function changeDate() {
	let formatDate_board = timeForToday($('#board_reportingDate').text());
	$('#board_reportingDate').text(formatDate_board);
	
	$('.comment_reportingDate').each(function() {
		let formatDate = timeForToday($(this).text());
		$(this).text(formatDate);
	
	});
	
	$('.plusCommentView_reportingDate').each(function() {
		let formatDate = timeForToday($(this).text());
		$(this).text(formatDate);
	
	});
}
function changeDate_comment() {
	
	$('.comment_reportingDate').each(function() {
		let formatDate = timeForToday($(this).text());
		$(this).text(formatDate);
	
	});
	
	$('.plusCommentView_reportingDate').each(function() {
		let formatDate = timeForToday($(this).text());
		$(this).text(formatDate);
	
	});
}
changeDate();

// 댓글 글자수 세기
function typingContents(form) {
	var contents = form.comment_text.value;

	if (!(contents.length == 0)) {
		$('#typingCount').text(contents.length + " 자 / 200 자");
	} else {
		$('#typingCount').text('');
	}
}

// 게시글 삭제

$('#board_delete a').on('click', function(e) {
	e.preventDefault();
	var boardId = $(this).attr("href");

	var answer = confirm("해당 게시글을 삭제할까요?");

	if (answer == true) {
		alert("삭제가 완료되었습니다.");
		location.href = "deleteBoard?boardId=" + boardId;
	}
});

// 게시글 삭제

$('#board_edit a').on('click', function(e) {
	e.preventDefault();
	var boardId = $(this).attr("href");

	var answer = confirm("해당 게시글의 수정 페이지로 이동합니다.");

	if (answer == true) {
		location.href = "editBoard?boardId=" + boardId;
	}
});


