const music = $('#cultureBtn').children('input:eq(0)');
const movie = $('#cultureBtn').children('input:eq(1)');
const trip = $('#cultureBtn').children('input:eq(2)');
//****************************************************** */
let mbtiValue = "none";
let contentType = "M";
let likesOR;
let mbtiSubmitStat;
let commentAppend = new Array();
//******************************************************* */


//mbti submit on/off 분기기능 
function ifSelectBtn(){
	console.log('들어옴');
	if(mbtiSubmitStat){
		$('#contentList').remove();
		selectMbti();
	}else{
		mbtiValue = "none";
		selectCulture();
		$('#nullType').remove();
	}		
}

/*********************************************************** */

$(document).on('click','.mbti-switch .toggle',function(){
	
	if ($(this).parent().children('input:eq(0)').attr('checked') == false || $(this).parent().children('input:eq(0)').attr('checked') == null) {
		$(this).parent().children('input:eq(0)').attr('checked', true);
		$(this).animate({
			left: 30,
		},300,function(){
			$(this).css("background",'#a4a0c4');
		});
	} else {
		$(this).parent().children('input:eq(0)').attr('checked', false);
		$(this).animate({			
			left: 0,
			
		},300,function(){
			$(this).css("background",'#b2d4ad');
		});
	}
})



/********************************************************************************* */


