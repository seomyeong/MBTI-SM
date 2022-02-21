let pass = false;
let type01, type02;

/*
* 비회원 및 로그인한 회원이 type01 버튼을 클릭할 경우
*/
	$('.type01').on('click', function() {
		
		$('.type01').each(function() {
			$(this).prop("checked", "false");
		});
		
		$(this).prop("checked", "true");	
	});
	
	$('.type02').on('click', function() {
		
		$('.type02').each(function() {
			$(this).prop("checked", "false");
		});
		
		$(this).prop("checked", "true");
	});
	
	// 결과보기 버튼을 클릭할 경우
	
	$(".submitBtn").on("click", function() {
		
		checkSelectType(); // 선택이 제대로 되었는지 확인하는 함수
		
		if(pass == true) { // 선택이 제대로 되었다면
			$('.type01').each(function() { // .type01을 하나씩 돌려보기.
				if($(this).prop("checked") == true) { // 버튼의 속성 'checked'가 true라면
					type01 = $(this).val(); // 버튼의 value값을 type01이라는 변수에 저장.
				}
			});
			$('.type02').each(function() { // 선택이 제대로 되었다면
				if($(this).prop("checked") == true) { // .type02를 하나씩 돌려보기.
					console.log("test2 : " + $(this).val()); // 버튼의 속성 'checked'가 true라면
					type02 = $(this).val(); // 버튼의 value값을 type02라는 변수에 저장.
				}	
			});
			
			location.href = "resultMbtiMatch?type01="+type01+"&type02="+type02; // resultMbtiMatch 페이지로 이동.
		}
	});	
	
	
	function checkSelectType() {
		
		let state1 = false;
		let state2 = false;
	
		$(".type01").each(function() {
			
			if($(this).prop("checked") == true) {
				state1 = true;
			}
		
		});
		
		$(".type02").each(function() {
			
			if($(this).prop("checked") == true) {
				state2 = true;
			}
		});
		
		if(state1 == true && state2 == true) {
			pass = true;
		} else if(state1 == false && state2 == true) {
			alert("나의 타입을 선택해주세요.");
			pass = false;
		} else if(state1 == true && state2 == false) {
			alert("상대방의 타입을 선택해주세요.");
			pass = false;
		} else {
			alert("모든 타입을 선택해주세요.");
			pass = false;
		}
	}