package com.javaex.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javaex.dao.BoardDao;
import com.javaex.vo.BoardVo;

@Service
public class BoardService {

	@Autowired
	private BoardDao boardDao;
	
	public Map<String, Object> getBoardList(int crtPage, String kwd){
		//게시글 리스트
		int listCnt = 10;
		
		crtPage = (crtPage <= 0) ? crtPage = 1 : crtPage; //실수,0들어오면 1번째 페이지보여줌
		/*if(crtPage < 0) {
			crtPage = 1;
		}*/
		
		int startRnum = (crtPage-1)*listCnt; //0, 10, 20 
		int endRnum = startRnum+listCnt; //10, 20, 30
		
		System.out.println("startRnum:"+startRnum);
		System.out.println("endRnum:"+endRnum);
		
		List<BoardVo> boardList = boardDao.selectBoardList(startRnum, endRnum, kwd);

		//페이지 버튼관련
		
		//전체 글 갯수
		int totalCount = boardDao.selectTotalCount(kwd);
		System.out.println("totalCount:"+totalCount);
		
		//페이지당 버튼 갯수
		int pageBtnCount = 5;
		
		int endPageBtnNo = (int)(Math.ceil(crtPage/(double)pageBtnCount)*pageBtnCount);
		int startPageBtnNo = endPageBtnNo - (pageBtnCount-1);
		
		//▶ 관련
		boolean next = false;
		if(endPageBtnNo * listCnt < totalCount) {
			next = true;
		} else {
			endPageBtnNo = (int)(Math.ceil(totalCount/(double)listCnt));
		}
		//◀관련
		boolean prev = false;
		if(startPageBtnNo !=1) {
			prev = true;
		}
		
		Map<String, Object> bMap = new HashMap<String, Object>();
		bMap.put("boardList", boardList);
		bMap.put("prev", prev);
		bMap.put("startPageBtnNo", startPageBtnNo);
		bMap.put("endPageBtnNo", endPageBtnNo);
		bMap.put("next", next);
		bMap.put("crtPage", crtPage);
		return bMap;
	}
	
	public int write(BoardVo boardVo) {
		
		/*for(int i = 1; i < 181; i++) {
			boardVo.setTitle(i+"번째 글입니다.");
			boardDao.insertBoard(boardVo);
		}*/
		return 1;
	}
	
	public int remove(BoardVo boardVo) {
		return boardDao.deleteBoard(boardVo);
	}
	
	@Transactional
	public BoardVo getBoardForRead(int no) {
		boardDao.updateHit(no);
		BoardVo boardVo = boardDao.selectBoard(no);
		return boardVo;
	}
	
	public BoardVo getBoardForModify(int no) {
		BoardVo boardVo = boardDao.selectBoard(no);
		return boardVo;
	}
	
	public int modifyBoard(BoardVo boardVo) {
		return boardDao.updateBoard(boardVo);
	}
}