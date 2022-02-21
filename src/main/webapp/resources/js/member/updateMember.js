$('#nav li:nth-of-type(6)').addClass('clicked');
/*------------------------------------
 ------------- 유효성 검사 -------------
------------------------------------*/
var pass = true;

function checkPattern(form) {
	var pattern1 = /[0-9]/; // 숫자 
	var pattern2 = /[a-zA-Z]/; // 문자 
	var pattern3 = /[~!@#$%^&*()_+|<>?:{}]/; // 특수문자 

	// 비밀번호 패턴 체크 (8자 이상, 문자, 숫자, 특수문자 포함여부 체크) 
	if (!pattern1.test(form.pw.value) || !pattern2.test(form.pw.value) || !pattern3.test(form.pw.value) || form.pw.value.length < 8) {
		$('.errorTxt').eq(1).text("8자리 이상 문자, 숫자, 특수문자로 구성하여야 합니다.");
		pass = false;
	} else {
		$('.errorTxt').eq(1).text("");
	}

	// 비밀번호 재확인
	var p1 = document.getElementById('password1').value;
	var p2 = document.getElementById('password2').value;

	if (p1 != p2) {
		$('.errorTxt').eq(2).text("비밀번호 불일치").css("color", "red");
		pass = false;
	} else {
		$('.errorTxt').eq(2).text("비밀번호가 일치합니다").css("color", "#000");
	}


	// 닉네임 검사 
	if (form.nickName.value.search(/[^(가-힣a-zA-Z0-9)]/) != -1) {
		$('.errorTxt').eq(4).text("잘못된 닉네임 입니다.").css("color", "red");
		pass = false;
	} else if (form.nickName.value.length < 2) {
		$('.errorTxt').eq(4).text("2자리 이상 입력해주세요.").css("color", "red");
		pass = false;
	}

	// 휴대전화 검사 
	if (form.phone.value.search(/[^0-9]/) != -1) {
		$('.errorTxt').eq(8).text("잘못된 번호형식 입니다.");
		pass = false;
	} else if (form.phone.value.length < 11) {
		$('.errorTxt').eq(8).text("11자리 입력해주세요.");
		pass = false;
	} else {
		$('.errorTxt').eq(8).text("");
	}


	/*------------------------------------
	 ------------- 공백 검사 -------------
	------------------------------------*/

	//비밀 번호 공백
	if (form.pw.value.search(/\s/) != -1) {
		alert("비밀번호에 공백은 들어갈 수 없습니다.");
		pass = false;
	}
	//이름 공백
	if (form.name.value.search(/\s/) != -1) {
		alert("이름에 공백은 들어갈 수 없습니다.");
		pass = false;
	}
	//닉네임 공백
	if (form.nickName.value.search(/\s/) != -1) {
		alert("닉네임에 공백은 들어갈 수 없습니다.");
		pass = false;
	}

	// 컨트롤러
	if (pass == true) {
		var formTag = document.getElementById("form");
		alert("입력하신 정보로 회원수정이 완료 되었습니다. ");
		formTag.submit();
	} else {
		alert("올바른 형식으로 작성해주세요.");
		pass = true;
	}

}



/*------------------------------------
 ------------- 중복 검사 -------------
------------------------------------*/

//닉네임 중복검사
function nickNameCheck(form) {
	var nickName = form.nickName.value;

	var param = {
		"nickName": nickName,
	}

	$.ajax({
		type: "post",
		data: JSON.stringify(param),
		url: "/myapp/member/nickNameCheck",
		contentType: "application/json; charset=UTF-8",
		success: function(data) {

			if (loginNickName == nickName) {
				pass = true;
			} else if (data["nickName"] == "") {
				$('.errorTxt').eq(4).html("");
				pass = false;
			} else if (data["msg"] == "중복되는 닉네임이 존재합니다." || data["msg"] == "2자리 이상 입력해주세요.") {
				$('.errorTxt').eq(4).html(data["msg"]).css("color", "red");
				pass = false;
			} else if (!(data["nickName"] == "")) {
				$('.errorTxt').eq(4).html(data["msg"]).css("color", "#000");
				pass = true;
			}
		}
	});
}


function decoMove() {
	$('#deco1').animate({ bottom: "30px" }, 2000).animate({ bottom: "0px" }, 2000)
	$('#deco2').animate({ top: "80px" }, 2500).animate({ top: "50px" }, 2500)
	$('#deco3').animate({ bottom: "180px" }, 3000).animate({ bottom: "200px" }, 3500)
}

setInterval(decoMove, 2000);