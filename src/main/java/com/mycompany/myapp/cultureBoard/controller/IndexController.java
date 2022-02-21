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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mycompany.myapp.cultureBoard.command.CultureBoardCommand;
import com.mycompany.myapp.cultureBoard.service.CultureCommunityService;
import com.mycompany.myapp.domain.CultureBoard;
import com.mycompany.myapp.domain.CultureBoardComment;
import com.mycompany.myapp.domain.Member;



@Controller
public class IndexController {
	@Autowired 
	CultureCommunityService cultureCommunityService;
	
	//첫시작 컨텐츠 
	@GetMapping(value= {"/","index","main"})
	public ModelAndView cultureConWrite(HttpSession session, @ModelAttribute CultureBoardCommand cultureBoardCommand) {
		ModelAndView mav = new ModelAndView();
		
		List<CultureBoard> contents = new ArrayList<CultureBoard>();
		List<CultureBoard> likeContents = new ArrayList<CultureBoard>();
		List<CultureBoardComment> cultureBoardComment = new ArrayList<CultureBoardComment>(); 
		List<CultureBoardComment> likeComments = new ArrayList<CultureBoardComment>();
		
		Long loginId = null;
		
		//첫인덱스화면 '뮤직'카테고리 게시물 전체 리스트 
		contents = cultureCommunityService.findFirstContents();
		cultureBoardComment = cultureCommunityService.findAllCultureBoardComment();
		
		Member loginMemberInfo = null;
		if (!(session == null || session.getAttribute("loginId") == null || session.getAttribute("loginId").equals(""))) {
			loginId = Long.parseLong(String.valueOf(session.getAttribute("loginId")));
			loginMemberInfo = cultureCommunityService.findMemberByMemberId(loginId);
			
			likeContents = cultureCommunityService.findLikesContentByMemberId(loginId);
			likeComments = cultureCommunityService.findLikesCommentByMemberId(loginId);
			
			mav.addObject("likeContents", likeContents);
			mav.addObject("likeComments", likeComments);
			
			mav.addObject("loginId", loginId);
			mav.addObject("contents", contents);
			mav.addObject("cultureBoardComment", cultureBoardComment);
			mav.addObject("memberInfo", loginMemberInfo);
	
		}else {
			loginId = null;
			loginMemberInfo = null;
			
			mav.addObject("loginId", loginId);
			mav.addObject("contents", contents);
			mav.addObject("cultureBoardComment", cultureBoardComment);
			mav.addObject("memberInfo", loginMemberInfo);
		}
 
		// 비로그인시 contents 리스트만 보내준다. 로그인시, member객체도 같이 보내준다.
		//mav.addObject("memberInfo", loginMemberInfo)
		//mav.addObject("contents", contents);
		//mav.addObject("cultureBoardComment", cultureBoardComment);
		
		//index.jsp로의 이동!
		//
		mav.setViewName("index");
		return mav;
	}
	
	
	
