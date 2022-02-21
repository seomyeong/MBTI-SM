let boardId ="";
let loginId ="";
let likeIndex;

//ajax를 통해 새로 append 된 함수에도 똑같이 적용시키고자 할때! $(document)!!
$(document).on('click', '.likeWrap a', function(){
	loginId = $(this).attr("data-loginId");
	boardId = $(this).attr("data-boardId");	
	likeIndex = (($(this).parents('.content').index()) - 1 ) / 2;	
	
})



function likes(){
	
	let param = {
			'loginId' : loginId,
			'boardId' : boardId
	};

		$.ajax({
		type : "post",
		data : JSON.stringify(param),
		url : "/myapp/cultureBoard/likes",
		contentType : "application/json; charset=UTF-8",
		success : function(data){
			if(data["likeCheck"] == "false") {
				
				$('.content').eq(likeIndex).find('.cultureBoard_unlikes').addClass('cultureBoard_likes');
				$('.content').eq(likeIndex).find('.cultureBoard_unlikes').removeClass('cultureBoard_unlikes');
				$('.content').eq(likeIndex).find('.likesCount').text(data["likes"]);
								
			} else {
				$('.content').eq(likeIndex).find('.cultureBoard_likes').addClass('cultureBoard_unlikes');
				$('.content').eq(likeIndex).find('.cultureBoard_likes').removeClass('cultureBoard_likes');
				$('.content').eq(likeIndex).find('.likesCount').text(data["likes"]);					
			}
		}
	})

}







