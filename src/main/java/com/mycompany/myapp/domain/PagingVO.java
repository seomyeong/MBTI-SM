package com.mycompany.myapp.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PagingVO {
	
	private int listSize = 10; // 초기값으로 목록개수를 10으로 셋팅
	private int rangeSize = 10; // 초기값으로 페이지범위를 10으로 셋팅

	private int page; // 현재 페이지
	private int range; // 현재 페이지 범위
	private int listCnt; // 총 게시물의 개수
	private int pageCnt; // 총 페이지의 개수
	private int startPage; // 각 페이지 범위 시작번호
	private int startList; // 게시판 시작번호
	private int endPage; // 각 페이지 범위 끝번호
	
	private int pageListSize; // 마지막 페이지가 10개가 아닐 경우 대비
	private int pageCnt_unCeil; // 총 페이지의 개수를 내림 한 것

	private boolean prev; // 이전 버튼 상태
	private boolean next; // 다음 버튼 상태
	

	public void pageInfo(int page, int range, int listCnt) {

		this.page = page;
		this.range = range;
		this.listCnt = listCnt;

		//전체 페이지수 
		this.pageCnt = (int) Math.ceil(((listCnt + 9) / 10 * 10)/listSize);
		this.pageCnt_unCeil = (int) Math.floor(listCnt/listSize);
		
		//시작 페이지 1 11 21 31
		this.startPage = (range - 1) * rangeSize + 1 ;

		//끝 페이지 10 20 30 40
		this.endPage = range * rangeSize;

		//게시판 시작번호 10 20 30 40
		this.startList = (page - 1) * listSize;
		
		// 마지막 페이지 10개가 아닐경우 대비
		this.pageListSize = this.listCnt - this.startList > this.listSize ? this.listSize : this.listCnt - this.startList;

		//이전 버튼 상태
		this.prev = range == 1 ? false : true;

		//다음 버튼 상태
//		this.next = endPage > pageCnt ? false : true;
//		this.next = (listCnt / rangeSize) + rangeSize >= range * rangeSize ? true : false;
		this.next = range * this.rangeSize <= Math.ceil(listCnt / this.rangeSize) ? true : false;
		// 30 < 28 ? true : false;
		
		if (this.endPage > this.pageCnt) {
			this.endPage = this.pageCnt;
			this.next = false;
		}
	}
	
	public PagingVO() {}
}
