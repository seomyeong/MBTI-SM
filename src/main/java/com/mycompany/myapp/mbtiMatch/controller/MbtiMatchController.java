package com.mycompany.myapp.mbtiMatch.controller;


import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.mycompany.myapp.domain.Member;
import com.mycompany.myapp.mbtiMatch.service.MbtiMatchService;

@Controller("controller/mbtiMatchController")
public class MbtiMatchController {
	
	@Autowired
	MbtiMatchService mbtiMatchService;
	
	// mbtiMatch/mbti_match.jsp 페이지에 들어갈 때 발생.
	// 멤버일 경우, 멤버 mbti 값을 가져와서 디폴트값으로 설정.
	// 세션으로 멤버의 id만 가져오므로 id를 통해서 mbti를 가져오는 서비스 필요.
	// 멤버가 아니면 디폴트 값 없음.
	@GetMapping("/mbtiMatch/mbtiMatch")
	public ModelAndView mbtiMatchForm(HttpSession session) {
		
		String type01;
		Long loginId;
		
		ModelAndView mav = new ModelAndView();
		
		// 로그인을 한 회원일 경우,
		if(session.getAttribute("loginId") != null) { //오브젝트로 받음
			
			// 1. 가져온 세션을("loginId") long으로 변환시키기.
			loginId = (Long)session.getAttribute("loginId"); // 캐스팅.
			
			// 2. loginId로 mbti값 찾아오기.
			Member loginInfo = mbtiMatchService.findMemberInfoById(loginId);
			
			// 3. 받아온 mbti값 type01변수에 담기.
			type01 = loginInfo.getMbti();
			
			// 4. 변수하나를 생성해서 type01을 넣고 그 값을 뷰로 넘겨주기.
			mav.addObject("loginIdMbti", type01);
		
			// 5. 지정된 경로의 페이지 보여주기.
			mav.setViewName("/mbtiMatch/mbtiMatch");
		}
		
		return mav;
			
	}

	// submit버튼을 누르면 type을 제대로 선택했는지 확인하고 success_페이지로 넘어감.
	@PostMapping("/mbtiMatch/mbtiMatch")
	public ModelAndView mbtiMatch() {
		
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("/mbtiMatch/resultMbtiMatch");
		return mav;
		
	}
}
