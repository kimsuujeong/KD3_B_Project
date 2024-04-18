package com.example.demo.like;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeServiceImpl implements LikeService{

	@Autowired
	LikeMapper likeMapper;
	
	// 유저아이디별 관심목록
	@Override
	public List<Like> list(String userID) {
		return likeMapper.list(userID);
	}

	// 관심목록 삽입
	@Override
	public void insert(Like like) {
		likeMapper.insert(like);
	}

	// 관심 삭제
	@Override
	public void delete(Integer likeID) {
		// TODO Auto-generated method stub
		likeMapper.delete(likeID);
	}

	// 관심했는지 확인
	@Override
	public int countLike(Integer postID, String userID) {
		// TODO Auto-generated method stub
		return likeMapper.countLike(postID, userID);
	}
}
