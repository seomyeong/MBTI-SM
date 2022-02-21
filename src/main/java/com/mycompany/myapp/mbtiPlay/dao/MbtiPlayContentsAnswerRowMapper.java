package com.mycompany.myapp.mbtiPlay.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mycompany.myapp.domain.MbtiPlayContentsAnswer;

public class MbtiPlayContentsAnswerRowMapper implements RowMapper<MbtiPlayContentsAnswer> {

	@Override
	public MbtiPlayContentsAnswer mapRow(ResultSet rs, int rowNum) throws SQLException {
		MbtiPlayContentsAnswer m = new MbtiPlayContentsAnswer(rs.getLong("id"), rs.getString("memberMbti"),
				rs.getLong("questionNum"), rs.getString("choosenNum"), rs.getString("isSubjective"),
				rs.getString("subjectiveContent"), rs.getInt("choosenNumCount"));
		return m;
	}

}