	//  영화/음악/여행 버튼 클릭시 컨텐츠
	@ResponseBody
	@PostMapping("/cultureBoard/index")
	public Map<String, Object> cultureBtn(HttpSession session, @RequestBody Map<String, String> param) {
		List<CultureBoard> contents = new ArrayList<CultureBoard>();
		List<CultureBoard> likeContents = new ArrayList<CultureBoard>();
		List<CultureBoardComment> cultureBoardComment = new ArrayList<CultureBoardComment>(); 
		List<CultureBoardComment> likeComments = new ArrayList<CultureBoardComment>();
		Member member = null;
		Long loginId = null;
		
		String strValue = "";
		
		Iterator<String> keys = param.keySet().iterator();
		while(keys.hasNext()) {
			String strKey = keys.next();
			strValue = param.get(strKey);
		}
	
		contents = cultureCommunityService.findCultureContents(strValue);
		cultureBoardComment = cultureCommunityService.findAllCultureBoardComment();
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		
		if (!(session == null || session.getAttribute("loginId") == null || session.getAttribute("loginId").equals(""))) {
			loginId = Long.parseLong(String.valueOf(session.getAttribute("loginId")));
			member = cultureCommunityService.findMemberByMemberId(loginId);
			
			likeContents = cultureCommunityService.findLikesContentByMemberId(loginId);
			likeComments = cultureCommunityService.findLikesCommentByMemberId(loginId);
			
			map.put("likeContents", likeContents);
			map.put("likeComments", likeComments);
			
			map.put("loginId", loginId);
			map.put("contents", contents);
			map.put("cultureBoardComment", cultureBoardComment);
			map.put("memberInfo", member);
		}else {
			loginId = null;
			member = null;
			
			map.put("loginId", loginId);
			map.put("contents", contents);
			map.put("cultureBoardComment", cultureBoardComment);
			map.put("memberInfo", member);
			
		}
		
		return map;	
	}	
	
	
	//MBTI 별 버튼 클릭시 보여주기
	@ResponseBody
	@PostMapping("/cultureBoard/index02")
	public Map<String, Object> mbtiBtn(HttpSession session, @RequestBody Map<String, String> param) {
		List<CultureBoard> contents = new ArrayList<CultureBoard>();
		List<CultureBoard> likeContents = new ArrayList<CultureBoard>();
		List<CultureBoardComment> cultureBoardComment = new ArrayList<CultureBoardComment>(); 
		List<CultureBoardComment> likeComments = new ArrayList<CultureBoardComment>();
		
		String mbtiValue = param.get("mbtiValue");
		String contentType = param.get("contentType");
		
		Member member = null;
		Long loginId = null;
		
		
		contents = cultureCommunityService.findMbtiContents(mbtiValue, contentType);
		cultureBoardComment = cultureCommunityService.findAllCultureBoardComment();
	
		Map<String, Object> map = new HashMap<String, Object>();
		
		
		if (!(session == null || session.getAttribute("loginId") == null || session.getAttribute("loginId").equals(""))) {
			loginId = Long.parseLong(String.valueOf(session.getAttribute("loginId")));
			member = cultureCommunityService.findMemberByMemberId(loginId);
			likeContents = cultureCommunityService.findLikesContentByMemberId(loginId);
			likeComments = cultureCommunityService.findLikesCommentByMemberId(loginId);
			
			map.put("likeContents", likeContents);
			map.put("likeComments", likeComments);
			
			map.put("loginId", loginId);
			map.put("contents", contents);
			map.put("cultureBoardComment", cultureBoardComment);
			map.put("memberInfo", member);
		}else {
			loginId = null;
			member = null;
			
			map.put("loginId", loginId);
			map.put("contents", contents);
			map.put("cultureBoardComment", cultureBoardComment);
			map.put("memberInfo", member);
			
		}
		
		return map;	
	}		
	
	
	
