package com.mycompany.myapp.mbtiPlay.command;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@ToString
@Getter
@Setter
public class MbtiPlayContentsAnswerCommand {
	private long id;
	private String memberMbti;
	private String isSubjective;
	private String subjectiveContent;
	
	public MbtiPlayContentsAnswerCommand() {}

	public MbtiPlayContentsAnswerCommand(long id, String memberMbti, String isSubjective, String subjectiveContent) {
		this.id = id;
		this.memberMbti = memberMbti;
		this.isSubjective = isSubjective;
		this.subjectiveContent = subjectiveContent;
	}

	public MbtiPlayContentsAnswerCommand(long id) {
		this.id = id;
	}

	
}
