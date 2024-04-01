package com.example.demo.like;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeServiceImpl implements LikeService{

	@Autowired
	LikeMapper likeMapper;
	
	@Override
	public List<Like> list(String userID) {
		return likeMapper.list(userID);
	}

	@Override
	public void insert(Like like) {
		likeMapper.insert(like);
	}

}
