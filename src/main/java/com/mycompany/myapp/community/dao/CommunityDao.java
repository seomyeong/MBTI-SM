package com.mycompany.myapp.community.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.mycompany.myapp.domain.CommunityBoard;
import com.mycompany.myapp.domain.CommunityComments;
import com.mycompany.myapp.domain.CommunityCommentsPlus;
import com.mycompany.myapp.domain.LikeLog;
import com.mycompany.myapp.domain.Member;

@Component
public class CommunityDao {
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	// Find Dao
	
	/**
	 * 모든 게시물을 시간 역순으로 찾아줌
	 * 
	 * @return 시간 역순의 CommunityBoard List(모든 게시물)
	 */
	public List<CommunityBoard> findAllContents() {
		// 최근 게시물이 위로 배치하도록
		String sql = "SELECT * FROM CommunityBoard ORDER BY reportingDate DESC";

		return jdbcTemplate.query(sql, new RowMapper<CommunityBoard>() {

			@Override
			public CommunityBoard mapRow(ResultSet rs, int rowNum) throws SQLException {
				SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

				Member member = null;

				member = findMemberByMemberId(rs.getLong("memberId"));

				CommunityBoard cb = new CommunityBoard(rs.getString("title"), rs.getString("contents"),
						fmt.format(rs.getTimestamp("reportingDate")), rs.getInt("views"), rs.getInt("likes"),
						rs.getInt("commentsCount"));
				cb.setId(rs.getLong("id"));
				cb.setMember(member);

				return cb;
			}

		});
	}
	/**
	 * id 값을 이용해 Member를 찾아낸다.
	 * 
	 * @param memberId
	 * @return Member(해당 id값을 가지는 Member)
	 */
	public Member findMemberByMemberId(Long memberId) {
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

		}, memberId);
	}
	/**
	 * id 값을 이용해 Board를 찾아낸다.
	 * 
	 * @param boardId
	 * @return CommunityBoard(해당 id값을 가지는 CommunityBoard)
	 */
	public CommunityBoard findBoardByBoardId(Long boardId) {
		String sql = "SELECT * FROM CommunityBoard WHERE id=?";
		return jdbcTemplate.queryForObject(sql, new RowMapper<CommunityBoard>() {

			@Override
			public CommunityBoard mapRow(ResultSet rs, int rowNum) throws SQLException {
				SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				CommunityBoard cb = new CommunityBoard(rs.getLong("id"), rs.getString("title"),
						rs.getString("contents"), fmt.format(rs.getTimestamp("reportingDate")), rs.getInt("views"),
						rs.getInt("likes"), rs.getInt("commentsCount"));

				cb.setMember(findMemberByMemberId(rs.getLong("memberId")));

				return cb;
			}

		}, boardId);
	}
	/**
	 * Board의 id값을 이용해 하위 Comment List를 찾아낸다.
	 * 
	 * @param boardId
	 * @return CommunityComment List(게시물에 대한 댓글목록)
	 */
	public List<CommunityComments> findCommentsByBoardId(Long boardId) {
		String sql = "SELECT * FROM CommunityComments WHERE boardId=?";
		return jdbcTemplate.query(sql, new RowMapper<CommunityComments>() {

			@Override
			public CommunityComments mapRow(ResultSet rs, int rowNum) throws SQLException {
				SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Member m = findMemberByMemberId(rs.getLong("memberId"));
				CommunityComments cc = new CommunityComments(rs.getLong("id"), m, rs.getString("comments"),
						rs.getInt("likes"), fmt.format(rs.getTimestamp("reportingDate")));
				return cc;
			}

		}, boardId);
	}
	/**
	 * 전체 Board의 카운트 값을 리턴한다.
	 * 
	 * @return All BoardList Count(전체 게시물의 카운트 값)
	 */
	public int findAllContentsCnt() {
		String sql = "SELECT * FROM CommunityBoard";

		List<CommunityBoard> list = jdbcTemplate.query(sql, new RowMapper<CommunityBoard>() {

			@Override
			public CommunityBoard mapRow(ResultSet rs, int rowNum) throws SQLException {
				return new CommunityBoard();
			}

		});

		return list.size();
	}
	/**
	 * 페이징 중 현재 page에 대해 부분적으로 게시물을 가져온다.
	 * 
	 * @param startList
	 * @param listSize
	 * @return CommunityBoard List(현재 페이지에 대한 게시물)
	 */
	public List<CommunityBoard> findBoardByStartList(int startList, int listSize) {
		List<CommunityBoard> cbList = new ArrayList<CommunityBoard>();
		String sql = "SELECT * FROM CommunityBoard ORDER BY reportingDate DESC";

		List<CommunityBoard> list = jdbcTemplate.query(sql, new RowMapper<CommunityBoard>() {

			@Override
			public CommunityBoard mapRow(ResultSet rs, int rowNum) throws SQLException {
				SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

				Member member = null;

				member = findMemberByMemberId(rs.getLong("memberId"));

				CommunityBoard cb = new CommunityBoard(rs.getString("title"), rs.getString("contents"),
						fmt.format(rs.getTimestamp("reportingDate")), rs.getInt("views"), rs.getInt("likes"),
						rs.getInt("commentsCount"));
				cb.setId(rs.getLong("id"));
				cb.setMember(member);

				return cb;
			}

		});

		for (int i = startList; i < startList + listSize; i++) {
			if (!(list.get(i) == null)) {
				cbList.add(list.get(i));
			}
		}

		return cbList;
	}
	/**
	 * 검색시 해당 검색어를 포함하는 게시물의 수를 리턴한다.
	 * 
	 * @param type
	 * @param q
	 * @return 해당 검색어를 포함하는 CommunityBoard Count 
	 */
	public int q_findContentsCnt(String type, String q) {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("type", type);
		params.put("fmtQ", "'%" + q + "%'");

		String sql = "SELECT * FROM CommunityBoard WHERE " + params.get("type") + " LIKE " + params.get("fmtQ");

		List<CommunityBoard> list = jdbcTemplate.query(sql, new RowMapper<CommunityBoard>() {

			@Override
			public CommunityBoard mapRow(ResultSet rs, int rowNum) throws SQLException {
				return new CommunityBoard();
			}

		});

		return list.size();
	}
	/**
	 * 검색기능 사용시 페이징 중 현재 page에 대해 부분적으로 게시물을 가져온다. 
	 * 
	 * @param type
	 * @param q
	 * @param startList
	 * @param pageListSize
	 * @return 검색어에 맞는 CommunityBoard List(현재 페이지에 대한 게시물)
	 */
	public List<CommunityBoard> q_findBoardByStartList(String type, String q, int startList, int pageListSize) {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("type", type);
		params.put("fmtQ", "'%" + q + "%'");

		List<CommunityBoard> cbList = new ArrayList<CommunityBoard>();
		String sql = "SELECT * FROM CommunityBoard WHERE " + params.get("type") + " LIKE " + params.get("fmtQ")
				+ " ORDER BY reportingDate DESC";

		List<CommunityBoard> list = jdbcTemplate.query(sql, new RowMapper<CommunityBoard>() {

			@Override
			public CommunityBoard mapRow(ResultSet rs, int rowNum) throws SQLException {
				SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

				Member member = null;

				member = findMemberByMemberId(rs.getLong("memberId"));

				CommunityBoard cb = new CommunityBoard(rs.getString("title"), rs.getString("contents"),
						fmt.format(rs.getTimestamp("reportingDate")), rs.getInt("views"), rs.getInt("likes"),
						rs.getInt("commentsCount"));
				cb.setId(rs.getLong("id"));
				cb.setMember(member);

				return cb;
			}

		});

		for (int i = startList; i < startList + pageListSize; i++) {
			if (!(list.get(i) == null)) {
				cbList.add(list.get(i));
			}
		}

		return cbList;
	}
	/**
	 * 해당 nickName을 가지는 Member를 리턴한다.
	 * 
	 * @param q
	 * @return Member(해당 nickName을 가지는 Member)
	 */
	public Member q_findMemberByMemberNickName(String q) {

		String sql = "SELECT * FROM Member WHERE nickName=?";
		List<Member> list = jdbcTemplate.query(sql, new RowMapper<Member>() {

			@Override
			public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
				Member m = new Member(rs.getLong("id"), rs.getString("email"), rs.getString("pw"), rs.getString("name"),
						rs.getString("nickName"), rs.getString("birth"), rs.getString("mbti"),
						rs.getString("gender"), rs.getString("phone"), rs.getDate("regDate"),
						rs.getInt("level"), rs.getInt("mabPoint"), rs.getString("profileImg"),
						rs.getInt("contentsCount"), rs.getInt("commentsCount"));
				return m;
			}

		}, q);

		if (list == null || list.size() == 0 || list.equals(null)) {
			return null;
		} else {
			return list.get(0);
		}
	}
	/**
	 * 해당 memberId에 대한 게시물을 최신 게시물 순서로 리턴한다.
	 * 
	 * @param memberId
	 * @param startList
	 * @param pageListSize
	 * @return CommunityBoard List(해당 memberId에 대한 게시물)
	 */
	public List<CommunityBoard> q_memberId_findBoardByStartList(Long memberId, int startList, int pageListSize) {

		List<CommunityBoard> cbList = new ArrayList<CommunityBoard>();
		String sql = "SELECT * FROM CommunityBoard WHERE memberId=? ORDER BY reportingDate DESC";

		List<CommunityBoard> list = jdbcTemplate.query(sql, new RowMapper<CommunityBoard>() {

			@Override
			public CommunityBoard mapRow(ResultSet rs, int rowNum) throws SQLException {
				SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

				Member member = null;

				member = findMemberByMemberId(rs.getLong("memberId"));

				CommunityBoard cb = new CommunityBoard(rs.getString("title"), rs.getString("contents"),
						fmt.format(rs.getTimestamp("reportingDate")), rs.getInt("views"), rs.getInt("likes"),
						rs.getInt("commentsCount"));
				cb.setId(rs.getLong("id"));
				cb.setMember(member);

				return cb;
			}

		}, memberId);

		for (int i = startList; i < startList + pageListSize; i++) {
			if (!(list.get(i) == null)) {
				cbList.add(list.get(i));
			}
		}

		return cbList;
	}
	/**
	 * 해당 memberId에 맞는 게시물 Count를 리턴한다.
	 * 
	 * @param memberId
	 * @return Count(해당 memberId에 맞는 게시물 Count)
	 */
	public int q_memberId_findContentsCnt(Long memberId) {
		String sql = "SELECT * FROM CommunityBoard WHERE memberId=?";

		List<CommunityBoard> list = jdbcTemplate.query(sql, new RowMapper<CommunityBoard>() {

			@Override
			public CommunityBoard mapRow(ResultSet rs, int rowNum) throws SQLException {
				return new CommunityBoard();
			}

		}, memberId);

		return list.size();
	}
	/**
	 * 해당 mbti를 가지는 Member가 쓴 전체 게시물의 카운트를 리턴한다.	
	 * 
	 * @param mbtiInfo
	 * @return Count(해당 mbti를 가지는 Member가 쓴 전체 게시물의 카운트)
	 */
	public int q_mbti_findContentsCnt(String mbtiInfo) {
		String sql = "SELECT * FROM CommunityBoard INNER JOIN Member ON Member.id = CommunityBoard.memberId WHERE mbti=?";

		List<CommunityBoard> list = jdbcTemplate.query(sql, new RowMapper<CommunityBoard>() {

			@Override
			public CommunityBoard mapRow(ResultSet rs, int rowNum) throws SQLException {
				return new CommunityBoard();
			}

		}, mbtiInfo);

		return list.size();
	}
	/**
	 * 해당 mbti를 가진 Member가 쓴 게시글을 페이징 처리할 때 부분적으로 Board List를 리턴한다,
	 * 
	 * @param mbtiInfo
	 * @param startList
	 * @param pageListSize
	 * @return CommunityBoard List(해당 mbti를 가진 Member가 쓴 게시글들의 일부)
	 */
	public List<CommunityBoard> q_mbti_findBoardByMbtiInfo(String mbtiInfo, int startList, int pageListSize) {
		List<CommunityBoard> cbList = new ArrayList<CommunityBoard>();
		String sql = "SELECT * FROM CommunityBoard INNER JOIN Member ON Member.id = CommunityBoard.memberId WHERE mbti=? ORDER BY reportingDate DESC";

		List<CommunityBoard> list = jdbcTemplate.query(sql, new RowMapper<CommunityBoard>() {

			@Override
			public CommunityBoard mapRow(ResultSet rs, int rowNum) throws SQLException {
				SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

				Member member = null;

				member = findMemberByMemberId(rs.getLong("memberId"));

				CommunityBoard cb = new CommunityBoard(rs.getString("title"), rs.getString("contents"),
						fmt.format(rs.getTimestamp("reportingDate")), rs.getInt("views"), rs.getInt("likes"),
						rs.getInt("commentsCount"));
				cb.setId(rs.getLong("id"));
				cb.setMember(member);

				return cb;
			}

		}, mbtiInfo);

		for (int i = startList; i < startList + pageListSize; i++) {
			if (!(list.get(i) == null)) {
				cbList.add(list.get(i));
			}
		}

		return cbList;
	}
	/**
	 * 추천이 높은 순서대로 CommunityBoard를 리턴한다.
	 * 
	 * @param startList
	 * @param pageListSize
	 * @return CommunityBoard List(추천이 높은 순서의 게시물)
	 */
	public List<CommunityBoard> findBoardByStartList_hot(int startList, int pageListSize) {
		List<CommunityBoard> cbList = new ArrayList<CommunityBoard>();
		String sql = "SELECT * FROM CommunityBoard ORDER BY likes DESC";

		List<CommunityBoard> list = jdbcTemplate.query(sql, new RowMapper<CommunityBoard>() {

			@Override
			public CommunityBoard mapRow(ResultSet rs, int rowNum) throws SQLException {
				SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

				Member member = null;

				member = findMemberByMemberId(rs.getLong("memberId"));

				CommunityBoard cb = new CommunityBoard(rs.getString("title"), rs.getString("contents"),
						fmt.format(rs.getTimestamp("reportingDate")), rs.getInt("views"), rs.getInt("likes"),
						rs.getInt("commentsCount"));
				cb.setId(rs.getLong("id"));
				cb.setMember(member);

				return cb;
			}

		});

		for (int i = startList; i < startList + pageListSize; i++) {
			if (!(list.get(i) == null)) {
				cbList.add(list.get(i));
			}
		}

		return cbList;
	}
	/**
	 * 해당 type의 역순으로 페이징 처리할 때 부분적으로 게시물을 리턴한다.
	 * 
	 * @param type
	 * @param startList
	 * @param pageListSize
	 * @return CommunityBoard List(해당 type에 대해 역순인 부분적 게시물)
	 */
	public List<CommunityBoard> findBoardByStartList_sort(String type, int startList, int pageListSize) {
		List<CommunityBoard> cbList = new ArrayList<CommunityBoard>();
		String sql = "SELECT * FROM CommunityBoard ORDER BY " + type + " DESC";
		
		List<CommunityBoard> list = jdbcTemplate.query(sql, new RowMapper<CommunityBoard>() {

			@Override
			public CommunityBoard mapRow(ResultSet rs, int rowNum) throws SQLException {
				SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

				Member member = null;

				member = findMemberByMemberId(rs.getLong("memberId"));

				CommunityBoard cb = new CommunityBoard(rs.getString("title"), rs.getString("contents"),
						fmt.format(rs.getTimestamp("reportingDate")), rs.getInt("views"), rs.getInt("likes"),
						rs.getInt("commentsCount"));
				cb.setId(rs.getLong("id"));
				cb.setMember(member);

				return cb;
			}

		});

		for (int i = startList; i < startList + pageListSize; i++) {
			if (!(list.get(i) == null)) {
				cbList.add(list.get(i));
			}
		}

		return cbList;
	}
	/**
	 * 해당 BoardId를 가지고 있는 CommunityCommentsPlus를 리턴한다.
	 * 
	 * @param boardId
	 * @return CommunityCommentsPlus(해당 BoardId를 가지고 있는 대댓글)
	 */
	public List<CommunityCommentsPlus> findCommentsPlusByBoardId(Long boardId) {
		String sql = "SELECT * FROM CommunityComments_plus WHERE boardId=?";
		return jdbcTemplate.query(sql, new RowMapper<CommunityCommentsPlus>() {

			@Override
			public CommunityCommentsPlus mapRow(ResultSet rs, int rowNum) throws SQLException {
				SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				CommunityBoard cb = findCommunityBoardByBoardId(rs.getLong("boardId"));
				CommunityComments cc = findCommunityCommentsByCommentId(rs.getLong("commentId"));
				Member m = findMemberByMemberId(rs.getLong("memberId"));
				CommunityCommentsPlus ccp = new CommunityCommentsPlus(rs.getLong("id"), cb, cc, m, rs.getString("comments"),
						rs.getInt("likes"), fmt.format(rs.getTimestamp("reportingDate")));
				return ccp;
			}

		}, boardId);
	}
	/**
	 * 해당 commentId를 가지고 있는 comment를 리턴한다.
	 * 
	 * @param commentId
	 * @return CommunityComments(해당 commentid를 가지고 있는 댓글)
	 */
	public CommunityComments findCommunityCommentsByCommentId(Long commentId) {
		String sql = "SELECT * FROM CommunityComments WHERE id=?";
		return jdbcTemplate.queryForObject(sql, new RowMapper<CommunityComments>() {

			@Override
			public CommunityComments mapRow(ResultSet rs, int rowNum) throws SQLException {
				CommunityComments cc = new CommunityComments();
				cc.setId(rs.getLong("id"));
				return cc;
			}

		}, commentId);
	}
	/**
	 * 해당 communityBoardId를 가지고 있는 CommunityBoard를 리턴한다.	
	 * 
	 * @param communityBoardId
	 * @return CommunityBoard(해당 communityBoardId를 가지고 있는 게시물)
	 */
	public CommunityBoard findCommunityBoardByBoardId(Long communityBoardId) {
		String sql = "SELECT * FROM CommunityBoard WHERE id=?";
		return jdbcTemplate.queryForObject(sql, new RowMapper<CommunityBoard>() {

			@Override
			public CommunityBoard mapRow(ResultSet rs, int rowNum) throws SQLException {
				CommunityBoard cb = new CommunityBoard();
				cb.setId(rs.getLong("id"));
				return cb;
			}

		}, communityBoardId);
	}
	/**
	 * 해당 plusCommentId를 가지고 있는 CommunityCommentsPlus를 리턴한다.
	 * 
	 * @param plusCommentId
	 * @return CommunityCommentsPlus(해당 plusCommentId를 가지고 있는 대댓글)
	 */
	public CommunityCommentsPlus findCommentPlusByPlusCommentId(String plusCommentId) {
		String sql = "SELECT * FROM CommunityComments_plus WHERE id=?";
		return jdbcTemplate.queryForObject(sql, new RowMapper<CommunityCommentsPlus>() {

			@Override
			public CommunityCommentsPlus mapRow(ResultSet rs, int rowNum) throws SQLException {
				SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				CommunityBoard cb = findCommunityBoardByBoardId(rs.getLong("boardId"));
				CommunityComments cc = findCommunityCommentsByCommentId(rs.getLong("commentId"));
				Member m = findMemberByMemberId(rs.getLong("memberId"));
				CommunityCommentsPlus ccp = new CommunityCommentsPlus(rs.getLong("id"), cb, cc, m, rs.getString("comments"),
						rs.getInt("likes"), fmt.format(rs.getTimestamp("reportingDate")));
				return ccp;
			}

		}, plusCommentId);
	}
	/**
	 * 해당 commentId를 가지고 있는 CommunityComment를 리턴한다.
	 * 
	 * @param commentId
	 * @return CommunityComments(해당 commentId를 가지고 있는 댓글)
	 */
	public CommunityComments findCommentByCommentId(Long commentId) {
		String sql = "SELECT * FROM CommunityComments WHERE id=?";
		return jdbcTemplate.queryForObject(sql, new RowMapper<CommunityComments>() {

			@Override
			public CommunityComments mapRow(ResultSet rs, int rowNum) throws SQLException {
				SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Member m = findMemberByMemberId(rs.getLong("memberId"));
				CommunityComments cc = new CommunityComments(rs.getLong("id"), m, rs.getString("comments"),
						rs.getInt("likes"), fmt.format(rs.getTimestamp("reportingDate")));
				return cc;
			}

		}, commentId);
	}
	/**
	 * 인기글을 랜덤으로 리턴한다.
	 * hotView => 뽑을 게시물의 수
	 * listScope => 추천수가 20이 넘는 게시물의 row 범위
	 * 
	 * @return CommunityBoard List(likes > 20을 만족하는 게시글)
	 * 
	 */
	int i = 1;
	public List<CommunityBoard> findRandomHotBoard() {
		int hotView = 3;
		int listScope = 10;
		List<Integer> randomNum = new ArrayList<Integer>();
		List<CommunityBoard> hotList = new ArrayList<CommunityBoard>();
		
		String sql = "SELECT * FROM CommunityBoard WHERE likes >= 20 ORDER BY reportingDate DESC";
		List<CommunityBoard> cbList = jdbcTemplate.query(sql, new RowMapper<CommunityBoard>() {

			@Override
			public CommunityBoard mapRow(ResultSet rs, int rowNum) throws SQLException {
				SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

				Member member = null;

				member = findMemberByMemberId(rs.getLong("memberId"));

				CommunityBoard cb = new CommunityBoard(rs.getString("title"), rs.getString("contents"),
						fmt.format(rs.getTimestamp("reportingDate")), rs.getInt("views"), rs.getInt("likes"),
						rs.getInt("commentsCount"));
				cb.setId(rs.getLong("id"));
				cb.setMember(member);
				
				// 10개까지만 뽑기
				if(i > listScope) {
					return null;
				}
				i++;
				
				return cb;
			}
			
		});
		
		i = 0;
		int realSize = 0;
		
		for(CommunityBoard cb : cbList) {
			if(cb != null) {
				realSize++;
			}
		}
		
		if(!(realSize < hotView)) {
			randomNum.add((int)((Math.random() * 10000) % realSize + 1));
			
			for(int j = 1; j < hotView; j++) {
				boolean dulplication = false;
				int num = (int)((Math.random() * 10000) % realSize + 1);
				
				for(Integer n : randomNum) {
					if(n == num) {
						dulplication = true;
						j--;
					}
				}
				if(!dulplication) randomNum.add(num);
			}
			
			for(int j = 0; j < hotView; j++) {
				hotList.add(cbList.get(randomNum.get(j)-1));
			}
			
			return hotList;
		}

		return null;
	}
	
	
	// Add Dao
	
	/**
	 * 해당 정보를 가지는 CommunityBoard를 생성한다.
	 * 
	 * @param loginId
	 * @param title
	 * @param contents
	 */
	public void addCommunityBoard(Long loginId, String title, String contents) {
		String sql = "INSERT INTO CommunityBoard(memberId, title, contents) VALUES(?, ?, ?)";
		jdbcTemplate.update(sql, loginId, title, contents);
	}
	/**
	 * 해당 정보를 가지는 Comment를 생성한다.
	 * 
	 * @param loginId
	 * @param boardId
	 * @param comment
	 * @return
	 */
	public Long addComment(Long loginId, Long boardId, String comment) {
		String sql = "INSERT INTO CommunityComments(memberId, boardId, comments) VALUES(?, ?, ?)";
		// jdbcTemplate.update(sql, loginId, boardId, comment);
		Connection conn = null;
		
		String url = "jdbc:derby://localhost:1527/MBTI";
        String id = "MBTI";
        String pw = "MBTI";
        
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            conn = DriverManager.getConnection(url, id, pw);
            
            PreparedStatement ps = conn.prepareStatement(sql,
            		Statement.RETURN_GENERATED_KEYS);
            
            ps.setLong(1, loginId);
            ps.setLong(2, boardId);
            ps.setString(3, comment);
            
            ps.execute();
            
            ResultSet rs = ps.getGeneratedKeys();
            Long generatedKey = Long.getLong("0");
            if (rs.next()) {
            	generatedKey = rs.getLong(1);
            }
            
            ps.close();
            rs.close();
            conn.close();
            
            return generatedKey;
        } catch (Exception e) {
            System.out.println("DBUtil.getConnection() : " + e.toString());
            return Long.getLong("0");
        }
        
	}
	/**
	 * 해당 정보로 likeLog를 생성한다.
	 * 
	 * @param loginId
	 * @param boardId
	 */
	public void addLikePoint(Long loginId, Long boardId) {
		String sql = "INSERT INTO LikeLog(memberId, boardId) VALUES(?, ?)";
		jdbcTemplate.update(sql, loginId, boardId);
	}
	/**
	 * 해당 BoardId를 가지는 CommunityBoard에 CommunityCommentCount를 1 증가시킨다.
	 * 
	 * @param boardId
	 */
	public void addCommentCount(Long boardId) {
		String sql = "UPDATE CommunityBoard SET commentsCount=commentsCount+1 WHERE id=?";
		jdbcTemplate.update(sql, boardId);
	}
	/**
	 * 해당 정보로 CommunityComment_plus를 생성한다.
	 * 
	 * @param boardId
	 * @param commentId
	 * @param memberId
	 * @param comments
	 */
	public void addPlusComment(Long boardId, Long commentId, Long memberId, String comments) {
		String sql = "INSERT INTO CommunityComments_plus(boardId, commentId, memberId, comments) VALUES(?, ?, ?, ?)";
		
		jdbcTemplate.update(sql, boardId, commentId, memberId, comments);
	}
	/**
	 * 해당 logindId를 가지는 Member에 ContentsCount를 1 증가시킨다.
	 * 
	 * @param loginId
	 */
	public void addContentsCount(Long loginId) {
		String sql = "UPDATE Member SET contentsCount=contentsCount+1 WHERE id=?";
		jdbcTemplate.update(sql, loginId);
	}
	/**
	 * 해당 logindId를 가지는 Member에 commentsCount를 1 증가시킨다.
	 * 
	 * @param loginId
	 */
	public void addCommentsCount(Long loginId) {
		String sql = "UPDATE Member SET commentsCount=commentsCount+1 WHERE id=?";
		jdbcTemplate.update(sql, loginId);
	}
	/**
	 * 해당 boardId를 가지는 Board에 views를 1 증가시킨다.
	 * 
	 * @param boardId
	 */
	public void viewPoint(Long boardId) {
		String sql = "UPDATE CommunityBoard SET views=views+1 WHERE id=?";
		jdbcTemplate.update(sql, boardId);
	}
	/**
	 * 해당 boardId를 가지는 Board에 likes를 1 증가시킨다.
	 * 
	 * @param boardId
	 */
	public void likePoint(Long boardId) {
		String sql = "UPDATE CommunityBoard SET likes=likes+1 WHERE id=?";
		jdbcTemplate.update(sql, boardId);
	}

	
	// Delete Dao
	
	/**
	 * 해당 boardId를 가지는 Board를 삭제한다.
	 * 
	 * @param boardId
	 */
	public void deleteBoard(String boardId) {
		String sql = "DELETE FROM CommunityBoard WHERE id=?";
		jdbcTemplate.update(sql, boardId);
	}
	/**
	 * 해당 commentId를 가지는 Comment를 삭제한다.
	 * 
	 * @param commentId
	 */
	public void deleteComment(Long commentId) {
		String sql = "DELETE FROM CommunityComments WHERE id=?";
		jdbcTemplate.update(sql, commentId);
	}
	/**
	 * 해당 plusCommentId를 가지는 PlusComment를 삭제한다.
	 * 
	 * @param plusCommentId
	 */
	public void deletePlusComment(Long plusCommentId) {
		String sql = "DELETE FROM CommunityComments_plus WHERE id=?";
		jdbcTemplate.update(sql, plusCommentId);
	}
	/**
	 * 해당 memberId를 가지는 Member의 contentsCount를 1 감소시킨다.
	 * 
	 * @param memberId
	 */
	public void deleteContentsCount(Long memberId) {
		String sql = "UPDATE Member SET contentsCount=contentsCount-1 WHERE id=?";
		jdbcTemplate.update(sql, memberId);
	}
	/**
	 * 해당 memberId를 가지는 Member의 commentsCount를 1 감소시킨다.
	 * 
	 * @param memberId
	 */
	public void deleteCommentsCount(Long memberId) {
		String sql = "UPDATE Member SET commentsCount=commentsCount-1 WHERE id=?";
		jdbcTemplate.update(sql, memberId);
	}
	
	
	// Is Dao

	/**
	 * LikeLog 테이블 중 해당 정보를 만족하는 데이터가 있는지 확인한다.
	 * 
	 * @param loginId
	 * @param boardId
	 * @return Boolean(LikeLog 테이블에 해당 정보를 만족하는 데이터가 있는지 여부)
	 */
	public boolean isLike(Long loginId, Long boardId) {
		int maxLikeCount = 3; // 추천 최대 횟수
		String sql = "SELECT * FROM LikeLog WHERE memberId=? AND boardId=?";
		List<LikeLog> likeListCheck = jdbcTemplate.query(sql, new RowMapper<LikeLog>() {

			@Override
			public LikeLog mapRow(ResultSet rs, int rowNum) throws SQLException {
				return new LikeLog(rs.getLong("memberId"), rs.getLong("boardId"));
			}

		}, loginId, boardId);

		if (likeListCheck.size() < maxLikeCount || likeListCheck == null || likeListCheck.equals(null)) {
			return false;
		}

		return true;

	}
	/**
	 * 해당 boardId를 가지는 board가 있는지 확인한다.
	 * 
	 * @param boardId
	 * @return 해당 boardId의 board가 존재하면 true, 그렇지 않으면 false
	 */
	public boolean isBoard(Long boardId) {
		String sql = "SELECT id FROM CommunityBoard WHERE id=?";
		List<CommunityBoard> list = null;
		list = jdbcTemplate.query(sql, new RowMapper<CommunityBoard>() {

			@Override
			public CommunityBoard mapRow(ResultSet rs, int rowNum) throws SQLException {
				return new CommunityBoard();
			}
			
		}, boardId);
		
		if(list.size() == 0 || list.equals(null) || list == null) {
			return false;
		}
		
		return true;
	}

	
	// 기타 Dao
	
	/**
	 * 해당 BoardId를 가지는 CommunityBoard의 CommentsCount를 1 감소시킨다.
	 * 
	 * @param boardId
	 */
	public void removeCommentCount(Long boardId, int countPlusComment) {
		String sql = "UPDATE CommunityBoard SET commentsCount=commentsCount-? WHERE id=?";
		jdbcTemplate.update(sql, countPlusComment, boardId);
	}
	/**
	 * 해당 CommentId를 가지는 CommunityComments_plus의 Count
	 * 
	 * @param commentId
	 * @return Count(해당 CommentId를 가지는 대댓글들의 Count)
	 */
	public int countPlusComment(Long commentId) {
		String sql = "SELECT * FROM CommunityComments_plus WHERE commentId=?";
		
		List<CommunityCommentsPlus> list = null;
				
		list = jdbcTemplate.query(sql, new RowMapper<CommunityCommentsPlus>() {

			@Override
			public CommunityCommentsPlus mapRow(ResultSet rs, int rowNum) throws SQLException {
				return new CommunityCommentsPlus();
			}
			
		}, commentId);
		
		return list.size();
	}
	/**
	 * 해당 memberId를 가지는 Member에 해당 mabPoint를 부여한다.
	 * 
	 * @param memberId
	 * @param mabPoint
	 */
	public void plusMab(Long memberId, int mabPoint) {
		String sql = "UPDATE Member SET mabPoint=? WHERE id=?";
		jdbcTemplate.update(sql, mabPoint, memberId);
	}
	/**
	 * 해당 memberId를 가지는 Member에 해당 mabPoint만큼 차감한다.
	 * 
	 * @param memberId
	 * @param mabPoint
	 */
	public void minusMab(Long memberId, int mabPoint) {
		String sql = "UPDATE Member SET mabPoint=mabPoint-? WHERE id=?";
		jdbcTemplate.update(sql, mabPoint, memberId);
	}
	/**
	 * 해당 id를 가지는 Member에 levelUp수치만큼 레벨을 올린다.
	 * 
	 * @param id
	 * @param levelUp
	 */
	public void resultLevel(Long id, int levelUp) {
		String sql = "UPDATE Member SET level=level+? WHERE id=?";
		jdbcTemplate.update(sql, levelUp, id);
	}
	/**
	 * 해당 id를 가지는 CommunityBoard에 title과 contents를 수정한다.
	 * @param boardId
	 * @param title
	 * @param contents
	 */
	public void UpdateCommunityBoard(long boardId, String title, String contents) {
		String sql = "UPDATE CommunityBoard SET title=?, contents=? WHERE id=?";
		jdbcTemplate.update(sql, title, contents, boardId);
	}
	
}
