package com.mycompany.myapp.community.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

import com.mycompany.myapp.community.service.CommunityService;
import com.mycompany.myapp.domain.CommunityBoard;
import com.mycompany.myapp.domain.CommunityComments;
import com.mycompany.myapp.domain.CommunityCommentsPlus;
import com.mycompany.myapp.domain.Member;
import com.mycompany.myapp.domain.PagingVO;

@Controller
public class BoardController {
	@Autowired
	CommunityService communityService;

	/**
	 * board GetMapping
	 */
	@GetMapping("/community/board")
	public ModelAndView write(@RequestParam String boardId, HttpSession session) {
		ModelAndView mav = new ModelAndView();

		// 조회수 올리기
		communityService.viewPoint(Long.parseLong(boardId));

		CommunityBoard cb = communityService.findBoardByBoardId(Long.parseLong(boardId));
		List<CommunityComments> cc = communityService.findCommentsByBoardId(Long.parseLong(boardId));
		List<CommunityCommentsPlus> ccp = communityService.findCommentsPlusByBoardId(Long.parseLong(boardId));
		
		// 로그인 아이디의 정보를 들고온다.
		Long loginId;
		Member loginMemberInfo = null;

		if (!(session == null || session.getAttribute("loginId") == null
				|| session.getAttribute("loginId").equals(""))) {
			loginId = Long.parseLong(String.valueOf(session.getAttribute("loginId")));
			loginMemberInfo = communityService.findMemberByMemberId(loginId);

			// 정보 최신화
			Member memberInfo = communityService.findMemberByMemberId(loginId);
			
			session.setAttribute("memberInfo", memberInfo);
			session.setMaxInactiveInterval(-1);
			
			// 알림
			LocalDateTime now = LocalDateTime.now();
			String formatedNow = now.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
			System.out.println("[" + formatedNow + "] '" + memberInfo.getNickName() + "(" + memberInfo.getName() + ")'님이 '" + cb.getMember().getNickName() + "(" + cb.getMember().getName() + ")'님의 \"" + cb.getTitle() + "\" 게시물을 조회합니다.");
			
		}
		
		// 댓글 수 가져오기
		cb.setCommentsCount(cc.size() + ccp.size());

		mav.addObject("loginMemberInfo", loginMemberInfo);
		mav.addObject("board", cb);
		mav.addObject("comments", cc);
		mav.addObject("commentsPlus", ccp);
		mav.setViewName("/community/board");
		
		return mav;
	}
	
	/**
	 * 댓글 작성시
	 */
	@ResponseBody
	@PostMapping("community/addComment")
	public Map<String, Object> ajaxAddComment(@RequestBody Map<String, String> param) {
		
		Long loginId = Long.parseLong(param.get("loginId"));
		Long boardId = Long.parseLong(param.get("boardId"));
		String comment = param.get("comment");
		
		
		// 댓글 테이블에 할당
		long commentId = communityService.addComment(loginId, boardId, comment);

		Member loginMember = communityService.findMemberByMemberId(loginId);
		
		CommunityBoard cb = communityService.findBoardByBoardId(boardId);
		List<CommunityComments> cc = communityService.findCommentsByBoardId(boardId);
		List<CommunityCommentsPlus> ccp = communityService.findCommentsPlusByBoardId(boardId);
		
		
		// 알림
		LocalDateTime now = LocalDateTime.now();
		String formatedNow = now.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
		System.out.println("[" + formatedNow + "] '" + loginMember.getNickName() + "(" + loginMember.getName() + ")'님이 '" + cb.getMember().getNickName() + "(" + cb.getMember().getName() + ")' 님의 \"" + cb.getTitle() + "\" 게시물에 \"" + comment + "\" 댓글을 작성했습니다.");
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("mbti", loginMember.getMbti());
		map.put("level", Integer.toString(loginMember.getLevel()));
		map.put("nickName", loginMember.getNickName());
		map.put("comment", comment);
		map.put("commentId", Long.toString(commentId));
		map.put("cb", cb);
		map.put("cc", cc);
		map.put("ccp", ccp);
		
		return map;
	}

