package com.mycompany.myapp.cultureBoard.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.mycompany.myapp.domain.CultureBoard;
import com.mycompany.myapp.domain.CultureBoardComment;
import com.mycompany.myapp.domain.LikeLog;
import com.mycompany.myapp.domain.LikeLogComment;
import com.mycompany.myapp.domain.Member;


@Component
public class CultureCommunityDao {
	@Autowired
	JdbcTemplate jdbcTemplate;

	
	public List<CultureBoard> findFirstContents(){
		String sql = "SELECT * FROM CultureBoard WHERE contentType='M' ORDER BY reportingDate DESC";
		
		return jdbcTemplate.query(sql, new RowMapper<CultureBoard>() {

			@Override
			public CultureBoard mapRow(ResultSet rs, int rowNum) throws SQLException {
				SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Member member = null;
				member = findMemberByMemberId(rs.getLong("memberId"));
				
				CultureBoard cb = new CultureBoard(rs.getLong("id"), member, rs.getString("contents01"), rs.getString("contents02"), rs.getString("contentType").charAt(0),
								rs.getString("title"), fmt.format(rs.getTimestamp("reportingDate")), rs.getInt("likes"), rs.getBoolean("likesStatus"),rs.getInt("commentNum"),rs.getString("link"));
				
				return cb;
			}
		});
	}
	
	
	//memberId를 통한 멤버객체 추출
	public Member findMemberByMemberId(Long memberId) {
		String sql = "SELECT * FROM Member WHERE id=?";
		return jdbcTemplate.queryForObject(sql, new RowMapper<Member>() {

			@Override
			public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
				Member m = new Member(rs.getLong("id"),rs.getString("email"), rs.getString("pw"), rs.getString("name"), rs.getString("nickName"), rs.getString("birth"), rs.getString("mbti"),
						rs.getString("gender"), rs.getString("phone"), rs.getDate("regDate"), rs.getInt("level"), rs.getInt("mabPoint"), rs.getString("profileImg"));
				return m;
			}
		}, memberId);
	}
	
	
	public String findCultureContentsByNicknameContent(String nickName, String content02) {
		String sql = "SELECT id FROM CultureBoard INNER JOIN MEMBER on MEMBER.id=CultureBoard.memberId WHERE nickName=? AND contents02=?";
		return jdbcTemplate.queryForObject(sql, new RowMapper<String>() {

			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				long id = rs.getLong("id");
				return String.valueOf(id);
			}
			
		},nickName, content02);
	}
	

	//영화/음악/여행 버튼을 통한 리스트 찾기
	public List<CultureBoard> findCultureContents(String contentType){
		String sql = "SELECT * FROM CultureBoard WHERE contentType=? ORDER BY reportingDate DESC";
		
		return jdbcTemplate.query(sql, new RowMapper<CultureBoard>() {

			@Override
			public CultureBoard mapRow(ResultSet rs, int rowNum) throws SQLException {
				
				SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Member member = null;
				member = findMemberByMemberId(rs.getLong("memberId"));
				
				CultureBoard cb = new CultureBoard(rs.getLong("id"), member, rs.getString("contents01"), rs.getString("contents02"), rs.getString("contentType").charAt(0),
						rs.getString("title"), fmt.format(rs.getTimestamp("reportingDate")), rs.getInt("likes"), rs.getBoolean("likesStatus"),rs.getInt("commentNum"),rs.getString("link"));
				
				return cb;
			}
		}, contentType);
	}
	
	
	public List<CultureBoard> findMbtiContents(String mbtiValue, String contentType){
		String sql="SELECT * FROM CultureBoard INNER JOIN MEMBER on MEMBER.id=CultureBoard.memberId WHERE mbti=? AND contentType=? ORDER BY reportingDate DESC";
		return jdbcTemplate.query(sql, new RowMapper<CultureBoard>() {

			@Override
			public CultureBoard mapRow(ResultSet rs, int rowNum) throws SQLException {
				SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Member member = null;
				member = findMemberByMemberId(rs.getLong("memberId"));
				
				CultureBoard cb = new CultureBoard(rs.getLong("id"), member, rs.getString("contents01"), rs.getString("contents02"), rs.getString("contentType").charAt(0),
						rs.getString("title"), fmt.format(rs.getTimestamp("reportingDate")), rs.getInt("likes"), rs.getBoolean("likesStatus"),rs.getInt("commentNum"),rs.getString("link"));
			
				return cb;
			}
			
		},mbtiValue, contentType);
	}

	//해당 컨텐츠의 해당 아이디가 '좋아요'를 눌렀는지 확인
	public Boolean isLike(long loginId, long boardId) {
		String sql = "SELECT * FROM LikeLogForCulture WHERE memberId=? AND boardId=?";
		//queryForObject는 무조건 값이 하나가 존재해야함!
		
		List<LikeLog> likeListCheck = jdbcTemplate.query(sql, new RowMapper<LikeLog>() {
		
			@Override 
			public LikeLog mapRow(ResultSet rs, int rowNum) throws SQLException {
				LikeLog ll = new LikeLog();
				ll.setBoardId(rs.getLong("boardId"));
				ll.setMemberId(rs.getLong("memberId"));
				return ll;
			}
		}, loginId, boardId);
		
		//해당 사람이 좋아요를 안누른상태 false
		if(likeListCheck.isEmpty()) {
			return false;
		}
		return true;
	}
	

	//해당 보드의 추천수 증가,상태TRUE DB에 적용()
	public void likePoint(long boardId) {
		String sql = "UPDATE CultureBoard SET likes=likes+1,likesStatus=TRUE WHERE id=?";
		jdbcTemplate.update(sql, boardId);
	}
	
	
	////////////////////////////////////////////////////////
	
	//해당 보드의 추천수 증가 및 DB에 적용()
		public void unlikePoint(long boardId) {
			String sql = "UPDATE CultureBoard SET likes=likes-1 ,likesStatus=FALSE WHERE id=?";
			jdbcTemplate.update(sql, boardId);
		}
	
	//증가 후 적용된 DB 추천수 받아오기
	public Long findLikesByBoardId(long boardId) {
		String sql = "SELECT likes FROM CultureBoard WHERE id=?";
		return jdbcTemplate.queryForObject(sql, new RowMapper<Long>(){
			@Override
			public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getLong("likes");
			}
		},boardId);
	}

	////////////////////////////////////////////////////////
	
	public void addLikePoint(long loginId, long boardId) {
		String sql = "INSERT INTO LikeLogForCulture(memberId, boardId) VALUES(?,?)";
		jdbcTemplate.update(sql, loginId, boardId);
	}
	
	public void removeLikePoint(long loginId, long boardId) {
		String sql = "DELETE FROM LikeLogForCulture WHERE memberId=? AND boardId=?";
		jdbcTemplate.update(sql, loginId, boardId);
	}


	public void addWrittenContent(long memberId, String contents01, String contents02, char contentType, String title,
			String link) {
		if(contents02 == null) {
			contents02 = "";
		}
		//char형으로 TABLE에 INSERT는 불가
		String contentTypeFormat = String.valueOf(contentType);
		String sql = "INSERT INTO CultureBoard(memberId, title, contents01, contents02, contentType, link) VALUES(?,?,?,?,?,?)";
		jdbcTemplate.update(sql, memberId, title, contents01, contents02, contentTypeFormat, link);
	}

	
	
	////////////////////////////////////////////
	
	
	public List<CultureBoard> findLikesOrderByType(String contentType){
		String sql = "SELECT * FROM CultureBoard WHERE ContentType=? ORDER BY Likes DESC";
		
		return jdbcTemplate.query(sql, new RowMapper<CultureBoard>() {

			@Override
			public CultureBoard mapRow(ResultSet rs, int rowNum) throws SQLException {
				SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Member member = null;
				member = findMemberByMemberId(rs.getLong("memberId"));
				
				CultureBoard cb = new CultureBoard(rs.getLong("id"), member, rs.getString("contents01"), rs.getString("contents02"), rs.getString("contentType").charAt(0),
						rs.getString("title"), fmt.format(rs.getTimestamp("reportingDate")), rs.getInt("likes"), rs.getBoolean("likesStatus"),rs.getInt("commentNum"),rs.getString("link"));
			
				
				return cb;
			}
		},contentType);
	}


	public List<CultureBoard> findLikesOrderByTypeMbti(String contentType, String mbtiValue) {
		String sql = "SELECT * FROM CultureBoard INNER JOIN MEMBER on MEMBER.id=CultureBoard.memberId WHERE mbti=? AND contentType=? ORDER BY Likes DESC";

		return jdbcTemplate.query(sql, new RowMapper<CultureBoard>() {

			@Override
			public CultureBoard mapRow(ResultSet rs, int rowNum) throws SQLException {
				SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Member member = null;
				member = findMemberByMemberId(rs.getLong("memberId"));
				
				CultureBoard cb = new CultureBoard(rs.getLong("id"), member, rs.getString("contents01"), rs.getString("contents02"), rs.getString("contentType").charAt(0),
						rs.getString("title"), fmt.format(rs.getTimestamp("reportingDate")), rs.getInt("likes"), rs.getBoolean("likesStatus"),rs.getInt("commentNum"),rs.getString("link"));
			
				return cb;
			}
		},mbtiValue , contentType);
	}
	
	///////////cultureBoard comment 부분 //////////////////////
	
	public List<CultureBoardComment> findAllCultureBoardComment(){
		String sql = "SELECT * FROM CultureBoardComment ORDER BY reportingDate DESC";
		return jdbcTemplate.query(sql, new RowMapper<CultureBoardComment>() {

			@Override
			public CultureBoardComment mapRow(ResultSet rs, int rowNum) throws SQLException {
				Member member = null;
				CultureBoard cultureBoard = null;
				SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				member = findMemberByMemberId(rs.getLong("memberId"));
				cultureBoard = findCultureBoardByBoardId(rs.getLong("boardId"));
				
				CultureBoardComment cbc = new CultureBoardComment(
						rs.getLong("id"),
						member, cultureBoard,
						rs.getString("comment"),
						rs.getInt("likes"),
						rs.getBoolean("likesStatus"),
						fmt.format(rs.getTimestamp("reportingDate"))
						);
				
				return cbc;
			}
		});
	}
	
	// 보드아이디를 통한 cultureBoard 찾기
	public CultureBoard findCultureBoardByBoardId(long boardId) {
		String sql = "SELECT * FROM CultureBoard WHERE id=?";
		return jdbcTemplate.queryForObject(sql, new RowMapper<CultureBoard>() {

			@Override
			public CultureBoard mapRow(ResultSet rs, int rowNum) throws SQLException {
				SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Member member = null;
				member = findMemberByMemberId(rs.getLong("memberId"));
				
				CultureBoard cb = new CultureBoard(rs.getLong("id"), member, rs.getString("contents01"), rs.getString("contents02"), rs.getString("contentType").charAt(0),
						rs.getString("title"), fmt.format(rs.getTimestamp("reportingDate")), rs.getInt("likes"), rs.getBoolean("likesStatus"),rs.getInt("commentNum"),rs.getString("link"));
			
				return cb;
			}
		},boardId);
	}
	
	//댓글 작성 db에 저장
	public void addCultureBoardComment(long loginId, long boardId, String comment) {
		String sql = "INSERT INTO CultureBoardComment(memberId, boardId, comment) VALUES(?, ?, ?)";
		jdbcTemplate.update(sql, loginId, boardId, comment);
	}
	//댓글 갯수 증가
	public void addCommentNum(long boardId) {
		String sql = "UPDATE CultureBoard SET commentNum=commentNum+1 WHERE id=?";
		jdbcTemplate.update(sql, boardId);
	}
	//댓글 작성시 댓글갯수 업데이트
		public Long findCommentNumByBoardId(Long boardId) {
			String sql = "SELECT commentNum FROM CultureBoard WHERE id=?";
			return jdbcTemplate.queryForObject(sql, new RowMapper<Long>() {

				@Override
				public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
					Long commentNum = rs.getLong("commentNum");
					return commentNum;
				}
			},boardId);
		}
	//보드 아이디를 통한 전체 comment list 
	public List<CultureBoardComment> findAllCultureBoardCommentByBoardId(long boardId){
		String sql = "SELECT * FROM CultureBoardComment WHERE boardId=? ORDER BY reportingDate DESC";
		return jdbcTemplate.query(sql, new RowMapper<CultureBoardComment>() {

			@Override
			public CultureBoardComment mapRow(ResultSet rs, int rowNum) throws SQLException {
				Member member = null;
				CultureBoard cultureBoard = null;
				SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				member = findMemberByMemberId(rs.getLong("memberId"));
				cultureBoard = findCultureBoardByBoardId(rs.getLong("boardId"));
				
				CultureBoardComment cbc = new CultureBoardComment(
						rs.getLong("id"),
						member, cultureBoard,
						rs.getString("comment"),
						rs.getInt("likes"),
						rs.getBoolean("likesStatus"),
						fmt.format(rs.getTimestamp("reportingDate"))
						);
				
				return cbc;
			}
		},boardId);
	}

	
	/////////댓글 좋아요 dao 부분////////////

	public Boolean isLikeForComment(Long loginId, Long commentId) {
		String sql = "SELECT * FROM LikeLogComment WHERE memberId=? AND commentId=?";
		List<LikeLogComment> llc = jdbcTemplate.query(sql, new RowMapper<LikeLogComment>() {

			@Override
			public LikeLogComment mapRow(ResultSet rs, int rowNum) throws SQLException {
				LikeLogComment llc = new LikeLogComment();
				llc.setMemberId(rs.getLong("memberId"));
				llc.setCommentId(rs.getLong("commentId"));
				return llc;
			}
		}, loginId, commentId); 
		if(llc.isEmpty()) {
			return false;
		}
		return true;
	}


	public void likePointForComment(Long commentId) {
		String sql = "UPDATE CultureBoardComment SET likes=likes+1,likesStatus=TRUE WHERE id=?";
		jdbcTemplate.update(sql, commentId);
	}


	public void unlikePointForComment(Long commentId) {
		String sql = "UPDATE CultureBoardComment SET likes=likes-1,likesStatus=FALSE WHERE id=?";
		jdbcTemplate.update(sql, commentId);
	}


	public long findCommentLikesByCommentId(Long commentId) {
		String sql = "SELECT likes FROM CultureBoardComment WHERE id=?";
		return jdbcTemplate.queryForObject(sql, new RowMapper<Long>() {

			@Override
			public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getLong("likes");
			}
		},commentId);
	}

	/**
	 * 로그인한 멤버 아이디를 통해 추천누른 게시글의 id값 가져오기
	 * @param loginId
	 * @return
	 */
	

	public List<Long> findLikesContentIdByMemberId(Long loginId) {
		String sql = "SELECT boardId FROM LikeLogForCulture WHERE memberId=?";
		
		return jdbcTemplate.query(sql, new RowMapper<Long>() {

			@Override
			public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getLong("boardId");
			}
			
		},loginId);
	}	
	
	
	
	
	
	
	
	
	
	
	
	
	public void addLikePointForComment(Long loginId, Long commentId) {
		String sql = "INSERT INTO LikeLogComment(memberId, commentId) VALUES(?, ?)";
		jdbcTemplate.update(sql, loginId, commentId);
		
	}


	public void removeLikePointForComment(Long loginId, Long commentId) {
		String sql = "DELETE FROM LikeLogComment WHERE memberId=? AND commentId=?";
		jdbcTemplate.update(sql, loginId, commentId);
	}

	
	///////////////댓글 삭제기능 DAO //////////////

	public void deleteComment(Long commentId) {
		String sql = "DELETE FROM CultureBoardComment WHERE id=?";
		jdbcTemplate.update(sql, commentId);
	}


	public void discountCommentNum(Long boardId) {
		String sql = "UPDATE CultureBoard SET commentNum=commentNum-1 WHERE id=?";
		jdbcTemplate.update(sql, boardId);
	}

	public void removeLikeCommentPoint(long loginId, long commentId) {
		String sql = "DELETE FROM LikeLogComment WHERE memberId=? AND commentId=?";
		jdbcTemplate.update(sql, loginId, commentId);
	}


	/**
	 *  댓글많은 순 조회 DAO
	 * @param contentType
	 * @return
	 */
	
	public List<CultureBoard> findCommentOrderByType(String contentType) {
		String sql = "SELECT * FROM CultureBoard WHERE contentType=? ORDER BY commentNum DESC";
		return jdbcTemplate.query(sql, new RowMapper<CultureBoard>() {

			@Override
			public CultureBoard mapRow(ResultSet rs, int rowNum) throws SQLException {
				CultureBoard cb = null;
				Member member = null;
				member = findMemberByMemberId(rs.getLong("memberId"));
				SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				
				cb = new CultureBoard(rs.getLong("id"), member, rs.getString("contents01"), rs.getString("contents02"), rs.getString("contentType").charAt(0),
				rs.getString("title"), fmt.format(rs.getTimestamp("reportingDate")), rs.getInt("likes"), rs.getBoolean("likesStatus"),rs.getInt("commentNum"),rs.getString("link"));	
				return cb;
			}
		}, contentType);
	}


	public List<CultureBoard> findCommentsOrderByTypeMbti(String contentType, String mbtiValue) {
		String sql = "SELECT * FROM CultureBoard INNER JOIN MEMBER on MEMBER.id=CultureBoard.memberId WHERE mbti=? AND contentType=? ORDER BY Likes DESC";
		return jdbcTemplate.query(sql, new RowMapper<CultureBoard>() {

			@Override
			public CultureBoard mapRow(ResultSet rs, int rowNum) throws SQLException {
				CultureBoard cb = null;
				Member member = null;
				member = findMemberByMemberId(rs.getLong("memberId"));
				SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				
				cb = new CultureBoard(rs.getLong("id"), member, rs.getString("contents01"), rs.getString("contents02"), rs.getString("contentType").charAt(0),
				rs.getString("title"), fmt.format(rs.getTimestamp("reportingDate")), rs.getInt("likes"), rs.getBoolean("likesStatus"),rs.getInt("commentNum"),rs.getString("link"));	
				return cb;
			}
		}, contentType, mbtiValue);
	}

	
	/**
	 * 레벨 적용에 필요한 컨텐츠추가시 함수들
	 * 
	 * @param loginId
	 */
	
	//// 게시글 수 올리기
	public void addCultureContentsCount(Long loginId) {
		String sql = "UPDATE Member SET contentsCount=contentsCount+1 WHERE id=?";
		jdbcTemplate.update(sql, loginId);
	}
	
	
	// 맙 포인트 부여
	public void plusMab(Long memberId, int mabPoint) {
		String sql = "UPDATE Member SET mabPoint=? WHERE id=?";
		jdbcTemplate.update(sql, mabPoint, memberId);
	}
	
	// 계산한 맙포인트를 이용해서 레벨 업 부여
	public void resultLevel(Long memberId, int levelUp) {
		String sql = "UPDATE Member SET level=level+? WHERE id=?";
		jdbcTemplate.update(sql, levelUp, memberId);
		
	}

	//레벨 업 후 처음부터 다시 재적용 될 맙포인트
	public void levelUpAfterMab(long memberId, int mabPoint) {
		String sql = "UPDATE Member SET mabPoint=mabPoint-? WHERE id=?";
		
		jdbcTemplate.update(sql, mabPoint, memberId);
		
	}

	// 해당 멤버의 댓글 갯수 증가
	public void addCommentCount(Long loginId) {
		String sql = "UPDATE Member SET commentsCount=commentsCount+1 WHERE id=?";
		jdbcTemplate.update(sql, loginId);
	}

	// 해당 멤버의 댓글 갯수 감소
	public void deleteCommentsCount(Long loginId) {
		String sql = "UPDATE Member SET commentsCount=commentsCount-1 WHERE id=?";
		jdbcTemplate.update(sql, loginId);
	}


	public List<Long> findLikesCommentByMemberId(Long loginId) {
		String sql = "SELECT commentId FROM LikeLogComment WHERE memberId=?";
		
		return jdbcTemplate.query(sql, new RowMapper<Long>() {

			@Override
			public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getLong("commentId");
			}
			
		},loginId);
	}


	public CultureBoardComment findCultureBoardCommentByCommentId(Long likeCommentId) {
		String sql = "SELECT * FROM CultureBoardComment WHERE id=?";
		return jdbcTemplate.queryForObject(sql, new RowMapper<CultureBoardComment>() {

			@Override
			public CultureBoardComment mapRow(ResultSet rs, int rowNum) throws SQLException {
				SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Member member = null;
				CultureBoard cultureBoard = null;
				member = findMemberByMemberId(rs.getLong("memberId"));
				cultureBoard = findCultureBoardByBoardId(rs.getLong("boardId"));
				CultureBoardComment cbc = new CultureBoardComment(
						rs.getLong("id"),
						member, cultureBoard,
						rs.getString("comment"),
						rs.getInt("likes"),
						rs.getBoolean("likesStatus"),
						fmt.format(rs.getTimestamp("reportingDate"))
				);
				return cbc;
			}
		},likeCommentId);
	}



	
	
	
	
	
	
	
	
}
