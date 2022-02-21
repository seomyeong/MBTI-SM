package com.mycompany.myapp.mbtiPlay.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.mycompany.myapp.domain.AnswersLog;
import com.mycompany.myapp.domain.ContentsLog;
import com.mycompany.myapp.domain.MbtiPlayContents;
import com.mycompany.myapp.domain.MbtiPlayContentsAnswer;
import com.mycompany.myapp.domain.Member;
import com.mycompany.myapp.mbtiPlay.command.ContentsLogCommand;
import com.mycompany.myapp.mbtiPlay.command.MbtiPlayContentsAnswerCommand;
import com.mycompany.myapp.mbtiPlay.command.SubjectiveCountCommand;

@Component
public class MbtiPlayMakeContentsDao {
	@Autowired
	JdbcTemplate jdbcTemplate;

	/*
	 * about Member table
	 * 
	 */
	
	/**
	 *  Member테이블의 회원정보를 세션id로 조회
	 * @param loginId
	 * @return 회원정보
	 */
	public Member findMemberById(Long loginId) {
		String sql = "SELECT * FROM Member WHERE id=?";
		return jdbcTemplate.queryForObject(sql, new RowMapper<Member>() {

			@Override
			public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
				Member m = new Member(rs.getLong("id"), rs.getString("email"), rs.getString("pw"), rs.getString("name"),
						rs.getString("nickName"), rs.getString("birth"), rs.getString("mbti"),
						rs.getString("gender"), rs.getString("phone"), rs.getDate("regDate"),
						rs.getInt("level"), rs.getInt("mabPoint"), rs.getString("profileImg"),
						rs.getInt("contentsCount"), rs.getInt("commentsCount"));
				return m;
			}

		}, loginId);
	}

	/**
	 *  맙티플레이 생성한 멤버에게 맙티포인트 30p 적립
	 * @param loginId
	 */
	public void updateMapPlus(Long loginId, int mapPoint) {
		String sql = "UPDATE member SET mabPoint=? WHERE id=?";
		jdbcTemplate.update(sql, mapPoint, loginId);
	}
	
	/**
	 * 레벨업 직후 맙포인트 리셋
	 * @param loginId
	 * @param mapPoint
	 */
	public void updateMapMinus(Long loginId, int mapPoint) {
		String sql = "UPDATE member SET mabPoint=mabPoint-? WHERE id=?";
		jdbcTemplate.update(sql, mapPoint, loginId);
	}
	
	/**
	 *  멤버의 맙포인트가 1000점모일 때 마다 레벨업
	 * @param loginId
	 * @param level
	 */
	public void levelUp(Long loginId, int level) {
		String sql = "UPDATE Member SET level=level+? WHERE id=?";
		jdbcTemplate.update(sql, level, loginId);
	}
	
	

	/*
	 * about MbtiPlayContents table
	 */
	
	/**
	 *  mbtiPlayMakeContents테이블에 유저가 만든 문답 데이터 추가
	 * @param memberId
	 * @param con
	 */
	public void addContent(long memberId, MbtiPlayContents con) {
		String sql = "INSERT INTO MbtiPlayContents(memberId, question, answer01, answer02, answer03) VALUES (?,?,?,?,?)";
		jdbcTemplate.update(sql, memberId, con.getQuestion(), con.getAnswer01(), con.getAnswer02(), con.getAnswer03());
		;
	}

	/**
	 *  MbtiPlayContents테이블의 pk수 조회
	 * @return
	 */
	public List<MbtiPlayContents> findContentsPk() {
		String sql = "SELECT id FROM MbtiPlayContents";
		return jdbcTemplate.query(sql, new RowMapper<MbtiPlayContents>() {

			@Override
			public MbtiPlayContents mapRow(ResultSet rs, int rowNum) throws SQLException {
				MbtiPlayContents m = new MbtiPlayContents(rs.getLong("id"));
				return m;
			}

		});
	}

	/**
	 *  MbtiPlayContents테이블의 문제를 랜덤으로 조회
	 * @param randomNum
	 * @return
	 */
	public List<MbtiPlayContents> findQuestionByRandomNum(long randomNum) {
		String sql = "SELECT * FROM MbtiPlayContents WHERE id=?";
		return jdbcTemplate.query(sql, new RowMapper<MbtiPlayContents>() {

			@Override
			public MbtiPlayContents mapRow(ResultSet rs, int rowNum) throws SQLException {
				MbtiPlayContents m = new MbtiPlayContents(rs.getLong("id"), rs.getString("question"),
						rs.getString("answer01"), rs.getString("answer02"), rs.getString("answer03"));
				return m;
			}

		}, randomNum);
	}

	
	/*
	 * about MbtiPlayContentsAnswer table
	 */

	/**
	 *  MbtiPlayContentsAnswer테이블에 유저가 선택한 객관식 데이터 추가
	 * @param memberMbti
	 * @param questionNum
	 * @param choosenNum
	 * @param isSubjective
	 * @param subjectiveContent
	 * @param choosenNumCount
	 */
	public void addAnswer(String memberMbti, Long questionNum, String choosenNum, String isSubjective,
			String subjectiveContent, int choosenNumCount) {
		String sql = "INSERT INTO MbtiPlayContentsAnswer(memberMbti, questionNum, choosenNum, isSubjective, subjectiveContent, choosenNumCount) VALUES (?,?,?,?,?,?)";
		jdbcTemplate.update(sql, memberMbti, questionNum, choosenNum, isSubjective, subjectiveContent, choosenNumCount);
	}
	
	/**
	 *  MbtiPlayContentsAnswer테이블의 객관식 보기값 조회
	 * @param memberMbti
	 * @param questionNum
	 * @param choosenNum
	 * @return
	 */
	public MbtiPlayContentsAnswer findObjectiveAnswer(String memberMbti, Long questionNum, String choosenNum) {
		String sql = "SELECT * FROM MbtiPlayContentsAnswer WHERE MemberMbti=? AND questionNum=? AND choosenNum=? AND isSubjective=false";
		return jdbcTemplate.queryForObject(sql, new MbtiPlayContentsAnswerRowMapper(), memberMbti, questionNum, choosenNum);
	}
	
	/**
	 *  MbtiPlayContentsAnswer테이블에 로그인한 유저의 MBTI와 해당 문제번호의 데이터가 있는지 검사
	 * @param memberMbti
	 * @param questionNum
	 * @return
	 */
	public boolean isMbtiTypeAndQuestion(String memberMbti, Long questionNum) {
		String sql = "SELECT * FROM MbtiPlayContentsAnswer WHERE memberMbti=? AND questionNum=?";
		List<MbtiPlayContentsAnswer> ans = null;

		ans = jdbcTemplate.query(sql, new MbtiPlayContentsAnswerRowMapper(), memberMbti, questionNum);

		if (ans.size() == 0) {
			return false;
		}
		return true;
	}
	
	/**
	 *  MbtiPlayContentsAnswer테이블에 객관식 보기에 대한 count를 업데이트
	 * @param subjectiveContent
	 * @param memberMbti
	 * @param questionNum
	 * @param choosenNum
	 * @param isSubjective
	 */
	public void updateObjectiveAnswer(String subjectiveContent, String memberMbti, Long questionNum, String choosenNum,
			String isSubjective) {
		String sql = "UPDATE MbtiPlayContentsAnswer SET choosenNumCount=choosenNumCount+1, subjectiveContent=?  WHERE memberMbti=? AND questionNum=? AND choosenNum=? AND isSubjective=?";
		jdbcTemplate.update(sql, subjectiveContent, memberMbti, questionNum, choosenNum, isSubjective);
	}
	
	/**
	 *  MbtiPlayContentsAnswer테이블의 해당하는 문제의 주관식답변 수 조회
	 * @param questionNum
	 * @return
	 */
	public SubjectiveCountCommand findSubCount(Long questionNum) {
		String sql="SELECT COUNT(*) FROM MbtiPlayContentsAnswer WHERE questionNum=? AND isSubjective=true";
		SubjectiveCountCommand ans = jdbcTemplate.queryForObject(sql, new RowMapper<SubjectiveCountCommand>() {

			@Override
			public SubjectiveCountCommand mapRow(ResultSet rs, int rowNum) throws SQLException {
				SubjectiveCountCommand s = new SubjectiveCountCommand(rs.getLong(1));
				return s;
			}
		}, questionNum);
		return ans;
	}

	/**
	 *  MbtiPlayContentsAnswer테이블에 객관식이 풀린 기록이 있는지 검사
	 * @param memberMbti
	 * @param questionNum
	 * @return
	 */
	public boolean isObjectiveAnswer(String memberMbti, long questionNum) {
		String sql = "SELECT * FROM MbtiPlayContentsAnswer WHERE MemberMbti=? AND questionNum=?";
		List<MbtiPlayContentsAnswer> ans = jdbcTemplate.query(sql, new MbtiPlayContentsAnswerRowMapper(), memberMbti, questionNum);

		if (ans.size() == 0) {
			return false;
		}
		return true;
	}
	
	/** MbtiPlayContentsAnswer테이블에 questionNum이 있는지 검사
	 * 
	 * @param questionNum
	 * @return
	 */
	public boolean isQuestionNum(long questionNum) {
		String sql = "SELECT * FROM MbtiPlayContentsAnswer WHERE questionNum=?";
		List<MbtiPlayContentsAnswer> ans = null;

		ans = jdbcTemplate.query(sql, new MbtiPlayContentsAnswerRowMapper(), questionNum);

		if (ans.size() == 0) {
			return false;
		}
		return true;
	}
	
	/**
	 *  MbtiPlayContentsAnswer테이블에 questionNum=?의 주관식 데이터가 있는지 검사
	 * @param questionNum
	 * @return
	 */
	public boolean isSubjectiveAnswer(Long questionNum) {
		String sql = "SELECT * FROM MbtiPlayContentsAnswer WHERE questionNum=? AND isSubjective=true";
		List<MbtiPlayContentsAnswer> ans = jdbcTemplate.query(sql, new MbtiPlayContentsAnswerRowMapper(), questionNum);

		if (ans.size() == 0) {
			return false;
		}
		return true;
	}

	/**MbtiPlayContentsAnswer테이블의 모든 주관식 답변 조회
	 * 
	 * @param questionNum
	 * @return
	 */
	public List<MbtiPlayContentsAnswerCommand> findAllSubjectiveAnswers(Long questionNum) {
		String sql = "SELECT * FROM MbtiPlayContentsAnswer WHERE questionNum=? AND isSubjective=true";
		List<MbtiPlayContentsAnswerCommand> ans = null;

		ans = jdbcTemplate.query(sql, new RowMapper<MbtiPlayContentsAnswerCommand>() {

			@Override
			public MbtiPlayContentsAnswerCommand mapRow(ResultSet rs, int rowNum) throws SQLException {
				MbtiPlayContentsAnswerCommand m = new MbtiPlayContentsAnswerCommand(rs.getLong("id"),
						rs.getString("memberMbti"), rs.getString("isSubjective"), rs.getString("subjectiveContent"));
				return m;
			}
		}, questionNum);
		return ans;
	}

	/*
	 * about Log tables
	 */
	/**
	 *  AnswersLog 테이블에 문답을 풀었는 기록이 있는지 검사
	 * @param loginId
	 * @param questionNum
	 * @return
	 */
	public boolean isAnswersLog(Long loginId, Long questionNum) {
		String sql = "SELECT * FROM AnswersLog WHERE memberId=? AND contentsNum=?";
		List<AnswersLog> ans = null;
		ans = jdbcTemplate.query(sql, new RowMapper<AnswersLog>() {

			@Override
			public AnswersLog mapRow(ResultSet rs, int rowNum) throws SQLException {
				AnswersLog a = new AnswersLog(rs.getLong("id"), rs.getLong("memberId"), rs.getLong("contentsNum"));
				return a;
			}

		}, loginId, questionNum);

		if (ans.size() == 0) {
			return false;
		}
		return true;
	}

	/**
	 *  AnswersLog테이블에 문답 이력 데이터 추가
	 * @param loginId
	 * @param questionNum
	 */
	public void addAnswersLog(Long loginId, Long questionNum) {
		String sql = "INSERT INTO AnswersLog(memberId, contentsNum) VALUES (?,?)";
		jdbcTemplate.update(sql, loginId, questionNum);
	}

	/**
	 *  ContentsLog테이블에 문답 생성이력 데이터 추가
	 * @param loginId
	 */
	public void addContentsLog(Long loginId) {
		String sql = "INSERT INTO ContentsLog(memberId, contentsCount) VALUES (?,1)";
		jdbcTemplate.update(sql, loginId);
	}

	/**
	 *  ContentsLog테이블에 문답 생성이력 있는지 검사
	 * @param loginId
	 * @return
	 */
	public boolean isContentsLog(Long loginId) {
		String sql = "SELECT * FROM ContentsLog WHERE memberId = ?";
		List<ContentsLog> cLog = null;
		cLog = jdbcTemplate.query(sql, new RowMapper<ContentsLog>() {

			@Override
			public ContentsLog mapRow(ResultSet rs, int rowNum) throws SQLException {
				ContentsLog c = new ContentsLog(rs.getLong("id"), rs.getLong("memberId"), rs.getInt("contentsCount"),
						rs.getTimestamp("regDate"));
				return c;
			}

		}, loginId);
		if (cLog.size() == 0) {
			return false;
		}
		return true;
	}

	/**
	 *  ContentsLog테이블에 조회하는 날짜와 동일한 년-월-일이 있는가?
	 * @param loginId
	 * @return
	 */
	public boolean isContentsLogDate(Long loginId) {
		String sql = "SELECT regDate FROM ContentsLog WHERE memberId=?";
		List<ContentsLog> cLog = null;
		cLog = jdbcTemplate.query(sql, new RowMapper<ContentsLog>() {

			@Override
			public ContentsLog mapRow(ResultSet rs, int rowNum) throws SQLException {
				ContentsLog c = new ContentsLog(rs.getTimestamp("regDate"));
				return c;
			}
		}, loginId);
		if (cLog.size() == 0) {
			return false;
		}
		return true;
	}

	/**
	 *  유저가 문답을 만들었을 때 ContentsLog에 찍히는 년-월-일 조회
	 * @param loginId
	 * @return
	 */
	public ContentsLog findContentsLogDate(Long loginId) {
		String sql = "SELECT regDate FROM ContentsLog WHERE memberId=?";
		return jdbcTemplate.queryForObject(sql, new RowMapper<ContentsLog>() {

			@Override
			public ContentsLog mapRow(ResultSet rs, int rowNum) throws SQLException {
				ContentsLog c = new ContentsLog(rs.getTimestamp("regDate"));
				return c;
			}
		}, loginId);
	}

	/**
	 *  ContentsLog 테이블의 contentsCount가 3번인지 검사
	 * @param loginId
	 * @param nowYear
	 * @param nowMonth
	 * @param nowDay
	 * @return
	 */
	public boolean isContentsLogLimitTime(Long loginId, String nowYear, String nowMonth, String nowDay) {
		String sql = "SELECT * FROM ContentsLog WHERE memberId=? AND contentsCount=3 AND YEAR(regDate)=? AND MONTH(regDate)=? AND DAY(regDate)=?";
		List<ContentsLog> cLog = null;
		cLog = jdbcTemplate.query(sql, new RowMapper<ContentsLog>() {

			@Override
			public ContentsLog mapRow(ResultSet rs, int rowNum) throws SQLException {
				ContentsLog c = new ContentsLog(rs.getLong("id"), rs.getLong("memberId"), rs.getInt("contentsCount"),
						rs.getTimestamp("regDate"));
				return c;
			}

		}, loginId, nowYear, nowMonth, nowDay);
		if (cLog.size() == 0) {
			return false;
		}
		return true;
	}

	/**
	 *  ContentsLog 테이블의 contentsCount가 3이 아닐 때 contentsCount++
	 * @param loginId
	 * @param nowYear
	 * @param nowMonth
	 * @param nowDay
	 */
	public void updateContentsLog(Long loginId, String nowYear, String nowMonth, String nowDay) {
		String sql = "UPDATE ContentsLog SET contentsCount=contentsCount+1 WHERE memberId=? "
				+ "AND YEAR(regDate)=? AND MONTH(regDate)=? AND DAY(regDate)=?";
		jdbcTemplate.update(sql, loginId, nowYear, nowMonth, nowDay);

	}

	/**
	 *  ContentsLog테이블에서 조회하는 당일 날짜와 일치하는 log데이터가 있는지 검사
	 * @param loginId
	 * @param nowYear
	 * @param nowMonth
	 * @param nowDay
	 * @return
	 */
	public boolean isContentsLogDate(Long loginId, String nowYear, String nowMonth, String nowDay) {
		String sql = "SELECT YEAR(regDate),MONTH(regDate),DAY(regDate) FROM ContentsLog WHERE memberId=? "
				+ "AND YEAR(regDate)=? AND MONTH(regDate)=? AND DAY(regDate)=?";
		List<ContentsLogCommand> cLog = null;
		cLog = jdbcTemplate.query(sql, new RowMapper<ContentsLogCommand>() {

			@Override
			public ContentsLogCommand mapRow(ResultSet rs, int rowNum) throws SQLException {
				ContentsLogCommand c = new ContentsLogCommand(rs.getString(1), rs.getString(2), rs.getString(3));
				return c;
			}

		}, loginId, nowYear, nowMonth, nowDay);
		if (cLog.size() == 0) {
			return false;
		}
		return true;
	}

	/**
	 *  AnswersLog테이블의 pk수 조회
	 * @param memberId
	 * @return
	 */
	public long isAnswersLogSize(long memberId) {
		String sql = "SELECT id FROM AnswersLog WHERE memberId=?";
		List<AnswersLog> ans = null;
		ans = jdbcTemplate.query(sql, new RowMapper<AnswersLog>() {

			@Override
			public AnswersLog mapRow(ResultSet rs, int rowNum) throws SQLException {
				AnswersLog a = new AnswersLog(rs.getLong("id"));
				return a;
			}

		}, memberId);
		long answersTotalNum = ans.size();
		return answersTotalNum;
	}

	/**
	 * ContentsLog테이블의 정보 조회
	 * @param loginId
	 * @param nowYear
	 * @param nowMonth
	 * @param nowDay
	 * @return
	 */
	public ContentsLog findContentsLog(Long loginId, String nowYear, String nowMonth, String nowDay) {
		String sql = "SELECT * FROM ContentsLog WHERE memberId = ? AND YEAR(regDate)=? AND MONTH(regDate)=? AND DAY(regDate)=?";
		ContentsLog cLog = jdbcTemplate.queryForObject(sql, new RowMapper<ContentsLog>() {

			@Override
			public ContentsLog mapRow(ResultSet rs, int rowNum) throws SQLException {
				ContentsLog c = new ContentsLog(rs.getLong("id"), rs.getLong("memberId"), rs.getInt("contentsCount"),
						rs.getTimestamp("regDate"));
				return c;
			}

		}, loginId, nowYear, nowMonth, nowDay);
		return cLog;
	}


}