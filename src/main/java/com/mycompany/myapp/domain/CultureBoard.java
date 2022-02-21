package com.mycompany.myapp.domain;

import java.util.Date;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter 
public class CultureBoard {
	private long id;
	private Member member;
	private String contents01;
	private String contents02;
	private char contentType;
	private String title;
	private String reportingDate;
	private int likes;
	private boolean likesStatus;
	private int commentNum;
	private String link;
	
	
	
	
	
	//FOR Comment
	public CultureBoard(long id, Member member, String contents01, String contents02, char contentType, String title,
			String reportingDate, int likes, boolean likesStatus, int commentNum,String link) {
		super();
		this.id = id;
		this.member = member;
		this.contents01 = contents01;
		this.contents02 = contents02;
		this.contentType = contentType;
		this.title = title;
		this.reportingDate = reportingDate;
		this.likes = likes;
		this.likesStatus = likesStatus;
		this.commentNum = commentNum;
		this.link = link;
	}
}


