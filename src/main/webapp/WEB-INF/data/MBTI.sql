CREATE TABLE MEMBER(
	id				BIGINT		PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
	email 			VARCHAR(40)	NOT NULL,
	pw 				VARCHAR(20)	NOT NULL,
	name 			VARCHAR(30)	NOT NULL,
	nickName 		VARCHAR(10)	NOT NULL,
	birth 			VARCHAR(20) NOT NULL,
	mbti 			VARCHAR(4)	NOT NULL,
	gender 			CHAR(1)		NOT NULL,
	phone 			VARCHAR(13) NOT NULL,
	regDate 		TIMESTAMP 	NOT NULL DEFAULT CURRENT_TIMESTAMP,
	level 			INT 		DEFAULT 1,
	mabPoint 		INT 		DEFAULT 0,
	profileImg 		VARCHAR(100) DEFAULT '',
	contentsCount 	INT DEFAULT 0,
	commentsCount 	INT DEFAULT 0
);

CREATE TABLE LoginLog(
	id					BIGINT			PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
	memberId			BIGINT			NOT NULL,
	regDate 			TIMESTAMP 		NOT NULL DEFAULT CURRENT_TIMESTAMP,
	CONSTRAINT LoginLog_memberId_FK FOREIGN KEY(memberId) REFERENCES Member(id)
);
-- Community

CREATE TABLE CommunityBoard(
	id				BIGINT			PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
	memberId		BIGINT			NOT NULL,
	title			VARCHAR(100)	NOT NULL,
	contents		VARCHAR(1000)	NOT NULL,
	reportingDate	TIMESTAMP		DEFAULT CURRENT_TIMESTAMP,
	views			INT				DEFAULT 0,
	likes			INT				DEFAULT 0,
	commentsCount 	INT				DEFAULT 0,
	CONSTRAINT Member_memberId_FK FOREIGN KEY(memberId) REFERENCES Member(id) ON DELETE CASCADE
);

CREATE TABLE CommunityComments(
	id				BIGINT			PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
	boardId			BIGINT			NOT NULL,
	memberId		BIGINT			NOT NULL,
	comments		VARCHAR(200)	NOT NULL,
	likes			INT				DEFAULT 0,
	reportingDate	TIMESTAMP		DEFAULT CURRENT_TIMESTAMP,
	CONSTRAINT CommunityBoard_boardId_FK FOREIGN KEY(boardId) REFERENCES CommunityBoard(id) ON DELETE CASCADE
);

CREATE TABLE CommunityComments_plus(
	id				BIGINT			PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
	boardId			BIGINT			NOT NULL,
	commentId		BIGINT			NOT NULL,
	memberId		BIGINT			NOT NULL,
	comments		VARCHAR(200)	NOT NULL,
	likes			INT				DEFAULT 0,
	reportingDate	TIMESTAMP		DEFAULT CURRENT_TIMESTAMP,
	CONSTRAINT CommunityComments_commentId_FK FOREIGN KEY(commentId) REFERENCES CommunityComments(id) ON DELETE CASCADE
);

-- 추천 로그관리

CREATE TABLE LikeLog(
	boardId			BIGINT			NOT NULL,
	memberId		BIGINT			NOT NULL
);



--MbtiPlay
SELECT * FROM MbtiPlayContents;
SELECT * FROM MbtiPlayContentsAnswer;
SELECT * FROM ContentsLog;
SELECT * FROM AnswersLog;

DROP TABLE MbtiPlayContents;
DROP TABLE MbtiPlayContentsAnswer;
DROP TABLE ContentsLog;
DROP TABLE AnswersLog;

--문답 쿼리
INSERT INTO MbtiPlayContents (memberId, question, answer01, answer02, answer03) VALUES (1, '친구들과 놀이동산을 간 당신, 놀이기구를 탈 때의 나는?', '오늘 이거 다섯 개는 꼭 타야 돼! 지도를 보며 계획을 세운다.', '오, 저거 재밌어 보인다! 일단 보이는 것부터 탄다.', '놀이기구는 흥미가 들지 않아 이끌려다닌다.');
INSERT INTO MbtiPlayContents (memberId, question, answer01, answer02, answer03) VALUES (1, '소개팅을 나간 당신, 첫 만남에 어색할 때','준비해온 멘트들을 건네며 대화에 시동을 건다.','미소만 지으며 상대가 말할 때까지 기다린다.','즉흥적으로 대화를 진행하다 집에가서 후회한다.');
INSERT INTO MbtiPlayContents (memberId, question, answer01, answer02, answer03) VALUES (1, '연인이 길을 가다가 누군가와 부딪혔다. 그때의 나는?','저기요! 사과하셔야죠! 일단 따지고 본다.','괜찮아? 다친데 없어? 연인을 위로한다.','그럴수도 있지. 대수롭지 않게 넘긴다.');
INSERT INTO MbtiPlayContents (memberId, question, answer01, answer02, answer03) VALUES (1, '친구한테 지금 내가 있는 카페 위치를 알려줄 때','5번 출구로 나와서 두번째 블록에서 좌회전 하면 나오는 사거리 근처야','거기 그 햄버거집 알지? 그 앞이야','지도보고 알아서 찾아와!');

