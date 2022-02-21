package com.mycompany.myapp.member.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.mycompany.myapp.domain.Member;
import com.mycompany.myapp.member.command.LoginLogCommand;

@Component
public class MemberDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * 회원가입
	 * 
	 * @param member
	 */
	public void addMember(Member member) {
		String sql = "INSERT INTO MEMBER(email, pw, name, nickName, birth, mbti, gender, phone, profileImg) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		jdbcTemplate.update(sql, member.getEmail(), member.getPw(), member.getName(), member.getNickName(),
				member.getBirth(), member.getMbti(), member.getGender(), member.getPhone(), member.getProfileImg());
	}

	/**
	 * 회원정보를 email로 조회
	 * 
	 * @param member
	 * @return
	 */
	public Member memberInfo(Member member) {
		String sql = "SELECT * FROM MEMBER WHERE email = ?";
		return jdbcTemplate.queryForObject(sql, new RowMapper<Member>() {

			@Override
			public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
				return new Member(rs.getLong("id"), rs.getString("email"), rs.getString("pw"), rs.getString("name"),
						rs.getString("nickname"), rs.getString("birth"), rs.getString("mbti"), rs.getString("gender"),
						rs.getString("phone"), rs.getTimestamp("regDate"), rs.getInt("level"), rs.getInt("mabPoint"),
						rs.getString("profileImg"), rs.getInt("contentsCount"), rs.getInt("commentsCount"));
			}

		}, member.getEmail());
	}

	/**
	 * Member테이블의 회원정보를 세션id로 조회
	 * 
	 * @param loginId
	 * @return 회원정보
	 */
	public Member findMemberById(Long loginId) {
		String sql = "SELECT * FROM Member WHERE id=?";
		return jdbcTemplate.queryForObject(sql, new RowMapper<Member>() {

			@Override
			public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
				Member m = new Member(rs.getLong("id"), rs.getString("email"), rs.getString("pw"), rs.getString("name"),
						rs.getString("nickName"), rs.getString("birth"), rs.getString("mbti"), rs.getString("gender"),
						rs.getString("phone"), rs.getDate("regDate"), rs.getInt("level"), rs.getInt("mabPoint"),
						rs.getString("profileImg"), rs.getInt("contentsCount"), rs.getInt("commentsCount"));
				return m;
			}

		}, loginId);
	}

	/**
	 * 회원정보 수정
	 * 
	 * @param member
	 * @param loginId
	 */
	public void updateMember(Member member, long loginId) {
		String sql = "UPDATE MEMBER SET email=?, pw=?, name=?, nickName=?, birth=?, mbti=?, gender=?, phone=?, profileImg=? WHERE id=?";
		jdbcTemplate.update(sql, member.getEmail(), member.getPw(), member.getName(), member.getNickName(),
				member.getBirth(), member.getMbti(), member.getGender(), member.getPhone(), member.getProfileImg(),
				loginId);
	}

	/**
	 * 로그인
	 * 
	 * @param member
	 * @return
	 */
	public boolean login(Member member) {
		String sql = "SELECT * FROM MEMBER WHERE email = ? AND pw = ?";
		List<Member> memberCheck = null;
		memberCheck = jdbcTemplate.query(sql, new RowMapper<Member>() {

			@Override
			public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
				Member member = new Member(rs.getLong("id"), rs.getString("email"), rs.getString("pw"),
						rs.getString("name"), rs.getString("nickname"), rs.getString("birth"), rs.getString("mbti"),
						rs.getString("gender"), rs.getString("phone"), rs.getTimestamp("regDate"));
				return member;
			}

		}, member.getEmail(), member.getPw());

		if (memberCheck.size() == 0) {
			return false;
		}
		return true;
	}


	/**
	 * LoginLog테이블에 로그인 이력 데이터 추가
	 * 
	 * @param loginId
	 */
	public void addLoginLog(Long loginId) {
		String sql = "INSERT INTO LoginLog(memberId) VALUES (?)";
		jdbcTemplate.update(sql, loginId);
	}

	/**
	 * LoginLog테이블에서 조회하는 당일 날짜와 일치하는 log데이터가 있는지 검사
	 * 
	 * @param loginId
	 * @param nowYear
	 * @param nowMonth
	 * @param nowDay
	 * @return
	 */
	public boolean isLoginLogDate(Long loginId, String nowYear, String nowMonth, String nowDay) {
		String sql = "SELECT YEAR(regDate), MONTH(regDate), DAY(regDate) FROM LoginLog WHERE memberId=? "
				+ "AND YEAR(regDate)=? AND MONTH(regDate)=? AND DAY(regDate)=?";
		List<LoginLogCommand> cLog = null;
		cLog = jdbcTemplate.query(sql, new RowMapper<LoginLogCommand>() {

			@Override
			public LoginLogCommand mapRow(ResultSet rs, int rowNum) throws SQLException {
				LoginLogCommand c = new LoginLogCommand(rs.getString(1), rs.getString(2), rs.getString(3));
				return c;
			}

		}, loginId, nowYear, nowMonth, nowDay);
		if (cLog.size() == 0) {
			return false;
		}
		return true;
	}

	/**
	 * 매일 첫 로그인 유저에게 맙티포인트 10p 적립
	 * 
	 * @param loginId
	 */
	public void calcLoginPoint(Long loginId, int mapPoint) {
		String sql = "UPDATE member SET mabPoint=? WHERE id=?";
		jdbcTemplate.update(sql, mapPoint, loginId);
	}

	/**
	 * 멤버의 맙포인트가 1000점모일 때 마다 레벨업
	 * 
	 * @param loginId
	 * @param level
	 */
	public void levelUp(Long loginId, int level) {
		String sql = "UPDATE Member SET level=level+? WHERE id=?";
		jdbcTemplate.update(sql, level, loginId);
	}

	/**
	 * 레벨업 직후 맙포인트 리셋
	 * 
	 * @param loginId
	 * @param mapPoint
	 */
	public void updateMapMinus(Long loginId, int mapPoint) {
		String sql = "UPDATE member SET mabPoint=mabPoint-? WHERE id=?";
		jdbcTemplate.update(sql, mapPoint, loginId);
	}

	/**
	 * 이메일 중복검사
	 * 
	 * @param email
	 * @return
	 */
	public boolean isEmailCheck(String email) {
		String sql = "SELECT * FROM Member WHERE email=?";
		List<Member> list = null;
		list = jdbcTemplate.query(sql, new RowMapper<Member>() {

			@Override
			public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
				return new Member();
			}

		}, email);

		if (list == null || list.equals(null) || list.size() == 0) {
			return true;
		}

		return false;
	}

	/**
	 * 닉네임 중복검사
	 * 
	 * @param nickName
	 * @return
	 */
	public boolean isNickNameCheck(String nickName) {
		String sql = "SELECT * FROM Member WHERE nickName=?";
		List<Member> list = null;
		list = jdbcTemplate.query(sql, new RowMapper<Member>() {

			@Override
			public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
				return new Member();
			}

		}, nickName);

		if (list == null || list.equals(null) || list.size() == 0) {
			return true;
		}

		return false;
	}

}