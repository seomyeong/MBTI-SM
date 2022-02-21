package com.mycompany.myapp.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CommunityCommentsPlus {
	private long id;
	private CommunityBoard communityBoard;
	private CommunityComments communityComments;
	private Member member;
	private String comments;
	private int likes;
	private String reportingDate;
	
	public CommunityCommentsPlus() {}

	public CommunityCommentsPlus(long id, CommunityBoard communityBoard, CommunityComments communityComments,
			Member member, String comments, int likes, String reportingDate) {
		super();
		this.id = id;
		this.communityBoard = communityBoard;
		this.communityComments = communityComments;
		this.member = member;
		this.comments = comments;
		this.likes = likes;
		this.reportingDate = reportingDate;
	}
	
	
}
