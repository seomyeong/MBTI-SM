package com.mycompany.myapp.mbtiPlay.command;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class ContentsLogCommand {
	private String year;
	private String month;
	private String day;
	
	public ContentsLogCommand() {}

	public ContentsLogCommand(String year, String month, String day) {
		super();
		this.year = year;
		this.month = month;
		this.day = day;
	}
}
