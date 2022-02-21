;(function($) {
    $(function() {
        
        $('#my-button').bind('click', function(e) {
            e.preventDefault();
            $('#element_to_pop_up').bPopup({
               
            });
        });        
     });
 })(jQuery);

/**뒤로가기 시 자동팝업 구현 차단 */
$(window).bind("pageshow", function(event){
	if(event.originalEvent.persisted){
		$('#element_to_pop_up').bPopup().close();
	}
})


let popContentType = "M";
 
$('.popupBtn').eq(0).addClass('btnClick');
$('.popupLayout').css('display','none');
$('.popupLayout').eq(0).css('display','flex');

$(document).on('click','.popupBtn',function(e){
   e.preventDefault();
	$(this).addClass('btnClick');
   $('.popupBtn').not($(this)).removeClass('btnClick');
   
   if($(this).text() == "음악"){
	popContentType = "M";
        $('.popupLayout').css({
            'display' : 'none'
        });
        $('.popupLayout').eq(0).css('display','flex');
       
   }
   if($(this).text() == "영화"){
	popContentType = "C";
        $('.popupLayout').css({
            'display' : 'none'
        });
        $('.popupLayout').eq(1).css('display','flex');
   
    }
    if($(this).text() == "여행지"){
	popContentType = "T";
        $('.popupLayout').css({
            'display' : 'none'
        });
        $('.popupLayout').eq(2).css('display','flex');   
    }
    
})


function writeSubmit(form){
	var contentType = form.contentType.value;
	var contents01 = form.contents01.value;
	if(popContentType == "M"){
		var contents02 = form.contents02.value;
	}else{
		var contents02 = "";
	}
	var link = form.link.value;
	var title = form.title.value;	
	var pass = true;
	

		
	if(popContentType == "M"){
		/****************************************** */
		if(contents01.length == 0){
			$('#errorMsgArtist').css('color','red');
			$('#errorMsgArtist').text("아티스트명을 작성해주세요");
			pass = false;
		}
		else if(contents01.length > 50){
			$('#errorMsgArtist').css('color','red');
			$('#errorMsgArtist').text("50글자 이내로 작성해주세요");
			pass = false;
		}
		else{
			$('#errorMsgArtist').text("");	
		}
		
		/****************************************** */
		
		if(contents02.length == 0){
			$('#errorMsgSong').css('color','red');
			$('#errorMsgSong').text("노래명을 작성해주세요");
			pass = false;
		}
		else if(contents02.length > 50){
			$('#errorMsgSong').css('color','red');
			$('#errorMsgSong').text("50글자 이내로 작성해주세요");
			pass = false;
		}
		else{
			$('#errorMsgSong').text("");	
		}
		/****************************************** */
		if(link.length == 0){
			$('.errorMsgLink').eq(0).css('color','red');
			$('.errorMsgLink').eq(0).text("링크를 작성해주세요");
			pass = false;
		}
		else{
			$('.errorMsgLink').eq(0).text("");	
		}
		/****************************************** */
		if(title.length > 100){
			$('.errorMsgMent').eq(0).css('color','red');
			$('.errorMsgMent').eq(0).text("100글자 이내로 작성해주세요");
			pass = false;
		}
		else if(title.length == 0){
			$('.errorMsgMent').eq(0).css('color','red');
			$('.errorMsgMent').eq(0).text("추천멘트를 작성해주세요");
			pass = false;
		}
		else{
			$('.errorMsgMent').eq(0).text("");	
		}
		/****************************************** */
	}
	
	if(popContentType == "C"){
		
		if(contents01.length == 0){
			$('#errorMsgMovie').css('color','red');
			$('#errorMsgMovie').text("영화명을 작성해주세요");
			pass = false;
		}
		else if(contents01.length > 50){
			$('#errorMsgArtist').css('color','red');
			$('#errorMsgArtist').text("50글자 이내로 작성해주세요");
			pass = false;
		}
		else{
			$('#errorMsgMovie').text("");	
		}
		
		if(link.length == 0){
			$('.errorMsgLink').eq(1).css('color','red');
			$('.errorMsgLink').eq(1).text("링크를 작성해주세요");
			pass = false;
		}
		else{
			$('.errorMsgLink').eq(1).text("");	
		}
		if(title.length > 100){
			$('.errorMsgMent').eq(1).css('color','red');
			$('.errorMsgMent').eq(1).text("100글자 이내로 작성해주세요");
			pass = false;
		}
		else if(title.length == 0){
			$('.errorMsgMent').eq(1).css('color','red');
			$('.errorMsgMent').eq(1).text("추천멘트를 작성해주세요");
			pass = false;
		}
		else{
			$('.errorMsgMent').eq(1).text("");	
		}
	}
	
	if(popContentType == "T"){
		if(contents01.length == 0){
			$('#errorMsgTrip').css('color','red');
			$('#errorMsgTrip').text("여행지를 작성해주세요");
			pass = false;
		}
		else if(contents01.length > 50){
			$('#errorMsgArtist').css('color','red');
			$('#errorMsgArtist').text("50글자 이내로 작성해주세요");
			pass = false;
		}
		else{
			$('#errorMsgTrip').text("");	
		}
		
		if(link.length == 0){
			$('.errorMsgLink').eq(2).css('color','red');
			$('.errorMsgLink').eq(2).text("링크를 작성해주세요");
			pass = false;
		}
		else{
			$('.errorMsgLink').eq(2).text("");	
		}
		
		if(title.length > 100){
			$('.errorMsgMent').eq(2).css('color','red');
			$('.errorMsgMent').eq(2).text("100글자 이내로 작성해주세요");
			pass = false;
		}
		else if(title.length == 0){
			$('.errorMsgMent').eq(2).css('color','red');
			$('.errorMsgMent').eq(2).text("추천멘트를 작성해주세요");
			pass = false;
		}
		else{
			$('.errorMsgMent').eq(2).text("");	
		}
	}
	
	
	
	if(pass == true){
		alert("작성완료");
		var formTag = document.getElementById("form");
		
        formTag.submit();
	}	
}




 

function goWriteToLogin() {
	var loginId = $('#my-button').attr("data-loginId");
	
	if(loginId == null || loginId == "") {
		alert("글쓰기는 로그인 후 이용이 가능합니다.");
		location.href = "member/login";
	}
}








