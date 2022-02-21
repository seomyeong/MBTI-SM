package com.mycompany.myapp.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LikeLog {
	private Long memberId;
	private Long boardId;
	
	public LikeLog() {}
	
	public LikeLog(Long memberId, Long boardId) {
		super();
		this.memberId = memberId;
		this.boardId = boardId;
	}
	
}
