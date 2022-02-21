package com.mycompany.myapp.mbtiMatch.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mycompany.myapp.domain.MbtiComments;
import com.mycompany.myapp.mbtiMatch.service.MbtiMatchService;

@Controller("controller/resultMbtiMatchController")
public class ResultMbtiMatchController {

	@Autowired
	MbtiMatchService mbtiMatchService;

	@GetMapping("/mbtiMatch/resultMbtiMatch") // controller탐색
	public ModelAndView ResultMbtiMatchForm(HttpSession session, @RequestParam String type01,
			@RequestParam String type02, MbtiComments mbtiComments) {

		Long loginId;

		loginId = (Long) session.getAttribute("loginId");

		ModelAndView mav = new ModelAndView();

		int result = mbtiMatchService.findResultByMbtiTypes(type01, type02);

		String matchResult;
		if (result == 1) {
			matchResult = "맞지 않는 관계";
		} else if (result == 2) {
			matchResult = "갈등 관계";
		} else if (result == 3) {
			matchResult = "잠재적 관계";
		} else if (result == 4) {
			matchResult = "끈끈한 관계";
		} else {
			matchResult = "이상적인 관계";
		}

		List<MbtiComments> mcInfo = mbtiMatchService.findMcInfoByType(type01, type02);
		
		mav.addObject("mcInfo", mcInfo);
		mav.addObject("loginId", loginId);
		mav.addObject("type01", type01);
		mav.addObject("type02", type02);
		mav.addObject("matchResult", matchResult);
		mav.addObject("result", result);
		mav.setViewName("/mbtiMatch/resultMbtiMatch"); // view탐색

		return mav;
	}

	@PostMapping("/mbtiMatch/resultMbtiMatch")
	public ModelAndView SuccessMbtiMatch(HttpSession session, @RequestParam String comment, MbtiComments mbtiComments) {

		ModelAndView mav = new ModelAndView();

		return mav;
	}

	@ResponseBody
	@PostMapping("/mbtiMatch/addComment")
	public Map<String, Object> ajaxComment(HttpSession session, @RequestBody Map<String, String> json) {

		Long loginId;
		List<MbtiComments> mcInfo = null;
		Map<String, Object> map = new HashMap<String, Object>();

		// 로그인한 회원일 경우,
		if (session.getAttribute("loginId") != null) {

			// json에서 loginId값과 comment값 받아오기.
			loginId = Long.parseLong(json.get("loginId"));
			String type01 = json.get("type01");
			String type02 = json.get("type02");
			String comment = json.get("comment");

			// 입력한 코멘트 테이블에 저장하기.
			mbtiMatchService.addComment(loginId, type01, type02, comment);

			mcInfo = mbtiMatchService.findMcInfoByType(type01, type02);

			map.put("mcInfo", mcInfo);

		}

		return map;
	}
}
