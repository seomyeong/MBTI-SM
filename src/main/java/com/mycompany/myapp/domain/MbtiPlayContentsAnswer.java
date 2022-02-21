package com.mycompany.myapp.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class MbtiPlayContentsAnswer{
	
	private long id;
	private Member member;
	private MbtiPlayContents mbtiPlayContents;
	private String memberMbti;
	private long questionNum;
	private String choosenNum;
	private String isSubjective;
	private String subjectiveContent;
	private int choosenNumCount;
	
	public MbtiPlayContentsAnswer() {
		
	}
	
	public MbtiPlayContentsAnswer(long id) {
		this.id = id;
	}
	
	public MbtiPlayContentsAnswer(String memberMbti, String subjectiveContent) {
		this.memberMbti=memberMbti;
		this.subjectiveContent=subjectiveContent;
	}

	public MbtiPlayContentsAnswer(long id, String memberMbti,
			long questionNum, String choosenNum, String isSubjective, String subjectiveContent, int choosenNumCount) {
		
		this.id = id;
		this.memberMbti = memberMbti;
		this.questionNum = questionNum;
		this.choosenNum = choosenNum;
		this.isSubjective = isSubjective;
		this.subjectiveContent = subjectiveContent;
		this.choosenNumCount = choosenNumCount;
	}
	
}
