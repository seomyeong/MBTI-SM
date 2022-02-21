package com.mycompany.myapp.member.command;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class LoginLogCommand {
	private String year;
	private String month;
	private String day;
	
	public LoginLogCommand() {}

	public LoginLogCommand(String year, String month, String day) {
		super();
		this.year = year;
		this.month = month;
		this.day = day;
	}
}