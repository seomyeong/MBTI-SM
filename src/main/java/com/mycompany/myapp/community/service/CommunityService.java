package com.mycompany.myapp.community.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.myapp.community.dao.CommunityDao;
import com.mycompany.myapp.domain.CommunityBoard;
import com.mycompany.myapp.domain.CommunityComments;
import com.mycompany.myapp.domain.CommunityCommentsPlus;
import com.mycompany.myapp.domain.Member;

@Service
public class CommunityService {
	@Autowired
	CommunityDao communityDao;
	
	// find Service
	public List<CommunityBoard> findAllContents(){
		return communityDao.findAllContents();
	}
	public CommunityBoard findBoardByBoardId(Long parseLong) {
		return communityDao.findBoardByBoardId(parseLong);
	}
	public List<CommunityComments> findCommentsByBoardId(Long boardId) {
		return communityDao.findCommentsByBoardId(boardId);
	}
	public Member findMemberByMemberId(Long loginId) {
		return communityDao.findMemberByMemberId(loginId);
	}
	public int findAllContentsCnt() {
		return communityDao.findAllContentsCnt();
	}
	public List<CommunityBoard> findBoardByStartList(int startList, int listSize) {
		return communityDao.findBoardByStartList(startList, listSize);
	}
	public int q_findContentsCnt(String type, String q) {
		return communityDao.q_findContentsCnt(type, q);
	}

	public List<CommunityBoard> q_findBoardByStartList(String type, String q, int startList, int pageListSize) {
		return communityDao.q_findBoardByStartList(type, q, startList, pageListSize);
	}

	public Member findMemberByMemberNickName(String q) {
		return communityDao.q_findMemberByMemberNickName(q);
	}
	
	public List<CommunityBoard> q_memberId_findBoardByStartList(long memberId, int startList, int pageListSize) {
		return communityDao.q_memberId_findBoardByStartList(memberId, startList, pageListSize);
	}

	public int q_memberId_findContentsCnt(Long memberId) {
		return communityDao.q_memberId_findContentsCnt(memberId);
	}

	public int q_mbti_findContentsCnt(String mbtiInfo) {
		return communityDao.q_mbti_findContentsCnt(mbtiInfo);
	}

	public List<CommunityBoard> q_mbti_findBoardByMbtiInfo(String mbtiInfo, int startList, int pageListSize) {
		return communityDao.q_mbti_findBoardByMbtiInfo(mbtiInfo, startList, pageListSize);
	}
	public List<CommunityBoard> findBoardByStartList_hot(int startList, int pageListSize) {
		return communityDao.findBoardByStartList_hot(startList, pageListSize);
	}

	public List<CommunityBoard> findBoardByStartList_sort(String type, int startList, int pageListSize) {
		return communityDao.findBoardByStartList_sort(type, startList, pageListSize);
	}
	public List<CommunityCommentsPlus> findCommentsPlusByBoardId(Long boardId) {
		return communityDao.findCommentsPlusByBoardId(boardId);
	}
	public CommunityCommentsPlus findCommentPlusByPlusCommentId(String plusCommentId) {
		return communityDao.findCommentPlusByPlusCommentId(plusCommentId);
	}

	public CommunityComments findCommentByCommentId(Long commentId) {
		return communityDao.findCommentByCommentId(commentId);
	}
	public List<CommunityBoard> findRandomHotBoard() {
		return communityDao.findRandomHotBoard();
	}
	
	
	// add Service
	public void addCommunityBoard(Long loginId, String title, String contents) {
		communityDao.addCommunityBoard(loginId, title, contents);
		
		// Member테이블에 총 게시글 수 카운트 올림
		communityDao.addContentsCount(loginId);
		
		// 50 맙 포인트 부여
		Member m = communityDao.findMemberByMemberId(loginId);
		
		m.calcContentsPoint();
		communityDao.plusMab(m.getId(), m.getMabPoint());
		
		this.resultLevel(m);
		
	}
	public long addComment(Long loginId, Long boardId, String comment) {
		long commentId = communityDao.addComment(loginId, boardId, comment);
		
		communityDao.addCommentCount(boardId);
		communityDao.addCommentsCount(loginId);
		
		// 30 맙 포인트 부여
		Member m = communityDao.findMemberByMemberId(loginId);
		
		m.calcCommentsPoint();
		communityDao.plusMab(m.getId(), m.getMabPoint());
		
		this.resultLevel(m);
		
		return commentId;
	}
	public void addlikePoint(Long loginId, Long boardId) {
		communityDao.addLikePoint(loginId, boardId);
	}
	public void addPlusComment(Long boardId, Long commentId, Long memberId, String comments) {
		communityDao.addPlusComment(boardId, commentId, memberId, comments);
		communityDao.addCommentCount(boardId);
		communityDao.addCommentsCount(memberId);
		
		// 30 맙 포인트 부여
		Member m = communityDao.findMemberByMemberId(memberId);
		
		m.calcCommentsPoint();
		communityDao.plusMab(m.getId(), m.getMabPoint());
				
		this.resultLevel(m);
	}
	
	
	// delete Service
	public void deleteBoard(Long memberId, String boardId) {
		communityDao.deleteBoard(boardId);
		communityDao.deleteContentsCount(memberId);
	}

	public void deleteComment(Long memberId, Long boardId, Long commentId) {
		int countPlusComment = communityDao.countPlusComment(commentId);
		
		// 현재 삭제하는 댓글의 수를 포함시켜야 하기 때문에 countPlusComment + 1
		communityDao.removeCommentCount(boardId, countPlusComment + 1);	

		communityDao.deleteComment(commentId);
		communityDao.deleteCommentsCount(memberId);
	}
	public void deletePlusComment(Long memberId, Long boardId, Long plusCommentId) {
		communityDao.deletePlusComment(plusCommentId);
		communityDao.removeCommentCount(boardId, 1);
		communityDao.deleteCommentsCount(memberId);
	}
	
	
	// is Service
	public boolean isLike(Long loginId, Long boardId) {
		return communityDao.isLike(loginId, boardId);
	}
	public boolean isBoard(Long boardId) {
		return communityDao.isBoard(boardId);
	}
	
	// 기타 Service
	public void viewPoint(Long boardId) {
		communityDao.viewPoint(boardId);
	}
	public Long likePoint(Long boardId) {
		communityDao.likePoint(boardId);
		// 증가 후 현재 추천 수 반환
		return (long) communityDao.findBoardByBoardId(boardId).getLikes();
	}
	public void resultLevel(Member m) {
		int maxExp = 1000;
		int mab = m.getMabPoint();
		int levelUp = mab / maxExp;
		
		if(levelUp != 0) {
			communityDao.resultLevel(m.getId(), levelUp);
			communityDao.minusMab(m.getId(), levelUp * maxExp);			
		}
	}
	public void checkHotBoard(Long boardId) {
		CommunityBoard cb = communityDao.findBoardByBoardId(boardId);
		
		if(cb.getLikes() == 20) {
			Member m = communityDao.findMemberByMemberId(cb.getMember().getId());
			
			m.calcBestPoint();
			communityDao.plusMab(m.getId(), m.getMabPoint());
			
			this.resultLevel(m);
		}
	}
	public void UpdateCommunityBoard(long boardId, String title, String contents) {
		communityDao.UpdateCommunityBoard(boardId, title, contents);
	}
	

}