//ajax를 통해 새로 append 된 함수에도 똑같이 적용시키고자 할때! $(document)!!
$(document).on('click', '.likeWrap a', function(){
	boardId = $(this).attr("data-boardId");
	
})

	//  영화,음악,여행 버튼 클릭시 contentType 저장
	movie.on('click',function(){
        contentType = "C";
    })

    music.on('click',function(){
        contentType = "M"; 
    })

    trip.on('click',function(){
        contentType = "T";
    })
	
	
	function selectCulture(){

	// 저장된 contentType(String)데이터를 ajax를 통해 index컨트롤러로 이동
    let param = {
        "type" : contentType
    };
	
	
    $.ajax({
        type: "post",
        data: JSON.stringify(param),
        url: "/myapp/cultureBoard/index",
        contentType: "application/json; charset=UTF-8",
        success: function(data) {
			$('#contentWrap').css('overflow-y','scroll');
			$('#contentList').remove();
			$('#noneDataWrap').remove();	
			
			let contents = data["contents"];
			let memberInfo = data["memberInfo"];
			let cultureBoardComment = data["cultureBoardComment"];
			let loginId = data["loginId"];
		
			let likeContents = data["likeContents"];
			let likeComments = data["likeComments"];
		
		
			let memberIsNull;
			let loginNull_commentSubmit;
			let memberImg;

			
			if(contentType == "M"){
				$('#contentWrap').append(
					"<div id='contentList'>"+
					"</div>"
				);
				$('#contentList').append(
    				"<div id='contentHead'>" +
					"<p>좋아요</p>" +
					"<p>MBTI</p>" +
					"<p>가수</p>" +
					"<p>노래</p>" +
					"<p>닉네임</p>" +
					"</div>"
				);
				
				for(con in contents){
					
					/**댓글작성 부분 비로그인/로그인시 */
					/**댓글 submit 버튼 비로그인/로그인시 */
					if(memberInfo == null || memberInfo == ""){
						memberIsNull=
						"<span class='authorLv'></span>" +
						"<span class='authorNickname'></span>" +
						"<span class='authorMbti'></span>";
						
						loginNull_commentSubmit = 
						"<input type='button' value='댓글'/>";
		
						
					}else{
						memberIsNull=
						"<span class='authorLv'>LV. "+ memberInfo.level + "</span>" +
						"<span class='authorNickname'>" + memberInfo.nickName + "</span>" +
						"<span class='authorMbti'>"+memberInfo.mbti+"</span>";
						
						loginNull_commentSubmit = 
						"<input type='button' value='댓글'" + " onclick='writeCommentSubmit(this.form,"+ contents[con].id + ","+ loginId +",this); return false'/>";
					}
					
					
					
					let state01 = 0;
					/** 비로그인/로그인시 메인컨텐츠별 추천*/
					if(memberInfo != null){
						
						for(lct in likeContents){
						
							if(likeContents[lct].id == contents[con].id){
								likesOR = "<a href='javascript:likes()' data-boardId =" + contents[con].id +" data-loginId=" + loginId + " class='cultureBoard_likes'></a>";
								state01 = 1;
							}
						
						}
						
						if(state01 == 0){
							likesOR = "<a href='javascript:likes()' data-boardId =" + contents[con].id +" data-loginId="+ loginId + " class='cultureBoard_unlikes'></a>";
							state01 = 0;
						}
					
					}else{
						likesOR = "<a href='#' class='cultureBoard_unlikes'></a>";
					}
						
					
					
					for(com in cultureBoardComment){
						let state02 = 0;
						/** 비로그인/로그인시 댓글추천 */
						if(memberInfo != null){
							
							for(lcm in likeComments){
								
								if(likeComments[lcm].id == cultureBoardComment[com].id){
									
						   			 commentLikesOr = "<a href='#'" + " onclick='commentLikes("+ cultureBoardComment[com].id + ","+ loginId +",this); return false' class='cultureBoardComment_likes'></a>";
									 state02 = 1;
								}
								
							}
							
							
							if(state02 == 0){
				   				commentLikesOr = "<a href='#'" + " onclick='commentLikes("+ cultureBoardComment[com].id + ","+ loginId +",this); return false' class='cultureBoardComment_unlikes'></a>";
								state02 = 0;
							}
						
						
						}else{
			    			commentLikesOr = "<a href='#' class='cultureBoardComment_unlikes'></a>";
						}
						
						
						/**비로그인/로그인시 댓글삭제 */
						if(memberInfo != null){
							if(loginId == cultureBoardComment[com].member.id){
							    commentDelOr =
							    "<div class='delIcon'>"+
							    "<a href='#' class='delCommentBtn' data-boardId ="+ contents[con].id + " data-loginId="+ loginId + " data-commentId="+ cultureBoardComment[com].id +">X</a>"+
								"</div>";
							}else{
							    commentDelOr=
							    "<div class='delIcon'>"+
								"</div>";
							}	
						}else{
							commentDelOr=
						    "<div class='delIcon'>"+
							"</div>";
						}
						
						
					
							
						if(contents[con].id == cultureBoardComment[com].cultureBoard.id){		
							commentAppend[com] = 
							"<div class='commentRead'>"+
							"<div class='memberImg'"+
							"style='background: url(" + cultureBoardComment[com].member.profileImg + ")  0 0 / cover'></div>"+
							"<div class='memberWrapOfWrap'>"+
							"<div class='memberWrap'>"+
							"<div class='memberInfo'>"+
							"<span class='eachMember memberLv'>LV. "+ cultureBoardComment[com].member.level+"</span>"+
							"<span class='eachMember memberNickname'>"+cultureBoardComment[com].member.nickName+"</span>"+
							"<span class='eachMember memberMbti'>"+cultureBoardComment[com].member.mbti+"</span>"+
							"<span class='eachMember comment_reportDate'>" + timeForToday02(cultureBoardComment[com].reportingDate) + "</span>" +
							"</div>"+
							"<div class='memberComment'>"+ cultureBoardComment[com].comment+"</div>"+	
							"</div>"+
							
							"<div class='memberLike'>"+
							commentLikesOr+
							"<p class='commentLikesCount'>"+ cultureBoardComment[com].likes+"</p>"+
							"</div>"+
							
							"<div class='memberDelete'>"+
							commentDelOr+
							"</div>"+
							"</div>"+
							"</div>";
						}
						
						if(memberInfo != null){
							memberImg = "<div class='authorImg' style='background: url(" + memberInfo.profileImg + ") 0 0 / cover'></div>";
							
						}else{
							memberImg = "<div class='authorImg'></div>";
						}
						
	
					}
					
					/**댓글 다른 보드 중복 방지 변수 */
					let commentSum;
					commentSum = commentAppend.join(" ");
					/************************ */
					
					$('#contentList').append(
					"<div class='content'>" +
					"<div class='contentForm01'>" +
					"<div class='likeWrap'>" +
					 likesOR +
					"<p class='likesCount'>"+ contents[con].likes + "</p>" +
					"</div>" +
					"<div>"+ contents[con].member.mbti + "</div>" +
					"<div>"+ contents[con].contents01 + "</div>" +
					"<div>"+ contents[con].contents02 + "</div>" +
					"<div id='eachNick'>"+ contents[con].member.nickName + "</div>" +
					"</div>" +
					"<div class='contentForm02'>" +
					"<div class='commentTd'>"+
					"<a class='dropCommentWrap' href='#'>" +
					"<span class='angleDown'></span>" +
					"<span class='dropComment'>답글 "+contents[con].commentNum+"개 보기</span>" +
					"</a>" + 
					"</div>" +
					"<div class='linkTd'><a class='linkTag' href=" + contents[con].link + " target='_blank'>"+
					"<div class='contentMent'>" + contents[con].title + "</div>" + "<span class='linkIconMusic'></span></a></div>" +
					"<div class='cultureBoard_reportDate'>" + timeForToday02(contents[con].reportingDate) + "</div>" +
					"</div>" +
					"</div>" +
					"<div class='commentWrap'>" +
					"<div class='comment'>"	+
						"<div class='commentWrite'>" +
						memberImg +
						"<div class='authorWrap'>" +
						"<div class='authorInfo'>" +
						memberIsNull +
						"</div>"+
						"<form class='writeTextForm'>"+
						"<div class='writeText'>"+
						"<input type='text' name='comment' placeholder='공개글 작성하기' maxlength='500' autocomplete='off'/>"+
						"<span class='commentErrorMsg'></span>"+
						"</div>"+
						"<div class='writeSubmit'>" +
						loginNull_commentSubmit	+					
						"</div>" +	
						"</form>"+
						"</div>" +
					"</div>" +
					"<div class='commentReadWrap'>" +
						commentSum +
					 "</div>"
					);	
					commentAppend = new Array();
					commentSum ="";
				}
			}
			
			
			
			
			
			
			else if(contentType == "C"){
				$('#contentWrap').append(
					"<div id='contentList'>"+
					"</div>"
				);
				$('#contentList').append(
    				"<div id='contentHead'>" +
					"<p>좋아요</p>" +
					"<p>MBTI</p>" +
					"<p>영화</p>" +
					"<p>닉네임</p>" +
					"</div>"
				);
				
				for(con in contents){
					
					/**댓글작성 부분 비로그인/로그인시 */
					/**댓글 submit 버튼 비로그인/로그인시 */
					if(memberInfo == null || memberInfo == ""){
						memberIsNull=
						"<span class='authorLv'></span>" +
						"<span class='authorNickname'></span>" +
						"<span class='authorMbti'></span>";
						
						loginNull_commentSubmit = 
						"<input type='button' value='댓글'/>";
		
						
					}else{
						memberIsNull=
						"<span class='authorLv'>LV. "+ memberInfo.level + "</span>" +
						"<span class='authorNickname'>" + memberInfo.nickName + "</span>" +
						"<span class='authorMbti'>"+memberInfo.mbti+"</span>";
						
						loginNull_commentSubmit = 
						"<input type='button' value='댓글'" + " onclick='writeCommentSubmit(this.form,"+ contents[con].id + ","+ loginId +",this); return false'/>";
					}
					
					
					
					let state01 = 0;
					/** 비로그인/로그인시 메인컨텐츠별 추천*/
					if(memberInfo != null){
						
						for(lct in likeContents){
						
							if(likeContents[lct].id == contents[con].id){
								likesOR = "<a href='javascript:likes()' data-boardId =" + contents[con].id +" data-loginId=" + loginId + " class='cultureBoard_likes'></a>";
								state01 = 1;
							}
						
						}
						
						if(state01 == 0){
							likesOR = "<a href='javascript:likes()' data-boardId =" + contents[con].id +" data-loginId="+ loginId + " class='cultureBoard_unlikes'></a>";
							state01 = 0;
						}
					
					}else{
						likesOR = "<a href='#' class='cultureBoard_unlikes'></a>";
					}
						
					
					
					for(com in cultureBoardComment){
						let state02 = 0;
						/** 비로그인/로그인시 댓글추천 */
						if(memberInfo != null){
							
							for(lcm in likeComments){
								
								if(likeComments[lcm].id == cultureBoardComment[com].id){
									
						   			 commentLikesOr = "<a href='#'" + " onclick='commentLikes("+ cultureBoardComment[com].id + ","+ loginId +",this); return false' class='cultureBoardComment_likes'></a>";
									 state02 = 1;
								}
								
							}
							
							
							if(state02 == 0){
				   				commentLikesOr = "<a href='#'" + " onclick='commentLikes("+ cultureBoardComment[com].id + ","+ loginId +",this); return false' class='cultureBoardComment_unlikes'></a>";
								state02 = 0;
							}
						
						
						}else{
			    			commentLikesOr = "<a href='#' class='cultureBoardComment_unlikes'></a>";
						}
						
						
						/**비로그인/로그인시 댓글삭제 */
						if(memberInfo != null){
							if(loginId == cultureBoardComment[com].member.id){
							    commentDelOr =
							    "<div class='delIcon'>"+
							    "<a href='#' class='delCommentBtn' data-boardId ="+ contents[con].id + " data-loginId="+ loginId + " data-commentId="+ cultureBoardComment[com].id +">X</a>"+
								"</div>";
							}else{
							    commentDelOr=
							    "<div class='delIcon'>"+
								"</div>";
							}	
						}else{
							commentDelOr=
						    "<div class='delIcon'>"+
							"</div>";
						}
						
						
					
							
						if(contents[con].id == cultureBoardComment[com].cultureBoard.id){		
							commentAppend[com] = 
							"<div class='commentRead'>"+
							"<div class='memberImg'"+
							"style='background: url(" + cultureBoardComment[com].member.profileImg + ")  0 0 / cover'></div>"+
							"<div class='memberWrapOfWrap'>"+
							"<div class='memberWrap'>"+
							"<div class='memberInfo'>"+
							"<span class='eachMember memberLv'>LV. "+ cultureBoardComment[com].member.level+"</span>"+
							"<span class='eachMember memberNickname'>"+cultureBoardComment[com].member.nickName+"</span>"+
							"<span class='eachMember memberMbti'>"+cultureBoardComment[com].member.mbti+"</span>"+
							"<span class='eachMember comment_reportDate'>" + timeForToday02(cultureBoardComment[com].reportingDate) + "</span>" +
							"</div>"+
							"<div class='memberComment'>"+ cultureBoardComment[com].comment+"</div>"+	
							"</div>"+
							
							"<div class='memberLike'>"+
							commentLikesOr+
							"<p class='commentLikesCount'>"+ cultureBoardComment[com].likes+"</p>"+
							"</div>"+
							
							"<div class='memberDelete'>"+
							commentDelOr+
							"</div>"+
							"</div>"+
							"</div>";
						}
						
						if(memberInfo != null){
							memberImg = "<div class='authorImg' style='background: url(" + memberInfo.profileImg + ") 0 0 / cover'></div>";
							
						}else{
							memberImg = "<div class='authorImg'></div>";
						}
						
	
					}
					
					/**댓글 다른 보드 중복 방지 변수 */
					let commentSum;
					commentSum = commentAppend.join(" ");
					/************************ */
					
					$('#contentList').append(
					"<div class='content'>" +
					"<div class='contentForm01'>" +
					"<div class='likeWrap'>" +
					 likesOR +
					"<p class='likesCount'>"+ contents[con].likes + "</p>" +
					"</div>" +
					"<div>"+ contents[con].member.mbti + "</div>" +
					"<div>"+ contents[con].contents01 + "</div>" +
					"<div id='eachNick'>"+ contents[con].member.nickName + "</div>" +
					"</div>" +
					"<div class='contentForm02'>" +
					"<div class='commentTd'>"+
					"<a class='dropCommentWrap' href='#'>" +
					"<span class='angleDown'></span>" +
					"<span class='dropComment'>답글 "+contents[con].commentNum+"개 보기</span>" +
					"</a>" + 
					"</div>" +
					"<div class='linkTd'><a class='linkTag' href=" + contents[con].link + " target='_blank'>"+ 
					"<div class='contentMent'>" + contents[con].title + "</div>" + "<span class='linkIconCinema'></span></a></div>" +
					"<div class='cultureBoard_reportDate'>" + timeForToday02(contents[con].reportingDate) + "</div>" +
					"</div>" +
					"</div>" +
					"<div class='commentWrap'>" +
					"<div class='comment'>"	+
						"<div class='commentWrite'>" +
						memberImg +
						"<div class='authorWrap'>" +
						"<div class='authorInfo'>" +
						memberIsNull +
						"</div>"+
						"<form class='writeTextForm'>"+
						"<div class='writeText'>"+
						"<input type='text' name='comment' placeholder='공개글 작성하기' maxlength='500' autocomplete='off'/>"+
						"<span class='commentErrorMsg'></span>"+
						"</div>"+
						"<div class='writeSubmit'>" +
						loginNull_commentSubmit	+					
						"</div>" +	
						"</form>"+
						"</div>" +
					"</div>" +
					"<div class='commentReadWrap'>" +
						commentSum +
					 "</div>"
					);	
					commentAppend = new Array();
					commentSum ="";
				}
			}
			
			
			else if(contentType == "T"){
				$('#contentWrap').append(
					"<div id='contentList'>"+
					"</div>"
				);
				$('#contentList').append(
    				"<div id='contentHead'>" +
					"<p>좋아요</p>" +
					"<p>MBTI</p>" +
					"<p>여행지</p>" +
					"<p>닉네임</p>" +
					"</div>"
				);
				
				for(con in contents){
					
					/**댓글작성 부분 비로그인/로그인시 */
					/**댓글 submit 버튼 비로그인/로그인시 */
					if(memberInfo == null || memberInfo == ""){
						memberIsNull=
						"<span class='authorLv'></span>" +
						"<span class='authorNickname'></span>" +
						"<span class='authorMbti'></span>";
						
						loginNull_commentSubmit = 
						"<input type='button' value='댓글'/>";
		
						
					}else{
						memberIsNull=
						"<span class='authorLv'>LV. "+ memberInfo.level + "</span>" +
						"<span class='authorNickname'>" + memberInfo.nickName + "</span>" +
						"<span class='authorMbti'>"+memberInfo.mbti+"</span>";
						
						loginNull_commentSubmit = 
						"<input type='button' value='댓글'" + " onclick='writeCommentSubmit(this.form,"+ contents[con].id + ","+ loginId +",this); return false'/>";
					}
					
					
					
					let state01 = 0;
					/** 비로그인/로그인시 메인컨텐츠별 추천*/
					if(memberInfo != null){
						
						for(lct in likeContents){
						
							if(likeContents[lct].id == contents[con].id){
								likesOR = "<a href='javascript:likes()' data-boardId =" + contents[con].id +" data-loginId=" + loginId + " class='cultureBoard_likes'></a>";
								state01 = 1;
							}
						
						}
						
						if(state01 == 0){
							likesOR = "<a href='javascript:likes()' data-boardId =" + contents[con].id +" data-loginId="+ loginId + " class='cultureBoard_unlikes'></a>";
							state01 = 0;
						}
					
					}else{
						likesOR = "<a href='#' class='cultureBoard_unlikes'></a>";
					}
						
					
					
					for(com in cultureBoardComment){
						let state02 = 0;
						/** 비로그인/로그인시 댓글추천 */
						if(memberInfo != null){
							
							for(lcm in likeComments){
								
								if(likeComments[lcm].id == cultureBoardComment[com].id){
									
						   			 commentLikesOr = "<a href='#'" + " onclick='commentLikes("+ cultureBoardComment[com].id + ","+ loginId +",this); return false' class='cultureBoardComment_likes'></a>";
									 state02 = 1;
								}
								
							}
							
							
							if(state02 == 0){
				   				commentLikesOr = "<a href='#'" + " onclick='commentLikes("+ cultureBoardComment[com].id + ","+ loginId +",this); return false' class='cultureBoardComment_unlikes'></a>";
								state02 = 0;
							}
						
						
						}else{
			    			commentLikesOr = "<a href='#' class='cultureBoardComment_unlikes'></a>";
						}
						
						
						/**비로그인/로그인시 댓글삭제 */
						if(memberInfo != null){
							if(loginId == cultureBoardComment[com].member.id){
							    commentDelOr =
							    "<div class='delIcon'>"+
							    "<a href='#' class='delCommentBtn' data-boardId ="+ contents[con].id + " data-loginId="+ loginId + " data-commentId="+ cultureBoardComment[com].id +">X</a>"+
								"</div>";
							}else{
							    commentDelOr=
							    "<div class='delIcon'>"+
								"</div>";
							}	
						}else{
							commentDelOr=
						    "<div class='delIcon'>"+
							"</div>";
						}
						
						
					
							
						if(contents[con].id == cultureBoardComment[com].cultureBoard.id){		
							commentAppend[com] = 
							"<div class='commentRead'>"+
							"<div class='memberImg'"+
							"style='background: url(" + cultureBoardComment[com].member.profileImg + ")  0 0 / cover'></div>"+
							"<div class='memberWrapOfWrap'>"+
							"<div class='memberWrap'>"+
							"<div class='memberInfo'>"+
							"<span class='eachMember memberLv'>LV. "+ cultureBoardComment[com].member.level+"</span>"+
							"<span class='eachMember memberNickname'>"+cultureBoardComment[com].member.nickName+"</span>"+
							"<span class='eachMember memberMbti'>"+cultureBoardComment[com].member.mbti+"</span>"+
							"<span class='eachMember comment_reportDate'>" + timeForToday02(cultureBoardComment[com].reportingDate) + "</span>" +
							"</div>"+
							"<div class='memberComment'>"+ cultureBoardComment[com].comment+"</div>"+	
							"</div>"+
							
							"<div class='memberLike'>"+
							commentLikesOr+
							"<p class='commentLikesCount'>"+ cultureBoardComment[com].likes+"</p>"+
							"</div>"+
							
							"<div class='memberDelete'>"+
							commentDelOr+
							"</div>"+
							"</div>"+
							"</div>";
						}
						
						if(memberInfo != null){
							memberImg = "<div class='authorImg' style='background: url(" + memberInfo.profileImg + ") 0 0 / cover'></div>";
							
						}else{
							memberImg = "<div class='authorImg'></div>";
						}
						
	
					}
					
					/**댓글 다른 보드 중복 방지 변수 */
					let commentSum;
					commentSum = commentAppend.join(" ");
					/************************ */
					
					$('#contentList').append(
					"<div class='content'>" +
					"<div class='contentForm01'>" +
					"<div class='likeWrap'>" +
					 likesOR +
					"<p class='likesCount'>"+ contents[con].likes + "</p>" +
					"</div>" +
					"<div>"+ contents[con].member.mbti + "</div>" +
					"<div>"+ contents[con].contents01 + "</div>" +
					"<div id='eachNick'>"+ contents[con].member.nickName + "</div>" +
					"</div>" +
					"<div class='contentForm02'>" +
					"<div class='commentTd'>"+
					"<a class='dropCommentWrap' href='#'>" +
					"<span class='angleDown'></span>" +
					"<span class='dropComment'>답글 "+contents[con].commentNum+"개 보기</span>" +
					"</a>" + 
					"</div>" +
					"<div class='linkTd'><a class='linkTag' href=" + contents[con].link + " target='_blank'>"+ 
					"<div class='contentMent'>" + contents[con].title + "</div>" + "<span class='linkIconTrip'></span></a></div>" +
					"<div class='cultureBoard_reportDate'>" + timeForToday02(contents[con].reportingDate) + "</div>" +
					"</div>" +
					"</div>" +
					"<div class='commentWrap'>" +
					"<div class='comment'>"	+
						"<div class='commentWrite'>" +
						memberImg +
						"<div class='authorWrap'>" +
						"<div class='authorInfo'>" +
						memberIsNull +
						"</div>"+
						"<form class='writeTextForm'>"+
						"<div class='writeText'>"+
						"<input type='text' name='comment' placeholder='공개글 작성하기' maxlength='500' autocomplete='off'/>"+
						"<span class='commentErrorMsg'></span>"+
						"</div>"+
						"<div class='writeSubmit'>" +
						loginNull_commentSubmit	+					
						"</div>" +	
						"</form>"+
						"</div>" +
					"</div>" +
					"<div class='commentReadWrap'>" +
						commentSum +
					 "</div>"
					);	
					commentAppend = new Array();
					commentSum ="";
				}
			}
		}
    });
}


