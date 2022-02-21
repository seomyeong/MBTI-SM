package com.mycompany.myapp.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class MbtiComments {
	private long id;
	private Member member;
	private String type01;
	private String type02;
	private String comment;
	private String reportingDate;
	
	public MbtiComments() {}
	
	public MbtiComments(Member member, String type01, String type02, String comment, String reportingDate) {
		super();
		this.member = member;
		this.type01 = type01;
		this.type02 = type02;
		this.comment = comment;
		this.reportingDate = reportingDate;
	}
	
}
