package com.mycompany.myapp.mbtiPlay.command;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class SubjectiveCountCommand {
	private long count;
	
	public SubjectiveCountCommand() {}

	public SubjectiveCountCommand(long count) {
		super();
		this.count = count;
	}
	
}
