package com.mycompany.myapp.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LikeLogComment {
	private Long memberId;
	private Long commentId;
	
	public LikeLogComment() {}

	public LikeLogComment(Long memberId, Long commentId) {
		super();
		this.memberId = memberId;
		this.commentId = commentId;
	}
}
