package com.mycompany.myapp.member.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.myapp.domain.Member;
import com.mycompany.myapp.member.dao.MemberDao;

@Service
public class MemberService {
	@Autowired
	MemberDao dao;

	/**
	 * 회원가입
	 */
	public void addMember(Member member) {
		dao.addMember(member);
	}

	/**
	 * 회원정보 조회
	 */
	public Member memberInfo(Member member) {
		return dao.memberInfo(member);
	}

	/**
	 * 회원정보 수정
	 */
	public void updateMember(Member member, long loginId) {
		dao.updateMember(member, loginId);
	}

	/**
	 * 로그인
	 */
	public boolean login(Member member) {
		return dao.login(member);
	}

	/**
	 * addLoginLog테이블에 로그인 이력 데이터 추가
	 */
	public void addLoginLog(Long loginId) {
		dao.addLoginLog(loginId);
	}

	/**
	 * LoginLog테이블에서 조회하는 당일 날짜와 일치하는 log데이터가 있는지 검사
	 */
	public boolean isLoginLogDate(Long loginId, String nowYear, String nowMonth, String nowDay) {
		if (dao.isLoginLogDate(loginId, nowYear, nowMonth, nowDay)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 매일 첫 로그인 유저에게 맙티포인트 10p 적립
	 */
	public void calcLoginPoint(Long loginId) {
		Member m = dao.findMemberById(loginId);

		m.calcLoginPoint();
		dao.calcLoginPoint(loginId, m.getMabPoint());

		this.levelUp(m);
	}

	/**
	 * 멤버 레벨업 계산
	 * 
	 * @param member
	 */
	public void levelUp(Member member) {
		member = dao.findMemberById(member.getId());
		int maxExp = 1000;
		int map = member.getMabPoint();
		int level = map / maxExp;

		if (level != 0) {
			dao.levelUp(member.getId(), level);
			dao.updateMapMinus(member.getId(), level * maxExp);
		}
	}

	/**
	 * 이메일 중복검사
	 */
	public boolean isEmailCheck(String email) {
		return dao.isEmailCheck(email);
	}

	/**
	 * 닉네임 중복검사
	 */
	public boolean isNickNameCheck(String nickName) {
		return dao.isNickNameCheck(nickName);
	}

}