CREATE TABLE MbtiPlayContents(
	id				BIGINT			PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
	memberId		BIGINT			NOT NULL, --멤버FK
	question		VARCHAR(500)	NOT NULL,
	answer01		VARCHAR(100)	NOT NULL,
	answer02		VARCHAR(100)	NOT NULL,
	answer03		VARCHAR(100)	NOT NULL,
	CONSTRAINT memberId_FK FOREIGN KEY(memberId) REFERENCES Member(id)
);

CREATE TABLE MbtiPlayContentsAnswer(
	id					BIGINT			PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
	memberMbti			VARCHAR(4)		NOT NULL, --회원의 MBTI
	questionNum			BIGINT			NOT NULL, --문제번호 FK
	choosenNum			CHAR(1)			NOT NULL, --선지번호
	isSubjective		VARCHAR(10)		NOT NULL, --주관식여부
	subjectiveContent	VARCHAR(100)	NOT NULL DEFAULT '', --주관식내용
	choosenNumCount		INT				NOT NULL DEFAULT 0,  --선지번호 선택건수
	CONSTRAINT questionNum_FK FOREIGN KEY(questionNum) REFERENCES MbtiPlayContents(id)
);

CREATE TABLE ContentsLog(
	id					BIGINT			PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
	memberId			BIGINT			NOT NULL,
	contentsCount		INT				DEFAULT 0,
	regDate 			TIMESTAMP 		NOT NULL DEFAULT CURRENT_TIMESTAMP,
	CONSTRAINT ContentsLog_memberId_FK FOREIGN KEY(memberId) REFERENCES Member(id)
);

CREATE TABLE AnswersLog(
	id					BIGINT			PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
	memberId			BIGINT			NOT NULL,
	contentsNum			BIGINT			NOT NULL,
	CONSTRAINT AnswersLog_memberId_FK FOREIGN KEY(memberId) REFERENCES Member(id),
	CONSTRAINT AnswersLog_contentsNum_FK FOREIGN KEY(contentsNum) REFERENCES MbtiPlayContents(id)
);



---수민 cultureBoard 파트 테이블------

CREATE TABLE CultureBoard(
	id				BIGINT			PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
	memberId 		BIGINT			NOT NULL,
	title			VARCHAR(50) 	NOT NULL,
	contents01		VARCHAR(50)		NOT NULL,
	contents02		VARCHAR(50)		DEFAULT '' ,
	contentType		CHAR			NOT NULL,
	likes			INT				DEFAULT 0,
	likesStatus		BOOLEAN			DEFAULT FALSE,
	link			VARCHAR(500)	NOT NULL,
	commentNum		INT				DEFAULT 0,
	reportingDate	TIMESTAMP		NOT NULL DEFAULT CURRENT_TIMESTAMP,
	
	CONSTRAINT CultureBoard_memberId_FK FOREIGN KEY(memberId) REFERENCES MEMBER(id)
);

SELECT * FROM CULTUREBOARDCOMMENT;

CREATE TABLE CultureBoardComment(
	id				BIGINT			PRIMARY KEY	GENERATED ALWAYS AS IDENTITY,
	memberId		BIGINT			NOT NULL,
	boardId			BIGINT			NOT NULL,
	comment			VARCHAR(500)	NOT NULL,
	likes			INT				DEFAULT 0,
	likesStatus		BOOLEAN			DEFAULT FALSE,
	reportingDate	TIMESTAMP		DEFAULT CURRENT_TIMESTAMP,
	
	CONSTRAINT CultureBoardComment_boardId_FK FOREIGN KEY(boardId) REFERENCES CultureBoard(id) ON DELETE CASCADE
);