	// 추천순 버튼 클릭시 컨텐츠 출력
	@ResponseBody
	@PostMapping("/cultureBoard/orderLikes")
	public Map<String, Object> orderLikesBtn(HttpSession session, @RequestBody Map<String, String> param){
		List<CultureBoard> contents = new ArrayList<CultureBoard>();
		List<CultureBoard> likeContents = new ArrayList<CultureBoard>();
		List<CultureBoardComment> cultureBoardComment = new ArrayList<CultureBoardComment>(); 
		List<CultureBoardComment> likeComments = new ArrayList<CultureBoardComment>();
		
		String contentType = param.get("type");
		String mbtiValue = param.get("mbtiValue");
		Member member = null;
		Long loginId = null;
		
		if(mbtiValue.equals("none")) {
			contents = cultureCommunityService.findLikesOrderByType(contentType);
		}else {
			contents = cultureCommunityService.findLikesOrderByTypeMbti(contentType, mbtiValue);
		}
		cultureBoardComment = cultureCommunityService.findAllCultureBoardComment();
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		
		if (!(session == null || session.getAttribute("loginId") == null || session.getAttribute("loginId").equals(""))) {
			loginId = Long.parseLong(String.valueOf(session.getAttribute("loginId")));
			member = cultureCommunityService.findMemberByMemberId(loginId);
			likeContents = cultureCommunityService.findLikesContentByMemberId(loginId);
			likeComments = cultureCommunityService.findLikesCommentByMemberId(loginId);
			
			map.put("likeContents", likeContents);
			map.put("likeComments", likeComments);
			map.put("loginId", loginId);
			map.put("contents", contents);
			map.put("cultureBoardComment", cultureBoardComment);
			map.put("memberInfo", member);
		}else {
			loginId = null;
			member = null;
			
			map.put("loginId", loginId);
			map.put("contents", contents);
			map.put("cultureBoardComment", cultureBoardComment);
			map.put("memberInfo", member);
			
		}
		
		return map;	
		
	}
	
	
	//댓글순 버튼 클릭시 컨텐츠 출력
	@ResponseBody
	@PostMapping("/cultureBoard/orderBestComment")
	public Map<String, Object> orderCommentBtn(HttpSession session, @RequestBody Map<String, String> param){
		String contentType = param.get("type");
		String mbtiValue = param.get("mbtiValue");
		Member member = null;
		Long loginId = null;
		
		List<CultureBoard> contents = new ArrayList<CultureBoard>();
		List<CultureBoard> likeContents = new ArrayList<CultureBoard>();
		List<CultureBoardComment> cultureBoardComment = new ArrayList<CultureBoardComment>(); 
		List<CultureBoardComment> likeComments = new ArrayList<CultureBoardComment>();
		
		
		if(mbtiValue.equals("none")) {
			contents = cultureCommunityService.findCommentsOrderByType(contentType);
		}else {
			contents = cultureCommunityService.findCommentsOrderByTypeMbti(contentType, mbtiValue);
		}
		cultureBoardComment = cultureCommunityService.findAllCultureBoardComment();
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		
		if (!(session == null || session.getAttribute("loginId") == null || session.getAttribute("loginId").equals(""))) {
			loginId = Long.parseLong(String.valueOf(session.getAttribute("loginId")));
			member = cultureCommunityService.findMemberByMemberId(loginId);
			
			likeContents = cultureCommunityService.findLikesContentByMemberId(loginId);
			likeComments = cultureCommunityService.findLikesCommentByMemberId(loginId);
			
			map.put("likeContents", likeContents);
			map.put("likeComments", likeComments);
			map.put("loginId", loginId);
			map.put("contents", contents);
			map.put("cultureBoardComment", cultureBoardComment);
			map.put("memberInfo", member);
		}else {
			loginId = null;
			member = null;
			
			map.put("loginId", loginId);
			map.put("contents", contents);
			map.put("cultureBoardComment", cultureBoardComment);
			map.put("memberInfo", member);
			
		}
		
		return map;
		
	}
	
	
	
	
	//좋아요 클릭 이벤트
	@ResponseBody
	@PostMapping("/cultureBoard/likes")
	public Map<String, String> ajaxWriteLikes(@RequestBody Map<String, String> param) {
		String loginId = param.get("loginId");
		String boardId = param.get("boardId");
		String appliedLikes = "";
		List<CultureBoard> cb = null; 
		//System.out.println(boardId);
		// 추천이 눌려있으면 true 안눌려있으면 false
		Boolean likeCheck = cultureCommunityService.isLike(Long.parseLong(loginId), Long.parseLong(boardId));
		
		// 해당 게시물에 추천을 눌렀었는지 확인 //  !(false) = true
		if(!(likeCheck)) {
			//추천 증가 및 적용 후 적용된 추천수 받아오기
			appliedLikes = Long.toString(cultureCommunityService.likePoint(Long.parseLong(boardId)));
			//LikeLog에 기록추가
			cultureCommunityService.addLikePoint(Long.parseLong(loginId), Long.parseLong(boardId));
		}else {
			//추천 감소 및 적용 후 적용된 추천수 받아오기
			appliedLikes = Long.toString(cultureCommunityService.unlikePoint(Long.parseLong(boardId)));
			//추천 누른 기록DB에 기록추가
			cultureCommunityService.removeLikePoint(Long.parseLong(loginId), Long.parseLong(boardId));
		}
		
		
		Map<String,String> map = new HashMap<String,String>();
		map.put("likes", appliedLikes); //추천수(증가/감소)
		map.put("likeCheck", String.valueOf(likeCheck)); 
		
		return map;
	}
	
	
	
	
	
}
