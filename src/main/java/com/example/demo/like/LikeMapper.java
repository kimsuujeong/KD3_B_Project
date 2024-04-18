package com.example.demo.like;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class LikeMapper {

	@Autowired
	SqlSession sqlSession;
	
	// 유저아이디별 관심 목록
	public List<Like> list(String userID) {
		// TODO Auto-generated method stub
		return sqlSession.selectList("like.list", userID);
	}
	
	// 관심목록 삽입
	public void insert(Like like) {
		// TODO Auto-generated method stub
		sqlSession.insert("like.insert", like);
	}

	// 관심 삭제
	public void delete(Integer likeID) {
		// TODO Auto-generated method stub
		sqlSession.delete("like.deletelike", likeID);
	}

	// 관심해놨는지 확인
	public int countLike(Integer postID, String userID) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userID", userID);
		map.put("postID", postID);
		return sqlSession.selectOne("like.countLike", map);
	}
}