SELECT * FROM LIKELOGFORCULTURE;
CREATE TABLE LikeLogForCulture(
	boardId			BIGINT			NOT NULL,
	memberId		BIGINT			NOT NULL
);

SELECT * FROM LikeLogComment;
CREATE TABLE LikeLogComment(
	memberId		BIGINT			NOT NULL,
	commentId		BIGINT			NOT NULL
);
DELETE FROM LIKELOGCOMMENT;
INSERT INTO LikeLogComment (memberId, commentId) VALUES (1, 76);

---- mbtiMatch ----

INSERT INTO MbtiMatch(type01, type02, result) VALUES('ENTJ', 'ENTJ', 1);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ENTJ', 'ENTP', 2);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ENTJ', 'INTJ', 3);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ENTJ', 'INTP', 3);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ENTJ', 'ESTJ', 2);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ENTJ', 'ESFJ', 1);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ENTJ', 'ISTJ', 2);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ENTJ', 'ISFJ', 1);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ENTJ', 'ENFJ', 2);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ENTJ', 'ENFP', 3);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ENTJ', 'INFJ', 3);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ENTJ', 'INFP', 5);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ENTJ', 'ESTP', 4);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ENTJ', 'ESFP', 4);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ENTJ', 'ISTP', 3);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ENTJ', 'ISFP', 5);

INSERT INTO MbtiMatch(type01, type02, result) VALUES('ENTP', 'ENTP', 4);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ENTP', 'INTJ', 3);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ENTP', 'INTP', 3);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ENTP', 'ESTJ', 4);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ENTP', 'ESFJ', 4);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ENTP', 'ISTJ', 5);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ENTP', 'ISFJ', 5);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ENTP', 'ENFJ', 3);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ENTP', 'ENFP', 2);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ENTP', 'INFJ', 3);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ENTP', 'INFP', 3);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ENTP', 'ESTP', 2);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ENTP', 'ESFP', 1);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ENTP', 'ISTP', 2);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ENTP', 'ISFP', 1);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ENTP', 'ENTJ', 1);

INSERT INTO MbtiMatch(type01, type02, result) VALUES('INTJ', 'INTJ', 3);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('INTJ', 'INTP', 2);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('INTJ', 'ESTJ', 2);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('INTJ', 'ESFJ', 1);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('INTJ', 'ISTJ', 3);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('INTJ', 'ISFJ', 1);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('INTJ', 'ENFJ', 3);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('INTJ', 'ENFP', 3);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('INTJ', 'INFJ', 3);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('INTJ', 'INFP', 4);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('INTJ', 'ENTJ', 3);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('INTJ', 'ENTP', 3);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('INTJ', 'ESTP', 5);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('INTJ', 'ESFP', 5);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('INTJ', 'ISTP', 3);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('INTJ', 'ISFP', 4);

INSERT INTO MbtiMatch(type01, type02, result) VALUES('INTP', 'INTP', 3);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('INTP', 'ESTJ', 4);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('INTP', 'ESFJ', 5);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('INTP', 'ISTJ', 3);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('INTP', 'ISFJ', 5);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('INTP', 'ENFJ', 5);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('INTP', 'ENFP', 3);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('INTP', 'INFJ', 4);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('INTP', 'INFP', 2);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('INTP', 'ENTJ', 3);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('INTP', 'ENTP', 3);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('INTP', 'INTJ', 2);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('INTP', 'ESTP', 2);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('INTP', 'ESFP', 1);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('INTP', 'ISTP', 2);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('INTP', 'ISFP', 1);

INSERT INTO MbtiMatch(type01, type02, result) VALUES('ENFJ', 'ESTP', 4);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ENFJ', 'ESFP', 4);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ENFJ', 'ISTP', 5);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ENFJ', 'ISFP', 3);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ENFJ', 'ENTJ', 2);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ENFJ', 'ENTP', 3);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ENFJ', 'INTJ', 3);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ENFJ', 'INTP', 5);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ENFJ', 'ESTJ', 1);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ENFJ', 'ESFJ', 3);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ENFJ', 'ISTJ', 1);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ENFJ', 'ISFJ', 2);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ENFJ', 'ENFJ', 4);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ENFJ', 'ENFP', 2);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ENFJ', 'INFJ', 3);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ENFJ', 'INFP', 3);

