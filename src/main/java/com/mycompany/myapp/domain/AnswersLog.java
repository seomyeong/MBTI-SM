package com.mycompany.myapp.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class AnswersLog {
	private long id;
	private long memberId;
	private long contentsNum;
	
	public AnswersLog() {}
	
	public AnswersLog(long id, long memberId, long contentsNum) {
		this.id = id;
		this.memberId = memberId;
		this.contentsNum = contentsNum;
	}

	public AnswersLog(long id) {
		this.id=id;
	}
}
