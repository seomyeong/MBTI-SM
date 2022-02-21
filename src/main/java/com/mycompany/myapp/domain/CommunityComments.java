package com.mycompany.myapp.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class CommunityComments {
	private long id;
	private Member member;
	private String comments;
	private int likes;
	private String reportingDate;
	
	public CommunityComments() {}
	
	public CommunityComments(long id, Member member, String comments, int likes, String reportingDate) {
		super();
		this.id = id;
		this.member = member;
		this.comments = comments;
		this.likes = likes;
		this.reportingDate = reportingDate;
	}
	
}
