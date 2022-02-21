package com.mycompany.myapp.domain;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class Member {
	private long id;
	private String email;
	private String pw;
	private String name;
	private String nickName;
	private String birth;
	private String mbti;
	private String gender;
	private String phone;
	private Date regDate;
	private int level;
	private int mabPoint;
	private String profileImg;
	private int contentsCount;
	private int commentsCount;
	
	
	public Member() {}

	public Member(long id, String email, String pw, String name, String nickName, String birth, String mbti,
			String gender, String phone, Date regDate, int level, int mabPoint, String profileImg, int contentsCount,
			int commentsCount) {
		super();
		this.id = id;
		this.email = email;
		this.pw = pw;
		this.name = name;
		this.nickName = nickName;
		this.birth = birth;
		this.mbti = mbti;
		this.gender = gender;
		this.phone = phone;
		this.regDate = regDate;
		this.level = level;
		this.mabPoint = mabPoint;
		this.profileImg = profileImg;
		this.contentsCount = contentsCount;
		this.commentsCount = commentsCount;
	}
	
	public Member(String email, String pw, String name, String nickName, String birth, String mbti,
			String gender, String phone, String profileImg) {
		super();
		this.email = email;
		this.pw = pw;
		this.name = name;
		this.nickName = nickName;
		this.birth = birth;
		this.mbti = mbti;
		this.gender = gender;
		this.phone = phone;
		this.profileImg = profileImg;
	}
	
	/***수민수민***/
	public Member(long id, String email, String pw, String name, String nickName, String birth, String mbti,
			String gender, String phone, Date regDate, int level, int mabPoint, String profileImg) {
		super();
		this.id = id;
		this.email = email;
		this.pw = pw;
		this.name = name;
		this.nickName = nickName;
		this.birth = birth;
		this.mbti = mbti;
		this.gender = gender;
		this.phone = phone;
		this.regDate = regDate;
		this.level = level;
		this.mabPoint = mabPoint;
		this.profileImg = profileImg;
	}
	
	
	public Member(long id, String email, String pw, String name, String nickName, String birth, String mbti,
			String gender, String phone, Date regDate, String profileImg) {
		super();
		this.id = id;
		this.email = email;
		this.pw = pw;
		this.name = name;
		this.nickName = nickName;
		this.birth = birth;
		this.mbti = mbti;
		this.gender = gender;
		this.phone = phone;
		this.regDate = regDate;
		this.profileImg = profileImg;
	}
	
	
	public Member(long id, String email, String pw, String name, String nickName, String birth, String mbti,
			String gender, String phone, Date regDate) {
		super();
		this.id = id;
		this.email = email;
		this.pw = pw;
		this.name = name;
		this.nickName = nickName;
		this.birth = birth;
		this.mbti = mbti;
		this.gender = gender;
		this.phone = phone;
		this.regDate = regDate;
	}
	
	
	public Member(String email, String pw, String name, String nickName, String birth, String mbti,
			String gender, String phone) {
		super();
		this.email = email;
		this.pw = pw;
		this.name = name;
		this.nickName = nickName;
		this.birth = birth;
		this.mbti = mbti;
		this.gender = gender;
		this.phone = phone;
	}
	

	public void calcLoginPoint() {
		this.mabPoint = this.mabPoint + 10;
	}
	
	public void calcContentsPoint() {
		this.mabPoint = this.mabPoint + 50;
	}
	
	public void calcCommentsPoint() {
		this.mabPoint = this.mabPoint + 30;
	}
	
	public void calcQuizPoint() {
		this.mabPoint = this.mabPoint + 30;
	}
	
	public void calcBestPoint() {
		this.mabPoint = this.mabPoint + 500;
	}

	

}
