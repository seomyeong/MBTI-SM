package com.mycompany.myapp.member.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
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

import com.mycompany.myapp.domain.Member;
import com.mycompany.myapp.member.command.MemberCommand;
import com.mycompany.myapp.member.service.MemberService;

@Controller
public class MemberController {
	@Autowired
	MemberService memberService;

	/**
	 * 회원가입창
	 */
	@GetMapping("/member/addMember")
	public String addMember(@ModelAttribute MemberCommand memberCommand) {
		return "member/addMember";
	}

	/**
	 * 회원가입 성공
	 */
	@GetMapping("/member/successAddMember")
	public String successAddMemberGet() {
		return "redirect:/";
	}

	@PostMapping("/member/successAddMember")
	public ModelAndView successAddMember(MemberCommand memberCommand) {
		ModelAndView mav = new ModelAndView();
		String profileImg = "/myapp/resources/img/avatar/MBTI_" + memberCommand.getMbti() + ".png";
		String email = memberCommand.getEmail1() + memberCommand.getEmail2();
		Member member = new Member(email, memberCommand.getPw(), memberCommand.getName(), memberCommand.getNickName(),
				memberCommand.getBirth(), memberCommand.getMbti(), memberCommand.getGender(), memberCommand.getPhone(),
				profileImg);
		memberService.addMember(member);
		mav.setViewName("redirect:/");
		return mav;
	}

	/**
	 * 회원정보 수정창
	 */
	@GetMapping("/member/updateMember")
	public String updateMember(@ModelAttribute MemberCommand memberCommand) {
		return "member/updateMember";

	}

	/**
	 * updateMember 회원정보 수정 성공
	 */
	@GetMapping("/member/successUpdateMember")
	public String successUpdateMemberGet() {
		return "redirect:/";
	}

	@PostMapping("/member/successUpdateMember")
	public ModelAndView successUpdateMember(MemberCommand memberCommand, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		String profileImg = "/myapp/resources/img/avatar/MBTI_" + memberCommand.getMbti() + ".png";
		Member member = new Member(memberCommand.getEmail1(), memberCommand.getPw(), memberCommand.getName(),
				memberCommand.getNickName(), memberCommand.getBirth(), memberCommand.getMbti(),
				memberCommand.getGender(), memberCommand.getPhone(), profileImg);
		long loginId = (long) session.getAttribute("loginId");
		session.removeAttribute("memberInfo");
		memberService.updateMember(member, loginId);
		// 수정한 정보 다시 보여주기
		Member memberInfo = memberService.memberInfo(member);
		String[] memberInfoBirth = memberInfo.getBirth().split(",");
		session.setAttribute("memberInfoBirth", memberInfoBirth);
		session.setAttribute("memberInfo", memberInfo);
		mav.setViewName("redirect:/");
		return mav;
	}

	/**
	 * 로그인
	 */
	@GetMapping("/member/login")
	public String loginGet() {
		return "member/login";
	}

	/**
	 * 로그인 실행
	 */
	@PostMapping("/member/login")
	public ModelAndView login(Member member, HttpSession session) {
		ModelAndView mav = new ModelAndView();

		Date today = new Date();
		SimpleDateFormat yearDf = new SimpleDateFormat("yyyy");
		SimpleDateFormat monthDf = new SimpleDateFormat("MM");
		SimpleDateFormat dayDf = new SimpleDateFormat("dd");

		String nowYear = yearDf.format(today);
		String nowMonth = monthDf.format(today);
		String nowDay = dayDf.format(today);

		if (memberService.login(member)) {
			Member memberInfo = memberService.memberInfo(member);

			// 첫 로그인인지 아닌지 구분
			if (!(memberService.isLoginLogDate(memberInfo.getId(), nowYear, nowMonth, nowDay))) {

				String[] memberInfoBirth = memberInfo.getBirth().split(",");
				session.setAttribute("memberInfoBirth", memberInfoBirth);
				session.setAttribute("memberInfo", memberInfo);

				// 세션에 id 값 할당
				session.setAttribute("loginId", memberInfo.getId());
				session.setMaxInactiveInterval(-1);

				long loginId = (long) session.getAttribute("loginId");
				memberService.addLoginLog(loginId);
				memberService.calcLoginPoint(loginId);
				
				mav.setViewName("redirect:/");
				return mav;

			} else {

				String[] memberInfoBirth = memberInfo.getBirth().split(",");
				session.setAttribute("memberInfoBirth", memberInfoBirth);
				session.setAttribute("memberInfo", memberInfo);

				// 세션에 id 값 할당
				session.setAttribute("loginId", memberInfo.getId());
				session.setMaxInactiveInterval(-1);

				mav.setViewName("redirect:/");
				return mav;
			}

		} else {

			mav.addObject("errorMsg", "회원정보가 일치하지 않습니다.");
			mav.setViewName("/member/login");
			return mav;
		}

	}

	/**
	 * 로그아웃
	 */
	@GetMapping("/member/logout")
	public String logoutGET(HttpSession session) throws Exception {

		session.removeAttribute("loginId");
		session.removeAttribute("memberInfo");

		return "redirect:/";
	}

	/**
	 * 이메일 중복체크
	 */
	@ResponseBody
	@PostMapping("/member/emailCheck")
	public Map<String, String> isEmailCheck(@RequestBody Map<String, String> param) {
		String msg = "";
		String email = param.get("email");
		String email1 = param.get("email1");

		if (email1.equals(null) || email1 == "") {
			msg = "";
		} else if (memberService.isEmailCheck(email)) {
			if (email1.length() > 4) {
				msg = "사용가능한 이메일입니다!";
			} else {
				msg = "5~20자로 설정해주세요.";
			}

		} else {
			msg = "중복되는 이메일이 존재합니다.";
		}

		Map<String, String> map = new HashMap<String, String>();
		map.put("msg", msg);
		map.put("email1", email1);

		return map;
	}

	/**
	 * 닉네임 중복체크
	 */
	@ResponseBody
	@PostMapping("/member/nickNameCheck")
	public Map<String, String> isNickCheck(@RequestBody Map<String, String> param) {

		String msg = "";
		String nickName = param.get("nickName");

		if (memberService.isNickNameCheck(nickName)) {
			if (nickName.length() > 1) {
				msg = "사용가능한 닉네임입니다.";
			} else {
				msg = "2자리 이상 입력해주세요.";
			}
		} else {
			msg = "중복되는 닉네임이 존재합니다.";
		}

		Map<String, String> map = new HashMap<String, String>();
		map.put("msg", msg);
		map.put("nickName", nickName);

		return map;
	}

}