	/**
	 * 게시물 삭제시
	 */
	@GetMapping("/community/deleteBoard")
	public String deleteBoard(@RequestParam String boardId, HttpSession session) {

		String type = session.getAttribute("type").equals("") ? "" : (String) session.getAttribute("type");
		String q = session.getAttribute("q").equals("") ? "" : (String) session.getAttribute("q");
		PagingVO pagingVO = (PagingVO) session.getAttribute("pagingVO");
		String page = pagingVO.getPage() == 1 ? "1" : Integer.toString(pagingVO.getPage());
		String range = pagingVO.getRange() == 1 ? "1" : Integer.toString(pagingVO.getRange());
		
		CommunityBoard cb = communityService.findBoardByBoardId(Long.parseLong(boardId));
		communityService.deleteBoard(cb.getMember().getId(), boardId);
		
		
		
		return "redirect:/community/mainCommunity?type=" + type + "&q=" + q + "&page=" + page + "&range=" + range;
	}
	
	/**
	 * 게시물 수정시
	 */
	@GetMapping("/community/editBoard")
	public ModelAndView editBoard(@RequestParam String boardId, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		CommunityBoard cb = communityService.findBoardByBoardId(Long.parseLong(boardId));
		
		// 로그인한 상태가 아닌 상태에서 페이지 진입시 로그인페이지로 보냄
		if (session == null || session.getAttribute("loginId") == null || session.getAttribute("loginId").equals("")) {
			mav.setViewName("redirect:/member/login");
			return mav;
		}
		
		mav.addObject("cb", cb);
		mav.setViewName("community/editBoard");
		return mav;
	}
	
	/**
	 * 댓글 삭제시
	 */
	@ResponseBody
	@PostMapping("/community/deleteComment")
	public Map<String, Object> deleteComment(@RequestBody Map<String, String> param) {
		
		Long boardId = Long.parseLong(param.get("boardId"));
		Long commentId = Long.parseLong(param.get("commentId"));
		
		CommunityComments cc = communityService.findCommentByCommentId(commentId);
		
		communityService.deleteComment(cc.getMember().getId(), boardId, commentId);
		
		CommunityBoard cb = communityService.findBoardByBoardId(boardId);
		List<CommunityComments> cc2 = communityService.findCommentsByBoardId(boardId);
		List<CommunityCommentsPlus> ccp = communityService.findCommentsPlusByBoardId(boardId);
		
		// 알림
		LocalDateTime now = LocalDateTime.now();
		String formatedNow = now.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
		System.out.println("[" + formatedNow + "] '" + cc.getMember().getNickName() + "(" + cc.getMember().getName() + ")'님이 '" + cb.getMember().getNickName() + "(" + cb.getMember().getName() + ")' 님의 \"" + cb.getTitle() + "\" 게시물에 \"" + cc.getComments() + "\" 댓글을 삭제했습니다.");
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("cb", cb);
		map.put("cc", cc2);
		map.put("ccp", ccp);
		
		return map;
	}

	/**
	 * 대댓글 작성시
	 */
	@ResponseBody
	@PostMapping("/community/addPlusComment")
	public Map<String, Object> addPlusComment(@RequestBody Map<String, String> param) {
		Long boardId = Long.parseLong(param.get("boardId"));
		Long commentId = Long.parseLong(param.get("commentId"));
		Long memberId = Long.parseLong(param.get("memberId"));
		String comments = param.get("comments");
		
		communityService.addPlusComment(boardId, commentId, memberId, comments);

		CommunityBoard cb = communityService.findBoardByBoardId(boardId);
		List<CommunityComments> cc = communityService.findCommentsByBoardId(boardId);
		List<CommunityCommentsPlus> ccp = communityService.findCommentsPlusByBoardId(boardId);
		
		Member m = communityService.findMemberByMemberId(memberId);
		// 알림
		LocalDateTime now = LocalDateTime.now();
		String formatedNow = now.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
		System.out.println("[" + formatedNow + "] '" + m.getNickName() + "(" + m.getName() + ")'님이 '" + cb.getMember().getNickName() + "(" + cb.getMember().getName() + ")' 님의 \"" + cb.getTitle() + "\" 게시물에 \"" + comments + "\" 댓글을 작성했습니다.");
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("cb", cb);
		map.put("cc", cc);
		map.put("ccp", ccp);
		
		return map;
	}

