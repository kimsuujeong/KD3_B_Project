package com.example.demo.like;

import java.util.List;

public interface LikeService {

	List<Like> list(String userID);
	void insert(Like like);
	void delete(Integer likeID);
	int countLike(Integer postID, String userID);
}
