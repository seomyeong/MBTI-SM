package com.mycompany.myapp.domain;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class ContentsLog {
	private long id;
	private long memberId;
	private int contentsCount;
	private Date regDate;

	public ContentsLog() {}
	
	public ContentsLog(long id, long memberId, int contentsCount, Date regDate) {
		this.id = id;
		this.memberId = memberId;
		this.contentsCount = contentsCount;
		this.regDate = regDate;
	}

	public ContentsLog(Date regDate) {
		super();
		this.regDate = regDate;
	}
}