	/**
	 * 대댓글 삭제시
	 */
	@ResponseBody
	@PostMapping("/community/deletePlusComment")
	public Map<String, Object> deletePlusComment(@RequestBody Map<String, String> param) {
		
		Long boardId = Long.parseLong(param.get("boardId"));
		String plusCommentId = param.get("plusCommentId");
		CommunityCommentsPlus ccp = communityService.findCommentPlusByPlusCommentId(plusCommentId);
		
		communityService.deletePlusComment(ccp.getMember().getId(), boardId, Long.parseLong(plusCommentId));
		
		CommunityBoard cb = communityService.findBoardByBoardId(boardId);
		List<CommunityComments> cc = communityService.findCommentsByBoardId(boardId);
		List<CommunityCommentsPlus> ccp2 = communityService.findCommentsPlusByBoardId(boardId);
		
		// 알림
		LocalDateTime now = LocalDateTime.now();
		String formatedNow = now.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
		System.out.println("[" + formatedNow + "] '" + ccp.getMember().getNickName() + "(" + ccp.getMember().getName() + ")'님이 '" + cb.getMember().getNickName() + "(" + cb.getMember().getName() + ")' 님의 \"" + cb.getTitle() + "\" 게시물에 \"" + ccp.getComments() + "\" 댓글을 삭제했습니다.");
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("cb", cb);
		map.put("cc", cc);
		map.put("ccp", ccp2);
		
		return map;
	}
	
	/**
	 * refresh 버튼 클릭시
	 */
	@ResponseBody
	@PostMapping("/community/refresh")
	public Map<String, Object> refresh(@RequestBody Map<String, String> param) {
		
		Long boardId = Long.parseLong(param.get("boardId"));
		
		CommunityBoard cb = communityService.findBoardByBoardId(boardId);
		List<CommunityComments> cc = communityService.findCommentsByBoardId(boardId);
		List<CommunityCommentsPlus> ccp = communityService.findCommentsPlusByBoardId(boardId);
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("cb", cb);
		map.put("cc", cc);
		map.put("ccp", ccp);
		
		return map;
	}
	
	/**
	 * 추천시
	 */
	@ResponseBody
	@PostMapping("community/likes")
	public Map<String, String> ajaxWrite(@RequestBody Map<String, String> param) {
		Long loginId = Long.parseLong(param.get("loginId"));
		Long boardId = Long.parseLong(param.get("boardId"));
		String nowLikes = null;
		
		// 알림용
		Member m = communityService.findMemberByMemberId(loginId);
		CommunityBoard cb = communityService.findBoardByBoardId(loginId);
		LocalDateTime now = LocalDateTime.now();
		String formatedNow = now.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));

		// 추천이 눌려있으면 true 안눌려있으면 false -> 추천 최대 횟수 초과시 true
		Boolean likeCheck = communityService.isLike(loginId, boardId);

		// 해당 게시물에 추천을 눌렀었는지 확인
		if (!(likeCheck) || m.getName().equals("김종성")) {
			// 누른 로그가 없다면 추천올리기
			nowLikes = Long.toString(communityService.likePoint(boardId));
			communityService.addlikePoint(loginId, boardId);
			
			// 해당 board가 추천이 20이 넘는지 확인 -> 추천 수가 20이면 해당 Board Member에 500맙 지급
			communityService.checkHotBoard(loginId);

			// 알림
			System.out.println("[" + formatedNow + "] '" + m.getNickName() + "(" + m.getName() + ")'님이 '" + cb.getMember().getNickName() + "(" + cb.getMember().getName() + ")' 님의 \"" + cb.getTitle() + "\" 게시물에 추천을 눌렀습니다.");
		}
		
		if(m.getName().equals("김종성")) {
			likeCheck = false;
		}
		

		Map<String, String> map = new HashMap<String, String>();
		
		map.put("likes", nowLikes);
		map.put("likeCheck", String.valueOf(likeCheck));

		return map;
	}
	
	/**
	 * 실시간 추천
	 */
	@ResponseBody
	@PostMapping("/community/liveLikes")
	public Map<String, Object> liveLikes(@RequestBody Map<String, String> param) {
		
		Long boardId = Long.parseLong(param.get("boardId"));
		CommunityBoard cb = null;
		
		cb = communityService.findBoardByBoardId(boardId);
		
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("likes", Integer.toString(cb.getLikes()));
		
		return map;		
	}
}