//**************************************************************** */

$(document).on('click', '#mbtiSubmit', function(){

	if($(this).hasClass('mbtiSubmitOff')){
		$(this).removeClass('mbtiSubmitOff');
		$(this).addClass('mbtiSubmitOn');
		mbtiSubmitStat = true;
	}
	else{
		$(this).removeClass('mbtiSubmitOn');
		$(this).addClass('mbtiSubmitOff');
		mbtiSubmitStat = false;
	}
})


// mbti filter
function selectMbti() {
	
	let type01;
	let type02;
	let type03;
	let type04;


	if ($('.mbti-switch input[name="type01"]').attr('checked') == false || $('.mbti-switch input[name="type01"]').attr('checked') == null) {
		type01 = 'E';
	} else {
		type01 = 'I';
	}

	if ($('.mbti-switch input[name="type02"]').attr('checked') == false || $('.mbti-switch input[name="type02"]').attr('checked') == null) {
		type02 = 'N';
	} else {
		type02 = 'S';
	}

	if ($('.mbti-switch input[name="type03"]').attr('checked') == false || $('.mbti-switch input[name="type03"]').attr('checked') == null) {
		type03 = 'F';
	} else {
		type03 = 'T';
	}

	if ($('.mbti-switch input[name="type04"]').attr('checked') == false || $('.mbti-switch input[name="type04"]').attr('checked') == null) {
		type04 = 'P';
	} else {
		type04 = 'J';
	}
	
	mbtiValue = type01+type02+type03+type04;
	console.log(mbtiValue);
	
	let param = {
        'mbtiValue' : mbtiValue,
		'contentType' : contentType
    };
	
	
	
	$.ajax({ 
		
		type: "post",
		data: JSON.stringify(param),
		url: "/myapp/cultureBoard/index02",
		contentType: "application/json; charset=UTF-8",
		success: function(data) {
			$('#contentWrap').css('overflow-y','scroll');
			$('#contentList').remove();
			$('#nullType').remove();
			$('#noneDataWrap').remove();	
			
			let contents = data["contents"];
			let memberInfo = data["memberInfo"];
			let cultureBoardComment = data["cultureBoardComment"];
			let loginId = data["loginId"];
		
			let likeContents = data["likeContents"];
			let likeComments = data["likeComments"];
		
		
			let memberIsNull;
			let loginNull_commentSubmit;
			let memberImg;

			
			
			
			
			if(contentType == "M"){
				$('#contentWrap').append(
					"<div id='contentList'>"+
					"</div>"
				);
				$('#contentList').append(
    				"<div id='contentHead'>" +
					"<p>좋아요</p>" +
					"<p>MBTI</p>" +
					"<p>가수</p>" +
					"<p>노래</p>" +
					"<p>닉네임</p>" +
					"</div>"
				);
				
				for(con in contents){
					
					/**댓글작성 부분 비로그인/로그인시 */
					/**댓글 submit 버튼 비로그인/로그인시 */
					if(memberInfo == null || memberInfo == ""){
						memberIsNull=
						"<span class='authorLv'></span>" +
						"<span class='authorNickname'></span>" +
						"<span class='authorMbti'></span>";
						
						loginNull_commentSubmit = 
						"<input type='button' value='댓글'/>";
		
						
					}else{
						memberIsNull=
						"<span class='authorLv'>LV. "+ memberInfo.level + "</span>" +
						"<span class='authorNickname'>" + memberInfo.nickName + "</span>" +
						"<span class='authorMbti'>"+memberInfo.mbti+"</span>";
						
						loginNull_commentSubmit = 
						"<input type='button' value='댓글'" + " onclick='writeCommentSubmit(this.form,"+ contents[con].id + ","+ loginId +",this); return false'/>";
					}
					
					
					
					let state01 = 0;
					/** 비로그인/로그인시 메인컨텐츠별 추천*/
					if(memberInfo != null){
						
						for(lct in likeContents){
						
							if(likeContents[lct].id == contents[con].id){
								likesOR = "<a href='javascript:likes()' data-boardId =" + contents[con].id +" data-loginId=" + loginId + " class='cultureBoard_likes'></a>";
								state01 = 1;
							}
						
						}
						
						if(state01 == 0){
							likesOR = "<a href='javascript:likes()' data-boardId =" + contents[con].id +" data-loginId="+ loginId + " class='cultureBoard_unlikes'></a>";
							state01 = 0;
						}
					
					}else{
						likesOR = "<a href='#' class='cultureBoard_unlikes'></a>";
					}
						
					
					
					for(com in cultureBoardComment){
						let state02 = 0;
						/** 비로그인/로그인시 댓글추천 */
						if(memberInfo != null){
							
							for(lcm in likeComments){
								
								if(likeComments[lcm].id == cultureBoardComment[com].id){
									
						   			 commentLikesOr = "<a href='#'" + " onclick='commentLikes("+ cultureBoardComment[com].id + ","+ loginId +",this); return false' class='cultureBoardComment_likes'></a>";
									 state02 = 1;
								}
								
							}
							
							
							if(state02 == 0){
				   				commentLikesOr = "<a href='#'" + " onclick='commentLikes("+ cultureBoardComment[com].id + ","+ loginId +",this); return false' class='cultureBoardComment_unlikes'></a>";
								state02 = 0;
							}
						
						
						}else{
			    			commentLikesOr = "<a href='#' class='cultureBoardComment_unlikes'></a>";
						}
						
						
						/**비로그인/로그인시 댓글삭제 */
						if(memberInfo != null){
							if(loginId == cultureBoardComment[com].member.id){
							    commentDelOr =
							    "<div class='delIcon'>"+
							    "<a href='#' class='delCommentBtn' data-boardId ="+ contents[con].id + " data-loginId="+ loginId + " data-commentId="+ cultureBoardComment[com].id +">X</a>"+
								"</div>";
							}else{
							    commentDelOr=
							    "<div class='delIcon'>"+
								"</div>";
							}	
						}else{
							commentDelOr=
						    "<div class='delIcon'>"+
							"</div>";
						}
						
						
					
							
						if(contents[con].id == cultureBoardComment[com].cultureBoard.id){		
							commentAppend[com] = 
							"<div class='commentRead'>"+
							"<div class='memberImg'"+
							"style='background: url(" + cultureBoardComment[com].member.profileImg + ")  0 0 / cover'></div>"+
							"<div class='memberWrapOfWrap'>"+
							"<div class='memberWrap'>"+
							"<div class='memberInfo'>"+
							"<span class='eachMember memberLv'>LV. "+ cultureBoardComment[com].member.level+"</span>"+
							"<span class='eachMember memberNickname'>"+cultureBoardComment[com].member.nickName+"</span>"+
							"<span class='eachMember memberMbti'>"+cultureBoardComment[com].member.mbti+"</span>"+
							"<span class='eachMember comment_reportDate'>" + timeForToday02(cultureBoardComment[com].reportingDate) + "</span>" +
							"</div>"+
							"<div class='memberComment'>"+ cultureBoardComment[com].comment+"</div>"+	
							"</div>"+
							
							"<div class='memberLike'>"+
							commentLikesOr+
							"<p class='commentLikesCount'>"+ cultureBoardComment[com].likes+"</p>"+
							"</div>"+
							
							"<div class='memberDelete'>"+
							commentDelOr+
							"</div>"+
							"</div>"+
							"</div>";
						}
						
						if(memberInfo != null){
							memberImg = "<div class='authorImg' style='background: url(" + memberInfo.profileImg + ") 0 0 / cover'></div>";
							
						}else{
							memberImg = "<div class='authorImg'></div>";
						}
						
	
					}
					
					/**댓글 다른 보드 중복 방지 변수 */
					let commentSum;
					commentSum = commentAppend.join(" ");
					/************************ */
					
					$('#contentList').append(
					"<div class='content'>" +
					"<div class='contentForm01'>" +
					"<div class='likeWrap'>" +
					 likesOR +
					"<p class='likesCount'>"+ contents[con].likes + "</p>" +
					"</div>" +
					"<div>"+ contents[con].member.mbti + "</div>" +
					"<div>"+ contents[con].contents01 + "</div>" +
					"<div>"+ contents[con].contents02 + "</div>" +
					"<div id='eachNick'>"+ contents[con].member.nickName + "</div>" +
					"</div>" +
					"<div class='contentForm02'>" +
					"<div class='commentTd'>"+
					"<a class='dropCommentWrap' href='#'>" +
					"<span class='angleDown'></span>" +
					"<span class='dropComment'>답글 "+contents[con].commentNum+"개 보기</span>" +
					"</a>" + 
					"</div>" +
					"<div class='linkTd'><a class='linkTag' href=" + contents[con].link + " target='_blank'>"+
					"<div class='contentMent'>" + contents[con].title + "</div>" + "<span class='linkIconMusic'></span></a></div>" +
					"<div class='cultureBoard_reportDate'>" + timeForToday02(contents[con].reportingDate) + "</div>" +
					"</div>" +
					"</div>" +
					"<div class='commentWrap'>" +
					"<div class='comment'>"	+
						"<div class='commentWrite'>" +
						memberImg +
						"<div class='authorWrap'>" +
						"<div class='authorInfo'>" +
						memberIsNull +
						"</div>"+
						"<form class='writeTextForm'>"+
						"<div class='writeText'>"+
						"<input type='text' name='comment' placeholder='공개글 작성하기' maxlength='500' autocomplete='off'/>"+
						"<span class='commentErrorMsg'></span>"+
						"</div>"+
						"<div class='writeSubmit'>" +
						loginNull_commentSubmit	+					
						"</div>" +	
						"</form>"+
						"</div>" +
					"</div>" +
					"<div class='commentReadWrap'>" +
						commentSum +
					 "</div>"
					);	
					commentAppend = new Array();
					commentSum ="";
				}
			}
			
			
			
			
			
			
			else if(contentType == "C"){
				$('#contentWrap').append(
					"<div id='contentList'>"+
					"</div>"
				);
				$('#contentList').append(
    				"<div id='contentHead'>" +
					"<p>좋아요</p>" +
					"<p>MBTI</p>" +
					"<p>영화</p>" +
					"<p>닉네임</p>" +
					"</div>"
				);
				
				for(con in contents){
					
					/**댓글작성 부분 비로그인/로그인시 */
					/**댓글 submit 버튼 비로그인/로그인시 */
					if(memberInfo == null || memberInfo == ""){
						memberIsNull=
						"<span class='authorLv'></span>" +
						"<span class='authorNickname'></span>" +
						"<span class='authorMbti'></span>";
						
						loginNull_commentSubmit = 
						"<input type='button' value='댓글'/>";
		
						
					}else{
						memberIsNull=
						"<span class='authorLv'>LV. "+ memberInfo.level + "</span>" +
						"<span class='authorNickname'>" + memberInfo.nickName + "</span>" +
						"<span class='authorMbti'>"+memberInfo.mbti+"</span>";
						
						loginNull_commentSubmit = 
						"<input type='button' value='댓글'" + " onclick='writeCommentSubmit(this.form,"+ contents[con].id + ","+ loginId +",this); return false'/>";
					}
					
					
					
					let state01 = 0;
					/** 비로그인/로그인시 메인컨텐츠별 추천*/
					if(memberInfo != null){
						
						for(lct in likeContents){
						
							if(likeContents[lct].id == contents[con].id){
								likesOR = "<a href='javascript:likes()' data-boardId =" + contents[con].id +" data-loginId=" + loginId + " class='cultureBoard_likes'></a>";
								state01 = 1;
							}
						
						}
						
						if(state01 == 0){
							likesOR = "<a href='javascript:likes()' data-boardId =" + contents[con].id +" data-loginId="+ loginId + " class='cultureBoard_unlikes'></a>";
							state01 = 0;
						}
					
					}else{
						likesOR = "<a href='#' class='cultureBoard_unlikes'></a>";
					}
						
					
					
					for(com in cultureBoardComment){
						let state02 = 0;
						/** 비로그인/로그인시 댓글추천 */
						if(memberInfo != null){
							
							for(lcm in likeComments){
								
								if(likeComments[lcm].id == cultureBoardComment[com].id){
									
						   			 commentLikesOr = "<a href='#'" + " onclick='commentLikes("+ cultureBoardComment[com].id + ","+ loginId +",this); return false' class='cultureBoardComment_likes'></a>";
									 state02 = 1;
								}
								
							}
							
							
							if(state02 == 0){
				   				commentLikesOr = "<a href='#'" + " onclick='commentLikes("+ cultureBoardComment[com].id + ","+ loginId +",this); return false' class='cultureBoardComment_unlikes'></a>";
								state02 = 0;
							}
						
						
						}else{
			    			commentLikesOr = "<a href='#' class='cultureBoardComment_unlikes'></a>";
						}
						
						
						/**비로그인/로그인시 댓글삭제 */
						if(memberInfo != null){
							if(loginId == cultureBoardComment[com].member.id){
							    commentDelOr =
							    "<div class='delIcon'>"+
							    "<a href='#' class='delCommentBtn' data-boardId ="+ contents[con].id + " data-loginId="+ loginId + " data-commentId="+ cultureBoardComment[com].id +">X</a>"+
								"</div>";
							}else{
							    commentDelOr=
							    "<div class='delIcon'>"+
								"</div>";
							}	
						}else{
							commentDelOr=
						    "<div class='delIcon'>"+
							"</div>";
						}
						
						
					
							
						if(contents[con].id == cultureBoardComment[com].cultureBoard.id){		
							commentAppend[com] = 
							"<div class='commentRead'>"+
							"<div class='memberImg'"+
							"style='background: url(" + cultureBoardComment[com].member.profileImg + ")  0 0 / cover'></div>"+
							"<div class='memberWrapOfWrap'>"+
							"<div class='memberWrap'>"+
							"<div class='memberInfo'>"+
							"<span class='eachMember memberLv'>LV. "+ cultureBoardComment[com].member.level+"</span>"+
							"<span class='eachMember memberNickname'>"+cultureBoardComment[com].member.nickName+"</span>"+
							"<span class='eachMember memberMbti'>"+cultureBoardComment[com].member.mbti+"</span>"+
							"<span class='eachMember comment_reportDate'>" + timeForToday02(cultureBoardComment[com].reportingDate) + "</span>" +
							"</div>"+
							"<div class='memberComment'>"+ cultureBoardComment[com].comment+"</div>"+	
							"</div>"+
							
							"<div class='memberLike'>"+
							commentLikesOr+
							"<p class='commentLikesCount'>"+ cultureBoardComment[com].likes+"</p>"+
							"</div>"+
							
							"<div class='memberDelete'>"+
							commentDelOr+
							"</div>"+
							"</div>"+
							"</div>";
						}
						
						if(memberInfo != null){
							memberImg = "<div class='authorImg' style='background: url(" + memberInfo.profileImg + ") 0 0 / cover'></div>";
							
						}else{
							memberImg = "<div class='authorImg'></div>";
						}
						
	
					}
					
					/**댓글 다른 보드 중복 방지 변수 */
					let commentSum;
					commentSum = commentAppend.join(" ");
					/************************ */
					
					$('#contentList').append(
					"<div class='content'>" +
					"<div class='contentForm01'>" +
					"<div class='likeWrap'>" +
					 likesOR +
					"<p class='likesCount'>"+ contents[con].likes + "</p>" +
					"</div>" +
					"<div>"+ contents[con].member.mbti + "</div>" +
					"<div>"+ contents[con].contents01 + "</div>" +
					"<div id='eachNick'>"+ contents[con].member.nickName + "</div>" +
					"</div>" +
					"<div class='contentForm02'>" +
					"<div class='commentTd'>"+
					"<a class='dropCommentWrap' href='#'>" +
					"<span class='angleDown'></span>" +
					"<span class='dropComment'>답글 "+contents[con].commentNum+"개 보기</span>" +
					"</a>" + 
					"</div>" +
					"<div class='linkTd'><a class='linkTag' href=" + contents[con].link + " target='_blank'>"+ 
					"<div class='contentMent'>" + contents[con].title + "</div>" + "<span class='linkIconCinema'></span></a></div>" +
					"<div class='cultureBoard_reportDate'>" + timeForToday02(contents[con].reportingDate) + "</div>" +
					"</div>" +
					"</div>" +
					"<div class='commentWrap'>" +
					"<div class='comment'>"	+
						"<div class='commentWrite'>" +
						memberImg +
						"<div class='authorWrap'>" +
						"<div class='authorInfo'>" +
						memberIsNull +
						"</div>"+
						"<form class='writeTextForm'>"+
						"<div class='writeText'>"+
						"<input type='text' name='comment' placeholder='공개글 작성하기' maxlength='500' autocomplete='off'/>"+
						"<span class='commentErrorMsg'></span>"+
						"</div>"+
						"<div class='writeSubmit'>" +
						loginNull_commentSubmit	+					
						"</div>" +	
						"</form>"+
						"</div>" +
					"</div>" +
					"<div class='commentReadWrap'>" +
						commentSum +
					 "</div>"
					);	
					commentAppend = new Array();
					commentSum ="";
				}
			}
			
			
			else if(contentType == "T"){
				$('#contentWrap').append(
					"<div id='contentList'>"+
					"</div>"
				);
				$('#contentList').append(
    				"<div id='contentHead'>" +
					"<p>좋아요</p>" +
					"<p>MBTI</p>" +
					"<p>여행지</p>" +
					"<p>닉네임</p>" +
					"</div>"
				);
				
				for(con in contents){
					
					/**댓글작성 부분 비로그인/로그인시 */
					/**댓글 submit 버튼 비로그인/로그인시 */
					if(memberInfo == null || memberInfo == ""){
						memberIsNull=
						"<span class='authorLv'></span>" +
						"<span class='authorNickname'></span>" +
						"<span class='authorMbti'></span>";
						
						loginNull_commentSubmit = 
						"<input type='button' value='댓글'/>";
		
						
					}else{
						memberIsNull=
						"<span class='authorLv'>LV. "+ memberInfo.level + "</span>" +
						"<span class='authorNickname'>" + memberInfo.nickName + "</span>" +
						"<span class='authorMbti'>"+memberInfo.mbti+"</span>";
						
						loginNull_commentSubmit = 
						"<input type='button' value='댓글'" + " onclick='writeCommentSubmit(this.form,"+ contents[con].id + ","+ loginId +",this); return false'/>";
					}
					
					
					
					let state01 = 0;
					/** 비로그인/로그인시 메인컨텐츠별 추천*/
					if(memberInfo != null){
						
						for(lct in likeContents){
						
							if(likeContents[lct].id == contents[con].id){
								likesOR = "<a href='javascript:likes()' data-boardId =" + contents[con].id +" data-loginId=" + loginId + " class='cultureBoard_likes'></a>";
								state01 = 1;
							}
						
						}
						
						if(state01 == 0){
							likesOR = "<a href='javascript:likes()' data-boardId =" + contents[con].id +" data-loginId="+ loginId + " class='cultureBoard_unlikes'></a>";
							state01 = 0;
						}
					
					}else{
						likesOR = "<a href='#' class='cultureBoard_unlikes'></a>";
					}
						
					
					
					for(com in cultureBoardComment){
						let state02 = 0;
						/** 비로그인/로그인시 댓글추천 */
						if(memberInfo != null){
							
							for(lcm in likeComments){
								
								if(likeComments[lcm].id == cultureBoardComment[com].id){
									
						   			 commentLikesOr = "<a href='#'" + " onclick='commentLikes("+ cultureBoardComment[com].id + ","+ loginId +",this); return false' class='cultureBoardComment_likes'></a>";
									 state02 = 1;
								}
								
							}
							
							
							if(state02 == 0){
				   				commentLikesOr = "<a href='#'" + " onclick='commentLikes("+ cultureBoardComment[com].id + ","+ loginId +",this); return false' class='cultureBoardComment_unlikes'></a>";
								state02 = 0;
							}
						
						
						}else{
			    			commentLikesOr = "<a href='#' class='cultureBoardComment_unlikes'></a>";
						}
						
						
						/**비로그인/로그인시 댓글삭제 */
						if(memberInfo != null){
							if(loginId == cultureBoardComment[com].member.id){
							    commentDelOr =
							    "<div class='delIcon'>"+
							    "<a href='#' class='delCommentBtn' data-boardId ="+ contents[con].id + " data-loginId="+ loginId + " data-commentId="+ cultureBoardComment[com].id +">X</a>"+
								"</div>";
							}else{
							    commentDelOr=
							    "<div class='delIcon'>"+
								"</div>";
							}	
						}else{
							commentDelOr=
						    "<div class='delIcon'>"+
							"</div>";
						}
						
						
					
							
						if(contents[con].id == cultureBoardComment[com].cultureBoard.id){		
							commentAppend[com] = 
							"<div class='commentRead'>"+
							"<div class='memberImg'"+
							"style='background: url(" + cultureBoardComment[com].member.profileImg + ")  0 0 / cover'></div>"+
							"<div class='memberWrapOfWrap'>"+
							"<div class='memberWrap'>"+
							"<div class='memberInfo'>"+
							"<span class='eachMember memberLv'>LV. "+ cultureBoardComment[com].member.level+"</span>"+
							"<span class='eachMember memberNickname'>"+cultureBoardComment[com].member.nickName+"</span>"+
							"<span class='eachMember memberMbti'>"+cultureBoardComment[com].member.mbti+"</span>"+
							"<span class='eachMember comment_reportDate'>" + timeForToday02(cultureBoardComment[com].reportingDate) + "</span>" +
							"</div>"+
							"<div class='memberComment'>"+ cultureBoardComment[com].comment+"</div>"+	
							"</div>"+
							
							"<div class='memberLike'>"+
							commentLikesOr+
							"<p class='commentLikesCount'>"+ cultureBoardComment[com].likes+"</p>"+
							"</div>"+
							
							"<div class='memberDelete'>"+
							commentDelOr+
							"</div>"+
							"</div>"+
							"</div>";
						}
						
						if(memberInfo != null){
							memberImg = "<div class='authorImg' style='background: url(" + memberInfo.profileImg + ") 0 0 / cover'></div>";
							
						}else{
							memberImg = "<div class='authorImg'></div>";
						}
						
	
					}
					
					/**댓글 다른 보드 중복 방지 변수 */
					let commentSum;
					commentSum = commentAppend.join(" ");
					/************************ */
					
					$('#contentList').append(
					"<div class='content'>" +
					"<div class='contentForm01'>" +
					"<div class='likeWrap'>" +
					 likesOR +
					"<p class='likesCount'>"+ contents[con].likes + "</p>" +
					"</div>" +
					"<div>"+ contents[con].member.mbti + "</div>" +
					"<div>"+ contents[con].contents01 + "</div>" +
					"<div id='eachNick'>"+ contents[con].member.nickName + "</div>" +
					"</div>" +
					"<div class='contentForm02'>" +
					"<div class='commentTd'>"+
					"<a class='dropCommentWrap' href='#'>" +
					"<span class='angleDown'></span>" +
					"<span class='dropComment'>답글 "+contents[con].commentNum+"개 보기</span>" +
					"</a>" + 
					"</div>" +
					"<div class='linkTd'><a class='linkTag' href=" + contents[con].link + " target='_blank'>"+ 
					"<div class='contentMent'>" + contents[con].title + "</div>" + "<span class='linkIconTrip'></span></a></div>" +
					"<div class='cultureBoard_reportDate'>" + timeForToday02(contents[con].reportingDate) + "</div>" +
					"</div>" +
					"</div>" +
					"<div class='commentWrap'>" +
					"<div class='comment'>"	+
						"<div class='commentWrite'>" +
						memberImg +
						"<div class='authorWrap'>" +
						"<div class='authorInfo'>" +
						memberIsNull +
						"</div>"+
						"<form class='writeTextForm'>"+
						"<div class='writeText'>"+
						"<input type='text' name='comment' placeholder='공개글 작성하기' maxlength='500' autocomplete='off'/>"+
						"<span class='commentErrorMsg'></span>"+
						"</div>"+
						"<div class='writeSubmit'>" +
						loginNull_commentSubmit	+					
						"</div>" +	
						"</form>"+
						"</div>" +
					"</div>" +
					"<div class='commentReadWrap'>" +
						commentSum +
					 "</div>"
					);	
					commentAppend = new Array();
					commentSum ="";
				}
			}
			
			
			if(Array.isArray(contents) && contents.length === 0){
				
				$('#contentWrap').append(
					"<div id='nullType'>" +
						"<p>"+ mbtiValue + "에 대한 검색결과가 없습니다</p>" +
					"</div>"		
				);
			}
		}
	});	
}

