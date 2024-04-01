package com.example.demo.like;

import java.util.List;

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

}