INSERT INTO MbtiMatch(type01, type02, result) VALUES('ENFP', 'ESTP', 1);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ENFP', 'ESFP', 1);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ENFP', 'ISTP', 1);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ENFP', 'ISFP', 3);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ENFP', 'ENTJ', 3);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ENFP', 'ENTP', 2);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ENFP', 'INTJ', 3);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ENFP', 'INTP', 3);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ENFP', 'ESTJ', 4);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ENFP', 'ESFJ', 4);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ENFP', 'ISTJ', 5);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ENFP', 'ISFJ', 5);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ENFP', 'ENFJ', 3);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ENFP', 'INFJ', 3);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ENFP', 'INFP', 2);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ENFP', 'ENFP', 2);

INSERT INTO MbtiMatch(type01, type02, result) VALUES('INFJ', 'ESTP', 5);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('INFJ', 'ESFP', 5);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('INFJ', 'ISTP', 5);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('INFJ', 'ISFP', 3);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('INFJ', 'ESTJ', 1);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('INFJ', 'ENTP', 3);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('INFJ', 'ENTJ', 3);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('INFJ', 'INTJ', 3);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('INFJ', 'INTP', 4);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('INFJ', 'ESFJ', 2);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('INFJ', 'ISTJ', 1);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('INFJ', 'ISFJ', 2);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('INFJ', 'ENFJ', 3);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('INFJ', 'ENFP', 3);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('INFJ', 'INFJ', 3);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('INFJ', 'INFP', 1);

INSERT INTO MbtiMatch(type01, type02, result) VALUES('INFP', 'ESTP', 1);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('INFP', 'ESFP', 2);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('INFP', 'ISTP', 1);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('INFP', 'ISFP', 2);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('INFP', 'ENTJ', 5);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('INFP', 'ENTP', 3);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('INFP', 'INTJ', 4);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('INFP', 'INTP', 2);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('INFP', 'ESTJ', 5);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('INFP', 'ESFJ', 3);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('INFP', 'ISTJ', 4);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('INFP', 'ISFJ', 3);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('INFP', 'ENFJ', 3);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('INFP', 'ENFP', 2);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('INFP', 'INFJ', 1);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('INFP', 'INFP', 3);

INSERT INTO MbtiMatch(type01, type02, result) VALUES('ESTP', 'ESTP', 3);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ESTP', 'ESFP', 2);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ESTP', 'ISTP', 3);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ESTP', 'ISFP', 3);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ESTP', 'ENTJ', 4);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ESTP', 'ENTP', 2);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ESTP', 'INTJ', 5);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ESTP', 'INTP', 2);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ESTP', 'ESTJ', 1);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ESTP', 'ESFJ', 3);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ESTP', 'ISTJ', 3);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ESTP', 'ISFJ', 3);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ESTP', 'ENFJ', 4);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ESTP', 'ENFP', 1);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ESTP', 'INFJ', 5);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ESTP', 'INFP', 1);

INSERT INTO MbtiMatch(type01, type02, result) VALUES('ESFP', 'ESFP', 2);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ESFP', 'ISTP', 3);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ESFP', 'ISFP', 3);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ESFP', 'ENTJ', 4);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ESFP', 'ENTP', 1);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ESFP', 'INTJ', 5);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ESFP', 'INTP', 1);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ESFP', 'ESTJ', 3);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ESFP', 'ESFJ', 2);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ESFP', 'ISTJ', 3);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ESFP', 'ISFJ', 3);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ESFP', 'ENFJ', 4);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ESFP', 'ENFP', 1);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ESFP', 'INFJ', 5);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ESFP', 'INFP', 2);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ESFP', 'ESTP', 2);

INSERT INTO MbtiMatch(type01, type02, result) VALUES('ISTP', 'ISTP', 2);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ISTP', 'ISFP', 2);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ISTP', 'ENTJ', 3);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ISTP', 'ENTP', 2);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ISTP', 'INTJ', 3);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ISTP', 'INTP', 2);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ISTP', 'ESTJ', 3);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ISTP', 'ESFJ', 5);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ISTP', 'ISTJ', 2);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ISTP', 'ISFJ', 4);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ISTP', 'ENFJ', 5);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ISTP', 'ENFP', 1);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ISTP', 'INFJ', 5);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ISTP', 'INFP', 1);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ISTP', 'ESTP', 3);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ISTP', 'ESFP', 3);