//************************************************************************* */

function orderLikes(){
	
	let param = {
        'type' : contentType,
		'mbtiValue' : mbtiValue
    };
	
	$.ajax({
		type: "post",
        data: JSON.stringify(param),
        url: "/myapp/cultureBoard/orderLikes",
        contentType: "application/json; charset=UTF-8",
        success: function(data) {
			$('#contentWrap').css('overflow-y','scroll');
			$('#contentList').remove();
			$('#noneDataWrap').remove();	
			
			let contents = data["contents"];
			let memberInfo = data["memberInfo"];
			let cultureBoardComment = data["cultureBoardComment"];
			let loginId = data["loginId"];
		
			let likeContents = data["likeContents"];
			let likeComments = data["likeComments"];
		
		
			let memberIsNull;
			let loginNull_commentSubmit;
			let memberImg;

			
			if(contentType == "M"){
				$('#contentWrap').append(
					"<div id='contentList'>"+
					"</div>"
				);
				$('#contentList').append(
    				"<div id='contentHead'>" +
					"<p>좋아요</p>" +
					"<p>MBTI</p>" +
					"<p>가수</p>" +
					"<p>노래</p>" +
					"<p>닉네임</p>" +
					"</div>"
				);
				
				for(con in contents){
					
					/**댓글작성 부분 비로그인/로그인시 */
					/**댓글 submit 버튼 비로그인/로그인시 */
					if(memberInfo == null || memberInfo == ""){
						memberIsNull=
						"<span class='authorLv'></span>" +
						"<span class='authorNickname'></span>" +
						"<span class='authorMbti'></span>";
						
						loginNull_commentSubmit = 
						"<input type='button' value='댓글'/>";
		
						
					}else{
						memberIsNull=
						"<span class='authorLv'>LV. "+ memberInfo.level + "</span>" +
						"<span class='authorNickname'>" + memberInfo.nickName + "</span>" +
						"<span class='authorMbti'>"+memberInfo.mbti+"</span>";
						
						loginNull_commentSubmit = 
						"<input type='button' value='댓글'" + " onclick='writeCommentSubmit(this.form,"+ contents[con].id + ","+ loginId +",this); return false'/>";
					}
					
					
					
					let state01 = 0;
					/** 비로그인/로그인시 메인컨텐츠별 추천*/
					if(memberInfo != null){
						
						for(lct in likeContents){
						
							if(likeContents[lct].id == contents[con].id){
								likesOR = "<a href='javascript:likes()' data-boardId =" + contents[con].id +" data-loginId=" + loginId + " class='cultureBoard_likes'></a>";
								state01 = 1;
							}
						
						}
						
						if(state01 == 0){
							likesOR = "<a href='javascript:likes()' data-boardId =" + contents[con].id +" data-loginId="+ loginId + " class='cultureBoard_unlikes'></a>";
							state01 = 0;
						}
					
					}else{
						likesOR = "<a href='#' class='cultureBoard_unlikes'></a>";
					}
						
					
					
					for(com in cultureBoardComment){
						let state02 = 0;
						/** 비로그인/로그인시 댓글추천 */
						if(memberInfo != null){
							
							for(lcm in likeComments){
								
								if(likeComments[lcm].id == cultureBoardComment[com].id){
									
						   			 commentLikesOr = "<a href='#'" + " onclick='commentLikes("+ cultureBoardComment[com].id + ","+ loginId +",this); return false' class='cultureBoardComment_likes'></a>";
									 state02 = 1;
								}
								
							}
							
							
							if(state02 == 0){
				   				commentLikesOr = "<a href='#'" + " onclick='commentLikes("+ cultureBoardComment[com].id + ","+ loginId +",this); return false' class='cultureBoardComment_unlikes'></a>";
								state02 = 0;
							}
						
						
						}else{
			    			commentLikesOr = "<a href='#' class='cultureBoardComment_unlikes'></a>";
						}
						
						
						/**비로그인/로그인시 댓글삭제 */
						if(memberInfo != null){
							if(loginId == cultureBoardComment[com].member.id){
							    commentDelOr =
							    "<div class='delIcon'>"+
							    "<a href='#' class='delCommentBtn' data-boardId ="+ contents[con].id + " data-loginId="+ loginId + " data-commentId="+ cultureBoardComment[com].id +">X</a>"+
								"</div>";
							}else{
							    commentDelOr=
							    "<div class='delIcon'>"+
								"</div>";
							}	
						}else{
							commentDelOr=
						    "<div class='delIcon'>"+
							"</div>";
						}
						
						
					
							
						if(contents[con].id == cultureBoardComment[com].cultureBoard.id){		
							commentAppend[com] = 
							"<div class='commentRead'>"+
							"<div class='memberImg'"+
							"style='background: url(" + cultureBoardComment[com].member.profileImg + ")  0 0 / cover'></div>"+
							"<div class='memberWrapOfWrap'>"+
							"<div class='memberWrap'>"+
							"<div class='memberInfo'>"+
							"<span class='eachMember memberLv'>LV. "+ cultureBoardComment[com].member.level+"</span>"+
							"<span class='eachMember memberNickname'>"+cultureBoardComment[com].member.nickName+"</span>"+
							"<span class='eachMember memberMbti'>"+cultureBoardComment[com].member.mbti+"</span>"+
							"<span class='eachMember comment_reportDate'>" + timeForToday02(cultureBoardComment[com].reportingDate) + "</span>" +
							"</div>"+
							"<div class='memberComment'>"+ cultureBoardComment[com].comment+"</div>"+	
							"</div>"+
							
							"<div class='memberLike'>"+
							commentLikesOr+
							"<p class='commentLikesCount'>"+ cultureBoardComment[com].likes+"</p>"+
							"</div>"+
							
							"<div class='memberDelete'>"+
							commentDelOr+
							"</div>"+
							"</div>"+
							"</div>";
						}
						
						if(memberInfo != null){
							memberImg = "<div class='authorImg' style='background: url(" + memberInfo.profileImg + ") 0 0 / cover'></div>";
							
						}else{
							memberImg = "<div class='authorImg'></div>";
						}
						
	
					}
					
					/**댓글 다른 보드 중복 방지 변수 */
					let commentSum;
					commentSum = commentAppend.join(" ");
					/************************ */
					
					$('#contentList').append(
					"<div class='content'>" +
					"<div class='contentForm01'>" +
					"<div class='likeWrap'>" +
					 likesOR +
					"<p class='likesCount'>"+ contents[con].likes + "</p>" +
					"</div>" +
					"<div>"+ contents[con].member.mbti + "</div>" +
					"<div>"+ contents[con].contents01 + "</div>" +
					"<div>"+ contents[con].contents02 + "</div>" +
					"<div id='eachNick'>"+ contents[con].member.nickName + "</div>" +
					"</div>" +
					"<div class='contentForm02'>" +
					"<div class='commentTd'>"+
					"<a class='dropCommentWrap' href='#'>" +
					"<span class='angleDown'></span>" +
					"<span class='dropComment'>답글 "+contents[con].commentNum+"개 보기</span>" +
					"</a>" + 
					"</div>" +
					"<div class='linkTd'><a class='linkTag' href=" + contents[con].link + " target='_blank'>"+
					"<div class='contentMent'>" + contents[con].title + "</div>" + "<span class='linkIconMusic'></span></a></div>" +
					"<div class='cultureBoard_reportDate'>" + timeForToday02(contents[con].reportingDate) + "</div>" +
					"</div>" +
					"</div>" +
					"<div class='commentWrap'>" +
					"<div class='comment'>"	+
						"<div class='commentWrite'>" +
						memberImg +
						"<div class='authorWrap'>" +
						"<div class='authorInfo'>" +
						memberIsNull +
						"</div>"+
						"<form class='writeTextForm'>"+
						"<div class='writeText'>"+
						"<input type='text' name='comment' placeholder='공개글 작성하기' maxlength='500' autocomplete='off'/>"+
						"<span class='commentErrorMsg'></span>"+
						"</div>"+
						"<div class='writeSubmit'>" +
						loginNull_commentSubmit	+					
						"</div>" +	
						"</form>"+
						"</div>" +
					"</div>" +
					"<div class='commentReadWrap'>" +
						commentSum +
					 "</div>"
					);	
					commentAppend = new Array();
					commentSum ="";
				}
			}
			
			
			
			
			
			
			else if(contentType == "C"){
				$('#contentWrap').append(
					"<div id='contentList'>"+
					"</div>"
				);
				$('#contentList').append(
    				"<div id='contentHead'>" +
					"<p>좋아요</p>" +
					"<p>MBTI</p>" +
					"<p>영화</p>" +
					"<p>닉네임</p>" +
					"</div>"
				);
				
				for(con in contents){
					
					/**댓글작성 부분 비로그인/로그인시 */
					/**댓글 submit 버튼 비로그인/로그인시 */
					if(memberInfo == null || memberInfo == ""){
						memberIsNull=
						"<span class='authorLv'></span>" +
						"<span class='authorNickname'></span>" +
						"<span class='authorMbti'></span>";
						
						loginNull_commentSubmit = 
						"<input type='button' value='댓글'/>";
		
						
					}else{
						memberIsNull=
						"<span class='authorLv'>LV. "+ memberInfo.level + "</span>" +
						"<span class='authorNickname'>" + memberInfo.nickName + "</span>" +
						"<span class='authorMbti'>"+memberInfo.mbti+"</span>";
						
						loginNull_commentSubmit = 
						"<input type='button' value='댓글'" + " onclick='writeCommentSubmit(this.form,"+ contents[con].id + ","+ loginId +",this); return false'/>";
					}
					
					
					
					let state01 = 0;
					/** 비로그인/로그인시 메인컨텐츠별 추천*/
					if(memberInfo != null){
						
						for(lct in likeContents){
						
							if(likeContents[lct].id == contents[con].id){
								likesOR = "<a href='javascript:likes()' data-boardId =" + contents[con].id +" data-loginId=" + loginId + " class='cultureBoard_likes'></a>";
								state01 = 1;
							}
						
						}
						
						if(state01 == 0){
							likesOR = "<a href='javascript:likes()' data-boardId =" + contents[con].id +" data-loginId="+ loginId + " class='cultureBoard_unlikes'></a>";
							state01 = 0;
						}
					
					}else{
						likesOR = "<a href='#' class='cultureBoard_unlikes'></a>";
					}
						
					
					
					for(com in cultureBoardComment){
						let state02 = 0;
						/** 비로그인/로그인시 댓글추천 */
						if(memberInfo != null){
							
							for(lcm in likeComments){
								
								if(likeComments[lcm].id == cultureBoardComment[com].id){
									
						   			 commentLikesOr = "<a href='#'" + " onclick='commentLikes("+ cultureBoardComment[com].id + ","+ loginId +",this); return false' class='cultureBoardComment_likes'></a>";
									 state02 = 1;
								}
								
							}
							
							
							if(state02 == 0){
				   				commentLikesOr = "<a href='#'" + " onclick='commentLikes("+ cultureBoardComment[com].id + ","+ loginId +",this); return false' class='cultureBoardComment_unlikes'></a>";
								state02 = 0;
							}
						
						
						}else{
			    			commentLikesOr = "<a href='#' class='cultureBoardComment_unlikes'></a>";
						}
						
						
						/**비로그인/로그인시 댓글삭제 */
						if(memberInfo != null){
							if(loginId == cultureBoardComment[com].member.id){
							    commentDelOr =
							    "<div class='delIcon'>"+
							    "<a href='#' class='delCommentBtn' data-boardId ="+ contents[con].id + " data-loginId="+ loginId + " data-commentId="+ cultureBoardComment[com].id +">X</a>"+
								"</div>";
							}else{
							    commentDelOr=
							    "<div class='delIcon'>"+
								"</div>";
							}	
						}else{
							commentDelOr=
						    "<div class='delIcon'>"+
							"</div>";
						}
						
						
					
							
						if(contents[con].id == cultureBoardComment[com].cultureBoard.id){		
							commentAppend[com] = 
							"<div class='commentRead'>"+
							"<div class='memberImg'"+
							"style='background: url(" + cultureBoardComment[com].member.profileImg + ")  0 0 / cover'></div>"+
							"<div class='memberWrapOfWrap'>"+
							"<div class='memberWrap'>"+
							"<div class='memberInfo'>"+
							"<span class='eachMember memberLv'>LV. "+ cultureBoardComment[com].member.level+"</span>"+
							"<span class='eachMember memberNickname'>"+cultureBoardComment[com].member.nickName+"</span>"+
							"<span class='eachMember memberMbti'>"+cultureBoardComment[com].member.mbti+"</span>"+
							"<span class='eachMember comment_reportDate'>" + timeForToday02(cultureBoardComment[com].reportingDate) + "</span>" +
							"</div>"+
							"<div class='memberComment'>"+ cultureBoardComment[com].comment+"</div>"+	
							"</div>"+
							
							"<div class='memberLike'>"+
							commentLikesOr+
							"<p class='commentLikesCount'>"+ cultureBoardComment[com].likes+"</p>"+
							"</div>"+
							
							"<div class='memberDelete'>"+
							commentDelOr+
							"</div>"+
							"</div>"+
							"</div>";
						}
						
						if(memberInfo != null){
							memberImg = "<div class='authorImg' style='background: url(" + memberInfo.profileImg + ") 0 0 / cover'></div>";
							
						}else{
							memberImg = "<div class='authorImg'></div>";
						}
						
	
					}
					
					/**댓글 다른 보드 중복 방지 변수 */
					let commentSum;
					commentSum = commentAppend.join(" ");
					/************************ */
					
					$('#contentList').append(
					"<div class='content'>" +
					"<div class='contentForm01'>" +
					"<div class='likeWrap'>" +
					 likesOR +
					"<p class='likesCount'>"+ contents[con].likes + "</p>" +
					"</div>" +
					"<div>"+ contents[con].member.mbti + "</div>" +
					"<div>"+ contents[con].contents01 + "</div>" +
					"<div id='eachNick'>"+ contents[con].member.nickName + "</div>" +
					"</div>" +
					"<div class='contentForm02'>" +
					"<div class='commentTd'>"+
					"<a class='dropCommentWrap' href='#'>" +
					"<span class='angleDown'></span>" +
					"<span class='dropComment'>답글 "+contents[con].commentNum+"개 보기</span>" +
					"</a>" + 
					"</div>" +
					"<div class='linkTd'><a class='linkTag' href=" + contents[con].link + " target='_blank'>"+ 
					"<div class='contentMent'>" + contents[con].title + "</div>" + "<span class='linkIconCinema'></span></a></div>" +
					"<div class='cultureBoard_reportDate'>" + timeForToday02(contents[con].reportingDate) + "</div>" +
					"</div>" +
					"</div>" +
					"<div class='commentWrap'>" +
					"<div class='comment'>"	+
						"<div class='commentWrite'>" +
						memberImg +
						"<div class='authorWrap'>" +
						"<div class='authorInfo'>" +
						memberIsNull +
						"</div>"+
						"<form class='writeTextForm'>"+
						"<div class='writeText'>"+
						"<input type='text' name='comment' placeholder='공개글 작성하기' maxlength='500' autocomplete='off'/>"+
						"<span class='commentErrorMsg'></span>"+
						"</div>"+
						"<div class='writeSubmit'>" +
						loginNull_commentSubmit	+					
						"</div>" +	
						"</form>"+
						"</div>" +
					"</div>" +
					"<div class='commentReadWrap'>" +
						commentSum +
					 "</div>"
					);	
					commentAppend = new Array();
					commentSum ="";
				}
			}
			
			
			else if(contentType == "T"){
				$('#contentWrap').append(
					"<div id='contentList'>"+
					"</div>"
				);
				$('#contentList').append(
    				"<div id='contentHead'>" +
					"<p>좋아요</p>" +
					"<p>MBTI</p>" +
					"<p>여행지</p>" +
					"<p>닉네임</p>" +
					"</div>"
				);
				
				for(con in contents){
					
					/**댓글작성 부분 비로그인/로그인시 */
					/**댓글 submit 버튼 비로그인/로그인시 */
					if(memberInfo == null || memberInfo == ""){
						memberIsNull=
						"<span class='authorLv'></span>" +
						"<span class='authorNickname'></span>" +
						"<span class='authorMbti'></span>";
						
						loginNull_commentSubmit = 
						"<input type='button' value='댓글'/>";
		
						
					}else{
						memberIsNull=
						"<span class='authorLv'>LV. "+ memberInfo.level + "</span>" +
						"<span class='authorNickname'>" + memberInfo.nickName + "</span>" +
						"<span class='authorMbti'>"+memberInfo.mbti+"</span>";
						
						loginNull_commentSubmit = 
						"<input type='button' value='댓글'" + " onclick='writeCommentSubmit(this.form,"+ contents[con].id + ","+ loginId +",this); return false'/>";
					}
					
					
					
					let state01 = 0;
					/** 비로그인/로그인시 메인컨텐츠별 추천*/
					if(memberInfo != null){
						
						for(lct in likeContents){
						
							if(likeContents[lct].id == contents[con].id){
								likesOR = "<a href='javascript:likes()' data-boardId =" + contents[con].id +" data-loginId=" + loginId + " class='cultureBoard_likes'></a>";
								state01 = 1;
							}
						
						}
						
						if(state01 == 0){
							likesOR = "<a href='javascript:likes()' data-boardId =" + contents[con].id +" data-loginId="+ loginId + " class='cultureBoard_unlikes'></a>";
							state01 = 0;
						}
					
					}else{
						likesOR = "<a href='#' class='cultureBoard_unlikes'></a>";
					}
						
					
					
					for(com in cultureBoardComment){
						let state02 = 0;
						/** 비로그인/로그인시 댓글추천 */
						if(memberInfo != null){
							
							for(lcm in likeComments){
								
								if(likeComments[lcm].id == cultureBoardComment[com].id){
									
						   			 commentLikesOr = "<a href='#'" + " onclick='commentLikes("+ cultureBoardComment[com].id + ","+ loginId +",this); return false' class='cultureBoardComment_likes'></a>";
									 state02 = 1;
								}
								
							}
							
							
							if(state02 == 0){
				   				commentLikesOr = "<a href='#'" + " onclick='commentLikes("+ cultureBoardComment[com].id + ","+ loginId +",this); return false' class='cultureBoardComment_unlikes'></a>";
								state02 = 0;
							}
						
						
						}else{
			    			commentLikesOr = "<a href='#' class='cultureBoardComment_unlikes'></a>";
						}
						
						
						/**비로그인/로그인시 댓글삭제 */
						if(memberInfo != null){
							if(loginId == cultureBoardComment[com].member.id){
							    commentDelOr =
							    "<div class='delIcon'>"+
							    "<a href='#' class='delCommentBtn' data-boardId ="+ contents[con].id + " data-loginId="+ loginId + " data-commentId="+ cultureBoardComment[com].id +">X</a>"+
								"</div>";
							}else{
							    commentDelOr=
							    "<div class='delIcon'>"+
								"</div>";
							}	
						}else{
							commentDelOr=
						    "<div class='delIcon'>"+
							"</div>";
						}
						
						
					
							
						if(contents[con].id == cultureBoardComment[com].cultureBoard.id){		
							commentAppend[com] = 
							"<div class='commentRead'>"+
							"<div class='memberImg'"+
							"style='background: url(" + cultureBoardComment[com].member.profileImg + ")  0 0 / cover'></div>"+
							"<div class='memberWrapOfWrap'>"+
							"<div class='memberWrap'>"+
							"<div class='memberInfo'>"+
							"<span class='eachMember memberLv'>LV. "+ cultureBoardComment[com].member.level+"</span>"+
							"<span class='eachMember memberNickname'>"+cultureBoardComment[com].member.nickName+"</span>"+
							"<span class='eachMember memberMbti'>"+cultureBoardComment[com].member.mbti+"</span>"+
							"<span class='eachMember comment_reportDate'>" + timeForToday02(cultureBoardComment[com].reportingDate) + "</span>" +
							"</div>"+
							"<div class='memberComment'>"+ cultureBoardComment[com].comment+"</div>"+	
							"</div>"+
							
							"<div class='memberLike'>"+
							commentLikesOr+
							"<p class='commentLikesCount'>"+ cultureBoardComment[com].likes+"</p>"+
							"</div>"+
							
							"<div class='memberDelete'>"+
							commentDelOr+
							"</div>"+
							"</div>"+
							"</div>";
						}
						
						if(memberInfo != null){
							memberImg = "<div class='authorImg' style='background: url(" + memberInfo.profileImg + ") 0 0 / cover'></div>";
							
						}else{
							memberImg = "<div class='authorImg'></div>";
						}
						
	
					}
					
					/**댓글 다른 보드 중복 방지 변수 */
					let commentSum;
					commentSum = commentAppend.join(" ");
					/************************ */
					
					$('#contentList').append(
					"<div class='content'>" +
					"<div class='contentForm01'>" +
					"<div class='likeWrap'>" +
					 likesOR +
					"<p class='likesCount'>"+ contents[con].likes + "</p>" +
					"</div>" +
					"<div>"+ contents[con].member.mbti + "</div>" +
					"<div>"+ contents[con].contents01 + "</div>" +
					"<div id='eachNick'>"+ contents[con].member.nickName + "</div>" +
					"</div>" +
					"<div class='contentForm02'>" +
					"<div class='commentTd'>"+
					"<a class='dropCommentWrap' href='#'>" +
					"<span class='angleDown'></span>" +
					"<span class='dropComment'>답글 "+contents[con].commentNum+"개 보기</span>" +
					"</a>" + 
					"</div>" +
					"<div class='linkTd'><a class='linkTag' href=" + contents[con].link + " target='_blank'>"+ 
					"<div class='contentMent'>" + contents[con].title + "</div>" + "<span class='linkIconTrip'></span></a></div>" +
					"<div class='cultureBoard_reportDate'>" + timeForToday02(contents[con].reportingDate) + "</div>" +
					"</div>" +
					"</div>" +
					"<div class='commentWrap'>" +
					"<div class='comment'>"	+
						"<div class='commentWrite'>" +
						memberImg +
						"<div class='authorWrap'>" +
						"<div class='authorInfo'>" +
						memberIsNull +
						"</div>"+
						"<form class='writeTextForm'>"+
						"<div class='writeText'>"+
						"<input type='text' name='comment' placeholder='공개글 작성하기' maxlength='500' autocomplete='off'/>"+
						"<span class='commentErrorMsg'></span>"+
						"</div>"+
						"<div class='writeSubmit'>" +
						loginNull_commentSubmit	+					
						"</div>" +	
						"</form>"+
						"</div>" +
					"</div>" +
					"<div class='commentReadWrap'>" +
						commentSum +
					 "</div>"
					);	
					commentAppend = new Array();
					commentSum ="";
				}
			}
		}
	});
}


