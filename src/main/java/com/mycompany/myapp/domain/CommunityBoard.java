package com.mycompany.myapp.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class CommunityBoard {
	private Long id;
	private Member member;
	private String title;
	private String contents;
	private String reportingDate;
	private int views;
	private int likes;
	private int commentsCount;
	
	public CommunityBoard(){}

	public CommunityBoard(String title, String contents, String reportingDate, int views,
			int likes, int commentsCount) {
		super();
		this.title = title;
		this.contents = contents;
		this.reportingDate = reportingDate;
		this.views = views;
		this.likes = likes;
		this.commentsCount = commentsCount;
	}

	public CommunityBoard(Long id, String title, String contents, String reportingDate, int views,
			int likes, int commentsCount) {
		super();
		this.id = id;
		this.title = title;
		this.contents = contents;
		this.reportingDate = reportingDate;
		this.views = views;
		this.likes = likes;
		this.commentsCount = commentsCount;
	}
	
	
}