INSERT INTO MbtiMatch(type01, type02, result) VALUES('ISFP', 'ISFP', 3);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ISFP', 'ENTJ', 5);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ISFP', 'ENTP', 1);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ISFP', 'INTJ', 4);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ISFP', 'INTP', 1);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ISFP', 'ESTJ', 5);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ISFP', 'ESFJ', 3);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ISFP', 'ISTJ', 4);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ISFP', 'ISFJ', 1);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ISFP', 'ENFJ', 3);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ISFP', 'ENFP', 3);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ISFP', 'INFJ', 3);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ISFP', 'INFP', 2);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ISFP', 'ESTP', 3);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ISFP', 'ESFP', 3);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ISFP', 'ISTP', 2);

INSERT INTO MbtiMatch(type01, type02, result) VALUES('ESTJ', 'ESTJ', 3);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ESTJ', 'ESFJ', 2);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ESTJ', 'ISTJ', 3);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ESTJ', 'ISFJ', 3);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ESTJ', 'ESTP', 1);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ESTJ', 'ESFP', 3);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ESTJ', 'ISTP', 3);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ESTJ', 'ISFP', 5);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ESTJ', 'ENTJ', 2);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ESTJ', 'ENTP', 4);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ESTJ', 'INTJ', 2);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ESTJ', 'INTP', 4);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ESTJ', 'ENFJ', 1);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ESTJ', 'ENFP', 4);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ESTJ', 'INFJ', 1);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ESTJ', 'INFP', 5);

INSERT INTO MbtiMatch(type01, type02, result) VALUES('ESFJ', 'ESFJ', 3);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ESFJ', 'ISTJ', 3);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ESFJ', 'ISFJ', 3);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ESFJ', 'ENFJ', 3);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ESFJ', 'ENFP', 4);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ESFJ', 'INFJ', 2);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ESFJ', 'INFP', 3);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ESFJ', 'ESTP', 3);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ESFJ', 'ESFP', 2);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ESFJ', 'ISTP', 5);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ESFJ', 'ISFP', 3);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ESFJ', 'ENTJ', 1);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ESFJ', 'ENTP', 4);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ESFJ', 'INTJ', 1);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ESFJ', 'INTP', 5);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ESFJ', 'ESTJ', 2);

INSERT INTO MbtiMatch(type01, type02, result) VALUES('ISTJ', 'ISTJ', 3);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ISTJ', 'ISFJ', 2);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ISTJ', 'ENFJ', 1);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ISTJ', 'ENFP', 5);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ISTJ', 'INFJ', 1);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ISTJ', 'INFP', 4);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ISTJ', 'ESTP', 3);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ISTJ', 'ESFP', 3);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ISTJ', 'ISTP', 2);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ISTJ', 'ISFP', 4);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ISTJ', 'ENTJ', 2);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ISTJ', 'ENTP', 5);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ISTJ', 'INTJ', 3);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ISTJ', 'INTP', 3);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ISTJ', 'ESTJ', 3);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ISTJ', 'ESFJ', 3);

INSERT INTO MbtiMatch(type01, type02, result) VALUES('ISFJ', 'ISFJ', 2);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ISFJ', 'ENFJ', 2);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ISFJ', 'ENFP', 5);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ISFJ', 'INFJ', 2);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ISFJ', 'INFP', 3);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ISFJ', 'ESTP', 3);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ISFJ', 'ESFP', 3);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ISFJ', 'ISTP', 4);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ISFJ', 'ISFP', 1);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ISFJ', 'ENTJ', 1);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ISFJ', 'ENTP', 5);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ISFJ', 'INTJ', 1);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ISFJ', 'INTP', 5);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ISFJ', 'ESTJ', 3);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ISFJ', 'ESFJ', 3);
INSERT INTO MbtiMatch(type01, type02, result) VALUES('ISFJ', 'ISTJ', 2);

CREATE TABLE MbtiMatch(
	id			BIGINT		PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
	type01		VARCHAR(4)	NOT NULL,
	type02		VARCHAR(4)	NOT NULL,
	result		INT			NOT NULL
);

CREATE TABLE MbtiComments(
	id				BIGINT			PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
	memberId		BIGINT			NOT NULL,
	type01			VARCHAR(4)		NOT NULL,
	type02			VARCHAR(4)		NOT NULL,
	comment			VARCHAR(100)	NOT NULL,
	reportingDate	TIMESTAMP		NOT NULL DEFAULT CURRENT_TIMESTAMP,
	CONSTRAINT MbtiComments_memberId_FK FOREIGN KEY(memberId) REFERENCES Member(id) ON DELETE CASCADE
);
