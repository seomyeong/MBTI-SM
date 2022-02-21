package com.mycompany.myapp.mbtiMatch.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.mycompany.myapp.domain.MbtiComments;
import com.mycompany.myapp.domain.MbtiMatch;
import com.mycompany.myapp.domain.Member;

@Component
public class MbtiMatchDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * 회원의 id를 통해 member정보를 조회.
	 * @param loginId
	 * @return
	 */
	public Member findMemberInfoById(Long loginId) {
		String sql = "SELECT * FROM Member WHERE id = ?";
		return jdbcTemplate.queryForObject(sql, new RowMapper<Member>() {

			@Override
			public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
				
				return new Member(rs.getString("email"), rs.getString("pw"),
						rs.getString("name"), rs.getString("nickName"), rs.getString("birth"),
						rs.getString("mbti"), rs.getString("gender"), 
						rs.getString("phone"), rs.getString("profileImg"));
			}
			
		}, loginId);

	}

	/**
	 * 
	 * type01, type02와 일치하는 궁합 결과를 조회.
	 * @param type01
	 * @param type02
	 * @return mbtiMatch.getResult()
	 */
	public int findResultByMbtiTypes(String type01, String type02) {
		String sql = "SELECT * FROM MbtiMatch WHERE type01=? AND type02=?";
		MbtiMatch mbtiMatch = jdbcTemplate.queryForObject(sql, new RowMapper<MbtiMatch>() {

			@Override
			public MbtiMatch mapRow(ResultSet rs, int rowNum) throws SQLException {
				return new MbtiMatch(rs.getInt("result"));
			}
			
		}, type01, type02);
		
		return mbtiMatch.getResult();
	}

	/***
	 * 유저가 조합한 type01, type02 뿐만 아니라 결과가 같은 type02, type01 조합의 코멘트도 조회. 
	 * @param type01
	 * @param type02
	 * @return
	 */
	public List<MbtiComments> findMcInfoByType(String type01, String type02) { // 매개변수(type)로 mcInfo 조회.
		String sql = "SELECT * FROM MbtiComments WHERE type01=? AND type02=? OR type01=? AND type02=? ORDER BY reportingDate DESC"; // 매개변수로 받은 값과 일치하는 행을 조회.
		List<MbtiComments> mcInfo = jdbcTemplate.query(sql, new RowMapper<MbtiComments>() { // 조건과 일치하는 결과값을 mbtiComments리스트에 저장.

			@Override
			public MbtiComments mapRow(ResultSet rs, int rowNum) throws SQLException {
				
				SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd"); // 조회할 reportingDate 날짜 형식 지정
				
				Member m = findMemberInfoById(rs.getLong("memberId")); // Member타입의 변수 m을 생성하고 memberId값으로 회원의 정보 조회.
				// MbtiComments의 생성자이므로 domain의 형식에 맞게 member, type01, type02, comment, reportingDate를 저장.
				return new MbtiComments(m, rs.getString("type01"), rs.getString("type02"), rs.getString("comment"), fmt.format(rs.getTimestamp("reportingDate"))); 	
			}	
		
		}, type01, type02, type02, type01);
		
		return mcInfo;
	}
	
	/***
	 * 작성한 코멘트 저장.
	 * @param loginId
	 * @param type01
	 * @param type02
	 * @param comment
	 */
	public void addComment(long loginId, String type01, String type02, String comment) {
		String sql = "INSERT INTO MbtiComments(memberId, type01, type02, comment) VALUES(?, ?, ?, ?)";
		jdbcTemplate.update(sql, loginId, type01, type02, comment);
	}
	 
}

	
