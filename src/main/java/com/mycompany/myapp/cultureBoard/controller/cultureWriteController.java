package com.mycompany.myapp.cultureBoard.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mycompany.myapp.cultureBoard.command.CultureBoardCommand;
import com.mycompany.myapp.cultureBoard.service.CultureCommunityService;
import com.mycompany.myapp.domain.CultureBoardComment;


 
@Controller
public class cultureWriteController {
	@Autowired 
	CultureCommunityService cultureCommunityService;
	
	@PostMapping("successWrite")
	public ModelAndView successWrite(HttpSession session, @ModelAttribute("cultureBoardCommand") CultureBoardCommand cbc) {
		ModelAndView mav = new ModelAndView();
		
		Long memberId = Long.parseLong(String.valueOf(session.getAttribute("loginId")));
		
		String contents01 = cbc.getContents01();
		String contents02 = cbc.getContents02();
		char contentType =	cbc.getContentType().charAt(0);
		String title = cbc.getTitle();
		String link = cbc.getLink();
		
		//System.out.println(memberId + contents01 + contents02 + contentType + title + link);
		cultureCommunityService.addWrittenContent(memberId, contents01, contents02, contentType, title, link);
		

		mav.setViewName("redirect:/index");
		
		return mav;
	}
	
	
	
	@ResponseBody
	@PostMapping("/cultureBoard/successComment")
	public Map<String, Object> cultureBtn(HttpSession session, @RequestBody Map<String, String> param) {
		List<CultureBoardComment> likeComments = new ArrayList<CultureBoardComment>();
		String comment = param.get("comment");  //ajax를 통한 comment 
		Long boardId = Long.parseLong(param.get("boardId"));
		Long loginId = Long.parseLong(String.valueOf(session.getAttribute("loginId")));

		
		
		List<CultureBoardComment> cultureBoardComment = new ArrayList<CultureBoardComment>();
		cultureBoardComment = cultureCommunityService.Saved_findAllCultureBoardComment(loginId, boardId, comment);
		Long commentNum = cultureCommunityService.findCommentNumByBoardId(boardId);
		
		likeComments = cultureCommunityService.findLikesCommentByMemberId(loginId);

		
		/////////////////
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("likeComments", likeComments);
		map.put("commentNum", commentNum);
		map.put("loginId", loginId);
		map.put("boardId", boardId);
		map.put("cultureBoardComment", cultureBoardComment);
		
		return map;	
	}	
	
	
	@ResponseBody
	@PostMapping("/cultureBoard/commentLikes")
	public Map<String, String> commentLikes(HttpSession session, @RequestBody Map<String, String> param){
		Long commentId = Long.parseLong(param.get("commentId"));
		Long loginId = Long.parseLong(String.valueOf(session.getAttribute("loginId")));
		String appliedLikes= "";
		Boolean likeCheck = cultureCommunityService.isLikeForComment(loginId, commentId);
		
		if(!(likeCheck)) {
			appliedLikes = Long.toString(cultureCommunityService.likePointForComment(commentId));
			cultureCommunityService.addLikePointForComment(loginId, commentId);
		}else {
			appliedLikes = Long.toString(cultureCommunityService.unlikePointForComment(commentId));
			cultureCommunityService.removeLikePointForComment(loginId, commentId);			
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("likes", appliedLikes); //추천수(증가/감소한 값 받아오기
		map.put("likeCheck", String.valueOf(likeCheck)); 
		return map;
		
	}
	
	@ResponseBody
	@PostMapping("/cultureBoard/delComment")
	public Map<String, Object> commentDelete(HttpSession session, @RequestBody Map<String, String> param){
		List<CultureBoardComment> likeComments = new ArrayList<CultureBoardComment>();
		Long commentId = Long.parseLong(param.get("commentId"));
		Long boardId = Long.parseLong(param.get("boardId"));
		Long loginId = Long.parseLong(String.valueOf(session.getAttribute("loginId")));

		List<CultureBoardComment> cultureBoardComment = new ArrayList<CultureBoardComment>();
		likeComments = cultureCommunityService.findLikesCommentByMemberId(loginId);
	
		cultureBoardComment = cultureCommunityService.deleteComment(loginId, boardId, commentId);
	
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("likeComments", likeComments);
		map.put("cultureBoardComment", cultureBoardComment);
		map.put("loginId", loginId);
		map.put("boardId", boardId);
		return map;
		
	}
	
	
	
	
	
	
}
