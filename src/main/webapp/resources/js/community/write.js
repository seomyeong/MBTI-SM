$('#nav li:nth-of-type(3)').addClass('clicked');

function writeSubmit(form) {
	var title = form.title.value;
	var contents = form.contents.value;
	var pass = true;

	// 글 제목이 100자 이상일시
	if (title.length > 100) {
		$('#title').css("border-color", "lightcoral");
		$('#errorMsg1').text("제목은 100글자 이내로 입력하세요.");
		pass = false;
	} else if (title.length == 0) {
		$('#title').css("border-color", "lightcoral");
		$('#errorMsg1').text("제목을 입력해주세요.");
		pass = false;
	} else {
		$('#title').css("border-color", "#828282");
		$('#errorMsg1').text("");
	}

	// 글 내용이 1000자 이상일시 
	if (contents.length > 1000) {
		$('#contents').css("border-color", "lightcoral").attr("placeholder", "글내용은 1000글자 이내로 입력하세요.");
		$('#errorMsg2').text("글내용은 1000글자 이내로 입력하세요.");
		pass = false;
	} else if (contents.length == 0) {
		$('#contents').css("border-color", "lightcoral");
		$('#errorMsg2').text("글내용을 입력해주세요.");
		pass = false;
	} else {
		$('#contents').css("border-color", "#828282").attr("placeholder", "");
		$('#errorMsg2').text("");
	}
	
	// 컨트롤러로 보내기
	
	if(pass == true) {
		alert("작성하신 글이 성공적으로 게시되었습니다.");
		alert("게시글 작성으로 50맙이 적립되었습니다.");
		var formTag = document.getElementById("form");
          formTag.submit();
	}
}

function editBoardSubmit(form) {
	var title = form.title.value;
	var contents = form.contents.value;
	var pass = true;

	// 글 제목이 100자 이상일시
	if (title.length > 100) {
		$('#title').css("border-color", "lightcoral");
		$('#errorMsg1').text("제목은 100글자 이내로 입력하세요.");
		pass = false;
	} else if (title.length == 0) {
		$('#title').css("border-color", "lightcoral");
		$('#errorMsg1').text("제목을 입력해주세요.");
		pass = false;
	} else {
		$('#title').css("border-color", "#828282");
		$('#errorMsg1').text("");
	}

	// 글 내용이 1000자 이상일시 
	if (contents.length > 1000) {
		$('#contents').css("border-color", "lightcoral").attr("placeholder", "글내용은 1000글자 이내로 입력하세요.");
		$('#errorMsg2').text("글내용은 1000글자 이내로 입력하세요.");
		pass = false;
	} else if (contents.length == 0) {
		$('#contents').css("border-color", "lightcoral");
		$('#errorMsg2').text("글내용을 입력해주세요.");
		pass = false;
	} else {
		$('#contents').css("border-color", "#828282").attr("placeholder", "");
		$('#errorMsg2').text("");
	}
	
	// 컨트롤러로 보내기
	
	if(pass == true) {
		alert("작성하신 글이 성공적으로 수정되었습니다.");
		var formTag = document.getElementById("form");
          formTag.submit();
	}
}
	
function typingContents(form) {
	var contents = form.contents.value;

	if (!(contents.length == 0)) {
		$('#typingCount').text(contents.length + " 자 / 1000 자");
	} else {
		$('#typingCount').text('');
	}
}