package com.mycompany.myapp.mbtiPlay.service;

import com.mycompany.myapp.domain.MbtiPlayContents;

public interface MbtiPlayMakeContentsService {
	//문답만들기 test
//	public void addContents(MbtiPlayContents con);
	
	//문답만들기
	public void addContents(long memberId, MbtiPlayContents con);
	
}
