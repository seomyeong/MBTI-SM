package com.mycompany.myapp.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class MbtiPlayContents {
	private long id;
	private Member member;
	private String question;
	private String answer01;
	private String answer02;
	private String answer03;
	
	public MbtiPlayContents() {
		
	}

	//findContentsPk
	public MbtiPlayContents(long id) {
		this.id = id;
	}

	//findQuestionByRandomNum
	public MbtiPlayContents(long id, String question, String answer01, String answer02,
			String answer03) {
		this.id = id;
		this.question = question;
		this.answer01 = answer01;
		this.answer02 = answer02;
		this.answer03 = answer03;
	}

}
