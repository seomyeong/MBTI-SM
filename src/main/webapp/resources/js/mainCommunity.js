$('#nav li:nth-of-type(3)').addClass('clicked');

// mbti 필터 클릭시 애니메이션 처리
$('.toggle-switch .switch').on('click', function() {
	if ($(this).parent().children('input:eq(0)').attr('checked') == false || $(this).parent().children('input:eq(0)').attr('checked') == null) {
		$(this).parent().children('input:eq(0)').attr('checked', true);
	} else {
		$(this).parent().children('input:eq(0)').attr('checked', false);
	}
});


$('.contents').on('mouseenter', function() {
	$(this).css("background", "#f2f2f2");
}).on('mouseleave', function() {
	$(this).css("background", "#fff");
});


// mbti 필터 기능 처리
function checkMbti() {
	var type = "mbti";
	let mbtiInfo;
	let type01;
	let type02;
	let type03;
	let type04;

	if ($('.toggle-switch input[name="type01"]').attr('checked') == false || $('.toggle-switch input[name="type01"]').attr('checked') == null) {
		type01 = 'E';
	} else {
		type01 = 'I';
	}

	if ($('.toggle-switch input[name="type02"]').attr('checked') == false || $('.toggle-switch input[name="type02"]').attr('checked') == null) {
		type02 = 'N';
	} else {
		type02 = 'S';
	}

	if ($('.toggle-switch input[name="type03"]').attr('checked') == false || $('.toggle-switch input[name="type03"]').attr('checked') == null) {
		type03 = 'F';
	} else {
		type03 = 'T';
	}

	if ($('.toggle-switch input[name="type04"]').attr('checked') == false || $('.toggle-switch input[name="type04"]').attr('checked') == null) {
		type04 = 'P';
	} else {
		type04 = 'J';
	}

	mbtiInfo = type01 + type02 + type03 + type04;
	console.log("javascript에서 실행 -> mbtiInfo : " + mbtiInfo);
	
	// 게시판에 필터 적용 시키기 위해 get방식으로 정보를 컨트롤러로 전달
	
	location.href = "mainCommunity?type=" + type + "&q=" + mbtiInfo + "&page=1&range=1";


	/* ajax 처리 연습
	let param = {
		"type01": type01,
		"type02": type02,
		"type03": type03,
		"type04": type04,
	};

	$.ajax({ // 비동기 방식 (페이지가 넘어가지 않고 그 페이지에서 바로 자료가 변경됨)
		type: "post",
		data: JSON.stringify(param),
		url: "/test/community/mainCommunity",
		contentType: "application/json; charset=UTF-8",
		success: function(data) {
			document.querySelector('#viewName').innerHTML = data["view"];
		}
	}); */
}

// 전체 보기
function allView() {
	let param = {
		"allView": "all"
	}

	$.ajax({ // 비동기 방식 (페이지가 넘어가지 않고 그 페이지에서 바로 자료가 변경됨)
		type: "post",
		data: JSON.stringify(param),
		url: "mainCommunity",
		contentType: "application/json; charset=UTF-8",
		success: function(data) {
			if (data["view"] == "all") {
				document.querySelector('#viewName').innerHTML = "전체글";
			}
		}
	});
}

// 검색 기능
function search(form) {
	
	var type;
	var q = form.searchContents.value;
	
	if(form.selectType.value == "제목") {
		type = "title";
	} else if(form.selectType.value == "작성자") {
		type = "memberId";
	}
	
	location.href = "mainCommunity?type=" + type + "&q=" + q + "&page=1&range=1";
}

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
$('.reportingDate').each(function() {
	let formatDate = timeForToday($(this).text());
	$(this).text(formatDate);
	
});

// 글쓰기 페이지 이동
function goWrite() {
	var loginId = $('#write').attr("data-loginId");
	
	if(loginId == null || loginId == "") {
		alert("글쓰기는 로그인 후 이용이 가능합니다.");
		location.href = "write";
	} else {
		location.href = "write";
	}
	
	
}