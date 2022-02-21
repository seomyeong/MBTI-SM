package com.mycompany.myapp.community.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.mycompany.myapp.community.service.CommunityService;
import com.mycompany.myapp.domain.CommunityBoard;
import com.mycompany.myapp.domain.Member;
import com.mycompany.myapp.domain.PagingVO;

@Controller
public class MainCommunityController {
	@Autowired
	CommunityService communityService;

	/**
	 * 전체보기, nav로 접근 시 세션을 지우고 전체 내용을 조회한다.
	 */
	@GetMapping("community/mainCommunity_deleteSession")
	public String deleteSession(HttpSession session) {
		
		PagingVO pagingVO = new PagingVO();
		pagingVO.setPage(1);
		pagingVO.setRange(1);
		
		session.setMaxInactiveInterval(-1);
		session.setAttribute("type", "");
		session.setAttribute("q", "");
		session.setAttribute("pagingVO", pagingVO);		

		return "redirect:/community/mainCommunity?type=reportingDate&q=&page=1&range=1";
	}
	
	/**
	 * 인기순
	 */
	@GetMapping("community/mainCommunity_hot")
	public String hotCommunity() {
		return "redirect:/community/mainCommunity?type=likes&q=&page=1&range=1";
	}
	
	/**
	 * 조회순 
	 */
	@GetMapping("community/mainCommunity_top")
	public String TopCommunity() {
		return "redirect:/community/mainCommunity?type=views&q=&page=1&range=1";
	}

	/**
	 * mainCommunity GetMapping
	 */
	@GetMapping("community/mainCommunity")
	public ModelAndView getMainCommunity(@RequestParam String type, @RequestParam String q, @RequestParam String page,
			@RequestParam String range, HttpSession session, HttpServletRequest request) {

		List<CommunityBoard> cbList = new ArrayList<CommunityBoard>();
		List<CommunityBoard> cbList_hot = null;
		ModelAndView mav = new ModelAndView();
		PagingVO pagingVO = new PagingVO(); // 페이징 처리
		String errorMsg = null;

		// 세션의 유효시간을 무한대로 설정
		session.setMaxInactiveInterval(-1);

		if (type.equals("title")) { // 제목으로 검색했을 경우
			pagingVO.pageInfo(Integer.parseInt(page), Integer.parseInt(range),
					communityService.q_findContentsCnt(type, q));
			
			session.setAttribute("q", q);

			cbList = communityService.q_findBoardByStartList(type, q, pagingVO.getStartList(),
					pagingVO.getPageListSize());
			
		} else if (type.equals("memberId")) { // 작성자로 검색했을 경우
			Member m = null;
			m = communityService.findMemberByMemberNickName(q);
			if (m == null) {
				errorMsg = "검색 결과가 없습니다.";
			} else {
				pagingVO.pageInfo(Integer.parseInt(page), Integer.parseInt(range),
						communityService.q_memberId_findContentsCnt(m.getId()));

				request.setAttribute("errorMsg", errorMsg);
				session.setAttribute("q", q);

				cbList = communityService.q_memberId_findBoardByStartList(m.getId(), pagingVO.getStartList(),
						pagingVO.getPageListSize());
				
			}
		} else if (type.equals("mbti")) { // mbti 필터
			pagingVO.pageInfo(Integer.parseInt(page), Integer.parseInt(range),
					communityService.q_mbti_findContentsCnt(q));
			session.setAttribute("q", q);
			
			cbList = communityService.q_mbti_findBoardByMbtiInfo(q, pagingVO.getStartList(),
					pagingVO.getPageListSize());
			
		} else if (type.equals("reportingDate") || type.equals("likes") || type.equals("views")) { // 전체, 추천, 조회 순
			pagingVO.pageInfo(Integer.parseInt(page), Integer.parseInt(range), communityService.findAllContentsCnt());

			cbList = communityService.findBoardByStartList_sort(type, pagingVO.getStartList(), 
					pagingVO.getPageListSize());
		}
		
		// 정보 최신화
		Long loginId;
		
		if(!(session.getAttribute("loginId") == null || session.getAttribute("loginId").equals(""))) {
			loginId = Long.parseLong(String.valueOf(session.getAttribute("loginId")));
			Member memberInfo = communityService.findMemberByMemberId(loginId);
			
			session.setAttribute("memberInfo", memberInfo);
			session.setMaxInactiveInterval(-1);
		}
		
		session.setAttribute("pagingVO", pagingVO);
		session.setAttribute("type", type);
		
		// 인기글 뽑기
		cbList_hot = communityService.findRandomHotBoard();

		// 해당 조건에 맞는 CommunityBoard(cbList)를 ModelAndView를 통해 내보낸다.
		mav.addObject("cbList", cbList);
		mav.addObject("cbList_hot", cbList_hot);
		mav.setViewName("community/mainCommunity");

		return mav;
	}

}
