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
	
	public List<Like> list(String userID) {
		// TODO Auto-generated method stub
		return sqlSession.selectList("like.list", userID);
	}

	public void insert(Like like) {
		// TODO Auto-generated method stub
		sqlSession.insert("like.insert", like);
	}

	public void delete(Integer likeID) {
		// TODO Auto-generated method stub
		sqlSession.delete("like.deletelike", likeID);
	}

	public int countLike(Integer postID, String userID) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("postID", postID);
		map.put("userID", userID);
		return sqlSession.selectOne("like.countLike", map);
	}
}
