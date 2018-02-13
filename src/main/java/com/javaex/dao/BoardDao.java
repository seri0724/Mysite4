package com.javaex.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.BoardVo;

@Repository
public class BoardDao {

	@Autowired
	private SqlSession sqlSession;

	public int selectTotalCount(String kwd) {
		return sqlSession.selectOne("board.totalCount", kwd);
	}
	
	public List<BoardVo> selectBoardList(int startRnum, int endRnum, String kwd) {
		Map<String, Object> mapCri = new HashMap<String, Object>();
		mapCri.put("startRnum", startRnum);
		mapCri.put("endRnum", endRnum);
		mapCri.put("kwd", kwd);
		System.out.println("dao:"+mapCri.toString());
		return sqlSession.selectList("board.selectList", mapCri);
	}
	
	public int insertBoard(BoardVo boardVo) {
		return sqlSession.insert("board.insert", boardVo);
	}

	public int deleteBoard(BoardVo boardVo) {
		return sqlSession.delete("board.delete", boardVo);
	}
	
	public BoardVo selectBoard(int no) {
		return sqlSession.selectOne("board.selctBoard", no);
	}
	
	public int updateBoard(BoardVo boardVo) {
		return sqlSession.update("board.update", boardVo);
	}
	
	public int updateHit(int no) {
		return sqlSession.update("board.updateHit", no);
	}
}