/****************************************/

function selectBestComment(){
	let param = {
        'type' : contentType,
		'mbtiValue' : mbtiValue
    };
	
	$.ajax({
		type: "post",
        data: JSON.stringify(param),
        url: "/myapp/cultureBoard/orderBestComment",
        contentType: "application/json; charset=UTF-8",
        success: function(data) {
			$('#contentWrap').css('overflow-y','scroll');
			$('#contentList').remove();
			$('#noneDataWrap').remove();	
			
			let contents = data["contents"];
			let memberInfo = data["memberInfo"];
			let cultureBoardComment = data["cultureBoardComment"];
			let loginId = data["loginId"];
		
			let likeContents = data["likeContents"];
			let likeComments = data["likeComments"];
		
		
			let memberIsNull;
			let loginNull_commentSubmit;
			let memberImg;

			
			if(contentType == "M"){
				$('#contentWrap').append(
					"<div id='contentList'>"+
					"</div>"
				);
				$('#contentList').append(
    				"<div id='contentHead'>" +
					"<p>좋아요</p>" +
					"<p>MBTI</p>" +
					"<p>가수</p>" +
					"<p>노래</p>" +
					"<p>닉네임</p>" +
					"</div>"
				);
				
				for(con in contents){
					
					/**댓글작성 부분 비로그인/로그인시 */
					/**댓글 submit 버튼 비로그인/로그인시 */
					if(memberInfo == null || memberInfo == ""){
						memberIsNull=
						"<span class='authorLv'></span>" +
						"<span class='authorNickname'></span>" +
						"<span class='authorMbti'></span>";
						
						loginNull_commentSubmit = 
						"<input type='button' value='댓글'/>";
		
						
					}else{
						memberIsNull=
						"<span class='authorLv'>LV. "+ memberInfo.level + "</span>" +
						"<span class='authorNickname'>" + memberInfo.nickName + "</span>" +
						"<span class='authorMbti'>"+memberInfo.mbti+"</span>";
						
						loginNull_commentSubmit = 
						"<input type='button' value='댓글'" + " onclick='writeCommentSubmit(this.form,"+ contents[con].id + ","+ loginId +",this); return false'/>";
					}
					
					
					
					let state01 = 0;
					/** 비로그인/로그인시 메인컨텐츠별 추천*/
					if(memberInfo != null){
						
						for(lct in likeContents){
						
							if(likeContents[lct].id == contents[con].id){
								likesOR = "<a href='javascript:likes()' data-boardId =" + contents[con].id +" data-loginId=" + loginId + " class='cultureBoard_likes'></a>";
								state01 = 1;
							}
						
						}
						
						if(state01 == 0){
							likesOR = "<a href='javascript:likes()' data-boardId =" + contents[con].id +" data-loginId="+ loginId + " class='cultureBoard_unlikes'></a>";
							state01 = 0;
						}
					
					}else{
						likesOR = "<a href='#' class='cultureBoard_unlikes'></a>";
					}
						
					
					
					for(com in cultureBoardComment){
						let state02 = 0;
						/** 비로그인/로그인시 댓글추천 */
						if(memberInfo != null){
							
							for(lcm in likeComments){
								
								if(likeComments[lcm].id == cultureBoardComment[com].id){
									
						   			 commentLikesOr = "<a href='#'" + " onclick='commentLikes("+ cultureBoardComment[com].id + ","+ loginId +",this); return false' class='cultureBoardComment_likes'></a>";
									 state02 = 1;
								}
								
							}
							
							
							if(state02 == 0){
				   				commentLikesOr = "<a href='#'" + " onclick='commentLikes("+ cultureBoardComment[com].id + ","+ loginId +",this); return false' class='cultureBoardComment_unlikes'></a>";
								state02 = 0;
							}
						
						
						}else{
			    			commentLikesOr = "<a href='#' class='cultureBoardComment_unlikes'></a>";
						}
						
						
						/**비로그인/로그인시 댓글삭제 */
						if(memberInfo != null){
							if(loginId == cultureBoardComment[com].member.id){
							    commentDelOr =
							    "<div class='delIcon'>"+
							    "<a href='#' class='delCommentBtn' data-boardId ="+ contents[con].id + " data-loginId="+ loginId + " data-commentId="+ cultureBoardComment[com].id +">X</a>"+
								"</div>";
							}else{
							    commentDelOr=
							    "<div class='delIcon'>"+
								"</div>";
							}	
						}else{
							commentDelOr=
						    "<div class='delIcon'>"+
							"</div>";
						}
						
						
					
							
						if(contents[con].id == cultureBoardComment[com].cultureBoard.id){		
							commentAppend[com] = 
							"<div class='commentRead'>"+
							"<div class='memberImg'"+
							"style='background: url(" + cultureBoardComment[com].member.profileImg + ")  0 0 / cover'></div>"+
							"<div class='memberWrapOfWrap'>"+
							"<div class='memberWrap'>"+
							"<div class='memberInfo'>"+
							"<span class='eachMember memberLv'>LV. "+ cultureBoardComment[com].member.level+"</span>"+
							"<span class='eachMember memberNickname'>"+cultureBoardComment[com].member.nickName+"</span>"+
							"<span class='eachMember memberMbti'>"+cultureBoardComment[com].member.mbti+"</span>"+
							"<span class='eachMember comment_reportDate'>" + timeForToday02(cultureBoardComment[com].reportingDate) + "</span>" +
							"</div>"+
							"<div class='memberComment'>"+ cultureBoardComment[com].comment+"</div>"+	
							"</div>"+
							
							"<div class='memberLike'>"+
							commentLikesOr+
							"<p class='commentLikesCount'>"+ cultureBoardComment[com].likes+"</p>"+
							"</div>"+
							
							"<div class='memberDelete'>"+
							commentDelOr+
							"</div>"+
							"</div>"+
							"</div>";
						}
						
						if(memberInfo != null){
							memberImg = "<div class='authorImg' style='background: url(" + memberInfo.profileImg + ") 0 0 / cover'></div>";
							
						}else{
							memberImg = "<div class='authorImg'></div>";
						}
						
	
					}
					
					/**댓글 다른 보드 중복 방지 변수 */
					let commentSum;
					commentSum = commentAppend.join(" ");
					/************************ */
					
					$('#contentList').append(
					"<div class='content'>" +
					"<div class='contentForm01'>" +
					"<div class='likeWrap'>" +
					 likesOR +
					"<p class='likesCount'>"+ contents[con].likes + "</p>" +
					"</div>" +
					"<div>"+ contents[con].member.mbti + "</div>" +
					"<div>"+ contents[con].contents01 + "</div>" +
					"<div>"+ contents[con].contents02 + "</div>" +
					"<div id='eachNick'>"+ contents[con].member.nickName + "</div>" +
					"</div>" +
					"<div class='contentForm02'>" +
					"<div class='commentTd'>"+
					"<a class='dropCommentWrap' href='#'>" +
					"<span class='angleDown'></span>" +
					"<span class='dropComment'>답글 "+contents[con].commentNum+"개 보기</span>" +
					"</a>" + 
					"</div>" +
					"<div class='linkTd'><a class='linkTag' href=" + contents[con].link + " target='_blank'>"+
					"<div class='contentMent'>" + contents[con].title + "</div>" + "<span class='linkIconMusic'></span></a></div>" +
					"<div class='cultureBoard_reportDate'>" + timeForToday02(contents[con].reportingDate) + "</div>" +
					"</div>" +
					"</div>" +
					"<div class='commentWrap'>" +
					"<div class='comment'>"	+
						"<div class='commentWrite'>" +
						memberImg +
						"<div class='authorWrap'>" +
						"<div class='authorInfo'>" +
						memberIsNull +
						"</div>"+
						"<form class='writeTextForm'>"+
						"<div class='writeText'>"+
						"<input type='text' name='comment' placeholder='공개글 작성하기' maxlength='500' autocomplete='off'/>"+
						"<span class='commentErrorMsg'></span>"+
						"</div>"+
						"<div class='writeSubmit'>" +
						loginNull_commentSubmit	+					
						"</div>" +	
						"</form>"+
						"</div>" +
					"</div>" +
					"<div class='commentReadWrap'>" +
						commentSum +
					 "</div>"
					);	
					commentAppend = new Array();
					commentSum ="";
				}
			}
			
			
			
			
			
			
			else if(contentType == "C"){
				$('#contentWrap').append(
					"<div id='contentList'>"+
					"</div>"
				);
				$('#contentList').append(
    				"<div id='contentHead'>" +
					"<p>좋아요</p>" +
					"<p>MBTI</p>" +
					"<p>영화</p>" +
					"<p>닉네임</p>" +
					"</div>"
				);
				
				for(con in contents){
					
					/**댓글작성 부분 비로그인/로그인시 */
					/**댓글 submit 버튼 비로그인/로그인시 */
					if(memberInfo == null || memberInfo == ""){
						memberIsNull=
						"<span class='authorLv'></span>" +
						"<span class='authorNickname'></span>" +
						"<span class='authorMbti'></span>";
						
						loginNull_commentSubmit = 
						"<input type='button' value='댓글'/>";
		
						
					}else{
						memberIsNull=
						"<span class='authorLv'>LV. "+ memberInfo.level + "</span>" +
						"<span class='authorNickname'>" + memberInfo.nickName + "</span>" +
						"<span class='authorMbti'>"+memberInfo.mbti+"</span>";
						
						loginNull_commentSubmit = 
						"<input type='button' value='댓글'" + " onclick='writeCommentSubmit(this.form,"+ contents[con].id + ","+ loginId +",this); return false'/>";
					}
					
					
					
					let state01 = 0;
					/** 비로그인/로그인시 메인컨텐츠별 추천*/
					if(memberInfo != null){
						
						for(lct in likeContents){
						
							if(likeContents[lct].id == contents[con].id){
								likesOR = "<a href='javascript:likes()' data-boardId =" + contents[con].id +" data-loginId=" + loginId + " class='cultureBoard_likes'></a>";
								state01 = 1;
							}
						
						}
						
						if(state01 == 0){
							likesOR = "<a href='javascript:likes()' data-boardId =" + contents[con].id +" data-loginId="+ loginId + " class='cultureBoard_unlikes'></a>";
							state01 = 0;
						}
					
					}else{
						likesOR = "<a href='#' class='cultureBoard_unlikes'></a>";
					}
						
					
					
					for(com in cultureBoardComment){
						let state02 = 0;
						/** 비로그인/로그인시 댓글추천 */
						if(memberInfo != null){
							
							for(lcm in likeComments){
								
								if(likeComments[lcm].id == cultureBoardComment[com].id){
									
						   			 commentLikesOr = "<a href='#'" + " onclick='commentLikes("+ cultureBoardComment[com].id + ","+ loginId +",this); return false' class='cultureBoardComment_likes'></a>";
									 state02 = 1;
								}
								
							}
							
							
							if(state02 == 0){
				   				commentLikesOr = "<a href='#'" + " onclick='commentLikes("+ cultureBoardComment[com].id + ","+ loginId +",this); return false' class='cultureBoardComment_unlikes'></a>";
								state02 = 0;
							}
						
						
						}else{
			    			commentLikesOr = "<a href='#' class='cultureBoardComment_unlikes'></a>";
						}
						
						
						/**비로그인/로그인시 댓글삭제 */
						if(memberInfo != null){
							if(loginId == cultureBoardComment[com].member.id){
							    commentDelOr =
							    "<div class='delIcon'>"+
							    "<a href='#' class='delCommentBtn' data-boardId ="+ contents[con].id + " data-loginId="+ loginId + " data-commentId="+ cultureBoardComment[com].id +">X</a>"+
								"</div>";
							}else{
							    commentDelOr=
							    "<div class='delIcon'>"+
								"</div>";
							}	
						}else{
							commentDelOr=
						    "<div class='delIcon'>"+
							"</div>";
						}
						
						
					
							
						if(contents[con].id == cultureBoardComment[com].cultureBoard.id){		
							commentAppend[com] = 
							"<div class='commentRead'>"+
							"<div class='memberImg'"+
							"style='background: url(" + cultureBoardComment[com].member.profileImg + ")  0 0 / cover'></div>"+
							"<div class='memberWrapOfWrap'>"+
							"<div class='memberWrap'>"+
							"<div class='memberInfo'>"+
							"<span class='eachMember memberLv'>LV. "+ cultureBoardComment[com].member.level+"</span>"+
							"<span class='eachMember memberNickname'>"+cultureBoardComment[com].member.nickName+"</span>"+
							"<span class='eachMember memberMbti'>"+cultureBoardComment[com].member.mbti+"</span>"+
							"<span class='eachMember comment_reportDate'>" + timeForToday02(cultureBoardComment[com].reportingDate) + "</span>" +
							"</div>"+
							"<div class='memberComment'>"+ cultureBoardComment[com].comment+"</div>"+	
							"</div>"+
							
							"<div class='memberLike'>"+
							commentLikesOr+
							"<p class='commentLikesCount'>"+ cultureBoardComment[com].likes+"</p>"+
							"</div>"+
							
							"<div class='memberDelete'>"+
							commentDelOr+
							"</div>"+
							"</div>"+
							"</div>";
						}
						
						if(memberInfo != null){
							memberImg = "<div class='authorImg' style='background: url(" + memberInfo.profileImg + ") 0 0 / cover'></div>";
							
						}else{
							memberImg = "<div class='authorImg'></div>";
						}
						
	
					}
					
					/**댓글 다른 보드 중복 방지 변수 */
					let commentSum;
					commentSum = commentAppend.join(" ");
					/************************ */
					
					$('#contentList').append(
					"<div class='content'>" +
					"<div class='contentForm01'>" +
					"<div class='likeWrap'>" +
					 likesOR +
					"<p class='likesCount'>"+ contents[con].likes + "</p>" +
					"</div>" +
					"<div>"+ contents[con].member.mbti + "</div>" +
					"<div>"+ contents[con].contents01 + "</div>" +
					"<div id='eachNick'>"+ contents[con].member.nickName + "</div>" +
					"</div>" +
					"<div class='contentForm02'>" +
					"<div class='commentTd'>"+
					"<a class='dropCommentWrap' href='#'>" +
					"<span class='angleDown'></span>" +
					"<span class='dropComment'>답글 "+contents[con].commentNum+"개 보기</span>" +
					"</a>" + 
					"</div>" +
					"<div class='linkTd'><a class='linkTag' href=" + contents[con].link + " target='_blank'>"+ 
					"<div class='contentMent'>" + contents[con].title + "</div>" + "<span class='linkIconCinema'></span></a></div>" +
					"<div class='cultureBoard_reportDate'>" + timeForToday02(contents[con].reportingDate) + "</div>" +
					"</div>" +
					"</div>" +
					"<div class='commentWrap'>" +
					"<div class='comment'>"	+
						"<div class='commentWrite'>" +
						memberImg +
						"<div class='authorWrap'>" +
						"<div class='authorInfo'>" +
						memberIsNull +
						"</div>"+
						"<form class='writeTextForm'>"+
						"<div class='writeText'>"+
						"<input type='text' name='comment' placeholder='공개글 작성하기' maxlength='500' autocomplete='off'/>"+
						"<span class='commentErrorMsg'></span>"+
						"</div>"+
						"<div class='writeSubmit'>" +
						loginNull_commentSubmit	+					
						"</div>" +	
						"</form>"+
						"</div>" +
					"</div>" +
					"<div class='commentReadWrap'>" +
						commentSum +
					 "</div>"
					);	
					commentAppend = new Array();
					commentSum ="";
				}
			}
			
			
			else if(contentType == "T"){
				$('#contentWrap').append(
					"<div id='contentList'>"+
					"</div>"
				);
				$('#contentList').append(
    				"<div id='contentHead'>" +
					"<p>좋아요</p>" +
					"<p>MBTI</p>" +
					"<p>여행지</p>" +
					"<p>닉네임</p>" +
					"</div>"
				);
				
				for(con in contents){
					
					/**댓글작성 부분 비로그인/로그인시 */
					/**댓글 submit 버튼 비로그인/로그인시 */
					if(memberInfo == null || memberInfo == ""){
						memberIsNull=
						"<span class='authorLv'></span>" +
						"<span class='authorNickname'></span>" +
						"<span class='authorMbti'></span>";
						
						loginNull_commentSubmit = 
						"<input type='button' value='댓글'/>";
		
						
					}else{
						memberIsNull=
						"<span class='authorLv'>LV. "+ memberInfo.level + "</span>" +
						"<span class='authorNickname'>" + memberInfo.nickName + "</span>" +
						"<span class='authorMbti'>"+memberInfo.mbti+"</span>";
						
						loginNull_commentSubmit = 
						"<input type='button' value='댓글'" + " onclick='writeCommentSubmit(this.form,"+ contents[con].id + ","+ loginId +",this); return false'/>";
					}
					
					
					
					let state01 = 0;
					/** 비로그인/로그인시 메인컨텐츠별 추천*/
					if(memberInfo != null){
						
						for(lct in likeContents){
						
							if(likeContents[lct].id == contents[con].id){
								likesOR = "<a href='javascript:likes()' data-boardId =" + contents[con].id +" data-loginId=" + loginId + " class='cultureBoard_likes'></a>";
								state01 = 1;
							}
						
						}
						
						if(state01 == 0){
							likesOR = "<a href='javascript:likes()' data-boardId =" + contents[con].id +" data-loginId="+ loginId + " class='cultureBoard_unlikes'></a>";
							state01 = 0;
						}
					
					}else{
						likesOR = "<a href='#' class='cultureBoard_unlikes'></a>";
					}
						
					
					
					for(com in cultureBoardComment){
						let state02 = 0;
						/** 비로그인/로그인시 댓글추천 */
						if(memberInfo != null){
							
							for(lcm in likeComments){
								
								if(likeComments[lcm].id == cultureBoardComment[com].id){
									
						   			 commentLikesOr = "<a href='#'" + " onclick='commentLikes("+ cultureBoardComment[com].id + ","+ loginId +",this); return false' class='cultureBoardComment_likes'></a>";
									 state02 = 1;
								}
								
							}
							
							
							if(state02 == 0){
				   				commentLikesOr = "<a href='#'" + " onclick='commentLikes("+ cultureBoardComment[com].id + ","+ loginId +",this); return false' class='cultureBoardComment_unlikes'></a>";
								state02 = 0;
							}
						
						
						}else{
			    			commentLikesOr = "<a href='#' class='cultureBoardComment_unlikes'></a>";
						}
						
						
						/**비로그인/로그인시 댓글삭제 */
						if(memberInfo != null){
							if(loginId == cultureBoardComment[com].member.id){
							    commentDelOr =
							    "<div class='delIcon'>"+
							    "<a href='#' class='delCommentBtn' data-boardId ="+ contents[con].id + " data-loginId="+ loginId + " data-commentId="+ cultureBoardComment[com].id +">X</a>"+
								"</div>";
							}else{
							    commentDelOr=
							    "<div class='delIcon'>"+
								"</div>";
							}	
						}else{
							commentDelOr=
						    "<div class='delIcon'>"+
							"</div>";
						}
						
						
					
							
						if(contents[con].id == cultureBoardComment[com].cultureBoard.id){		
							commentAppend[com] = 
							"<div class='commentRead'>"+
							"<div class='memberImg'"+
							"style='background: url(" + cultureBoardComment[com].member.profileImg + ")  0 0 / cover'></div>"+
							"<div class='memberWrapOfWrap'>"+
							"<div class='memberWrap'>"+
							"<div class='memberInfo'>"+
							"<span class='eachMember memberLv'>LV. "+ cultureBoardComment[com].member.level+"</span>"+
							"<span class='eachMember memberNickname'>"+cultureBoardComment[com].member.nickName+"</span>"+
							"<span class='eachMember memberMbti'>"+cultureBoardComment[com].member.mbti+"</span>"+
							"<span class='eachMember comment_reportDate'>" + timeForToday02(cultureBoardComment[com].reportingDate) + "</span>" +
							"</div>"+
							"<div class='memberComment'>"+ cultureBoardComment[com].comment+"</div>"+	
							"</div>"+
							
							"<div class='memberLike'>"+
							commentLikesOr+
							"<p class='commentLikesCount'>"+ cultureBoardComment[com].likes+"</p>"+
							"</div>"+
							
							"<div class='memberDelete'>"+
							commentDelOr+
							"</div>"+
							"</div>"+
							"</div>";
						}
						
						if(memberInfo != null){
							memberImg = "<div class='authorImg' style='background: url(" + memberInfo.profileImg + ") 0 0 / cover'></div>";
							
						}else{
							memberImg = "<div class='authorImg'></div>";
						}
						
	
					}
					
					/**댓글 다른 보드 중복 방지 변수 */
					let commentSum;
					commentSum = commentAppend.join(" ");
					/************************ */
					
					$('#contentList').append(
					"<div class='content'>" +
					"<div class='contentForm01'>" +
					"<div class='likeWrap'>" +
					 likesOR +
					"<p class='likesCount'>"+ contents[con].likes + "</p>" +
					"</div>" +
					"<div>"+ contents[con].member.mbti + "</div>" +
					"<div>"+ contents[con].contents01 + "</div>" +
					"<div id='eachNick'>"+ contents[con].member.nickName + "</div>" +
					"</div>" +
					"<div class='contentForm02'>" +
					"<div class='commentTd'>"+
					"<a class='dropCommentWrap' href='#'>" +
					"<span class='angleDown'></span>" +
					"<span class='dropComment'>답글 "+contents[con].commentNum+"개 보기</span>" +
					"</a>" + 
					"</div>" +
					"<div class='linkTd'><a class='linkTag' href=" + contents[con].link + " target='_blank'>"+ 
					"<div class='contentMent'>" + contents[con].title + "</div>" + "<span class='linkIconTrip'></span></a></div>" +
					"<div class='cultureBoard_reportDate'>" + timeForToday02(contents[con].reportingDate) + "</div>" +
					"</div>" +
					"</div>" +
					"<div class='commentWrap'>" +
					"<div class='comment'>"	+
						"<div class='commentWrite'>" +
						memberImg +
						"<div class='authorWrap'>" +
						"<div class='authorInfo'>" +
						memberIsNull +
						"</div>"+
						"<form class='writeTextForm'>"+
						"<div class='writeText'>"+
						"<input type='text' name='comment' placeholder='공개글 작성하기' maxlength='500' autocomplete='off'/>"+
						"<span class='commentErrorMsg'></span>"+
						"</div>"+
						"<div class='writeSubmit'>" +
						loginNull_commentSubmit	+					
						"</div>" +	
						"</form>"+
						"</div>" +
					"</div>" +
					"<div class='commentReadWrap'>" +
						commentSum +
					 "</div>"
					);	
					commentAppend = new Array();
					commentSum ="";
				}
			}
		}
	});	
}



