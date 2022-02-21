package com.mycompany.myapp.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class MbtiMatch {
	private long id;
	private String type01;
	private String type02;
	private int result;
	
	public MbtiMatch() {}

	public MbtiMatch(int result) {
		super();
		this.result = result;
	}
	
	
}
