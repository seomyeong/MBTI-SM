package com.mycompany.myapp.mbtiPlay.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

import com.mycompany.myapp.domain.ContentsLog;
import com.mycompany.myapp.domain.MbtiPlayContents;
import com.mycompany.myapp.domain.MbtiPlayContentsAnswer;
import com.mycompany.myapp.domain.Member;
import com.mycompany.myapp.mbtiPlay.command.MbtiPlayContentsAnswerCommand;
import com.mycompany.myapp.mbtiPlay.service.MbtiPlayMakeContentsServiceImpl;

@Controller
public class MbtiPlayController {
	@Autowired
	MbtiPlayMakeContentsServiceImpl service;

	/*
	 * 맙티플레이 진입
	 */
	@GetMapping("/mbtiPlay/mbtiPlayZone")
	public String mbtiPlay() {
		return "mbtiPlay/mbtiPlayZone";
	}

	/*
	 * 문답 풀기 클릭
	 */
	@ResponseBody
	@RequestMapping(value="/mbtiPlay/mbtiPlayContents", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView mbtiPlayContents(MbtiPlayContentsAnswer mbtiPlayContentsAnswer,
			@SessionAttribute Long loginId) {
		ModelAndView mav = new ModelAndView();

		Member memberMbti = service.findMemberById(loginId);

		// 테이블의 pk값을 random으로 돌리기
		List<MbtiPlayContents> contentsPK = service.findContentsPk();
		long contentsTotalNum = contentsPK.size();

		Set<Long> set = new HashSet<Long>();

		while (set.size() <= contentsTotalNum) {
			long randomNum = (long) (Math.random() * contentsTotalNum) + 1;
			set.add(randomNum);
			if (!(service.isAnswersLog(loginId, randomNum))) {
				List<MbtiPlayContents> content = service.findQuestionByRandomNum(randomNum);
				mav.setViewName("mbtiPlay/mbtiPlayContents");
				mav.addObject("memberMbti", memberMbti.getMbti());
				mav.addObject("content", content);
				return mav;
			} else if (service.isAnswersLog(loginId, randomNum)
					&& service.isAnswerLogSize(loginId) == contentsTotalNum) {
				mav.setViewName("redirect:/mbtiPlay/mbtiPlayMakeGuide");
				return mav;
			}
		}
		return mav;
	}

	/*
	 * ajax 실행 시
	 * 
	 */
	@ResponseBody
	@PostMapping("/mbtiPlay/addAnswer")
	public Map<String, Object> addAnswer(@RequestBody Map<String, String> param,
			MbtiPlayContentsAnswer mbtiPlayContentsAnswer, @SessionAttribute Long loginId) {

		// insert시킬 path값 가져오기
		String memberMbti = param.get("memberMbti");
		Long questionNum = Long.parseLong(param.get("questionNum"));
		String choosenNum = param.get("choosenNum");
		String isSubjective = param.get("isSubjective");
		String subjectiveContent = param.get("subjectiveContent");
		int choosenNumCount = Integer.parseInt(param.get("choosenNumCount"));

		// AnswersLog테이블에 memberId, 푼 문제번호 찍기
		if (!(service.isAnswersLog(loginId, questionNum))) {
			service.addAnswersLog(loginId, questionNum);
		}
		// MbtiPlayContentsAnswer테이블에 유저가 선택한 객관식 데이터 insert
		if (!(service.isMbtiTypeAndQuestion(memberMbti, questionNum))) {
			if (isSubjective.equals("false")) {
				//객관식 first insert
				service.addAnswer(memberMbti, questionNum, "1", "false", "null", 0);
				service.addAnswer(memberMbti, questionNum, "2", "false", "null", 0);
				service.addAnswer(memberMbti, questionNum, "3", "false", "null", 0);

				if (service.isMbtiTypeAndQuestion(memberMbti, questionNum)) {
					//선택받은 객관식 cnt++
					service.updateObjectiveAnswer(subjectiveContent, memberMbti, questionNum, choosenNum, isSubjective);
				}
			} else if (isSubjective.equals("true")) {
				// 주관식 first insert
				service.addAnswer(memberMbti, questionNum, "1", "false", "null", 0);
				service.addAnswer(memberMbti, questionNum, "2", "false", "null", 0);
				service.addAnswer(memberMbti, questionNum, "3", "false", "null", 0);
				service.addAnswer(memberMbti, questionNum, choosenNum, isSubjective, subjectiveContent,
						choosenNumCount);
			}
		} else {
			if (isSubjective.equals("false")) {
				// 객관식 update
				service.updateObjectiveAnswer(subjectiveContent, memberMbti, questionNum, choosenNum, isSubjective);
			} else if (isSubjective.equals("true")) {
				// 주관식 second insert
				service.addAnswer(memberMbti, questionNum, choosenNum, isSubjective, subjectiveContent,
						choosenNumCount);
			}
		}

		// view에 출력할 값 얻어오기
		Map<String, Object> map = new HashMap<String, Object>();

		// 데이터 출력
		if (service.isQuestionNum(questionNum)) {
			MbtiPlayContentsAnswer firstObj = service.findObjectiveAnswer(memberMbti, questionNum, "1");
			MbtiPlayContentsAnswer secondObj = service.findObjectiveAnswer(memberMbti, questionNum, "2");
			MbtiPlayContentsAnswer thirdObj = service.findObjectiveAnswer(memberMbti, questionNum, "3");

			map.put("firstObj", firstObj);
			map.put("secondObj", secondObj);
			map.put("thirdObj", thirdObj);
		}

		// 주관식 데이터 출력
		if (service.isSubjectiveAnswer(questionNum)) {
			List<MbtiPlayContentsAnswerCommand> subList = service.findAllSubjectiveAnswers(questionNum);
			map.put("subList", subList);
		} else {
			map.put("nullSubMsg", "아직 작성된 기타 답변이 없어요!");
		}
		return map;
	}

	/*
	 * 문답 만들기
	 */
	//@RequestMapping(value="/mbtiPlay/mbtiPlayMakeContents", method = {RequestMethod.POST})
	@PostMapping("/mbtiPlay/mbtiPlayMakeContents")
	public ModelAndView makeContents(@ModelAttribute MbtiPlayContents mbtiPlayContents, @SessionAttribute Long loginId) {
		ModelAndView mav = new ModelAndView();
		
		Date today = new Date();
		SimpleDateFormat yearDf = new SimpleDateFormat("yyyy");
		SimpleDateFormat monthDf = new SimpleDateFormat("MM");
		SimpleDateFormat dayDf = new SimpleDateFormat("dd");

		String nowYear = yearDf.format(today);
		String nowMonth = monthDf.format(today);
		String nowDay = dayDf.format(today);
		
		if (service.isContentsLogDate(loginId, nowYear, nowMonth, nowDay)) {
			ContentsLog cLog = service.findContentsLog(loginId, nowYear, nowMonth, nowDay);
			mav.addObject("contentsCount", cLog.getContentsCount());

		} else {
			mav.addObject("zeroCount", 0);
		}

		mav.setViewName("mbtiPlay/mbtiPlayMakeContents");
		return mav;
	}

	/*
	 * 문답 만들기 성공
	 */
	@RequestMapping(value="/mbtiPlay/successAddMbtiPlayMakeContents", method = {RequestMethod.POST})
	public ModelAndView successAddMbtiPlayMakeContents(@ModelAttribute MbtiPlayContents mbtiPlayContents,
			@SessionAttribute Long loginId, Member member) {
		ModelAndView mav = new ModelAndView();

		Date today = new Date();
		SimpleDateFormat yearDf = new SimpleDateFormat("yyyy");
		SimpleDateFormat monthDf = new SimpleDateFormat("MM");
		SimpleDateFormat dayDf = new SimpleDateFormat("dd");

		String nowYear = yearDf.format(today);
		String nowMonth = monthDf.format(today);
		String nowDay = dayDf.format(today);

		//first insert
		if (!(service.isContentsLogDate(loginId, nowYear, nowMonth, nowDay))) {
			service.addContents(loginId, mbtiPlayContents);
			service.addContentsLog(loginId);
			service.calcQuizPoint(loginId);

			return mav;

		} else if (service.isContentsLogDate(loginId, nowYear, nowMonth, nowDay)
				&& !(service.isContentsLogLimitTime(loginId, nowYear, nowMonth, nowDay))) {

			service.addContents(loginId, mbtiPlayContents);
			service.updateContentsLog(loginId, nowYear, nowMonth, nowDay);
			service.calcQuizPoint(loginId);

		//second insert (limitMakeContents)
		} else {
			mav.setViewName("redirect:/mbtiPlay/limitMakeContents");
			return mav;
		}

		mav.setViewName("mbtiPlay/successAddMbtiPlayMakeContents");
		return mav;
	}

	/*
	 * 맙티플레이를 다 풀었을 시
	 */
	@GetMapping("/mbtiPlay/mbtiPlayMakeGuide")
	public String mbtiPlayMakeGuide() {
		return "mbtiPlay/mbtiPlayMakeGuide";
	}

	/*
	 * 맙티플레이 생성 허용횟수(3회) 초과 시
	 */
	@GetMapping("/mbtiPlay/limitMakeContents")
	public String limitMakeContents() {
		return "mbtiPlay/limitMakeContents";
	}
}
