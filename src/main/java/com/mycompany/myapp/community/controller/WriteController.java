package com.mycompany.myapp.community.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

import com.mycompany.myapp.community.service.CommunityService;

@Controller
public class WriteController {
	@Autowired
	CommunityService communityService;
	
	/**
	 * write GetMapping
	 */
	@GetMapping("community/write")
	public ModelAndView getWrite(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		// 로그인한 상태가 아닌 상태에서 페이지 진입시 로그인페이지로 보냄
		if (session == null || session.getAttribute("loginId") == null || session.getAttribute("loginId").equals("")) {
			mav.setViewName("redirect:/member/login");
			return mav;
		}
		
		mav.setViewName("community/write");

		return mav;
	}
	
	/**
	 * 댓글 작성 성공시
	 */
	@PostMapping("community/successWrite")
	public ModelAndView successWrite(@SessionAttribute("loginId") String loginId, @RequestParam String title, @RequestParam String contents) {
		ModelAndView mav = new ModelAndView();
		
		// 작성한 게시글 테이블에 추가
		communityService.addCommunityBoard(Long.parseLong(loginId), title, contents);
				
		mav.setViewName("redirect:mainCommunity?type=reportingDate&q=&page=1&range=1");

		return mav;
	}
	
	/**
	 * 댓글 작성 성공시
	 */
	@PostMapping("community/successEdit")
	public ModelAndView successEdit(@RequestParam String boardId, @RequestParam String title, @RequestParam String contents) {
		ModelAndView mav = new ModelAndView();
		
		// 작성한 게시글 테이블에 수정
		communityService.UpdateCommunityBoard(Long.parseLong(boardId), title, contents);
				
		mav.setViewName("redirect:mainCommunity?type=reportingDate&q=&page=1&range=1");

		return mav;
	}
}
