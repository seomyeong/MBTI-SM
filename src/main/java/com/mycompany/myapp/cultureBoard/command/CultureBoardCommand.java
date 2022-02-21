package com.mycompany.myapp.cultureBoard.command;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@ToString
@Getter
@Setter
public class CultureBoardCommand {
	
	
	private String contents01;
	private String contents02;
	
	private String contentType; // char 형태로 변환 필요
	
	private String title;
	private String link;
	
	public CultureBoardCommand(String contents01, String contents02, String contentType, String title, String link) {
		super();
		this.contents01 = contents01;
		this.contents02 = contents02;
		this.contentType = contentType;
		this.title = title;
		this.link = link;
	}
	
	
	
}
